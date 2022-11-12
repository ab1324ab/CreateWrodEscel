package com.myigou.clientView.impl;

import com.myigou.clientService.CreateExcel2;
import com.myigou.clientService.response.DataSourceResponse;
import com.myigou.clientService.enums.WeekPropertiesEnum;
import com.myigou.clientView.FunctionInter;
import com.myigou.module.OnTop;
import com.myigou.tool.BusinessTool;
import com.myigou.tool.PropertiesTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2018/1/22.
 */
public class WeekPlanMake2 extends JPanel implements FunctionInter {
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 字体类型
    private Font font = new Font("微软雅黑", Font.PLAIN, 12);
    // 内容map
    private Map<String, String> contentMap = null;
    // 存储第一页面板里的部件 本周
    private Map<String, List<Object>> tswkMap = null;
    // 存储第二页面板里的部件 下周
    private Map<String, List<Object>> nxvWkMap = null;
    // 余留问题；需其它部门或领导协助解决的事宜；工作中的不足和需改进之处
    private Map<String, Object> troubleShootingMap = new HashMap<String, Object>();
    // 文字颜色
    private Color redColor = null;
    // 面板颜色
    Color panelColor = null;

    public WeekPlanMake2() {
        contentMap = PropertiesTool.redConfigFile("config.properties");
        // 面板文字颜色
        String[] colorPanelValue= contentMap.get("colorPanelValue").split(",");
        redColor = new Color(Integer.parseInt(colorPanelValue[0]), Integer.parseInt(colorPanelValue[1]), Integer.parseInt(colorPanelValue[2]));
        // 面板颜色
        String[] colorList = contentMap.get("colorExamplesValue").split(",");
        int r = Integer.parseInt(colorList[0]);
        int g = Integer.parseInt(colorList[1]);
        int b = Integer.parseInt(colorList[2]);
        panelColor = new Color(r,g,b);
    }

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        this.jPanelWriting(jPanel, jFrame);
        this.initComponent();
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame,Font font) {
        JLabel title = new JLabel("周计划生成第二版",JLabel.CENTER);
        title.setFont(font);
        jPanel.add(title);
        return jPanel;
    }

    /**
     * 控制界面
     *
     * @param jPanel
     * @param jFrame
     * @return
     */
    public JPanel jPanelWriting(JPanel jPanel, JFrame jFrame) {
        OnTop onTop = new OnTop();
        Map<String, Map<String, JPanel>> jPanelMap = new HashMap<String, Map<String, JPanel>>();
        int countPanel = 6;
        for (int i = 0; i < countPanel; i++) {
            jPanelMap.put(String.valueOf(i), getJPanelMap(i));
        }
        JPanel erJpanel = new JPanel();
        erJpanel.setLayout(new BorderLayout());
        JPanel ctrlonPanel = new JPanel();
        JLabel explain = new JLabel("文档名:");
        explain.setFont(new Font("微软雅黑", Font.BOLD, 14));
        ctrlonPanel.add(explain);
        JTextField fileName = new JTextField();
        fileName.setMinimumSize(new Dimension(350,30));
        fileName.setPreferredSize(new Dimension(350,30));
        fileName.setForeground(redColor);
        SimpleDateFormat fileFormat = new SimpleDateFormat(contentMap.get("params"));
        String fileDate = fileFormat.format(new Date());
        String fName = contentMap.get("fileJText").replaceFirst("%t",fileDate).replaceFirst("%n",contentMap.get("name"));
        fileName.setFont(font);
        fileName.setText(fName);
        ctrlonPanel.add(fileName);
        JButton serveButton = new JButton("获取周计划源");
        serveButton.setFont(font);
        // 去掉按钮文字周围焦点
        serveButton.setFocusPainted(false);
        Dimension preferredSize = new Dimension(150, 30);
        serveButton.setPreferredSize(preferredSize);
        ctrlonPanel.add(serveButton);
        serveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileSystemView fsv = FileSystemView.getFileSystemView();
            File homeFile = fsv.getHomeDirectory();
            chooser.setCurrentDirectory(homeFile);

            // 设置只能选择文件 Escel文档
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Escel文档", "xlsx");
            chooser.setFileFilter(filter);
            String directoryURL = null;
            int returnVal = chooser.showOpenDialog(jFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                directoryURL = chooser.getSelectedFile().getPath();
                chooser.hide();
            }
            if (directoryURL == null) {
                return;
            }
            List<String> weekPlanList = new ArrayList<String>();
            CreateExcel2 excel2 = new CreateExcel2();
            DataSourceResponse dataSources = excel2.obtainingDataSources(weekPlanList,directoryURL);
            if (dataSources.getStatus() != null) {
                JOptionPane.showMessageDialog(jPanel, dataSources.getStatus(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
//                String string = "功能开发/后台组&配合测询&高&3&30%&侯文康&只有调账功能还不通";
//                String string1 = "功能开发/后台组&配合测询&中&2&10%&侯文康&只有还不通";
//                String string2 = "功能开发/后台组&配fasfa询&低&6&20%&侯文康&有调账功能还";
//                List<String> weekPlanList1 = new ArrayList<String>();
//                weekPlanList1.add(string);
//                weekPlanList1.add(string1);
//                weekPlanList1.add(string2);
//                Map<Integer, String> weekPlanMap = new HashMap<Integer, String>();
//                weekPlanMap.put(4, string);
//                weekPlanMap.put(5, string1);
//                weekPlanMap.put(6, string2);
//                weekPlanMap.put(7, string1);
//                weekPlanMap.put(8, string2);
            // 本周
            editBox(tswkMap, dataSources.getDataSource(), PropertiesTool.READ_SGMTA_SPLIT);
            // 下周
            //editBox(nxvWkMap, weekPlanList1, PropertiesTool.READ_SGMTA_SPLIT);
            // 余留问题；需其它部门或领导协助解决的事宜；工作中的不足和需改进之处
            //editBox(troubleShootingMap, weekPlanMap);
        });
        // 生成文档按钮
        JButton makeButton = new JButton("生成文档");
        makeButton.setFont(font);
        // 去掉按钮文字周围焦点
        makeButton.setFocusPainted(false);
        makeButton.setPreferredSize(preferredSize);
        ctrlonPanel.add(makeButton);
        makeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateExcel2 createExcel2 = new CreateExcel2();
                // 本周 写入配置文件
                createExcel2.serveWeedPlanProperties(tswkMap, WeekPropertiesEnum.tswMapLine);
                // 下周 写入配置文件
                createExcel2.serveWeedPlanProperties(nxvWkMap, WeekPropertiesEnum.nxvWkMapLine);
                // 其余 写入配置文件
                createExcel2.serveTroubleShootingProperties(troubleShootingMap);
                // 生成Excel
                String msg = createExcel2.createExcel(tswkMap.size(), nxvWkMap.size(),fName);
                JOptionPane.showMessageDialog(jPanel, msg, "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        //ctrlonPanel.setBackground(new Color(210, 210, 210));
        erJpanel.add(ctrlonPanel, BorderLayout.NORTH);
        erJpanel.add(onTop.getJContentPane(countPanel, jPanelMap), BorderLayout.CENTER);
        jPanel.add(erJpanel, BorderLayout.CENTER);
        return jPanel;
    }

    public Map<String, JPanel> getJPanelMap(int i) {
        Map<String, JPanel> panelHashMap = new HashMap<String, JPanel>();
        if (i == 0) {
            JPanel panel = new JPanel();
            // 创建内容面板
            JPanel contentJPanel = new JPanel();
            contentJPanel.setLayout(gridBagLayout);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            // 本周计划
            JLabel title = new JLabel();
            title.setHorizontalAlignment(SwingConstants.LEFT);
            title.setFont(new Font("仿宋", Font.BOLD, 20));
            title.setText("本周计划");
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            //gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weightx = 1;  // 当窗口放大时，长度变
            gridBagConstraints.weighty = 1;  // 当窗口放大时，高度变
            //gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
            //gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(title, gridBagConstraints);
            contentJPanel.add(title);
            // 部门文本
            JLabel ranks = new JLabel();
            ranks.setFont(font);
            ranks.setText("部门：");
            ranks.setHorizontalAlignment(SwingConstants.CENTER);
            //gridBagConstraints.weightx = 1;
            //gridBagConstraints.weighty = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(ranks, gridBagConstraints);
            contentJPanel.add(ranks);
            //部门 编辑框
            JTextField ranksText = new JTextField(contentMap.get("department"));
            ranksText.setForeground(redColor);
            ranksText.setColumns(5);
            ranksText.setHorizontalAlignment(JTextField.CENTER);
            gridBagLayout.setConstraints(ranksText, gridBagConstraints);
            contentJPanel.add(ranksText);
            troubleShootingMap.put("line0", ranksText);
            // 计划人文本
            JLabel jLabel = new JLabel();
            jLabel.setFont(font);
            jLabel.setText("计划人：");
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gridBagLayout.setConstraints(jLabel, gridBagConstraints);
            contentJPanel.add(jLabel);
            //计划人 编辑框
            JTextField plannedText = new JTextField(contentMap.get("name"));
            plannedText.setForeground(redColor);
            plannedText.setColumns(5);
            plannedText.setHorizontalAlignment(JTextField.CENTER);
            gridBagLayout.setConstraints(plannedText, gridBagConstraints);
            contentJPanel.add(plannedText);
            troubleShootingMap.put("line1", plannedText);
            // 获取开始时间和结束时间
            List<Date> dateList = BusinessTool.getStartDateAndEndDate();
            // 总结日期
            JLabel summaryDate = new JLabel();
            summaryDate.setFont(font);
            summaryDate.setHorizontalAlignment(SwingConstants.CENTER);
            summaryDate.setText("总结日期：");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(summaryDate, gridBagConstraints);
            contentJPanel.add(summaryDate);
            // 总结日期编辑框
            SimpleDateFormat dateFormat = new SimpleDateFormat(contentMap.get("otherText"));
            JTextField summaryDateText = new JTextField(dateFormat.format(dateList.get(4)));
            summaryDateText.setForeground(redColor);
            summaryDateText.setColumns(5);
            summaryDateText.setHorizontalAlignment(JTextField.CENTER);
            gridBagLayout.setConstraints(summaryDateText, gridBagConstraints);
            contentJPanel.add(summaryDateText);
            troubleShootingMap.put("line2", summaryDateText);
            // 计划日期
            JLabel plannedDate = new JLabel();
            plannedDate.setFont(font);
            plannedDate.setHorizontalAlignment(SwingConstants.CENTER);
            plannedDate.setText("计划日期：");
            gridBagConstraints.gridwidth = 2;
            gridBagLayout.setConstraints(plannedDate, gridBagConstraints);
            contentJPanel.add(plannedDate);
            // 计划日期编辑框
            JTextField plannedDateText = new JTextField(
                    dateFormat.format(dateList.get(0)) + "-" + dateFormat.format(dateList.get(4)));
            plannedDateText.setForeground(redColor);
            plannedDateText.setColumns(5);
            plannedDateText.setHorizontalAlignment(JTextField.CENTER);
            gridBagLayout.setConstraints(plannedDateText, gridBagConstraints);
            contentJPanel.add(plannedDateText);
            troubleShootingMap.put("line3", plannedDateText);
            gridBagConstraints.gridy = 2;
            // 任务人/组别
            JLabel personGroup = new JLabel();
            personGroup.setFont(font);
            personGroup.setHorizontalAlignment(SwingConstants.CENTER);
            personGroup.setText("任务名/组别");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(personGroup, gridBagConstraints);
            contentJPanel.add(personGroup);
            // 任务内容
            JLabel mandateContent = new JLabel();
            mandateContent.setFont(font);
            mandateContent.setHorizontalAlignment(SwingConstants.CENTER);
            mandateContent.setText("任务内容");
            gridBagConstraints.gridwidth = 3;
            gridBagLayout.setConstraints(mandateContent, gridBagConstraints);
            contentJPanel.add(mandateContent);
            // 难易度
            JLabel difficulty = new JLabel();
            difficulty.setFont(font);
            difficulty.setHorizontalAlignment(SwingConstants.CENTER);
            difficulty.setText("难易度");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(difficulty, gridBagConstraints);
            contentJPanel.add(difficulty);
            // 预计完成时间
            JLabel projectedCompletion = new JLabel();
            projectedCompletion.setFont(font);
            projectedCompletion.setHorizontalAlignment(SwingConstants.CENTER);
            projectedCompletion.setText("预计完成时间");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(projectedCompletion, gridBagConstraints);
            contentJPanel.add(projectedCompletion);
            // 完成比例
            JLabel percentage = new JLabel();
            percentage.setFont(font);
            percentage.setHorizontalAlignment(SwingConstants.CENTER);
            percentage.setText("完成比例");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(percentage, gridBagConstraints);
            contentJPanel.add(percentage);
            // 跟进人
            JLabel head = new JLabel();
            head.setFont(font);
            head.setHorizontalAlignment(SwingConstants.CENTER);
            head.setText("跟进人");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(head, gridBagConstraints);
            contentJPanel.add(head);
            // 完成情况
            JLabel status = new JLabel();
            status.setFont(font);
            status.setHorizontalAlignment(SwingConstants.CENTER);
            status.setText("完成情况");
            gridBagConstraints.gridwidth = 3;
            gridBagLayout.setConstraints(status, gridBagConstraints);
            contentJPanel.add(status);
            String tswkText = contentMap.get("tswkText");
            int tswkTextLine = Integer.parseInt(tswkText) + 3;
            // 最高显示100行
            if(103 < tswkTextLine){
                tswkTextLine = 103;
            }
            tswkMap = new HashMap<String, List<Object>>();
            for (int j = 3; j < tswkTextLine; j++) {
                List<Object> tswkList = new ArrayList<Object>();
                gridBagConstraints.gridy = j;
                // 任务人/组别编辑框
                JTextField personGroupText = new JTextField();
                personGroupText.setForeground(redColor);
                personGroupText.setColumns(5);
                gridBagConstraints.gridwidth = 1;
                personGroupText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(personGroupText, gridBagConstraints);
                contentJPanel.add(personGroupText);
                tswkList.add(personGroupText);
                // 任务内容编辑框
                JTextField mandateContentText = new JTextField();
                mandateContentText.setForeground(redColor);
                mandateContentText.setColumns(20);
                gridBagConstraints.gridwidth = 3;
                mandateContentText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(mandateContentText, gridBagConstraints);
                contentJPanel.add(mandateContentText);
                tswkList.add(mandateContentText);
                // 难易度编辑框
                String nations[] = contentMap.get("facilityValueText").split(",");
                JComboBox jcb1 = new JComboBox(nations);
                jcb1.setSelectedItem("");
                // JTextField difficultyText = new JTextField();
                jcb1.setMaximumRowCount(3);
                Dimension dimension = new Dimension(20, 10);
                jcb1.setSize(dimension);
                gridBagConstraints.gridwidth = 1;
                gridBagLayout.setConstraints(jcb1, gridBagConstraints);
                contentJPanel.add(jcb1);
                tswkList.add(jcb1);
                // 预计完成时间编辑框
                JTextField projectedCompletionText = new JTextField();
                projectedCompletionText.setForeground(redColor);
                projectedCompletionText.setColumns(3);
                gridBagConstraints.gridwidth = 1;
                projectedCompletionText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(projectedCompletionText, gridBagConstraints);
                contentJPanel.add(projectedCompletionText);
                tswkList.add(projectedCompletionText);
                // 完成比例编辑框
                String percentageText[] = {"", "10%", "20%", "30%", "50%", "60%", "70%", "80%", "90%", "100%"};
                JComboBox percentageBox = new JComboBox(percentageText);
                percentageBox.setSelectedItem("");
                percentageBox.setMaximumRowCount(5);
                gridBagConstraints.gridwidth = 1;
                gridBagLayout.setConstraints(percentageBox, gridBagConstraints);
                contentJPanel.add(percentageBox);
                tswkList.add(percentageBox);
                // 跟进人编辑框
                JTextField headText = new JTextField();
                headText.setForeground(redColor);
                headText.setColumns(5);
                gridBagConstraints.gridwidth = 1;
                headText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(headText, gridBagConstraints);
                contentJPanel.add(headText);
                tswkList.add(headText);
                // 完成情况 编辑框
                JTextField statusText = new JTextField();
                statusText.setForeground(redColor);
                statusText.setColumns(20);
                gridBagConstraints.gridwidth = 3;
                statusText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(statusText, gridBagConstraints);
                contentJPanel.add(statusText);
                tswkList.add(statusText);
                int line = j;
                tswkMap.put(WeekPropertiesEnum.line + (line - 3), tswkList);
            }
            JScrollPane jScrollPane = new JScrollPane(contentJPanel);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            String[] colorList = contentMap.get("colorExamplesValue").split(",");
            int r = Integer.parseInt(colorList[0]);
            int g = Integer.parseInt(colorList[1]);
            int b = Integer.parseInt(colorList[2]);
            Color color = new Color(r,g,b);
            contentJPanel.setBackground(color);
            panel.setLayout(new BorderLayout());
            panel.add(jScrollPane, BorderLayout.CENTER);
            JButton newText = new JButton("增加");
            panel.add(newText, BorderLayout.SOUTH);
            panelHashMap.put("本周计划", panel);
        } else if (i == 1) {
            JPanel panel = new JPanel();
            // 创建内容面板
            JPanel contentJPanel = new JPanel();
            contentJPanel.setLayout(gridBagLayout);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            // 下周工作计划
            JLabel title = new JLabel();
            title.setFont(new Font("仿宋", Font.BOLD, 20));
            title.setHorizontalAlignment(SwingConstants.LEFT);
            title.setText("下周工作计划");
            //gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.gridwidth = 5;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(title, gridBagConstraints);
            contentJPanel.add(title);
            // 任务人/组别
            JLabel personGroup = new JLabel();
            personGroup.setFont(font);
            personGroup.setHorizontalAlignment(SwingConstants.CENTER);
            personGroup.setText("任务名/组别");
            gridBagConstraints.gridy = 1;
            //gridBagConstraints.weightx = 1;
            //gridBagConstraints.weighty = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(personGroup, gridBagConstraints);
            contentJPanel.add(personGroup);
            // 任务内容
            JLabel mandateContent = new JLabel();
            mandateContent.setFont(font);
            mandateContent.setHorizontalAlignment(SwingConstants.CENTER);
            mandateContent.setText("任务内容");
            gridBagConstraints.gridwidth = 3;
            gridBagLayout.setConstraints(mandateContent, gridBagConstraints);
            contentJPanel.add(mandateContent);
            // 难易度
            JLabel difficulty = new JLabel();
            difficulty.setFont(font);
            difficulty.setHorizontalAlignment(SwingConstants.CENTER);
            difficulty.setText("难易度");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(difficulty, gridBagConstraints);
            contentJPanel.add(difficulty);
            // 预计完成时间
            JLabel projectedCompletion = new JLabel();
            projectedCompletion.setFont(font);
            projectedCompletion.setHorizontalAlignment(SwingConstants.CENTER);
            projectedCompletion.setText("预计完成时间");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(projectedCompletion, gridBagConstraints);
            contentJPanel.add(projectedCompletion);
            // 完成比例
            JLabel percentage = new JLabel();
            percentage.setFont(font);
            percentage.setHorizontalAlignment(SwingConstants.CENTER);
            percentage.setText("完成比例");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(percentage, gridBagConstraints);
            contentJPanel.add(percentage);
            // 跟进人
            JLabel head = new JLabel();
            head.setFont(font);
            head.setHorizontalAlignment(SwingConstants.CENTER);
            head.setText("跟进人");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(head, gridBagConstraints);
            contentJPanel.add(head);
            // 完成情况
            JLabel status = new JLabel();
            status.setFont(font);
            status.setHorizontalAlignment(SwingConstants.CENTER);
            status.setText("完成情况");
            gridBagConstraints.gridwidth = 3;
            gridBagLayout.setConstraints(status, gridBagConstraints);
            contentJPanel.add(status);
            nxvWkMap = new HashMap<String, List<Object>>();
            String nxvwkText = contentMap.get("nxvwkText");
            int nxvwkTextLine = Integer.parseInt(nxvwkText) + 2;
            // 最高显示100行
            if(102 < nxvwkTextLine){
                nxvwkTextLine = 102;
            }
            for (int j = 2; j < nxvwkTextLine; j++) {
                int jLine = j;
                List<Object> nxvWkList = new ArrayList<Object>();
                gridBagConstraints.gridy = j;
                // 任务人/组别编辑框
                JTextField personGroupText = new JTextField();
                personGroupText.setForeground(redColor);
                personGroupText.setColumns(5);
                gridBagConstraints.gridwidth = 1;
                personGroupText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(personGroupText, gridBagConstraints);
                contentJPanel.add(personGroupText);
                nxvWkList.add(personGroupText);
                // 任务内容编辑框
                JTextField mandateContentText = new JTextField();
                mandateContentText.setForeground(redColor);
                mandateContentText.setColumns(20);
                gridBagConstraints.gridwidth = 3;
                mandateContentText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(mandateContentText, gridBagConstraints);
                contentJPanel.add(mandateContentText);
                nxvWkList.add(mandateContentText);
                // 难易度编辑框
                String nations[] = {"", "中", "低", "高"};
                JComboBox degreeBox = new JComboBox(nations);
                degreeBox.setSelectedItem("");
                degreeBox.setMaximumRowCount(3);
                Dimension dimension = new Dimension(20, 10);
                degreeBox.setSize(dimension);
                gridBagConstraints.gridwidth = 1;
                gridBagLayout.setConstraints(degreeBox, gridBagConstraints);
                contentJPanel.add(degreeBox);
                nxvWkList.add(degreeBox);
                // 预计完成时间编辑框
                JTextField projectedCompletionText = new JTextField();
                projectedCompletionText.setForeground(redColor);
                projectedCompletionText.setColumns(3);
                gridBagConstraints.gridwidth = 1;
                projectedCompletionText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(projectedCompletionText, gridBagConstraints);
                contentJPanel.add(projectedCompletionText);
                nxvWkList.add(projectedCompletionText);
                // 完成比例编辑框
                String percentageText[] = {"", "10%", "20%", "30%", "50%", "60%", "70%", "80%", "90%", "100%"};
                JComboBox percentageBox = new JComboBox(percentageText);
                percentageBox.setSelectedItem("");
                percentageBox.setMaximumRowCount(5);
                gridBagConstraints.gridwidth = 1;
                gridBagLayout.setConstraints(percentageBox, gridBagConstraints);
                contentJPanel.add(percentageBox);
                nxvWkList.add(percentageBox);
                // 跟进人编辑框
                JTextField headText = new JTextField();
                headText.setForeground(redColor);
                headText.setColumns(5);
                gridBagConstraints.gridwidth = 1;
                headText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(headText, gridBagConstraints);
                contentJPanel.add(headText);
                nxvWkList.add(headText);
                // 完成情况 编辑框
                JTextField statusText = new JTextField();
                statusText.setForeground(redColor);
                statusText.setColumns(20);
                gridBagConstraints.gridwidth = 3;
                statusText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(statusText, gridBagConstraints);
                contentJPanel.add(statusText);
                nxvWkList.add(statusText);

                nxvWkMap.put(WeekPropertiesEnum.line + (jLine - 2), nxvWkList);
            }
            JScrollPane jScrollPane = new JScrollPane(contentJPanel);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            contentJPanel.setBackground(panelColor);
            panel.setLayout(new BorderLayout());
            panel.add(jScrollPane, BorderLayout.CENTER);
            JButton newText = new JButton("增加");
            panel.add(newText, BorderLayout.SOUTH);
            panelHashMap.put("下周工作计划", panel);
        } else if (i == 2) {
            JPanel panel = new JPanel();
            // 创建内容面板
            JPanel contentJPanel = new JPanel();
            contentJPanel.setLayout(gridBagLayout);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            // 余留问题 文字
            JLabel problems = new JLabel();
            problems.setFont(new Font("仿宋", Font.BOLD, 20));
            problems.setText("余留问题");
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;

            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(problems, gridBagConstraints);
            contentJPanel.add(problems);
            // 余留问题 文本域
            JTextArea leaveArea = new JTextArea(1, 1);
            leaveArea.setForeground(redColor);
            JScrollPane jScrollPane = new JScrollPane();
            jScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            jScrollPane.setViewportView(leaveArea);
            leaveArea.setFont(new Font("仿宋", Font.BOLD, 20));
            leaveArea.setLineWrap(true);
            leaveArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(jScrollPane, gridBagConstraints);
            contentJPanel.add(jScrollPane);

            troubleShootingMap.put("line4", leaveArea);
            panel.setLayout(new BorderLayout());
            contentJPanel.setBackground(panelColor);
            panel.add(contentJPanel, BorderLayout.CENTER);
            panelHashMap.put("余留问题", panel);
        } else if (i == 3) {
            Font font = new Font("仿宋", Font.BOLD, 15);
            JPanel panel = new JPanel();
            JPanel contentJPanel = new JPanel();
            contentJPanel.setLayout(gridBagLayout);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            // 余留问题 文字
            JLabel problems = new JLabel();
            problems.setFont(new Font("仿宋", Font.BOLD, 20));
            problems.setText("需其它部门或领导协助解决的事宜");
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(problems, gridBagConstraints);
            contentJPanel.add(problems);
            // 紧急 文本
            JLabel urgentLabel = new JLabel("紧急：");
            urgentLabel.setFont(font);
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(urgentLabel, gridBagConstraints);
            contentJPanel.add(urgentLabel);
            // 紧急文本 文本域
            JTextArea urgentArea = new JTextArea(4, 5);
            urgentArea.setForeground(redColor);
            JScrollPane urgentScrollPane = new JScrollPane();
            urgentScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            urgentScrollPane.setViewportView(urgentArea);
            urgentArea.setFont(new Font("仿宋", Font.BOLD, 20));
            urgentArea.setLineWrap(true);
            urgentArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 2;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(urgentScrollPane, gridBagConstraints);
            contentJPanel.add(urgentScrollPane);
            troubleShootingMap.put("line5", urgentArea);
            // 一般 文本
            JLabel commonlyLabel = new JLabel("一般：");
            commonlyLabel.setFont(font);
            gridBagConstraints.gridy = 3;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(commonlyLabel, gridBagConstraints);
            contentJPanel.add(commonlyLabel);
            // 一般 文本域
            JTextArea commonlyArea = new JTextArea(4, 5);
            commonlyArea.setForeground(redColor);
            JScrollPane commonlyScrollPane = new JScrollPane();
            commonlyScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            commonlyScrollPane.setViewportView(commonlyArea);
            commonlyArea.setFont(new Font("仿宋", Font.BOLD, 20));
            commonlyArea.setLineWrap(true);
            commonlyArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 4;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(commonlyScrollPane, gridBagConstraints);
            contentJPanel.add(commonlyScrollPane);
            troubleShootingMap.put("line6", commonlyArea);
            // 稍缓 文本
            JLabel slowlyLabel = new JLabel("稍缓：");
            slowlyLabel.setFont(font);
            gridBagConstraints.gridy = 5;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(slowlyLabel, gridBagConstraints);
            contentJPanel.add(slowlyLabel);
            // 稍缓 文本域
            JTextArea slowlyArea = new JTextArea(4, 5);
            slowlyArea.setForeground(redColor);
            JScrollPane slowlyScrollPane = new JScrollPane();
            slowlyScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            slowlyScrollPane.setViewportView(slowlyArea);
            slowlyArea.setFont(new Font("仿宋", Font.BOLD, 20));
            slowlyArea.setLineWrap(true);
            slowlyArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 6;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(slowlyScrollPane, gridBagConstraints);
            contentJPanel.add(slowlyScrollPane);
            troubleShootingMap.put("line7", slowlyArea);

            panel.setLayout(new BorderLayout());
            contentJPanel.setBackground(panelColor);
            panel.add(contentJPanel, BorderLayout.CENTER);
            panelHashMap.put("需其它部门或领导协助解决的事宜", panel);
        } else if (i == 4) {
            JPanel panel = new JPanel();
            // 创建内容面板
            JPanel contentJPanel = new JPanel();
            contentJPanel.setLayout(gridBagLayout);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            // 工作中的不足和需改进之处 文字
            JLabel problems = new JLabel();
            problems.setFont(new Font("仿宋", Font.BOLD, 20));
            problems.setText("工作中的不足和需改进之处");
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;

            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(problems, gridBagConstraints);
            contentJPanel.add(problems);
            // 工作中的不足和需改进之处 文本域
            JTextArea improvementJTextArea = new JTextArea(1, 1);
            improvementJTextArea.setForeground(redColor);
            JScrollPane jScrollPane = new JScrollPane();
            jScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            jScrollPane.setViewportView(improvementJTextArea);
            improvementJTextArea.setFont(new Font("仿宋", Font.BOLD, 20));
            improvementJTextArea.setLineWrap(true);
            // jTextArea.setBounds(10, 0,50,50);
            improvementJTextArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(jScrollPane, gridBagConstraints);
            contentJPanel.add(jScrollPane);
            troubleShootingMap.put("line8", improvementJTextArea);

            panel.setLayout(new BorderLayout());
            panel.add(contentJPanel, BorderLayout.CENTER);
            contentJPanel.setBackground(panelColor);
            panelHashMap.put("工作中的不足和需改进之处", panel);
        } else if (i == 5) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(redColor);
            jLabel.setText("本周总结");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(new BorderLayout());
            panel.setBackground(panelColor);
            panel.add(jLabel, BorderLayout.CENTER);
            panelHashMap.put("本周总结", panel);
        }
        return panelHashMap;
    }


    /**
     * （下周、本周）计划写入编辑框
     *
     * @param contentColumnMap 内容行
     * @param contentLineList  内容列
     * @param decollator       分隔符
     */
    public void editBox(Map<String, List<Object>> contentColumnMap, List<String> contentLineList, String decollator) {
        for (int c = 0; c < contentColumnMap.size(); c++) {
            List<Object> entryValue = (List<Object>) contentColumnMap.get(WeekPropertiesEnum.line + c);
            for (int i = 0; i < 7; i++) {
                if (i == 2 || i == 4) {
                    JComboBox jComboBox = (JComboBox) entryValue.get(i);
                    jComboBox.setSelectedItem("");
                } else {
                    JTextField jTextField = (JTextField) entryValue.get(i);
                    jTextField.setText("");
                }
            }
        }
        for (int mapLine = 0; mapLine < contentLineList.size(); mapLine++) {
            List<Object> entryValue = (List<Object>) contentColumnMap.get(WeekPropertiesEnum.line + mapLine);
            for (int i = 0; i < 7; i++) {
                String vstr = contentLineList.get(mapLine).split(decollator)[i];
                if (WeekPropertiesEnum.ASK.equals(vstr)) {
                    vstr = "";
                }
                if (i == 2 || i == 4) {
                    JComboBox jComboBox = (JComboBox) entryValue.get(i);
                    jComboBox.setSelectedItem(vstr);
                } else {
                    JTextField jTextField = (JTextField) entryValue.get(i);
                    jTextField.setText(vstr);
                }
            }
        }
    }

    /**
     * 余留问题；需其它部门或领导协助解决的事宜；工作中的不足和需改进之处
     *
     * @param contentColumnMap 故障排除部件
     * @param contentLineMap   内容列表
     */
    public void editBox(Map<String, Object> contentColumnMap, Map<Integer, String> contentLineMap) {
        for (int i = 0; i < contentColumnMap.size(); i++) {
            if (i > 3) {
                JTextArea jTextArea = (JTextArea) contentColumnMap.get(WeekPropertiesEnum.line + i);
                jTextArea.setText(contentLineMap.get(i));
            }
        }
    }

    /**
     * 初始化部件内容
     */
    public void initComponent() {
        List<String> tswkPlanList = new ArrayList<String>();
        for (int tswk = 0; tswk < tswkMap.size(); tswk++) {
            String conten = contentMap.get(WeekPropertiesEnum.tswMapLine + tswk);
            if (!StringUtils.isEmpty(conten) && conten != null) {
                tswkPlanList.add(conten);
            }
        }
        List<String> nxvWkPlanList = new ArrayList<String>();
        for (int nxvWk = 0; nxvWk < nxvWkMap.size(); nxvWk++) {
            String conten = contentMap.get(WeekPropertiesEnum.nxvWkMapLine + nxvWk);
            if (!StringUtils.isEmpty(conten) && conten != null) {
                nxvWkPlanList.add(conten);
            }
        }
        Map<Integer, String> weekPlanMap = new HashMap<Integer, String>();
        weekPlanMap.put(4, contentMap.get(WeekPropertiesEnum.leaveArea));
        weekPlanMap.put(5, contentMap.get(WeekPropertiesEnum.urgentArea));
        weekPlanMap.put(6, contentMap.get(WeekPropertiesEnum.commonlyArea));
        weekPlanMap.put(7, contentMap.get(WeekPropertiesEnum.slowlyArea));
        weekPlanMap.put(8, contentMap.get(WeekPropertiesEnum.improvementJTextArea));
        // 本周
        editBox(tswkMap, tswkPlanList, PropertiesTool.READ_SGMTA_SPLIT);
        // 下周
        editBox(nxvWkMap, nxvWkPlanList, PropertiesTool.READ_SGMTA_SPLIT);
        // 余留问题；需其它部门或领导协助解决的事宜；工作中的不足和需改进之处
        editBox(troubleShootingMap, weekPlanMap);
    }
}
