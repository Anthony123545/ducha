package com.supervision.web.Hikvision.carDoor.connectSDK.hik;


import com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 远程控制道闸模块，功能：远程控制道闸开启、关闭、获取道闸状态
 */
@Component
public class BarrierGateManage {

    private static final Logger logger = LoggerFactory.getLogger(BarrierGateManage.class);

    @Autowired
    public ISAPI isapi;

    /**
     * 远程控制道闸
     * @param lUserID 登录返回的用户ID
     * @param laneNo 道闸号
     * @param operate 操作码：0-关闭，1-开启，2-停止，3-锁定，4-解锁
     * @return true=成功, false=失败
     */
    public boolean  BarrierGateCtrl(HCNetSDK hCNetSDK, int lUserID, int laneNo, int operate) {
        if (lUserID < 0 || laneNo <= 0 || operate < 0 || operate > 4) {
            logger.warn("参数不合法 lUserID={}, laneNo={}, operate={}", lUserID, laneNo, operate);
            return false;
        }

        // 初始化结构体
        HCNetSDK.NET_DVR_BARRIERGATE_CFG cfg = new HCNetSDK.NET_DVR_BARRIERGATE_CFG();
        cfg.read();
        cfg.dwSize = cfg.size(); // 结构体大小
        cfg.dwChannel = 1; // 通道号
        cfg.byLaneNo = (byte) laneNo; // 道闸号：0- 表示无效值(设备需要做有效值判断)，1- 道闸1
        cfg.byBarrierGateCtrl = (byte) operate; // 控制参数：0- 关闭道闸，1- 开启道闸，2- 停止道闸，3- 锁定道闸，4- 解锁道闸
        cfg.byEntranceNo = 1; // 出入口编号，取值范围：[1,8]
        cfg.byUnlock = 0; // 启用解锁使能：0- 不启用，1- 启用
        // 如果设备不支持byUnlock字段，该参数赋值为0，byBarrierGateCtrl控制参数取值“0-关闭道闸、1-开启道闸、2-停止道闸”中任何一种操作皆可进行解锁；
        // 如果设备支持byUnlock字段，解锁时需要将参数赋值为1，并结合byBarrierGateCtrl赋值为“4-解锁道闸”来进行解锁，byUnlock为1时其他操作均不能解锁
        cfg.byRes[0] = 0;
        cfg.write();

        // 调用 SDK 控制道闸 // TRUE表示成功，FALSE表示失败。接口返回失败请调用NET_DVR_GetLastError获取错误码，通过错误码判断出错原因。
        boolean res = hCNetSDK.NET_DVR_RemoteControl(
                lUserID, // lUserID [in] NET_DVR_Login_V40等登录接口的返回值
                HCNetSDK.NET_DVR_BARRIERGATE_CTRL, // dwCommand [in] 控制命令，详见列表 { NET_DVR_BARRIERGATE_CTRL 3128 远程控制道闸 }
                cfg.getPointer(), // lpInBuffer [in] 输入参数，具体内容跟控制命令相关，详见列表
                cfg.size() // dwInBufferSize [in] 输入参数长度
        );

        if (!res) {
            int errCode = hCNetSDK.NET_DVR_GetLastError();
            logger.error("远程控制道闸失败 laneNo={}, operate={}, errCode={}", laneNo, operate, errCode);
            return false;
        }

        String[] statusDesc = {"关闭", "开启", "停止", "锁定", "解锁"};
        logger.info("远程控制道闸成功 laneNo={}, 状态={}", laneNo, statusDesc[operate]);
        return true;
    }

    /**
     * 获取道闸状态
     * <barrierGateStatus>
     * <!--ro, opt, enum, 出入口道闸状态, subType:int, [0#无信号,1#关到位,2#开到位]-->1
     * </barrierGateStatus>
     *
     * @param lUserID
     */
    public String getBarrierGateState(HCNetSDK hCNetSDK, int lUserID) {
        String getBarrierGateStateUrl = "GET /ISAPI/Parking/channels/1/barrierGate/barrierGateStatus";
        String responseString = isapi.stdXMLConfig(
                hCNetSDK,
                lUserID,
                getBarrierGateStateUrl,
                ""
        );
        logger.info("获取道闸状态返回报文：" + responseString);
        return responseString;
    }

    // 出入口控制参数获取
    public void getEntranceParmCfg(HCNetSDK hCNetSDK, int lUserID) {
        // 出入口控制条件结构体 lpInBuffer对应结构体
        HCNetSDK.NET_DVR_BARRIERGATE_COND struBarrierCond = new HCNetSDK.NET_DVR_BARRIERGATE_COND();
        struBarrierCond.read();
        struBarrierCond.byLaneNo = 1; //车道号：0- 表示无效值(设备需要做有效值判断)，1- 车道1
        struBarrierCond.write();

        Pointer pStruBarrierCond = struBarrierCond.getPointer();

        // 出入口控制条件结构体 lpOutBuffer对应结构体
        HCNetSDK.NET_DVR_ENTRANCE_CFG struEnterCfg = new HCNetSDK.NET_DVR_ENTRANCE_CFG();
        struEnterCfg.read();
        struEnterCfg.dwSize = struEnterCfg.size(); // dwSize结构体大小
        struEnterCfg.write();
        Pointer pStruEnterCfg = struEnterCfg.getPointer();
        IntByReference list = new IntByReference(0);

        // TRUE表示成功，但不代表每一个配置都成功，哪一个成功，对应查看lpStatusList[n]值；FALSE表示全部失败。接口返回失败请调用NET_DVR_GetLastError获取错误码，通过错误码判断出错原因。
        boolean res = hCNetSDK.NET_DVR_GetDeviceConfig(
                lUserID, // lUserID [in] NET_DVR_Login_V40等登录接口的返回值
                HCNetSDK.NET_DVR_GET_ENTRANCE_PARAMCFG, // dwCommand [in] 设备配置命令，参见配置命令 {NET_DVR_GET_ENTRANCE_PARAMCFG 获取出入口控制参数}
                1, // dwCount [in] 一次要获取的监控点个数，0和1都表示1个监控点信息，2表示2个监控点信息，以此递增，最大64个
                pStruBarrierCond, // lpInBuffer [in] 配置条件缓冲区指针，如流id等
                struBarrierCond.size(), // dwInBufferSize [in] 配置条件缓冲区长度
                list.getPointer(), // lpStatusList [out] 错误信息列表，和要查询的监控点一一对应，例如lpStatusList[2]就对应lpInBuffer[2]，由用户分配内存，每个错误信息为4个字节(1个32位无符号整数值)，参数值：0或者1表示成功，其他值为失败对应的错误号
                pStruEnterCfg, // lpOutBuffer [out] 设备返回的参数内容，和要查询的监控点一一对应。如果某个监控点对应的lpStatusList信息为大于1的值，对应lpOutBuffer的内容就是无效的
                struEnterCfg.size() // dwOutBufferSize [in] dwCount个返回结果的总大小
        );
        if (!res) {
            int err = hCNetSDK.NET_DVR_GetLastError();
            logger.error("获取出入口控制参数失败，错误码：" + err);
        }
        logger.info("获取出入口控制参数成功");

        struEnterCfg.read();
        logger.info("触发模式：" + "0x" + Integer.toHexString(struEnterCfg.dwRelateTriggerMode));
        logger.info("道闸状态：" + struEnterCfg.byGateSingleIO[0]);  // 单个IO触发参数，数组0表示IO1，数组1表示IO2，依次类推，具体数组值的含义：0- 无，1- 道闸开到位，2- 道闸关到位，3- 消防报警
    }

}
