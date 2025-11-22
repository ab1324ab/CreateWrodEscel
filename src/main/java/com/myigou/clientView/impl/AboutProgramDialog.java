package com.myigou.clientView.impl;

import com.myigou.mainView.WindowView;
import com.myigou.tool.ImageIconTool;

import javax.swing.*;
import java.awt.*;

public class AboutProgramDialog extends JDialog {

    private JPanel jpanel;
    private JLabel tubiao;
    private JTextPane thisProgramIsFreeTextPane;
    private JScrollPane jscrollpane;
    private JLabel version;

    public AboutProgramDialog(JFrame owner, String title) {
        super(owner, title);
        jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
        tubiao.setIcon(ImageIconTool.gitImageIcon("icons/tubiao.png", 70, 70));
        thisProgramIsFreeTextPane.setCaretPosition(0);
        version.setText(WindowView.VERSION_THIS);

        this.setResizable(false);
        this.setSize(500, 400);
        this.setMinimumSize(new Dimension(500, 400));
        this.setMaximumSize(new Dimension(500, 400));
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(jpanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }
}
