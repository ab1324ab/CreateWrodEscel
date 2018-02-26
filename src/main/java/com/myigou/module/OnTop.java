package com.myigou.module;


import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Color;
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
            jPanel.setPreferredSize(new Dimension(100, 0));
            jPanel.setBackground(new Color(210, 210, 210));
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
        Dimension preferredSize = new Dimension(90,35);
        jButton.setPreferredSize(preferredSize);
        // jButton.setSize(100, 15);
        jButton.setForeground(Color.red);
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
//            jLabel = new JLabel();
//            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
//            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
//            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
//            jLabel.setForeground(new Color(255, 51, 51));
//            jLabel.setText("Panel " + i);
//            panel = new JPanel();
//            panel.setBackground(Color.red);
//            panel.setLayout(null);
//            panel.add(jLabel, null);
            panelMap.put(keyName, panelc);
        }
        return panelc;
    }


    /**
     * @param args
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OnTop thisClass = new OnTop();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    /**
     * This is the default constructor
     */
    public OnTop() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(472, 410);
        // this.setResizable(false);
        // this.setContentPane(getJContentPane());
        this.setTitle("JPanel切换示例");
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
            jContentPane.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));

            jContentPane.setLayout(new BorderLayout());
            // 中间面板
            jContentPane.add(getJPanel1("0", jPanelMap.get("0").get(mapKey)), BorderLayout.CENTER);
            // 左邊面板
            jContentPane.add(getJPanel(num, jPanelMap), BorderLayout.WEST);
        }
        return jContentPane;
    }
}

