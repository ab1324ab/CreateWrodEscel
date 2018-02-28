package com.myigou.client.impl;

import com.myigou.client.FunctionInter;
import com.myigou.module.OnTop;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2018/1/22.
 */
public class WeekPlanMake2 extends JPanel implements FunctionInter {
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 字体类型
    Font font= new Font("仿宋", Font.BOLD, 12);
    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        this.xxxx(jPanel, jFrame);

        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame) {
        JLabel titleFileName = new JLabel("周计划生成2");
        jPanel.add(titleFileName);
        return jPanel;
    }

    public JPanel xxxx(JPanel jPanel, JFrame jFrame) {
        OnTop onTop = new OnTop();

        Map<String, Map<String, JPanel>> jPanelMap = new HashMap<String, Map<String, JPanel>>();
        int countPanel = 6;
        for (int i = 0; i < countPanel; i++) {
            jPanelMap.put(String.valueOf(i), getJPanelMap(i));
        };
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
        Dimension preferredSize = new Dimension(200,35);
        serveButton.setPreferredSize(preferredSize);
        ctrlonPanel.add(serveButton);

        JButton makeButton= new JButton("生成文档");
        // 去掉按钮文字周围焦点
        makeButton.setFocusPainted(false);
        makeButton.setPreferredSize(preferredSize);
        ctrlonPanel.add(makeButton);

        ctrlonPanel.setBackground(new Color(210, 210, 210));

        erJpanel.add(ctrlonPanel,BorderLayout.NORTH);
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
            title.setText("本周计划");
            gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(title, gridBagConstraints);
            contentJPanel.add(title);
            // 计划人文本
            JLabel jLabel = new JLabel();
            jLabel.setFont(font);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("计划人:");
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.gridy = 1;
            gridBagLayout.setConstraints(jLabel, gridBagConstraints);
            contentJPanel.add(jLabel);
            //计划人 编辑框
            JTextField plannedText = new JTextField();
            plannedText.setColumns(5);
            gridBagLayout.setConstraints(plannedText, gridBagConstraints);
            contentJPanel.add(plannedText);
            // 计划日期
            JLabel plannedDate = new JLabel();
            plannedDate.setFont(font);
            plannedDate.setForeground(new Color(255, 51, 51));
            plannedDate.setHorizontalAlignment(SwingConstants.CENTER);
            plannedDate.setText("计划日期:");
            gridBagLayout.setConstraints(plannedDate, gridBagConstraints);
            contentJPanel.add(plannedDate);
            // 计划日期编辑框
            JTextField plannedDateText = new JTextField();
            plannedDateText.setColumns(5);
            gridBagLayout.setConstraints(plannedDateText, gridBagConstraints);
            contentJPanel.add(plannedDateText);
            // 总结日期
            JLabel summaryDate = new JLabel();
            summaryDate.setFont(font);
            summaryDate.setForeground(new Color(255, 51, 51));
            summaryDate.setHorizontalAlignment(SwingConstants.CENTER);
            summaryDate.setText("总结日期:");
            gridBagLayout.setConstraints(summaryDate, gridBagConstraints);
            contentJPanel.add(summaryDate);
            // 总结日期编辑框
            JTextField summaryDateText = new JTextField();
            summaryDateText.setColumns(5);
            gridBagLayout.setConstraints(summaryDateText, gridBagConstraints);
            contentJPanel.add(summaryDateText);

            gridBagConstraints.gridy = 2;
            // 任务人/组别
            JLabel personGroup = new JLabel();
            personGroup.setFont(font);
            personGroup.setForeground(new Color(255, 51, 51));
            personGroup.setHorizontalAlignment(SwingConstants.CENTER);
            personGroup.setText("任务名/组别:");
            gridBagLayout.setConstraints(personGroup, gridBagConstraints);
            contentJPanel.add(personGroup);
            // 任务内容
            JLabel mandateContent = new JLabel();
            mandateContent.setFont(font);
            mandateContent.setForeground(new Color(255, 51, 51));
            mandateContent.setHorizontalAlignment(SwingConstants.CENTER);
            mandateContent.setText("任务内容:");
            gridBagLayout.setConstraints(mandateContent, gridBagConstraints);
            contentJPanel.add(mandateContent);
            // 难易度
            JLabel difficulty = new JLabel();
            difficulty.setFont(font);
            difficulty.setForeground(new Color(255, 51, 51));
            difficulty.setHorizontalAlignment(SwingConstants.CENTER);
            difficulty.setText("难易度:");
            gridBagLayout.setConstraints(difficulty, gridBagConstraints);
            contentJPanel.add(difficulty);
            // 预计完成时间
            JLabel projectedCompletion = new JLabel();
            projectedCompletion.setFont(font);
            projectedCompletion.setForeground(new Color(255, 51, 51));
            projectedCompletion.setHorizontalAlignment(SwingConstants.CENTER);
            projectedCompletion.setText("预计完成时间:");
            gridBagLayout.setConstraints(projectedCompletion, gridBagConstraints);
            contentJPanel.add(projectedCompletion);
            // 完成比例
            JLabel percentage = new JLabel();
            percentage.setFont(font);
            percentage.setForeground(new Color(255, 51, 51));
            percentage.setHorizontalAlignment(SwingConstants.CENTER);
            percentage.setText("完成比例:");
            gridBagLayout.setConstraints(percentage, gridBagConstraints);
            contentJPanel.add(percentage);
            // 跟进人
            JLabel head = new JLabel();
            head.setFont(font);
            head.setForeground(new Color(255, 51, 51));
            head.setHorizontalAlignment(SwingConstants.CENTER);
            head.setText("跟进人:");
            gridBagLayout.setConstraints(head, gridBagConstraints);
            contentJPanel.add(head);
            // 完成情况
            JLabel status = new JLabel();
            status.setFont(font);
            status.setForeground(new Color(255, 51, 51));
            status.setHorizontalAlignment(SwingConstants.CENTER);
            status.setText("完成情况:");
            gridBagLayout.setConstraints(status, gridBagConstraints);
            contentJPanel.add(status);

            for(int j = 3; j<10; j++ ){
                gridBagConstraints.gridy = j;
                // 任务人/组别编辑框
                JTextField personGroupText = new JTextField();
                personGroupText.setColumns(5);
                gridBagLayout.setConstraints(personGroupText, gridBagConstraints);
                contentJPanel.add(personGroupText);
                // 任务内容编辑框
                JTextField mandateContentText = new JTextField();
                mandateContentText.setColumns(5);
                gridBagLayout.setConstraints(mandateContentText, gridBagConstraints);
                contentJPanel.add(mandateContentText);
                // 难易度编辑框
                JTextField difficultyText = new JTextField();
                difficultyText.setColumns(5);
                gridBagLayout.setConstraints(difficultyText, gridBagConstraints);
                contentJPanel.add(difficultyText);
                // 预计完成时间编辑框
                JTextField projectedCompletionText = new JTextField();
                projectedCompletionText.setColumns(5);
                gridBagLayout.setConstraints(projectedCompletionText, gridBagConstraints);
                contentJPanel.add(projectedCompletionText);
                // 完成比例编辑框
                JTextField percentageText = new JTextField();
                percentageText.setColumns(5);
                gridBagLayout.setConstraints(percentageText, gridBagConstraints);
                contentJPanel.add(percentageText);
                // 跟进人编辑框
                JTextField headText = new JTextField();
                headText.setColumns(5);
                gridBagLayout.setConstraints(headText, gridBagConstraints);
                contentJPanel.add(headText);
                // 跟进人编辑框
                JTextField statusText = new JTextField();
                statusText.setColumns(5);
                gridBagLayout.setConstraints(statusText, gridBagConstraints);
                contentJPanel.add(statusText);
            }

            panel.setLayout(new BorderLayout());
            panel.add(contentJPanel, BorderLayout.NORTH);
            JButton newText = new JButton("增加");
            panel.add(newText);
            //panel.add(title);
            panelHashMap.put("本周计划", panel);
        } else if (i == 1) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("下周工作计划");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("下周工作计划", panel);
        } else if (i == 2) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("余留问题");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("余留问题", panel);
        }else if (i == 3) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("需其它部门或领导协助解决的事宜");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("需其它部门或领导协助解决的事宜", panel);
        }else if (i == 4) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("工作中的不足和需改进之处");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("工作中的不足和需改进之处", panel);
        }
        else if (i == 5) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("本周总结");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("本周总结", panel);
        }


        return panelHashMap;
    }
}
