package com.myigou.mainView;


import com.myigou.clientView.impl.AboutProgramDialog;
import com.myigou.tool.ImageIconTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * @author ab1324ab
 * Created by ab1324ab on 2017/6/29.
 */
public class ViewMain {

    public static HashMap<String, JPanel> displayJpael = null;
    public static WindowView windowView = null;
    public static Thread thread = null;
    private final int ico_Width = 20;
    private final int ico_Height = 20;

    // 主方法启动类
    public static void main(String[] args) {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        //JDialog.setDefaultLookAndFeelDecorated(true);
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        windowView = new WindowView();
        SwingUtilities.updateComponentTreeUI(windowView);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //SubstanceLookAndFeel.setSkin(new NebulaBrickWallSkin());
                thread = new Thread(windowView, "更新线程启动");
                thread.interrupt();
                thread.start();
                windowView.init();
            }
        });
    }


    /**
     * 添加菜单项
     * @param jFrame
     */
    public void menuBox(final JFrame jFrame) {
        JMenuBar bar = new JMenuBar();

        JMenu menu1 = new JMenu("文件", false);
        JMenuItem saveFile = new JMenuItem("保存文本", ImageIconTool.gitImageIcon("icons/save.png", ico_Width, ico_Height));
        saveFile.addActionListener(e -> windowView.selectButton(windowView.accessDisplay("txt_3")));
        this.muenStyle(saveFile, "1");
        saveFile.setEnabled(false);

        JMenuItem saveAsFile = new JMenuItem("文本另存", ImageIconTool.gitImageIcon("icons/saveAs.png", ico_Width, ico_Height));
        saveAsFile.addActionListener(e -> windowView.selectButton(windowView.accessDisplay("txt_4")));
        this.muenStyle(saveAsFile, "1");
        saveAsFile.setEnabled(false);

        JMenuItem newFile = new JMenuItem("新建文本", ImageIconTool.gitImageIcon("icons/new.png", ico_Width, ico_Height));
        newFile.addActionListener(e -> {
            saveFile.setEnabled(true);
            saveAsFile.setEnabled(true);
            windowView.selectButton(windowView.accessDisplay("txt_1"));
        });
        this.muenStyle(newFile, "1");
        menu1.add(newFile);

        JMenuItem openFile = new JMenuItem("打开文本", ImageIconTool.gitImageIcon("icons/open.png", ico_Width, ico_Height));
        openFile.addActionListener(e -> {
            saveFile.setEnabled(true);
            saveAsFile.setEnabled(true);
            windowView.selectButton(windowView.accessDisplay("txt_2"));
        });
        this.muenStyle(openFile, "1");
        menu1.add(openFile);
        menu1.add(saveFile);
        menu1.add(saveAsFile);
        JMenuItem exitFile = new JMenuItem("退出", ImageIconTool.gitImageIcon("icons/exit.png", ico_Width, ico_Height));
        exitFile.addActionListener(e -> System.exit(0));
        this.muenStyle(exitFile, "1");
        menu1.add(exitFile);
        this.muenStyle(menu1, "0");
        bar.add(menu1);

        JMenu function = new JMenu("功能选项", false);
        JMenuItem weekPlan = new JMenuItem("周计划", ImageIconTool.gitImageIcon("icons/excel.png", ico_Width, ico_Height));
        weekPlan.addActionListener(e -> windowView.selectButton(windowView.accessDisplay("fun_1")));
        this.muenStyle(weekPlan, "1");
        function.add(weekPlan);

        JMenuItem weekPlan2 = new JMenuItem("周计划第二版", ImageIconTool.gitImageIcon("icons/excel.png", ico_Width, ico_Height));
        weekPlan2.addActionListener(e -> windowView.selectButton(windowView.accessDisplay("fun_1_2")));
        this.muenStyle(weekPlan2, "1");
        function.add(weekPlan2);

        JMenuItem fFile = new JMenuItem("文件编辑", ImageIconTool.gitImageIcon("icons/file.png", ico_Width, ico_Height));
        fFile.addActionListener(e -> windowView.selectButton(windowView.accessDisplay("fun_2")));
        this.muenStyle(fFile, "1");
        function.add(fFile);

        JMenuItem sendEmail = new JMenuItem("局域网消息", ImageIconTool.gitImageIcon("icons/email.png", ico_Width, ico_Height));
        sendEmail.addActionListener(e -> windowView.selectButton(windowView.accessDisplay("fun_3")));
        this.muenStyle(sendEmail, "1");
        function.add(sendEmail);

        JMenuItem request = new JMenuItem("网络硬盘", ImageIconTool.gitImageIcon("icons/network.png", ico_Width, ico_Height));
        request.addActionListener(e -> JOptionPane.showMessageDialog(jFrame, "网络请求", "提示", JOptionPane.WARNING_MESSAGE));
        this.muenStyle(request, "1");
        function.add(request);

        this.muenStyle(function, "0");
        bar.add(function);

        JMenu window = new JMenu("窗口显示", false);
        JMenuItem setting = new JMenuItem("设置窗口", ImageIconTool.gitImageIcon("icons/setUp.png", ico_Width, ico_Height));
        setting.addActionListener(e -> windowView.selectButton(windowView.accessDisplay("set_1")));
        this.muenStyle(setting, "1");
        this.muenStyle(window, "0");
        window.add(setting);
        bar.add(window);

        JMenu help = new JMenu("帮助", false);
        JMenuItem contact = new JMenuItem("联系我");
        contact.addActionListener(e -> JOptionPane.showMessageDialog(jFrame, "联系我", "提示", JOptionPane.WARNING_MESSAGE));
        this.muenStyle(contact, "1");
        help.add(contact, JLabel.CENTER);
        JMenuItem aboutMe = new JMenuItem("关于", JLabel.CENTER);
        aboutMe.addActionListener(e -> new AboutProgramDialog(jFrame, "关于").setVisible(true));
        this.muenStyle(aboutMe, "1");
        help.add(aboutMe);
        this.muenStyle(help, "0");
        bar.add(help);
        jFrame.setJMenuBar(bar);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveFile.setEnabled(false);
                saveAsFile.setEnabled(false);
            }
        };
        function.addMouseListener(mouseAdapter);
        window.addMouseListener(mouseAdapter);
        help.addMouseListener(mouseAdapter);
    }

    public void muenStyle(AbstractButton jmuen, String sign) {
        if ("1".equals(sign)) {
            jmuen.setPreferredSize(new Dimension(150, 30));
        } else {
            jmuen.setPreferredSize(new Dimension(100, 30));
            int textLenth = 0;
            switch (jmuen.getText().length()) {
                case 1:
                    textLenth = 10;
                    break;
                case 2:
                    textLenth = 7;
                    break;
                case 3:
                    textLenth = 5;
                    break;
                case 4:
                    textLenth = 4;
                    break;
                case 5:
                    textLenth = 2;
                    break;
                case 6:
                    textLenth = 1;
                    break;
                default:
                    textLenth = 0;
            }
            String conter = "";
            for (int i = 0; i <= textLenth; i++) {
                conter += " ";
            }
            jmuen.setText(conter + jmuen.getText());
        }
    }


}
