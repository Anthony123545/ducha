package com.supervision.web.Hikvision.carDoor.connectSDK.hik;


import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 功能：透传接口实现，透传ISAPI命令
 */
@Component
public class ISAPI {

    private static final Logger logger = LoggerFactory.getLogger(ISAPI.class);

    /**
     * SDK透传ISAPI协议报文接口
     *
     * @param lUserID
     * @param url
     * @param inputXml 输入报文
     * @return
     */
    public String stdXMLConfig(HCNetSDK hCNetSDK, int lUserID, String url, String inputXml) {
        // 创建输入参数
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struXMLInput = createXMLInput(url, inputXml);

        // 创建输出缓冲区
        HCNetSDK.BYTE_ARRAY stringXMLOut = new HCNetSDK.BYTE_ARRAY(16 * 1024);
        stringXMLOut.read();
        HCNetSDK.BYTE_ARRAY struXMLStatus = new HCNetSDK.BYTE_ARRAY(16 * 1024);
        struXMLStatus.read();

        // 创建输出对象
        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struXMLOutput = createXMLOutput(stringXMLOut, struXMLStatus);

        // 透传 ISAPI 命令
        if (!hCNetSDK.NET_DVR_STDXMLConfig(lUserID, struXMLInput, struXMLOutput)) {
            int iErr = hCNetSDK.NET_DVR_GetLastError();
            logger.error("NET_DVR_STDXMLConfig 失败，错误号: {}，URL: {}", iErr, url);
            throw new RuntimeException("ISAPI 请求失败，错误码：" + iErr + "，URL: " + url);
        } else {
            // 读取输出并记录日志
            stringXMLOut.read();
            String strOutXML = new String(stringXMLOut.byValue).trim();

            // 记录输出 XML 大小
            logger.debug("输出文本大小：{}", struXMLOutput.dwReturnedXMLSize);

            // 读取状态信息并记录日志
            struXMLStatus.read();
            String strStatus = new String(struXMLStatus.byValue).trim();
            logger.debug("响应状态：{}", strStatus);

            return strOutXML;
        }
    }

    private HCNetSDK.NET_DVR_XML_CONFIG_INPUT createXMLInput(String url, String inputXml) {
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struXMLInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struXMLInput.read();
        struXMLInput.dwSize = struXMLInput.size();

        HCNetSDK.BYTE_ARRAY stringRequest = new HCNetSDK.BYTE_ARRAY(url.length());
        stringRequest.read();
        // 输入ISAPI协议命令   批量查询人脸库命令：GET /ISAPI/Intelligent/FDLib
        System.arraycopy(url.getBytes(), 0, stringRequest.byValue, 0, url.length());
        stringRequest.write();

        struXMLInput.lpRequestUrl = stringRequest.getPointer();
        struXMLInput.dwRequestUrlLen = url.length();

        // 处理输入 XML // 输入XML文本，GET命令不传输入文本
        int inputDataLen = inputXml.getBytes(StandardCharsets.UTF_8).length;
        if (inputDataLen > 0) {
            HCNetSDK.BYTE_ARRAY stringInBuffer = new HCNetSDK.BYTE_ARRAY(inputDataLen);
            stringInBuffer.read();
            byte[] bytes = inputXml.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(bytes, 0, stringInBuffer.byValue, 0, bytes.length);
            stringInBuffer.write();

            struXMLInput.lpInBuffer = stringInBuffer.getPointer();
            struXMLInput.dwInBufferSize = inputDataLen;
        } else {
            struXMLInput.lpInBuffer = null;
            struXMLInput.dwInBufferSize = 0;
        }

        struXMLInput.write();
        return struXMLInput;
    }

    private HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT createXMLOutput(HCNetSDK.BYTE_ARRAY stringXMLOut, HCNetSDK.BYTE_ARRAY struXMLStatus) {
        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struXMLOutput = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        struXMLOutput.read();
        struXMLOutput.dwSize = struXMLOutput.size();
        struXMLOutput.lpOutBuffer = stringXMLOut.getPointer();
        struXMLOutput.dwOutBufferSize = stringXMLOut.size();
        struXMLOutput.lpStatusBuffer = struXMLStatus.getPointer();
        struXMLOutput.dwStatusSize = struXMLStatus.size();
        struXMLOutput.write();
        return struXMLOutput;
    }




    // =========================================  新增的JSON方法（deleteVechileList使用）===========================================
    public String stdJSONConfig(HCNetSDK hCNetSDK, int lUserID, String url, String inputJson) {
        // 后端截图的URL是：/ISAPI/Traffic/channels/1/DelLicensePlateAuditData?format=json
        // 直接用这个URL，不需要额外拼接（避免重复加format=json）
        String jsonUrl = "PUT /ISAPI/Traffic/channels/1/DelLicensePlateAuditData?format=json";

        // 创建输入参数（已包含PUT方法）
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struXMLInput = createJSONInput(jsonUrl, inputJson);

        // 输出缓冲区（保持16K）
        int bufferSize = 16 * 1024;

//        HCNetSDK.BYTE_ARRAY stringJSONOut = new HCNetSDK.BYTE_ARRAY(bufferSize);
//        stringJSONOut.read();
//        HCNetSDK.BYTE_ARRAY struJSONStatus = new HCNetSDK.BYTE_ARRAY(bufferSize);
//        struJSONStatus.read();
        Memory outBuffer = new Memory(bufferSize);
        Memory statusBuffer = new Memory(bufferSize);
        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struJSONOutput = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        struJSONOutput.read();
        struJSONOutput.dwSize = struJSONOutput.size();
        struJSONOutput.lpOutBuffer = outBuffer; // 直接用Memory指针，避免局部对象回收
        struJSONOutput.dwOutBufferSize = bufferSize;
        struJSONOutput.lpStatusBuffer = statusBuffer;
        struJSONOutput.dwStatusSize = bufferSize;
        struJSONOutput.write();

//        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struJSONOutput = createXMLOutput(stringJSONOut, struJSONStatus);

        // 调用SDK接口
        if (!hCNetSDK.NET_DVR_STDXMLConfig(lUserID, struXMLInput, struJSONOutput)) {
            int iErr = hCNetSDK.NET_DVR_GetLastError();
            logger.error("请求失败，错误码: {}，URL: {}，请求方法: PUT", iErr, jsonUrl);
            throw new RuntimeException("ISAPI请求失败，错误码：" + iErr);
        } else {
            // 读取响应（指定UTF-8编码）
//            stringJSONOut.read();
            byte[] outBytes = outBuffer.getByteArray(0, bufferSize);
            String strOutJSON = new String(outBytes, StandardCharsets.UTF_8).trim();
//            String strOutJSON = new String(stringJSONOut.byValue, StandardCharsets.UTF_8).trim();
            logger.info("删除成功，响应: {}", strOutJSON);
            return strOutJSON;
        }
    }


    private HCNetSDK.NET_DVR_XML_CONFIG_INPUT createJSONInput(String url, String inputJson) {
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struXMLInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struXMLInput.read();
        struXMLInput.dwSize = struXMLInput.size(); // 关键：必须正确设置结构体大小

        // URL处理逻辑不变
        byte[] urlBytes = url.getBytes(StandardCharsets.US_ASCII);
        Memory urlMemory = new Memory(urlBytes.length + 1); // +1 补结束符
        urlMemory.write(0, urlBytes, 0, urlBytes.length);
        urlMemory.setByte(urlBytes.length, (byte) 0); // 字符串结束符
        struXMLInput.lpRequestUrl = urlMemory;
        struXMLInput.dwRequestUrlLen = urlBytes.length;

//        HCNetSDK.BYTE_ARRAY stringRequest = new HCNetSDK.BYTE_ARRAY(urlBytes.length);
//        stringRequest.read();
//        System.arraycopy(urlBytes, 0, stringRequest.byValue, 0, urlBytes.length);
//        stringRequest.write();
//        struXMLInput.lpRequestUrl = stringRequest.getPointer();
//        struXMLInput.dwRequestUrlLen = urlBytes.length; // URL长度必须和实际字节数一致


        byte[] jsonBytes = inputJson.getBytes(StandardCharsets.UTF_8);
        int actualJsonLen = jsonBytes.length; // 实际JSON字节长度

        if (actualJsonLen > 0) {
            Memory jsonMemory = new Memory(jsonBytes.length + 1);
            jsonMemory.write(0, jsonBytes, 0, jsonBytes.length);
            jsonMemory.setByte(jsonBytes.length, (byte) 0);
            struXMLInput.lpInBuffer = jsonMemory;
            struXMLInput.dwInBufferSize = jsonBytes.length; // 精确设置长度

            // 必须用实际长度创建缓冲区，不能用固定值
//            HCNetSDK.BYTE_ARRAY stringInBuffer = new HCNetSDK.BYTE_ARRAY(actualJsonLen);
//            stringInBuffer.read();
//            System.arraycopy(jsonBytes, 0, stringInBuffer.byValue, 0, actualJsonLen);
//            stringInBuffer.write();

//            struXMLInput.lpInBuffer = stringInBuffer.getPointer();
//            struXMLInput.dwInBufferSize = actualJsonLen; // 缓冲区大小=实际JSON长度（核心！）
        } else {
            struXMLInput.lpInBuffer = Pointer.NULL;
            struXMLInput.dwInBufferSize = 0;
        }

        // 补充：设置超时时间（避免设备响应慢导致的异常）
        struXMLInput.dwRecvTimeOut = 5000; // 5秒超时

        struXMLInput.write();
        return struXMLInput;
    }

}
