package com.myigou.clientView.impl.textWord.tool;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * 文本创建工具*
 * 2023年2月19日11点04分*
 *
 * @author hawk*
 */
public class TextTool {

    /**
     * 获取一个唯一的文件名称
     *
     * @param path
     * @param fileName
     * @return
     */
    public static String createUniqueFileName(String path, String fileName) {
        if (StringUtils.isBlank(fileName)) fileName = "新建 文件";
        String[] fttr = fileName.split("\\.");
        String suffix = "";
        if (fttr.length == 2) suffix = fttr[fttr.length - 1];
        File file = new File(path + File.separator + fttr[0] + "." + suffix);
        if (!file.exists()) return fileName;
        else {
            String returnFileName = "";
            for (int i = 2; i < 100; i++) {
                file = new File(path + File.separator + fttr[0] + " (" + i + ")" + "." + suffix);
                if (!file.exists()) {
                    returnFileName = fttr[0] + " (" + i + ")";
                    break;
                }
            }
            return returnFileName + "." + suffix;
        }
    }

}
