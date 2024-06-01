package com.myigou.module;


import javax.swing.SwingUtilities;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/6/29.
 */
public class OnTop extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JPanel jPanel = null;
    private JButton jButton = null;
    private JLabel jLabel = null;
    private Map<String, JPanel> panelMap = new HashMap<String, JPanel>();

    /**
     * This method initializes jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel(int num, Map<String, Map<String, JPanel>> jPanelMap) {
        if (jPanel == null) {
            jPanel = new JPanel();
            // jPanel.setLayout(new GridLayout(num, 1));
            jPanel.setPreferredSize(new Dimension(150, 0));
            //jPanel.setBackground(new Color(210, 210, 210));
            jPanel.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
            for (int i = 0; i < num; i++) {
                jPanel.add(getJButton(jPanelMap.get(String.valueOf(i))), null);
            }
        }
        return jPanel;
    }

    /**
     * This method initializes jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getJButton(Map<String, JPanel> parameMap) {
        jButton = new JButton();
        jButton.setFocusPainted(false);
        // jButton.setBounds(new Rectangle(0, 0, 90, 30));
        // 设置尺寸
        Dimension preferredSize = new Dimension(130,35);
        jButton.setPreferredSize(preferredSize);
        // jButton.setSize(100, 15);
        //jButton.setForeground(Color.red);
        jButton.setFont(new Font("微软雅黑",Font.PLAIN,12));
        String mapKey = "";
        for (String key : parameMap.keySet()) {
            mapKey = key;
        }
        String finalMapKey = mapKey;
        jButton.setText(finalMapKey);
        jButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // 删除内容面板中间原来的组建
                jContentPane.remove(((BorderLayout) jContentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER));
                // 添加要切换的面板
                jContentPane.add(getJPanel1(finalMapKey, parameMap.get(finalMapKey)), BorderLayout.CENTER);
                // 重构内容面板
                jContentPane.validate();
                // 重绘内容面板
                jContentPane.repaint();
                // 上面两句缺一不可，
                // 没有validate()删除和添加没有效果
                // 没有repaint()界面绘出现混乱
            }
        });
        return jButton;
    }


    /**
     * This method initializes jPanel1
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1(String keyName, JPanel panelc) {
        JPanel panel = panelMap.get(keyName);
        if (panel == null) {
            panelMap.put(keyName, panelc);
        }
        return panelc;
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    public JPanel getJContentPane(int num, Map<String, Map<String, JPanel>> jPanelMap) {
        if (jContentPane == null) {
            String mapKey = "";
            for (String key : jPanelMap.get("0").keySet()) {
                mapKey = key;
            }
            jContentPane = new JPanel();
            //jContentPane.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));

            jContentPane.setLayout(new BorderLayout());
            // 中间面板
            jContentPane.add(getJPanel1("0", jPanelMap.get("0").get(mapKey)), BorderLayout.CENTER);
            // 左邊面板
            jContentPane.add(getJPanel(num, jPanelMap), BorderLayout.WEST);
        }
        return jContentPane;
    }
}

