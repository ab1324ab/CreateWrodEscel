package com.myigou.clientView.impl.sendMessage.module;

import com.myigou.clientView.impl.sendMessage.model.ContentMouseAdapter;
import com.myigou.clientView.impl.sendMessage.model.MessageBody;
import com.myigou.tool.ImageIconTool;

import javax.swing.*;

/**
 * 自己发送消息面板*
 * 2022年11月18日14点01分*
 */
public class Theirown {

    private JPanel jpanel;
    private JLabel nameJLabel;
    private JTextArea messageSimple;
    private JLabel avatarIco;
    private JPanel contentjpanel;

    public JPanel getJpanel() {
        return jpanel;
    }

    public Theirown(String avatarUrl, String name, MessageBody messageBody) {
        avatarIco.setIcon(ImageIconTool.gitImageIcon("images/avatars/" + avatarUrl, 70, 70));
        nameJLabel.setText(name);
        messageSimple.setText(messageBody.getMessage());
        messageSimple.addMouseListener(new ContentMouseAdapter(messageBody.getMessage(), "COM", contentjpanel, null));
    }

}
