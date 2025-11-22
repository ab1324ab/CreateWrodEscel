package com.myigou.clientView.impl.sendMessage.tool;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.myigou.tool.JSONConvertTool;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 消息文件处理*
 */
public class MessageFileTool {

    /**
     * 设置基础用户信息*
     * @param mapObject
     */
    public static void setBasic(Map<String, String> mapObject) throws IOException {
        String avatarUrl = mapObject.get("avatarUrl");
        String name = mapObject.get("name");
        String soloId = mapObject.get("soloId");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Map<String, String> basicMapPath = getSaveBasicPath(soloId);
        String filePath = basicMapPath.get("filePath");
        String fileName = basicMapPath.get("fileName");
        Map<String, String> basicMap = new HashMap<>();
        if (basicMapPath.get("isNotNew").equals("1")) basicMap.put("firstTime", df.format(new Date()));
        basicMap.put("lastTime", df.format(new Date()));
        basicMap.put("avatarUrl", avatarUrl);
        basicMap.put("name", name);
        basicMap.put("soloId", soloId);
        Properties properties = new Properties();
        for (String key : basicMap.keySet()) {
            String pathDir = filePath + File.separator + fileName;
            properties.load(new FileInputStream(pathDir));
            properties.setProperty(key, basicMap.get(key));
            properties.store(new FileOutputStream(pathDir), key);
        }
    }

    /**
     * 获取用户基础信息*
     * @param soloId
     * @return
     */
    public static Map<String, String> getSaveBasicPath(String soloId) {
        try {
            String path = System.getProperty("user.dir") + File.separator + "all users" + File.separator + soloId;
            File fileDir = new File(path);
            String isNotNew = "0";
            if (!fileDir.exists()) fileDir.mkdirs();
            String fileName = "SIC-" + soloId + ".properties";
            String filePath = path + File.separator + fileName;
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
                isNotNew = "1";
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("filePath", path);
            map.put("fileName", fileName);
            map.put("isNotNew", isNotNew);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取基础信息*
     * @param soloId
     * @return
     */
    public static Map<String, String> getBasicInfo(String soloId) {
        Map<String, String> basicMap = new HashMap<>();
        try {
            Map<String, String> path = getSaveBasicPath(soloId);
            String filePath = path.get("filePath");
            String fileName = path.get("fileName");
            Properties properties = new Properties();
            properties.load(new FileInputStream(filePath + File.separator + fileName));
            Iterator it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                basicMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return basicMap;
    }

    /**
     * 获取目录下的用户信息*
     * @return
     */
    @SuppressWarnings("all")
    public static List<Map<String, String>> getMessageAllList() throws IOException {
        String path = System.getProperty("user.dir") + File.separator + "all users";
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        File[] allFile = file.listFiles();
        List<Map<String, String>> alluser = new ArrayList();
        for (File user : allFile) {
            File[] u = user.listFiles(pathname -> pathname.isFile());
            if (u.length == 0) continue;
            Map<String, String> contentMap = new HashMap<String, String>();
            Properties properties = new Properties();
            properties.load(new FileInputStream(u[0].getPath()));
            Iterator it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                contentMap.put(key, value);
            }
            File messFile = new File(getSaveMessageFilePath(contentMap.get("soloId")));
            if (messFile.exists()) {
                String message = "";
                String messageTime = "";
                String messageType = "";
                String jsonStr = JSONConvertTool.readJson(messFile.getPath());
                if (StringUtils.isNotEmpty(jsonStr)) {
                    String[] messages = jsonStr.split("\r\n");
                    JSONObject jsonObject = JSON.parseObject(messages[messages.length - 1]);
                    message = jsonObject.get("message") + "";
                    messageTime = jsonObject.get("messageTime") + "";
                    messageType = jsonObject.get("messageType") + "";
                }
                contentMap.put("message", message);
                contentMap.put("messageTime", messageTime);
                contentMap.put("messageType", messageType);
            } else {
                contentMap.put("messageTime", "");
                contentMap.put("message", "");
                contentMap.put("messageType", "");
            }
            alluser.add(contentMap);
        }
        return alluser;
    }

    /**
     * 消息文件路径获取*
     * @param soloId
     * @return
     */
    public static String getSaveMessageFilePath(String soloId) {

        String path = System.getProperty("user.dir") + File.separator + "all users" + File.separator + soloId + File.separator + "message";
        File fileDir = new File(path);
        if (!fileDir.exists()) fileDir.mkdirs();
        SimpleDateFormat jtdf = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String messageFileName = "MESS_" + jtdf.format(new Date()) + ".dll";
        String messageFilePath = path + File.separator + messageFileName;
        return messageFilePath;
    }

    /**
     * 消息文件路径获取*
     * @param soloId
     * @return
     */
    public static String getSaveMessagePath(String soloId) {
        String path = System.getProperty("user.dir") + File.separator + "all users" + File.separator + soloId + File.separator + "message";
        File fileDir = new File(path);
        if (!fileDir.exists()) fileDir.mkdirs();
        String messageFilePath = path + File.separator;
        return messageFilePath;
    }

    /**
     * 消息文件路径获取*
     * @param soloId
     * @return
     */
    public static String getSaveFilePath(String soloId) {
        String path = System.getProperty("user.dir") + File.separator + "all users" + File.separator + soloId + File.separator + "fileFold";
        File fileDir = new File(path);
        if (!fileDir.exists()) fileDir.mkdirs();
        String messageFilePath = path + File.separator;
        return messageFilePath;
    }

    /**
     * 消息图片路径获取*
     * @param soloId
     * @return
     */
    public static String getSaveImagePath(String soloId) {
        String path = System.getProperty("user.dir") + File.separator + "all users" + File.separator + soloId + File.separator + "imageFold";
        File fileDir = new File(path);
        if (!fileDir.exists()) fileDir.mkdirs();
        String messageFilePath = path + File.separator;
        return messageFilePath;
    }

    /**
     * 清除消息文件夹*
     * @param path
     * @return
     */
    public static boolean deleteMessagePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                if (!f.delete()) {
                    System.out.println(f.getAbsolutePath() + " delete error!");
                    return false;
                }
            } else {
                if (!deleteMessagePath(f.getAbsolutePath())) {
                    return false;
                }
            }
        }
        return file.delete();
    }

}
