package com.myigou.tool;

import com.myigou.mainView.MuenBar;


import javax.swing.*;
import java.awt.*;
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
        InputStream input= MuenBar.class.getClass().getResourceAsStream(fileUrl);
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
}
