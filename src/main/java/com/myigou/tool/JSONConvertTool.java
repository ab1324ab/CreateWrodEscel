package com.myigou.tool;

import java.io.*;

/**
 * @author ab1324ab
 * Created by ab1324ab on 2022年11月18日14点41分
 * JSON文件操作*
 */
public class JSONConvertTool {

    /**
     * 读取json文件数据
     *
     * @param jsonPath json文件路径
     * @return 字符串
     */
    public static String readJson(String jsonPath) {
        File jsonFile = new File(jsonPath);
        try {
            FileReader fileReader = new FileReader(jsonFile);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            while (true) {
                int ch = reader.read();
                if (ch != -1) {
                    sb.append((char) ch);
                } else {
                    break;
                }
            }
            fileReader.close();
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 往json文件中写入数据
     *
     * @param jsonPath json文件路径
     * @param data    Map类型数据
     * @param flag     写入状态，true表示在文件中追加数据，false表示覆盖文件数据
     * @return 写入文件状态  成功或失败
     */
    public static boolean writeJson(String jsonPath, String data, boolean flag) {
        File jsonFile = new File(jsonPath);
        boolean isNotNew = true;
        try {
            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
                isNotNew = false;
            }
            FileWriter fileWriter = new FileWriter(jsonFile.getAbsoluteFile(), flag);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            if (isNotNew) bw.newLine();
            bw.write(data);
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
