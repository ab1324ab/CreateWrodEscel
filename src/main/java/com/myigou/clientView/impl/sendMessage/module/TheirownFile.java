package com.myigou.clientView.impl.sendMessage.module;

import com.myigou.clientView.impl.sendMessage.model.ContentMouseAdapter;
import com.myigou.clientView.impl.sendMessage.model.MessageBody;
import com.myigou.tool.BusinessTool;
import com.myigou.tool.ImageIconTool;

import javax.swing.*;
import java.io.File;

/**
 * 自己发送消息面板*文件
 * 2022年11月18日14点01分*
 */
public class TheirownFile {

    private JPanel jpanel;
    private JLabel nameJLabel;
    private JLabel avatarIco;
    private JLabel fileico;
    private JLabel filename;
    private JLabel filelength;
    public JLabel filepace;
    private JPanel contentjpanel;

    public JPanel getJpanel() {
        return jpanel;
    }

    public TheirownFile(String avatarUrl, String name, MessageBody messageBody) {
        try {
            avatarIco.setIcon(ImageIconTool.gitImageIcon("/images/avatars/" + avatarUrl, 70, 70));
            nameJLabel.setText(name);
            File file = new File(messageBody.getMessage());
            if (!file.exists()) {
                filepace.setVisible(true);
                filepace.setText("文件已被清理!");
                fileico.setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/close.png", 30, 30));
            }
            if (file.exists()) {
                ImageIcon imageIcon = new ImageIcon(sun.awt.shell.ShellFolder.getShellFolder(file).getIcon(true));
                fileico.setIcon(imageIcon);
                filelength.setText(BusinessTool.fileSizeCalculation(file.length()));
            }
            filename.setText(file.getName());
            contentjpanel.addMouseListener(new ContentMouseAdapter(messageBody.getMessage(), "FILE", contentjpanel, filepace));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
