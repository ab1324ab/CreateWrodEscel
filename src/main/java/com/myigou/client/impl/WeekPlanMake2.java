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
            // 计划人文本
            JLabel jLabel = new JLabel();
            // jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(font);
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("计划人:");
            // gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            gridBagLayout.setConstraints(jLabel, gridBagConstraints);
            contentJPanel.add(jLabel);
            //计划人 编辑框
            JTextField planned = new JTextField();
            planned.setColumns(5);
            gridBagLayout.setConstraints(planned, gridBagConstraints);
            contentJPanel.add(planned);

            panel.setBackground(new Color(241, 241, 241));
            panel.setLayout(new GridLayout(3,2));
            panel.add(contentJPanel, null);
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
