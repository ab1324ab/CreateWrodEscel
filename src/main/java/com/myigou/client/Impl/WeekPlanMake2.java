package com.myigou.client.Impl;

import com.myigou.client.FunctionInter;
import com.myigou.module.OnTop;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ab1324ab on 2018/1/22.
 */
public class WeekPlanMake2  extends JPanel implements FunctionInter {
    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        this.xxxx(jPanel,jFrame);

        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame) {
        return null;
    }

    public JPanel xxxx(JPanel jPanel,JFrame jFrame){
        OnTop onTop=new OnTop();
        Map<String,Map<String,JPanel>> jPanelMap=new HashMap<String,Map<String,JPanel>>();
        int countPanel=3;
        for (int i=0;i<countPanel;i++){
            jPanelMap.put(String.valueOf(i),getJPanelMap(i));
        };
        jPanel.add(onTop.getJContentPane(countPanel,jPanelMap), BorderLayout.CENTER);

        return jPanel;
    }

    public Map<String,JPanel> getJPanelMap(int i){
        Map<String,JPanel> panelHashMap=new HashMap<String,JPanel>();
        if (i==0){
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("Panel 假的" );
            JPanel panel = new JPanel();
            panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("第一页",panel);
        }else if(i==1){
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("Panel 也假的" );
            JPanel panel = new JPanel();
            panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("第二页",panel);
        }else if(i==2){
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("Panel 是的" );
            JPanel panel = new JPanel();
            panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("第三页",panel);
        }


        return panelHashMap;
    }
}
