package com.myigou.clientView.impl.windowSetting.module;

import com.myigou.clientService.enums.ColorEnum;
import com.myigou.tool.PropertiesTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class WeekPlanMakeSetting {
    private JPanel jpanel;
    private JScrollPane jScrollPane;
    private JTextField fileJText;
    private JTextField params;
    private JTextField name;
    private JTextField otherText;
    private JTextField excelText;
    private JTextField connectorText;
    private JRadioButton 红RadioButton;
    private JRadioButton 橙RadioButton;
    private JRadioButton 绿RadioButton;
    private JRadioButton 青RadioButton;
    private JRadioButton 蓝RadioButton;
    private JRadioButton 紫RadioButton;
    private JRadioButton 黑RadioButton;
    private JRadioButton 粉RadioButton;
    private JButton applicationButton;
    private JTextField completionRatioText;
    private JTextField facilityValueText;
    private JTextField nxvwkText;
    private JTextField tswkText;

    public JPanel getJpanel() {
        return jpanel;
    }

    public WeekPlanMakeSetting(JFrame jFrame) {
        Map<String, String> contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // 格式（例如:%t周总结-开发三部门%n）xx月xx日周总结-开发三部门某某
        fileJText.setText(contentMap.get("fileJText")); // 周总结文件名
        params.setText(contentMap.get("params")); // 文件名日期格式
        name.setText(contentMap.get("name")); // 姓名
        otherText.setText(contentMap.get("otherText")); // 总结日期格式
        // 文档工作表名示例（MM.dd），MM大写表示月份，dd小写表示日期。
        excelText.setText(contentMap.get("excelText")); // 工作表名 输入框
        connectorText.setText(contentMap.get("connectorText")); // 连接符 输入框
        // 初始行数（最高100）
        tswkText.setText(contentMap.get("tswkText"));   // 本周计划行
        nxvwkText.setText(contentMap.get("nxvwkText")); // 下周计划行
        // 文字颜色
        ButtonGroup group = new ButtonGroup();
        group.add(红RadioButton);
        group.add(橙RadioButton);
        group.add(绿RadioButton);
        group.add(青RadioButton);
        group.add(蓝RadioButton);
        group.add(紫RadioButton);
        group.add(黑RadioButton);
        group.add(粉RadioButton);
        Enumeration<AbstractButton> elements = group.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton btn = elements.nextElement();
            if (contentMap.get("colorPanel").equals(btn.getText())) btn.setSelected(true);
            btn.addActionListener(e -> {
                String colorValue = ColorEnum.getColorValue(btn.getText());
                PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, "colorPanel", btn.getText());
                PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, "colorPanelValue", colorValue);
            });
        }
        // 下拉框数值设置
        facilityValueText.setText(contentMap.get("facilityValueText").replaceFirst(",", ""));    // 难易度
        completionRatioText.setText(contentMap.get("completionRatioText").replaceFirst(",", ""));    // 完成比例
        Map<String, Object> setMap = new HashMap();
        setMap.put("fileJText", fileJText);
        setMap.put("params", params);
        setMap.put("name", name);
        setMap.put("otherText", otherText);
        setMap.put("excelText", excelText);
        setMap.put("connectorText", connectorText);
        setMap.put("tswkText", tswkText);
        setMap.put("nxvwkText", nxvwkText);
        setMap.put("facilityValueText", facilityValueText);
        setMap.put("completionRatioText", completionRatioText);

        applicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String key : setMap.keySet()) {
                        if ("facilityValueText".equals(key)) {
                        JTextField textField = (JTextField) setMap.get(key);
                        String textFieldText = "," + textField.getText();
                        PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, key, textFieldText);
                    } else if ("completionRatioText".equals(key)) {
                        JTextField textField = (JTextField) setMap.get(key);
                        String textFieldText = "," + textField.getText();
                        PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, key, textFieldText);
                    } else {
                        JTextField textField = (JTextField) setMap.get(key);
                        if (StringUtils.isEmpty(textField.getText())) {
                            JOptionPane.showMessageDialog(jFrame, "不允许设置内容空值！", "提示", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, key, textField.getText());
                    }
                }
                JOptionPane.showMessageDialog(jFrame, "应用成功！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
