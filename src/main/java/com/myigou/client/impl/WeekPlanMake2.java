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
        int countPanel = 3;
        for (int i = 0; i < countPanel; i++) {
            jPanelMap.put(String.valueOf(i), getJPanelMap(i));
        };
        JPanel erJpanel = new JPanel();
        erJpanel.setLayout(new BorderLayout());
        JPanel ctrlonPanel = new JPanel();
        JLabel explain = new JLabel("控制");
        explain.setFont(new Font("Dialog", Font.BOLD, 25));
        ctrlonPanel.add(explain);

        JButton serveButton = new JButton("保存文档");
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
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("生成 Excel");
            JPanel panel = new JPanel();
            panel.setBackground(new Color(241, 241, 241));
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("第一页", panel);
        } else if (i == 1) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("生成 Excel2");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("第二页", panel);
        } else if (i == 2) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("生成 Excel3");
            JPanel panel = new JPanel();
            // panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("第三页", panel);
        }


        return panelHashMap;
    }
}
