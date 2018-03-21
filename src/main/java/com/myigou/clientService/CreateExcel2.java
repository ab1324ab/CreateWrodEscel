package com.myigou.clientService;

import com.myigou.clientService.configKeyEnum.WeekPropertiesEnum;
import com.myigou.tool.BusinessTool;
import com.myigou.tool.PropertiesTool;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

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
    // 时间格式化
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");

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
            Map<Integer, String> stringMap = new HashMap<Integer, String>();
            List<Object> entryValue = (List<Object>) tswkNxvWkMap.get(WeekPropertiesEnum.line + mapLine);
            for (int i = 0; i < 7; i++) {
                if (i == 2 || i == 4) {
                    JComboBox jComboBox = (JComboBox) entryValue.get(i);
                    Object jComboBoxValue = jComboBox.getSelectedItem();
                    jComboBoxValue = jComboBoxValue.toString().replace("&", "");
                    jComboBoxValue = jComboBoxValue.toString().replace("?", "");
                    if (!"".equals(jComboBoxValue) && jComboBoxValue != null) {
                        strLine = strLine + jComboBoxValue + PropertiesTool.READ_SGMTA_SPLIT;
                    } else {
                        strLine = strLine + WeekPropertiesEnum.ASK + PropertiesTool.READ_SGMTA_SPLIT;
                    }
                } else {
                    JTextField jTextField = (JTextField) entryValue.get(i);
                    String jTextFieldText = jTextField.getText();
                    jTextFieldText = jTextFieldText.replace("&", "");
                    jTextFieldText = jTextFieldText.replace("?", "");
                    if (!"".equals(jTextFieldText) && jTextFieldText != null) {
                        strLine = strLine + jTextFieldText + PropertiesTool.READ_SGMTA_SPLIT;
                    } else {
                        strLine = strLine + WeekPropertiesEnum.ASK + PropertiesTool.READ_SGMTA_SPLIT;
                    }
                }
            }
            if (!"?&?&?&?&?&?&?&".equals(strLine)) {
                PropertiesTool.writeSet(create_Excel_Properties, line + mapLine, strLine);
            } else {
                PropertiesTool.removeKey(create_Excel_Properties, line + mapLine);
            }
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
     *
     * @return String 返回状态
     */
    public String createExcel(int tswkRow, int nxvWkRow) {
        contentMap = PropertiesTool.redConfigFile("config.properties");
        List<String> tswkPlanList = new ArrayList<String>();
        for (int tswk = 0; tswk < tswkRow; tswk++) {
            String conten = contentMap.get(WeekPropertiesEnum.tswMapLine + tswk);
            if (!"".equals(conten) && conten != null) {
                tswkPlanList.add(conten);
            }
        }
        List<String> nxvWkPlanList = new ArrayList<String>();
        for (int nxvWk = 0; nxvWk < nxvWkRow; nxvWk++) {
            String conten = contentMap.get(WeekPropertiesEnum.nxvWkMapLine + nxvWk);
            if (!"".equals(conten) && conten != null) {
                nxvWkPlanList.add(conten);
            }
        }
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
        Integer[] cellRow = new Integer[]{0, 1, 4, 5, 6, 7, 8};
        for (int tswk = 0; tswk < tswkPlanList.size(); tswk++) {
            String[] row = tswkPlanList.get(tswk).split(PropertiesTool.READ_SGMTA_SPLIT);
            for (int irow = 0; irow < row.length; irow++) {
                String cellValue = row[irow];
                if (WeekPropertiesEnum.ASK.equals(cellValue)) {
                    cellValue = "";
                }
                cell = sheet.getRow(5 + tswk).getCell(cellRow[irow]);
                cell.setCellValue(cellValue);
            }
        }
        // 下周计划 内容 生成
        for (int nxvWk = 0; nxvWk < nxvWkPlanList.size(); nxvWk++) {
            String[] row = nxvWkPlanList.get(nxvWk).split(PropertiesTool.READ_SGMTA_SPLIT);
            for (int irow = 0; irow < row.length; irow++) {
                String cellValue = row[irow];
                if (WeekPropertiesEnum.ASK.equals(cellValue)) {
                    cellValue = "";
                }
                cell = sheet.getRow(28 + nxvWk).getCell(cellRow[irow]);
                cell.setCellValue(cellValue);
            }
        }
        cell = sheet.getRow(41).getCell(0);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.leaveArea));
        cell = sheet.getRow(43).getCell(1);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.urgentArea));
        cell = sheet.getRow(44).getCell(1);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.commonlyArea));
        cell = sheet.getRow(45).getCell(1);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.slowlyArea));
        cell = sheet.getRow(47).getCell(0);
        cell.setCellValue(contentMap.get(WeekPropertiesEnum.improvementJTextArea));
        // 创建输出文件
        FileOutputStream os = null;
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String runPath = desktopDir.getAbsolutePath();
        File file = new File(runPath + "\\" + contentMap.get("fileName") + "周计划总结.xlsx");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "写出成功！";
    }

    public List<String> obtainingDataSources(List<String> weekPlanList) {
        List<Date> dateList = BusinessTool.getStartDateAndEndDate();
        String foundSheetName = dateFormat.format(dateList.get(0)).replaceFirst("0", "")
                + "~" + dateFormat.format(dateList.get(4)).replaceFirst("0", "");
        try {
            File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
            String runPath = desktopDir.getAbsolutePath();
            String fileExcel = runPath + "\\" + "开发三部.xlsx";
            Workbook wb = new XSSFWorkbook(new FileInputStream(new File(fileExcel)));
            Sheet sheet = wb.getSheet(foundSheetName);
            Integer[] lineNum = new Integer[]{0, 1, 4, 5, 6, 7, 8};
            for (int rowsLineNum = 4; rowsLineNum < sheet.getPhysicalNumberOfRows(); rowsLineNum++) {
                Row row = sheet.getRow(rowsLineNum);
                if("上周遗留任务".equals(row.getCell(lineNum[0]).getStringCellValue())){
                    break;
                }
                String string = "";
                for (int cellLineNum = 0; cellLineNum < lineNum.length; cellLineNum++) {
                    String rowContent = row.getCell(lineNum[cellLineNum]).toString();
                    if("".equals(rowContent)){
                        rowContent = WeekPropertiesEnum.ASK;
                    }else if (cellLineNum == 2){

                    }else if(cellLineNum == 3){

                    }
                    string = string + rowContent + PropertiesTool.READ_SGMTA_SPLIT;
                }
                weekPlanList.add(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return weekPlanList;
        }
    }

    /**
     * 根据不同情况获取Java类型值
     * @param cell
     * @return 返回Object类型值
     */
    public Object getJavaValue(Cell cell) {
        Object o = null;
        int cellType = cell.getCellType();
        switch (cellType) {
            case XSSFCell.CELL_TYPE_BLANK:
                o = "";
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                o = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_ERROR:
                o = "Bad value!";
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                o = getValueOfNumericCell(cell);
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                try {
                    o = getValueOfNumericCell(cell);
                } catch (IllegalStateException e) {
                    try {
                        o = cell.getRichStringCellValue().toString();
                    } catch (IllegalStateException e2) {
                        o = cell.getErrorCellValue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                o = cell.getRichStringCellValue().getString();
        }
        return o;
    }
    // 获取数字类型的cell值
    private static Object getValueOfNumericCell(Cell cell) {
        Boolean isDate = DateUtil.isCellDateFormatted(cell);
        Double d = cell.getNumericCellValue();
        Object o = null;
        if (isDate) {
            o = DateFormat.getDateTimeInstance()
                    .format(cell.getDateCellValue());
        } else {
            o = getRealStringValueOfDouble(d);
        }
        return o;
    }
    // 处理科学计数法与普通计数法的字符串显示，尽最大努力保持精度
    private static String getRealStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        boolean b = doubleStr.contains("E");
        int indexOfPoint = doubleStr.indexOf('.');
        if (b) {
            int indexOfE = doubleStr.indexOf('E');
            // 小数部分
            BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint
                    + BigInteger.ONE.intValue(), indexOfE));
            // 指数
            int pow = Integer.valueOf(doubleStr.substring(indexOfE
                    + BigInteger.ONE.intValue()));
            int xsLen = xs.toByteArray().length;
            int scale = xsLen - pow > 0 ? xsLen - pow : 0;
            doubleStr = String.format("%." + scale + "f", d);
        } else {
            java.util.regex.Pattern p = Pattern.compile(".0$");
            java.util.regex.Matcher m = p.matcher(doubleStr);
            if (m.find()) {
                doubleStr = doubleStr.replace(".0", "");
            }
        }
        return doubleStr;
    }
}
