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
 * 功能模块：透传接口实现语音播放设置，功能：实现获取组合播报内容，设置组合语音播报设置，
 */
@Component
public class VoiceManage {

    private static final Logger logger = LoggerFactory.getLogger(VoiceManage.class);

    @Autowired
    private ISAPI iSAPI;

    /**
     * 平台下发语言播报，适用平台自定义下发文本信息，设备响应进行单独播报
     *
     * @param lUserID
     */
    public Map<String, Object> voiceBroadcastInfo(HCNetSDK hCNetSDK, int lUserID) {
        String voiceBroadcastInfoURL = "PUT /ISAPI/Parking/channels/1/voiceBroadcastInfo";
        Map<String, Object> res = new HashMap<>();
        try {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("TTSInfo", "[v1]测试语音播报"); //[V]中数字表示音量等级，取值范围1-10)
        String strInbuff = ConfigFileUtil.getReqBodyFromTemplate("/conf/ITC/voiceBroadcastInfo.xml", parameter);
        logger.info("语音播报输入报文:" + strInbuff);
        String responseString = iSAPI.stdXMLConfig(hCNetSDK, lUserID, voiceBroadcastInfoURL, strInbuff);
        logger.info("语音播报返回报文：" + responseString);

        res.put("status", "success");
        res.put("message", "语音播报成功");
        res.put("requestData", strInbuff);
        res.put("responseData", responseString);
        } catch (Exception e) {
            res.put("status", "error");
            res.put("message", "语音播报失败：" + e.getMessage());
        }
        return res;
    }

    /**
     * 获取组合语音播报参数
     *
     * @param lUserID
     */
    public Map<String, Object> getCombinateBroadcastInfo(HCNetSDK hCNetSDK, int lUserID) {
        Map<String, Object> res = new HashMap<>();
        String getCombinateBroadcastInfoUrl = "GET /ISAPI/Parking/channels/1/voiceBroadcastInfo/combinateBroadcast?format=json";

        try {
            String responseString = iSAPI.stdXMLConfig(hCNetSDK, lUserID, getCombinateBroadcastInfoUrl, "");
            logger.info("取组合语音播报参数返回报文：" + responseString);
            res.put("status", "success");
            res.put("message", "获取组合语音播报参数成功");
            res.put("responseData", responseString);
        }
        catch (Exception e) {
            res.put("status", "error");
            res.put("message", "取组合语音播报参数失败：" + e.getMessage());
        }
        return res;
    }

    /**
     * 设置组合语音播报参数
     *
     * @param lUserID
     */
    public Map<String, Object> setCombinateBroadcastInfo(HCNetSDK hCNetSDK, int lUserID) {
        Map<String, Object> res = new HashMap<>();
        String setCombinateBroadcastInfoUrl = "PUT /ISAPI/Parking/channels/1/voiceBroadcastInfo/combinateBroadcast?format=json";
        try {
            String strInbuff = ConfigFileUtil.readFileContent("/conf/ITC/CombinateBroadcast.json");
            logger.info("设置组合语音播报输入报文:" + strInbuff);
            String responseString = iSAPI.stdXMLConfig(hCNetSDK, lUserID, setCombinateBroadcastInfoUrl, strInbuff);
            logger.info("设置组合语音播报返回报文：" + responseString);

            res.put("status", "success");
            res.put("message", "设置组合语音播报参数成功");
            res.put("requestData", strInbuff);
            res.put("responseData", responseString);
        } catch (Exception e) {
            res.put("status", "error");
            res.put("message", "设置组合语音播报参数失败：" + e.getMessage());
        }
        return res;
    }

}
