package com.myigou.clientView.impl;

import com.myigou.clientView.FunctionInter;
import com.myigou.tool.ImageIconTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by ab1324ab on 2018/8/6.
 */
public class SendMessage implements FunctionInter{
    private JPanel jpanel;
    private JPanel topJPanel;
    private JLabel emailJlabel;
    private JPanel countJpanl;
    private JTextField textField1;
    private JPanel leftJpanel;
    private JPanel message;
    private JPanel ctrl;
    private JTextArea TextArea1;
    private JTextArea textArea2;
    private JButton SendButton;
    private JButton expressionButton;
    private JButton updateFileButton;
    private JButton messageButton;
    private JTextPane textPane1;

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jpanel,BorderLayout.CENTER);
//        expressionButton.setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/expression.png",30,30));


        expressionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/expression3.png",30,30));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/expression2.png",30,30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/expression.png",30,30));
            }
        });
        updateFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/updateFile3.png",30,30));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/updateFile2.png",30,30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/updateFile.png",30,30));
            }
        });
        messageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/message3.png",30,30));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/message2.png",30,30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton)e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/message.png",30,30));
            }
        });
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
