package com.myigou.clientView.impl.sendMessage.module;

import com.myigou.clientView.impl.sendMessage.model.ContentMouseAdapter;
import com.myigou.clientView.impl.sendMessage.model.MessageBody;
import com.myigou.tool.BusinessTool;
import com.myigou.tool.ImageIconTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.io.File;

/**
 * 对方发送消息面板*文件
 * 2022年11月18日14点01分*
 */
public class CounterpartFile {
    private JPanel jpanel;
    private JLabel nameJLabel;
    private JLabel avatarIco;
    private JLabel fileico;
    private JLabel filename;
    private JLabel filelength;
    private JPanel contentjpanel;
    private JLabel filepace;

    public JPanel getJpanel() {
        return jpanel;
    }

    public CounterpartFile(String avatarUrl, String name, MessageBody messagerct) {
        try {
            avatarIco.setIcon(ImageIconTool.gitImageIcon("images/avatars/" + avatarUrl, 70, 70));
            nameJLabel.setText(name);
            File file = new File(messagerct.getMessage());
            if (!file.exists()) {
                filepace.setVisible(true);
                if (StringUtils.isNotBlank(messagerct.getMessageRemark())) filepace.setText(messagerct.getMessageRemark());
                else filepace.setText("文件已被清理!");
                fileico.setIcon(ImageIconTool.gitImageIcon("icons/LANMessage/close.png", 30, 30));
            }
            if (file.exists()) {
                fileico.setIcon(ImageIconTool.gitImageIcon(file));
                filelength.setText(BusinessTool.fileSizeCalculation(file.length()));
            }
            filename.setText(file.getName());
            contentjpanel.addMouseListener(new ContentMouseAdapter(messagerct.getMessage(), "FILE", contentjpanel, filepace));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
