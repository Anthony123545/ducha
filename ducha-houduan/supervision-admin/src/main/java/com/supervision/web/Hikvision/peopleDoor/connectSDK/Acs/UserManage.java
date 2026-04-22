package com.supervision.web.Hikvision.peopleDoor.connectSDK.Acs;


import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import com.alibaba.fastjson2.JSON;

/**
 * 功能：人脸下发、查询、删除、人员计划模板配置
 */
@Component
public class UserManage {

    private static final Logger logger = LoggerFactory.getLogger(UserManage.class);

    // 定义所有必传字段
    private static final List<String> SEARCH_USER_INFO_REQUIRED_FIELDS = Arrays.asList(
    );

    private static final List<String> ADD_USER_INFO_REQUIRED_FIELDS = Arrays.asList(
    );

    private static final List<String> UPDATE_USER_INFO_REQUIRED_FIELDS = Arrays.asList(
    );

    private static final List<String> DELETE_USER_INFO_REQUIRED_FIELDS = Arrays.asList(

    );


    /**
     * 获取设备上所有人员的数量
     *
     * @param userID 用户登录句柄
     * @return 人员数量
     */
    public Map<String, Object> getUserCount(HCNetSDK hCNetSDK, int userID) {
        Map<String, Object> res = new HashMap<>();

        // 设置输入参数
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struXMLInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struXMLInput.read();

        // 设置请求URL
        HCNetSDK.BYTE_ARRAY stringRequest = new HCNetSDK.BYTE_ARRAY(1024);
        stringRequest.read();
        String url = "GET /ISAPI/AccessControl/UserInfo/Count?format=json";
        System.arraycopy(url.getBytes(), 0, stringRequest.byValue, 0, url.length());
        stringRequest.write();

        struXMLInput.dwSize = struXMLInput.size();  // 设置请求的 URL
        struXMLInput.lpRequestUrl = stringRequest.getPointer();  // 设置请求的 URL
        struXMLInput.dwRequestUrlLen = url.length();  // 设置请求的 URL
        struXMLInput.lpInBuffer = null;  // 设置请求的 URL
        struXMLInput.dwInBufferSize = 0;  // 设置请求的 URL
        struXMLInput.write();

        // 设置输出参数
        HCNetSDK.BYTE_ARRAY stringXMLOut = new HCNetSDK.BYTE_ARRAY(8 * 1024);
        stringXMLOut.read();

        HCNetSDK.BYTE_ARRAY struXMLStatus = new HCNetSDK.BYTE_ARRAY(1024);
        struXMLStatus.read();

        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struXMLOutPut = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        struXMLOutPut.read();
        struXMLOutPut.dwSize = struXMLOutPut.size();
        struXMLOutPut.lpOutBuffer = stringXMLOut.getPointer();
        struXMLOutPut.dwOutBufferSize = stringXMLOut.size();
        struXMLOutPut.lpStatusBuffer = struXMLStatus.getPointer();
        struXMLOutPut.dwStatusSize = struXMLStatus.size();
        struXMLOutPut.write();

        // 执行 ISAPI 命令
        boolean handler = hCNetSDK.NET_DVR_STDXMLConfig(
                userID,
                struXMLInput,
                struXMLOutPut
        );
        if (!handler) {
            logger.error("获取人员数量失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
            res.put("status", false);
            res.put("message", "获取人员数量失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
            return res;
        }

        // 获取返回的 XML 内容（ISAPI 接口返回的是 XML 数据）
        byte[] responseData = struXMLOutPut.lpOutBuffer.getByteArray(0, struXMLOutPut.dwOutBufferSize);
        String responseJson = new String(responseData, StandardCharsets.UTF_8).trim();  // 明确指定编码
//        logger.info("responseJson:{}", responseJson);
        Map<String, Object> resultMap = JSON.parseObject(responseJson, Map.class);

        try {
            Map<String, Object> userInfoCount = (Map<String, Object>) resultMap.get("UserInfoCount");
            logger.info("获取人员数量信息成功：" + resultMap);
            res.put("status", true);
            res.put("message", "获取人员数量信息成功");
            res.put("data", userInfoCount);
        } catch (Exception e) {
            logger.error("获取人员数量失败，错误信息：" + e.getMessage(), e);
            res.put("status", false);
            res.put("message", "获取人员数量失败");
        }
        return res;
    }

    /**
     * 按工号查询单个人员信息
     *
     * @param hCNetSDK SDK 实例
     * @param userID 登录句柄
     * @param condition 查询条件（包含工号）
     * @return 查询结果 Map
     */
    public Map<String, Object> getUserInfo(HCNetSDK hCNetSDK, int userID, Map<String, Object> condition) {
        Map<String, Object> res = new HashMap<>();
        int lHandler = -1;

        try {
            // 1. 启动远程配置
            String url = "POST /ISAPI/AccessControl/UserInfo/Search?format=json";
            lHandler = startRemoteConfig(hCNetSDK, url, userID);
            if (lHandler < 0) {
                int errCode = hCNetSDK.NET_DVR_GetLastError();
                logger.error("NET_DVR_StartRemoteConfig 失败, 错误码: {}", errCode);
                res.put("status", false);
                res.put("message", "NET_DVR_StartRemoteConfig 失败, 错误码: " + errCode);
                return res;
            }

            // 2. 构建输入参数
            String inputJson = generateSearchCondition(condition);
            logger.info("查询人员参数: {}", inputJson);

            byte[] inputBytes = inputJson.getBytes(StandardCharsets.UTF_8);
            HCNetSDK.BYTE_ARRAY inputBuffer = new HCNetSDK.BYTE_ARRAY(inputBytes.length);
            System.arraycopy(inputBytes, 0, inputBuffer.byValue, 0, inputBytes.length);
            inputBuffer.write();

            // 3. 接收输出结果
            HCNetSDK.BYTE_ARRAY outputBuffer = new HCNetSDK.BYTE_ARRAY(10 * 1024);
            IntByReference pInt = new IntByReference(0);

            while (true) {
                int dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(
                        lHandler,
                        inputBuffer.getPointer(),
                        inputBytes.length,
                        outputBuffer.getPointer(),
                        outputBuffer.byValue.length,
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
                outputBuffer.read();
                String resultJson = new String(outputBuffer.byValue, StandardCharsets.UTF_8).trim();
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
                    logger.debug("查询人员信息失败: {}", jsonResult);
                    res.put("status", false);
                    res.put("statusCode", statusCode);
                    res.put("statusString", statusString);
                    res.put("message", "查询人员信息失败");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                    logger.debug("查询人员信息异常: {}", jsonResult);
                    res.put("status", false);
                    res.put("statusCode", statusCode);
                    res.put("statusString", statusString);
                    res.put("message", "查询人员信息异常");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                    if (statusCode != 1) {
                        logger.warn("查询人员信息成功,但是有异常情况: {}", jsonResult);
                        res.put("message", "查询人员信息成功,但是有异常情况");
                    } else {
                        logger.debug("查询人员信息成功: {}", jsonResult);
                        res.put("message", "查询人员信息成功");
                    }
                    Map<String, Object> userInfoSearchResult = extractUserInfo(jsonResult);
                    res.put("status", true);
                    res.put("statusCode", statusCode);
                    res.put("statusString", statusString);
                    res.put("searchID", userInfoSearchResult.get("searchID"));
                    res.put("responseStatusStrg", userInfoSearchResult.get("responseStatusStrg"));
                    res.put("numOfMatches", userInfoSearchResult.get("numOfMatches"));
                    res.put("totalMatches", userInfoSearchResult.get("totalMatches"));
                    res.put("UserInfo", userInfoSearchResult.get("UserInfo"));
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                    logger.info("查询人员信息完成");
                    res.put("status", true);
                    res.put("statusCode", statusCode);
                    res.put("statusString", statusString);
                    res.put("message", "查询人员信息完成");
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
            logger.error("查询人员信息异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "查询人员信息异常");
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
        boolean res = validateRequiredFields(condition, SEARCH_USER_INFO_REQUIRED_FIELDS);
        if (!res) {
            logger.error("缺少必填字段");
            return null;
        }

        // 1. 构造最外层的 UserInfoSearchCond 对象
        Map<String, Object> userInfoSearchCond = new HashMap<>();

        // 2. 处理 searchID（字符串，可能是必填）
        if (condition.containsKey("searchID") && condition.get("searchID") != null) {
            Object searchIdObj = condition.get("searchID");
            if (!(searchIdObj instanceof String)) {
                throw new IllegalArgumentException("字段 searchID 必须为字符串类型");
            }
            userInfoSearchCond.put("searchID", searchIdObj.toString().trim());
        }

        // 3. 处理 searchResultPosition（整数，可选）
        if (condition.containsKey("searchResultPosition") && condition.get("searchResultPosition") != null) {
            Object posObj = condition.get("searchResultPosition");
            if (!(posObj instanceof Integer)) {
                // 如果不是 Integer，但可能是 Long 或其他数字，可以做个转换
                if (posObj instanceof Number) {
                    userInfoSearchCond.put("searchResultPosition", ((Number) posObj).intValue());
                } else {
                    throw new IllegalArgumentException("字段 searchResultPosition 必须为整数类型");
                }
            } else {
                userInfoSearchCond.put("searchResultPosition", posObj);
            }
        }

        // 4. 处理 maxResults（整数，可选）
        if (condition.containsKey("maxResults") && condition.get("maxResults") != null) {
            Object maxResObj = condition.get("maxResults");
            if (!(maxResObj instanceof Integer)) {
                if (maxResObj instanceof Number) {
                    userInfoSearchCond.put("maxResults", ((Number) maxResObj).intValue());
                } else {
                    throw new IllegalArgumentException("字段 maxResults 必须为整数类型");
                }
            } else {
                userInfoSearchCond.put("maxResults", maxResObj);
            }
        }

        // 5. 处理 EmployeeNoList（数组，可选，但每个元素必须是含 employeeNo 的对象）
        if (condition.containsKey("EmployeeNoList") && condition.get("EmployeeNoList") != null) {
            Object empListObj = condition.get("EmployeeNoList");
            if (!(empListObj instanceof List)) {
                throw new IllegalArgumentException("字段 EmployeeNoList 必须为数组格式");
            }

            List<?> rawEmpList = (List<?>) empListObj;
            List<Map<String, Object>> validEmpList = new ArrayList<>();

            for (Object item : rawEmpList) {
                if (!(item instanceof Map)) {
                    throw new IllegalArgumentException("EmployeeNoList 中的每一项必须为对象（Map）");
                }

                Map<?, ?> rawItem = (Map<?, ?>) item;
                if (!rawItem.containsKey("employeeNo") || rawItem.get("employeeNo") == null) {
                    throw new IllegalArgumentException("EmployeeNoList 中的每一项必须包含 employeeNo 字段");
                }

                if (!(rawItem.get("employeeNo") instanceof String)) {
                    throw new IllegalArgumentException("EmployeeNoList 中的 employeeNo 必须为字符串类型");
                }

                // 直接放入合法的项
                validEmpList.add((Map<String, Object>) item);
            }

            userInfoSearchCond.put("EmployeeNoList", validEmpList);
        }

        // 6. 处理 fuzzySearch（模糊查询关键字，字符串，可选）
        if (condition.containsKey("fuzzySearch") && condition.get("fuzzySearch") != null) {
            Object fuzzyObj = condition.get("fuzzySearch");
            if (!(fuzzyObj instanceof String)) {
                throw new IllegalArgumentException("字段 fuzzySearch 必须为字符串类型");
            }
            userInfoSearchCond.put("fuzzySearch", fuzzyObj.toString().trim());
        }

        // 7. 构造最外层结构：将 UserInfoSearchCond 放入一个 Map 中（模拟你可能的外层结构，或者直接返回它）
        Map<String, Object> outerWrapper = new HashMap<>();
        outerWrapper.put("UserInfoSearchCond", userInfoSearchCond);

        // 8. 转为 JSON 字符串
        String jsonResult = new JSONObject(outerWrapper).toString(); // 使用 org.json.JSONObject 或 fastjson 等
        logger.debug("生成的查询条件 JSON: {}", jsonResult);
        return jsonResult;
    }

    /**
     * 提取人员信息字段，防止空指针
     */
    private Map<String, Object> extractUserInfo(JSONObject jsonResult) {
        Map<String, Object> userInfoResult = new HashMap<>();
        JSONObject userInfoSearch = jsonResult.optJSONObject("UserInfoSearch");
        if (userInfoSearch == null) return userInfoResult;

        userInfoResult.put("searchID", userInfoSearch.optString("searchID"));
        userInfoResult.put("responseStatusStrg", userInfoSearch.optString("responseStatusStrg"));
        userInfoResult.put("numOfMatches", userInfoSearch.optInt("numOfMatches", 0));
        userInfoResult.put("totalMatches", userInfoSearch.optInt("totalMatches", 0));

        Object userInfoObject = userInfoSearch.get("UserInfo");
            if (userInfoObject != null) {
                if (userInfoObject instanceof JSONArray) {
                    JSONArray userInfoArray = (JSONArray) userInfoObject;
                    List<Object> UserInfoList = userInfoArray.toList();
                    userInfoResult.put("UserInfo", UserInfoList);
                }
                if (userInfoObject instanceof JSONObject) {
                    JSONObject userInfoJSONObject = (JSONObject) userInfoObject;
                    userInfoResult.put("UserInfo", userInfoJSONObject.toMap());
                }
            } else {
                userInfoResult.put("UserInfo", null);
            }
        return userInfoResult;
    }

    public int startRemoteConfig(HCNetSDK hCNetSDK, String url, int userID) {
        HCNetSDK.BYTE_ARRAY ptrUrl = new HCNetSDK.BYTE_ARRAY(1024);
        System.arraycopy(url.getBytes(), 0, ptrUrl.byValue, 0, url.length());
        ptrUrl.write();
        return hCNetSDK.NET_DVR_StartRemoteConfig(
                userID,
                HCNetSDK.NET_DVR_JSON_CONFIG,
                ptrUrl.getPointer(),
                url.length(),
                null,
                null
        );
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

    /**
     * 新增人员
     *
     * @param userID    登录句柄
     * @param condition 人员信息对象（包含所有字段，参考 SDK JSON）
     * @throws JSONException
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
    public Map<String, Object> addUserInfo(HCNetSDK hCNetSDK, int userID, Map<String, Object> condition)
            throws JSONException {
        Map<String, Object> res = new HashMap<>();
        int lHandler = -1;
        try {
            // 1. 启动远程配置
            String url = "PUT /ISAPI/AccessControl/UserInfo/SetUp?format=json";
            lHandler = startRemoteConfig(hCNetSDK, url, userID);

            if (lHandler < 0) {
                int errCode = hCNetSDK.NET_DVR_GetLastError();
                logger.error("NET_DVR_StartRemoteConfig 失败, 错误码: {}", errCode);
                res.put("status", false);
                res.put("message", "NET_DVR_StartRemoteConfig 失败, 错误码: " + errCode);
                return res;
            }

            // 2. 构建输入参数
            String inputJson = generateAddCondition(condition);
            logger.info("新增人员参数: {}", inputJson);

            byte[] byInbuffer = inputJson.getBytes(StandardCharsets.UTF_8);
            int iInBufLen = byInbuffer.length;

            HCNetSDK.BYTE_ARRAY ptrInBuffer = new HCNetSDK.BYTE_ARRAY(iInBufLen);
            ptrInBuffer.read();
            System.arraycopy(byInbuffer, 0, ptrInBuffer.byValue, 0, iInBufLen);
            ptrInBuffer.write();

            // 3. 接收输出结果
            HCNetSDK.BYTE_ARRAY ptroutputBuffer = new HCNetSDK.BYTE_ARRAY(10 * 1024);
            IntByReference pInt = new IntByReference(0);

            while (true) {
                int dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(
                        lHandler,
                        ptrInBuffer.getPointer(),
                        iInBufLen,
                        ptroutputBuffer.getPointer(),
                        ptroutputBuffer.byValue.length,
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
                ptroutputBuffer.read();
                String resultJson = new String(ptroutputBuffer.byValue, StandardCharsets.UTF_8).trim();
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
                    logger.debug("添加人员失败: {}", jsonResult);
                    res.put("status", false);
                    res.put("statusCode", jsonResult.optInt("statusCode", -1));
                    res.put("message", "添加人员失败");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION) {
                    logger.debug("添加人员异常: {}", jsonResult);
                    res.put("status", false);
                    res.put("message", "添加人员异常");
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS) {
                    if (statusCode != 1) {
                        logger.warn("添加人员信息成功,但是有异常情况: {}", jsonResult);
                        res.put("message", "添加人员信息成功,但是有异常情况");
                    } else {
                        logger.debug("添加人员信息成功: {}", jsonResult);
                        res.put("message", "添加人员信息成功");
                    }
                    res.put("status", true);
                    res.put("statusCode", jsonResult.optInt("statusCode", 1));
                    res.put("statusString", jsonResult.optString("statusString", ""));
                    res.put("subStatusCode", jsonResult.optString("subStatusCode", ""));
                    res.put("errorCode", jsonResult.optInt("errorCode", 1));
                    res.put("errorMsg", jsonResult.optString("errorMsg", ""));
                    break;
                } else if (dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FINISH) {
                    logger.info("添加人员信息完成");
                    res.put("status", true);
                    res.put("message", "添加人员信息完成");
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
            logger.error("添加人员信息异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "添加人员信息异常");
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
    private String generateAddCondition(Map<String, Object> condition) throws IOException {
        boolean res = validateRequiredFields(condition, ADD_USER_INFO_REQUIRED_FIELDS);
        if (!res) {
            logger.error("缺少必填字段");
            return null;
        }

        // 1. 构造最外层结构
        Map<String, Object> outerMap = new HashMap<>();

        // 2. 构造 UserInfo 内部对象
        Map<String, Object> userInfoMap = new HashMap<>();

        // ===== 基础字段 =====
        if (condition.containsKey("employeeNo") && condition.get("employeeNo") != null) {
            Object empNoObj = condition.get("employeeNo");
            if (!(empNoObj instanceof String)) {
                throw new IllegalArgumentException("字段 employeeNo 必须为字符串类型");
            }
            userInfoMap.put("employeeNo", empNoObj.toString().trim());
        }

        if (condition.containsKey("deleteUser") && condition.get("deleteUser") != null) {
            Object delUserObj = condition.get("deleteUser");
            if (!(delUserObj instanceof Boolean)) {
                throw new IllegalArgumentException("字段 deleteUser 必须为布尔类型");
            }
            userInfoMap.put("deleteUser", delUserObj);
        }

        if (condition.containsKey("name") && condition.get("name") != null) {
            Object nameObj = condition.get("name");
            if (!(nameObj instanceof String)) {
                throw new IllegalArgumentException("字段 name 必须为字符串类型");
            }
            userInfoMap.put("name", nameObj.toString().trim());
        }

        if (condition.containsKey("userType") && condition.get("userType") != null) {
            Object userTypeObj = condition.get("userType");
            if (!(userTypeObj instanceof String)) {
                throw new IllegalArgumentException("字段 userType 必须为字符串类型");
            }
            userInfoMap.put("userType", userTypeObj.toString().trim());
        }

        if (condition.containsKey("closeDelayEnabled") && condition.get("closeDelayEnabled") != null) {
            Object closeDelayObj = condition.get("closeDelayEnabled");
            if (!(closeDelayObj instanceof Boolean)) {
                throw new IllegalArgumentException("字段 closeDelayEnabled 必须为布尔类型");
            }
            userInfoMap.put("closeDelayEnabled", closeDelayObj);
        }

        if (condition.containsKey("belongGroup") && condition.get("belongGroup") != null) {
            Object groupObj = condition.get("belongGroup");
            if (!(groupObj instanceof String)) {
                throw new IllegalArgumentException("字段 belongGroup 必须为字符串类型");
            }
            userInfoMap.put("belongGroup", groupObj.toString().trim());
        }

        if (condition.containsKey("password") && condition.get("password") != null) {
            Object pwdObj = condition.get("password");
            if (!(pwdObj instanceof String)) {
                throw new IllegalArgumentException("字段 password 必须为字符串类型");
            }
            userInfoMap.put("password", pwdObj.toString().trim());
        }

        if (condition.containsKey("gender") && condition.get("gender") != null) {
            Object genderObj = condition.get("gender");
            if (!(genderObj instanceof String)) {
                throw new IllegalArgumentException("字段 gender 必须为字符串类型");
            }
            userInfoMap.put("gender", genderObj.toString().trim());
        }

        // ===== Valid（有效期）字段 =====
        if (condition.containsKey("Valid") && condition.get("Valid") != null) {
            Object validObj = condition.get("Valid");
            if (!(validObj instanceof Map)) {
                throw new IllegalArgumentException("字段 Valid 必须为对象（Map）类型");
            }
            userInfoMap.put("Valid", validObj);
        }

        // ===== 门权限相关 =====
        if (condition.containsKey("doorRight") && condition.get("doorRight") != null) {
            Object doorRightObj = condition.get("doorRight");
            if (!(doorRightObj instanceof String)) {
                throw new IllegalArgumentException("字段 doorRight 必须为字符串类型");
            }
            userInfoMap.put("doorRight", doorRightObj.toString().trim());
        }

        if (condition.containsKey("RightPlan") && condition.get("RightPlan") != null) {
            Object rightPlanObj = condition.get("RightPlan");
            if (!(rightPlanObj instanceof List)) {
                throw new IllegalArgumentException("字段 RightPlan 必须为数组（List）类型");
            }
            List<?> rightPlanList = (List<?>) rightPlanObj;
            if (!rightPlanList.isEmpty()) {  // 只有当 RightPlan 非空时才放入
                userInfoMap.put("RightPlan", rightPlanObj);
            }
        }

        // ===== 其它数值型字段 =====
        if (condition.containsKey("maxOpenDoorTime") && condition.get("maxOpenDoorTime") != null) {
            Object maxOpObj = condition.get("maxOpenDoorTime");
            if (!(maxOpObj instanceof Number)) {
                throw new IllegalArgumentException("字段 maxOpenDoorTime 必须为数字类型");
            }
            userInfoMap.put("maxOpenDoorTime", ((Number) maxOpObj).intValue());
        }

        if (condition.containsKey("openDoorTime") && condition.get("openDoorTime") != null) {
            Object openDoorObj = condition.get("openDoorTime");
            if (!(openDoorObj instanceof Number)) {
                throw new IllegalArgumentException("字段 openDoorTime 必须为数字类型");
            }
            userInfoMap.put("openDoorTime", ((Number) openDoorObj).intValue());
        }

        if (condition.containsKey("roomNumber") && condition.get("roomNumber") != null) {
            Object roomObj = condition.get("roomNumber");
            if (!(roomObj instanceof Number)) {
                throw new IllegalArgumentException("字段 roomNumber 必须为数字类型");
            }
            userInfoMap.put("roomNumber", ((Number) roomObj).intValue());
        }

        if (condition.containsKey("floorNumber") && condition.get("floorNumber") != null) {
            Object floorObj = condition.get("floorNumber");
            if (!(floorObj instanceof Number)) {
                throw new IllegalArgumentException("字段 floorNumber 必须为数字类型");
            }
            userInfoMap.put("floorNumber", ((Number) floorObj).intValue());
        }

        // ===== 权限与UI相关 =====
        if (condition.containsKey("doubleLockRight") && condition.get("doubleLockRight") != null) {
            Object doubleLockObj = condition.get("doubleLockRight");
            if (!(doubleLockObj instanceof Boolean)) {
                throw new IllegalArgumentException("字段 doubleLockRight 必须为布尔类型");
            }
            userInfoMap.put("doubleLockRight", doubleLockObj);
        }

        if (condition.containsKey("localUIRight") && condition.get("localUIRight") != null) {
            Object localUiObj = condition.get("localUIRight");
            if (!(localUiObj instanceof Boolean)) {
                throw new IllegalArgumentException("字段 localUIRight 必须为布尔类型");
            }
            userInfoMap.put("localUIRight", localUiObj);
        }

        if (condition.containsKey("userVerifyMode") && condition.get("userVerifyMode") != null) {
            Object verifyModeObj = condition.get("userVerifyMode");
            if (!(verifyModeObj instanceof String)) {
                throw new IllegalArgumentException("字段 userVerifyMode 必须为字符串类型");
            }
            userInfoMap.put("userVerifyMode", verifyModeObj.toString().trim());
        }

        if (condition.containsKey("checkUser") && condition.get("checkUser") != null) {
            Object checkUserObj = condition.get("checkUser");
            if (!(checkUserObj instanceof Boolean)) {
                throw new IllegalArgumentException("字段 checkUser 必须为布尔类型");
            }
            userInfoMap.put("checkUser", checkUserObj);
        }

        // ===== 扩展信息 =====
        if (condition.containsKey("PersonInfoExtends") && condition.get("PersonInfoExtends") != null) {
            Object extObj = condition.get("PersonInfoExtends");
            if (!(extObj instanceof List)) {
                throw new IllegalArgumentException("字段 PersonInfoExtends 必须为数组（List）类型");
            }
            List<?> personInfoExtendsList = (List<?>) extObj;
            if (!personInfoExtendsList.isEmpty()) {  // ✅ 只有当列表非空时才放入 userInfoMap
                userInfoMap.put("PersonInfoExtends", extObj);
            }
        }

        // ===== 其它字符串字段 =====
        if (condition.containsKey("dynamicCode") && condition.get("dynamicCode") != null) {
            Object dynCodeObj = condition.get("dynamicCode");
            if (!(dynCodeObj instanceof String)) {
                throw new IllegalArgumentException("字段 dynamicCode 必须为字符串类型");
            }
            userInfoMap.put("dynamicCode", dynCodeObj.toString().trim());
        }

        if (condition.containsKey("groupName") && condition.get("groupName") != null) {
            Object groupObj = condition.get("groupName");
            if (!(groupObj instanceof String)) {
                throw new IllegalArgumentException("字段 groupName 必须为字符串类型");
            }
            userInfoMap.put("groupName", groupObj.toString().trim());
        }

        // ===== 可选：呼叫号码列表 =====
        if (condition.containsKey("callNumbers") && condition.get("callNumbers") != null) {
            Object callNumObj = condition.get("callNumbers");
            if (!(callNumObj instanceof List)) {
                throw new IllegalArgumentException("字段 callNumbers 必须为数组（List）类型");
            }
            userInfoMap.put("callNumbers", callNumObj);
        }

        if (condition.containsKey("floorNumbers") && condition.get("floorNumbers") != null) {
            Object floorNumObj = condition.get("floorNumbers");
            if (!(floorNumObj instanceof List)) {
                throw new IllegalArgumentException("字段 floorNumbers 必须为数组（List）类型");
            }
            userInfoMap.put("floorNumbers", floorNumObj);
        }

        // 3. 组装最外层
        outerMap.put("UserInfo", userInfoMap);

        // 4. 转为 JSON 字符串
        String jsonResult = new JSONObject(outerMap).toString(); // 使用 org.json 或 fastjson
        logger.debug("生成的人员信息 JSON: {}", jsonResult);
        return jsonResult;
    }

    /**
     * 删除人员信息（含进度监控）
     *
     *
     * @param userID     设备登录句柄（通过 {@code NET_DVR_Login_V40} 获取）
     * @param condition
     */
    public Map<String, Object> deleteUserInfo(HCNetSDK hCNetSDK, int userID, Map<String, Object> condition) {
        Map<String, Object> res = new HashMap<>();
        try {
//        // 删除所有人员
//        String deleteUserjson = "{\n" +
//                "\t\"UserInfoDetail\": {\t\n" +
//                "\t\t\"mode\":  \"all\",\t\n" +
//                "\t\t\"EmployeeNoList\": [\t\n" +
//                "\t\t]\n" +
//                "\n" +
//                "\t}\n" +
//                "}";

            // url
            String url = "PUT /ISAPI/AccessControl/UserInfoDetail/Delete?format=json";
            // 构建输入参数
            String inputJson = generateDeleteCondition(condition);
            logger.info("删除人员信息参数：{}", inputJson);

            if (inputJson == null) {
                res.put("status", false);
                res.put("message", "删除人员的输入参数为空");
                return res;
            }

            TransIsapi obj = new TransIsapi();
            String resultJson = obj.put_isapi(hCNetSDK, userID, url, inputJson);
            logger.info("删除人员信息结果：{}",resultJson);

            // 解析JSON结果
            JSONObject jsonResult = new JSONObject(resultJson);

            res.put("status", true);
            res.put("message", "删除人员成功");
            res.put("StatusCode", jsonResult.optInt("subStatusCode", 1));
            res.put("statusString", jsonResult.optString("statusString", ""));
            res.put("subStatusCode", jsonResult.optString("subStatusCode", ""));

            // 获取删除进度
//            while (true) {
//                String getDeleteProcessUrl = "GET /ISAPI/AccessControl/UserInfoDetail/DeleteProcess?format=json";
//
//                TransIsapi obj2 = new TransIsapi();
//                String deleteResult = obj2.get_isapi(hCNetSDK, userID, getDeleteProcessUrl);
//                JSONObject jsonObject = new JSONObject(deleteResult);
//                JSONObject jsonObject1 = jsonObject.getJSONObject("UserInfoDetailDeleteProcess");
//                String process = jsonObject1.getString("status");
//                logger.info("删除人员进度：{}", process);
//                if (process.equals("processing")) {
//                    logger.info("正在删除：{}", process);
//                    continue;
//                } else if (process.equals("success")) {
//                    logger.info("删除成功：{}", process);
//                    res.put("status", true);
//                    res.put("message", "删除成功");
//                    break;
//                } else if (process.equals("failed")) {
//                    logger.info("删除失败：{}", process);
//                    res.put("status", true);
//                    res.put("message", "删除失败");
//                    break;
//                }
//            }
        } catch (JSONException e) {
            logger.error("JSON解析异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "JSON解析异常");
        } catch (Exception e) {
            logger.error("删除人员信息异常：{}", e.getMessage());
            res.put("status", false);
            res.put("message", "删除人员信息异常");
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
        boolean res = validateRequiredFields(condition, DELETE_USER_INFO_REQUIRED_FIELDS);
        if (!res) {
            logger.error("缺少必填字段");
            return null;
        }

        // 1. 最外层容器
        Map<String, Object> outerMap = new HashMap<>();

        // 2. UserInfoDetail 对象
        Map<String, Object> userInfoDetailMap = new HashMap<>();

        // ===== mode 字段（可能是必填）=====
        if (condition.containsKey("mode") && condition.get("mode") != null) {
            Object modeObj = condition.get("mode");
            if (!(modeObj instanceof String)) {
                throw new IllegalArgumentException("字段 mode 必须为字符串类型");
            }
            userInfoDetailMap.put("mode", modeObj.toString().trim());
        }

        // ===== EmployeeNoList（数组，每个元素是含 employeeNo 的对象）=====
        if (condition.containsKey("EmployeeNoList") && condition.get("EmployeeNoList") != null) {
            Object empListObj = condition.get("EmployeeNoList");
            if (!(empListObj instanceof List)) {
                throw new IllegalArgumentException("字段 EmployeeNoList 必须为数组（List）类型");
            }

            List<?> rawEmpList = (List<?>) empListObj;
            List<Map<String, Object>> validEmpList = new ArrayList<>();

            for (Object item : rawEmpList) {
                if (!(item instanceof Map)) {
                    throw new IllegalArgumentException("EmployeeNoList 中的每一项必须为对象（Map）");
                }

                Map<?, ?> empItem = (Map<?, ?>) item;
                if (!empItem.containsKey("employeeNo") || empItem.get("employeeNo") == null) {
                    throw new IllegalArgumentException("EmployeeNoList 中的每一项必须包含 employeeNo 字段");
                }

                if (!(empItem.get("employeeNo") instanceof String)) {
                    throw new IllegalArgumentException("EmployeeNoList 中的 employeeNo 必须为字符串类型");
                }

                // 直接放入合法的项
                validEmpList.add((Map<String, Object>) item);
            }

            userInfoDetailMap.put("EmployeeNoList", validEmpList);
        }

        // ===== operateType（操作类型，字符串，枚举）=====
        if (condition.containsKey("operateType") && condition.get("operateType") != null) {
            Object opTypeObj = condition.get("operateType");
            if (!(opTypeObj instanceof String)) {
                throw new IllegalArgumentException("字段 operateType 必须为字符串类型");
            }
            userInfoDetailMap.put("operateType", opTypeObj.toString().trim());
        }

        // ===== terminalNoList（终端ID列表，数字数组）=====
        if (condition.containsKey("terminalNoList") && condition.get("terminalNoList") != null) {
            Object termListObj = condition.get("terminalNoList");
            if (!(termListObj instanceof List)) {
                throw new IllegalArgumentException("字段 terminalNoList 必须为数组（List）类型");
            }

            List<?> rawTermList = (List<?>) termListObj;
            List<Integer> validTermList = new ArrayList<>();

            for (Object item : rawTermList) {
                if (!(item instanceof Number)) {
                    throw new IllegalArgumentException("terminalNoList 中的每一项必须为数字类型（如 Integer）");
                }
                validTermList.add(((Number) item).intValue());
            }

            userInfoDetailMap.put("terminalNoList", validTermList);
        }

        // ===== orgNoList（组织ID列表，数字数组）=====
        if (condition.containsKey("orgNoList") && condition.get("orgNoList") != null) {
            Object orgListObj = condition.get("orgNoList");
            if (!(orgListObj instanceof List)) {
                throw new IllegalArgumentException("字段 orgNoList 必须为数组（List）类型");
            }

            List<?> rawOrgList = (List<?>) orgListObj;
            List<Integer> validOrgList = new ArrayList<>();

            for (Object item : rawOrgList) {
                if (!(item instanceof Number)) {
                    throw new IllegalArgumentException("orgNoList 中的每一项必须为数字类型（如 Integer）");
                }
                validOrgList.add(((Number) item).intValue());
            }

            userInfoDetailMap.put("orgNoList", validOrgList);
        }

        // 3. 组装最外层结构
        outerMap.put("UserInfoDetail", userInfoDetailMap);

        // 4. 转为 JSON 字符串
        String jsonResult = new JSONObject(outerMap).toString(); // 使用 org.json.JSONObject
        logger.debug("生成的 UserInfoDetail 请求 JSON: {}", jsonResult);
        return jsonResult;
    }

    /**
     * 人员计划模板配置
     *
     * @param userID              用户登录句柄
     * @param iPlanTemplateNumber 计划模板编号，从1开始，最大值从门禁能力集获取
     */
    public Map<String, Object> setPeopleTemplate(HCNetSDK hCNetSDK, int userID, int iPlanTemplateNumber) {
        //新增res作为返回值
        Map<String, Object> res = new HashMap<>();
        // 设置卡权限计划模板参数
        // lpInBuffer对应结构体
        HCNetSDK.NET_DVR_PLAN_TEMPLATE_COND struPlanCond = new HCNetSDK.NET_DVR_PLAN_TEMPLATE_COND();
        struPlanCond.dwSize = struPlanCond.size(); // 结构体大小
        struPlanCond.dwPlanTemplateNumber = iPlanTemplateNumber; // 计划模板编号，从1开始，最大值从门禁能力集获取
        struPlanCond.wLocalControllerID = 0; // 就地控制器序号[1,64]，0表示门禁主机
        struPlanCond.write();

        // lpOutBuffer对应结构体
        HCNetSDK.NET_DVR_PLAN_TEMPLATE struPlanTemCfg = new HCNetSDK.NET_DVR_PLAN_TEMPLATE();
        struPlanTemCfg.dwSize = struPlanTemCfg.size(); // 结构体大小
        struPlanTemCfg.byEnable = 1; // 是否使能：0- 否，1- 是
        // struPlanTemCfg.byRes1 = 0; // 保留，置为0
        // struPlanTemCfg.byTemplateName = "1".getBytes(); // 计划模板名称
        struPlanTemCfg.dwWeekPlanNo = 2; // 周计划编号，0表示无效
        struPlanTemCfg.dwHolidayGroupNo[0] = 0; // 假日组编号，按值表示，采用紧凑型排列，中间遇到0则后续无效
        // struPlanTemCfg.byRes2 = 0; // 保留，置为0

        // 设置模板名称
        byte[] byTemplateName;
        try {
            byTemplateName = "CardTemplatePlan_2".getBytes("GBK");
            for (int i = 0; i < HCNetSDK.NAME_LEN; i++) {
                struPlanTemCfg.byTemplateName[i] = 0;
            }
            System.arraycopy(byTemplateName, 0, struPlanTemCfg.byTemplateName, 0, byTemplateName.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        struPlanTemCfg.write();

        // 发送卡权限计划模板配置到设备
        IntByReference pInt = new IntByReference(0);
        Pointer lpStatusList = pInt.getPointer();

        // 1.卡权限计划模板参数配置
        // NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50 获取卡权限计划模板参数，dwCount设为1
        // TRUE表示成功，但不代表每一个配置都成功，哪一个成功，对应查看lpStatusList[n]值；FALSE表示全部失败。接口返回失败请调用NET_DVR_GetLastError获取错误码，通过错误码判断出错原因。
        boolean res1 = hCNetSDK.NET_DVR_SetDeviceConfig(
                userID, // lUserID [in] NET_DVR_Login_V40等登录接口的返回值
                HCNetSDK.NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50, // dwCommand [in] 设备配置命令，参见配置命令
                1, // dwCount [in] 一次要获取的配置个数，0和1都表示1个监控点信息，2表示2个监控点信息，以此递增，最大64个
                struPlanCond.getPointer(),//lpInBuffer  [in] 配置条件缓冲区指针
                struPlanCond.size(), // dwInBufferSize  [in] 配置条件缓冲区长度
                lpStatusList,
                // lpStatusList  [out] 错误信息列表，和要查询的监控点一一对应，例如lpStatusList[2]就对应lpInBuffer[2]，由用户分配内存，每个错误信息为4个字节(1个32位无符号整数值)，参数值：0- 成功，大于0- 失败
                struPlanTemCfg.getPointer(),
                // lpOutBuffer [out] 设备返回的参数内容，和要查询的监控点一一对应。如果某个监控点对应的lpStatusList信息为大于1的值，对应lpOutBuffer的内容就是无效的
                struPlanTemCfg.size() // dwOutBufferSize [in] dwCount个返回结果的总大小
        );

        if (!res1) {
            logger.info("获取卡权限计划模板参数失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
            res.put("status", "error");
            res.put("stage", "PlanTemplate");
            res.put("errorCode", hCNetSDK.NET_DVR_GetLastError());
            res.put("message", "设置卡权限计划模板参数失败");
            return res;
        }
        logger.info("获取卡权限计划模板参数成功！");

        // 2.卡权限周计划参数配置
        HCNetSDK.NET_DVR_WEEK_PLAN_COND struWeekPlanCond = new HCNetSDK.NET_DVR_WEEK_PLAN_COND();
        struWeekPlanCond.dwSize = struWeekPlanCond.size();
        struWeekPlanCond.dwWeekPlanNumber = 2;
        struWeekPlanCond.wLocalControllerID = 0;
        HCNetSDK.NET_DVR_WEEK_PLAN_CFG struWeekPlanCfg = new HCNetSDK.NET_DVR_WEEK_PLAN_CFG();
        struWeekPlanCond.write();
        struWeekPlanCfg.write();

        Pointer lpCond = struWeekPlanCond.getPointer();
        Pointer lpInbuferCfg = struWeekPlanCfg.getPointer();


        boolean res2 = hCNetSDK.NET_DVR_GetDeviceConfig(
                userID,
                HCNetSDK.NET_DVR_GET_CARD_RIGHT_WEEK_PLAN_V50, // 获取卡权限周计划参数，dwCount设为1
                1,
                lpCond,
                struWeekPlanCond.size(),
                lpStatusList,
                lpInbuferCfg,
                struWeekPlanCfg.size()
        );
        if (!res2) {
            logger.info("获取卡权限周计划参数失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
            res.put("status", "error");
            res.put("stage", "GetWeekPlan");
            res.put("errorCode", hCNetSDK.NET_DVR_GetLastError());
            res.put("message", "获取卡权限周计划参数失败");
            return res;
        }
        logger.info("获取卡权限周计划参数成功！");


        struWeekPlanCfg.read();  //从 SDK 的底层内存中 读取 设备返回的数据到 Java 结构体中

        struWeekPlanCfg.byEnable = 1; // 是否使能：0- 否，1- 是

        // 初始化时间段配置：七天，每天最多8个时间段
        //设置卡权限“周计划”的时间段，使设备知道每天什么时候允许刷卡、什么时候禁止刷卡。
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].byEnable = 0; // 初始化时间段为禁用
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.byHour = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.byMinute = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.bySecond = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.byHour = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.byMinute = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.bySecond = 0;
            }
        }

        // 示例：一周7天，每天设置1个时间段（21:00-23:00）
        for (int i = 0; i < 7; i++) {
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].byEnable = 1;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struBeginTime.byHour = 21;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struBeginTime.byMinute = 0;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struBeginTime.bySecond = 0;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struEndTime.byHour = 23;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struEndTime.byMinute = 0;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struEndTime.bySecond = 0;
        }

        // 设置卡权限周计划参数
        struWeekPlanCfg.write();
        if (false == hCNetSDK.NET_DVR_SetDeviceConfig(
                userID,
                HCNetSDK.NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50,
                1,
                lpCond,
                struWeekPlanCond.size(),
                lpStatusList,
                lpInbuferCfg,
                struWeekPlanCfg.size())) {
            logger.info("NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
            res.put("status", "error");
            res.put("stage", "SetWeekPlan");
            res.put("errorCode", hCNetSDK.NET_DVR_GetLastError());
            res.put("message", "设置卡权限周计划参数失败");
            return res;
        } else {
            logger.info("NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50成功！");
            res.put("status", "success");
            res.put("stage", "SetWeekPlan");
            res.put("message", "设置卡权限周计划参数成功");
        }
        res.put("status", "success");
        res.put("planTemplateNumber", iPlanTemplateNumber);
        res.put("message", "人员计划模板配置成功");
        return res;

//        // 配置假日组
//        HCNetSDK.NET_DVR_HOLIDAY_GROUP struHolidayGroup = new HCNetSDK.NET_DVR_HOLIDAY_GROUP();
//        struHolidayGroup.dwSize = struHolidayGroup.size();
//        struHolidayGroup.byHolidayGroupNo = 1; // 假日组编号
//        struHolidayGroup.dwHolidayNum = 1; // 假日数量
//        struHolidayGroup.write();
//
//        if (false == hCNetSDK.NET_DVR_SetDeviceConfig(userID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50, 1, struHolidayGroup.getPointer(), struHolidayGroup.size(), lpStatusList, struHolidayGroup.getPointer(), struHolidayGroup.size())) {
//            logger.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
//        } else {
//            logger.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50成功！");
//        }
//
//        // 配置假日计划
//        HCNetSDK.NET_DVR_HOLIDAY_PLAN struHolidayPlan = new HCNetSDK.NET_DVR_HOLIDAY_PLAN();
//        struHolidayPlan.dwSize = struHolidayPlan.size();
//        struHolidayPlan.byHolidayGroupNo = 1; // 假日组编号
//        struHolidayPlan.dwHolidayNo = 1; // 假日计划编号
//        struHolidayPlan.dwHolidayBeginTime = 20230101; // 假日开始时间（例如：2023年1月1日）
//        struHolidayPlan.dwHolidayEndTime = 20230105; // 假日结束时间（例如：2023年1月5日）
//        struHolidayPlan.write();
//
//        if (false == hCNetSDK.NET_DVR_SetDeviceConfig(userID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50, 1, struHolidayPlan.getPointer(), struHolidayPlan.size(), lpStatusList, struHolidayPlan.getPointer(), struHolidayPlan.size())) {
//            logger.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
//        } else {
//            logger.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50成功！");
//        }
    }

    /**
     * 人员计划模板配置
     *
     * @param userID              用户登录句柄
     * @param iPlanTemplateNumber 计划模板编号，从1开始，最大值从门禁能力集获取
     */
    public Map<String, Object> setCardTemplate(HCNetSDK hCNetSDK, int userID, int iPlanTemplateNumber) {
        Map<String, Object> res = new HashMap<>();
        // 设置卡权限计划模板参数
        HCNetSDK.NET_DVR_PLAN_TEMPLATE_COND struPlanCond = new HCNetSDK.NET_DVR_PLAN_TEMPLATE_COND();
        struPlanCond.dwSize = struPlanCond.size();
        struPlanCond.dwPlanTemplateNumber = iPlanTemplateNumber; // 计划模板编号，从1开始，最大值从门禁能力集获取
        struPlanCond.wLocalControllerID = 0; // 就地控制器序号[1,64]，0表示门禁主机
        struPlanCond.write();

        HCNetSDK.NET_DVR_PLAN_TEMPLATE struPlanTemCfg = new HCNetSDK.NET_DVR_PLAN_TEMPLATE();
        struPlanTemCfg.dwSize = struPlanTemCfg.size();
        struPlanTemCfg.byEnable = 1; // 是否使能：0- 否，1- 是
        struPlanTemCfg.dwWeekPlanNo = 2; // 周计划编号，0表示无效
        struPlanTemCfg.dwHolidayGroupNo[0] = 0; // 假日组编号，按值表示，采用紧凑型排列，中间遇到0则后续无效

        // 设置模板名称
        byte[] byTemplateName;
        try {
            byTemplateName = "CardTemplatePlan_2".getBytes("GBK");
            for (int i = 0; i < HCNetSDK.NAME_LEN; i++) {
                struPlanTemCfg.byTemplateName[i] = 0;
            }
            System.arraycopy(byTemplateName, 0, struPlanTemCfg.byTemplateName, 0, byTemplateName.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        struPlanTemCfg.write();

        // 发送卡权限计划模板配置到设备
        IntByReference pInt = new IntByReference(0);
        Pointer lpStatusList = pInt.getPointer();
        if (false == hCNetSDK.NET_DVR_SetDeviceConfig(userID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50, 1,
                struPlanCond.getPointer(), struPlanCond.size(), lpStatusList, struPlanTemCfg.getPointer(),
                struPlanTemCfg.size())) {
            logger.info(
                    "NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
            res.put("status", "error");
            res.put("stage", "SetWeekPlan");
            res.put("errorCode", hCNetSDK.NET_DVR_GetLastError());
            res.put("message", "发送卡权限计划模板配置失败");
            return res;
        }
        logger.info("NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50成功！");

        // 获取卡权限周计划参数
        HCNetSDK.NET_DVR_WEEK_PLAN_COND struWeekPlanCond = new HCNetSDK.NET_DVR_WEEK_PLAN_COND();
        struWeekPlanCond.dwSize = struWeekPlanCond.size();
        struWeekPlanCond.dwWeekPlanNumber = 2;
        struWeekPlanCond.wLocalControllerID = 0;
        HCNetSDK.NET_DVR_WEEK_PLAN_CFG struWeekPlanCfg = new HCNetSDK.NET_DVR_WEEK_PLAN_CFG();
        struWeekPlanCond.write();
        struWeekPlanCfg.write();

        Pointer lpCond = struWeekPlanCond.getPointer();
        Pointer lpInbuferCfg = struWeekPlanCfg.getPointer();
        if (false == hCNetSDK.NET_DVR_GetDeviceConfig(userID, HCNetSDK.NET_DVR_GET_CARD_RIGHT_WEEK_PLAN_V50, 1, lpCond,
                struWeekPlanCond.size(), lpStatusList, lpInbuferCfg, struWeekPlanCfg.size())) {
            logger.info("NET_DVR_GET_CARD_RIGHT_WEEK_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
            res.put("status", "error");
            res.put("stage", "SetWeekPlan");
            res.put("errorCode", hCNetSDK.NET_DVR_GetLastError());
            res.put("message", "获取卡权限周计划参数失败");
            return res;
        }
        struWeekPlanCfg.read();

        struWeekPlanCfg.byEnable = 1; // 是否使能：0- 否，1- 是

        // 初始化时间段配置：七天，每天最多8个时间段
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].byEnable = 0; // 初始化时间段为禁用
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.byHour = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.byMinute = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.bySecond = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.byHour = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.byMinute = 0;
                struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.bySecond = 0;
            }
        }

        // 示例：一周7天，每天设置1个时间段（21:00-23:00）
        for (int i = 0; i < 7; i++) {
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].byEnable = 1;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struBeginTime.byHour = 21;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struBeginTime.byMinute = 0;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struBeginTime.bySecond = 0;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struEndTime.byHour = 23;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struEndTime.byMinute = 0;
            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[0].struTimeSegment.struEndTime.bySecond = 0;
        }

        // 设置卡权限周计划参数
        struWeekPlanCfg.write();
        if (false == hCNetSDK.NET_DVR_SetDeviceConfig(
                userID,
                HCNetSDK.NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50,
                1, lpCond,
                struWeekPlanCond.size(),
                lpStatusList, lpInbuferCfg,
                struWeekPlanCfg.size())
        ) {
            logger.info("NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
            res.put("status", "error");
            res.put("stage", "SetWeekPlan");
            res.put("errorCode", hCNetSDK.NET_DVR_GetLastError());
            res.put("message", "设置卡权限周计划参数失败");
        } else {
            logger.info("NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50成功！");
        }
        res.put("status", "success");
        res.put("stage", "SetWeekPlan");
        res.put("message", "人员计划模板配置成功（以卡为中心）");
        return res;

//        // 配置假日组
//        HCNetSDK.NET_DVR_HOLIDAY_GROUP struHolidayGroup = new HCNetSDK.NET_DVR_HOLIDAY_GROUP();
//        struHolidayGroup.dwSize = struHolidayGroup.size();
//        struHolidayGroup.byHolidayGroupNo = 1; // 假日组编号
//        struHolidayGroup.dwHolidayNum = 1; // 假日数量
//        struHolidayGroup.write();
//
//        if (false == hCNetSDK.NET_DVR_SetDeviceConfig(userID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50, 1, struHolidayGroup.getPointer(), struHolidayGroup.size(), lpStatusList, struHolidayGroup.getPointer(), struHolidayGroup.size())) {
//            logger.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
//        } else {
//            logger.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50成功！");
//        }
//
//        // 配置假日计划
//        HCNetSDK.NET_DVR_HOLIDAY_PLAN struHolidayPlan = new HCNetSDK.NET_DVR_HOLIDAY_PLAN();
//        struHolidayPlan.dwSize = struHolidayPlan.size();
//        struHolidayPlan.byHolidayGroupNo = 1; // 假日组编号
//        struHolidayPlan.dwHolidayNo = 1; // 假日计划编号
//        struHolidayPlan.dwHolidayBeginTime = 20230101; // 假日开始时间（例如：2023年1月1日）
//        struHolidayPlan.dwHolidayEndTime = 20230105; // 假日结束时间（例如：2023年1月5日）
//        struHolidayPlan.write();
//
//        if (false == hCNetSDK.NET_DVR_SetDeviceConfig(userID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50, 1, struHolidayPlan.getPointer(), struHolidayPlan.size(), lpStatusList, struHolidayPlan.getPointer(), struHolidayPlan.size())) {
//            logger.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
//        } else {
//            logger.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50成功！");
//        }
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

}
