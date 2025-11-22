package com.myigou.tool;

import java.awt.*;
import java.io.File;
import java.util.Objects;

/**
 * @author ab1324ab
 * Created by ab1324ab on 2017/6/18.
 */
public class WindowTool {

    /**
     * 窗口居中
     * @param frame
     */
    public static void winConter(Frame frame) {
        // 当前窗口属性
        Toolkit kit = frame.getToolkit();
        try {
            Image image = Objects.requireNonNull(ImageIconTool.gitImageIcon("icons/tubiao.png", 20, 20)).getImage();
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

    /**
     * 获取桌面的路径
     * @return
     */
    public static String getDesktop() {
        // 更准确地获取桌面路径
        String userHome = System.getProperty("user.home");
        File desktop = new File(userHome, "Desktop");
        if (!desktop.exists()) { // 如果英文"Desktop"不存在，尝试中文"桌面"
            desktop = new File(userHome, "桌面");
        }
        return desktop.getAbsolutePath();
    }
}
