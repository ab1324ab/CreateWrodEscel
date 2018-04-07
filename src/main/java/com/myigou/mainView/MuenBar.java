package com.myigou.mainView;


import com.myigou.tool.ImageIconTool;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.NebulaBrickWallSkin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/6/29.
 */
public class MuenBar {

    public static HashMap<String, JPanel> displayJpael = null;
    public static WindowView windowView = null;
    public static Thread thread = null;
    private int ico_Width = 20;
    private int ico_Height = 20;
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
     *
     * @param jFrame
     */
    public void menuBox(final JFrame jFrame) {
        JMenuBar bar = new JMenuBar();
        JMenu menu1 = new JMenu("文件", false);
        JMenuItem newFile = new JMenuItem("新建文本",ImageIconTool.gitImageIcon("/icons/new.png",ico_Width,ico_Height));
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "新建文本", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        this.muenStyle(newFile, "1");
        menu1.add(newFile);
        JMenuItem openFile = new JMenuItem("打开文本",ImageIconTool.gitImageIcon("/icons/open.png",ico_Width,ico_Height));
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "打开文本", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        this.muenStyle(openFile, "1");
        menu1.add(openFile);
        JMenuItem saveFile = new JMenuItem("保存文本",ImageIconTool.gitImageIcon("/icons/save.png",ico_Width,ico_Height));
        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "保存文本", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        this.muenStyle(saveFile, "1");
        menu1.add(saveFile);
        JMenuItem saveAsFile = new JMenuItem("文本另存",ImageIconTool.gitImageIcon("/icons/saveAs.png",ico_Width,ico_Height));
        saveAsFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "文本另存", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        this.muenStyle(saveAsFile, "1");
        menu1.add(saveAsFile);
        JMenuItem exitFile = new JMenuItem("退出",ImageIconTool.gitImageIcon("/icons/exit.png",ico_Width,ico_Height));
        exitFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.muenStyle(exitFile, "1");
        menu1.add(exitFile);
        this.muenStyle(menu1, "0");
        bar.add(menu1);

        JMenu function = new JMenu("功能选项", false);
        JMenuItem weekPlan = new JMenuItem("周计划",ImageIconTool.gitImageIcon("/icons/excel.png",ico_Width,ico_Height));
        weekPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowView.selectButton(windowView.accessDisplay("fun_1"));
            }
        });
        this.muenStyle(weekPlan, "1");
        function.add(weekPlan);
        JMenuItem weekPlan2 = new JMenuItem("周计划第二版", ImageIconTool.gitImageIcon("/icons/excel.png",ico_Width,ico_Height));
        weekPlan2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowView.selectButton(windowView.accessDisplay("fun_1_2"));
            }
        });
        this.muenStyle(weekPlan2, "1");
        function.add(weekPlan2);
        JMenuItem documentFile = new JMenuItem("文件编辑",ImageIconTool.gitImageIcon("/icons/file.png",ico_Width,ico_Height));
        documentFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowView.selectButton(windowView.accessDisplay("fun_2"));
            }
        });
        this.muenStyle(documentFile, "1");
        function.add(documentFile);
        JMenuItem sendEmail = new JMenuItem("邮件发送",ImageIconTool.gitImageIcon("/icons/email.png",ico_Width,ico_Height));
        sendEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowView.selectButton(windowView.accessDisplay("fun_3"));
            }
        });
        this.muenStyle(sendEmail, "1");
        function.add(sendEmail);
        JMenuItem request = new JMenuItem("网络请求",ImageIconTool.gitImageIcon("/icons/network.png",ico_Width,ico_Height));
        request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "网络请求", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        this.muenStyle(request, "1");
        function.add(request);
        this.muenStyle(function, "0");
        bar.add(function);

        JMenu window = new JMenu("窗口显示", false);
        JMenuItem setting = new JMenuItem("设置窗口",ImageIconTool.gitImageIcon("/icons/setUp.png",ico_Width,ico_Height));
        setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowView.selectButton(windowView.accessDisplay("set_1"));
            }
        });
        this.muenStyle(setting, "1");
        this.muenStyle(window, "0");
        window.add(setting);
        bar.add(window);

        JMenu help = new JMenu("帮助", false);
        JMenuItem contact = new JMenuItem("联系我");
        contact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "联系我", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        this.muenStyle(contact, "1");
        help.add(contact,JLabel.CENTER);
        JMenuItem aboutMe = new JMenuItem("关于",JLabel.CENTER);
        aboutMe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "关于", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        this.muenStyle(aboutMe, "1");
        help.add(aboutMe);
        this.muenStyle(help, "0");
        bar.add(help);
        jFrame.setJMenuBar(bar);
    }

    public void muenStyle(AbstractButton jmuen, String sign) {
        if ("1".equals(sign)) {
            jmuen.setPreferredSize(new Dimension(150, 25));
        }else{
            jmuen.setPreferredSize(new Dimension(100, 25));
            int textLenth = 0;
            switch (jmuen.getText().length()) {
                case 1:textLenth = 10;break;
                case 2:textLenth = 7;break;
                case 3:textLenth = 5;break;
                case 4:textLenth = 4;break;
                case 5:textLenth = 2;break;
                case 6:textLenth = 1;break;
                default:textLenth = 0;
            }
            String conter = "";
            for (int i = 0; i <= textLenth; i++) {
                conter += " ";
            }
            jmuen.setText(conter+jmuen.getText());
        }
    }


}
