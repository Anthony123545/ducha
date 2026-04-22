package com.supervision.web.Hikvision.peopleDoor.web.util;

import com.alibaba.fastjson2.JSONObject;
import com.sun.jna.Pointer;
import com.supervision.web.Hikvision.carCamera.web.util.GetCarInfo;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 海康门禁报警信息转换工具类
 */
public class ACSAlarmJsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(ACSAlarmJsonUtil.class);

    private ACSAlarmJsonUtil() {
        // 工具类，防止实例化
    }

    /**
     * 转换车牌摄像头报警信息到JSON
     */
    public static JSONObject convertPlateAlarmInfoToJson(com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK.NET_ITS_PLATE_RESULT plateAlarmInfo) {
        JSONObject json = new JSONObject();
        if (plateAlarmInfo == null) {
            return json;
        }

        try {
            // ---------- 基本信息 ----------
            json.put("dwSize", plateAlarmInfo.dwSize);
            json.put("dwMatchNo", plateAlarmInfo.dwMatchNo);
            json.put("byGroupNum", plateAlarmInfo.byGroupNum);
            json.put("byPicNo", plateAlarmInfo.byPicNo);
            json.put("bySecondCam", plateAlarmInfo.bySecondCam);
            json.put("byFeaturePicNo", plateAlarmInfo.byFeaturePicNo);
            json.put("byDriveChan", plateAlarmInfo.byDriveChan);
            json.put("byVehicleType", plateAlarmInfo.byVehicleType);
            json.put("byDetSceneID", plateAlarmInfo.byDetSceneID);
            json.put("byVehicleAttribute", plateAlarmInfo.byVehicleAttribute);

            // ---------- 违章信息 ----------
            json.put("wIllegalType", plateAlarmInfo.wIllegalType);
            json.put("byIllegalSubType", bytesToHex(plateAlarmInfo.byIllegalSubType));
            json.put("byPostPicNo", plateAlarmInfo.byPostPicNo);
            json.put("byChanIndex", plateAlarmInfo.byChanIndex);
            json.put("wSpeedLimit", plateAlarmInfo.wSpeedLimit);
            json.put("byChanIndexEx", plateAlarmInfo.byChanIndexEx);
            json.put("dwCustomIllegalType", plateAlarmInfo.dwCustomIllegalType);
            json.put("byIllegalFromatType", plateAlarmInfo.byIllegalFromatType);
            json.put("pIllegalInfoBuf", plateAlarmInfo.pIllegalInfoBuf != null);

            // 车牌信息
            com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK.NET_DVR_PLATE_INFO plateInfo = plateAlarmInfo.struPlateInfo;
            if (plateAlarmInfo.struPlateInfo != null) {
                Map<String, Object> plateMap = GetCarInfo.convertPlateInfoToMap(plateAlarmInfo.struPlateInfo);
                json.put("plateInfo", plateMap);
            }

            // 车辆信息
            com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK.NET_DVR_VEHICLE_INFO vehicleInfo = plateAlarmInfo.struVehicleInfo;
            if (plateAlarmInfo.struVehicleInfo != null) {
                Map<String, Object> vehicleMap = GetCarInfo.convertVehicleInfoToMap(plateAlarmInfo.struVehicleInfo);
                json.put("vehicleInfo", vehicleMap);
            }

            // ---------- 设备与监测点 ----------
            json.put("byMonitoringSiteID", new String(plateAlarmInfo.byMonitoringSiteID, StandardCharsets.UTF_8).trim());
            json.put("byDeviceID", new String(plateAlarmInfo.byDeviceID, StandardCharsets.UTF_8).trim());

            // ---------- 方向与检测类型 ----------
            json.put("byDir", plateAlarmInfo.byDir);
            json.put("byDetectType", plateAlarmInfo.byDetectType);
            json.put("byRelaLaneDirectionType", plateAlarmInfo.byRelaLaneDirectionType);
            json.put("byCarDirectionType", plateAlarmInfo.byCarDirectionType);

            // ---------- 驾驶员状态 ----------
            json.put("byPilotSafebelt", plateAlarmInfo.byPilotSafebelt);
            json.put("byCopilotSafebelt", plateAlarmInfo.byCopilotSafebelt);
            json.put("byPilotSunVisor", plateAlarmInfo.byPilotSunVisor);
            json.put("byCopilotSunVisor", plateAlarmInfo.byCopilotSunVisor);
            json.put("byPilotCall", plateAlarmInfo.byPilotCall);

            // ---------- 道闸与报警类型 ----------
            json.put("byBarrierGateCtrlType", plateAlarmInfo.byBarrierGateCtrlType);
            json.put("byAlarmDataType", plateAlarmInfo.byAlarmDataType);

            // ---------- 黄标车 / 危险品车 ----------
            json.put("byYellowLabelCar", plateAlarmInfo.byYellowLabelCar);
            json.put("byDangerousVehicles", plateAlarmInfo.byDangerousVehicles);

            // ---------- 时间信息 ----------
            if (plateAlarmInfo.struSnapFirstPicTime != null) {
                com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK.NET_DVR_TIME_V30 t = plateAlarmInfo.struSnapFirstPicTime;
                String alarmTime = String.format("%04d-%02d-%02d %02d:%02d:%02d.%03d",
                        t.wYear & 0xFFFF,
                        t.byMonth & 0xFF,
                        t.byDay & 0xFF,
                        t.byHour & 0xFF,
                        t.byMinute & 0xFF,
                        t.bySecond & 0xFF,
                        t.wMilliSec & 0xFFFF  // 毫秒
                );
                json.put("alarmTime", alarmTime);  // 输出完整到毫秒
            }

            json.put("dwIllegalTime", plateAlarmInfo.dwIllegalTime);
            json.put("dwPicNum", plateAlarmInfo.dwPicNum);

            // ---------- 图片信息 ----------
            // 图片保存（提取到独立方法）
            List<String> imagePaths = savePlateAlarmImage(plateAlarmInfo, (String) json.get("alarmTime"));
            if (imagePaths != null) {
                json.put("imagePaths", imagePaths);
            }

            // 指针信息（如 pIllegalInfoBuf）暂时不解析，可根据需求解析
            json.put("pIllegalInfoBuf", plateAlarmInfo.pIllegalInfoBuf != null ? true : false);

        } catch (Exception e) {
            logger.error("转换车牌摄像头报警信息到JSON失败", e);
        }
        return json;
    }

    /**
     * 保存报警图片
     */
    /**
     * 保存车牌报警图片
     */
    private static List<String> savePlateAlarmImage(com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK.NET_ITS_PLATE_RESULT plateAlarmInfo, String alarmTimeStr) {

        List<String> savedPaths = new ArrayList<>();
        if (plateAlarmInfo == null || plateAlarmInfo.dwPicNum <= 0) return savedPaths;

        try {
            for (int i = 0; i < plateAlarmInfo.dwPicNum; i++) {
                com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK.NET_ITS_PICTURE_INFO picInfo = plateAlarmInfo.struPicInfo[i];
                if (picInfo.dwDataLen <= 0 || picInfo.pBuffer == null) continue;

                byte[] picData = picInfo.pBuffer.getByteArray(0, picInfo.dwDataLen);

                String fileName = generateImageFileName(alarmTimeStr, i, picInfo.byType); // 可以加序号和类型
                File outputFile = new File("event/carCamera", fileName);
                outputFile.getParentFile().mkdirs();

                // 如果是 URL 数据
                if (picInfo.byDataType == 1) { // URL 数据
                    String url = new String(picData, StandardCharsets.UTF_8);
                    Files.write(outputFile.toPath(), url.getBytes(StandardCharsets.UTF_8));
                    logger.info("报警图片 URL 已保存: {}", outputFile.getAbsolutePath());
                } else { // 二进制图片数据
                    Files.write(outputFile.toPath(), picData);
                    logger.info("报警图片已保存: {}", outputFile.getAbsolutePath());
                }

                savedPaths.add(outputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            logger.error("保存报警图片失败", e);
        }

        return savedPaths;
    }

    /**
     * 生成图片文件名
     */
    private static String generateImageFileName(String alarmTime, int picIndex, byte picType) {
        if (alarmTime == null) {
            // 如果没有时间信息，使用当前时间戳
            return System.currentTimeMillis() + "_" + picIndex + "_" + picType + "_Plate_Event.jpg";
        }

        // 清理时间中的非数字字符，格式：yyyyMMddHHmmss_ACS_Event.jpg
        String safeTime = alarmTime.replaceAll("[^0-9]", "");
        return safeTime + "_Plate_Event.jpg";
    }

    /**
     * 辅助方法：字节数组转十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        if (bytes == null) return "";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * 转换门禁主机报警信息到JSON
     */
    public static JSONObject convertACSAlarmInfoToJson(HCNetSDK.NET_DVR_ACS_ALARM_INFO acsAlarmInfo) {
        JSONObject json = new JSONObject();
        if (acsAlarmInfo == null) {
            return json;
        }

        try {
            // 基本报警信息
            // 门禁事件的详细信息解析，通过主次类型的可以判断当前的具体门禁类型，
            // 例如（主类型：0X5 次类型：0x4b 表示人脸认证通过，主类型：0X5 次类型：0x4c 表示人脸认证失败）
            json.put("majorType", acsAlarmInfo.dwMajor); // 报警主类型
            json.put("minorType", acsAlarmInfo.dwMinor); // 报警次类型
            json.put("inductiveEventType", acsAlarmInfo.wInductiveEventType); // 归纳事件类型
            json.put("picDataLen", acsAlarmInfo.dwPicDataLen); // 图片信息
            json.put("picTransType", acsAlarmInfo.byPicTransType); // 图片传输类型 // 0-二进制；1-url
            json.put("picTransTypeDesc", getPicTransTypeDescription(acsAlarmInfo.byPicTransType));
            json.put("iotChannelNo", acsAlarmInfo.dwIOTChannelNo); // IOT通道号
            json.put("timeType", acsAlarmInfo.byTimeType); // 时间类型 // 0-设备本地时间，1-UTC时间
            json.put("timeTypeDesc", getTimeTypeDescription(acsAlarmInfo.byTimeType));

            // 时间信息
            if (acsAlarmInfo.struTime != null) {
                HCNetSDK.NET_DVR_TIME t = acsAlarmInfo.struTime;
                String alarmTime = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                        t.dwYear & 0xFFFF,
                        t.dwMonth & 0xFF,
                        t.dwDay & 0xFF,
                        t.dwHour & 0xFF,
                        t.dwMinute & 0xFF,
                        t.dwSecond & 0xFF
                );
                json.put("alarmTime", alarmTime); // 时间信息
                json.put("isUTCTime", acsAlarmInfo.byTimeType == 1); // 是否为UTC时间
            }

            // 用户信息
            if (acsAlarmInfo.sNetUser != null) {
                int nullIndex = 0;
                while (nullIndex < acsAlarmInfo.sNetUser.length && acsAlarmInfo.sNetUser[nullIndex] != 0) {
                    nullIndex++;
                }
                String netUser = new String(Arrays.copyOf(acsAlarmInfo.sNetUser, nullIndex), StandardCharsets.UTF_8).trim();
                json.put("netUser", netUser); // 用户信息
            }

            // 远程主机地址
            if (acsAlarmInfo.struRemoteHostAddr != null) {
                String remoteIP = new String(acsAlarmInfo.struRemoteHostAddr.sIpV4, StandardCharsets.UTF_8).trim();
                json.put("remoteIP", remoteIP); // 远程主机地址
            }

            // 门禁事件详细信息
            if (acsAlarmInfo.struAcsEventInfo != null) {
                JSONObject eventInfoJson = convertAcsEventInfoToJson(acsAlarmInfo.struAcsEventInfo);
                json.put("acsEventInfo", eventInfoJson);
            }

            // 扩展信息标志
            boolean hasExtendInfo = acsAlarmInfo.byAcsEventInfoExtend == 1;
            boolean hasExtendV20Info = acsAlarmInfo.byAcsEventInfoExtendV20 == 1;
            json.put("hasExtendInfo", hasExtendInfo);
            json.put("hasExtendV20Info", hasExtendV20Info);
            // 解析扩展信息 V1
            if (hasExtendInfo && acsAlarmInfo.pAcsEventInfoExtend != null) {
                HCNetSDK.NET_DVR_ACS_EVENT_INFO_EXTEND extendInfo =
                        parsePointerData(acsAlarmInfo.pAcsEventInfoExtend,
                                new HCNetSDK.NET_DVR_ACS_EVENT_INFO_EXTEND());
                if (extendInfo != null) {
                    JSONObject extendJson = convertExtendInfoToJson(extendInfo);
                    json.put("extendInfo", extendJson);
                }
            }
            // 解析扩展信息 V20
            if (hasExtendV20Info && acsAlarmInfo.pAcsEventInfoExtendV20 != null) {
                HCNetSDK.NET_DVR_ACS_EVENT_INFO_EXTEND_V20 extendV20 =
                        parsePointerData(acsAlarmInfo.pAcsEventInfoExtendV20,
                                new HCNetSDK.NET_DVR_ACS_EVENT_INFO_EXTEND_V20());
                if (extendV20 != null) {
                    JSONObject extendV20Json = convertExtendV20ToJson(extendV20);
                    json.put("extendV20Info", extendV20Json);
                }
            }

            // 图片保存（提取到独立方法）
            String imagePath = saveAlarmImage(acsAlarmInfo, (String) json.get("alarmTime"));
            if (imagePath != null) {
                json.put("imagePath", imagePath);
            }
        } catch (Exception e) {
            logger.error("转换门禁主机报警信息到JSON失败", e);
        }
        return json;
    }

    /**
     * 保存报警图片
     */
    private static String saveAlarmImage(HCNetSDK.NET_DVR_ACS_ALARM_INFO acsAlarmInfo, String alarmTimeStr) {
        int picDataLen = acsAlarmInfo.dwPicDataLen;

        // 检查是否有图片数据
        if (picDataLen <= 0 || acsAlarmInfo.pPicData == null) {
            return null;
        }

        try {
            byte[] picData = acsAlarmInfo.pPicData.getByteArray(0, picDataLen);

            if (picData != null && picData.length > 0) {
                // 生成图片文件名
                String fileName = generateImageFileName(alarmTimeStr);

                // 构建保存路径：根目录/event/peopleDoor/
                File outputFile = new File("event/peopleDoor", fileName);

                // 确保目录存在
                outputFile.getParentFile().mkdirs();

                // 写入图片文件
                Files.write(outputFile.toPath(), picData);
                logger.info("报警图片已保存: {}", outputFile.getAbsolutePath());

                return outputFile.getAbsolutePath();
            }
        } catch (Exception e) {
            logger.error("保存报警图片失败", e);
        }

        return null;
    }

    /**
     * 生成图片文件名
     */
    private static String generateImageFileName(String alarmTime) {
        if (alarmTime == null) {
            // 如果没有时间信息，使用当前时间戳
            return System.currentTimeMillis() + "_ACS_Event.jpg";
        }

        // 清理时间中的非数字字符，格式：yyyyMMddHHmmss_ACS_Event.jpg
        String safeTime = alarmTime.replaceAll("[^0-9]", "");
        return safeTime + "_ACS_Event.jpg";
    }

    /**
     * NET_DVR_ACS_EVENT_INFO_EXTEND 转 JSON
     */
    public static JSONObject convertExtendInfoToJson(HCNetSDK.NET_DVR_ACS_EVENT_INFO_EXTEND extendInfo) {
        JSONObject json = new JSONObject();
        if (extendInfo == null) {
            return json;
        }

        try {
            // 基本事件信息
            json.put("frontSerialNo", extendInfo.dwFrontSerialNo);
            json.put("userType", extendInfo.byUserType);
            json.put("userTypeDesc", getUserTypeDescription(extendInfo.byUserType));
            json.put("currentVerifyMode", extendInfo.byCurrentVerifyMode);
            json.put("verifyModeDesc", getVerifyModeDescription(extendInfo.byCurrentVerifyMode));
            json.put("currentEvent", extendInfo.byCurrentEvent);
            json.put("isRealTimeEvent", extendInfo.byCurrentEvent == 1);
            json.put("purePwdVerifyEnable", extendInfo.byPurePwdVerifyEnable);
            json.put("supportPurePassword", extendInfo.byPurePwdVerifyEnable == 1);

            // 人员信息
            String employeeNo = new String(extendInfo.byEmployeeNo, StandardCharsets.ISO_8859_1).trim();
            json.put("employeeNo", employeeNo);
            json.put("attendanceStatus", extendInfo.byAttendanceStatus);
            json.put("attendanceStatusDesc", getAttendanceStatusDescription(extendInfo.byAttendanceStatus));
            json.put("statusValue", extendInfo.byStatusValue);

            // 设备信息
            String uuid = new String(extendInfo.byUUID, StandardCharsets.ISO_8859_1).trim();
            String deviceName = new String(extendInfo.byDeviceName, StandardCharsets.ISO_8859_1).trim();
            json.put("uuid", uuid);
            json.put("deviceName", deviceName);

        } catch (Exception e) {
            logger.error("转换扩展信息到JSON失败", e);
        }

        return json;
    }

    /**
     * NET_DVR_ACS_EVENT_INFO_EXTEND_V20 转 JSON
     */
    public static JSONObject convertExtendV20ToJson(HCNetSDK.NET_DVR_ACS_EVENT_INFO_EXTEND_V20 extendV20) {
        JSONObject json = new JSONObject();
        if (extendV20 == null) {
            return json;
        }

        try {
            // 远程核验信息
            json.put("remoteCheck", extendV20.byRemoteCheck);
            json.put("needRemoteCheck", extendV20.byRemoteCheck == 2);

            // 测温信息
            json.put("thermometryUnit", extendV20.byThermometryUnit);
            json.put("temperatureUnit", getTemperatureUnit(extendV20.byThermometryUnit));
            json.put("isAbnormalTemperature", extendV20.byIsAbnomalTemperature == 1);
            json.put("currentTemperature", extendV20.fCurrTemperature);

            // 人脸坐标
            if (extendV20.struRegionCoordinates != null) {
                JSONObject coordinates = new JSONObject();
                coordinates.put("x", extendV20.struRegionCoordinates.fX);
                coordinates.put("y", extendV20.struRegionCoordinates.fY);
                json.put("faceCoordinates", coordinates);
            }

            // 数据长度信息
            json.put("qrCodeInfoLen", extendV20.dwQRCodeInfoLen);
            json.put("visibleLightDataLen", extendV20.dwVisibleLightDataLen);
            json.put("thermalDataLen", extendV20.dwThermalDataLen);

            // 处理二维码信息
            if (extendV20.dwQRCodeInfoLen > 0 && extendV20.pQRCodeInfo != null) {
                try {
                    byte[] qrCodeData = extendV20.pQRCodeInfo.getByteArray(0, extendV20.dwQRCodeInfoLen);
                    String qrCodeInfo = new String(qrCodeData, StandardCharsets.ISO_8859_1).trim();
                    json.put("qrCodeInfo", qrCodeInfo);
                } catch (Exception e) {
                    logger.warn("解析二维码信息失败", e);
                    json.put("qrCodeInfo", "解析失败");
                }
            } else {
                json.put("qrCodeInfo", "");
            }

            // 图片数据信息（只记录长度，不包含实际二进制数据）
            json.put("hasVisibleLightImage", extendV20.dwVisibleLightDataLen > 0);
            json.put("hasThermalImage", extendV20.dwThermalDataLen > 0);

        } catch (Exception e) {
            logger.error("转换V20扩展信息到JSON失败", e);
        }

        return json;
    }

    public static JSONObject convertAcsEventInfoToJson(HCNetSDK.NET_DVR_ACS_EVENT_INFO eventInfo) {
        JSONObject json = new JSONObject();
        if (eventInfo == null) {
            return json;
        }

        try {
            // 基本事件信息
            json.put("size", eventInfo.dwSize);

            // 卡信息
            String cardNo = new String(eventInfo.byCardNo, StandardCharsets.ISO_8859_1).trim();
            json.put("cardNo", cardNo);
            json.put("cardType", eventInfo.byCardType);
            json.put("cardTypeDesc", getCardTypeDescription(eventInfo.byCardType));
            json.put("allowListNo", eventInfo.byAllowListNo);
            json.put("reportChannel", eventInfo.byReportChannel);
            json.put("cardReaderKind", eventInfo.byCardReaderKind);
            json.put("cardReaderNo", eventInfo.dwCardReaderNo);
            json.put("swipeCardType", eventInfo.bySwipeCardType);
            json.put("swipeCardTypeDesc", getSwipeCardTypeDescription(eventInfo.bySwipeCardType));

            // 门和设备信息
            json.put("doorNo", eventInfo.dwDoorNo);
            json.put("verifyNo", eventInfo.dwVerifyNo);
            json.put("alarmInNo", eventInfo.dwAlarmInNo);
            json.put("alarmOutNo", eventInfo.dwAlarmOutNo);
            json.put("caseSensorNo", eventInfo.dwCaseSensorNo);
            json.put("rs485No", eventInfo.dwRs485No);
            json.put("multiCardGroupNo", eventInfo.dwMultiCardGroupNo);
            json.put("accessChannel", eventInfo.wAccessChannel);
            json.put("deviceNo", eventInfo.byDeviceNo);
            json.put("distractControlNo", eventInfo.byDistractControlNo);

            // 人员信息
            json.put("employeeNo", eventInfo.dwEmployeeNo);
            json.put("localControllerID", eventInfo.wLocalControllerID);
            json.put("internetAccess", eventInfo.byInternetAccess);
            json.put("type", eventInfo.byType);

            // MAC地址
            String macAddr = bytesToMacAddress(eventInfo.byMACAddr);
            json.put("macAddr", macAddr);

            // 安全检测信息
            json.put("mask", eventInfo.byMask);
            json.put("maskDesc", getMaskDescription(eventInfo.byMask));
            json.put("helmet", eventInfo.byHelmet);
            json.put("helmetDesc", getHelmetDescription(eventInfo.byHelmet));

            // 序列号和控制器信息
            json.put("serialNo", eventInfo.dwSerialNo);
            json.put("channelControllerID", eventInfo.byChannelControllerID);
            json.put("channelControllerLampID", eventInfo.byChannelControllerLampID);
            json.put("channelControllerIRAdaptorID", eventInfo.byChannelControllerIRAdaptorID);
            json.put("channelControllerIREmitterID", eventInfo.byChannelControllerIREmitterID);

        } catch (Exception e) {
            logger.error("转换门禁事件信息到JSON失败", e);
        }

        return json;
    }

    /**
     * 转换门禁主机报警信息到JSON
     */
    public static JSONObject convertSearchAlarmEventInfoToJson(HCNetSDK.NET_DVR_ACS_EVENT_CFG acsAlarmInfo) {
        JSONObject json = new JSONObject();
        if (acsAlarmInfo == null) {
            return json;
        }

        try {
            // 基本报警信息
            // 门禁事件的详细信息解析，通过主次类型的可以判断当前的具体门禁类型，
            // 例如（主类型：0X5 次类型：0x4b 表示人脸认证通过，主类型：0X5 次类型：0x4c 表示人脸认证失败）
            json.put("majorType", acsAlarmInfo.dwMajor); // 报警主类型
            json.put("minorType", acsAlarmInfo.dwMinor); // 报警次类型
            json.put("inductiveEventType", acsAlarmInfo.wInductiveEventType); // 归纳事件类型
            json.put("picDataLen", acsAlarmInfo.dwPicDataLen); // 图片信息
//            json.put("picTransType", acsAlarmInfo.byPicTransType); // 图片传输类型 // 0-二进制；1-url
//            json.put("picTransTypeDesc", getPicTransTypeDescription(acsAlarmInfo.byPicTransType));
//            json.put("iotChannelNo", acsAlarmInfo.dwIOTChannelNo); // IOT通道号
            json.put("timeType", acsAlarmInfo.byTimeType); // 时间类型 // 0-设备本地时间，1-UTC时间
            json.put("timeTypeDesc", getTimeTypeDescription(acsAlarmInfo.byTimeType));

            // 时间信息
            if (acsAlarmInfo.struTime != null) {
                HCNetSDK.NET_DVR_TIME t = acsAlarmInfo.struTime;
                String alarmTime = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                        t.dwYear & 0xFFFF,
                        t.dwMonth & 0xFF,
                        t.dwDay & 0xFF,
                        t.dwHour & 0xFF,
                        t.dwMinute & 0xFF,
                        t.dwSecond & 0xFF
                );
                json.put("alarmTime", alarmTime); // 时间信息
                json.put("isUTCTime", acsAlarmInfo.byTimeType == 1); // 是否为UTC时间
            }

            // 用户信息
            if (acsAlarmInfo.sNetUser != null) {
                int nullIndex = 0;
                while (nullIndex < acsAlarmInfo.sNetUser.length && acsAlarmInfo.sNetUser[nullIndex] != 0) {
                    nullIndex++;
                }
                String netUser = new String(Arrays.copyOf(acsAlarmInfo.sNetUser, nullIndex), StandardCharsets.UTF_8).trim();
                json.put("netUser", netUser); // 用户信息
            }

            // 远程主机地址
            if (acsAlarmInfo.struRemoteHostAddr != null) {
                String remoteIP = new String(acsAlarmInfo.struRemoteHostAddr.sIpV4, StandardCharsets.UTF_8).trim();
                json.put("remoteIP", remoteIP); // 远程主机地址
            }

            // 门禁事件详细信息
            if (acsAlarmInfo.struAcsEventInfo != null) {
                JSONObject eventInfoJson = convertSearchEventInfoToJson(acsAlarmInfo.struAcsEventInfo);
                json.put("acsEventInfo", eventInfoJson);
            }

//            // 图片保存（提取到独立方法）
//            String imagePath = saveAlarmImage(acsAlarmInfo, (String) json.get("alarmTime"));
//            if (imagePath != null) {
//                json.put("imagePath", imagePath);
//            }
        } catch (Exception e) {
            logger.error("转换门禁主机报警信息到JSON失败", e);
        }
        return json;
    }


    public static JSONObject convertSearchEventInfoToJson(HCNetSDK.NET_DVR_ACS_EVENT_DETAIL eventInfo) {
        JSONObject json = new JSONObject();
        if (eventInfo == null) {
            return json;
        }

        try {
            // 基本事件信息
            json.put("size", eventInfo.dwSize);

            // 卡信息
            String cardNo = new String(eventInfo.byCardNo, StandardCharsets.ISO_8859_1).trim();
            json.put("cardNo", cardNo);
            json.put("cardType", eventInfo.byCardType);
            json.put("cardTypeDesc", getCardTypeDescription(eventInfo.byCardType));
            json.put("allowListNo", eventInfo.byAllowListNo);
            json.put("reportChannel", eventInfo.byReportChannel);
            json.put("cardReaderKind", eventInfo.byCardReaderKind);
            json.put("cardReaderNo", eventInfo.dwCardReaderNo);
            json.put("swipeCardType", eventInfo.bySwipeCardType);
            json.put("swipeCardTypeDesc", getSwipeCardTypeDescription(eventInfo.bySwipeCardType));

            // 门和设备信息
            json.put("doorNo", eventInfo.dwDoorNo);
            json.put("verifyNo", eventInfo.dwVerifyNo);
            json.put("alarmInNo", eventInfo.dwAlarmInNo);
            json.put("alarmOutNo", eventInfo.dwAlarmOutNo);
            json.put("caseSensorNo", eventInfo.dwCaseSensorNo);
            json.put("rs485No", eventInfo.dwRs485No);
            json.put("multiCardGroupNo", eventInfo.dwMultiCardGroupNo);
            json.put("accessChannel", eventInfo.wAccessChannel);
            json.put("deviceNo", eventInfo.byDeviceNo);
            json.put("distractControlNo", eventInfo.byDistractControlNo);

            // 人员信息
            json.put("employeeNo", eventInfo.dwEmployeeNo);
            json.put("localControllerID", eventInfo.wLocalControllerID);
            json.put("internetAccess", eventInfo.byInternetAccess);
            json.put("type", eventInfo.byType);

            // MAC地址
            String macAddr = bytesToMacAddress(eventInfo.byMACAddr);
            json.put("macAddr", macAddr);

            // 安全检测信息
            json.put("mask", eventInfo.byMask);
            json.put("maskDesc", getMaskDescription(eventInfo.byMask));
//            json.put("helmet", eventInfo.byHelmet);
//            json.put("helmetDesc", getHelmetDescription(eventInfo.byHelmet));

            // 序列号和控制器信息
            json.put("serialNo", eventInfo.dwSerialNo);
            json.put("channelControllerID", eventInfo.byChannelControllerID);
            json.put("channelControllerLampID", eventInfo.byChannelControllerLampID);
            json.put("channelControllerIRAdaptorID", eventInfo.byChannelControllerIRAdaptorID);
            json.put("channelControllerIREmitterID", eventInfo.byChannelControllerIREmitterID);

        } catch (Exception e) {
            logger.error("转换门禁事件信息到JSON失败", e);
        }

        return json;
    }

    /**
     * 获取用户类型描述
     */
    public static String getUserTypeDescription(byte userType) {
        switch (userType) {
            case 0: return "无效";
            case 1: return "普通人（主人）";
            case 2: return "来宾（访客）";
            case 3: return "禁止名单人";
            case 4: return "管理员";
            default: return "未知";
        }
    }

    /**
     * 获取验证方式描述
     */
    public static String getVerifyModeDescription(byte verifyMode) {
        switch (verifyMode) {
            case 0: return "无效";
            case 1: return "休眠";
            case 2: return "刷卡+密码";
            case 3: return "刷卡";
            case 4: return "刷卡或密码";
            case 5: return "指纹";
            case 6: return "指纹+密码";
            case 7: return "指纹或刷卡";
            case 8: return "指纹+刷卡";
            case 9: return "指纹+刷卡+密码";
            case 10: return "人脸或指纹或刷卡或密码";
            case 11: return "人脸+指纹";
            case 12: return "人脸+密码";
            case 13: return "人脸+刷卡";
            case 14: return "人脸";
            case 15: return "工号+密码";
            case 16: return "指纹或密码";
            case 17: return "工号+指纹";
            case 18: return "工号+指纹+密码";
            case 19: return "人脸+指纹+刷卡";
            case 20: return "人脸+密码+指纹";
            case 21: return "工号+人脸";
            case 22: return "人脸或人脸+刷卡";
            case 23: return "指纹或人脸";
            case 24: return "刷卡或人脸或密码";
            case 25: return "刷卡或人脸";
            case 26: return "刷卡或人脸或指纹";
            case 27: return "刷卡或指纹或密码";
            default: return "未知验证方式";
        }
    }

    /**
     * 获取考勤状态描述
     */
    public static String getAttendanceStatusDescription(byte status) {
        switch (status) {
            case 0: return "未定义";
            case 1: return "上班";
            case 2: return "下班";
            case 3: return "开始休息";
            case 4: return "结束休息";
            case 5: return "开始加班";
            case 6: return "结束加班";
            default: return "未知状态";
        }
    }

    /**
     * 获取温度单位描述
     */
    public static String getTemperatureUnit(byte unit) {
        switch (unit) {
            case 0: return "摄氏度";
            case 1: return "华氏度";
            case 2: return "开尔文";
            default: return "未知单位";
        }
    }

    /**
     * 获取卡类型描述
     */
    public static String getCardTypeDescription(byte cardType) {
        switch (cardType) {
            case 0: return "普通卡";
            case 1: return "残疾人卡";
            case 2: return "黑名单卡";
            case 3: return "巡更卡";
            case 4: return "胁迫卡";
            case 5: return "超级卡";
            case 6: return "来宾卡";
            case 7: return "解除卡";
            case 8: return "员工卡";
            case 9: return "应急卡";
            case 10: return "应急管理卡";
            default: return "未知卡类型";
        }
    }

    /**
     * 获取刷卡类型描述
     */
    public static String getSwipeCardTypeDescription(byte swipeCardType) {
        switch (swipeCardType) {
            case 0: return "无效";
            case 1: return "二维码";
            case 2: return "身份证";
            case 3: return "CPU卡";
            case 4: return "指纹";
            case 5: return "人脸";
            default: return "未知刷卡类型";
        }
    }

    /**
     * 获取口罩状态描述
     */
    public static String getMaskDescription(byte mask) {
        switch (mask) {
            case 0: return "保留";
            case 1: return "未知";
            case 2: return "不戴口罩";
            case 3: return "戴口罩";
            default: return "未知状态";
        }
    }

    /**
     * 获取安全帽状态描述
     */
    public static String getHelmetDescription(byte helmet) {
        switch (helmet) {
            case 0: return "保留";
            case 1: return "未知";
            case 2: return "不戴安全帽";
            case 3: return "戴安全帽";
            default: return "未知状态";
        }
    }

    /**
     * 字节数组转MAC地址字符串
     */
    public static String bytesToMacAddress(byte[] macBytes) {
        if (macBytes == null || macBytes.length == 0) {
            return "";
        }

        try {
            StringBuilder mac = new StringBuilder();
            for (int i = 0; i < macBytes.length; i++) {
                if (macBytes[i] == 0 && i > 0) {
                    break; // 遇到0停止，MAC地址可能不是全长度
                }
                mac.append(String.format("%02X", macBytes[i]));
                if (i < macBytes.length - 1 && macBytes[i + 1] != 0) {
                    mac.append(":");
                }
            }
            return mac.toString();
        } catch (Exception e) {
            logger.warn("转换MAC地址失败", e);
            return "";
        }
    }

    /**
     * 获取图片传输类型描述
     */
    public static String getPicTransTypeDescription(byte picTransType) {
        switch (picTransType) {
            case 0: return "二进制";
            case 1: return "URL";
            default: return "未知传输类型";
        }
    }

    /**
     * 获取时间类型描述
     */
    public static String getTimeTypeDescription(byte timeType) {
        switch (timeType) {
            case 0: return "设备本地时间";
            case 1: return "UTC时间";
            default: return "未知时间类型";
        }
    }

    /**
     * 解析指针数据到结构体
     */
    public static <T extends com.sun.jna.Structure> T parsePointerData(Pointer pointer, T structure) {
        if (pointer == null) {
            return null;
        }

        try {
            int size = structure.size();
            byte[] data = pointer.getByteArray(0, size);

            structure.write();
            Pointer structPointer = structure.getPointer();
            structPointer.write(0, data, 0, size);
            structure.read();

            return structure;
        } catch (Exception e) {
            logger.error("解析指针数据失败", e);
            return null;
        }
    }

}
