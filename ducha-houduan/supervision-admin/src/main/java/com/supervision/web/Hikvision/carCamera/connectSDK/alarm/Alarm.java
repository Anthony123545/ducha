package com.supervision.web.Hikvision.carCamera.connectSDK.alarm;


import com.supervision.web.Hikvision.carCamera.connectSDK.CommonMethod.osSelect;
import com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Alarm {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Alarm.class);

    private HCNetSDK hCNetSDK;

    private int lUserID = -1; // 用户句柄 实现对设备登录

    private int lAlarmHandle = -1; // 报警布防句柄

    private int lListenHandle = -1; // 报警监听句柄

    private CarCameraFMSGCallBack_V31 fMSFCallBack_V31 = null; // 报警布防回调函数

    private FMSGCallBack fMSFCallBack = null; // 报警监听回调函数

    /**
     * 动态库加载
     *
     * @return
     */
    private boolean createSDKInstance() {
        if (hCNetSDK == null) {
            synchronized (HCNetSDK.class) {
                String strDllPath = "";
                try {
                    if (osSelect.isWindows())
                    //win系统加载库路径
                    {
                        strDllPath = System.getProperty("user.dir") + "\\supervision-admin\\lib\\HCNetSDK.dll";
                    } else if (osSelect.isLinux())
                    //Linux系统加载库路径
                    {
                        strDllPath = System.getProperty("user.dir") + "/supervision-admin/lib/libhcnetsdk.so";
                    }
                    hCNetSDK = (HCNetSDK) Native.loadLibrary(strDllPath, HCNetSDK.class);
                } catch (Exception ex) {
                    logger.info("loadLibrary: " + strDllPath + " Error: " + ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param args
     */
    public void main(String[] args) throws InterruptedException {
        if (hCNetSDK == null) {
            if (!createSDKInstance()) {
                logger.info("Load SDK fail");
                return;
            }
        }
        // linux系统建议调用以下接口加载组件库
        if (osSelect.isLinux()) {
            HCNetSDK.BYTE_ARRAY ptrByteArray1 = new HCNetSDK.BYTE_ARRAY(256);
            HCNetSDK.BYTE_ARRAY ptrByteArray2 = new HCNetSDK.BYTE_ARRAY(256);
            //这里是库的绝对路径，请根据实际情况修改，注意改路径必须有访问权限
            String strPath1 = System.getProperty("user.dir") + "/supervision-admin/lib/libcrypto.so.1.1";
            String strPath2 = System.getProperty("user.dir") + "/supervision-admin/lib/libssl.so.1.1";
            System.arraycopy(strPath1.getBytes(), 0, ptrByteArray1.byValue, 0, strPath1.length());
            ptrByteArray1.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_LIBEAY_PATH, ptrByteArray1.getPointer());
            System.arraycopy(strPath2.getBytes(), 0, ptrByteArray2.byValue, 0, strPath2.length());
            ptrByteArray2.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_SSLEAY_PATH, ptrByteArray2.getPointer());
            String strPathCom = System.getProperty("user.dir") + "/supervision-admin/lib/";
            HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
            System.arraycopy(strPathCom.getBytes(), 0, struComPath.sPath, 0, strPathCom.length());
            struComPath.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_SDK_PATH, struComPath.getPointer());
        }

        /**初始化*/
        hCNetSDK.NET_DVR_Init();
        /**加载日志*/
        hCNetSDK.NET_DVR_SetLogToFile(3, "./sdklog1", false);


        // 设置报警回调函数
        if (fMSFCallBack_V31 == null) {
            fMSFCallBack_V31 = new CarCameraFMSGCallBack_V31();
            Pointer pUser = null;
            boolean setCallback = hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(
                    fMSFCallBack_V31, // fMessageCallBack [in] 回调函数
                    pUser // pUser [in] 用户数据
            );
            if (!setCallback) {
                logger.info("设置回调函数失败!");
                return;
            } else {
                logger.info("设置回调函数成功!");
            }
        }

        /** 设备上传的报警信息是COMM_VCA_ALARM(0x4993)类型，
         在SDK初始化之后增加调用NET_DVR_SetSDKLocalCfg(enumType为NET_DVR_LOCAL_CFG_TYPE_GENERAL)设置通用参数NET_DVR_LOCAL_GENERAL_CFG的byAlarmJsonPictureSeparate为1，
         将Json数据和图片数据分离上传，这样设置之后，报警布防回调函数里面接收到的报警信息类型为COMM_ISAPI_ALARM(0x6009)，
         报警信息结构体为NET_DVR_ALARM_ISAPI_INFO（与设备无关，SDK封装的数据结构），更便于解析。*/

        HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG struNET_DVR_LOCAL_GENERAL_CFG = new HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG();
        struNET_DVR_LOCAL_GENERAL_CFG.byAlarmJsonPictureSeparate = 1;   //设置JSON透传报警数据和图片分离
        struNET_DVR_LOCAL_GENERAL_CFG.write();
        Pointer pStrNET_DVR_LOCAL_GENERAL_CFG = struNET_DVR_LOCAL_GENERAL_CFG.getPointer();
        hCNetSDK.NET_DVR_SetSDKLocalCfg(17, pStrNET_DVR_LOCAL_GENERAL_CFG);

        lUserID = loginDevice("10.9.137.17", (short) 8000, "admin", "hik12345");  //登录设备

        for (boolean exit = false; !exit; ) {
            logger.info("请输入您想要执行的demo实例! （退出请输入yes）");
            Scanner input = new Scanner(System.in);
            String str = input.next();
            // 转换为标准输入
            str = str.toLowerCase();
            if (str.equals("yes")) {
                // 退出程序
                exit = true;
                break;
            }
            switch (str) {
//                case "1": {
//                    logger.info("\n[Module]报警布防示例代码");
//                    lAlarmHandle = Alarm.setAlarmChan(lUserID);//报警布防，和报警监听二选一即可
//                    break;
//                }
//                case "2": {
//                    logger.info("\n[Module]报警撤防示例代码");
//                    Alarm.closedAlarmChan(lAlarmHandle);
//                    break;
//                }
//                case "3": {
//                    //报警监听不需要登录设备，Alarm.loginDevice登录接口可以注释
//                    logger.info("\n[Module]开启报警监听示例代码");
//                    lListenHandle = Alarm.startListen("10.9.137.101", (short) 7201); //传入监听PC本机的IP地址和端口
//                    break;
//                }
//                case "4": {
//                    logger.info("\n[Module]停止监听示例代码");
//                    Alarm.stopListen(lListenHandle);
//                    break;
//                }
//                default: {
//                    logger.info("\n未知的指令操作!请重新输入!\n");
//                }
            }
        }
        //设备注销
        logoutDev(lUserID);
        //释放SDK
        hCNetSDK.NET_DVR_Cleanup();
        return;
    }

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param ip   设备IP地址
     * @param port SDK端口，默认为设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     * @return 登录成功返回用户ID，失败返回-1
     */
    public int loginDevice(String ip, short port, String user, String psw) {
        // 创建设备登录信息和设备信息对象
        HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
        HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();

        // 设置设备IP地址
        byte[] deviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        byte[] ipBytes = ip.getBytes();
        System.arraycopy(ipBytes, 0, deviceAddress, 0, Math.min(ipBytes.length, deviceAddress.length));
        loginInfo.sDeviceAddress = deviceAddress;

        // 设置用户名和密码
        byte[] userName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        byte[] password = psw.getBytes();
        System.arraycopy(user.getBytes(), 0, userName, 0, Math.min(user.length(), userName.length));
        System.arraycopy(password, 0, loginInfo.sPassword, 0, Math.min(password.length, loginInfo.sPassword.length));
        loginInfo.sUserName = userName;

        // 设置端口和登录模式
        loginInfo.wPort = port;
        loginInfo.bUseAsynLogin = false; // 同步登录
        loginInfo.byLoginMode = 0; // 使用SDK私有协议

        // 执行登录操作
        int userID = hCNetSDK.NET_DVR_Login_V40(loginInfo, deviceInfo);
        if (userID == -1) {
            System.err.println("登录失败，错误码为: " + hCNetSDK.NET_DVR_GetLastError());
        } else {
            logger.info(ip + " 设备登录成功！");
            // 处理通道号逻辑
            int startDChan = deviceInfo.struDeviceV30.byStartDChan;
            logger.info("预览起始通道号: " + startDChan);
        }
        return userID; // 返回登录结果
    }

    /**
     * 设备登录V30
     *
     * @param ip   设备IP
     * @param port SDK端口，默认设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     */
    public void loginDeviceV30(String ip, short port, String user, String psw) {
        HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        lUserID = hCNetSDK.NET_DVR_Login_V30(ip, port, user, psw, m_strDeviceInfo);
        logger.info("UsID:" + lUserID);
        if ((lUserID == -1) || (lUserID == 0xFFFFFFFF)) {
            logger.info("登录失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            logger.info(ip + ":设备登录成功！");
            return;
        }
    }


    // 获取设备能力集
    private boolean getCameraAbility() {
        HCNetSDK.NET_DVR_STD_ABILITY ability = new HCNetSDK.NET_DVR_STD_ABILITY();
        ability.dwOutSize = ability.size();
        ability.write();

        // TRUE表示成功，FALSE表示失败。接口返回失败请调用NET_DVR_GetLastError获取错误码，通过错误码判断出错原因。
        boolean res = hCNetSDK.NET_DVR_GetDeviceAbility(
                lUserID, // lUserID [in] NET_DVR_Login_V40等登录接口的返回值
                HCNetSDK.DEVICE_ABILITY_INFO, // dwAbilityType [in] // 能力类型DEVICE_ABILITY_INFO 智能交通摄像机和ITS智能终端设备能力
                null, // pInBuf [in] 输入缓冲区指针（按照设备规定的能力参数的描述方式组合，可以是XML文本或结构体形式，详见“Remarks”说明）
                0, // dwInLength [in] 输入缓冲区的长度
                ability.getPointer(), // pOutBuf [out] 输出缓冲区指针（按照设备规定的能力集的描述方式，可以是XML文本或结构体形式，详见“Remarks”说明）
                ability.size() // dwOutLength [in] 接收数据的缓冲区的长度
        );
        if(!res) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            logger.info("获取设备能力集失败！错误码：" + errorCode);
            return false;
        }
        logger.info("获取设备能力集成功");
        return true;
    }

    /**
     * 报警布防
     *
     * @param userID 设备登录句柄ID
     * @return
     */
    public int setAlarmChan(int userID) {
        if (userID == -1) {
            logger.info("请先注册");
            return -1;
        }
        if (lAlarmHandle < 0)//尚未布防,需要布防
        {
            //报警布防参数设置
            HCNetSDK.NET_DVR_SETUPALARM_PARAM alarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            alarmInfo.dwSize = alarmInfo.size();
            alarmInfo.byLevel = 0;  //布防等级
            alarmInfo.byAlarmInfoType =
                    1;   // 智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
            alarmInfo.byDeployType = 0;   //布防类型：0-客户端布防，1-实时布防，客户端布防仅支持一路
            alarmInfo.write();
            lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(userID, alarmInfo);
            if (lAlarmHandle == -1) {
                System.err.println("布防失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
                return -1;
            } else {
                logger.info("布防成功");
                return lAlarmHandle;
            }
        } else {
            logger.info("设备已经布防，请先撤防！");
            return lAlarmHandle;
        }
    }

    /**
     * 设备撤防
     *
     * @param AlarmHandle 布防句柄
     */
    public void closedAlarmChan(int AlarmHandle) {
        if (AlarmHandle <= -1) {
            logger.info("设备未布防，请先布防！");
            return;
        }
        if (!hCNetSDK.NET_DVR_CloseAlarmChan(AlarmHandle)) {
            System.err.println("撤防失败，err " + hCNetSDK.NET_DVR_GetLastError());
            return;
        }
        logger.info("撤防成功");
        return;
    }

    /**
     * 报警布防V50接口
     *
     * @param
     */
    public int setAlarmChanV50(int userID) {
        if (userID == -1) {
            logger.info("请先注册");
            return -1;
        }
        if (lAlarmHandle < 0) // 尚未布防,需要布防
        {
            //报警布防参数设置
            HCNetSDK.NET_DVR_SETUPALARM_PARAM_V50 m_strAlarmInfoV50 = new HCNetSDK.NET_DVR_SETUPALARM_PARAM_V50();
            m_strAlarmInfoV50.dwSize = m_strAlarmInfoV50.size();
            m_strAlarmInfoV50.byLevel = 0;  //布防等级
            m_strAlarmInfoV50.byAlarmInfoType =
                    1;   // 智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
            m_strAlarmInfoV50.byRetAlarmTypeV40 =
                    1; //0- 移动侦测、视频丢失、遮挡、IO信号量等报警信息以普通方式上传（报警类型：COMM_ALARM_V30，报警信息结构体：NET_DVR_ALARMINFO_V30），
            // 1- 报警信息以数据可变长方式上传（报警类型：COMM_ALARM_V40，报警信息结构体：NET_DVR_ALARMINFO_V40，设备若不支持则仍以普通方式上传）
            m_strAlarmInfoV50.byDeployType = 0;   //布防类型：0-客户端布防，1-实时布防
            m_strAlarmInfoV50.write();


            lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V50(
                    userID,
                    m_strAlarmInfoV50,
                    Pointer.NULL,
                    0
            );
            if (lAlarmHandle == -1) {
                System.err.println("布防失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
                return -1;
            } else {
                logger.info("布防成功");
                return lAlarmHandle;
            }
        } else {
            logger.info("设备已经布防，请先撤防！");
            return lAlarmHandle;
        }
    }

    /**
     * 开启监听
     *
     * @param ip   监听IP
     * @param port 监听端口
     */
    public int startListen(String ip, short port) {
        if (lListenHandle <= 0) {
            if (fMSFCallBack == null) {
                fMSFCallBack = new FMSGCallBack();
            }
            lListenHandle = hCNetSDK.NET_DVR_StartListen_V30(ip, port, fMSFCallBack, null);
            if (lListenHandle == -1) {
                logger.info("监听失败" + hCNetSDK.NET_DVR_GetLastError());
                return -1;
            } else {
                logger.info("监听成功");
                return lListenHandle;
            }
        } else {
            logger.info("监听已经开启，请先停止监听！");
            return lListenHandle;
        }
    }

    /**
     * 停止监听
     *
     * @param Handle 监听句柄
     */
    public void stopListen(int Handle) {
        if (Handle <= -1) {
            logger.info("监听未开启");
            return;
        }
        if (!hCNetSDK.NET_DVR_StopListen_V30(Handle)) {
            System.err.println("停止监听失败，err: " + hCNetSDK.NET_DVR_GetLastError());
            return;
        }
        logger.info("停止监听成功");
        return;
    }

    /**
     * 设备注销
     *
     * @param
     */
    public void logoutDev(int userID) {
        if (userID > -1) {
            if (hCNetSDK.NET_DVR_Logout(userID)) {
                logger.info("注销成功");
                return;
            }
        } else {
            logger.info("设备未注册，请先注册");
            return;
        }
        return;
    }

}
