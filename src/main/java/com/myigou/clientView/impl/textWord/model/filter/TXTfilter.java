package com.myigou.clientView.impl.textWord.model.filter;

import java.io.File;

//保存TXT格式的过滤器
public class TXTfilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        if (file.toString().toLowerCase().endsWith(".txt") ||
                file.isDirectory()) {
            return true;
        } else
            return false;
    }

    public String getDescription() {
        return "*.txt(txt文本)";
    }
}
