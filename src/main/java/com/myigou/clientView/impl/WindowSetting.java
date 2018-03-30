package com.myigou.clientView.impl;

import com.myigou.clientView.FunctionInter;
import com.myigou.module.OnTop;
import com.myigou.tool.PropertiesTool;
import com.myigou.mainView.WindowView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/9/6.
 */
public class WindowSetting implements FunctionInter {
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 内容部件Map
    private Map<String, String> contentMap = new HashMap<String, String>();

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        this.settingMain(jPanel,jFrame);

        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame) {
        jPanel.add(new JLabel("窗口设置"));
        return jPanel;
    }

    public void settingMain(final JPanel jPanel,JFrame jFrame) {
        // 初始化资源文件
        contentMap = WindowView.contentMap;
        JPanel jPanel1 = new JPanel(new BorderLayout());
        JPanel showMain = new JPanel();
        showMain.add(new JLabel("启动主页显示选项："));
        JRadioButton createExcel = new JRadioButton("周计划生成");
        JRadioButton fileManage = new JRadioButton("文件编辑");
        JRadioButton sendEmail = new JRadioButton("邮件发送");
        JRadioButton createExcel2 = new JRadioButton("周计划生成第二版");
        createExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 周计划生成
                PropertiesTool.writeSet("config.properties", "showMain", "fun_1");
                System.out.println("fun_1");
            }
        });
        fileManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 文件编辑
                PropertiesTool.writeSet("config.properties", "showMain", "fun_2");
                System.out.println("fun_2");
            }
        });
        sendEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 邮件编辑
                PropertiesTool.writeSet("config.properties", "showMain", "fun_3");
                System.out.println("fun_3");
            }
        });
        createExcel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 周计划生成第二版
                PropertiesTool.writeSet("config.properties", "showMain", "fun_1_2");
                System.out.println("fun_1_2");
            }
        });
        if ("fun_1".equals(contentMap.get("showMain"))) {
            createExcel.setSelected(true);
        } else if ("fun_2".equals(contentMap.get("showMain"))) {
            fileManage.setSelected(true);
        } else if ("fun_3".equals(contentMap.get("showMain"))) {
            sendEmail.setSelected(true);
        } else if ("fun_1_2".equals(contentMap.get("showMain"))) {
            createExcel2.setSelected(true);
        }
        ButtonGroup group = new ButtonGroup();
        group.add(createExcel);
        group.add(fileManage);
        group.add(sendEmail);
        group.add(createExcel2);
        showMain.add(createExcel);
        showMain.add(fileManage);
        showMain.add(sendEmail);
        showMain.add(createExcel2);
        jPanel1.add(showMain, BorderLayout.NORTH);

        OnTop onTop = new OnTop();
        Map<String, Map<String, JPanel>> jPanelMap = new HashMap<String, Map<String, JPanel>>();
        int countPanel = 3;
        for (int i = 0; i < countPanel; i++) {
            jPanelMap.put(String.valueOf(i), getJPanelMap(i));
        };
        jPanel1.add(onTop.getJContentPane(countPanel, jPanelMap), BorderLayout.CENTER);
        jPanel.add(jPanel1, BorderLayout.CENTER);
    }

    public Map<String, JPanel> getJPanelMap(int i) {
        Map<String, JPanel> panelHashMap = new HashMap<String, JPanel>();
        if (i == 0) {
            JPanel panel = new JPanel();
            JPanel contentJpanel = new JPanel();
            contentJpanel.setLayout(gridBagLayout);
            Border border = BorderFactory.createEtchedBorder();
            border = BorderFactory.createTitledBorder(border,"周计划设置",
                    TitledBorder.LEFT,TitledBorder.CENTER,new Font("楷体", Font.PLAIN, 13),Color.BLACK);
            panel.setBorder(border);
            GridBagConstraints gbc = new GridBagConstraints();
            // 说明
            JLabel explain = new JLabel("说明：文件名格式“%s”占位符（例如：%s周总结-开发三部门%s），结果：‘xx月xx日’周总结-开发三部门‘某某’。");
            explain.setBorder(BorderFactory.createLineBorder(new Color(190,190,190),1));
            explain.setFont(new Font("楷体",Font.PLAIN,16));
            explain.setForeground(new Color(232,40,40));
            //explain.setBorder(new EmptyBorder(10, 10, 10, 10));
            explain.setSize(new Dimension(10,18));
            gbc.gridy = 0;
            //gbc.anchor = GridBagConstraints.PAGE_START;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridwidth = 10;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.insets = new Insets(10, 10, 10, 10);
            gridBagLayout.setConstraints(explain,gbc);
            contentJpanel.add(explain);
            // 文件名
            JLabel fileName = new JLabel("文件名:",JLabel.RIGHT);
            gbc.gridwidth = 1;
            gbc.gridy = 1;
            gridBagLayout.setConstraints(fileName, gbc);
            contentJpanel.add(fileName);
            // 文件名输入框
            JTextField fileJText = new JTextField();
            fileJText.setColumns(30);
            gbc.gridwidth = 1;
            gridBagLayout.setConstraints(fileJText,gbc);
            contentJpanel.add(fileJText);
            // 文件名参数
            JLabel dateParams = new JLabel("时间格式:");
            gridBagLayout.setConstraints(dateParams,gbc);
            contentJpanel.add(dateParams);
            // 文件名参数输入框
            JTextField params1 = new JTextField();
            params1.setColumns(20);
            gridBagLayout.setConstraints(params1,gbc);
            contentJpanel.add(params1);
            // 生成人名
            JLabel nameJLabel = new JLabel("姓名:");
            gridBagLayout.setConstraints(nameJLabel,gbc);
            contentJpanel.add(nameJLabel);
            // 名字
            JTextField nameJText = new JTextField();
            nameJText.setColumns(20);
            gridBagLayout.setConstraints(nameJText,gbc);
            contentJpanel.add(nameJText);
            // 生成人名
            JLabel otherJLabel = new JLabel("其它:");
            gridBagLayout.setConstraints(otherJLabel,gbc);
            contentJpanel.add(otherJLabel);
            // 名字
            JTextField otherText = new JTextField();
            otherText.setColumns(20);
            gridBagLayout.setConstraints(otherText,gbc);
            contentJpanel.add(otherText);

            // 说明
            JLabel explainExcel = new JLabel("说明：文档工作表名示例（MM.dd），MM大写表示月份，dd小写表示日期。");
            explainExcel.setBorder(BorderFactory.createLineBorder(new Color(190,190,190),1));
            explainExcel.setFont(new Font("楷体",Font.PLAIN,16));
            explainExcel.setForeground(new Color(232,40,40));
            gbc.gridy = 2;
            gbc.gridwidth = 10;
            gridBagLayout.setConstraints(explainExcel,gbc);
            contentJpanel.add(explainExcel);
            // 工作表名
            JLabel excelJLabel = new JLabel("日期格式:");
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            gridBagLayout.setConstraints(excelJLabel,gbc);
            contentJpanel.add(excelJLabel);
            // 工作表名 输入框
            JTextField excelText = new JTextField();
            otherText.setColumns(20);
            gridBagLayout.setConstraints(excelText,gbc);
            contentJpanel.add(excelText);

            panel.setLayout(new BorderLayout());
            panel.add(contentJpanel, BorderLayout.NORTH);
            panelHashMap.put("周计划设置", panel);
        } else if (i == 1) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("Panel 也假的");
            JPanel panel = new JPanel();
            panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("第二页", panel);
        } else if (i == 2) {
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("Panel 是的");
            JPanel panel = new JPanel();
            panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            panelHashMap.put("第三页", panel);
        }
        return panelHashMap;
    }
}
