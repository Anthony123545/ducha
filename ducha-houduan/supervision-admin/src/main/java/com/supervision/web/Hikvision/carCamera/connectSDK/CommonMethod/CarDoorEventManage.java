package com.supervision.web.Hikvision.carCamera.connectSDK.CommonMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CarDoorEventManage {

    private static final Logger logger = LoggerFactory.getLogger(CarDoorEventManage.class);

    // SDK 实例
//    private static final HCNetSDK sdk = HCNetSDK.INSTANCE;

    // 登录返回的用户句柄
    private int lUserID = -1;

    // 布防通道句柄
    private int lAlarmHandle = -1;

    /**
     * 初始化并布防门禁设备，接收实时事件
     */
    public void initPeopleDoorAlarm(String ip, short port, String username, String password) {
//        // 初始化 SDK
//        if (!sdk.NET_DVR_Init()) {
//            logger.error("SDK 初始化失败！");
//            return;
//        }
//        logger.info(" SDK 初始化成功");
//
//        // 设置报警回调函数 (V31)
//        boolean setCb = sdk.NET_DVR_SetDVRMessageCallBack_V31( new PeopleDoorFMSGCallBack_V31(), null);
//        if (!setCb) {
//            logger.error("设置报警回调函数失败，错误码：" + sdk.NET_DVR_GetLastError());
//            return;
//        }
//        logger.info(" 报警回调函数（V31）设置成功");

        // 登录设备（PeopleDoorDeviceManager里已登录）
//        HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
//        HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();

        // 登录信息设置
//        System.arraycopy(ip.getBytes(), 0, loginInfo.sDeviceAddress, 0, ip.length());
//        System.arraycopy(username.getBytes(), 0, loginInfo.sUserName, 0, username.length());
//        System.arraycopy(password.getBytes(), 0, loginInfo.sPassword, 0, password.length());
//        loginInfo.wPort = port;
//        loginInfo.bUseAsynLogin = false; // 同步登录
//
//        lUserID = sdk.NET_DVR_Login_V40(loginInfo, deviceInfo);
//        if (lUserID < 0) {
//            logger.error("设备登录失败，错误码：" + sdk.NET_DVR_GetLastError());
//            return;
//        }
//        logger.info(" 登录成功，设备IP：" + ip + "，用户句柄：" + lUserID);

        // 布防设置
//        HCNetSDK.NET_DVR_SETUPALARM_PARAM alarmParam = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
//        alarmParam.dwSize = alarmParam.size();
//        alarmParam.byLevel = 1;      // 0-一级布防, 1-二级布防
//        alarmParam.byAlarmInfoType = 1; // 1: 新报警信息（JSON结构）
//        alarmParam.byRetAlarmTypeV40 = 1; // 返回V40结构体信息
//
//        lAlarmHandle = sdk.NET_DVR_SetupAlarmChan_V41(lUserID, alarmParam);
//        if (lAlarmHandle < 0) {
//            logger.error("布防失败，错误码：" + sdk.NET_DVR_GetLastError());
//            return;
//        }
//        logger.info(" 布防成功，开始接收实时事件");
    }

    /**
     * 撤防并释放资源
     */
//    public void closePeopleDoorAlarm() {
//        if (lAlarmHandle >= 0) {
//            sdk.NET_DVR_CloseAlarmChan_V30(lAlarmHandle);
//            logger.info("撤防成功");
//        }
//        if (lUserID >= 0) {
//            sdk.NET_DVR_Logout(lUserID);
//            logger.info("设备登出成功");
//        }
//        sdk.NET_DVR_Cleanup();
//        logger.info("SDK 清理完成");
//    }


}
