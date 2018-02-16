package com.myigou.tool;

import java.awt.*;
import java.net.URL;

/**
 * Created by ab1324ab on 2017/6/18.
 */
public class WindowTool {

    /**
     * 窗口居中
     * @param frame
     */
    public static void winConter(Frame frame){
        //当前窗口属性
        Toolkit kit =Toolkit.getDefaultToolkit();
        //Image image=kit.createImage();//("C:\\Users\\ab1324ab\\Desktop\\notefile\\view_note\\target\\classes\\com\\myigou\\window\\title.PNG");
        try {
            frame.setIconImage(kit.getImage(new URL("http://p3.so.qhimgs1.com/bdr/200_200_/t01b5697d383fc43d23.png")));
            //this.setIconImage(new ImageIcon("C:\\Users\\ab1324ab\\Desktop\\notefile\\view_note\\target\\classes\\com\\myigou\\window\\title.PNG").getImage());
            Dimension screenSize= kit.getScreenSize();//获取屏幕封装对象
            int screenSizeWidth=screenSize.width;//获取屏幕高度
            int screenSizeheight=screenSize.height;//获取屏幕高度
            int windownWidth=frame.getWidth();//获取当前窗口宽度
            int windownHeight=frame.getHeight();//获取当前窗口高度
            frame.setLocation((screenSizeWidth-windownWidth)/2,(screenSizeheight-windownHeight)/2);//保持屏幕居中
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
