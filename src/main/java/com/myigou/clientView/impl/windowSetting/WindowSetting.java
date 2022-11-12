package com.myigou.clientView.impl.windowSetting;

import com.myigou.clientService.enums.ColorEnum;
import com.myigou.clientView.FunctionInter;
import com.myigou.clientView.impl.windowSetting.module.FileDisposeSetting;
import com.myigou.clientView.impl.windowSetting.module.WeekPlanMakeSetting;
import com.myigou.module.OnTop;
import com.myigou.tool.PropertiesTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ab1324ab
 * Created by ab1324ab on 2017/9/6.
 */
public class WindowSetting implements FunctionInter {
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 内容部件Map
    private Map<String, String> contentMap = new HashMap<String, String>();
    // 内容字体
    Font font = new Font("楷体", Font.PLAIN, 16);
    // 设置部件Map
    private Map<String, Object> setMap = new HashMap();
    // 应用按钮
    JButton applicationButton = null;
    // 当前选中颜色
    String selectColor = null;
    // 配置文件
    final String CONFIG_FILE = "config.properties";
    // 颜色示咧
    JLabel colorExamples = null;

    Font biaotiziti = new Font("微软雅黑", Font.BOLD, 14);
    Font neirongziti = new Font("微软雅黑", Font.PLAIN, 12);

    public WindowSetting() {
        contentMap = contentMap = PropertiesTool.redConfigFile(CONFIG_FILE);
        selectColor = contentMap.get("colorPanel");
    }

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        this.settingMain(jPanel, jFrame);
//        this.registerListener(jFrame);
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame, Font font) {
        JLabel title = new JLabel("窗口设置", JLabel.CENTER);
        title.setFont(font);
        jPanel.add(title);
        return jPanel;
    }

    public void settingMain(final JPanel jPanel, JFrame jFrame) {
        // 初始化资源文件
        JPanel jPanel1 = new JPanel(new BorderLayout());
        JPanel showMain = new JPanel();
        JLabel jLabelqidong = new JLabel("启动优先显示菜单：");
        jLabelqidong.setFont(biaotiziti);
        showMain.add(jLabelqidong);
        JRadioButton createExcel = new JRadioButton("周计划生成");
        createExcel.setFont(neirongziti);
        JRadioButton fileManage = new JRadioButton("文件编辑");
        fileManage.setFont(neirongziti);
        JRadioButton sendEmail = new JRadioButton("局域网消息");
        sendEmail.setFont(neirongziti);
        JRadioButton createExcel2 = new JRadioButton("周计划生成第二版");
        createExcel2.setFont(neirongziti);
        // 设置周计划生成
        createExcel.addActionListener(e -> PropertiesTool.writeSet("config.properties", "showMain", "fun_1"));
        // 设置文件编辑
        fileManage.addActionListener(e -> PropertiesTool.writeSet("config.properties", "showMain", "fun_2"));
        // 设置局域网消息
        sendEmail.addActionListener(e -> PropertiesTool.writeSet("config.properties", "showMain", "fun_3"));
        // 设置周计划生成第二版
        createExcel2.addActionListener(e -> PropertiesTool.writeSet("config.properties", "showMain", "fun_1_2"));

        if ("fun_1".equals(contentMap.get("showMain"))) createExcel.setSelected(true);
        else if ("fun_2".equals(contentMap.get("showMain"))) fileManage.setSelected(true);
        else if ("fun_3".equals(contentMap.get("showMain"))) sendEmail.setSelected(true);
        else if ("fun_1_2".equals(contentMap.get("showMain"))) createExcel2.setSelected(true);

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
            jPanelMap.put(String.valueOf(i), getJPanelMap(i, jFrame));
        }
        jPanel1.add(onTop.getJContentPane(countPanel, jPanelMap), BorderLayout.CENTER);
        jPanel.add(jPanel1, BorderLayout.CENTER);
    }

    public Map<String, JPanel> getJPanelMap(int i, JFrame jFrame) {
        Map<String, JPanel> panelHashMap = new HashMap<String, JPanel>();
        if (i == 0) {
            JPanel panel = new JPanel();
            Border border = BorderFactory.createEtchedBorder();
            border = BorderFactory.createTitledBorder(border, "周计划第二版设置", TitledBorder.LEFT, TitledBorder.CENTER, new Font("楷体", Font.PLAIN, 13), Color.BLACK);
            panel.setBorder(border);
            panel.setLayout(new BorderLayout());
            panel.add(new WeekPlanMakeSetting(jFrame).getJpanel());
            panelHashMap.put("周计划菜单设置", panel);
        } else if (i == 1) {
            JPanel panel = new JPanel();
            Border border = BorderFactory.createEtchedBorder();
            border = BorderFactory.createTitledBorder(border, "文件编辑菜单设置", TitledBorder.LEFT, TitledBorder.CENTER, new Font("楷体", Font.PLAIN, 13), Color.BLACK);
            panel.setBorder(border);
            panel.setLayout(new BorderLayout());
            panel.add(new FileDisposeSetting(jFrame).getJpanel());
            panelHashMap.put("文件编辑菜单设置", panel);
        } else if (i == 2) {
            JPanel panel = new JPanel();
            Border border = BorderFactory.createEtchedBorder();
            border = BorderFactory.createTitledBorder(border, "第三页", TitledBorder.LEFT, TitledBorder.CENTER, new Font("楷体", Font.PLAIN, 13), Color.BLACK);
            panel.setBorder(border);
            panel.setLayout(new BorderLayout());
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
//            jLabel.setText("第三页，开发中。。。");
            //panel.setBackground(Color.red);
            panel.setLayout(new BorderLayout());
            panel.add(jLabel);
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
                for (String key : setMap.keySet()) {
                    if ("colorPanel".equals(key)) {
                        String colorValue = ColorEnum.getColorValue(selectColor);
                        PropertiesTool.writeSet(CONFIG_FILE, key, selectColor);
                        PropertiesTool.writeSet(CONFIG_FILE, "colorPanelValue", colorValue);
                    } else if ("facilityValueText".equals(key)) {
                        JTextField textField = (JTextField) setMap.get(key);
                        String textFieldText = "," + textField.getText();
                        PropertiesTool.writeSet(CONFIG_FILE, key, textFieldText);
                    } else if ("completionRatioText".equals(key)) {
                        JTextField textField = (JTextField) setMap.get(key);
                        String textFieldText = "," + textField.getText();
                        PropertiesTool.writeSet(CONFIG_FILE, key, textFieldText);
                    } else {
                        JTextField textField = (JTextField) setMap.get(key);
                        if (StringUtils.isEmpty(textField.getText())) {
                            JOptionPane.showMessageDialog(jFrame, "不允许设置内容空值！", "提示", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        PropertiesTool.writeSet(CONFIG_FILE, key, textField.getText());
                    }
                }
                JOptionPane.showMessageDialog(jFrame, "应用成功！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
