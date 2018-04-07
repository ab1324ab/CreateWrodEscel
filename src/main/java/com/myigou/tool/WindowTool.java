package com.myigou.tool;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/6/18.
 */
public class WindowTool {

    /**
     * 窗口居中
     *
     * @param frame
     */
    public static void winConter(Frame frame)  {
        // 当前窗口属性
        Toolkit kit = frame.getToolkit();
        try {
            Image image = kit.getImage(WindowTool.class.getClass().getResource("/icons/tubiao.png"));
            frame.setIconImage(image);
            // 获取屏幕封装对象
            Dimension screenSize = kit.getScreenSize();
            // 获取屏幕高度
            int screenSizeWidth = screenSize.width;
            // 获取屏幕高度
            int screenSizeheight = screenSize.height;
            // 获取当前窗口宽度
            int windownWidth = frame.getWidth();
            // 获取当前窗口高度
            int windownHeight = frame.getHeight();
            // 保持屏幕居中
            frame.setLocation((screenSizeWidth - windownWidth) / 2, (screenSizeheight - windownHeight) / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
