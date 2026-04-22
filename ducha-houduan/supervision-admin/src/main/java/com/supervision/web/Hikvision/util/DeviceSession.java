package com.supervision.web.Hikvision.util;


import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import lombok.Data;

/**
 * 通用设备会话对象（支持多种类型 SDK）
 * -------------------------------------------------
 * 注意：不直接依赖 HCNetSDK 结构体，以避免多 SDK 类型冲突。
 */
@Data
public class DeviceSession<TLoginInfo, TDeviceInfo> {

    private String ip;

    private int userID;

    private SdkType sdkType;

    private Device device;

    private TLoginInfo loginInfo;

    private TDeviceInfo deviceInfo;

    private int alarmHandle;  // 设备专属布防句柄

    public DeviceSession(String ip, int userID, SdkType sdkType, Device device, TLoginInfo loginInfo, TDeviceInfo deviceInfo, int alarmHandle) {
        this.ip = ip;
        this.userID = userID;
        this.sdkType = sdkType;
        this.device = device;
        this.loginInfo = loginInfo;
        this.deviceInfo = deviceInfo;
        this.alarmHandle = alarmHandle;
    }

}
