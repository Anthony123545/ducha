package com.supervision.web.Hikvision.carCamera.connectSDK.alarm;


import com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Pointer;
import org.springframework.stereotype.Component;

@Component
public class CarCameraFMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {

    // 报警信息回调函数
    @Override
    public boolean invoke(
            int lCommand, // lCommand [out] 上传的消息类型，不同的报警信息对应不同的类型，通过类型区分是什么报警信息，详见“Remarks”中列表
            HCNetSDK.NET_DVR_ALARMER pAlarmer, // pAlarmer [out] 报警设备信息，包括设备序列号、IP地址、登录IUserID句柄等
            Pointer pAlarmInfo, // pAlarmInfo [out] 报警信息，通过lCommand值判断pAlarmer对应的结构体，详见“Remarks”中列表
            int dwBufLen, // dwBufLen [out] 报警信息缓存大小
            Pointer pUser // pUser[out] 用户数据
    ) {
        CarCameraAlarmDataParse.alarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        return true;
    }

}







