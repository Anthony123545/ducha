package com.supervision.web.Hikvision.peopleDoor.connectSDK.Acs;


import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 功能：透传接口实现，透传ISAPI命令
 */
@Component
public final class TransIsapi {

    private static final Logger logger = LoggerFactory.getLogger(TransIsapi.class);

    public String get_isapi(HCNetSDK hCNetSDK, int lUserID, String url) {
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struXMLInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struXMLInput.read();
        HCNetSDK.BYTE_ARRAY stringRequest = new HCNetSDK.BYTE_ARRAY(1024);
        stringRequest.read();
        // 输入ISAPI协议命令
        System.arraycopy(url.getBytes(), 0, stringRequest.byValue, 0, url.length());
        stringRequest.write();
        struXMLInput.dwSize = struXMLInput.size();
        struXMLInput.lpRequestUrl = stringRequest.getPointer();
        struXMLInput.dwRequestUrlLen = url.length();
        struXMLInput.lpInBuffer = null;
        struXMLInput.dwInBufferSize = 0;
        struXMLInput.write();

        HCNetSDK.BYTE_ARRAY stringXMLOut = new HCNetSDK.BYTE_ARRAY(8 * 1024);
        stringXMLOut.read();
        HCNetSDK.BYTE_ARRAY struXMLStatus = new HCNetSDK.BYTE_ARRAY(1024);
        struXMLStatus.read();
        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struXMLOutput = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        struXMLOutput.read();
        struXMLOutput.dwSize = struXMLOutput.size();
        struXMLOutput.lpOutBuffer = stringXMLOut.getPointer();
        struXMLOutput.dwOutBufferSize = stringXMLOut.size();
        struXMLOutput.lpStatusBuffer = struXMLStatus.getPointer();
        struXMLOutput.dwStatusSize = struXMLStatus.size();
        struXMLOutput.write();
        if (!hCNetSDK.NET_DVR_STDXMLConfig(lUserID, struXMLInput, struXMLOutput)) {
            int iErr = hCNetSDK.NET_DVR_GetLastError();
            System.err.println("NET_DVR_STDXMLConfig失败，错误号" + iErr + "----URL:" + url);
            return null;
        } else {
            stringXMLOut.read();
            logger.info("输出文本大小：" + struXMLOutput.dwReturnedXMLSize);
            // 打印输出XML文本
            String strOutXML = new String(stringXMLOut.byValue).trim();
            logger.info(strOutXML);
            struXMLStatus.read();
            String strStatus = new String(struXMLStatus.byValue).trim();
            logger.info(strStatus);
            return strOutXML;
        }
    }

    public String put_isapi(HCNetSDK hCNetSDK, int lUserID, String url, String inputXml) {
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struXMLInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struXMLInput.read();
        HCNetSDK.BYTE_ARRAY stringRequest = new HCNetSDK.BYTE_ARRAY(1024);
        stringRequest.read();
        // 输入ISAPI协议命令
        System.arraycopy(url.getBytes(), 0, stringRequest.byValue, 0, url.length());
        stringRequest.write();
        struXMLInput.dwSize = struXMLInput.size();
        struXMLInput.lpRequestUrl = stringRequest.getPointer();
        struXMLInput.dwRequestUrlLen = url.length();
        HCNetSDK.BYTE_ARRAY ptrInBuffer = new HCNetSDK.BYTE_ARRAY(inputXml.length());
        ptrInBuffer.read();
        try {
            System.arraycopy(inputXml.getBytes("utf-8"), 0, ptrInBuffer.byValue, 0, inputXml.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ptrInBuffer.write();
        struXMLInput.lpInBuffer = ptrInBuffer.getPointer();
        struXMLInput.dwInBufferSize = inputXml.length();
        struXMLInput.write();
        // 输出缓冲区大小，可以依据事件输出报文大小进行调整
        HCNetSDK.BYTE_ARRAY stringXMLOut = new HCNetSDK.BYTE_ARRAY(2 * 1024 * 1024);
        stringXMLOut.read();
        HCNetSDK.BYTE_ARRAY struXMLStatus = new HCNetSDK.BYTE_ARRAY(1024);
        struXMLStatus.read();
        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struXMLOutput = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        struXMLOutput.read();
        struXMLOutput.dwSize = struXMLOutput.size();
        struXMLOutput.lpOutBuffer = stringXMLOut.getPointer();
        struXMLOutput.dwOutBufferSize = stringXMLOut.size();
        struXMLOutput.lpStatusBuffer = struXMLStatus.getPointer();
        struXMLOutput.dwStatusSize = struXMLStatus.size();
        struXMLOutput.write();
        if (!hCNetSDK.NET_DVR_STDXMLConfig(
                lUserID, struXMLInput, struXMLOutput)) {
            int iErr = hCNetSDK.NET_DVR_GetLastError();
            System.err.println("NET_DVR_STDXMLConfig失败，错误号" + iErr + "----URL:" + url);
            return null;
        } else {
            stringXMLOut.read();
            logger.info("输出文本大小：" + struXMLOutput.dwReturnedXMLSize);
            // 打印输出XML文本
            String strOutXML = new String(stringXMLOut.byValue).trim();
            struXMLStatus.read();
            String strStatus = new String(struXMLStatus.byValue).trim();
            return strOutXML;
        }
    }

}
