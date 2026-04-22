package com.supervision.web.Hikvision.util;

import com.supervision.web.Hikvision.webSocket.EventWebSocket;
import java.util.*;

public class DoorGateEventSimulator {

    private static Random random = new Random();

    // 生成 NET_DVR_TIME
    private static Map<String, Integer> generateTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -random.nextInt(3600)); // 随机1小时内
        Map<String, Integer> time = new HashMap<>();
        time.put("dwYear", cal.get(Calendar.YEAR));
        time.put("dwMonth", cal.get(Calendar.MONTH) + 1);
        time.put("dwDay", cal.get(Calendar.DAY_OF_MONTH));
        time.put("dwHour", cal.get(Calendar.HOUR_OF_DAY));
        time.put("dwMinute", cal.get(Calendar.MINUTE));
        time.put("dwSecond", cal.get(Calendar.SECOND));
        return time;
    }

    // 生成 NET_DVR_IPADDR
    private static Map<String, String> generateIp() {
        Map<String, String> ip = new HashMap<>();
        ip.put("sIpV4", "192.168.1." + (random.nextInt(200) + 1));
        ip.put("sIpV6", ""); // 暂时留空
        return ip;
    }

    public static void getStart() {
        Random random = new Random();
        long[] deviceIds = {1, 2, 3}; // 三个设备 ID
        for (int i = 0; i < 30; i++) {
            Map<String, Object> doorGateEvent = new HashMap<>();
            doorGateEvent.put("dwSize", 128);
            doorGateEvent.put("dwMajor", random.nextInt(5));
            doorGateEvent.put("dwMinor", random.nextInt(10));
            doorGateEvent.put("struTime", generateTime());
            doorGateEvent.put("sNetUser", "user" + (random.nextInt(10) + 1));
            doorGateEvent.put("struRemoteHostAddr", generateIp());

            Map<String, Object> acsEventInfo = new HashMap<>();
            acsEventInfo.put("eventType", random.nextBoolean() ? "card" : "face");
            acsEventInfo.put("gateId", random.nextInt(5) + 1);
            acsEventInfo.put("status", random.nextBoolean() ? "open" : "close");
            doorGateEvent.put("struAcsEventInfo", acsEventInfo);

            doorGateEvent.put("dwPicDataLen", random.nextBoolean() ? 1024 : 0);
            doorGateEvent.put("pPicData", random.nextBoolean() ? "http://example.com/pic.jpg" : null);
            doorGateEvent.put("wInductiveEventType", random.nextInt(3));
            doorGateEvent.put("byPicTransType", random.nextInt(2));
            doorGateEvent.put("dwIOTChannelNo", random.nextInt(5));

            // ✅ 随机设备ID
            long deviceId = deviceIds[random.nextInt(deviceIds.length)];

            EventWebSocket.broadcast("doorGate", deviceId, doorGateEvent);
            EventWebSocket.broadcast("carGate", deviceId, doorGateEvent);

            try {
                Thread.sleep(500); // 模拟事件间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
