package com.supervision.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * 闸机照片压缩工具类 (纯JDK实现，无需外网下载依赖)
 */
public class ImageCompressUtil {

    private static final long MAX_SIZE_BYTE = 200 * 1024; // 200KB阈值

    /**
     * 自动压缩图片并转为JPG格式
     */
    public static byte[] compressToJpg(byte[] originalBytes) throws IOException {
        // 1. 如果原图已经小于200KB，直接返回原图（如果你想统一转JPG，则不跳过）
        if (originalBytes.length <= MAX_SIZE_BYTE) {
            // return originalBytes; // 这里可选：如果想保险点，可以不论大小统一走一遍压缩
        }

        // 2. 读取原始图片
        BufferedImage srcImage = ImageIO.read(new ByteArrayInputStream(originalBytes));
        if (srcImage == null) throw new IOException("无法识别图片格式");

        // 3. 预处理：解决透明背景变黑，统一创建白色背景的RGB画布
        BufferedImage newImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = newImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        g2d.drawImage(srcImage, 0, 0, null);
        g2d.dispose();

        // 4. 尺寸缩放：如果宽度大于1000像素，等比例缩小（减小基础体积，提高识别效率）
        if (newImage.getWidth() > 1000) {
            int targetWidth = 1000;
            int targetHeight = (int) (newImage.getHeight() * (1000.0 / newImage.getWidth()));
            BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resized.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(newImage, 0, 0, targetWidth, targetHeight, null);
            g.dispose();
            newImage = resized;
        }

        // 5. 循环降低质量压缩，直到小于200KB
        float quality = 0.9f;
        byte[] result;
        do {
            result = writeJpgBytes(newImage, quality);
            quality -= 0.1f;
        } while (result.length > MAX_SIZE_BYTE && quality > 0.1f);

        return result;
    }

    private static byte[] writeJpgBytes(BufferedImage image, float quality) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) throw new IOException("JDK未找到JPG写入器");

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality); // 设置质量 0.0-1.0

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
        }
        writer.dispose();
        return baos.toByteArray();
    }
}
