package com.supervision.web.Hikvision.videoManage.other;


import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 命令行工具类：统一执行系统命令（支持 Windows / Linux）
 * 可用于执行 ffmpeg、taskkill、pkill 等命令。
 */
@Slf4j
public class CommandUtil {

    /** 判断是否为 Windows 系统 */
    private static final boolean IS_WINDOWS =
            System.getProperty("os.name").toLowerCase().contains("win");

    /** 判断是否为 Linux 系统 */
    private static final boolean IS_LINUX =
            System.getProperty("os.name").toLowerCase().contains("linux");

    /**
     * 通用命令执行入口，自动判断系统类型
     */
    public String exec(String cmd) {
        if (IS_WINDOWS) {
            return winExec(cmd);
        } else if (IS_LINUX) {
            return linuxExec(cmd);
        } else {
            log.warn("未知系统类型，无法执行命令: {}", cmd);
            return "不支持的系统类型";
        }
    }

    /**
     * 执行 Linux 命令
     */
    public String linuxExec(String cmd) {
        log.info("执行 Linux 命令: {}", cmd);
        StringBuilder output = new StringBuilder();
        Process process = null;
        try {
            // 使用 bash 执行命令，支持管道符和复杂指令
            process = new ProcessBuilder("/bin/bash", "-c", cmd)
                    .redirectErrorStream(true)
                    .start();

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            int exitCode = process.waitFor();
            log.info("命令执行完成, 退出码: {}", exitCode);
            return output.toString();

        } catch (IOException | InterruptedException e) {
            log.error("执行 Linux 命令失败: {}", cmd, e);
            Thread.currentThread().interrupt();
            return "命令执行失败：" + e.getMessage();
        } finally {
            if (process != null) process.destroy();
        }
    }

    /**
     * 执行 Windows 命令
     */
    public String winExec(String cmd) {
        log.info("执行 Windows 命令: {}", cmd);
        StringBuilder output = new StringBuilder();
        Process process = null;
        try {
            // 用 cmd /c 执行命令，防止窗口常驻
            process = new ProcessBuilder("cmd", "/c", cmd)
                    .redirectErrorStream(true)
                    .start();

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            int exitCode = process.waitFor();
            log.info("命令执行完成, 退出码: {}", exitCode);
            return output.toString();

        } catch (IOException | InterruptedException e) {
            log.error("执行 Windows 命令失败: {}", cmd, e);
            Thread.currentThread().interrupt();
            return "命令执行失败：" + e.getMessage();
        } finally {
            if (process != null) process.destroy();
        }
    }
}
