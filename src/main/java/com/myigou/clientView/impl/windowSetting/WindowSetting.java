package com.myigou.clientView.impl.windowSetting;

import com.myigou.clientView.FunctionInter;
import com.myigou.clientView.impl.windowSetting.module.FileDisposeSetting;
import com.myigou.clientView.impl.windowSetting.module.SendMessageSetting.SendMessageSetting;
import com.myigou.clientView.impl.windowSetting.module.WeekPlanMakeSetting;
import com.myigou.module.OnTop;
import com.myigou.tool.PropertiesTool;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ab1324ab
 * Created by ab1324ab on 2017/9/6.
 */
public class WindowSetting implements FunctionInter {

    // 内容部件Map
    private Map<String, String> contentMap = new HashMap<String, String>();
    // 配置文件
    final String CONFIG_FILE = "config.properties";

    Font biaotiziti = new Font("微软雅黑", Font.BOLD, 14);
    Font neirongziti = new Font("微软雅黑", Font.PLAIN, 12);

    public WindowSetting() {
        contentMap = PropertiesTool.redConfigFile(CONFIG_FILE);
    }

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        this.settingMain(jPanel, jFrame);

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
        createExcel.setRequestFocusEnabled(false);
        createExcel.setFont(neirongziti);
        JRadioButton fileManage = new JRadioButton("文件编辑");
        fileManage.setRequestFocusEnabled(false);
        fileManage.setFont(neirongziti);
        JRadioButton sendMessage = new JRadioButton("局域网消息");
        sendMessage.setRequestFocusEnabled(false);
        sendMessage.setFont(neirongziti);
        JRadioButton createExcel2 = new JRadioButton("周计划生成第二版");
        createExcel2.setRequestFocusEnabled(false);
        createExcel2.setFont(neirongziti);
        // 设置周计划生成
        createExcel.addActionListener(e -> PropertiesTool.writeSet("config.properties", "showMain", "fun_1"));
        // 设置文件编辑
        fileManage.addActionListener(e -> PropertiesTool.writeSet("config.properties", "showMain", "fun_2"));
        // 设置局域网消息
        sendMessage.addActionListener(e -> PropertiesTool.writeSet("config.properties", "showMain", "fun_3"));
        // 设置周计划生成第二版
        createExcel2.addActionListener(e -> PropertiesTool.writeSet("config.properties", "showMain", "fun_1_2"));

        if ("fun_1".equals(contentMap.get("showMain"))) createExcel.setSelected(true);
        else if ("fun_2".equals(contentMap.get("showMain"))) fileManage.setSelected(true);
        else if ("fun_3".equals(contentMap.get("showMain"))) sendMessage.setSelected(true);
        else if ("fun_1_2".equals(contentMap.get("showMain"))) createExcel2.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(createExcel);
        group.add(fileManage);
        group.add(sendMessage);
        group.add(createExcel2);
        showMain.add(createExcel);
        showMain.add(fileManage);
        showMain.add(sendMessage);
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
        JPanel panel = new JPanel();
        Border border = BorderFactory.createEtchedBorder();
        panel.setLayout(new BorderLayout());
        String title = "";
        if (i == 0) {
            panel.add(new WeekPlanMakeSetting(jFrame).getJpanel());
            title = "周计划菜单设置";
        } else if (i == 1) {
            panel.add(new FileDisposeSetting(jFrame).getJpanel());
            title = "文件编辑菜单设置";
        } else if (i == 2) {
            panel.add(new SendMessageSetting(jFrame).getJpanel());
            title = "局域网消息设置";
        }
        border = BorderFactory.createTitledBorder(border, title, TitledBorder.LEFT, TitledBorder.CENTER, new Font("楷体", Font.PLAIN, 13), Color.BLACK);
        panel.setBorder(border);
        panelHashMap.put(title, panel);
        return panelHashMap;
    }

}
