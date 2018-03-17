package com.myigou.clientService;

import com.myigou.tool.PropertiesTool;
import com.myigou.view.WindowView;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
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
    //
    Workbook wb = null;
    //
    private String create_Excel_Properties= "config.properties";
    public CreateExcel2(){
        contentMap = WindowView.contentMap;
        try {
            wb = new XSSFWorkbook(CreateExcel2.class.getClass().getResourceAsStream("/template2.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入本周及下周部件内容到配置文件
     * @param tswkMap 存储第一页面板里的部件 本周
     * @param nxvWkMap 存储第二页面板里的部件 下周
     */
    public void serveWeedPlanProperties(Map<String, List<Object>> tswkMap,Map<String, List<Object>> nxvWkMap){
        for (int mapLine = 0; mapLine < tswkMap.size(); mapLine++) {
            String strLine = "";
            List<Object> entryValue = (List<Object>) tswkMap.get("line" + mapLine);
            for (int i = 0; i < 7; i++) {
                if (i == 2 || i == 4) {
                    JComboBox jComboBox = (JComboBox) entryValue.get(i);
                     Object jComboBoxValue= jComboBox.getSelectedItem();
                    if(jComboBoxValue != null){
                        strLine = strLine + jComboBoxValue + PropertiesTool.READ_SGMTA_SPLIT;
                    }else{
                        strLine = strLine + PropertiesTool.READ_SGMTA_SPLIT;
                    }
                } else {
                    JTextField jTextField = (JTextField) entryValue.get(i);
                    String jTextFieldText= jTextField.getText();
                    if(i < 6){
                        strLine = strLine + jTextFieldText + PropertiesTool.READ_SGMTA_SPLIT;
                    }else{
                        strLine = strLine + jTextFieldText;
                    }
                }
            }
            PropertiesTool.writeSet(create_Excel_Properties,"tswMapLine"+mapLine,strLine);
        }

        for (int mapLine = 0; mapLine < nxvWkMap.size(); mapLine++) {
            String strLine = "";
            List<Object> entryValue = (List<Object>) nxvWkMap.get("line" + mapLine);
            for (int i = 0; i < 7; i++) {
                if (i == 2 || i == 4) {
                    JComboBox jComboBox = (JComboBox) entryValue.get(i);
                    Object jComboBoxValue= jComboBox.getSelectedItem();
                    if(jComboBoxValue != null){
                        strLine = strLine + jComboBoxValue + PropertiesTool.READ_SGMTA_SPLIT;
                    }else{
                        strLine = strLine + PropertiesTool.READ_SGMTA_SPLIT;
                    }
                } else {
                    JTextField jTextField = (JTextField) entryValue.get(i);
                    String jTextFieldText= jTextField.getText();
                    if(i < 6){
                        strLine = strLine + jTextFieldText + PropertiesTool.READ_SGMTA_SPLIT;
                    }else{
                        strLine = strLine + jTextFieldText;
                    }
                }
            }
            PropertiesTool.writeSet(create_Excel_Properties,"nxvWkMapLine"+mapLine,strLine);
        }
    }

    /**
     * 写入其余个别部件
     * @param troubleShootingMap 余留问题；需其它部门或领导协助解决的事宜；工作中的不足和需改进之处部件
     */
    public void serveTroubleShootingProperties(Map<String, Object> troubleShootingMap){
        // 部门 编辑框
        JTextField ranksText = (JTextField)troubleShootingMap.get("line"+0);
        PropertiesTool.writeSet(create_Excel_Properties,"department",ranksText.getText());
        // 计划人 编辑框
        JTextField plannedText = (JTextField)troubleShootingMap.get("line"+1);
        PropertiesTool.writeSet(create_Excel_Properties,"name",plannedText.getText());
        // 总结日期 编辑框
        JTextField summaryDateText = (JTextField)troubleShootingMap.get("line"+2);
        // PropertiesTool.writeSet(create_Excel_Properties,"department",ranksText.getText());
        // 计划日期 编辑框
        JTextField plannedDateText = (JTextField)troubleShootingMap.get("line"+3);
        // PropertiesTool.writeSet(create_Excel_Properties,"department",ranksText.getText());
        // 余留问题 文本域
        JTextArea leaveArea = (JTextArea)troubleShootingMap.get("line"+4);
        PropertiesTool.writeSet(create_Excel_Properties,"leaveArea",leaveArea.getText());
        // 紧急 文本域
        JTextArea urgentArea = (JTextArea)troubleShootingMap.get("line"+5);
        PropertiesTool.writeSet(create_Excel_Properties,"urgentArea",urgentArea.getText());
        // 一般 文本域
        JTextArea commonlyArea = (JTextArea)troubleShootingMap.get("line"+6);
        PropertiesTool.writeSet(create_Excel_Properties,"commonlyArea",commonlyArea.getText());
        // 稍缓 文本域
        JTextArea slowlyArea = (JTextArea)troubleShootingMap.get("line"+7);
        PropertiesTool.writeSet(create_Excel_Properties,"slowlyArea",slowlyArea.getText());
        // 工作中的不足和需改进之处 文本域
        JTextArea improvementJTextArea = (JTextArea)troubleShootingMap.get("line"+8);
        PropertiesTool.writeSet(create_Excel_Properties,"improvementJTextArea",improvementJTextArea.getText());


    }
}
