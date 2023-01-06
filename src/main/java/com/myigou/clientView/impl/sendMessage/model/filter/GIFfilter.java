package com.myigou.clientView.impl.sendMessage.model.filter;


import java.io.File;

/**
 * 保存GIF格式的过滤器*
 */
public class GIFfilter extends javax.swing.filechooser.FileFilter {

    public GIFfilter() {
    }

    public boolean accept(File file) {
        if (file.toString().toLowerCase().endsWith(".gif") || file.isDirectory()) {
            return true;
        } else
            return false;
    }

    public String getDescription() {
        return "*.GIF(GIF图像)";
    }
}
