package com.myigou.tool;

import com.myigou.mainView.ViewMain;


import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ab1324ab on 2018/3/27.
 */
public class ImageIconTool {
    /**
     * 获取图片
     * @param fileUrl
     * @return
     */
    public static ImageIcon gitImageIcon(String fileUrl,int width,int height){
        try {
        InputStream input= ViewMain.class.getClass().getResourceAsStream(fileUrl);
        byte[] byt = new byte[input.available()];
            input.read(byt);
            input.close();
            ImageIcon imageIcon= new ImageIcon(byt);
            Image image= imageIcon.getImage();
            Image smallImage = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
            ImageIcon smallIcon = new ImageIcon(smallImage);
            return smallIcon;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将图片置灰 ---  使用现成的类
     * @param originalImage
     * @return
     */
    public static BufferedImage getGrayImage(BufferedImage originalImage){
        BufferedImage grayImage;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        //TYPE_3BYTE_BGR -->  表示一个具有 8 位 RGB 颜色分量的图像，对应于 Windows 风格的 BGR 颜色模型，具有用 3 字节存储的 Blue、Green 和 Red 三种颜色。
        grayImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_3BYTE_BGR);
        ColorConvertOp cco = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        cco.filter(originalImage, grayImage);
        return grayImage;
    }

    /**
     * 将图片置灰 --- 手动
     * @param originalImage
     * @return
     */
    public static BufferedImage getGrayImageManual(BufferedImage originalImage){
        int green = 0,red = 0,blue = 0,rgb;
        int imageWidth = originalImage.getWidth();
        int imageHight = originalImage.getHeight();
        BufferedImage routeImage = new BufferedImage(imageWidth,imageHight,BufferedImage.TYPE_3BYTE_BGR);
        for(int i = originalImage.getMinX();i < imageWidth;i++){
            for(int j = originalImage.getMinY();j < imageHight;j++){
                //获取该点像素，用Object类型标识
                Object data = routeImage.getRaster().getDataElements(i, j,null);
                red = routeImage.getColorModel().getRed(data);
                green = routeImage.getColorModel().getGreen(data);
                blue = routeImage.getColorModel().getBlue(data);
                red = (red*3+green*6+blue*1)/10;
                green = red;
                blue = green;
                rgb = (red*256 +green)*256 +blue;
                if(rgb>8388608){
                    rgb = rgb - 256*256*256;
                }
                //将rgb值写回图片
                routeImage.setRGB(i, j, rgb);
            }
        }
        return routeImage;
    }

}
