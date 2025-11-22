//import com.swetake.util.Qrcode;

import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {


    /**
     * @param srcImgPath       源图片路径
     * @param tarImgPath       保存的图片路径
     * @param waterMarkContent 水印内容
     * @param markContentColor 水印颜色
     * @param font             水印字体
     */
    public void addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent, Color markContentColor, Font font) {

        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            //设置水印的坐标
            int x = srcImgWidth - 2 * getWatermarkLength(waterMarkContent, g);
            int y = srcImgHeight - 2 * getWatermarkLength(waterMarkContent, g);
            g.drawString(waterMarkContent, x, y);  //画出水印
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }


    /**
     * 生成二维码(QRCode)图片的公共方法
     * @param content  存储内容
     * @param imgType  图片类型
     * @param size     二维码尺寸
     * @param logoPath 图片logo路径
     * @return
     * @throws Exception
     */
    public static BufferedImage qRCodeCommon(String content, String imgType, int size, String logoPath) throws Exception {
        BufferedImage bufImg = null;
        try {
//            Qrcode qrcodeHandler = new Qrcode();
//            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
//            qrcodeHandler.setQrcodeErrorCorrect('L');
//            qrcodeHandler.setQrcodeEncodeMode('B');
//            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
//            qrcodeHandler.setQrcodeVersion(size);
            // 获得内容的字节数组，设置编码格式
            byte[] contentBytes = content.getBytes("utf-8");
            // 图片尺寸
            int imgSize = 67 + 12 * (size - 1);
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();
            // 设置背景颜色
            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, imgSize, imgSize);

            // 设定图像颜色> BLACK
            gs.setColor(Color.BLACK);
            // 设置偏移量，不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容> 二维码
            if (contentBytes.length > 0 && contentBytes.length < 120) {
                boolean[][] codeOut = null;//qrcodeHandler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
            }
            //非空设置二维码logo,否则不设置
            if (StringUtils.isNotBlank(logoPath)) {
                Image image = ImageIO.read(new File(logoPath));
                gs.drawImage(image, (imgSize - image.getWidth(null)) / 2, (imgSize - image.getHeight(null)) / 2, null);
            }
            gs.dispose();
            bufImg.flush();
        } catch (Exception e) {
            throw e;
        }
        return bufImg;
    }


    public static void main(String[] args) {
        Font font = new Font("微软雅黑", Font.PLAIN, 16);                     //水印字体
        String srcImgPath = "C:\\Users\\hwk-t170\\Desktop\\多得宝图标\\MMSWeb\\login\\images\\qrbackground.jpg"; //背景
        String tarImgPath = "C:\\Users\\hwk-t170\\Desktop\\多得宝图标\\MMSWeb\\login\\images\\logo2.png"; //待存储的地址
        String waterMarkContent = "NO:1234567890";  //商家号
        try {
            BufferedImage buImg = qRCodeCommon("http://www.baidu.com", "png", 12, null);//

            ImageUtils.qrCodeWatermark(srcImgPath, buImg, waterMarkContent, "C:\\Users\\hwk-t170\\Desktop\\多得宝图标\\MMSWeb\\login\\images\\112.png");

//            File srcImgFile = new File(srcImgPath);
//            Image srcImg = ImageIO.read(srcImgFile);
//            BufferedImage bufImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
//            Graphics2D bufImgGraphics = bufImg.createGraphics();
//            // 重要设置 png图片背景透明不变黑
//            bufImg = bufImgGraphics.getDeviceConfiguration().createCompatibleImage(srcImg.getWidth(null), srcImg.getHeight(null), Transparency.TRANSLUCENT);
//            bufImgGraphics = bufImg.createGraphics();
//
//            bufImgGraphics.drawImage(buImg, 0, 0,null);
//            bufImgGraphics.setColor(Color.BLACK);
//            bufImgGraphics.setFont(font);
//            int x = bufImg.getWidth()/4-10;
//            int y = bufImg.getHeight()-10;
//            //画出水印
//            bufImgGraphics.drawString(waterMarkContent, x, y);
//            bufImgGraphics.dispose();
//
//
//            File file = new File("C:\\Users\\hwk-t170\\Desktop\\多得宝图标\\MMSWeb\\login\\images\\112.png");
//            if(file.exists()){
//                file.mkdir();
//           }
//
//
//            Boolean flag = ImageIO.write(bufImg, "PNG", new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 二维码添加 商户号水印
     * @param backgroundPic
     * @param qrCodePic
     * @param content
     * @param imgPath
     */
    public static void qrCodeWatermark(String backgroundPic, BufferedImage qrCodePic, String content, String imgPath) {
        try {
            Font font = new Font("微软雅黑", Font.PLAIN, 16);
            File srcImgFile = new File(backgroundPic);
            BufferedImage bufImg = ImageIO.read(srcImgFile);
            Graphics2D bufImgGraphics = bufImg.createGraphics();
            // 二维码图片位置
            int qrX = (bufImg.getWidth(null) - qrCodePic.getWidth(null)) / 2;
            int qrY = (bufImg.getHeight(null) - qrCodePic.getHeight(null)) / 2;
            bufImgGraphics.drawImage(qrCodePic, qrX, qrY, null);
            // 文字宽度
            FontMetrics fontMetrics = bufImgGraphics.getFontMetrics(font);
            int contentWidth = fontMetrics.stringWidth(content);
            bufImgGraphics.setColor(Color.BLACK);
            bufImgGraphics.setFont(font);
            int textX = bufImg.getWidth(null) / 2 - contentWidth / 2;
            int textY = bufImg.getHeight(null) - 110;
            //画出水印
            bufImgGraphics.drawString(content, textX, textY);
            bufImgGraphics.dispose();
            Boolean flag = ImageIO.write(bufImg, "png", new FileOutputStream(new File(imgPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
