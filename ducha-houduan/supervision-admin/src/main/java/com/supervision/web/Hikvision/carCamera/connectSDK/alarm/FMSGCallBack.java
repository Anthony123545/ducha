package com.supervision.web.Hikvision.carCamera.connectSDK.alarm;


import com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Pointer;
import org.springframework.stereotype.Component;

/**
 * @author jiangxin
 * @create 2022-08-15-17:26
 */
@Component
public class FMSGCallBack implements HCNetSDK.FMSGCallBack {

    // 报警信息回调函数
    @Override
    public void invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        CarCameraAlarmDataParse.alarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        return;
    }

}
