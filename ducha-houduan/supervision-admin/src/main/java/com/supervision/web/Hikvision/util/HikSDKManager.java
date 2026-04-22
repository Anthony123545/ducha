package com.supervision.web.Hikvision.util;


import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HikSDKManager
 *
 * 统一管理多个类型的海康 SDK（如：人行门禁、车牌识别、车辆道闸）
 * 支持动态加载不同目录下的 HCNetSDK 库文件。
 *
 * 设计目标：
 *  1. 避免多模块重复加载 SDK 导致冲突；
 *  2. 每个模块（如 peopleDoor、carCamera、carDoor）都可独立获取对应 SDK 实例；
 *  3. 支持 Linux/Windows 系统动态选择库文件路径；
 *  4. 在 Spring 容器销毁时自动清理所有 SDK。
 */
@Component
public class HikSDKManager {

    private static final Logger logger = LoggerFactory.getLogger(HikSDKManager.class);

    /** 保存不同类型 SDK 的实例 */
    private static final Map<SdkType, Object> sdkMap = new ConcurrentHashMap<>();

    /** 工程路径（假设所有 lib 库放在 supervision-admin/lib 目录下） */
    private static final String BASE_PATH = System.getProperty("user.dir") + "/supervision-admin";

    /**---------------------------------------------
     * 统一初始化全部 SDK（Spring 启动时执行）
     *---------------------------------------------*/
    @PostConstruct
    public void initAllSDKs() {
        logger.info("开始初始化全部海康 SDK...");
        for (SdkType type : SdkType.values()) {
            try {
                initSDK(type);
            } catch (Exception e) {
                logger.error("初始化 {} SDK 失败: {}", type, e.getMessage(), e);
            }
        }
        logger.info("全部海康 SDK 初始化完成");
    }

    /**---------------------------------------------
     * 清理所有 SDK（Spring 容器关闭时执行）
     *---------------------------------------------*/
    /**
     * Spring 容器销毁时自动执行
     * 调用所有 SDK 的 Cleanup 方法释放资源
     */
    @PreDestroy
    public void cleanupAll() {
        for (Map.Entry<SdkType, Object> entry : sdkMap.entrySet()) {
            try {
                entry.getValue().getClass().getMethod("NET_DVR_Cleanup").invoke(entry.getValue());
                logger.info("{} SDK 已清理", entry.getKey());
            } catch (Exception e) {
                logger.error("{} SDK 清理失败: {}", entry.getKey(), e.getMessage());
            }
        }
        sdkMap.clear();
    }

    /**---------------------------------------------
     * 获取已初始化的 SDK 实例
     *---------------------------------------------*/
    /**
     * 获取指定类型的 SDK 实例
     *
     * @param type SDK 类型（枚举）
     * @return 已加载的 SDK 实例（Object，可强制转换）
     */
    public Object getSDK(SdkType type) {
        Object sdk = sdkMap.get(type);
        if (sdk == null) {
            logger.warn("SDK 未初始化: {}", type);
        }
        return sdk;
    }

    /**---------------------------------------------
     * 初始化指定类型的 SDK
     *---------------------------------------------*/
    /**
     * 初始化指定类型的 SDK（线程安全）
     *
     * @param type SDK 类型
     */
    public synchronized void initSDK(SdkType type) {
        // 若已加载过则跳过
        if (sdkMap.containsKey(type)) {
            logger.info("{} SDK 已初始化", type);
            return;
        }

        try {
            String dllName = "HCNetSDK"; // 注意不要加 .dll 后缀

            // 运行时目录（jar 所在或项目根）
            File jarDir = new File(System.getProperty("user.dir"));

            // ① 优先尝试运行目录的 lib（打包后）
            File libDir = new File(jarDir, "lib");
            File dllFile = new File(libDir, "HCNetSDK.dll");

            // ② 如果没找到，再尝试开发环境下 supervision-admin/lib（IDEA运行）
            if (!dllFile.exists()) {
                File devLibDir = new File(jarDir, "supervision-admin/lib");
                File devDllFile = new File(devLibDir, "HCNetSDK.dll");
                if (devDllFile.exists()) {
                    libDir = devLibDir;
                    dllFile = devDllFile;
                }
            }

            if (!dllFile.exists()) {
                throw new RuntimeException("HCNetSDK.dll 文件不存在: " + dllFile.getAbsolutePath());
            }

            // 设置 JNA 搜索路径
            System.setProperty("jna.library.path", libDir.getAbsolutePath());
            logger.info("加载 {} SDK: {}", type, dllFile.getAbsolutePath());

            Object sdk;

            // 根据 SDK 类型加载不同类
            switch (type) {
                case PEOPLE_DOOR:
                    sdk = Native.loadLibrary(dllName, com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK.class);
                    break;
                case CAR_CAMERA:
                    sdk = Native.loadLibrary(dllName, HCNetSDK.class);
                    break;
                case CAR_DOOR:
                    sdk = Native.loadLibrary(dllName, com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK.class);
                    break;
                default:
                    throw new IllegalArgumentException("未知 SDK 类型: " + type);
            }

            // 存入全局 Map
            sdkMap.put(type, sdk);

            // Linux 系统需要额外加载 openssl 依赖
            if (isLinux()) loadLinuxLibraries(type);

            // 调用 SDK 初始化方法
            sdk.getClass().getMethod("NET_DVR_Init").invoke(sdk);

            // 开启 SDK 加载日志
            sdk.getClass().getMethod("NET_DVR_SetLogToFile", int.class, String.class, boolean.class)
                    .invoke(sdk, 3, "./sdklog", false);

            logger.info("{} SDK 初始化成功", type);

        } catch (Exception e) {
            logger.error("{} SDK 初始化失败: {}", type, e.getMessage(), e);
        }
    }

    /**---------------------------------------------
     * Linux 系统加载依赖库 libcrypto/libssl
     *---------------------------------------------*/
    /**
     * 加载 Linux 环境依赖库（OpenSSL）
     */
    private void loadLinuxLibraries(SdkType type) {
        try {
            String opensslPath = "/usr/lib64/";
            if (!new File(opensslPath + "libcrypto.so.1.1").exists()) {
                opensslPath = BASE_PATH + "/lib/";
            }

            String libCrypto = opensslPath + "libcrypto.so.1.1";
            String libSSL = opensslPath + "libssl.so.1.1";

            Object sdk = sdkMap.get(type);
            if (sdk == null) throw new RuntimeException(type + " SDK 实例未初始化");

            // 获取 SDK 内部 BYTE_ARRAY 类
            Class<?> sdkClass = sdk.getClass();
            Class<?> byteArrayClass = sdkClass.getDeclaredClasses()[0]; // HCNetSDK.BYTE_ARRAY

            // 创建 BYTE_ARRAY
            Object arr1 = byteArrayClass.getConstructor(int.class).newInstance(256);
            Object arr2 = byteArrayClass.getConstructor(int.class).newInstance(256);

            // 把路径写入 BYTE_ARRAY
            byte[] pathBytes1 = libCrypto.getBytes();
            byte[] pathBytes2 = libSSL.getBytes();

            java.lang.reflect.Field byValueField1 = arr1.getClass().getField("byValue");
            java.lang.reflect.Field byValueField2 = arr2.getClass().getField("byValue");

            byte[] byValue1 = (byte[]) byValueField1.get(arr1);
            byte[] byValue2 = (byte[]) byValueField2.get(arr2);

            System.arraycopy(pathBytes1, 0, byValue1, 0, pathBytes1.length);
            System.arraycopy(pathBytes2, 0, byValue2, 0, pathBytes2.length);

            // 写入结构体
            ((com.sun.jna.Structure) arr1).write();
            ((com.sun.jna.Structure) arr2).write();

            // 调用 SDK 设置
            sdkClass.getMethod("NET_DVR_SetSDKInitCfg", int.class, Pointer.class)
                    .invoke(sdk, 1, ((com.sun.jna.Structure) arr1).getPointer());
            sdkClass.getMethod("NET_DVR_SetSDKInitCfg", int.class, Pointer.class)
                    .invoke(sdk, 2, ((com.sun.jna.Structure) arr2).getPointer());

            logger.info("{} Linux依赖库加载完成", type);

        } catch (Exception e) {
            logger.error("{} 加载Linux依赖库失败: {}", type, e.getMessage());
        }
    }

    /** 判断当前系统是否为 Linux */
    private boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    /** 判断当前系统是否为 Windows */
    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    // 获取人行门禁 SDK
    public com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK getPeopleDoorSDK() {
        Object sdk = sdkMap.get(SdkType.PEOPLE_DOOR);
        if (sdk == null) {
            logger.warn("PeopleDoor SDK 未初始化");
            return null;
        }
        if (!(sdk instanceof com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK)) {
            logger.error("PeopleDoor SDK 类型错误: {}", sdk.getClass());
            return null;
        }
        return (com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK) sdk;
    }

    // 获取车牌识别摄像头 SDK
    public HCNetSDK getCarCameraSDK() {
        Object sdk = sdkMap.get(SdkType.CAR_CAMERA);
        if (sdk == null) {
            logger.warn("CarCamera SDK 未初始化");
            return null;
        }
        if (!(sdk instanceof HCNetSDK)) {
            logger.error("CarCamera SDK 类型错误: {}", sdk.getClass());
            return null;
        }
        return (HCNetSDK) sdk;
    }

    // 获取车辆道闸 SDK
    public com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK getCarDoorSDK() {
        Object sdk = sdkMap.get(SdkType.CAR_DOOR);
        if (sdk == null) {
            logger.warn("CarDoor SDK 未初始化");
            return null;
        }
        if (!(sdk instanceof com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK)) {
            logger.error("CarDoor SDK 类型错误: {}", sdk.getClass());
            return null;
        }
        return (com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK) sdk;
    }

}
