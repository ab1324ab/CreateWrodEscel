package com.myigou.tool;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/5/6.
 */
public class PropertiesTool {

    // 配置文件分隔符
    public static final String SGMTA_SPLIT = "\\&";
    public static final String SGMTA_SPLIT_LATER = "\\|";
    // 部件文本分隔符
    public static final String READ_SPLIT_LATER = "|";
    public static final String READ_SGMTA_SPLIT = "&";

    private static Properties properties = new Properties();

    /**
     * 读取配置文件
     *
     * @param fileName
     * @return
     */
    public static Map<String, String> redConfigFile(String fileName) {
        Map<String, String> contentMap = new HashMap<String, String>();
        try {
            properties.load(new FileInputStream(new File(System.getProperty("user.dir") + "/" + fileName)));
            Iterator it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                contentMap.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentMap;
    }

    /**
     * 写入配置文件
     *
     * @param filename
     * @param key
     * @param value
     */
    public static void writeSet(String filename, String key, String value) {
        try {
            String pathDir = System.getProperty("user.dir") + "\\" + filename;
            properties.load(new FileInputStream(new File(pathDir)));
            properties.setProperty(key, value);
            properties.store(new FileOutputStream(new File(pathDir)), key);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 删除配置文件相应KEY
     *
     * @param filename
     * @param key
     */
    public static void removeKey(String filename, String key) {
        try {
            String pathDir = System.getProperty("user.dir") + "\\" + filename;
            properties.load(new FileInputStream(new File(pathDir)));
            properties.remove(key);
            properties.store(new FileOutputStream(new File(pathDir)), key);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 自动恢复设置
     */
    public static void initialization() {
        InputStream inputStream = PropertiesTool.class.getClass().getResourceAsStream("/config.properties");
        // System.out.println("自动："+PropertiesTool.class.getClass().getResourceAsStream("/config.properties"));
        byte[] bytes = new byte[1024];
        try {
            String configFileUrl = new File(System.getProperty("user.dir")) + "/config.properties";
            if (!new File(configFileUrl).exists()) {
                new File(configFileUrl).createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File(configFileUrl));
            int bytesWritten = 0;
            while (bytesWritten != -1) {
                fileOutputStream.write(bytes, 0, bytesWritten);
                bytesWritten = inputStream.read(bytes);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
