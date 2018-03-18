package com.myigou.clientService;

import com.myigou.clientService.configKeyEnum.WeekPropertiesEnum;
import com.myigou.tool.PropertiesTool;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2018/3/17.
 */
public class CreateExcel2 {
    // 读取所有配置项
    private static Map<String, String> contentMap = new HashMap<String, String>();
    // excel对象
    Workbook wb = null;
    // 写入配置文件
    private String create_Excel_Properties = "config.properties";

    public CreateExcel2() {
        try {
            wb = new XSSFWorkbook(CreateExcel2.class.getClass().getResourceAsStream("/template2.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入本周及下周部件内容到配置文件
     *
     * @param tswkNxvWkMap 存储(本周\下周)面板里的部件
     * @param line         存入配置文件键名
     */
    public void serveWeedPlanProperties(Map<String, List<Object>> tswkNxvWkMap, String line) {
        for (int mapLine = 0; mapLine < tswkNxvWkMap.size(); mapLine++) {
            String strLine = "";
            List<Object> entryValue = (List<Object>) tswkNxvWkMap.get(WeekPropertiesEnum.line + mapLine);
            for (int i = 0; i < 7; i++) {
                if (i == 2 || i == 4) {
                    JComboBox jComboBox = (JComboBox) entryValue.get(i);
                    Object jComboBoxValue = jComboBox.getSelectedItem();
                    if (jComboBoxValue != null) {
                        strLine = strLine + jComboBoxValue + PropertiesTool.READ_SGMTA_SPLIT;
                    } else {
                        strLine = strLine + PropertiesTool.READ_SGMTA_SPLIT;
                    }
                } else {
                    JTextField jTextField = (JTextField) entryValue.get(i);
                    String jTextFieldText = jTextField.getText();
                    if (i < 6) {
                        strLine = strLine + jTextFieldText + PropertiesTool.READ_SGMTA_SPLIT;
                    } else {
                        strLine = strLine + jTextFieldText;
                    }
                }
            }
            PropertiesTool.writeSet(create_Excel_Properties, line + mapLine, strLine);
        }
    }

    /**
     * 写入其余个别部件
     *
     * @param troubleShootingMap 余留问题；需其它部门或领导协助解决的事宜；工作中的不足和需改进之处部件
     */
    public void serveTroubleShootingProperties(Map<String, Object> troubleShootingMap) {
        // 部门 编辑框
        JTextField ranksText = (JTextField) troubleShootingMap.get(WeekPropertiesEnum.line + 0);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.department, ranksText.getText());
        // 计划人 编辑框
        JTextField plannedText = (JTextField) troubleShootingMap.get(WeekPropertiesEnum.line + 1);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.name, plannedText.getText());
        // 总结日期 编辑框
        JTextField summaryDateText = (JTextField) troubleShootingMap.get(WeekPropertiesEnum.line + 2);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.summaryDateText, summaryDateText.getText());
        // 计划日期 编辑框
        JTextField plannedDateText = (JTextField) troubleShootingMap.get(WeekPropertiesEnum.line + 3);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.plannedDateText, plannedDateText.getText());
        // 余留问题 文本域
        JTextArea leaveArea = (JTextArea) troubleShootingMap.get(WeekPropertiesEnum.line + 4);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.leaveArea, leaveArea.getText());
        // 紧急 文本域
        JTextArea urgentArea = (JTextArea) troubleShootingMap.get(WeekPropertiesEnum.line + 5);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.urgentArea, urgentArea.getText());
        // 一般 文本域
        JTextArea commonlyArea = (JTextArea) troubleShootingMap.get(WeekPropertiesEnum.line + 6);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.commonlyArea, commonlyArea.getText());
        // 稍缓 文本域
        JTextArea slowlyArea = (JTextArea) troubleShootingMap.get(WeekPropertiesEnum.line + 7);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.slowlyArea, slowlyArea.getText());
        // 工作中的不足和需改进之处 文本域
        JTextArea improvementJTextArea = (JTextArea) troubleShootingMap.get(WeekPropertiesEnum.line + 8);
        PropertiesTool.writeSet(create_Excel_Properties, WeekPropertiesEnum.improvementJTextArea, improvementJTextArea.getText());
    }

    /**
     * 创建周计划
     * @return String 返回状态
     */
    public String createExcel() {
        contentMap = PropertiesTool.redConfigFile("config.properties");
        Cell cell = null;
        // 创建表格页
        Sheet sheet = wb.getSheetAt(1);
        // 部门
        cell = sheet.getRow(3).getCell(1);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.department));
        // 名字
        cell = sheet.getRow(3).getCell(3);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.name));
        // 计划日期
        cell = sheet.getRow(3).getCell(5);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.plannedDateText));
        // 总结日期
        cell = sheet.getRow(3).getCell(8);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.summaryDateText));

        // 本周计划 内容 生成
        String tswMapLine = contentMap.get(WeekPropertiesEnum.tswMapLine+0);
        String[] row= tswMapLine.split(PropertiesTool.READ_SGMTA_SPLIT);
        // 任务名/组别
        cell = sheet.getRow(5).getCell(0);
        cell.setCellValue(row[0]);
        // 任务内容
        cell = sheet.getRow(5).getCell(1);
        cell.setCellValue(row[1]);
        // 难易度
        cell = sheet.getRow(5).getCell(4);
        cell.setCellValue(row[2]);
        // 预计完成时间
        cell = sheet.getRow(5).getCell(5);
        cell.setCellValue(row[3]);
        // 完成比例
        cell = sheet.getRow(5).getCell(6);
        cell.setCellValue(row[4]);
        // 跟进人
        cell = sheet.getRow(5).getCell(7);
        cell.setCellValue(row[5]);
        // 完成情况
        cell = sheet.getRow(5).getCell(8);
        cell.setCellValue(row[6]);



        // 创建输出文件
        FileOutputStream os = null;
        String runPath = "C:\\Users\\ab1324ab\\Desktop";//System.getProperty("user.dir");
        File file = new File(runPath + "\\" + contentMap.get("fileName") + "周计划总结.xlsx");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "写出成功！";
    }
}
