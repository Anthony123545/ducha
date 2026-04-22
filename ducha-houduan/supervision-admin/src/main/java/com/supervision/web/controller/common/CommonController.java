package com.supervision.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
 import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.supervision.common.config.SupervisionConfig;
import com.supervision.common.constant.Constants;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.utils.StringUtils;
import com.supervision.common.utils.file.FileUploadUtils;
import com.supervision.common.utils.file.FileUtils;
import com.supervision.framework.config.ServerConfig;

import com.spire.doc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.text.DecimalFormat;

/**
 * 通用请求处理
 *
 * @author supervision
 */
@RestController
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Value("${supervision.profile}")
    private String profile;
    @Value("${supervision.profileaddress}")
    private String profileadress;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = SupervisionConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = SupervisionConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            String aa = url.substring(url.lastIndexOf("."));
            boolean equals = ".docx".equals(aa);
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            String replace = url.replace("http://"+hostAddress+":8089/profile/","D:/supervision/uploadPath/" );
            File file1 = new File(replace);

            if(".doc".equals(aa)||".docx".equals(aa)){
                Document doc = new Document();
                doc.loadFromFile(replace);
                int pagecount = doc.getPageCount();
                ajax.put("pageCount",pagecount);
            }
            if(".pdf".equals(aa)){
                String htttpurl = url.substring(url.lastIndexOf("upload"));
                String fileurl= "D:\\supervision\\uploadPath/"+htttpurl;
                File file11 = new File(fileurl);
                PDDocument pdDocument = PDDocument.load(file11);
                int pages = pdDocument.getNumberOfPages();
                ajax.put("pageCount",pages);
            }

            //获取文件大小
            DecimalFormat df = new DecimalFormat("0.00");//设置保留位数
            long size = file.getSize();
            String format = df.format((float) size / 1024);
            Double i = Double.parseDouble(format);
            String fileLength = i + "KB";
            if (i > 1024) {
                String format2 = df.format((float) 1 / 1024);
                fileLength = format2 + "MB";
            }
            ajax.put("fileLength", fileLength);
            ajax.put("houZUi", aa);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    public static MultipartFile getFile(String URI) throws IOException {
        String replace = URI.replace("/profile/","D:/supervision/uploadPath/" );
        File file = new File(replace);
        if (file.exists()){
            //FIle 转 MultipartFile
//            FileInputStream fis = new FileInputStream(file);
            // 修复点1：读取文件字节数组（避免流一次性耗尽）
            byte[] fileContent = Files.readAllBytes(file.toPath());

            // 修复点2：根据文件名设置正确的图片ContentType（替换原application/octet-stream）
            String contentType = file.getName().toLowerCase().endsWith(".png")
                    ? "image/png"
                    : "image/jpeg";
            return new MockMultipartFile(
                    file.getName(),
                    file.getName(),
//                    "application/octet-stream",
//                    fis
                    contentType,
                    fileContent
            );
        }
        return null;
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = SupervisionConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }
}
