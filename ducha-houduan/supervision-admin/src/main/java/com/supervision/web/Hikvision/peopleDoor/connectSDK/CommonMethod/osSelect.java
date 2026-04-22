package com.supervision.web.Hikvision.peopleDoor.connectSDK.CommonMethod;


import org.springframework.stereotype.Component;

@Component
public class osSelect {

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

}
