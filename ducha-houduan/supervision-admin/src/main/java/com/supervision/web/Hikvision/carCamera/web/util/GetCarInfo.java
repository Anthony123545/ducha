package com.supervision.web.Hikvision.carCamera.web.util;


import com.sun.jna.Pointer;
import com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCarInfo {

    private static final Logger logger = LoggerFactory.getLogger(GetCarInfo.class);

    /**
     * 将 NET_DVR_PLATE_INFO 转换为 Map
     */
    public static Map<String, Object> convertPlateInfoToMap(HCNetSDK.NET_DVR_PLATE_INFO plateInfo) {
        Map<String, Object> map = new HashMap<>();
        if (plateInfo == null) return map;

        try {
            // 基本信息
            String sLicense = new String(plateInfo.sLicense,  "GBK").trim();
            map.put("license", sLicense); // 车牌号
            map.put("plateType", getPlateType(plateInfo.byPlateType)); // 车牌类型
            map.put("color", getPlateColor(plateInfo.byColor));       // 车牌颜色
            map.put("bright", plateInfo.byBright);                   // 车牌亮度
            map.put("licenseLen", plateInfo.byLicenseLen);           // 车牌字符个数
            map.put("entireBelieve", plateInfo.byEntireBelieve);     // 整个车牌的置信度
            map.put("region", plateInfo.byRegion);                   // 区域索引
            map.put("country", plateInfo.byCountry);                 // 国家索引
            map.put("byArea", plateInfo.byArea);                     // 省份/区域
            map.put("plateSize", plateInfo.byPlateSize);             // 车牌尺寸
            map.put("byAddInfoFlag", plateInfo.byAddInfoFlag);       // 是否有附加信息
            map.put("wCRIndex", plateInfo.wCRIndex);                 // 国家/地区索引
            map.put("plateCategoryInfo", new String(plateInfo.sPlateCategory, StandardCharsets.UTF_8).trim()); // 附加信息

            // 车牌位置
            HCNetSDK.NET_VCA_RECT rect = plateInfo.struPlateRect;
            if (rect != null) {
                Map<String, Object> rectMap = new HashMap<>();
                rectMap.put("fX", rect.fX);
                rectMap.put("fY", rect.fY);
                rectMap.put("fWidth", rect.fWidth);
                rectMap.put("fHeight", rect.fHeight);
                map.put("plateRect", rectMap);
            }

            // 字符置信度数组
            List<Integer> believeList = new ArrayList<>();
            for (int i = 0; i < plateInfo.byLicenseLen && i < plateInfo.byBelieve.length; i++) {
                believeList.add((int) plateInfo.byBelieve[i]);
            }
            map.put("characterBelieve", believeList);

            // XML 报警信息
            if (plateInfo.dwXmlLen > 0 && plateInfo.pXmlBuf != null) {
                byte[] xmlData = plateInfo.pXmlBuf.getByteArray(0, plateInfo.dwXmlLen);
                String xmlStr = new String(xmlData, StandardCharsets.UTF_8).trim();
                map.put("xmlAlarmInfo", xmlStr);
            }

            // XML 长度
            map.put("dwXmlLen", plateInfo.dwXmlLen);

        } catch (Exception e) {
            logger.error("转换 NET_DVR_PLATE_INFO 为 Map 失败", e);
        }

        return map;
    }

    public static void logPlateInfo(HCNetSDK.NET_DVR_PLATE_INFO plateInfo) {
        String sLicense = new String(plateInfo.sLicense).trim();// 车牌号码，注：中东车牌需求把小字也纳入车牌号码，小字和车牌号中间用空格分隔
        String plateType = getPlateType(plateInfo.byPlateType); // 车牌类型
        String color = getPlateColor(plateInfo.byColor);        // 车牌颜色
        byte bright = plateInfo.byBright;                       // 车牌亮度
        byte licenseLen = plateInfo.byLicenseLen;               // 车牌字符个数
        byte entireBelieve = plateInfo.byEntireBelieve;         // 整个车牌的置信度，取值范围：0~100
        byte region =
                plateInfo.byRegion;                       // 区域索引值：0- 保留，1- 欧洲(Europe Region)，2- 俄罗斯(Russian Region)，3- 欧洲&俄罗斯(EU&CIS) ，0xff- 所有
        byte country = plateInfo.byCountry;                     // 国家和地区索引值，取值定义详见COUNTRY_INDEX，不支持0xff(全部)
        byte byArea = plateInfo.byArea;                         // 区域（省份），各国家和地区内部区域枚举，阿联酋参照EMI_AREA
        byte plateSize = plateInfo.byPlateSize;                 // 车牌尺寸：0- 未知，1- long，2- short(中东车牌使用)
        byte byAddInfoFlag =
                plateInfo.byAddInfoFlag;           // 附加信息标识（即是否有NET_DVR_VEHICLE_ADDINFO结构体）：0- 无附加信息,，1- 有附加信息。
        short wCRIndex = plateInfo.wCRIndex;
        ;          //国家/地区索引，索引值参考_CR_ INDEX_
        byte[] plateCategoryInfo =
                plateInfo.sPlateCategory;         // 车牌附加信息, 即中东车牌中车牌号码旁边的小字信息，(目前只有中东地区支持)
        int dwXmlLen = plateInfo.dwXmlLen;              // XML报警信息长度
        Pointer pXmlBuf = plateInfo.pXmlBuf;                // XML报警信息指针
        HCNetSDK.NET_VCA_RECT rect = plateInfo.struPlateRect; // 车牌位置
        byte[] byBelieve =
                plateInfo.byBelieve;                   // 各个识别字符的置信度，如检测到车牌"浙A12345", 置信度为20,30,40,50,60,70，则表示"浙"字正确的可能性是20%，"A"字的正确的可能性是30%

        logger.info("车牌号: " + sLicense);
        logger.info("车牌类型: " + plateType);
        logger.info("车牌颜色: " + color);
        logger.info("车牌亮度: " + bright);
        logger.info("车牌字符数: " + licenseLen);
        logger.info("整体置信度: " + entireBelieve + "%");
        logger.info("区域索引(region): " + region);
        logger.info("国家(country): " + country);
        logger.info("省份(area): " + byArea);
        logger.info("车牌尺寸: " + (plateSize == 1 ? "long" : plateSize == 2 ? "short" : "未知"));
        String plateCategory = String.valueOf(plateCategoryInfo);
        if (!plateCategory.isEmpty()) {
            logger.info("车牌附加信息: " + plateCategory);
        }
        System.out.print("字符置信度: ");
        for (int i = 0; i < licenseLen && i < byBelieve.length; i++) {
            System.out.print(byBelieve[i] + "% ");
        }
        logger.info(String.format("车牌坐标: [%.2f, %.2f, %.2f, %.2f]",
                rect.fX, rect.fY, rect.fWidth, rect.fHeight));
        if (dwXmlLen > 0 && pXmlBuf != null) {
            byte[] xmlData = pXmlBuf.getByteArray(0, dwXmlLen);
            String xmlStr = new String(xmlData).trim();
            logger.info("XML报警信息: " + xmlStr);
        }
    }

    /**
     * 将 NET_DVR_VEHICLE_INFO 转换为 Map
     */
    public static Map<String, Object> convertVehicleInfoToMap(HCNetSDK.NET_DVR_VEHICLE_INFO vehicleInfo) {
        Map<String, Object> map = new HashMap<>();
        if (vehicleInfo == null) return map;

        try {
            map.put("dwIndex", vehicleInfo.dwIndex); // 车辆序号
            map.put("vehicleType", getVehicleType(vehicleInfo.byVehicleType)); // 车辆类型
            map.put("colorDepth", getColorDepth(vehicleInfo.byColorDepth));     // 车身颜色深浅
            map.put("color", getColor(vehicleInfo.byColor));                   // 车身颜色
            map.put("radarState", getRadarState(vehicleInfo.byRadarState));    // 雷达状态

            map.put("wSpeed", vehicleInfo.wSpeed);     // 车辆速度 km/h
            map.put("wLength", vehicleInfo.wLength);   // 车身长度 cm
            map.put("illegalType", getIllegalType(vehicleInfo.byIllegalType)); // 违法类型

            map.put("vehicleLogoRecog", vehicleInfo.byVehicleLogoRecog);      // 主品牌
            map.put("vehicleSubLogoRecog", vehicleInfo.byVehicleSubLogoRecog);// 子品牌
            map.put("vehicleModel", vehicleInfo.byVehicleModel);              // 年款
            map.put("wVehicleLogoRecog", vehicleInfo.wVehicleLogoRecog);      // 兼容主品牌字段
            map.put("isParking", vehicleInfo.byIsParking);                    // 是否停车
            map.put("dwParkingTime", vehicleInfo.dwParkingTime);              // 停车时间 s

            // 自定义信息
            if (vehicleInfo.byCustomInfo != null) {
                map.put("customInfo", Arrays.toString(vehicleInfo.byCustomInfo));
            }

        } catch (Exception e) {
            logger.error("转换 NET_DVR_VEHICLE_INFO 为 Map 失败", e);
        }

        return map;
    }

    public static void logVehicleInfo(HCNetSDK.NET_DVR_VEHICLE_INFO vehicleInfo) {
        int dwIndex = vehicleInfo.dwIndex; // 车辆序号
        String byVehicleType = getVehicleType(vehicleInfo.byVehicleType); // 车辆类型
        String byColorDepth = getColorDepth(vehicleInfo.byColorDepth); // 车身颜色深浅
        String byColor = getColor(vehicleInfo.byColor); // 车身颜色
        String byRadarState = getRadarState(vehicleInfo.byRadarState); // 雷达状态

        int wSpeed = vehicleInfo.wSpeed; //车辆速度，单位km/h
        int wLength = vehicleInfo.wLength; // 车身长度
        String byIllegalType = getIllegalType(vehicleInfo.byIllegalType); // 违法类型

        byte vehicleLogoRecog = vehicleInfo.byVehicleLogoRecog; // 车辆主品牌
        byte vehicleSubLogoRecog = vehicleInfo.byVehicleSubLogoRecog; // 车辆子品牌，根据不同的主类型，子品牌取值定义不同
        byte vehicleModel = vehicleInfo.byVehicleModel; // 车辆子品牌年款，根据不同的主类型，子品牌年款取值定义不同
        byte[] customInfo = vehicleInfo.byCustomInfo; // 自定义信息
        short wVehicleLogoRecog = vehicleInfo.wVehicleLogoRecog; // 车辆主品牌(该字段兼容byVehicleLogoRecog)
        byte isParking = vehicleInfo.byIsParking; // 是否停车 0-无效，1-停车，2-未停车
        int dwParkingTime = vehicleInfo.dwParkingTime; // 停车时间，单位：s

        logger.info("车辆序号: {}", dwIndex);
        logger.info("车辆类型: {}", byVehicleType);
        logger.info("车身颜色深浅: {}", byColorDepth);
        logger.info("车身颜色: {}", byColor);
        logger.info("雷达状态: {}", byRadarState);
        logger.info("车辆速度: {} km/h", wSpeed);
        logger.info("车身长度: {} cm", wLength);
        logger.info("违法类型: {}", byIllegalType);
        logger.info("车辆主品牌: {}", vehicleLogoRecog);
        logger.info("车辆子品牌: {}", vehicleSubLogoRecog);
        logger.info("车辆年款: {}", vehicleModel);
        logger.info("是否停车: {}", isParking);
        logger.info("停车时间: {} 秒", dwParkingTime);
        logger.info("自定义信息: {}", customInfo);
    }


    private static String getColor(byte color) {
        switch (Byte.toUnsignedInt(color)) {
            case 0:
                return "其他色";
            case 1:
                return "白色";
            case 2:
                return "银色";
            case 3:
                return "灰色";
            case 4:
                return "黑色";
            case 5:
                return "红色";
            case 6:
                return "深蓝";
            case 7:
                return "蓝色";
            case 8:
                return "黄色";
            case 9:
                return "绿色";
            case 10:
                return "棕色";
            case 11:
                return "粉色";
            case 12:
                return "紫色";
            case 13:
                return "深灰";
            case 14:
                return "青色";
            case 0xFF:
                return "未识别";
            default:
                return "未知颜色";
        }
    }

    private static String getColorDepth(byte colorDepth) {
        switch (Byte.toUnsignedInt(colorDepth)) {
            case 0:
                return "深色";
            case 1:
                return "浅色";
            default:
                return "未知颜色";
        }
    }

    private static String getRadarState(byte byRadarState) {
        switch (Byte.toUnsignedInt(byRadarState)) {
            case 0:
                return "雷达正常";
            case 1:
                return "雷达故障";
            case 2:
                return "雷达一直发送相同速度值";
            case 3:
                return "雷达送出数据为0";
            case 4:
                return "雷达送出数据过大或过小";
            default:
                return "未知状态";
        }
    }

    private static String getVehicleType(byte type) {
        switch (Byte.toUnsignedInt(type)) {
            case 1:
                return "小型车";
            case 2:
                return "大型车";
            case 3:
                return "行人触发";
            case 4:
                return "二轮车";
            case 5:
                return "三轮车";
            default:
                return "其他";
        }
    }

    private String getParkingStatus(byte status) {
        switch (Byte.toUnsignedInt(status)) {
            case 0:
                return "无效";
            case 1:
                return "停车";
            case 2:
                return "未停车";
            default:
                return "未知";
        }
    }

    private String getVehicleColor(byte color) {
        switch (Byte.toUnsignedInt(color)) {
            case 0:
                return "白色";
            case 1:
                return "灰色";
            case 2:
                return "黄色";
            case 3:
                return "粉色";
            case 4:
                return "红色";
            case 5:
                return "绿色";
            case 6:
                return "蓝色";
            case 7:
                return "棕色";
            case 8:
                return "黑色";
            case 9:
                return "银色";
            default:
                return "未知";
        }
    }

    private static String getIllegalType(byte type) {
        switch (Byte.toUnsignedInt(type)) {
            case 0:
                return "正常";
            case 1:
                return "低速";
            case 2:
                return "超速";
            case 3:
                return "逆行";
            case 4:
                return "闯红灯";
            case 5:
                return "压车道线";
            case 6:
                return "不按导向";
            case 7:
                return "路口滞留";
            case 8:
                return "机占非";
            case 9:
                return "违法变道";
            case 10:
                return "不按车道";
            case 11:
                return "违反禁令";
            case 12:
                return "路口停车";
            case 13:
                return "绿灯停车";
            case 14:
                return "未礼让行人";
            case 15:
                return "违章停车";
            case 16:
                return "违章掉头";
            case 17:
                return "占用应急车道";
            case 18:
                return "禁右";
            case 19:
                return "禁左";
            case 20:
                return "压黄线";
            case 21:
                return "未系安全带";
            case 22:
                return "行人闯红灯";
            case 23:
                return "加塞";
            case 24:
                return "违法使用远光灯";
            case 25:
                return "驾驶时拨打接听手持电话";
            case 26:
                return "左转不让直行";
            case 27:
                return "右转不让左转";
            case 28:
                return "掉头不让直行";
            case 29:
                return "大弯小转";
            case 30:
                return "闯绿灯";
            case 31:
                return "未带头盔";
            case 32:
                return "非机动车载人";
            case 33:
                return "非机动车占用机动车道";
            case 34:
                return "非机动车打伞棚";
            case 35:
                return "黑烟车";
            case 36:
                return "鸣笛";
            default:
                return "未知";
        }
    }

    /**
     * 获取车牌类型描述
     *
     * @param type 枚举值，对应 VCA_PLATE_TYPE
     * @return 对应车牌类型的字符串描述
     */
    private static String getPlateType(byte type) {
        switch (type) {
            case 0:
                return "标准民用车与军车车牌";
            case 1:
                return "02式民用车牌";
            case 2:
                return "武警车车牌";
            case 3:
                return "警车车牌";
            case 4:
                return "民用车双行尾牌";
            case 5:
                return "使馆车牌";
            case 6:
                return "农用车车牌";
            case 7:
                return "摩托车车牌";
            case 8:
                return "新能源汽车牌";
            case 9:
                return "领事馆车牌";
            case 10:
                return "应急车牌";
            case 0x20:
                return "运输车";
            case 0x21:
                return "商用车";
            case 0x22:
                return "私家车";
            case 0x23:
                return "教练车";
            case 0x24:
                return "使馆车";
            case 0x25:
                return "使馆车";
            case 0x26:
                return "军车";
            case 0x27:
                return "Protocol 车牌";
            case 0x28:
                return "政府车";
            case 0x29:
                return "出口车";
            case 0x2A:
                return "出租车";
            case 0x2B:
                return "测试车";
            case 0x2C:
                return "转运车";
            case 0x2D:
                return "货车";
            case 0x2E:
                return "公交车";
            case 0x2F:
                return "公共车辆";
            case 0x30:
                return "公共运输车";
            case 0x31:
                return "私人运输车";
            case (byte) 0xFF:
                return "未知车牌类型";
            default:
                return "未定义类型: " + type;
        }
    }

    private static String getPlateColor(byte color) {
        switch (color) {
            case 0:
                return "蓝色";
            case 1:
                return "黄色";
            case 2:
                return "白色";
            case 3:
                return "黑色";
            case 4:
                return "绿色";
            case 5:
                return "民航黑色";
            case (byte) 0xFF:
                return "其他";
            default:
                return "未知";
        }
    }

}
