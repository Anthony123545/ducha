package com.supervision.web.Hikvision.peopleDoor.connectSDK.Acs;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.supervision.web.Hikvision.carDoor.connectSDK.CommonMethod.ConfigFileUtil;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
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
public class FaceForPeople {

    private static final Logger logger = LoggerFactory.getLogger(FaceManage.class);

    // 定义所有必传字段
    private static final List<String> SEARCH_FACE_INFO_REQUIRED_FIELDS = Arrays.asList(
            "searchResultPosition",
            "maxResults"
    );

    private static final List<String> ADD_FACE_INFO_REQUIRED_FIELDS = Arrays.asList(
            "faceLibType",
            "FDID"
    );

    private static final List<String> DELETE_FACE_INFO_REQUIRED_FIELDS = Arrays.asList(

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
            String strInbuff = generateFaceSearchCondition(condition);
            if(strInbuff == null  ){
                res.put("status", false);
                res.put("message", "缺少必要参数");
                return res;
            }
            logger.info("查询人脸参数: {}", strInbuff);

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
                logger.debug("strResult:" + strResult);
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
    private String generateSearchCondition(Map<String, Object> condition) throws IOException {
        boolean res = validateRequiredFields(condition, SEARCH_FACE_INFO_REQUIRED_FIELDS);
        if (!res) {
            logger.error("缺少必填字段");
            return null;
        }

        // 构建 FDSearchDescription 对象（核心搜索参数）
        JSONObject searchDesc = new JSONObject();

        // 【必填】searchResultPosition：查询起始位置，整数
        if (condition.containsKey("searchResultPosition") || condition.get("searchResultPosition") != null) {
            Object posObj = condition.get("searchResultPosition");
            if (!(posObj instanceof Integer)) {
                throw new IllegalArgumentException("必填字段 'searchResultPosition' 必须为整数类型");
            }
            searchDesc.put("searchResultPosition", posObj);
        }

        // 【必填】maxResults：最大返回数量，整数
        if (condition.containsKey("maxResults") || condition.get("maxResults") != null) {
            Object maxResObj = condition.get("maxResults");
            if (!(maxResObj instanceof Integer)) {
                throw new IllegalArgumentException("必填字段 'maxResults' 必须为整数类型");
            }
            searchDesc.put("maxResults", maxResObj);
        }

        // 【必填】FDID：人脸库ID,人脸库唯一标识，字符串，最大63字节，必填
        Object fdidObj = condition.get("FDID");
        if (fdidObj != null || fdidObj.toString().trim().isEmpty()) {
            String fdid = fdidObj.toString().trim();
            if (fdid.length() > 63) {
                throw new IllegalArgumentException("必填字段 'FDID' 长度不能超过 63 字节");
            }
            searchDesc.put("FDID", fdid);
        }

        // 【必填】FPID：人员ID
        Object fpidObj = condition.get("FPID");
        if (fpidObj != null || fpidObj.toString().trim().isEmpty()) {
            String fpid = fpidObj.toString().trim();
            if (fpid.length() > 63) {
                throw new IllegalArgumentException("必填字段 'FPID' 长度不能超过 63 字节");
            }
            searchDesc.put("FPID", fpid);
        }

        // 【必填】searchID：搜索任务唯一标识
        if (condition.containsKey("searchID") || condition.get("searchID") != null) {
            Object searchIdObj = condition.get("searchID");
            searchDesc.put("searchID", searchIdObj.toString().trim());
        }

        // 【必填】name：姓名
        if (condition.containsKey("name") || condition.get("name") != null) {
            Object nameObj = condition.get("name");
            searchDesc.put("name", nameObj.toString().trim());
        }

        //
        if (condition.containsKey("faceLibType") || condition.get("faceLibType") != null) {
            Object faceLibTypeObj = condition.get("faceLibType");
            searchDesc.put("faceLibType", faceLibTypeObj.toString().trim());
        }

        // ========== 可选字段：有值则放入 ==========
        // startTime：开始时间，格式如 "2024-01-01+08:00"
        if (condition.containsKey("startTime") && condition.get("startTime") != null) {
            searchDesc.put("startTime", condition.get("startTime").toString().trim());
        }

        // endTime：结束时间
        if (condition.containsKey("endTime") && condition.get("endTime") != null) {
            searchDesc.put("endTime", condition.get("endTime").toString().trim());
        }

        // sex：性别 [male/female/unknown]
        if (condition.containsKey("sex") && condition.get("sex") != null) {
            searchDesc.put("sex", condition.get("sex").toString().trim());
        }

        // province：省份代码
        if (condition.containsKey("province") && condition.get("province") != null) {
            searchDesc.put("province", condition.get("province").toString().trim());
        }

        // city：城市代码
        if (condition.containsKey("city") && condition.get("city") != null) {
            searchDesc.put("city", condition.get("city").toString().trim());
        }

        // certificateType：证件类型 [ID/passport/other]
        if (condition.containsKey("certificateType") && condition.get("certificateType") != null) {
            searchDesc.put("certificateType", condition.get("certificateType").toString().trim());
        }

        // certificateNumber：证件号码
        if (condition.containsKey("certificateNumber") && condition.get("certificateNumber") != null) {
            searchDesc.put("certificateNumber", condition.get("certificateNumber").toString().trim());
        }

        // phoneNumber：电话号码
        if (condition.containsKey("phoneNumber") && condition.get("phoneNumber") != null) {
            searchDesc.put("phoneNumber", condition.get("phoneNumber").toString().trim());
        }

        // FaceModeList：人脸模式列表（复杂结构，可选）
        if (condition.containsKey("FaceModeList") && condition.get("FaceModeList") != null) {
            searchDesc.put("FaceModeList", condition.get("FaceModeList")); // 假设传入的是合法的 List<Map>
        }

        // modelStatus：建模状态
        if (condition.containsKey("modelStatus") && condition.get("modelStatus") != null) {
            searchDesc.put("modelStatus", condition.get("modelStatus").toString().trim());
        }

        // modelingStatus：建模状态列表（似乎是固定枚举，通常不传值）
        if (condition.containsKey("modelingStatus") && condition.get("modelingStatus") != null) {
            searchDesc.put("modelingStatus", condition.get("modelingStatus"));
        }

        // customFaceLibID：自定义人脸库ID
        if (condition.containsKey("customFaceLibID") && condition.get("customFaceLibID") != null) {
            searchDesc.put("customFaceLibID", condition.get("customFaceLibID").toString().trim());
        }

        // sortord / order：排序字段
        if (condition.containsKey("sortord") && condition.get("sortord") != null) {
            searchDesc.put("sortord", condition.get("sortord").toString().trim());
        }
        if (condition.containsKey("order") && condition.get("order") != null) {
            searchDesc.put("order", condition.get("order").toString().trim());
        }

        // StrangerLibFaceAppendData：扩展信息（复杂结构，可选）
        if (condition.containsKey("StrangerLibFaceAppendData") && condition.get("StrangerLibFaceAppendData") != null) {
            searchDesc.put("StrangerLibFaceAppendData", condition.get("StrangerLibFaceAppendData"));
        }

        // OccurrencesInfo：出现次数条件
        if (condition.containsKey("OccurrencesInfo") && condition.get("OccurrencesInfo") != null) {
            searchDesc.put("OccurrencesInfo", condition.get("OccurrencesInfo"));
        }

        // faceScore / faceScoreMax：人脸评分筛选
        if (condition.containsKey("faceScore") && condition.get("faceScore") != null) {
            searchDesc.put("faceScore", condition.get("faceScore"));
        }
        if (condition.containsKey("faceScoreMax") && condition.get("faceScoreMax") != null) {
            searchDesc.put("faceScoreMax", condition.get("faceScoreMax"));
        }

        // pictureStatus：图片状态
        if (condition.containsKey("pictureStatus") && condition.get("pictureStatus") != null) {
            searchDesc.put("pictureStatus", condition.get("pictureStatus").toString().trim());
        }

        // FDIDList：人脸库ID列表（数组）
        if (condition.containsKey("FDIDList") && condition.get("FDIDList") != null) {
            searchDesc.put("FDIDList", condition.get("FDIDList"));
        }


        // ========== 构造最终 JSON 字符串 ==========
        String jsonTemplate = new JSONObject(searchDesc).toString();
        logger.debug("生成的查询人脸参数 JSON: {}", jsonTemplate);
        return jsonTemplate;
    }


//    searchID	string	必填，每次搜索要唯一
//    searchResultPosition	int	必填，分页起始位置
//    maxResults	int	必填，返回数量
//    FDID 或 FDIDList	string/object	你要查哪个库（强烈建议只用 FDID）

    private String generateFaceSearchCondition(Map<String, Object> condition)throws IOException {

        JSONObject json = new JSONObject();

        json.put("searchResultPosition",
                condition.getOrDefault("searchResultPosition", 0));

        json.put("maxResults",
                condition.getOrDefault("maxResults", 30));

        json.put("FDID",
                condition.getOrDefault("FDID", "1"));

        if (condition.containsKey("FPID")) {
            json.put("FPID", condition.get("FPID"));
        }

        if (condition.containsKey("faceLibType")) {
            json.put("faceLibType", condition.get("faceLibType"));
        } else {
            json.put("faceLibType", "face");
        }

        // 最推荐的写法：按工号查人脸
        if (condition.containsKey("employeeNo")) {
            JSONObject employeeList = new JSONObject();
            employeeList.put("employeeNo",
                    new JSONArray().put(condition.get("employeeNo")));
            json.put("employeeNoList", employeeList);
        }

        return json.toString();
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
            HCNetSDK.BYTE_ARRAY ptrInBuffer = new HCNetSDK.BYTE_ARRAY(1024);
            System.arraycopy(strInBuffer.getBytes(), 0, ptrInBuffer.byValue, 0, inBufferLength);
            ptrInBuffer.write();

            lHandler = hCNetSDK.NET_DVR_StartRemoteConfig(
                    userID,
                    HCNetSDK.NET_DVR_FACE_DATA_RECORD,
                    ptrInBuffer.getPointer(),
                    strInBuffer.length(),
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
//            struAddFaceDataCfg.lpJsonData = jsonPtr;
//            struAddFaceDataCfg.dwJsonDataSize = jsonBytes.length;
            struAddFaceDataCfg.dwJsonDataSize = jsonDataLength;

            MultipartFile file = (MultipartFile) condition.get("file");

            if (file == null || file.isEmpty()) {
                logger.error("上传的人脸图片文件为空");
                res.put("status", false);
                res.put("message", "上传的人脸图片文件为空");
                return res;
            }

            int picdataLength = (int) file.getSize();
            if (picdataLength < 0) {
                logger.error("上传的图片文件大小非法: {}", picdataLength);
                res.put("status", false);
                res.put("message", "上传的图片文件大小非法");
                return res;
            }

            HCNetSDK.BYTE_ARRAY ptrpicByte = new HCNetSDK.BYTE_ARRAY(picdataLength);
            try {
                byte[] fileBytes = file.getBytes();
                System.arraycopy(fileBytes, 0, ptrpicByte.byValue, 0, fileBytes.length);
                ptrpicByte.write();

                struAddFaceDataCfg.dwPicDataSize = picdataLength;
                struAddFaceDataCfg.lpPicData = ptrpicByte.getPointer();
                struAddFaceDataCfg.write();
            } catch (IOException e) {
                logger.error("读取上传的图片文件失败", e);
                res.put("status", false);
                res.put("message", "读取上传的图片文件失败: " + e.getMessage());
                return res;
            }

            // 3. 接收输出结果
            HCNetSDK.BYTE_ARRAY ptrOutuff = new HCNetSDK.BYTE_ARRAY(1024);
            ptrOutuff.write();
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
     * 生成新增人脸的 JSON 请求参数字符串
     *
     * @param condition 传入的人脸参数，包括必填和可选字段，类型为 Map<String, Object>
     * @return 生成的 JSON 格式字符串，符合海康威视 SDK 接口规范
     * @throws IllegalArgumentException 如果必填字段缺失或字段值不合法
     */
    private String generateAddCondition(Map<String, Object> condition) throws IllegalArgumentException {
        boolean res = validateRequiredFields(condition, ADD_FACE_INFO_REQUIRED_FIELDS);
        if (!res) {
            logger.error("缺少必填字段");
            return null;
        }

        // 用于构造最终 JSON 的字段容器
        Map<String, Object> userInfoAddCond = new HashMap<>();

        // ========== 必填字段校验与处理 ==========
        // 1. faceLibType：人脸库类型，如 "blackFD"（名单库）、"staticFD"（静态库），必填
        Object faceLibTypeObj = condition.get("faceLibType");
        if (faceLibTypeObj != null || !faceLibTypeObj.toString().trim().isEmpty()) {
            String faceLibType = faceLibTypeObj.toString().trim();
            // 可选增强：校验是否是合法枚举值，比如只允许 "blackFD" 或 "staticFD"
            // if (!"blackFD".equals(faceLibType) && !"staticFD".equals(faceLibType)) { ... }
            userInfoAddCond.put("faceLibType", faceLibType);
        }

        // 2. FDID：人脸库唯一标识，字符串，最大63字节，必填
        Object fdidObj = condition.get("FDID");
        if (fdidObj != null || !fdidObj.toString().trim().isEmpty()) {
            String fdid = fdidObj.toString().trim();
            if (fdid.length() > 63) {
                throw new IllegalArgumentException("必填字段 'FDID' 长度不能超过 63 字节");
            }
            userInfoAddCond.put("FDID", fdid);
        }

        // ========== 可选字段处理 ==========
        // 1. faceURL：图片 URL（可选，如果使用 MultipartFile 上传图片，通常不需要）
        if (condition.containsKey("faceURL") && condition.get("faceURL") != null) {
            Object faceURLObj = condition.get("faceURL");
            userInfoAddCond.put("faceURL", faceURLObj.toString().trim());
        }

        // 2. FPID：人脸记录ID，字符串，最大63字节（可选）
        if (condition.containsKey("FPID") && condition.get("FPID") != null) {
            Object fpidObj = condition.get("FPID");
            String fpid = fpidObj.toString().trim();
            if (fpid.length() > 63) {
                throw new IllegalArgumentException("可选字段 'FPID' 长度不能超过 63 字节");
            }
            userInfoAddCond.put("FPID", fpid);
        }

        // 3. deleteFP：是否删除该人脸，布尔类型（可选）
        if (condition.containsKey("deleteFP") && condition.get("deleteFP") != null) {
            Object deleteFpObj = condition.get("deleteFP");
            if (deleteFpObj instanceof Boolean) {
                userInfoAddCond.put("deleteFP", deleteFpObj);
            } else if (deleteFpObj instanceof String) {
                // 兼容字符串 "true"/"false"
                userInfoAddCond.put("deleteFP", Boolean.parseBoolean(deleteFpObj.toString()));
            }
            // 如果不是 Boolean 或 String 类型，可以选择忽略或记录警告日志
        }

        // 4. modelData：模型数据，通常是 base64，字符串类型（可选）
        if (condition.containsKey("modelData") && condition.get("modelData") != null) {
            userInfoAddCond.put("modelData", condition.get("modelData").toString().trim());
        }

        // 5. PicFeaturePoint：特征点坐标数组，结构较复杂，可选（建议后续可封装为对象）
        if (condition.containsKey("PicFeaturePoint") && condition.get("PicFeaturePoint") != null) {
            userInfoAddCond.put("PicFeaturePoint", condition.get("PicFeaturePoint"));
        }

        // 6. faceType：人脸类型，如 normalFace，字符串，可选
        if (condition.containsKey("faceType") && condition.get("faceType") != null) {
            userInfoAddCond.put("faceType", condition.get("faceType").toString().trim());
        }

        // 7. saveFacePic：是否保存人脸底图，布尔类型，可选
        if (condition.containsKey("saveFacePic") && condition.get("saveFacePic") != null) {
            Object saveFacePicObj = condition.get("saveFacePic");
            if (saveFacePicObj instanceof Boolean) {
                userInfoAddCond.put("saveFacePic", saveFacePicObj);
            } else if (saveFacePicObj instanceof String) {
                userInfoAddCond.put("saveFacePic", Boolean.parseBoolean(saveFacePicObj.toString()));
            }
        }

        // 8. leaderPermission：首次认证权限，字符串数组，如 ["door1", "door2"]，可选
        if (condition.containsKey("leaderPermission") && condition.get("leaderPermission") != null) {
            userInfoAddCond.put("leaderPermission", condition.get("leaderPermission"));
        }

        // ========== 构造最终 JSON 字符串 ==========
        String jsonTemplate = new JSONObject(userInfoAddCond).toString();
        logger.debug("生成的新增人脸参数 JSON: {}", jsonTemplate);
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
        try {
            // url
            String url = "PUT /ISAPI/Intelligent/FDLib/FDSearch/Delete?format=json&FDID=1&faceLibType=blackFD";
            // 构建输入参数
            String inputJson = generateDeleteCondition(condition);
            logger.info("删除人脸参数: {}", inputJson);

            TransIsapi obj = new TransIsapi();
            String result= obj.put_isapi(hCNetSDK, userID, url, inputJson);
            logger.info("删除人脸结果：{}", result);

            res.put("status", true);
            res.put("message", "删除人脸成功");
        } catch (JSONException e) {
            logger.error("JSON解析异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "JSON解析异常");
        } catch (Exception e) {
            logger.error("删除人脸信息异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "删除人脸信息异常");
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
    private String generateDeleteCondition(Map<String, Object> condition) throws IllegalArgumentException {
        boolean res = validateRequiredFields(condition, DELETE_FACE_INFO_REQUIRED_FIELDS);
        if (!res) {
            logger.error("缺少必填字段");
            return null;
        }

        // 用于构造最终 JSON 的字段容器
        Map<String, Object> userInfoDeleteCond = new HashMap<>();

        // ===== 处理 operateType =====
        if (condition.containsKey("operateType") && condition.get("operateType") != null) {
            userInfoDeleteCond.put("operateType", condition.get("operateType").toString().trim());
        }

        // ===== 处理 FPID（人脸记录ID列表） =====
        if (condition.containsKey("FPID") && condition.get("FPID") != null) {
            Object fpidObj = condition.get("FPID");
            // 简单校验：期望 FPID 是一个 List（数组）
            if (!(fpidObj instanceof List)) {
                throw new IllegalArgumentException("字段 FPID 必须为数组格式");
            }
            // 直接放入，假设前端已经传入了正确结构的 List<Map>
            userInfoDeleteCond.put("FPID", fpidObj);
        }

        // ===== 处理 terminalNoList（终端ID列表） =====
        if (condition.containsKey("terminalNoList") && condition.get("terminalNoList") != null) {
            Object terminalNoListObj = condition.get("terminalNoList");
            if (!(terminalNoListObj instanceof List)) {
                throw new IllegalArgumentException("字段 terminalNoList 必须为数组格式");
            }
            userInfoDeleteCond.put("terminalNoList", terminalNoListObj);
        }

        // ========== 构造最终 JSON 字符串 ==========
        String jsonTemplate = new JSONObject(userInfoDeleteCond).toString();
        logger.debug("生成的删除人脸参数 JSON: {}", jsonTemplate);
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

