package com.supervision.web.controller.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import com.spire.doc.*;

import com.spire.doc.documents.*;
import com.spire.doc.fields.DocPicture;
import com.spire.xls.ExcelVersion;
import com.spire.xls.ViewMode;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.*;

import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;


public class PrintCommon {


    /**
     * ecxle打印生成
     * @param filepath
     * @param filenamber
     * @return
     */
    public static String printPDF(String filepath, String filenamber) {

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(filepath));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\Supervision\\uploadPath\\print\\"+filenamber+".pdf"));
            setQrCodeForPDF(bos, inputStream,filenamber,filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filenamber+".pdf";
    }
    /**
     * word打印生成
     * @param filepath
     * @param filenamber
     * @return
     */
    public static String printWord(String filepath, String filenamber) {

        //AjaxResult ajab = AjaxResult.success();

        Document doc = new Document();
        doc.loadFromFile(filepath);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;

        try {
//            InputStream pathis= new FileInputStream(filepath);
//            XWPFDocument doc1 = new XWPFDocument(pathis);

            bitMatrix = qrCodeWriter.encode(filenamber, BarcodeFormat.QR_CODE, 62, 64);
            BufferedImage image = deleteWhite(bitMatrix);
            File outputfile = new File("D:\\supervision\\" + "QRCode.png");
            if (!outputfile.getParentFile().exists()) {//如果文件夹不存在
                outputfile.mkdirs();//创建文件夹
            }
            ImageIO.write(image, "png", outputfile);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        int count = doc.getPageCount();
        for (int i = 0; i < doc.getSections().getCount(); i++) {
            Section section = doc.getSections().get(i);
           // section.getPageSetup().setFooterDistance(20);
            //添加二维码图片到Word页脚
            //float y = (float) (section.getPageSetup().getPageSize().getHeight() / 0);
           // float x = (float) (section.getPageSetup().getPageSize().getWidth() / 0);
            HeaderFooter footer = section.getHeadersFooters().getFooter();
            //footer.getParagraphs().clear();//清除格式
            Paragraph para1= footer.addParagraph();//重新添加段落
            if(section.getPageSetup().getOrientation().equals(PageOrientation.Portrait)){
                DocPicture pic2 = para1.appendPicture("D:\\supervision\\" +"QRCode.png"+"");//加载图片
                pic2.setTextWrappingStyle(TextWrappingStyle.In_Front_Of_Text);//图片置于文字下方
                pic2.setHorizontalPosition(20f);
                pic2.setHorizontalOrigin(HorizontalOrigin.Left_Margin_Area);
                pic2.setVerticalPosition(20f);
                pic2.setVerticalOrigin(VerticalOrigin.Bottom_Margin_Area);
                // pic2.setVerticalAlignment(ShapeVerticalAlignment.Bottom);
              //  pic2.setHorizontalAlignment(ShapeHorizontalAlignment.Left);//设置图片对齐方式


            }else {
                DocPicture pic2 = para1.appendPicture("D:\\supervision\\" +"QRCode.png" + "");//加载图片
                pic2.setTextWrappingStyle(TextWrappingStyle.In_Front_Of_Text);//图片置于文字下方
                pic2.setHorizontalPosition(20f);
                pic2.setHorizontalOrigin(HorizontalOrigin.Left_Margin_Area);
                pic2.setVerticalPosition(35f);
                pic2.setVerticalOrigin(VerticalOrigin.Bottom_Margin_Area);
                //如果设置对齐方式  绝对值无法起作用
               // pic2.setVerticalAlignment(ShapeVerticalAlignment.Bottom);

               // pic2.setHorizontalAlignment(ShapeHorizontalAlignment.Left);//设置图片对齐方式

            }
        }

        //保存文档
        doc.saveToFile("D:\\Supervision\\uploadPath\\print\\"+filenamber+".docx", FileFormat.Docx_2013);
        doc.dispose();
        //ajab.put("presentPath","D:\\Supervision\\uploadPath\\print\\"+filenamber+".docx");
        //ajab.put("filenamber",filenamber);
        return filenamber+".docx";
    }

    /**
     * ecxle打印生成
     * @param filepath
     * @param filenamber
     * @return
     */
    public static String printExcle(String filepath, String filenamber) {

       // AjaxResult ajab = AjaxResult.success();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(filenamber, BarcodeFormat.QR_CODE, 62, 64);
            BufferedImage image = deleteWhite(bitMatrix);
            File outputfile = new File("D:\\supervision\\" + "QRCode.png");
            if (!outputfile.getParentFile().exists()) {//如果文件夹不存在
                outputfile.mkdirs();//创建文件夹
            }
            ImageIO.write(image, "png", outputfile);
            //加载Excel示例文档
            Workbook workbook = new Workbook();
            workbook.loadFromFile(filepath);

            for (int i=0;i<workbook.getWorksheets().getCount();i++){
                //获取第一个工作表
                Worksheet worksheet = workbook.getWorksheets().get(i);
                //加载图片
                BufferedImage image1 = ImageIO.read(new File("D:\\supervision\\" +"QRCode.png"));
                //设置图片页眉
//        worksheet.getPageSetup().setLeftHeaderImage(image1);
//        worksheet.getPageSetup().setLeftHeader("&G");
                worksheet.getPageSetup().setLeftFooterImage(image1);
                worksheet.getPageSetup().setLeftFooter("&G");
                //设置显示样式
                worksheet.setViewMode(ViewMode.Layout);
            }

            //保存文档
            workbook.saveToFile("D:\\Supervision\\uploadPath\\print\\"+filenamber+".xlsx", ExcelVersion.Version2010);
            workbook.dispose();
           // ajab.put("presentPath","D:\\Supervision\\uploadPath\\print\\"+filenamber+".docx");
            //ajab.put("filenamber",filenamber);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return filenamber+".xlsx";
    }

    private static BufferedImage deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }

        int width = resMatrix.getWidth();
        int height = resMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, resMatrix.get(x, y) ? BLACK
                        : WHITE);
            }
        }
        return image;
    }
    /**
     * PDF附件添加二维码
     *
     * @param bos   输出文件的位置
     * @param input 输入文件流
     * @author nicky.ma
     * @date 2019年6月11日下午3:42:15
     */
    public static void setQrCodeForPDF(BufferedOutputStream bos, InputStream input,String filenamber,String filepath) {

        try {
            // 创建水印图片
            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(filenamber, 62, 64, null);
            com.itextpdf.text.Image iTextImage = barcodeQRCode.getImage();
            // 水印图片位置
            iTextImage.setAbsolutePosition(10, 0);
            // 边框固定
//            iTextImage.scaleToFit(200, 200);
            // 设置旋转弧度
            //image.setRotation(30);// 旋转 弧度
            // 设置旋转角度
//            iTextImage.setRotationDegrees(45);
            // 设置等比缩放
            iTextImage.scalePercent(80);
            // 自定义大小
            iTextImage.scaleAbsolute(62, 64);
            //PDF附件加上二维码水印
            setWatermarkForPDF(bos, input, iTextImage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }



    /**
     * 为PDF附件添加图片水印
     *
     * @param bos   输出文件的位置
     * @param input 输入文件流
     * @author nicky.ma
     * @date 2019/6/11 12:00:32
     */
    public static void setWatermarkForPDF(BufferedOutputStream bos, InputStream input, Image iTextImage)
            throws IOException, DocumentException {
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte waterMar;

        PdfGState gs = new PdfGState();
        long startTime = System.currentTimeMillis();
        System.out.println("PDF加图片水印 start");
        for (int i = 1; i < total; i++) {
//            waterMar = stamper.getOverContent(i);// 在内容上方加水印
            waterMar = stamper.getOverContent(i);//在内容下方加水印
            // 设置图片透明度为0.2f
            //gs.setFillOpacity(0.2f);
            // 设置笔触字体不透明度为0.4f
            //gs.setStrokeOpacity(0.4f);
            // 开始水印处理
            waterMar.beginText();
            // 设置透明度
            waterMar.setGState(gs);
            // 设置水印字体参数及大小
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            waterMar.setFontAndSize(bf, 12);
//             设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
            // waterMar.showTextAligned(Element.ALIGN_CENTER, "存证证书", 60, 725, 0);
            // 设置水印颜色
            waterMar.setColorFill(BaseColor.GRAY);
            // 附件加上水印图片
            waterMar.addImage(iTextImage);
            // 完成水印添加
            waterMar.endText();
            // stroke
            waterMar.stroke();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("PDF加图片水印 ok 所用时间:" + (endTime - startTime) + "s");
        stamper.close();
        reader.close();
    }
}


