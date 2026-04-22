package com.supervision.web.Hikvision.peopleDoor.connectSDK.alarm;


import com.sun.jna.Pointer;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {

    @Autowired
    public AlarmDataParse alarmDataParse;

    // 报警信息回调函数
    @Override
    public boolean invoke(
            int lCommand, // lCommand [out] 上传的消息类型，不同的报警信息对应不同的类型，通过类型区分是什么报警信息，详见“Remarks”中列表
            HCNetSDK.NET_DVR_ALARMER pAlarmer, // pAlarmer [out] 报警设备信息，包括设备序列号、IP地址、登录IUserID句柄等
            Pointer pAlarmInfo, // pAlarmInfo [out] 报警信息，通过lCommand值判断pAlarmer对应的结构体，详见“Remarks”中列表
            int dwBufLen, // dwBufLen [out] 报警信息缓存大小
            Pointer pUser // pUser[out] 用户数据
    ) {
        alarmDataParse.alarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        return true;
    }
}
