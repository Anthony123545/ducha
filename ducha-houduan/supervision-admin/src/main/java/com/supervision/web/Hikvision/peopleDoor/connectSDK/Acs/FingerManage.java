package com.supervision.web.Hikvision.peopleDoor.connectSDK.Acs;


import com.supervision.web.Hikvision.peopleDoor.connectSDK.CommonMethod.ConfigFileUtil;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 指纹管理：指纹采集，指纹下发，指纹信息查询，指纹删除
 */
@Component
public final class FingerManage {

    private static final Logger logger = LoggerFactory.getLogger(FingerManage.class);

    @Autowired
    private TransIsapi transIsapi;

    /**
     * 采集指纹模块，采集指纹数据为二进制
     *
     * @param userID
     */
    public void fingerCapture(HCNetSDK hCNetSDK, int userID) {
        HCNetSDK.NET_DVR_CAPTURE_FINGERPRINT_COND strFingerCond = new HCNetSDK.NET_DVR_CAPTURE_FINGERPRINT_COND();
        strFingerCond.read();
        strFingerCond.dwSize = strFingerCond.size();
        strFingerCond.byFingerPrintPicType = 1;  //指纹读卡器
        strFingerCond.byFingerNo = 1;   //指纹编号
        strFingerCond.write();
        int lGetFingerHandle =
                hCNetSDK.NET_DVR_StartRemoteConfig(userID, HCNetSDK.NET_DVR_CAPTURE_FINGERPRINT_INFO,
                        strFingerCond.getPointer(), strFingerCond.dwSize, null, null);
        if (lGetFingerHandle == -1) {
            logger.info("建立采集指纹长连接失败，错误码为：" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            logger.info("建立采集指纹长连接成功！");
        }
        HCNetSDK.NET_DVR_CAPTURE_FINGERPRINT_CFG strFingerCfg = new HCNetSDK.NET_DVR_CAPTURE_FINGERPRINT_CFG();
        strFingerCfg.dwSize = strFingerCfg.size();
        strFingerCfg.write();
        while (true) {
            int dwFingerState =
                    hCNetSDK.NET_DVR_GetNextRemoteConfig(lGetFingerHandle, strFingerCfg.getPointer(),
                            strFingerCfg.size());

            if (dwFingerState == -1) {
                logger.info("NET_DVR_GetNextRemoteConfig采集指纹失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                break;
            } else if (dwFingerState == HCNetSDK.NET_SDK_GET_NEXT_STATUS_FAILED) {
                logger.info("采集指纹失败");
                break;
            } else if (dwFingerState == HCNetSDK.NET_SDK_GET_NEXT_STATUS_NEED_WAIT) {
                logger.info("正在采集指纹中,请等待...");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            } else if (dwFingerState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                //超时时间5秒内设备本地人脸采集失败就会返回失败,连接会断开
                logger.info("采集指纹异常, 网络异常导致连接断开 ");
                break;
            } else if (dwFingerState == HCNetSDK.NET_SDK_GET_NEXT_STATUS_SUCCESS) {
                strFingerCfg.read();
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                String newName = sf.format(new Date());
                String fileName = newName + "_capFinger.data";
                String filePath = "..\\pic\\" + fileName;
                BufferedOutputStream bos = null;
                FileOutputStream fos = null;
                File file = null;
                try {
                    File dir = new File(filePath);
                    if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                        dir.mkdirs();
                    }
                    file = new File(filePath);
                    fos = new FileOutputStream(file);
                    bos = new BufferedOutputStream(fos);
                    bos.write(strFingerCfg.byFingerData);
                    logger.info("采集指纹成功！");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                if ((strFingerCfg.dwFingerPrintPicSize > 0) && (strFingerCfg.pFingerPrintPicBuffer != null)) {
                    FileOutputStream fout;
                    try {
                        String filename = "..\\pic\\" + newName + "_FingerPrintPic.jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strFingerCfg.pFingerPrintPicBuffer.getByteBuffer(offset,
                                strFingerCfg.dwFingerPrintPicSize);
                        byte[] bytes = new byte[strFingerCfg.dwFingerPrintPicSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                        logger.info("采集指纹成功, 图片保存路径: " + filename);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            } else {
                logger.info("其他异常, dwState: " + dwFingerState);
                break;
            }
        }
        //采集成功之后断开连接、释放资源
        if (!hCNetSDK.NET_DVR_StopRemoteConfig(lGetFingerHandle)) {
            logger.info(
                    "NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            logger.info("NET_DVR_StopRemoteConfig接口成功");
        }
    }

    /**
     * 采用透传ISAPI协议方式采集指纹，获取的指纹信息为BASE64编码，
     */
    public void fingerCpaureByisapi(HCNetSDK hCNetSDK, int userID) {
        //采集指纹URL
        String fingerCapUrl = "POST /ISAPI/AccessControl/CaptureFingerPrint";
        //输入参数，XML或者JSON数据,
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("fingerNo", 1); //指纹ID
        String XmlInput = ConfigFileUtil.getReqBodyFromTemplate("/conf/acs/CaptureFingerInfo.xml", parameter);
        logger.info("采集指纹报文：" + XmlInput);
        String result = transIsapi.put_isapi(hCNetSDK, userID, fingerCapUrl, XmlInput);
        logger.info("采集指纹结果:" + result);
    }

    /**
     * 下发指纹数据
     *
     * @param userID
     * @param employeeNo
     * @param figerdata
     * @throws JSONException
     */
    public void setOneFinger(HCNetSDK hCNetSDK, int userID, String employeeNo, String figerdata)
            throws JSONException {
        HCNetSDK.BYTE_ARRAY ptrByteArray = new HCNetSDK.BYTE_ARRAY(1024);    //数组
        String strInBuffer = "POST /ISAPI/AccessControl/FingerPrint/SetUp?format=json";
        System.arraycopy(strInBuffer.getBytes(), 0, ptrByteArray.byValue, 0, strInBuffer.length());//字符串拷贝到数组中
        ptrByteArray.write();

        int lHandler = hCNetSDK.NET_DVR_StartRemoteConfig(userID, HCNetSDK.NET_DVR_JSON_CONFIG,
                ptrByteArray.getPointer(), strInBuffer.length(), null, null);
        if (lHandler < 0) {
            logger.info(
                    "SearchFaceInfo NET_DVR_StartRemoteConfig 失败,错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            logger.info("SearchFaceInfo NET_DVR_StartRemoteConfig成功!");
            //输入参数，XML或者JSON数据
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("employeeNo", employeeNo); // 人员工号
            parameter.put("fingerData", figerdata); // 指纹编码数据
            String strInbuff = ConfigFileUtil.getReqBodyFromTemplate("conf/acs/AddFingerPrintInfo.json", parameter);
            logger.info("查询的json报文:" + strInbuff);
            //把string传递到Byte数组中，后续用.getPointer()方法传入指针地址中。
            HCNetSDK.BYTE_ARRAY ptrInbuff = new HCNetSDK.BYTE_ARRAY(strInbuff.length());
            System.arraycopy(strInbuff.getBytes(), 0, ptrInbuff.byValue, 0, strInbuff.length());
            ptrInbuff.write();
            HCNetSDK.NET_DVR_STRING_POINTER ptrOutbuff = new HCNetSDK.NET_DVR_STRING_POINTER(3 * 1024);
            ptrOutbuff.write();
            IntByReference pInt = new IntByReference(0);
            while (true) {
                int dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(lHandler, ptrInbuff.getPointer(),
                        strInbuff.length(), ptrOutbuff.getPointer(), ptrOutbuff.size(), pInt);
                ptrOutbuff.read();
                logger.info(String.valueOf(dwState));
                if (dwState == -1) {
                    logger.info("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" +
                            hCNetSDK.NET_DVR_GetLastError());
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEED_WAIT) {
                    logger.info("配置等待");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    continue;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                    logger.info("下发指纹失败");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                    logger.info("下发指纹异常");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                    logger.info("下发指纹成功");
                    //解析JSON字符串
                    ptrOutbuff.read();
                    logger.info("返回的报文：" + new String(ptrOutbuff.byString));

                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                    logger.info("下发指纹完成");
                    break;
                }
            }
            if (!hCNetSDK.NET_DVR_StopRemoteConfig(lHandler)) {
                logger.info(
                        "NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
            } else {
                logger.info("NET_DVR_StopRemoteConfig接口成功");
                lHandler = -1;
            }
        }
    }

    /**
     * 查询指纹信息，基于人员工号查询
     *
     * @param userID
     * @param employeeNo 工号
     * @throws JSONException
     */
    public void SearchFingerInfo(HCNetSDK hCNetSDK, int userID, String employeeNo) throws JSONException {
        HCNetSDK.BYTE_ARRAY ptrByteArray = new HCNetSDK.BYTE_ARRAY(1024);    //数组
        String strInBuffer = "POST /ISAPI/AccessControl/FingerPrintUpload?format=json";
        System.arraycopy(strInBuffer.getBytes(), 0, ptrByteArray.byValue, 0, strInBuffer.length());//字符串拷贝到数组中
        ptrByteArray.write();

        int lHandler = hCNetSDK.NET_DVR_StartRemoteConfig(userID, HCNetSDK.NET_DVR_JSON_CONFIG,
                ptrByteArray.getPointer(), strInBuffer.length(), null, null);
        if (lHandler < 0) {
            logger.info(
                    "SearchFaceInfo NET_DVR_StartRemoteConfig 失败,错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            logger.info("SearchFaceInfo NET_DVR_StartRemoteConfig成功!");

            //输入参数，XML或者JSON数据
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("searchID", UUID.randomUUID()); // 查询ID
            parameter.put("employeeNo", employeeNo); // 人员工号ID
            String strInbuff = ConfigFileUtil.getReqBodyFromTemplate("conf/acs/SearchFingerPrintParam.json", parameter);
            logger.info("查询的json报文:" + strInbuff);
            //把string传递到Byte数组中，后续用.getPointer()方法传入指针地址中。
            HCNetSDK.BYTE_ARRAY ptrInbuff = new HCNetSDK.BYTE_ARRAY(strInbuff.length());
            System.arraycopy(strInbuff.getBytes(), 0, ptrInbuff.byValue, 0, strInbuff.length());
            ptrInbuff.write();
            HCNetSDK.NET_DVR_STRING_POINTER ptrOutbuff = new HCNetSDK.NET_DVR_STRING_POINTER(3 * 1024);
            ptrOutbuff.write();
            IntByReference pInt = new IntByReference(0);
            while (true) {
                int dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(lHandler, ptrInbuff.getPointer(),
                        strInbuff.length(), ptrOutbuff.getPointer(), ptrOutbuff.size(), pInt);
                ptrOutbuff.read();
                logger.info(String.valueOf(dwState));
                if (dwState == -1) {
                    logger.info("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" +
                            hCNetSDK.NET_DVR_GetLastError());
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEED_WAIT) {
                    logger.info("配置等待");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    continue;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                    logger.info("查询指纹失败");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                    logger.info("查询指纹异常");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                    logger.info("查询指纹成功");
                    //解析JSON字符串
                    ptrOutbuff.read();
                    logger.info("查询的报文：" + new String(ptrOutbuff.byString));

                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                    logger.info("获取指纹完成");
                    break;
                }
            }
            if (!hCNetSDK.NET_DVR_StopRemoteConfig(lHandler)) {
                logger.info(
                        "NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
            } else {
                logger.info("NET_DVR_StopRemoteConfig接口成功");
                lHandler = -1;
            }

        }
    }

    /**
     * 按工号和读卡器删除指纹，按工号是逐个删除，按读卡器是批量删除指定读卡器上所有的指纹数据
     *
     * @param userID
     * @param employeeNo
     */
    public void deleteFinger(HCNetSDK hCNetSDK, int userID, String employeeNo) {
        int iErr = 0;
        HCNetSDK.NET_DVR_FINGER_PRINT_INFO_CTRL_V50 m_struFingerDelInfoParam =
                new HCNetSDK.NET_DVR_FINGER_PRINT_INFO_CTRL_V50();
        m_struFingerDelInfoParam.dwSize = m_struFingerDelInfoParam.size();
        m_struFingerDelInfoParam.byMode = 0;// 删除方式，0-按卡号（人员ID）方式删除，1-按读卡器删除
        m_struFingerDelInfoParam.struProcessMode.setType(HCNetSDK.NET_DVR_FINGER_PRINT_BYCARD_V50.class);
        for (int i = 0; i < employeeNo.length(); i++) {
            m_struFingerDelInfoParam.struProcessMode.struByCard.byEmployeeNo[i] = employeeNo.getBytes()[i];
        }
        m_struFingerDelInfoParam.struProcessMode.struByCard.byEnableCardReader[0] = 1;//指纹的读卡器信息，按位表示
        m_struFingerDelInfoParam.struProcessMode.struByCard.byFingerPrintID[0] =
                1;//需要删除的指纹编号，按数组下标，值表示0-不删除，1-删除该指纹 ,指纹编号1删除


        Pointer lpInBuffer1 = m_struFingerDelInfoParam.getPointer();
        m_struFingerDelInfoParam.write();


        int lHandle = hCNetSDK.NET_DVR_StartRemoteConfig(userID, HCNetSDK.NET_DVR_DEL_FINGERPRINT, lpInBuffer1,
                m_struFingerDelInfoParam.size(), null, null);
        if (lHandle < 0) {
            iErr = hCNetSDK.NET_DVR_GetLastError();
            logger.info("NET_DVR_DEL_FINGERPRINT_CFG_V50 建立长连接失败，错误号：" + iErr);
            return;
        }
        while (true) {
            HCNetSDK.NET_DVR_FINGER_PRINT_INFO_CTRL_V50 v50 = new HCNetSDK.NET_DVR_FINGER_PRINT_INFO_CTRL_V50();
            v50.dwSize = v50.size();
            v50.write();
            int res = hCNetSDK.NET_DVR_GetNextRemoteConfig(lHandle, v50.getPointer(), v50.size());
            if (res == 1002) {
                hCNetSDK.NET_DVR_StopRemoteConfig(lHandle);
                logger.info("删除指纹成功！！！");
                break;
            } else if (res == 1003) {
                logger.info("接口失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
                hCNetSDK.NET_DVR_StopRemoteConfig(lHandle);
                break;
            }
        }
    }

}
