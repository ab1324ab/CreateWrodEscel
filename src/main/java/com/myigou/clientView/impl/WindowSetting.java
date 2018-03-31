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
    // 内容字体
    Font font= new Font("黑体",Font.BOLD,16);
    // 设置部件Map
    private Map setMap = new HashMap();
    // 应用按钮
    JButton applicationButton = null;
    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        this.settingMain(jPanel,jFrame);
        this.registerListener(jFrame);
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
            border = BorderFactory.createTitledBorder(border,"周计划第二版设置",
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
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 10;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.insets = new Insets(5, 5, 5, 5);
            gridBagLayout.setConstraints(explain,gbc);
            contentJpanel.add(explain);
            // 文件名
            JLabel fileName = new JLabel("文件名:",JLabel.CENTER);
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.gridy = 1;
            gridBagLayout.setConstraints(fileName, gbc);
            contentJpanel.add(fileName);
            // 文件名输入框
            JTextField fileJText = new JTextField();
            fileJText.setHorizontalAlignment(JTextField.CENTER);
            gbc.weightx = 1;
            fileJText.setFont(font);
            fileJText.setColumns(30);
            gridBagLayout.setConstraints(fileJText,gbc);
            setMap.put("fileJText",fileJText);
            contentJpanel.add(fileJText);
            // 文件名参数
            JLabel dateParams = new JLabel("时间格式:",JLabel.CENTER);
            gbc.weightx = 0;
            gridBagLayout.setConstraints(dateParams,gbc);
            contentJpanel.add(dateParams);
            // 文件名参数输入框
            JTextField params = new JTextField();
            gbc.weightx = 1;
            params.setHorizontalAlignment(JTextField.CENTER);
            params.setFont(font);
            params.setColumns(20);
            gridBagLayout.setConstraints(params,gbc);
            setMap.put("params",params);
            contentJpanel.add(params);
            // 生成人名
            JLabel nameJLabel = new JLabel("姓名:",JLabel.CENTER);
            gbc.weightx = 0;
            gridBagLayout.setConstraints(nameJLabel,gbc);
            contentJpanel.add(nameJLabel);
            // 名字 输入框
            JTextField nameJText = new JTextField();
            gbc.weightx = 1;
            nameJText.setHorizontalAlignment(JTextField.CENTER);
            nameJText.setFont(font);
            nameJText.setColumns(20);
            gridBagLayout.setConstraints(nameJText,gbc);
            setMap.put("nameJText",nameJText);
            contentJpanel.add(nameJText);
            // 生成人名
            JLabel otherJLabel = new JLabel("其它:",JLabel.CENTER);
            gbc.weightx = 0;
            gridBagLayout.setConstraints(otherJLabel,gbc);
            contentJpanel.add(otherJLabel);
            // 名字 输入框
            JTextField otherText = new JTextField();
            gbc.weightx = 1;
            otherText.setHorizontalAlignment(JTextField.CENTER);
            otherText.setFont(font);
            otherText.setColumns(20);
            gridBagLayout.setConstraints(otherText,gbc);
            setMap.put("otherText",otherText);
            contentJpanel.add(otherText);

            // 说明
            JLabel explainExcel = new JLabel("说明：文档工作表名示例（MM.dd），MM大写表示月份，dd小写表示日期。");
            explainExcel.setBorder(BorderFactory.createLineBorder(new Color(190,190,190),1));
            explainExcel.setFont(new Font("楷体",Font.PLAIN,16));
            explainExcel.setForeground(new Color(232,40,40));
            gbc.gridy = 2;
            gbc.gridwidth = 8;
            gridBagLayout.setConstraints(explainExcel,gbc);
            contentJpanel.add(explainExcel);
            // 工作表名
            JLabel excelJLabel = new JLabel("日期格式:",JLabel.CENTER);
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gridBagLayout.setConstraints(excelJLabel,gbc);
            contentJpanel.add(excelJLabel);
            // 工作表名 输入框
            JTextField excelText = new JTextField();
            gbc.weightx = 1;
            excelText.setHorizontalAlignment(JTextField.CENTER);
            excelText.setFont(font);
            otherText.setColumns(20);
            gridBagLayout.setConstraints(excelText,gbc);
            setMap.put("excelText",excelText);
            contentJpanel.add(excelText);
            // 连接符
            JLabel connectorJLabel = new JLabel("日期连接符:",JLabel.CENTER);
            gbc.weightx = 0;
            gridBagLayout.setConstraints(connectorJLabel,gbc);
            contentJpanel.add(connectorJLabel);
            // 连接符 输入框
            JTextField connectorText = new JTextField(JTextField.CENTER);
            gbc.weightx = 1;
            connectorText.setHorizontalAlignment(JTextField.CENTER);
            connectorText.setFont(font);
            otherText.setColumns(20);
            gridBagLayout.setConstraints(connectorText,gbc);
            setMap.put("connectorText",connectorText);
            contentJpanel.add(connectorText);

            // 说明
            JLabel weekLine = new JLabel("说明：可录入多少行，以及显示多少；行内容可为空，行数一定要够。");
            weekLine.setBorder(BorderFactory.createLineBorder(new Color(190,190,190),1));
            weekLine.setFont(new Font("楷体",Font.PLAIN,16));
            weekLine.setForeground(new Color(232,40,40));
            gbc.gridy = 4;
            gbc.gridwidth = 8;
            gridBagLayout.setConstraints(weekLine,gbc);
            contentJpanel.add(weekLine);
            // 本周计划 行
            JLabel tswkJLabel = new JLabel("本周计划:",JLabel.CENTER);
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.gridy = 5;
            gridBagLayout.setConstraints(tswkJLabel,gbc);
            contentJpanel.add(tswkJLabel);
            // 本周计划 输入框
            JTextField tswkText = new JTextField();
            gbc.weightx = 1;
            tswkText.setHorizontalAlignment(JTextField.CENTER);
            tswkText.setFont(font);
            otherText.setColumns(20);
            gridBagLayout.setConstraints(tswkText,gbc);
            setMap.put("tswkText",tswkText);
            contentJpanel.add(tswkText);
            // 下周计划 行
            JLabel nxvwkJLabel = new JLabel("下周计划:",JLabel.CENTER);
            gbc.weightx = 0;
            gbc.gridy = 5;
            gridBagLayout.setConstraints(nxvwkJLabel,gbc);
            contentJpanel.add(nxvwkJLabel);
            // 下周计划 输入框
            JTextField nxvwkText = new JTextField();
            gbc.weightx = 1;
            nxvwkText.setHorizontalAlignment(JTextField.CENTER);
            nxvwkText.setFont(font);
            otherText.setColumns(20);
            gridBagLayout.setConstraints(nxvwkText,gbc);
            setMap.put("nxvwkText",nxvwkText);
            contentJpanel.add(nxvwkText);

            // 说明
            JLabel fontJLabel = new JLabel("说明：文字设置");
            fontJLabel.setBorder(BorderFactory.createLineBorder(new Color(190,190,190),1));
            fontJLabel.setFont(new Font("楷体",Font.PLAIN,16));
            fontJLabel.setForeground(new Color(232,40,40));
            gbc.gridy = 6;
            gbc.gridwidth = 8;
            gridBagLayout.setConstraints(fontJLabel,gbc);
            contentJpanel.add(fontJLabel);
            // 文字颜色 选择
            JLabel colorLabel = new JLabel("文字颜色:",JLabel.CENTER);
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.gridy = 7;
            gridBagLayout.setConstraints(colorLabel,gbc);
            contentJpanel.add(colorLabel);
            // 颜色选择
            JPanel color = new JPanel();
            color.setLayout(new GridLayout(1,7));
            JRadioButton red = new JRadioButton("红");
            JRadioButton orange = new JRadioButton("橙");
            JRadioButton green = new JRadioButton("绿");
            JRadioButton young = new JRadioButton("青");
            JRadioButton blue = new JRadioButton("蓝");
            JRadioButton purple = new JRadioButton("紫");
            JRadioButton pink = new JRadioButton("粉");
            JRadioButton black = new JRadioButton("黑");
            ButtonGroup group = new ButtonGroup();
            group.add(red);
            group.add(orange);
            group.add(green);
            group.add(young);
            group.add(purple);
            group.add(black);
            group.add(blue);
            group.add(pink);
            color.add(orange);
            color.add(green);
            color.add(young);
            color.add(purple);
            color.add(red);
            color.add(black);
            color.add(blue);
            color.add(pink);
            gbc.gridwidth = 3;
            //gbc.weightx = 1;
            gridBagLayout.setConstraints(color,gbc);
            setMap.put("color",color);
            contentJpanel.add(color);
            //文字大小
            JLabel textSizeLabel = new JLabel("文字大小:",JLabel.CENTER);
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gridBagLayout.setConstraints(textSizeLabel,gbc);
            contentJpanel.add(textSizeLabel);
            // 文字大小 输入框
            JTextField wordsText = new JTextField();
            gbc.weightx = 1;
            wordsText.setHorizontalAlignment(JTextField.CENTER);
            wordsText.setFont(font);
            otherText.setColumns(20);
            gridBagLayout.setConstraints(wordsText,gbc);
            setMap.put("wordsText",wordsText);
            contentJpanel.add(wordsText);

            // 数值
            JLabel dropdownBox = new JLabel("说明：下拉框数值设置");
            dropdownBox.setBorder(BorderFactory.createLineBorder(new Color(190,190,190),1));
            dropdownBox.setFont(new Font("楷体",Font.PLAIN,16));
            dropdownBox.setForeground(new Color(232,40,40));
            gbc.gridy = 8;
            gbc.gridwidth = 8;
            gridBagLayout.setConstraints(dropdownBox,gbc);
            contentJpanel.add(dropdownBox);
            //  难易度
            JLabel facilityValue = new JLabel("难易度:",JLabel.CENTER);
            gbc.gridwidth = 1;
            gbc.gridy = 9;
            gbc.weightx = 0;
            gridBagLayout.setConstraints(facilityValue,gbc);
            contentJpanel.add(facilityValue);
            // 难易度 输入框
            JTextField facilityValueText = new JTextField();
            gbc.weightx = 1;
            facilityValueText.setHorizontalAlignment(JTextField.CENTER);
            facilityValueText.setFont(font);
            otherText.setColumns(20);
            gridBagLayout.setConstraints(facilityValueText,gbc);
            setMap.put("facilityValueText",facilityValueText);
            contentJpanel.add(facilityValueText);
            // 完成比例
            JLabel completionRatio = new JLabel("完成比例:",JLabel.CENTER);
            gbc.weightx = 0;
            gridBagLayout.setConstraints(completionRatio,gbc);
            contentJpanel.add(completionRatio);
            // 完成比例 输入框
            JTextField completionRatioText = new JTextField();
            gbc.weightx = 1;
            gbc.gridwidth = 3;
            completionRatioText.setHorizontalAlignment(JTextField.CENTER);
            completionRatioText.setFont(font);
            completionRatioText.setColumns(20);
            gridBagLayout.setConstraints(completionRatioText,gbc);
            setMap.put("completionRatioText",completionRatioText);
            contentJpanel.add(completionRatioText);

            // 按钮
            applicationButton = new JButton("应用");
            Dimension preferredSize = new Dimension(200,200);//设置尺寸
            applicationButton.setFont(font);
            applicationButton.setPreferredSize(preferredSize );
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridwidth = 8; // 横占一个单元格
            gbc.gridheight = 2;
            gbc.gridy = 10;
            gridBagLayout.setConstraints(applicationButton,gbc);
            contentJpanel.add(applicationButton);

            panel.setLayout(new BorderLayout());
            panel.add(contentJpanel, BorderLayout.CENTER);
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

    /**
     * 按钮事件
     */
    public void registerListener(final JFrame jFrame) {

        applicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "注意：修改完成之后需要重启应用才能生效。", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

    }
}
