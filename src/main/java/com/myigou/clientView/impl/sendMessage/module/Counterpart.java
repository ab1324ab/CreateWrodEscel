package com.myigou.clientView.impl.sendMessage.module;

import com.myigou.clientView.impl.sendMessage.model.ContentMouseAdapter;
import com.myigou.clientView.impl.sendMessage.model.MessageBody;
import com.myigou.tool.ImageIconTool;

import javax.swing.*;

/**
 * 对方发送消息面板*
 * 2022年11月18日14点01分*
 */
public class Counterpart {

    private JPanel jpanel;
    private JTextArea messageSimple;
    private JLabel avatarIco;
    private JLabel nameJLabel;
    private JPanel contentjpanel;

    public JPanel getJpanel() {
        return jpanel;
    }

    public Counterpart(String avatarUrl, String name, MessageBody messagerct) {
        avatarIco.setIcon(ImageIconTool.gitImageIcon("images/avatars/" + avatarUrl, 70, 70));
        nameJLabel.setText(name);
        messageSimple.setText(messagerct.getMessage());
        messageSimple.addMouseListener(new ContentMouseAdapter(messagerct.getMessage(), "COM", contentjpanel, null));
    }

}