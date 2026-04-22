package com.supervision.web.Hikvision.peopleDoor.connectSDK.Acs;


import com.alibaba.fastjson.JSON;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能模块：人脸管理，下发、查询、删除人脸图片，注：下发人脸图片前，先下发人脸工号。
 */
@Component
public class FaceManage {

    private static final Logger logger = LoggerFactory.getLogger(FaceManage.class);

    // 定义所有必传字段
    private static final List<String> SEARCH_FACE_INFO_REQUIRED_FIELDS = Arrays.asList(
            "searchID",
            "searchResultPosition",
            "maxResults",
            "FDID",
            "name"
    );

    private static final List<String> ADD_FACE_INFO_REQUIRED_FIELDS = Arrays.asList(
            "faceLibType",
            "FDID",
            "name",
            "bornTime"
    );

    // 获取人脸信息(与获取人脸信息的结构类似)
    public Map<String, Object> getFaceInfo(HCNetSDK hCNetSDK, int userID, Map<String, Object> condition) {
        Map<String, Object> res = new HashMap<>();
        int lHandler = -1;

        try {
            // 启动远程配置
            String url = "POST /ISAPI/Intelligent/FDLib/FDSearch?format=json";
            lHandler = startRemoteConfig(hCNetSDK, HCNetSDK.NET_DVR_FACE_DATA_SEARCH, url, userID);
            if (lHandler < 0) {
                int errCode = hCNetSDK.NET_DVR_GetLastError();
                logger.error("NET_DVR_StartRemoteConfig 失败, 错误码: {}", errCode);
                res.put("status", false);
                res.put("message", "NET_DVR_StartRemoteConfig 失败, 错误码: " + errCode);
                return res;
            }

            // 构建输入参数
            String strInbuff = generateQueryCondition(condition);
            if(strInbuff == null  ){
                res.put("status", false);
                res.put("message", "缺少必要参数");
                return res;
            }
            logger.info("查询人脸参数: {}", strInbuff);

//            byte[] inputBytes = inputJson.getBytes(StandardCharsets.UTF_8);
//            HCNetSDK.BYTE_ARRAY inputBuffer = new HCNetSDK.BYTE_ARRAY(inputBytes.length);
//            System.arraycopy(inputBytes, 0, inputBuffer.byValue, 0, inputBytes.length);
//            inputBuffer.write();
//            // 接收输出结果
//            HCNetSDK.BYTE_ARRAY outputBuffer = new HCNetSDK.BYTE_ARRAY(10 * 1024);
//            IntByReference pInt = new IntByReference(0);

            // 把string传递到Byte数组中，后续用.getPointer()方法传入指针地址中。
            HCNetSDK.BYTE_ARRAY ptrInbuff = new HCNetSDK.BYTE_ARRAY(strInbuff.length());
            System.arraycopy(strInbuff.getBytes(), 0, ptrInbuff.byValue, 0, strInbuff.length());
            ptrInbuff.write();

            HCNetSDK.NET_DVR_JSON_DATA_CFG m_struJsonData = new HCNetSDK.NET_DVR_JSON_DATA_CFG();
            m_struJsonData.write();

            IntByReference pInt = new IntByReference(0);

            while (true) {
                int dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(
                        lHandler,
                        ptrInbuff.getPointer(),
                        strInbuff.length(),
                        m_struJsonData.getPointer(),
                        m_struJsonData.size(),
                        pInt
                );

                if (dwState == -1) {
                    int errCode = hCNetSDK.NET_DVR_GetLastError();
                    logger.error("接口调用失败，错误码：{}", errCode);
                    res.put("status", false);
                    res.put("message", "接口调用失败，错误码：" + errCode);
                    return res;
                }

                // 解析返回的JSON数据
                m_struJsonData.read();
                HCNetSDK.BYTE_ARRAY pJsonData = new HCNetSDK.BYTE_ARRAY(m_struJsonData.dwJsonDataSize);
                pJsonData.write();
                Pointer pPlateInfo = pJsonData.getPointer();
                pPlateInfo.write(0, m_struJsonData.lpJsonData.getByteArray(0, pJsonData.size()), 0, pJsonData.size());
                pJsonData.read();
                String strResult = new String(pJsonData.byValue).trim();
                System.out.println("strResult:" + strResult);
                JSONObject jsonResult = new JSONObject(strResult);
                int statusCode = jsonResult.optInt("statusCode", -1);
                String statusString = jsonResult.optString("statusString", "");

                if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEED_WAIT) {
                    logger.info("配置等待");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        logger.error("配置等待失败");
                        Thread.currentThread().interrupt();
                    }
                    continue;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                    logger.debug("查询人脸信息失败: {}", jsonResult);
                    res.put("status", false);
                    res.put("statusCode", jsonResult.optInt("statusCode", -1));
                    res.put("message", "查询人脸信息失败");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                    logger.debug("查询人脸信息异常: {}", jsonResult);
                    res.put("status", false);
                    res.put("message", "查询人脸信息异常");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                    if (statusCode != 1) {
                        logger.warn("查询人脸信息成功,但是有异常情况: {}", jsonResult);
                        res.put("message", "查询人脸信息成功,但是有异常情况");
                    } else {
                        logger.debug("查询人脸信息成功: {}", jsonResult);
                        res.put("message", "查询人脸信息成功");
                    }
                    res.put("status", true);
                    res.put("statusCode", statusCode);
                    res.put("statusString", statusString);

                    res.put("subStatusCode", jsonResult.getString("subStatusCode"));
                    res.put("responseStatusStrg", jsonResult.getString("responseStatusStrg"));
                    res.put("numOfMatches", jsonResult.getInt("numOfMatches"));
                    res.put("totalMatches", jsonResult.getInt("totalMatches"));
                    if (jsonResult.getInt("numOfMatches") > 0) {
                        JSONArray MatchList = jsonResult.getJSONArray("MatchList");
                        res.put("MatchList", MatchList);
                        JSONObject MatchList_1 = MatchList.optJSONObject(0);
                        String FPID = MatchList_1.getString("FPID");
                        FileOutputStream fout = null;
                        try {
                            // 构建文件路径
                            String filePath = System.getProperty("user.dir") + "/supervision-admin/peoplePicture/" + condition.get("deviceId") + "/pic/";
                            String fileName = "[" + FPID + "]_FacePic.jpg";
                            String fullPath = filePath + fileName;
                            // 创建目录
                            File directory = new File(filePath);
                            if (!directory.exists()) {
                                directory.mkdirs();
                            }
                            // 保存图片文件
                            fout = new FileOutputStream(fullPath);
                            long offset = 0;
                            ByteBuffer buffers = m_struJsonData.lpPicData.getByteBuffer(offset, m_struJsonData.dwPicDataSize);
                            byte[] bytes = new byte[m_struJsonData.dwPicDataSize];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                            // 将图片转换为Base64返回给前端
                            String base64Image = Base64.getEncoder().encodeToString(bytes);
                            String imageData = "data:image/jpeg;base64," + base64Image;
                            res.put("faceImage", imageData);
                        } catch (FileNotFoundException e) {
                            logger.error("文件不存在 {}", e.getMessage());
                        } catch (IOException e) {
                            logger.error("文件保存异常 {}", e.getMessage());
                        } finally {
                            if (fout != null) {
                                try {
                                    fout.close();
                                } catch (IOException e) {
                                    logger.error("关闭文件流异常 {}", e.getMessage());
                                }
                            }
                        }
                    }
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                    logger.info("查询人脸信息完成");
                    res.put("status", true);
                    res.put("message", "查询人脸信息完成");
                    break;
                } else {
                    logger.warn("未知状态码：" + dwState);
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("网络或IO异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "网络或IO异常");
        } catch (JSONException e) {
            logger.error("JSON解析异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "JSON解析异常");
        } catch (Exception e) {
            logger.error("查询人脸信息异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "查询人脸信息异常");
        } finally {
            if (lHandler >= 0) stopRemoteConfig(hCNetSDK, lHandler, res);
        }
        return res;
    }

    /**
     * 生成查询条件的 JSON 字符串
     *
     * @param condition 查询参数（包含 searchID、EmployeeNoList 等）
     * @return 查询条件的 JSON 字符串
     * @throws IOException 文件读写异常
     */
    private String generateQueryCondition(Map<String, Object> condition) throws IOException {
//        boolean res = validateRequiredFields(condition,SEARCH_FACE_INFO_REQUIRED_FIELDS);
//        if (!res) {
//            logger.error("缺少必填字段");
//            return null;
//        }
        Map<String, Object> faceInfoSearchCond = new HashMap<>();
        faceInfoSearchCond.put("searchID", condition.get("searchID"));
        faceInfoSearchCond.put("searchResultPosition", condition.getOrDefault("searchResultPosition", 0));
        faceInfoSearchCond.put("maxResults", condition.getOrDefault("maxResults", 30));
        String faceLibType = (String) condition.get("faceLibType");
        if (faceLibType == null || faceLibType.isEmpty()) {
            faceLibType = "blackFD";  // 可以改成 staticFD
        }
        faceInfoSearchCond.put("faceLibType", faceLibType);
        faceInfoSearchCond.put("FDID", condition.getOrDefault("FDID", "1"));
        faceInfoSearchCond.put("FPID", condition.getOrDefault("FPID", "1"));
        String jsonTemplate = new JSONObject(faceInfoSearchCond).toString();
        return jsonTemplate;
    }

    private int startRemoteConfig(HCNetSDK hcNetSDK, int dwCommand, String url, int userID) {
        HCNetSDK.BYTE_ARRAY ptrUrl = new HCNetSDK.BYTE_ARRAY(1024);
        System.arraycopy(url.getBytes(), 0, ptrUrl.byValue, 0, url.length());
        ptrUrl.write();
        return hcNetSDK.NET_DVR_StartRemoteConfig(
                userID,
                dwCommand,
                ptrUrl.getPointer(),
                url.length(),
                null,
                null
        );
    }

    /**
     * 停止远程配置（公共方法）
     * 关闭长连接配置接口所创建的句柄，释放资源。
     */
    private void stopRemoteConfig(HCNetSDK hcNetSDK, int lHandler, Map<String, Object> res) {
        if (lHandler < 0) { // 句柄无效
            logger.info("远程配置句柄无效！");
            return;
        }
        if (!hcNetSDK.NET_DVR_StopRemoteConfig(lHandler)) {
            int errCode = hcNetSDK.NET_DVR_GetLastError();
            logger.error("停止远程配置失败，错误码：{}", errCode);
            res.put("stopMessage", "停止远程配置失败，错误码：" + errCode);
        } else {
            logger.info("远程配置已正常关闭");
            res.put("stopMessage", "远程配置正常关闭");
        }
    }

    /**
     * 新增人脸
     *
     * @param userID    登录句柄
     * @param condition 人脸信息对象（包含所有字段，参考 SDK JSON）
     * @throws JSONException
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
    public Map<String, Object> addFaceInfo(HCNetSDK hCNetSDK, int userID, Map<String, Object> condition) throws JSONException {
        Map<String, Object> res = new HashMap<>();
        int lHandler = -1;

        try {
            // 1. 启动远程配置
            String strInBuffer = "PUT /ISAPI/Intelligent/FDLib/FDSetUp?format=json";
            int inBufferLength = strInBuffer.getBytes().length;
            HCNetSDK.BYTE_ARRAY ptrInBuffer = new HCNetSDK.BYTE_ARRAY(inBufferLength + 1);
            System.arraycopy(strInBuffer.getBytes(), 0, ptrInBuffer.byValue, 0, inBufferLength);
            ptrInBuffer.write();

            lHandler = hCNetSDK.NET_DVR_StartRemoteConfig(
                    userID,
                    HCNetSDK.NET_DVR_FACE_DATA_RECORD,
                    ptrInBuffer.getPointer(),
                    inBufferLength,
                    null,
                    null
            );
//            if (lHandler < 0) {
//                int errCode = hCNetSDK.NET_DVR_GetLastError();
//                logger.error("NET_DVR_StartRemoteConfig 失败, 错误码: {}", errCode);
//                res.put("status", false);
//                res.put("message", "NET_DVR_StartRemoteConfig 失败, 错误码: " + errCode);
//                return res;
//            }

            // 2. 构建输入参数
            String strJsonData = generateAddCondition(condition);
            logger.info("新增人脸参数: {}", strJsonData);

            int jsonDataLength = strJsonData.getBytes().length;
            // 动态分配足够大小的缓冲区
            HCNetSDK.BYTE_ARRAY ptrJsonData = new HCNetSDK.BYTE_ARRAY(jsonDataLength + 1);
            System.arraycopy(strJsonData.getBytes(), 0, ptrJsonData.byValue, 0, jsonDataLength);
            ptrJsonData.write();

            HCNetSDK.NET_DVR_JSON_DATA_CFG struAddFaceDataCfg = new HCNetSDK.NET_DVR_JSON_DATA_CFG();
            struAddFaceDataCfg.dwSize = struAddFaceDataCfg.size();
            struAddFaceDataCfg.lpJsonData = ptrJsonData.getPointer();  // 指向新的缓冲区
            struAddFaceDataCfg.dwJsonDataSize = jsonDataLength;

            // 3. 处理base64图片数据（关键修改部分）
            if (condition.get("modelData") != null && !condition.get("modelData").toString().isEmpty()) {
                try {
                    String base64Data = condition.get("modelData").toString();
                    // 移除base64前缀
                    if (base64Data.contains(",")) {
                        base64Data = base64Data.split(",")[1];
                    }
                    // 解码base64
                    byte[] picData = Base64.getDecoder().decode(base64Data);
                    int picDataLength = picData.length;
                    logger.info("图片数据大小: {} 字节", picDataLength);
                    // 创建图片数据缓冲区
                    HCNetSDK.BYTE_ARRAY ptrPicData = new HCNetSDK.BYTE_ARRAY(picDataLength);
                    System.arraycopy(picData, 0, ptrPicData.byValue, 0, picDataLength);
                    ptrPicData.write();
                    // 设置图片参数
                    struAddFaceDataCfg.dwPicDataSize = picDataLength;
                    struAddFaceDataCfg.lpPicData = ptrPicData.getPointer();
                } catch (Exception e) {
                    logger.error("图片数据处理失败: {}", e.getMessage(), e);
                    res.put("status", false);
                    res.put("message", "图片数据处理失败: " + e.getMessage());
                    return res;
                }
            } else {
                logger.warn("未提供图片数据");
                struAddFaceDataCfg.dwPicDataSize = 0;
                struAddFaceDataCfg.lpPicData = null;
            }
            struAddFaceDataCfg.write();

            // 3. 接收输出结果
            HCNetSDK.BYTE_ARRAY ptrOutuff = new HCNetSDK.BYTE_ARRAY(1024);
            IntByReference pInt = new IntByReference(0);

            while (true) {
                int dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(
                        lHandler,
                        struAddFaceDataCfg.getPointer(),
                        struAddFaceDataCfg.dwSize,
                        ptrOutuff.getPointer(),
                        1024,
                        pInt
                );
                if (dwState == -1) {
                    int errCode = hCNetSDK.NET_DVR_GetLastError();
                    logger.error("接口调用失败，错误码：{}", errCode);
                    res.put("status", false);
                    res.put("message", "接口调用失败，错误码：" + errCode);
                    return res;
                }

                // 读取返回的JSON并解析
                ptrOutuff.read();
                String resultJson = new String(ptrOutuff.byValue, StandardCharsets.UTF_8).trim();
                if (resultJson.isEmpty()) continue;
                logger.debug("dwState: {}", dwState);
                logger.debug("resultJson: {}", resultJson);

                // 解析JSON结果
                JSONObject jsonResult = new JSONObject(resultJson);
                int statusCode = jsonResult.optInt("statusCode", -1);
                String statusString = jsonResult.optString("statusString", "");

                if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEED_WAIT) {
                    logger.info("配置等待");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        logger.error("配置等待失败");
                        Thread.currentThread().interrupt();
                    }
                    continue;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                    logger.debug("添加人脸信息失败: {}", jsonResult);
                    res.put("status", false);
                    res.put("statusCode", jsonResult.optInt("statusCode", -1));
                    res.put("message", "添加人脸信息失败");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                    logger.debug("添加人脸信息异常: {}", jsonResult);
                    res.put("status", false);
                    res.put("message", "添加人脸信息异常");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                    if (statusCode != 1) {
                        logger.warn("添加人脸信息成功,但是有异常情况: {}", jsonResult);
                        res.put("message", "添加人脸信息成功,但是有异常情况:");
                    } else {
                        logger.debug("添加人脸信息成功: {}", jsonResult);
                        res.put("message", "添加人脸信息成功");
                    }
                    res.put("status", true);
                    if (jsonResult.has("requestURL")) {
                        res.put("requestURL", jsonResult.get("requestURL"));
                    }
                    res.put("statusCode", jsonResult.get("statusCode"));
                    res.put("statusString", jsonResult.get("statusString"));
                    res.put("subStatusCode", jsonResult.get("subStatusCode"));
                    if (jsonResult.has("errorCode")) {
                        res.put("errorCode", jsonResult.get("errorCode"));
                    }
                    if (jsonResult.has("errorMsg")) {
                        res.put("errorMsg", jsonResult.get("errorMsg"));
                    }
                    if (jsonResult.has("FPID")) {
                        res.put("FPID", jsonResult.get("FPID"));
                    }
                    if (jsonResult.has("rowKey")) {
                        res.put("rowKey", jsonResult.get("rowKey"));
                    }
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                    logger.info("添加人脸信息完成");
                    res.put("status", true);
                    res.put("message", "添加人脸信息完成");
                    break;
                } else {
                    logger.warn("未知状态码：" + dwState);
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("网络或IO异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "网络或IO异常");
        } catch (JSONException e) {
            logger.error("JSON解析异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "JSON解析异常");
        } catch (Exception e) {
            logger.error("添加人脸信息异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "添加人脸信息异常");
        } finally {
            if (lHandler >= 0) stopRemoteConfig(hCNetSDK, lHandler, res);
        }
        return res;
    }

    /**
     * 生成增加人脸的 JSON 字符串
     *
     * @param condition 查询参数（包含 searchID、EmployeeNoList 等）
     * @return 查询条件的 JSON 字符串
     * @throws IOException 文件读写异常
     */
    private String generateAddCondition(Map<String, Object> condition) throws IOException {
        Map<String, Object> userInfoSearchCond = new HashMap<>();
        userInfoSearchCond.put("faceLibType", condition.get("faceLibType"));
        userInfoSearchCond.put("faceURL", condition.get("faceURL"));
        userInfoSearchCond.put("FDID", condition.getOrDefault("FDID",1));
//        userInfoSearchCond.put("FPID", condition.getOrDefault("FPID", null));
        userInfoSearchCond.put("name", condition.get("name"));
        userInfoSearchCond.put("gender", condition.get("gender"));
        userInfoSearchCond.put("bornTime", condition.get("bornTime"));
//        userInfoSearchCond.put("city", condition.get("city"));
//        userInfoSearchCond.put("certificateType", condition.getOrDefault("certificateType", null));
//        userInfoSearchCond.put("certificateNumber", condition.getOrDefault("certificateNumber", null));
//        userInfoSearchCond.put("caseInfo", condition.getOrDefault("caseInfo", null));
//        userInfoSearchCond.put("tag", condition.getOrDefault("tag", null));
//        userInfoSearchCond.put("address", condition.getOrDefault("address", null));
//        userInfoSearchCond.put("customInfo", condition.getOrDefault("customInfo", null));
        userInfoSearchCond.put("modelData", condition.getOrDefault("modelData", null));
//        userInfoSearchCond.put("transfer", condition.getOrDefault("transfer", null));
//        userInfoSearchCond.put("operateType", condition.getOrDefault("operateType", null));
//        userInfoSearchCond.put("terminalNoList", condition.getOrDefault("terminalNoList", null));
//        userInfoSearchCond.put("PicFeaturePoints", condition.getOrDefault("PicFeaturePoints", null));
//        userInfoSearchCond.put("faceType", condition.getOrDefault("faceType", null));
//        userInfoSearchCond.put("saveFacePic", condition.getOrDefault("saveFacePic", null));
//        userInfoSearchCond.put("leaderPermission", condition.getOrDefault("leaderPermission", null));
        String jsonTemplate = new JSONObject(userInfoSearchCond).toString();
        return jsonTemplate;
    }

    /**
     * 修改人脸信息
     *
     * @param userID    登录句柄
     * @param condition 员工ID 人脸信息对象（包含所有字段，参考 SDK JSON）
     */
    public Map<String, Object> updateFaceInfo(HCNetSDK hCNetSDK, int userID, Map<String, Object> condition) {
        Map<String, Object> res = new HashMap<>();
        int lHandler = -1;

        try {
            String strInBuffer = "PUT /ISAPI/Intelligent/FDLib/FDSearch?format=json&FDID=1&FPID=&faceLibType=blackFD";
            int inBufferLength = strInBuffer.getBytes().length;
            HCNetSDK.BYTE_ARRAY ptrInBuffer = new HCNetSDK.BYTE_ARRAY(inBufferLength + 1);
            System.arraycopy(strInBuffer.getBytes(), 0, ptrInBuffer.byValue, 0, inBufferLength);
            ptrInBuffer.write();

            lHandler = hCNetSDK.NET_DVR_StartRemoteConfig(
                    userID,
                    HCNetSDK.NET_DVR_FACE_DATA_RECORD,
                    ptrInBuffer.getPointer(),
                    inBufferLength,
                    null,
                    null
            );
            if (lHandler < 0) {
                int errCode = hCNetSDK.NET_DVR_GetLastError();
                logger.error("NET_DVR_StartRemoteConfig 失败, 错误码: {}", errCode);
                res.put("status", false);
                res.put("message", "NET_DVR_StartRemoteConfig 失败, 错误码: " + errCode);
                return res;
            }

            // 2. 构建输入参数
            String strJsonData = generateUpdateCondition(condition);
            logger.info("查询人脸参数: {}", strJsonData);

            int jsonDataLength = strJsonData.getBytes().length;
            // 动态分配足够大小的缓冲区
            HCNetSDK.BYTE_ARRAY ptrJsonData = new HCNetSDK.BYTE_ARRAY(jsonDataLength + 1);
            System.arraycopy(strJsonData.getBytes(), 0, ptrJsonData.byValue, 0, jsonDataLength);
            ptrJsonData.write();

            HCNetSDK.NET_DVR_JSON_DATA_CFG struAddFaceDataCfg = new HCNetSDK.NET_DVR_JSON_DATA_CFG();
            struAddFaceDataCfg.dwSize = struAddFaceDataCfg.size();
            struAddFaceDataCfg.lpJsonData = ptrJsonData.getPointer();  // 指向新的缓冲区
            struAddFaceDataCfg.dwJsonDataSize = jsonDataLength;

            // 3. 处理base64图片数据（关键修改部分）
            if (condition.get("modelData") != null && !condition.get("modelData").toString().isEmpty()) {
                try {
                    String base64Data = condition.get("modelData").toString();
                    // 移除base64前缀
                    if (base64Data.contains(",")) {
                        base64Data = base64Data.split(",")[1];
                    }
                    // 解码base64
                    byte[] picData = Base64.getDecoder().decode(base64Data);
                    int picDataLength = picData.length;
                    logger.info("图片数据大小: {} 字节", picDataLength);
                    // 创建图片数据缓冲区
                    HCNetSDK.BYTE_ARRAY ptrPicData = new HCNetSDK.BYTE_ARRAY(picDataLength);
                    System.arraycopy(picData, 0, ptrPicData.byValue, 0, picDataLength);
                    ptrPicData.write();
                    // 设置图片参数
                    struAddFaceDataCfg.dwPicDataSize = picDataLength;
                    struAddFaceDataCfg.lpPicData = ptrPicData.getPointer();
                } catch (Exception e) {
                    logger.error("图片数据处理失败: {}", e.getMessage(), e);
                    res.put("status", false);
                    res.put("message", "图片数据处理失败: " + e.getMessage());
                    return res;
                }
            } else {
                logger.warn("未提供图片数据");
                struAddFaceDataCfg.dwPicDataSize = 0;
                struAddFaceDataCfg.lpPicData = null;
            }
            struAddFaceDataCfg.write();

            // 3. 接收输出结果
            HCNetSDK.BYTE_ARRAY ptrOutuff = new HCNetSDK.BYTE_ARRAY(1024);
            IntByReference pInt = new IntByReference(0);

            while (true) {
                int dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(
                        lHandler,
                        struAddFaceDataCfg.getPointer(),
                        struAddFaceDataCfg.dwSize,
                        ptrOutuff.getPointer(),
                        1024,
                        pInt
                );

                if (dwState == -1) {
                    int errCode = hCNetSDK.NET_DVR_GetLastError();
                    logger.error("接口调用失败，错误码：{}", errCode);
                    res.put("status", false);
                    res.put("message", "接口调用失败，错误码：" + errCode);
                    return res;
                }

                // 读取返回的JSON并解析
                ptrOutuff.read();
                String resultJson = new String(ptrOutuff.byValue, StandardCharsets.UTF_8).trim();
                if (resultJson.isEmpty()) continue;
                logger.debug("dwState: {}", dwState);
                logger.debug("resultJson: {}", resultJson);

                // 解析JSON结果
                JSONObject jsonResult = new JSONObject(resultJson);
                int statusCode = jsonResult.optInt("statusCode", -1);
                String statusString = jsonResult.optString("statusString", "");

                if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEED_WAIT) {
                    logger.info("配置等待");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        logger.error("配置等待失败");
                        Thread.currentThread().interrupt();
                    }
                    continue;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                    logger.debug("更新人脸信息失败: {}", jsonResult);
                    res.put("status", false);
                    res.put("statusCode", jsonResult.optInt("statusCode", -1));
                    res.put("message", "更新人脸信息失败");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                    logger.debug("更新人脸信息异常: {}", jsonResult);
                    res.put("status", false);
                    res.put("message", "查询人脸信息异常");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                    if (statusCode != 1) {
                        logger.warn("更新人脸信息成功,但是有异常情况: {}", jsonResult);
                        res.put("message", "更新人脸信息成功,但是有异常情况");
                    } else {
                        logger.debug("更新人脸信息成功: {}", jsonResult);
                        res.put("message", "更新人脸信息成功");
                    }
                    res.put("status", true);
                    res.put("statusCode", jsonResult.optInt("statusCode", 1));
                    res.put("statusString", jsonResult.optString("statusString", "ok"));
                    res.put("subStatusCode", jsonResult.optString("statusString", "ok"));
                    res.put("errorCode", jsonResult.optInt("errorCode", 1));
                    res.put("errorMsg", jsonResult.optString("errorMsg", "ok"));
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                    logger.info("更新人脸信息完成");
                    res.put("status", true);
                    res.put("message", "更新人脸信息完成");
                    break;
                } else {
                    logger.warn("未知状态码：" + dwState);
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("网络或IO异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "网络或IO异常");
        } catch (JSONException e) {
            logger.error("JSON解析异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "JSON解析异常");
        } catch (Exception e) {
            logger.error("查询人脸信息异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "查询人脸信息异常");
        } finally {
            if (lHandler >= 0) stopRemoteConfig(hCNetSDK, lHandler, res);
        }
        return res;
    }

    /**
     * 生成查询条件的 JSON 字符串
     *
     * @param condition 查询参数（包含 searchID、EmployeeNoList 等）
     * @return 查询条件的 JSON 字符串
     * @throws IOException 文件读写异常
     */
    private String generateUpdateCondition(Map<String, Object> condition) throws IOException {
        Map<String, Object> userInfoSearchCond = new HashMap<>();

//        userInfoSearchCond.put("faceURL", condition.getOrDefault("faceURL", null));
        userInfoSearchCond.put("name", condition.getOrDefault("name", null));
//        userInfoSearchCond.put("gender", condition.getOrDefault("gender", null));
        userInfoSearchCond.put("bornTime", condition.getOrDefault("bornTime", null));
//        userInfoSearchCond.put("city", condition.getOrDefault("city", null));
//        userInfoSearchCond.put("certificateType", condition.getOrDefault("certificateType", null));
//        userInfoSearchCond.put("certificateNumber", condition.getOrDefault("certificateNumber", null));
//        userInfoSearchCond.put("caseInfo", condition.getOrDefault("caseInfo", null));
//        userInfoSearchCond.put("tag", condition.getOrDefault("tag", null));
//        userInfoSearchCond.put("address", condition.getOrDefault("address", null));
//        userInfoSearchCond.put("customInfo", condition.getOrDefault("customInfo", null));
//        userInfoSearchCond.put("modelData", condition.getOrDefault("modelData", null));
//        userInfoSearchCond.put("transfer", condition.getOrDefault("transfer", null));
        userInfoSearchCond.put("PicFeaturePoints", condition.getOrDefault("PicFeaturePoints", null));
//        userInfoSearchCond.put("faceType", condition.getOrDefault("faceType", null));
//        userInfoSearchCond.put("saveFacePic", condition.getOrDefault("saveFacePic", null));
//        userInfoSearchCond.put("leaderPermission", condition.getOrDefault("leaderPermission", null));

        // JDK8 不支持 Map.of()，手动构造
        Map<String, Object> queryCondition = new HashMap<>();
//        queryCondition.put("UserInfoSearchCond", userInfoSearchCond);

        // ---------- 2. 尝试从资源文件读取模板 ----------
        String jsonTemplate = new JSONObject(userInfoSearchCond).toString();

//        String jsonTemplate;
//        String relativePath = "resources/conf/acs/Face/input/UpdateFaceInfoInputParam.json";
//        String absolutePath = System.getProperty("user.dir") + File.separator
//                + "supervision-admin" + File.separator + relativePath;
//
//        File templateFile = new File(absolutePath);
//
//        logger.info("🧭 当前工作目录: {}", System.getProperty("user.dir"));
//        logger.info("📂 模板文件路径: {}", templateFile.getAbsolutePath());
//
//        if (templateFile.isFile() && templateFile.canRead()) {
//            // JDK8 没有 Files.readString()，改用 readAllBytes()
//            byte[] bytes = Files.readAllBytes(templateFile.toPath());
//            jsonTemplate = new String(bytes, StandardCharsets.UTF_8);
//            logger.info("✅ 模板文件读取成功");
//        } else {
//            logger.warn("⚠ 模板文件不存在或不可读，使用默认 JSON 构建");
//            jsonTemplate = new JSONObject(queryCondition).toString();
//        }
//
//        // ---------- 3. 内容校验与兜底 ----------
//        if (jsonTemplate == null || jsonTemplate.trim().isEmpty()) {
//            logger.error("⚠ 模板内容为空，使用默认 JSON 兜底");
//            jsonTemplate = new JSONObject(queryCondition).toString();
//        }
//
//        // ---------- 4. 打印最终结果 ----------
//        logger.info("📄 最终生成的 JSON 内容: {}", jsonTemplate);
//        logger.info("📏 JSON 长度: {}", jsonTemplate.length());

        return jsonTemplate;
    }

    /**
     * 删除人脸信息
     *
     * @param userID    登录句柄
     * @param condition 员工ID 人脸信息对象（包含所有字段，参考 SDK JSON）
     */
    public Map<String, Object> deleteFaceInfo(HCNetSDK hCNetSDK, int userID, Map<String, Object> condition) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("FPID", condition.get("FPID"));
        parameter.put("operateType", condition.getOrDefault("operateType", null));
        parameter.put("terminalNoList", condition.getOrDefault("operateType", null));
        try {
            // 构建输入参数
            String url = "PUT /ISAPI/Intelligent/FDLib/FDSearch/Delete?format=json&FDID=1&faceLibType=blackFD";
            // 构建输入参数
            String inputJson = new JSONObject(parameter).toString();
//            String inputJson = generateDeleteCondition(condition);
            logger.info("删除人脸参数: {}", inputJson);
            TransIsapi obj = new TransIsapi();
            String result= obj.put_isapi(hCNetSDK,userID,url,inputJson);
            logger.info("删除人员结果：{}", result);
            res.put("status", true);
            res.put("message", "删除人脸成功");
        } catch (JSONException e) {
            logger.error("JSON解析异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "JSON解析异常");
        } catch (Exception e) {
            logger.error("删除人脸异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "删除人脸异常");
        }
        return res;
    }

    /**
     * 生成查询条件的 JSON 字符串
     *
     * @param condition 查询参数（包含 searchID、EmployeeNoList 等）
     * @return 查询条件的 JSON 字符串
     * @throws IOException 文件读写异常
     */
    private String generateDeleteCondition(Map<String, Object> condition) throws IOException {
        // ---------- 1. 构建查询条件 ----------
        Map<String, Object> userInfoSearchCond = new HashMap<>();

        userInfoSearchCond.put("FPID", condition.get("FPID"));
        userInfoSearchCond.put("operateType", condition.getOrDefault("operateType", null));
        userInfoSearchCond.put("terminalNoList", condition.getOrDefault("terminalNoList", null));

        // JDK8 不支持 Map.of()，手动构造
        Map<String, Object> queryCondition = new HashMap<>();
        queryCondition.put("UserInfoSearchCond", userInfoSearchCond);

        // ---------- 2. 尝试从资源文件读取模板 ----------
        String jsonTemplate;
        String relativePath = "resources/conf/acs/Face/input/UpdateUserInfoInputParam.json";
        String absolutePath = System.getProperty("user.dir") + File.separator
                + "supervision-admin" + File.separator + relativePath;

        File templateFile = new File(absolutePath);

        logger.info("🧭 当前工作目录: {}", System.getProperty("user.dir"));
        logger.info("📂 模板文件路径: {}", templateFile.getAbsolutePath());

        if (templateFile.isFile() && templateFile.canRead()) {
            // JDK8 没有 Files.readString()，改用 readAllBytes()
            byte[] bytes = Files.readAllBytes(templateFile.toPath());
            jsonTemplate = new String(bytes, StandardCharsets.UTF_8);
            logger.info("✅ 模板文件读取成功");
        } else {
            logger.warn("⚠ 模板文件不存在或不可读，使用默认 JSON 构建");
            jsonTemplate = new JSONObject(queryCondition).toString();
        }

        // ---------- 3. 内容校验与兜底 ----------
        if (jsonTemplate == null || jsonTemplate.trim().isEmpty()) {
            logger.error("⚠ 模板内容为空，使用默认 JSON 兜底");
            jsonTemplate = new JSONObject(queryCondition).toString();
        }

        // ---------- 4. 打印最终结果 ----------
        logger.info("📄 最终生成的 JSON 内容: {}", jsonTemplate);
        logger.info("📏 JSON 长度: {}", jsonTemplate.length());

        return jsonTemplate;
    }

    /**
     * 获取设备上所有人脸的数量(新加的)
     *
     * @param userID 用户登录句柄
     * @return 人脸数量
     */
    public Map<String, Object> getFaceCount(HCNetSDK hCNetSDK, int userID) {
        HashMap<String, Object> res = new HashMap<>();
        // 设置输入参数
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struXMLInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struXMLInput.read();

        HCNetSDK.BYTE_ARRAY stringRequest = new HCNetSDK.BYTE_ARRAY(1024);
        stringRequest.read();
        // 设置请求 URL
        String url = "GET /ISAPI/Intelligent/FDLib/Count?format=json";
        // 构造字节数组以存储 URL，并写入到 SDK 所需的内存区域

        System.arraycopy(url.getBytes(), 0, stringRequest.byValue, 0, url.length());
        stringRequest.write();

        struXMLInput.dwSize = struXMLInput.size();
        struXMLInput.lpRequestUrl = stringRequest.getPointer();
        struXMLInput.dwRequestUrlLen = url.length();
        struXMLInput.lpInBuffer = null;
        struXMLInput.dwInBufferSize = 0;
        struXMLInput.write();

        // 设置输出参数
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

        // 调用海康 SDK 接口 // 调用标准 XML 配置接口，执行 ISAPI 命令
        boolean handler = hCNetSDK.NET_DVR_STDXMLConfig(
                userID,
                struXMLInput,
                struXMLOutput
        );
        if (!handler) {
            logger.error("获取人脸数量失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
            res.put("status", false);
            res.put("message", "获取人脸数量失败，错误码"+ hCNetSDK.NET_DVR_GetLastError());
            return res;
        }

        // 获取返回的 XML 内容（ISAPI 接口返回的是 XML 数据）
        byte[] responseData = struXMLOutput.lpOutBuffer.getByteArray(0, struXMLOutput.dwOutBufferSize);
        String responseJson = new String(responseData, StandardCharsets.UTF_8);  // 明确指定编码
//        logger.info("responseJson:{}", responseJson);
        Map<String, Object> resultMap = JSON.parseObject(responseJson,Map.class);

        try {
            logger.info("获取人脸数量信息成功：" + resultMap);
            res.put("status", true);
            res.put("message", "获取人脸数量信息成功");
            res.put("data", resultMap);
        } catch (Exception e) {
            logger.error("获取人脸数量失败，错误信息：" + e.getMessage(), e);
            res.put("status", false);
            res.put("message", "获取人脸数量失败");
        }
        return res;
    }

    /**
     * 人脸采集，下发人脸采集命令，从设备中采集人脸图片保存到本地
     *
     * @param userID 用户注册ID
     */
    public Map<String, Object> captureFaceInfo(HCNetSDK hCNetSDK, int userID) {
        Map<String, Object> res = new HashMap<>();
        // 构造采集条件结构体 NET_DVR_CAPTURE_FACE_COND（采集参数）
        HCNetSDK.NET_DVR_CAPTURE_FACE_COND struCapCond = new HCNetSDK.NET_DVR_CAPTURE_FACE_COND();
        struCapCond.read();
        struCapCond.dwSize = struCapCond.size();
        struCapCond.write();
        // 建立远程配置会话，用于采集人脸（返回句柄 lCaptureFaceHandle）
        int lCaptureFaceHandle = hCNetSDK.NET_DVR_StartRemoteConfig(
                userID,
                HCNetSDK.NET_DVR_CAPTURE_FACE_INFO,
                struCapCond.getPointer(),
                struCapCond.size(),
                null, null
        );

        //  判断连接是否成功
        if (lCaptureFaceHandle == -1) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            logger.info("建立采集人脸长连接失败，错误码：" + errorCode);
            res.put("status", "error");
            res.put("code", errorCode);
            res.put("message", "建立采集人脸长连接失败" );
            return res;
        } else {
            logger.info("建立采集人脸长连接成功！");
        }
        // 采集的人脸信息
        // 创建接收人脸数据的结构体 NET_DVR_CAPTURE_FACE_CFG（采集结果）
        HCNetSDK.NET_DVR_CAPTURE_FACE_CFG struFaceInfo = new HCNetSDK.NET_DVR_CAPTURE_FACE_CFG();
        struFaceInfo.read();

        // 进入循环，从设备持续读取采集状态或图像数据
        while (true) {
            int dwState = hCNetSDK.NET_DVR_GetNextRemoteConfig(
                    lCaptureFaceHandle,
                    struFaceInfo.getPointer(),
                    struFaceInfo.size()
            );
            struFaceInfo.read();
            if (dwState == -1) {
                int errorCode = hCNetSDK.NET_DVR_GetLastError();
                logger.info("NET_DVR_GetNextRemoteConfig采集人脸失败，错误码：" + errorCode);
                res.put("status", "error");
                res.put("code", errorCode);
                res.put("message", "采集人脸失败");
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_NEED_WAIT) {
                logger.info("正在采集中,请等待...");
                continue;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED) {
                logger.info("采集人脸失败");
                res.put("status", "error");
                res.put("code", null);
                res.put("message", "采集人脸失败");
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                // 超时时间5秒内设备本地人脸采集失败就会返回失败,连接会断开
                logger.info("采集人脸异常, 网络异常导致连接断开 ");
                res.put("status", "error");
                res.put("message", "网络异常导致采集中断");
                break;
            } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                // 采集成功，有人脸图像数据
                if ((struFaceInfo.dwFacePicSize > 0) && (struFaceInfo.pFacePicBuffer != null)) {
                    // 生成文件名与保存路径
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = "..\\pic\\" + newName + "_capFaceInfo.jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers =
                                struFaceInfo.pFacePicBuffer.getByteBuffer(offset, struFaceInfo.dwFacePicSize);
                        byte[] bytes = new byte[struFaceInfo.dwFacePicSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();

                        // Base64 编码（可选，用于上传）
                        String base64Image = Base64.getEncoder().encodeToString(bytes);
                        res.put("status", "success");                        // 采集状态
                        res.put("message", "人脸采集成功");                   // 状态描述
                        res.put("filePath", filename);                        // 图片保存路径
                        res.put("fileName", newName + "_capFaceInfo.jpg");    // 图片文件名
                        res.put("base64Image", base64Image);                  // Base64（可选）
                        res.put("timestamp", System.currentTimeMillis());     // 时间戳
                        logger.info("采集人脸成功, 图片保存路径: " + filename);
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
                logger.info("其他异常, dwState: " + dwState);
                res.put("status", "error");
                res.put("code", null);
                res.put("message", "其他异常, dwState: " + dwState);
                break;
            }
        }
        // 采集成功之后断开连接、释放资源
        stopRemoteConfig(hCNetSDK, lCaptureFaceHandle, res);
        return res;
    }

    public boolean validateRequiredFields(Map<String, Object> condition, List<String> requiredFields) {
        for (String key : requiredFields) {
            Object value = condition.get(key);
            if (value == null || String.valueOf(value).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public String getReturnMessage(int state) {
        switch (state) {
            case 1000:
                return "成功读取到数据，处理完本次数据后需要再次调用NET_DVR_SendWithRecvRemoteConfig获取下一条数据";
            case 1001:
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "需等待设备发送数据，继续调用NET_DVR_SendWithRecvRemoteConfig";
            case 1002:
                return "数据全部取完，调用NET_DVR_StopRemoteConfig结束长连接";
            case 1003:
                return "配置失败，可重新NET_DVR_SendWithRecvRemoteConfig下发下一条";
            case 1004:
                return "出现异常，可调用NET_DVR_StopRemoteConfig结束长连接";
            default:
                return "未知错误";
        }
    }

}
