package com.supervision.web.Hikvision.peopleDoor.connectSDK.Acs;


import com.alibaba.fastjson2.JSONObject;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Pointer;
import com.supervision.web.Hikvision.peopleDoor.web.util.ACSAlarmJsonUtil;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 事件查询模块
 */
@Component
public final class EventSearch {

    public static final Logger logger = LoggerFactory.getLogger(EventSearch.class);

    /**
     * 创建NET_DVR_TIME对象
     */
    private HCNetSDK.NET_DVR_TIME createNetDvrTime(LocalDateTime localDateTime) {
        HCNetSDK.NET_DVR_TIME time = new HCNetSDK.NET_DVR_TIME();
        time.read(); // 初始化结构体
        time.dwYear = localDateTime.getYear();
        time.dwMonth = localDateTime.getMonthValue();
        time.dwDay = localDateTime.getDayOfMonth();
        time.dwHour = localDateTime.getHour();
        time.dwMinute = localDateTime.getMinute();
        time.dwSecond = localDateTime.getSecond();
        return time;
    }

    /**
     * 门禁事件查询，基于起止时间，事件类型进行查询
     *
     * @param lUserID
     */
    public Map<String, Object> searchAllEvent(HCNetSDK hCNetSDK, int lUserID, Map<String, Object> condition) {
        Map<String, Object> res = new HashMap<>();
        int lHandler = -1;

        try {
            List<Map<String, Object>> eventList = new ArrayList<>();
            int i = 0; //事件条数

            // 构建查询条件
            HCNetSDK.NET_DVR_ACS_EVENT_COND struAcsEventCond = buildAcsEventConditionSafely(condition);

            Pointer ptrStruEventCond = struAcsEventCond.getPointer();
            lHandler =
                    hCNetSDK.NET_DVR_StartRemoteConfig(
                            lUserID,
                            HCNetSDK.NET_DVR_GET_ACS_EVENT,
                            ptrStruEventCond,
                            struAcsEventCond.size(),
                            null,
                            null);
            if (lHandler <= -1) {
                logger.debug("NET_DVR_StartRemoteConfig调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                res.put("status", false);
                res.put("message", "NET_DVR_StartRemoteConfig调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                return res;
            }

            HCNetSDK.NET_DVR_ACS_EVENT_CFG struAcsEventCfg = new HCNetSDK.NET_DVR_ACS_EVENT_CFG();
            struAcsEventCfg.read();
            struAcsEventCfg.dwSize = struAcsEventCfg.size();
            struAcsEventCfg.write();
            Pointer ptrStruEventCfg = struAcsEventCfg.getPointer();

            // === 循环读取事件记录 ===
            while (true) {
                logger.debug("i=" + i);
                int dwEventSearch = hCNetSDK.NET_DVR_GetNextRemoteConfig(
                        lHandler,
                        ptrStruEventCfg,
                        struAcsEventCfg.size()
                );
                if (dwEventSearch <= -1) {
                    logger.debug("NET_DVR_GetNextRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                }
                if (dwEventSearch == HCNetSDK.NET_SDK_GET_NEXT_STATUS_NEED_WAIT) {
                    logger.debug("配置等待....");
                    Thread.sleep(10);
                    continue;
                } else if (dwEventSearch == HCNetSDK.NET_SDK_NEXT_STATUS__FINISH) {
                    logger.debug("获取事件完成");
                    break;
                } else if (dwEventSearch == HCNetSDK.NET_SDK_GET_NEXT_STATUS_FAILED) {
                    logger.debug("获取事件出现异常");
                    res.put("status", "error");
                    res.put("message", "NET_DVR_GetNextRemoteConfig失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                    break;
                } else if (dwEventSearch == HCNetSDK.NET_SDK_GET_NEXT_STATUS_SUCCESS) {
                    struAcsEventCfg.read();
                    // 获取的事件信息通过struAcsEventCfg结构体进行解析，
                    logger.debug(
                            i + "获取事件成功, 报警主类型：" + Integer.toHexString(struAcsEventCfg.dwMajor) + "报警次类型：" +
                                    Integer.toHexString(struAcsEventCfg.dwMinor) + "卡号：" +
                                    new String(struAcsEventCfg.struAcsEventInfo.byCardNo).trim());
                    /**
                     * 工号有两个字段，dwEmployeeNo表示工号，当值为0时，表示无效，解析byEmployeeNo参数
                     */
                    JSONObject alarmJson = ACSAlarmJsonUtil.convertSearchAlarmEventInfoToJson(struAcsEventCfg);
                    eventList.add(alarmJson);
                    i++;
                }
            }
            i = 0;
            // === 返回结构化结果 ===
            res.put("status", "success");
            res.put("count", eventList.size());
            res.put("data", eventList);
        } catch (JSONException e) {
            logger.error("JSON解析异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "JSON解析异常");
        } catch (Exception e) {
            logger.error("查询事件信息异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "查询事件信息异常");
        } finally {
            if (lHandler >= 0) stopRemoteConfig(hCNetSDK, lHandler, res);
        }
        return res;
    }

    /**
     * 构建海康威视ACS事件查询条件（安全版本）
     * @param condition 查询条件Map，可为null
     * @return HCNetSDK.NET_DVR_ACS_EVENT_COND 事件查询条件对象
     */
    public HCNetSDK.NET_DVR_ACS_EVENT_COND buildAcsEventConditionSafely(Map<String, Object> condition) {
        if (condition == null) {
            condition = new HashMap<>();
        }

        HCNetSDK.NET_DVR_ACS_EVENT_COND struAcsEventCond = new HCNetSDK.NET_DVR_ACS_EVENT_COND();
        struAcsEventCond.read();
        struAcsEventCond.dwSize = struAcsEventCond.size();

        try {
            // 1. 主次事件类型（安全转换）
            struAcsEventCond.dwMajor = safeGetInteger(condition, "dwMajor", 0);
            struAcsEventCond.dwMinor = safeGetInteger(condition, "dwMinor", 0);

            // 2. 时间范围
            struAcsEventCond.struStartTime = parseStartTime(condition);
            struAcsEventCond.struEndTime = parseEndTime(condition);

            // 3. 其他字段
            struAcsEventCond.byPicEnable = safeGetByte(condition, "byPicEnable", (byte) 0);
            struAcsEventCond.byCardNo = safeGetByteArray(condition, "byCardNo");
            struAcsEventCond.byName = safeGetByteArray(condition, "byName");
            struAcsEventCond.byTimeType = safeGetByte(condition, "byTimeType", (byte) 0);
            struAcsEventCond.wInductiveEventType = safeGetShort(condition, "wInductiveEventType", (short) 0);
            struAcsEventCond.dwBeginSerialNo = safeGetInteger(condition, "dwBeginSerialNo", 0);
            struAcsEventCond.dwEndSerialNo = safeGetInteger(condition, "dwEndSerialNo", 0);
            struAcsEventCond.dwIOTChannelNo = safeGetInteger(condition, "dwIOTChannelNo", 0);
            struAcsEventCond.bySearchType = safeGetByte(condition, "bySearchType", (byte) 0);

            // 保留字段
            struAcsEventCond.byRes2 = new byte[0];
            struAcsEventCond.byRes = new byte[0];

            struAcsEventCond.write();

        } catch (Exception e) {
            logger.error("构建ACS事件查询条件时出错", e);
            throw new RuntimeException("构建查询条件失败", e);
        }

        return struAcsEventCond;
    }

    /**
     * 解析开始时间（带验证）
     */
    private HCNetSDK.NET_DVR_TIME parseStartTime(Map<String, Object> condition) {
        try {
            String startTimeStr = (String) condition.get("struStartTime");
            if (startTimeStr == null || startTimeStr.trim().isEmpty()) {
                // 默认开始时间：一周前
                LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
                logger.debug("使用默认开始时间: {}", oneWeekAgo);
                return createNetDvrTime(oneWeekAgo);
            } else {
                HCNetSDK.NET_DVR_TIME startTime = parseDateTime(startTimeStr);
                logger.debug("解析开始时间: {} -> {}", startTimeStr, formatNetDvrTime(startTime));
                return startTime;
            }
        } catch (Exception e) {
            logger.error("解析开始时间失败，使用默认时间", e);
            return createNetDvrTime(LocalDateTime.now().minusWeeks(1));
        }
    }

    /**
     * 解析结束时间（带验证）
     */
    private HCNetSDK.NET_DVR_TIME parseEndTime(Map<String, Object> condition) {
        try {
            String endTimeStr = (String) condition.get("struEndTime");
            if (endTimeStr == null || endTimeStr.trim().isEmpty()) {
                // 默认结束时间：当前时间
                LocalDateTime now = LocalDateTime.now();
                logger.debug("使用默认结束时间: {}", now);
                return createNetDvrTime(now);
            } else {
                HCNetSDK.NET_DVR_TIME endTime = parseDateTime(endTimeStr);
                logger.debug("解析结束时间: {} -> {}", endTimeStr, formatNetDvrTime(endTime));

                // 验证结束时间不能早于开始时间
                HCNetSDK.NET_DVR_TIME startTime = parseStartTime(condition);
                if (isTimeBefore(endTime, startTime)) {
                    logger.warn("结束时间不能早于开始时间，自动调整为开始时间后1小时");
                    return createNetDvrTime(
                            LocalDateTime.of(
                                    startTime.dwYear, startTime.dwMonth, startTime.dwDay,
                                    startTime.dwHour, startTime.dwMinute, startTime.dwSecond
                            ).plusHours(1)
                    );
                }

                return endTime;
            }
        } catch (Exception e) {
            logger.error("解析结束时间失败，使用默认时间", e);
            return createNetDvrTime(LocalDateTime.now());
        }
    }

    /**
     * 比较两个NET_DVR_TIME的时间先后
     */
    private boolean isTimeBefore(HCNetSDK.NET_DVR_TIME time1, HCNetSDK.NET_DVR_TIME time2) {
        LocalDateTime dt1 = LocalDateTime.of(
                time1.dwYear, time1.dwMonth, time1.dwDay,
                time1.dwHour, time1.dwMinute, time1.dwSecond
        );
        LocalDateTime dt2 = LocalDateTime.of(
                time2.dwYear, time2.dwMonth, time2.dwDay,
                time2.dwHour, time2.dwMinute, time2.dwSecond
        );
        return dt1.isBefore(dt2);
    }

    /**
     * 格式化NET_DVR_TIME为字符串（用于日志）
     */
    private String formatNetDvrTime(HCNetSDK.NET_DVR_TIME time) {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d",
                time.dwYear, time.dwMonth, time.dwDay,
                time.dwHour, time.dwMinute, time.dwSecond
        );
    }

    // 辅助方法
    private int safeGetInteger(Map<String, Object> condition, String key, int defaultValue) {
        Object value = condition.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return defaultValue;
    }

    private byte safeGetByte(Map<String, Object> condition, String key, byte defaultValue) {
        Object value = condition.get(key);
        if (value instanceof Byte) {
            return (Byte) value;
        } else if (value instanceof Number) {
            return ((Number) value).byteValue();
        }
        return defaultValue;
    }

    private short safeGetShort(Map<String, Object> condition, String key, short defaultValue) {
        if (condition == null || !condition.containsKey(key)) {
            return defaultValue;
        }

        Object value = condition.get(key);
        if (value instanceof Short) {
            return (Short) value;
        } else if (value instanceof Number) {
            return ((Number) value).shortValue();
        } else if (value instanceof String) {
            try {
                return Short.parseShort((String) value);
            } catch (NumberFormatException e) {
                logger.warn("无法将字符串转换为Short: key={}, value={}", key, value);
                return defaultValue;
            }
        }

        logger.warn("不支持的Short类型: key={}, valueType={}", key, value != null ? value.getClass().getSimpleName() : "null");
        return defaultValue;
    }

    private byte[] safeGetByteArray(Map<String, Object> condition, String key) {
        Object value = condition.get(key);
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else if (value instanceof String) {
            return ((String) value).getBytes(StandardCharsets.UTF_8);
        }
        return new byte[0];
    }

    public void stopRemoteConfig(HCNetSDK hCNetSDK, int lHandler, Map<String, Object> res) {
        if (!hCNetSDK.NET_DVR_StopRemoteConfig(lHandler)) {
            int errCode = hCNetSDK.NET_DVR_GetLastError();
            logger.error("停止远程配置失败，错误码：{}", errCode);
            res.put("stopMessage", "停止远程配置失败，错误码：" + errCode);
        } else {
            logger.info("远程配置已正常关闭");
            res.put("stopMessage", "远程配置正常关闭");
        }
    }

    // 1. 首先定义时间转换方法
    private HCNetSDK.NET_DVR_TIME parseDateTime(String dateTimeStr) {
        HCNetSDK.NET_DVR_TIME time = new HCNetSDK.NET_DVR_TIME();
        time.read();

        try {
            // 解析前端传来的时间字符串 "2025-11-14 00:00:00"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, formatter);

            time.dwYear = localDateTime.getYear();
            time.dwMonth = localDateTime.getMonthValue();
            time.dwDay = localDateTime.getDayOfMonth();
            time.dwHour = localDateTime.getHour();
            time.dwMinute = localDateTime.getMinute();
            time.dwSecond = localDateTime.getSecond();
        } catch (Exception e) { // 解析失败，使用默认时间
            logger.warn("时间解析失败: {},", dateTimeStr, e);
        }
        return time;
    }

    // 添加时间转换方法
    private HCNetSDK.NET_DVR_TIME parseTimeToNetDvrTime(String timeStr) {
        HCNetSDK.NET_DVR_TIME netDvrTime = new HCNetSDK.NET_DVR_TIME();

        if (timeStr == null || timeStr.isEmpty()) {
            // 如果时间为空，设置为当前时间或默认时间
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -1); // 默认查询最近一天

            netDvrTime.dwYear = cal.get(Calendar.YEAR);
            netDvrTime.dwMonth = cal.get(Calendar.MONTH) + 1;
            netDvrTime.dwDay = cal.get(Calendar.DAY_OF_MONTH);
            netDvrTime.dwHour = 0;
            netDvrTime.dwMinute = 0;
            netDvrTime.dwSecond = 0;
        } else {
            try {
                // 解析字符串时间 "yyyy-MM-dd HH:mm:ss"
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(timeStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                netDvrTime.dwYear = cal.get(Calendar.YEAR);
                netDvrTime.dwMonth = cal.get(Calendar.MONTH) + 1;
                netDvrTime.dwDay = cal.get(Calendar.DAY_OF_MONTH);
                netDvrTime.dwHour = cal.get(Calendar.HOUR_OF_DAY);
                netDvrTime.dwMinute = cal.get(Calendar.MINUTE);
                netDvrTime.dwSecond = cal.get(Calendar.SECOND);

            } catch (Exception e) {
                logger.debug("时间格式转换错误，使用默认时间: " + timeStr);
                // 使用当前时间作为默认值
                Calendar cal = Calendar.getInstance();
                netDvrTime.dwYear = cal.get(Calendar.YEAR);
                netDvrTime.dwMonth = cal.get(Calendar.MONTH) + 1;
                netDvrTime.dwDay = cal.get(Calendar.DAY_OF_MONTH);
                netDvrTime.dwHour = cal.get(Calendar.HOUR_OF_DAY);
                netDvrTime.dwMinute = cal.get(Calendar.MINUTE);
                netDvrTime.dwSecond = cal.get(Calendar.SECOND);
            }
        }
        return netDvrTime;
    }

}
