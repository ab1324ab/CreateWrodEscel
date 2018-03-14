package com.myigou.client.impl;

import com.myigou.client.FunctionInter;
import com.myigou.module.OnTop;
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
 *         Created by ab1324ab on 2018/1/22.
 */
public class WeekPlanMake2 extends JPanel implements FunctionInter {
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 字体类型
    Font font = new Font("仿宋", Font.BOLD, 12);
    // 内容map
    private Map<String, String> contentMap = null;
    // 存储第一页面板里的部件 本周
    Map<String, List<Object>> tswkMap = null;
    // 存储第二页面板里的部件 下周
    Map<String, List<Object>> nxvWkMap = null;
    public WeekPlanMake2() {
        contentMap = PropertiesTool.redConfigFile("config.properties");
    }

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        this.jPanelWriting(jPanel, jFrame);

        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame) {
        JLabel titleFileName = new JLabel("周计划生成第二版");
        jPanel.add(titleFileName);
        return jPanel;
    }

    public JPanel jPanelWriting(JPanel jPanel, JFrame jFrame) {
        OnTop onTop = new OnTop();

        Map<String, Map<String, JPanel>> jPanelMap = new HashMap<String, Map<String, JPanel>>();
        int countPanel = 6;
        for (int i = 0; i < countPanel; i++) {
            jPanelMap.put(String.valueOf(i), getJPanelMap(i));
        }
        ;
        JPanel erJpanel = new JPanel();
        erJpanel.setLayout(new BorderLayout());
        JPanel ctrlonPanel = new JPanel();
        JLabel explain = new JLabel("控制");
        explain.setFont(new Font("仿宋", Font.BOLD, 25));
        ctrlonPanel.add(explain);

        JButton serveButton = new JButton("保存文档");
        serveButton.setFont(font);
        // 去掉按钮文字周围焦点
        serveButton.setFocusPainted(false);
        Dimension preferredSize = new Dimension(200, 35);
        serveButton.setPreferredSize(preferredSize);
        ctrlonPanel.add(serveButton);
        serveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = "功能开发/后台组,配合测询,高,3,30%,侯文康,只有调账功能还不通";
                String string1="功能开发/后台组,配合测询,中,2,10%,侯文康,只有还不通";
                String string2="功能开发/后台组,配fasfa询,低,6,20%,侯文康,有调账功能还";
                List<String>  weekPlanList = new ArrayList<String>();
                weekPlanList.add(string);
                weekPlanList.add(string1);
                weekPlanList.add(string2);
                // 本周
                editBox(tswkMap,weekPlanList,",");
                // 下周
                editBox(nxvWkMap,weekPlanList,",");

            }
        });

        JButton makeButton = new JButton("生成文档");
        // 去掉按钮文字周围焦点
        makeButton.setFocusPainted(false);
        makeButton.setPreferredSize(preferredSize);
        ctrlonPanel.add(makeButton);

        ctrlonPanel.setBackground(new Color(210, 210, 210));

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
            title.setFont(font);
            title.setForeground(new Color(255, 51, 51));
            title.setHorizontalAlignment(SwingConstants.LEFT);
            title.setFont(new Font("仿宋", Font.BOLD, 20));
            title.setText("本周计划");
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(title, gridBagConstraints);
            contentJPanel.add(title);
            // 部门文本
            JLabel ranks = new JLabel();
            ranks.setFont(font);
            ranks.setForeground(new Color(255, 51, 51));
            ranks.setText("部门：");
            ranks.setHorizontalAlignment(SwingConstants.CENTER);
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(ranks, gridBagConstraints);
            contentJPanel.add(ranks);
            //部门 编辑框
            JTextField ranksText = new JTextField(contentMap.get("department"));
            ranksText.setColumns(5);
            ranksText.setHorizontalAlignment(JTextField.CENTER);
            gridBagLayout.setConstraints(ranksText, gridBagConstraints);
            contentJPanel.add(ranksText);
            // 计划人文本
            JLabel jLabel = new JLabel();
            jLabel.setFont(font);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("计划人：");
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gridBagLayout.setConstraints(jLabel, gridBagConstraints);
            contentJPanel.add(jLabel);
            //计划人 编辑框
            JTextField plannedText = new JTextField(contentMap.get("name"));
            plannedText.setColumns(5);
            plannedText.setHorizontalAlignment(JTextField.CENTER);
            gridBagLayout.setConstraints(plannedText, gridBagConstraints);
            contentJPanel.add(plannedText);
            // 获取开始时间和结束时间
            List<String> dateAndEndDate = getStartDateAndEndDate();
            // 总结日期
            JLabel summaryDate = new JLabel();
            summaryDate.setFont(font);
            summaryDate.setForeground(new Color(255, 51, 51));
            summaryDate.setHorizontalAlignment(SwingConstants.CENTER);
            summaryDate.setText("总结日期：");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(summaryDate, gridBagConstraints);
            contentJPanel.add(summaryDate);
            // 总结日期编辑框
            JTextField summaryDateText = new JTextField(dateAndEndDate.get(6));
            summaryDateText.setColumns(5);
            summaryDateText.setHorizontalAlignment(JTextField.CENTER);
            gridBagLayout.setConstraints(summaryDateText, gridBagConstraints);
            contentJPanel.add(summaryDateText);
            // 计划日期
            JLabel plannedDate = new JLabel();
            plannedDate.setFont(font);
            plannedDate.setForeground(new Color(255, 51, 51));
            plannedDate.setHorizontalAlignment(SwingConstants.CENTER);
            plannedDate.setText("计划日期：");
            gridBagConstraints.gridwidth = 2;
            gridBagLayout.setConstraints(plannedDate, gridBagConstraints);
            contentJPanel.add(plannedDate);
            // 计划日期编辑框
            JTextField plannedDateText = new JTextField(dateAndEndDate.get(0) + "-" + dateAndEndDate.get(6));
            plannedDateText.setColumns(5);
            plannedDateText.setHorizontalAlignment(JTextField.CENTER);
            gridBagLayout.setConstraints(plannedDateText, gridBagConstraints);
            contentJPanel.add(plannedDateText);

            gridBagConstraints.gridy = 2;
            // 任务人/组别
            JLabel personGroup = new JLabel();
            personGroup.setFont(font);
            personGroup.setForeground(new Color(255, 51, 51));
            personGroup.setHorizontalAlignment(SwingConstants.CENTER);
            personGroup.setText("任务名/组别");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(personGroup, gridBagConstraints);
            contentJPanel.add(personGroup);
            // 任务内容
            JLabel mandateContent = new JLabel();
            mandateContent.setFont(font);
            mandateContent.setForeground(new Color(255, 51, 51));
            mandateContent.setHorizontalAlignment(SwingConstants.CENTER);
            mandateContent.setText("任务内容");
            gridBagConstraints.gridwidth = 3;
            gridBagLayout.setConstraints(mandateContent, gridBagConstraints);
            contentJPanel.add(mandateContent);
            // 难易度
            JLabel difficulty = new JLabel();
            difficulty.setFont(font);
            difficulty.setForeground(new Color(255, 51, 51));
            difficulty.setHorizontalAlignment(SwingConstants.CENTER);
            difficulty.setText("难易度");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(difficulty, gridBagConstraints);
            contentJPanel.add(difficulty);
            // 预计完成时间
            JLabel projectedCompletion = new JLabel();
            projectedCompletion.setFont(font);
            projectedCompletion.setForeground(new Color(255, 51, 51));
            projectedCompletion.setHorizontalAlignment(SwingConstants.CENTER);
            projectedCompletion.setText("预计完成时间");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(projectedCompletion, gridBagConstraints);
            contentJPanel.add(projectedCompletion);
            // 完成比例
            JLabel percentage = new JLabel();
            percentage.setFont(font);
            percentage.setForeground(new Color(255, 51, 51));
            percentage.setHorizontalAlignment(SwingConstants.CENTER);
            percentage.setText("完成比例");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(percentage, gridBagConstraints);
            contentJPanel.add(percentage);
            // 跟进人
            JLabel head = new JLabel();
            head.setFont(font);
            head.setForeground(new Color(255, 51, 51));
            head.setHorizontalAlignment(SwingConstants.CENTER);
            head.setText("跟进人");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(head, gridBagConstraints);
            contentJPanel.add(head);
            // 完成情况
            JLabel status = new JLabel();
            status.setFont(font);
            status.setForeground(new Color(255, 51, 51));
            status.setHorizontalAlignment(SwingConstants.CENTER);
            status.setText("完成情况");
            gridBagConstraints.gridwidth = 3;
            gridBagLayout.setConstraints(status, gridBagConstraints);
            contentJPanel.add(status);
            String[] str = {"功能开发/后台组", "配合测试同事测试智宝付自动轮询", "中", "3", "80%", "侯文康", "只有调账功能还不通"};
            tswkMap = new HashMap<String, List<Object>>();
            for (int j = 3; j < 14; j++) {
                List<Object> tswkList = new ArrayList<Object>();
                gridBagConstraints.gridy = j;
                // 任务人/组别编辑框
                JTextField personGroupText = new JTextField(str[0]);
                personGroupText.setColumns(5);
                gridBagConstraints.gridwidth = 1;
                personGroupText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(personGroupText, gridBagConstraints);
                contentJPanel.add(personGroupText);
                tswkList.add(personGroupText);
                // 任务内容编辑框
                JTextField mandateContentText = new JTextField(str[1]);
                mandateContentText.setColumns(20);
                gridBagConstraints.gridwidth = 3;
                mandateContentText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(mandateContentText, gridBagConstraints);
                contentJPanel.add(mandateContentText);
                tswkList.add(mandateContentText);
                // 难易度编辑框
                String nations[] = {"中", "低", "高"};
                JComboBox jcb1 = new JComboBox(nations);
                jcb1.setSelectedItem(str[2]);
                // JTextField difficultyText = new JTextField();
                jcb1.setMaximumRowCount(3);
                Dimension dimension = new Dimension(20, 10);
                jcb1.setSize(dimension);
                gridBagConstraints.gridwidth = 1;
                gridBagLayout.setConstraints(jcb1, gridBagConstraints);
                contentJPanel.add(jcb1);
                tswkList.add(jcb1);
                // 预计完成时间编辑框
                JTextField projectedCompletionText = new JTextField(str[3]);
                projectedCompletionText.setColumns(3);
                gridBagConstraints.gridwidth = 1;
                projectedCompletionText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(projectedCompletionText, gridBagConstraints);
                contentJPanel.add(projectedCompletionText);
                tswkList.add(projectedCompletionText);
                // 完成比例编辑框
                String percentageText[] = {"10%", "20%", "30%", "50%", "60%", "70%", "80%", "90%", "100%"};
                JComboBox percentageBox = new JComboBox(percentageText);
                percentageBox.setSelectedItem(str[4]);
                percentageBox.setMaximumRowCount(5);
                gridBagConstraints.gridwidth = 1;
                gridBagLayout.setConstraints(percentageBox, gridBagConstraints);
                contentJPanel.add(percentageBox);
                tswkList.add(percentageBox);
                // 跟进人编辑框
                JTextField headText = new JTextField(str[5]);
                headText.setColumns(5);
                gridBagConstraints.gridwidth = 1;
                headText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(headText, gridBagConstraints);
                contentJPanel.add(headText);
                tswkList.add(headText);
                // 完成情况 编辑框
                JTextField statusText = new JTextField(str[6]);
                statusText.setColumns(20);
                gridBagConstraints.gridwidth = 3;
                statusText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(statusText, gridBagConstraints);
                contentJPanel.add(statusText);
                tswkList.add(statusText);
                int line = j;
                tswkMap.put("line" + (line - 3), tswkList);
            }
            panel.setLayout(new BorderLayout());
            panel.add(contentJPanel, BorderLayout.NORTH);
            JButton newText = new JButton("增加");
            panel.add(newText);
            panelHashMap.put("本周计划", panel);
        } else if (i == 1) {
            JPanel panel = new JPanel();
            // 创建内容面板
            JPanel contentJPanel = new JPanel();
            contentJPanel.setLayout(gridBagLayout);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            // 本周计划
            JLabel title = new JLabel();
            title.setFont(new Font("仿宋", Font.BOLD, 20));
            title.setForeground(new Color(255, 51, 51));
            title.setHorizontalAlignment(SwingConstants.LEFT);
            title.setText("下周工作计划");
            //gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.gridwidth = 5;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(title, gridBagConstraints);
            contentJPanel.add(title);
            // 任务人/组别
            JLabel personGroup = new JLabel();
            personGroup.setFont(font);
            personGroup.setForeground(new Color(255, 51, 51));
            personGroup.setHorizontalAlignment(SwingConstants.CENTER);
            personGroup.setText("任务名/组别");
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(personGroup, gridBagConstraints);
            contentJPanel.add(personGroup);
            // 任务内容
            JLabel mandateContent = new JLabel();
            mandateContent.setFont(font);
            mandateContent.setForeground(new Color(255, 51, 51));
            mandateContent.setHorizontalAlignment(SwingConstants.CENTER);
            mandateContent.setText("任务内容");
            gridBagConstraints.gridwidth = 3;
            gridBagLayout.setConstraints(mandateContent, gridBagConstraints);
            contentJPanel.add(mandateContent);
            // 难易度
            JLabel difficulty = new JLabel();
            difficulty.setFont(font);
            difficulty.setForeground(new Color(255, 51, 51));
            difficulty.setHorizontalAlignment(SwingConstants.CENTER);
            difficulty.setText("难易度");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(difficulty, gridBagConstraints);
            contentJPanel.add(difficulty);
            // 预计完成时间
            JLabel projectedCompletion = new JLabel();
            projectedCompletion.setFont(font);
            projectedCompletion.setForeground(new Color(255, 51, 51));
            projectedCompletion.setHorizontalAlignment(SwingConstants.CENTER);
            projectedCompletion.setText("预计完成时间");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(projectedCompletion, gridBagConstraints);
            contentJPanel.add(projectedCompletion);
            // 完成比例
            JLabel percentage = new JLabel();
            percentage.setFont(font);
            percentage.setForeground(new Color(255, 51, 51));
            percentage.setHorizontalAlignment(SwingConstants.CENTER);
            percentage.setText("完成比例");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(percentage, gridBagConstraints);
            contentJPanel.add(percentage);
            // 跟进人
            JLabel head = new JLabel();
            head.setFont(font);
            head.setForeground(new Color(255, 51, 51));
            head.setHorizontalAlignment(SwingConstants.CENTER);
            head.setText("跟进人");
            gridBagConstraints.gridwidth = 1;
            gridBagLayout.setConstraints(head, gridBagConstraints);
            contentJPanel.add(head);
            // 完成情况
            JLabel status = new JLabel();
            status.setFont(font);
            status.setForeground(new Color(255, 51, 51));
            status.setHorizontalAlignment(SwingConstants.CENTER);
            status.setText("完成情况");
            gridBagConstraints.gridwidth = 3;
            gridBagLayout.setConstraints(status, gridBagConstraints);
            contentJPanel.add(status);
            String[] str = {"功能开发/后台组", "配合测试同事测试智宝付自动轮询", "中", "3", "80%", "侯文康", "只有调账功能还不通"};
            nxvWkMap = new HashMap<String, List<Object>>();
            for (int j = 2; j < 14; j++) {
                int jLine = j;
                List<Object> nxvWkList = new ArrayList<Object>();
                gridBagConstraints.gridy = j;
                // 任务人/组别编辑框
                JTextField personGroupText = new JTextField(str[0]);
                personGroupText.setColumns(5);
                gridBagConstraints.gridwidth = 1;
                personGroupText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(personGroupText, gridBagConstraints);
                contentJPanel.add(personGroupText);
                nxvWkList.add(personGroupText);
                // 任务内容编辑框
                JTextField mandateContentText = new JTextField(str[1]);
                mandateContentText.setColumns(20);
                gridBagConstraints.gridwidth = 3;
                mandateContentText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(mandateContentText, gridBagConstraints);
                contentJPanel.add(mandateContentText);
                nxvWkList.add(mandateContentText);
                // 难易度编辑框
                String nations[] = {"中", "低", "高"};
                JComboBox degreeBox = new JComboBox(nations);
                degreeBox.setSelectedItem(str[2]);
                degreeBox.setMaximumRowCount(3);
                Dimension dimension = new Dimension(20, 10);
                degreeBox.setSize(dimension);
                gridBagConstraints.gridwidth = 1;
                gridBagLayout.setConstraints(degreeBox, gridBagConstraints);
                contentJPanel.add(degreeBox);
                nxvWkList.add(degreeBox);
                // 预计完成时间编辑框
                JTextField projectedCompletionText = new JTextField(str[3]);
                projectedCompletionText.setColumns(3);
                gridBagConstraints.gridwidth = 1;
                projectedCompletionText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(projectedCompletionText, gridBagConstraints);
                contentJPanel.add(projectedCompletionText);
                nxvWkList.add(projectedCompletionText);
                // 完成比例编辑框
                String percentageText[] = {"10%", "20%", "30%", "50%", "60%", "70%", "80%", "90%", "100%"};
                JComboBox percentageBox = new JComboBox(percentageText);
                percentageBox.setSelectedItem(str[4]);
                percentageBox.setMaximumRowCount(5);
                gridBagConstraints.gridwidth = 1;
                gridBagLayout.setConstraints(percentageBox, gridBagConstraints);
                contentJPanel.add(percentageBox);
                nxvWkList.add(percentageBox);
                // 跟进人编辑框
                JTextField headText = new JTextField(str[5]);
                headText.setColumns(5);
                gridBagConstraints.gridwidth = 1;
                headText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(headText, gridBagConstraints);
                contentJPanel.add(headText);
                nxvWkList.add(headText);
                // 完成情况 编辑框
                JTextField statusText = new JTextField(str[6]);
                statusText.setColumns(20);
                gridBagConstraints.gridwidth = 3;
                statusText.setHorizontalAlignment(JTextField.CENTER);
                gridBagLayout.setConstraints(statusText, gridBagConstraints);
                contentJPanel.add(statusText);
                nxvWkList.add(statusText);

                nxvWkMap.put("line"+(jLine-2),nxvWkList);
            }
            panel.setLayout(new BorderLayout());
            panel.add(contentJPanel, BorderLayout.NORTH);
            JButton newText = new JButton("增加");
            panel.add(newText);
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
            problems.setForeground(new Color(255, 51, 51));
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
            JTextArea jTextArea = new JTextArea(1, 1);
            JScrollPane jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(jTextArea);
            jTextArea.setFont(new Font("仿宋", Font.BOLD, 20));
            jTextArea.setLineWrap(true);
            jTextArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(jScrollPane, gridBagConstraints);
            contentJPanel.add(jScrollPane);
            panel.setLayout(new BorderLayout());
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
            problems.setForeground(new Color(255, 51, 51));
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
            urgentLabel.setForeground(new Color(255, 51, 51));
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(urgentLabel, gridBagConstraints);
            contentJPanel.add(urgentLabel);
            // 紧急文本输入框
            JTextArea urgentArea = new JTextArea(4, 5);
            JScrollPane urgentScrollPane = new JScrollPane();
            urgentScrollPane.setViewportView(urgentArea);
            urgentArea.setFont(new Font("仿宋", Font.BOLD, 20));
            urgentArea.setLineWrap(true);
            urgentArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 2;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(urgentScrollPane, gridBagConstraints);
            contentJPanel.add(urgentScrollPane);
            // 一般 文本
            JLabel commonlyLabel = new JLabel("一般：");
            commonlyLabel.setFont(font);
            commonlyLabel.setForeground(new Color(255, 51, 51));
            gridBagConstraints.gridy = 3;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(commonlyLabel, gridBagConstraints);
            contentJPanel.add(commonlyLabel);
            // 一般 文本输入框
            JTextArea commonlyArea = new JTextArea(4, 5);
            JScrollPane commonlyScrollPane = new JScrollPane();
            commonlyScrollPane.setViewportView(commonlyArea);
            commonlyArea.setFont(new Font("仿宋", Font.BOLD, 20));
            commonlyArea.setLineWrap(true);
            commonlyArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 4;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(commonlyScrollPane, gridBagConstraints);
            contentJPanel.add(commonlyScrollPane);
            // 稍缓 文本
            JLabel slowlyLabel = new JLabel("稍缓：");
            slowlyLabel.setFont(font);
            slowlyLabel.setForeground(new Color(255, 51, 51));
            gridBagConstraints.gridy = 5;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(slowlyLabel, gridBagConstraints);
            contentJPanel.add(slowlyLabel);
            // 稍缓 文本输入框
            JTextArea slowlyArea = new JTextArea(4, 5);
            JScrollPane slowlyScrollPane = new JScrollPane();
            slowlyScrollPane.setViewportView(slowlyArea);
            slowlyArea.setFont(new Font("仿宋", Font.BOLD, 20));
            slowlyArea.setLineWrap(true);
            slowlyArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 6;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(slowlyScrollPane, gridBagConstraints);
            contentJPanel.add(slowlyScrollPane);

            panel.setLayout(new BorderLayout());
            panel.add(contentJPanel, BorderLayout.CENTER);
            panelHashMap.put("需其它部门或领导协助解决的事宜", panel);
        } else if (i == 4) {
            JPanel panel = new JPanel();
            // 创建内容面板
            JPanel contentJPanel = new JPanel();
            contentJPanel.setLayout(gridBagLayout);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            // 余留问题 文字
            JLabel problems = new JLabel();
            problems.setFont(new Font("仿宋", Font.BOLD, 20));
            problems.setForeground(new Color(255, 51, 51));
            problems.setText("工作中的不足和需改进之处");
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;

            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(problems, gridBagConstraints);
            contentJPanel.add(problems);
            // 余留问题 文本域
            JTextArea jTextArea = new JTextArea(1, 1);
            JScrollPane jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(jTextArea);
            jTextArea.setFont(new Font("仿宋", Font.BOLD, 20));
            jTextArea.setLineWrap(true);
            // jTextArea.setBounds(10, 0,50,50);
            jTextArea.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagLayout.setConstraints(jScrollPane, gridBagConstraints);
            contentJPanel.add(jScrollPane);
            panel.setLayout(new BorderLayout());
            panel.add(contentJPanel, BorderLayout.CENTER);
            panelHashMap.put("工作中的不足和需改进之处", panel);
        } else if (i == 5) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("本周总结");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(new BorderLayout());
            panel.add(jLabel, BorderLayout.CENTER);
            panelHashMap.put("本周总结", panel);
        }


        return panelHashMap;
    }

    /**
     * 获取时间星期一 星期天的日期
     *
     * @return List
     */
    public List<String> getStartDateAndEndDate() {
        List<String> dateList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int difference = 1 - (week == 0 ? 7 : week);
        // 星期一
        calendar.add(Calendar.DAY_OF_YEAR, difference);
        String monday = sdf.format(calendar.getTime());
        dateList.add(monday);
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String tuesday = sdf.format(calendar.getTime());
            dateList.add(tuesday);
        }
        return dateList;
    }

    /**
     *  （下周、本周）计划写入编辑框
     * @param contentColumnMap  内容行
     * @param contentLineList   内容列
     * @param decollator   分隔符
     */
    public void editBox(Map<String, List<Object>> contentColumnMap,List<String> contentLineList,String decollator){
        for (int mapLine = 0; mapLine < contentColumnMap.size(); mapLine++) {
            List<Object> entryValue = (List<Object>) contentColumnMap.get("line" + mapLine);
            String[] boxLine = new String[7];
            if (mapLine < contentLineList.size()) {
                boxLine = contentLineList.get(mapLine).split(decollator);
            }
            for (int i = 0; i < 7; i++) {
                if (i == 2 || i == 4) {
                    JComboBox jComboBox = (JComboBox) entryValue.get(i);
                    jComboBox.setSelectedItem(boxLine[i]);
                } else {
                    JTextField jTextField = (JTextField) entryValue.get(i);
                    jTextField.setText(boxLine[i]);
                }
            }
        }
    }
}
