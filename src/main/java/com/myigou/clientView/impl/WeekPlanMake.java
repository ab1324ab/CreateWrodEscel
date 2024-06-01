package com.myigou.clientView.impl;

import com.myigou.clientService.enums.WeekPropertiesEnum;
import com.myigou.clientView.FunctionInter;
import com.myigou.clientService.CreateExcel;
import com.myigou.tool.PropertiesTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/6/25.
 */
public class WeekPlanMake extends JPanel implements FunctionInter {
    // 第一页文本
    private List<List<JTextField>> jTextFieldList1 = new ArrayList<List<JTextField>>();
    // 第一页文本
    private List<List<JTextField>> jTextFieldList2 = new ArrayList<List<JTextField>>();
    // 第三页文本
    private List<List<JTextField>> jTextFieldList3 = new ArrayList<List<JTextField>>();
    // 内容map
    private Map<String, String> contentMap = new HashMap<String, String>();
    // 选择框1
    private List<JCheckBox> jCheckBoxsPage1 = new ArrayList<JCheckBox>();
    // 选择框2
    private List<JCheckBox> jCheckBoxsPage2 = new ArrayList<JCheckBox>();
    // 选择框3
    private List<JCheckBox> jCheckBoxsPage3 = new ArrayList<JCheckBox>();
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 第一行文本
    private List<JTextField> jTextFields1 = null;
    // 第二行文本
    private List<JTextField> jTextFields2 = null;
    // 第三行文本
    private List<JTextField> jTextFields3 = null;
    // 部门
    private JTextField department = null;
    // 部门文件名
    private JTextField fileName = null;
    // 姓名
    private JTextField name = null;
    // 保存写出excel
    private JButton create = null;
    // 保存
    private JButton save = null;

    private Font font = new Font("微软雅黑",Font.PLAIN,14);

    public WeekPlanMake() {
        contentMap = PropertiesTool.redConfigFile("config.properties");
    }

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        // 重要布局
        jPanel.setLayout(gridBagLayout);
        this.controlCenter(jPanel);
        this.page1(jPanel);
        this.page2(jPanel);
        this.page3(jPanel);
        this.registerListener(jPanel);
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame,Font font) {
        JLabel title = new JLabel("周计划生成",JLabel.CENTER);
        title.setFont(font);
        jPanel.add(title);
        return jPanel;
    }

    /**
     * 控制面板组件
     *
     * @param pagePanel1
     */
    public void controlCenter(JPanel pagePanel1) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        // 文件名 提示标签
        JLabel titleFileName = new JLabel("文件名：",JLabel.RIGHT);
        titleFileName.setFont(font);
        gridBagLayout.setConstraints(titleFileName, gridBagConstraints);
        pagePanel1.add(titleFileName);
        // 文件名 输入框
        fileName = new JTextField(contentMap.get("fileName"));
        fileName.setColumns(27);
        fileName.setFont(font);
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(fileName, gridBagConstraints);
        pagePanel1.add(fileName);
        // 按钮保存
        save = new JButton("保存");
        save.setFont(font);
        save.setFocusPainted(false);
        save.setPreferredSize(new Dimension(80, 25));
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.gridwidth = 2;
        gridBagLayout.setConstraints(save, gridBagConstraints);
        pagePanel1.add(save);
        // 按钮保存并写出Escel
        create = new JButton("保存并写出Escel");
        create.setFont(font);
        create.setFocusPainted(false);
        create.setPreferredSize(new Dimension(190, 25));
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(create, gridBagConstraints);
        pagePanel1.add(create);
    }

    /**
     * 周计划第一页内容
     *
     * @param pagePanel1
     */
    public void page1(JPanel pagePanel1) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        //gridBagConstraints.anchor = GridBagConstraints.EAST;
        JLabel titleDepartment = new JLabel(contentMap.get("titleDepartment"),JLabel.RIGHT);
        titleDepartment.setFont(font);
        JLabel titleName = new JLabel(contentMap.get("titleName"),JLabel.RIGHT);
        titleName.setFont(font);
        // 部门
        department = new JTextField(contentMap.get(WeekPropertiesEnum.department));
        department.setFont(font);
        // 名字
        name = new JTextField(contentMap.get(WeekPropertiesEnum.name));
        name.setFont(font);
        gridBagConstraints.gridy = 1;

        gridBagLayout.setConstraints(titleDepartment, gridBagConstraints);
        // 部门
        pagePanel1.add(titleDepartment);
        gridBagLayout.setConstraints(department, gridBagConstraints);
        department.setColumns(8);
        // 创建文本框
        pagePanel1.add(department);
        //titleName.setBorder(BorderFactory.createLineBorder(Color.black));//边框
        gridBagLayout.setConstraints(titleName, gridBagConstraints);
        pagePanel1.add(titleName);

        gridBagLayout.setConstraints(name, gridBagConstraints);
        name.setColumns(8);
        // 创建文本框
        pagePanel1.add(name);

        String[] title1 = contentMap.get("title1").split(PropertiesTool.SGMTA_SPLIT);
        for (int j = 0; j <= 9; j++) {
            JLabel renwuContents = null;
            if(j == 9){
                renwuContents = new JLabel(title1[j],JLabel.LEFT);
                gridBagConstraints.weightx = 0;
            }else{
                renwuContents = new JLabel(title1[j],JLabel.CENTER);
            }
            if (j == 2 || j == 3 || j == 4 || j == 6 || j == 7 || j == 8) {
                gridBagConstraints.gridwidth = 1;
            } else if (j == 0 || j == 5) {
                gridBagConstraints.gridwidth = 1;
            } else {
                gridBagConstraints.gridwidth = 3;
            }
            gridBagConstraints.gridy = 2;
            renwuContents.setFont(font);
            gridBagLayout.setConstraints(renwuContents, gridBagConstraints);
            pagePanel1.add(renwuContents);
        }
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        String[] pageContents1 = contentMap.get("pageContents1").split(PropertiesTool.SGMTA_SPLIT_LATER);
        for (int i = 2; i < 2 + pageContents1.length; i++) {
            jTextFields1 = new ArrayList<JTextField>();
            for (int j = 0; j < 9; j++) {
                String sdf = pageContents1[i - 2];
                String[] fhl = sdf.split(PropertiesTool.SGMTA_SPLIT);
                JTextField textField = new JTextField(fhl[j]);
                if (j == 2 || j == 3 || j == 4 || j == 6 || j == 7 || j == 8) {
                    textField.setHorizontalAlignment(JTextField.CENTER);
                    textField.setColumns(3);
                    gridBagConstraints.gridwidth = 1;
                } else if (j == 0 || j == 5) {
                    textField.setHorizontalAlignment(JTextField.CENTER);
                    textField.setColumns(8);
                    gridBagConstraints.gridwidth = 1;
                } else {
                    textField.setHorizontalAlignment(JTextField.LEFT);
                    textField.setColumns(27);
                    gridBagConstraints.gridwidth = 3;
                }
                // 添加文本对象到list
                textField.setFont(font);
                jTextFields1.add(textField);
                gridBagConstraints.gridy = i + 1;
                gridBagLayout.setConstraints(textField, gridBagConstraints);
                // 完成比例
                pagePanel1.add(textField);
                if (j == 8) {
                    if (i == 2) {
                        jCheckBoxsPage1.add(new JCheckBox("", true));
                    } else {
                        jCheckBoxsPage1.add(new JCheckBox("", false));
                    }
                    gridBagLayout.setConstraints(jCheckBoxsPage1.get(i - 2), gridBagConstraints);
                    pagePanel1.add(jCheckBoxsPage1.get(i - 2));
                }
            }
            // 添加文本对象list
            jTextFieldList1.add(jTextFields1);
        }
    }

    /**
     * 周计划第二页内容
     *
     * @param pagePanel1
     */
    public void page2(JPanel pagePanel1) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        String[] title2 = contentMap.get("title2").split(PropertiesTool.SGMTA_SPLIT);
        for (int j = 0; j <= 6; j++) {
            JLabel renwuContents = null;
            if(j == 6){
                renwuContents = new JLabel(title2[j],JLabel.LEFT);
                gridBagConstraints.weightx = 0;
            }else{
                renwuContents = new JLabel(title2[j],JLabel.CENTER);
            }
            if (j == 0) {
                gridBagConstraints.gridwidth = 1;
            } else if (j == 1) {
                gridBagConstraints.gridwidth = 1;
            } else if (j == 3 || j == 4 || j == 5) {
                gridBagConstraints.gridwidth = 1;
            } else {
                gridBagConstraints.gridwidth = 6;
            }
            gridBagConstraints.gridy = 5;
            renwuContents.setFont(font);
            gridBagLayout.setConstraints(renwuContents, gridBagConstraints);
            pagePanel1.add(renwuContents);
        }
        String[] com = new String[7];
        for (int i = 0; i < 7; i++) {
            com[i] = contentMap.get("content" + i);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar rightNow = Calendar.getInstance();
        String[] weeks = new String[]{"七", "一", "二", "三", "四", "五", "六",};
        rightNow.setTime(new Date());
        int week = rightNow.get(Calendar.DAY_OF_WEEK) - 1;
        int difference = 1 - (week == 0 ? 7 : week);
        rightNow.add(Calendar.DAY_OF_WEEK, difference);
        for (int i = 5; i < 12; i++) {
            jTextFields2 = new ArrayList<JTextField>();
            for (int j = 0; j <= 5; j++) {
                JTextField weelText = null;
                if (j == 0) {
                    weelText = new JTextField(weeks[rightNow.get(Calendar.DAY_OF_WEEK) - 1] + "(" + sdf.format(rightNow.getTime()) + ")");
                    weelText.setColumns(8);
                    weelText.setHorizontalAlignment(JTextField.CENTER);
                    gridBagConstraints.gridwidth = 1;
                } else if (j == 1) {
                    weelText = new JTextField(com[i - 5].split(PropertiesTool.SGMTA_SPLIT)[j]);
                    weelText.setColumns(8);
                    weelText.setHorizontalAlignment(JTextField.CENTER);
                    gridBagConstraints.gridwidth = 1;
                } else if (j == 3 || j == 4 || j == 5) {
                    weelText = new JTextField(com[i - 5].split(PropertiesTool.SGMTA_SPLIT)[j]);
                    weelText.setColumns(3);
                    weelText.setHorizontalAlignment(JTextField.CENTER);
                    gridBagConstraints.gridwidth = 1;
                } else {
                    weelText = new JTextField(com[i - 5].split(PropertiesTool.SGMTA_SPLIT)[j]);
                    weelText.setColumns(42);
                    weelText.setHorizontalAlignment(JTextField.LEFT);
                    gridBagConstraints.gridwidth = 6;
                }
                weelText.setFont(font);
                jTextFields2.add(weelText);
                gridBagConstraints.gridy = i + 1;
                gridBagLayout.setConstraints(weelText, gridBagConstraints);
                // 完成比例
                pagePanel1.add(weelText);
                if (j == 5) {
                    if (i == 10 || i == 11) {
                        jCheckBoxsPage2.add(new JCheckBox("", false));
                    } else {
                        jCheckBoxsPage2.add(new JCheckBox("", true));
                    }
                    gridBagConstraints.gridy = i + 1;
                    gridBagLayout.setConstraints(jCheckBoxsPage2.get(i - 5), gridBagConstraints);
                    pagePanel1.add(jCheckBoxsPage2.get(i - 5));
                }
            }
            rightNow.add(Calendar.DAY_OF_WEEK, 1);
            jTextFieldList2.add(jTextFields2);
        }
    }

    /**
     * 周计划第三页内容
     *
     * @param pagePanel1
     */
    public void page3(JPanel pagePanel1) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        String[] title3 = contentMap.get("title3").split(PropertiesTool.SGMTA_SPLIT);
        for (int j = 0; j <= 5; j++) {
            JLabel renwuContents = null;
            if(j == 5){
                renwuContents = new JLabel(title3[j],JLabel.LEFT);
                gridBagConstraints.weightx = 0;
            }else{
                renwuContents = new JLabel(title3[j],JLabel.CENTER);
            }
            gridBagConstraints.gridy = 13;
            if (j == 1) {
                gridBagConstraints.gridwidth = 3;
            } else {
                gridBagConstraints.gridwidth = 1;
            }
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            renwuContents.setFont(font);
            gridBagLayout.setConstraints(renwuContents, gridBagConstraints);
            pagePanel1.add(renwuContents);
        }
        String[] pageContents3 = contentMap.get("pageContents3").split(PropertiesTool.SGMTA_SPLIT_LATER);
        for (int i = 13; i < 13 + pageContents3.length; i++) {
            jTextFields3 = new ArrayList<JTextField>();
            for (int j = 0; j < 5; j++) {
                JTextField textField = new JTextField(pageContents3[i - 13].split(PropertiesTool.SGMTA_SPLIT)[j]);
                if (j == 0) {
                    gridBagConstraints.gridwidth = 1;
                    textField.setHorizontalAlignment(JTextField.CENTER);
                    textField.setColumns(8);
                } else if (j == 1) {
                    gridBagConstraints.gridwidth = 3;
                    textField.setHorizontalAlignment(JTextField.LEFT);
                    textField.setColumns(27);
                } else {
                    gridBagConstraints.gridwidth = 1;
                    textField.setHorizontalAlignment(JTextField.CENTER);
                    textField.setColumns(3);
                }
                textField.setFont(font);
                jTextFields3.add(textField);
                gridBagConstraints.gridy = i + 1;
                gridBagLayout.setConstraints(textField, gridBagConstraints);
                // 完成比例
                pagePanel1.add(textField);
                if (j == 4) {
                    if (i == 13) {
                        jCheckBoxsPage3.add(new JCheckBox("", true));
                    } else {
                        jCheckBoxsPage3.add(new JCheckBox("", false));
                    }
                    gridBagLayout.setConstraints(jCheckBoxsPage3.get(i - 13), gridBagConstraints);
                    pagePanel1.add(jCheckBoxsPage3.get(i - 13));
                }
            }
            jTextFieldList3.add(jTextFields3);
        }
    }

    /**
     * 按钮事件
     *
     * @param jPanel
     */
    public void registerListener(final JPanel jPanel) {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 写入第一页配置文件
                    writePage1();
                    // 写入第二页配置文件
                    writePage2();
                    // 写入第三页配置文件
                    writePage3();
                    JOptionPane.showMessageDialog(jPanel, "保存成功！", "提示", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(jPanel, ex.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 写入第一页配置文件
                    writePage1();
                    // 写入第二页配置文件
                    writePage2();
                    // 写入第三页配置文件
                    writePage3();
                    Map<String, Object> controlMap = new HashMap<String, Object>();
                    boolean[] hiddenPage1 = new boolean[jCheckBoxsPage1.size()];
                    for (int i = 0; i < jCheckBoxsPage1.size(); i++) {
                        hiddenPage1[i] = jCheckBoxsPage1.get(i).isSelected() ? false : true;
                    }
                    controlMap.put("hiddenPage1", hiddenPage1);
                    boolean[] hidden = new boolean[jCheckBoxsPage2.size()];
                    for (int i = 0; i < jCheckBoxsPage2.size(); i++) {
                        hidden[i] = jCheckBoxsPage2.get(i).isSelected() ? false : true;
                    }
                    controlMap.put("jCheckBoxs", hidden);
                    boolean[] hiddenPage3 = new boolean[jCheckBoxsPage3.size()];
                    for (int i = 0; i < jCheckBoxsPage3.size(); i++) {
                        hiddenPage3[i] = jCheckBoxsPage3.get(i).isSelected() ? false : true;
                    }
                    controlMap.put("hiddenPage3", hiddenPage3);
                    // 创建文档
                    String msg = CreateExcel.mainCreate(controlMap);
                    JOptionPane.showMessageDialog(jPanel, msg, "提示", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(jPanel, ex.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * 写入配置文件第一页内容
     *
     * @return String
     */
    public String writePage1() {
        String fileNameText = fileName.getText();
        PropertiesTool.writeSet("config.properties", "fileName", fileNameText);
        String departmentText = department.getText();
        PropertiesTool.writeSet("config.properties", WeekPropertiesEnum.department, departmentText);
        String nameText = name.getText();
        PropertiesTool.writeSet("config.properties", WeekPropertiesEnum.name, nameText);
        String pageTem1 = "";
        String pageContents1 = "";
        int indexCell = 2;
        for (int i = 0; i < indexCell; i++) {
            pageTem1 = "";
            for (int j = 0; j < 9; j++) {
                if (j == 8) {
                    pageTem1 += jTextFieldList1.get(i).get(j).getText();
                } else {
                    pageTem1 += jTextFieldList1.get(i).get(j).getText() + PropertiesTool.READ_SGMTA_SPLIT;
                }
            }
            if (i == 1) {
                pageContents1 += pageTem1;
            } else {
                pageContents1 += pageTem1 + PropertiesTool.READ_SPLIT_LATER;
            }
        }
        PropertiesTool.writeSet("config.properties", "pageContents1", pageContents1);
        return "10";
    }

    /**
     * 写入第二页配置内容
     *
     * @return String
     */
    public String writePage2() {
        String pageTem2 = "";
        int indexCell = 7;
        for (int i = 0; i < indexCell; i++) {
            pageTem2 = "";
            for (int j = 0; j < 6; j++) {
                if (j == 5) {
                    pageTem2 += jTextFieldList2.get(i).get(j).getText();
                } else {
                    pageTem2 += jTextFieldList2.get(i).get(j).getText() + PropertiesTool.READ_SGMTA_SPLIT;
                }
            }
            PropertiesTool.writeSet("config.properties", "content" + i, pageTem2);
        }
        return "10";
    }

    /**
     * 写入第三页配置内容
     *
     * @return String
     */
    public String writePage3() {
        String pageTem3 = "";
        String pageContents3 = "";
        int indexCell = 2;
        for (int i = 0; i < indexCell; i++) {
            pageTem3 = "";
            for (int j = 0; j < 5; j++) {
                if (j == 4) {
                    pageTem3 += jTextFieldList3.get(i).get(j).getText();
                } else {
                    pageTem3 += jTextFieldList3.get(i).get(j).getText() + PropertiesTool.READ_SGMTA_SPLIT;
                }
            }
            if (i == 1) {
                pageContents3 += pageTem3;
            } else {
                pageContents3 += pageTem3 + PropertiesTool.READ_SPLIT_LATER;
            }
        }
        PropertiesTool.writeSet("config.properties", "pageContents3", pageContents3);
        return "10";
    }
}
