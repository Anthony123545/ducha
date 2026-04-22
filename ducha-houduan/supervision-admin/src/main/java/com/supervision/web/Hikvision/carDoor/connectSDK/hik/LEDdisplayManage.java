package com.supervision.web.Hikvision.carDoor.connectSDK.hik;

import com.supervision.web.Hikvision.carDoor.connectSDK.CommonMethod.ConfigFileUtil;
import com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * LED屏幕显示设置
 *
 * @author jiangxin
 * @create 2021-04-21-11:28
 */
@Component
public class LEDdisplayManage {

    private static final Logger logger = LoggerFactory.getLogger(LEDdisplayManage.class);

    @Autowired
    private ISAPI iSAPI;

    /**
     * 获取LED屏幕单场景显示参数(需要看设备是否支持此功能，例如守尉设备支持)
     * 命令：GET /ISAPI/Parking/channels/<channelID>/LEDConfigurations/multiScene/<SID>?format=json
     * channelID =通道号，一般默认1
     * SID场景号：1：passingVehicle（过车场景），2：noVehicle（无过车场景）
     */
    public Map<String, Object> getLEDdisplayMultiScene(HCNetSDK hCNetSDK, int lUserID) {

        Map<String, Object> res = new HashMap<>();
        //  获取LED屏幕过车场景显示参数命令：GET /ISAPI/Parking/channels/1/LEDConfigurations/multiScene/1?format=json    channelID：通道号  SID:场景编号
        String StringpassingVehicle =
                iSAPI.stdXMLConfig(hCNetSDK, lUserID, "GET /ISAPI/Parking/channels/1/LEDConfigurations/multiScene/1?format=json",
                        "");
        logger.info("LED过车场景显示配置参数报文：" + StringpassingVehicle);
        //获取LED屏幕空闲场景显示参数命令：GET /ISAPI/Parking/channels/1/LEDConfigurations/multiScene/2?format=json
        String StringnoVehicle =
                iSAPI.stdXMLConfig(hCNetSDK, lUserID, "GET /ISAPI/Parking/channels/1/LEDConfigurations/multiScene/2?format=json",
                        "");
        logger.info("LED空闲场景显示配置参数报文：" + StringnoVehicle);
        res.put("noVehicleScene", StringnoVehicle);
        return res;
    }

    /**
     * 设置LED屏幕单场景显示参数(需要看设备是否支持此功能，例如守尉设备支持)
     *
     * @param lUserID
     */
    public Map<String, Object> setLEDdisplayMultiScene(HCNetSDK hCNetSDK, int lUserID) {
        Map<String, Object> res = new HashMap<>();
        String setLEDdisplayMultiSceneUrl = "PUT /ISAPI/Parking/channels/1/LEDConfigurations/multiScene/2?format=json";
        //空闲场景输入报文
        String strInbuff = ConfigFileUtil.readFileContent("/conf/ITC/SingleSceneLEDConfigurations.json");
        logger.info("设置LED空闲场景输入报文:" + strInbuff);
        res.put("requestData", strInbuff);
        String responseString = iSAPI.stdXMLConfig(hCNetSDK, lUserID, setLEDdisplayMultiSceneUrl, strInbuff);
        logger.info("设置LED空闲场景返回报文：" + responseString);
        res.put("responseData", responseString);
        return  res;
    }

}
