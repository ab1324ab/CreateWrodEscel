package com.myigou.clientView.impl.sendMessage.model.filter;

import java.io.File;

//保存BMP格式的过滤器
public class BMPfilter extends javax.swing.filechooser.FileFilter {
    public BMPfilter() {
    }

    public boolean accept(File file) {
        if (file.toString().toLowerCase().endsWith(".bmp") ||
                file.isDirectory()) {
            return true;
        } else
            return false;
    }

    public String getDescription() {
        return "*.BMP(BMP图像)";
    }
}
