package com.supervision.web.Hikvision.peopleDoor.connectSDK.Acs;


import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.ptr.IntByReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 门禁设备管理：
 * 1、设置门禁主机参数
 * 2、获取门禁主机参数
 * 3、获取门禁主机工作状态
 * 4、远程控门
 */
@Component
public final class ACSManage {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ACSManage.class);

    /**
     * 获取门禁主机参数
     *
     * @param userID 用户登录句柄
     */
    public void getAcsCfg(HCNetSDK hCNetSDK, int userID) {
        // 参数验证
        if (userID < 0) {
            logger.warn(String.format("无效的userID：%d", userID));
            return;
        }

        // 获取门禁主机配置
        HCNetSDK.NET_DVR_ACS_CFG struAcsCfg = new HCNetSDK.NET_DVR_ACS_CFG();
        struAcsCfg.dwSize = struAcsCfg.size();
        struAcsCfg.write();

        IntByReference intByReference = new IntByReference(0);
        boolean b_GetAcsCfg = false;
        int retryCount = 0;

        // 尝试获取门禁主机配置，最多重试3次
        while (retryCount < 3) {
            try {
                b_GetAcsCfg = hCNetSDK.NET_DVR_GetDVRConfig(
                        userID,
                        HCNetSDK.NET_DVR_GET_ACS_CFG, // 获取门禁主机参数
                        0xFFFFFFFF, // 无效的通道号，表示获取所有配置
                        struAcsCfg.getPointer(), // 输入缓冲区
                        struAcsCfg.size(), // 输入缓冲区大小
                        intByReference // 实际接收到的字节数
                );

                if (!b_GetAcsCfg) {
                    int errorCode = hCNetSDK.NET_DVR_GetLastError();
                    logger.warn(String.format("获取门禁主机参数失败，错误码为：%d", errorCode));
                } else {
                    logger.info("获取门禁主机参数成功");
                    struAcsCfg.read();
                    logger.info("1. 是否显示抓拍图片: " + struAcsCfg.byShowCapPic);
                    logger.info("2. 是否显示卡号: " + struAcsCfg.byShowCardNo);
                    logger.info("3. 是否开启语音提示: " + struAcsCfg.byVoicePrompt);
                    logger.info("4. 联动抓图是否上传: " + struAcsCfg.byUploadCapPic);
                    logger.info("5. 是否保存抓拍图片: " + struAcsCfg.bySaveCapPic);
                    logger.info("6. 是否启用下行RS485通信备份: " + struAcsCfg.byRS485Backup);
                    return; // 成功则返回，不需要重试
                }
            } catch (Exception e) {
                logger.warn("获取门禁主机配置时发生异常: " + e.getMessage());
            }

            retryCount++;
            if (retryCount < 3) {
                logger.info("正在重试获取门禁主机参数... (" + retryCount + "/" + 3 + ")");
                try {
                    Thread.sleep(1000); // 每次重试之间增加延迟
                } catch (InterruptedException ex) {
                    logger.warn("重试过程中发生中断: " + ex.getMessage());
                }
            }
        }

        // 如果重试多次后仍然失败，给出提示
        logger.warn("获取门禁主机参数失败，请检查设备连接或网络状况！");
    }

    /**
     * 设置门禁主机参数
     *
     * @param userID 用户登录句柄
     */
    public void setAcsCfg(HCNetSDK hCNetSDK, int userID) {
        // 参数验证
        if (userID < 0) {
            logger.warn(String.format("无效的userID：%d", userID));
            return;
        }

        HCNetSDK.NET_DVR_ACS_CFG struAcsCfg = new HCNetSDK.NET_DVR_ACS_CFG();
        struAcsCfg.dwSize = struAcsCfg.size();
        struAcsCfg.write(); // 写入默认配置

        // 设置参数
        struAcsCfg.byRS485Backup = 0; // 是否启用下行RS485通信备份功能：0- 不启用，1- 启用
        struAcsCfg.byShowCapPic = 1; // 是否显示抓拍图片：0- 不显示，1- 显示
        struAcsCfg.byShowCardNo = 1; // 是否显示卡号：0- 不显示，1- 显示
        struAcsCfg.byShowUserInfo = 1; // 是否显示用户信息：0- 不显示，1- 显示
        struAcsCfg.byOverlayUserInfo = 1; // 是否叠加用户信息：0- 不叠加，1- 叠加
        struAcsCfg.byVoicePrompt = 0; // 是否启用语音提示：0- 不启用，1- 启用
        struAcsCfg.byUploadCapPic = 1; // 联动抓拍是否上传图片：0- 不上传，1- 上传
        struAcsCfg.bySaveCapPic = 1; // 是否保存抓拍图片：0- 不保存，1- 保存
        struAcsCfg.write();

        boolean b_SetAcsCfg = hCNetSDK.NET_DVR_SetDVRConfig(
                userID,
                HCNetSDK.NET_DVR_SET_ACS_CFG, // 设置门禁主机参数
                0xFFFFFFFF, // 无效的通道号，表示设置所有配置
                struAcsCfg.getPointer(), // 输入缓冲区
                struAcsCfg.size() // 输入缓冲区大小
        );

        if (!b_SetAcsCfg) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            logger.warn(String.format("设置门禁主机参数失败，错误码为：%d", errorCode));
        } else {
            logger.info("设置门禁主机参数成功！");
        }
    }

    /**
     * 获取门禁主机工作状态
     *
     * @param userID 用户登录句柄
     */
    public void getAcsStatus(HCNetSDK hCNetSDK, int userID) {
        // 参数验证
        if (userID < 0) {
            logger.warn(String.format("无效的userID：%d", userID));
            return;
        }

        HCNetSDK.NET_DVR_ACS_WORK_STATUS_V50 netDvrAcsWorkStatusV50 = new HCNetSDK.NET_DVR_ACS_WORK_STATUS_V50();
        netDvrAcsWorkStatusV50.dwSize = netDvrAcsWorkStatusV50.size();
        netDvrAcsWorkStatusV50.write();

        IntByReference intByReference = new IntByReference(0);

        // 获取门禁主机状态
        boolean b_GetAcsStatus = false;
        // 尝试获取门禁主机状态，最多重试3次
        int retryCount = 0;
        while (retryCount < 3) {
            try {
                b_GetAcsStatus = hCNetSDK.NET_DVR_GetDVRConfig(
                        userID, // NET_DVR_Login_V40 等登录接口的返回值
                        HCNetSDK.NET_DVR_GET_ACS_WORK_STATUS_V50, // 设备配置命令， NET_DVR_GET_ACS_WORK_STATUS_V50 为获取门禁主机工作状态
                        0xFFFFFFFF, // 通道号，不同的命令对应不同的取值，如果该参数无效则置为0xFFFFFFFF即可
                        netDvrAcsWorkStatusV50.getPointer(), // 接收数据的缓冲指针
                        netDvrAcsWorkStatusV50.size(), // 接收数据的缓冲长度(以字节为单位)，不能为0
                        intByReference //  实际收到的数据长度指针，不能为NULL
                );

                if (!b_GetAcsStatus) {
                    int errorCode = hCNetSDK.NET_DVR_GetLastError();
                    logger.warn(String.format("获取门禁主机工作状态失败，错误码为：%d", errorCode));
                } else {
                    logger.info("获取门禁主机工作状态成功！");
                    netDvrAcsWorkStatusV50.read();

                    logger.info("1.门锁状态（或者梯控的继电器开合状态）：" + netDvrAcsWorkStatusV50.byDoorLockStatus[0] +
                            "\n"); // 门锁状态：0- 正常关，1- 正常开，2- 短路报警，3- 断路报警，4- 异常报警
                    logger.info("2.门状态（或者梯控的楼层状态）：" + netDvrAcsWorkStatusV50.byDoorStatus[0] +
                            "\n"); // 门状态（或者梯控的楼层状态）：1- 休眠，2- 常开状态（对于梯控，表示自由状态），3- 常闭状态（对于梯控，表示禁用状态），4- 普通状态（对于梯控，表示受控状态）
                    logger.info("3.门磁状态：" + netDvrAcsWorkStatusV50.byMagneticStatus[0] +
                            "\n"); // 门磁状态，0-正常关，1-正常开，2-短路报警，3-断路报警，4-异常报警
                    logger.info("4.事件报警输入状态：" + netDvrAcsWorkStatusV50.byCaseStatus[0] +
                            "\n"); // 事件报警输入状态：0- 无输入，1- 有输入
                    return; // 成功则返回，不需要重试
                }
            } catch (Exception e) {
                logger.warn("获取门禁主机状态时发生异常: " + e.getMessage());
            }

            retryCount++;
            if (retryCount < 3) {
                logger.info("正在重试获取门禁主机工作状态... (" + retryCount + "/" + 3 + ")");
                try {
                    Thread.sleep(1000); // 每次重试之间增加延迟
                } catch (InterruptedException ex) {
                    logger.warn("重试过程中发生中断: " + ex.getMessage());
                }
            }
        }

        // 如果重试多次后仍然失败，给出提示
        logger.warn("获取门禁主机工作状态失败，请检查设备连接或网络状况！");
    }

    /**
     * 远程控门
     *
     * @param userID       NET_DVR_Login_V40等登录接口的返回值  登录返回的用户ID
     * @param gatewayIndex 门禁序号（楼层编号、锁ID），从1开始，-1表示对所有门（或者梯控的所有楼层）进行操作
     * @param command      命令值 (0- 关闭（对于梯控，表示受控），1- 打开（对于梯控，表示开门），2- 常开（对于梯控，表示自由、通道状态），3- 常关（对于梯控，表示禁用），4- 恢复（梯控，普通状态），5- 访客呼梯（梯控），6- 住户呼梯（梯控）)
     */
    public boolean remoteControlGate(HCNetSDK hCNetSDK, int userID, int gatewayIndex, int command) {
        // 参数验证
        if (userID < 0) {
            logger.warn("无效的userID：{}", userID);
            return false;
        }
        if (gatewayIndex < -1) {
            logger.warn("无效的门禁序号：{}", gatewayIndex);
            return false;
        }
        if (command < 0 || command > 6) {
            logger.warn("无效的命令值：{}", command);
            return false;
        }

        // 调用SDK接口进行门禁控制
        boolean result = hCNetSDK.NET_DVR_ControlGateway(userID, gatewayIndex, command);
        if (!result) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            logger.warn("远程门禁操作失败 | userID={}, gatewayIndex={}, command={}, 错误码={}", userID, gatewayIndex, command, errorCode);
            return false;
        }

        logger.info("远程门禁操作成功 | userID={}, gatewayIndex={}, command={}", userID, gatewayIndex, command);
        return true;
    }

}
