package com.myigou.clientView.impl;

import com.myigou.clientView.FunctionInter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ab1324ab on 2018/8/6.
 */
public class SendEmails implements FunctionInter{
    private JCheckBox CheckBox;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addButton;
    private JButton delButton;
    private JPanel jpanel;
    private JPanel topJPanel;
    private JLabel emailJlabel;

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jpanel,BorderLayout.CENTER);
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame, Font font) {
        jPanel.setLayout(new BorderLayout());
        emailJlabel.setFont(font);
        jPanel.add(topJPanel,BorderLayout.CENTER);
        return jPanel;
    }
}
