package com.myigou.clientView.impl.sendMessage.module;

import com.myigou.clientView.impl.sendMessage.model.ContentMouseAdapter;
import com.myigou.clientView.impl.sendMessage.model.MessageBody;
import com.myigou.tool.ImageIconTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 自己发送消息面板*图片
 * 2022年11月18日14点01分*
 */
public class TheirownIMG {

    private JPanel jpanel;
    private JLabel nameJLabel;
    private JLabel fileimg;
    private JLabel avatarIco;
    public JLabel filepace;
    private JPanel contentjpanel;

    public JPanel getJpanel() {
        return jpanel;
    }

    public TheirownIMG(String avatarUrl, String name, MessageBody messageBody) {
        try {
            avatarIco.setIcon(ImageIconTool.gitImageIcon("images/avatars/" + avatarUrl, 70, 70));
            nameJLabel.setText(name);
            File file = new File(messageBody.getMessage());
            if (!file.exists()) {
                fileimg.setText("图片加载失败!");
                fileimg.setIcon(ImageIconTool.gitImageIcon("icons/ImageFailedLoad.png", 64, 64));
            } else {
                Image transferData = ImageIO.read(file);
                int height = transferData.getHeight(null);
                int width = transferData.getWidth(null);
                int fenmu = 0;
                while (height >= 250 || width >= 250) {
                    fenmu++;
                    height = transferData.getHeight(null) / fenmu;
                    width = transferData.getWidth(null) / fenmu;
                }
                ImageIcon imageIcon = new ImageIcon(transferData.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                fileimg.setIcon(imageIcon);
            }
            contentjpanel.addMouseListener(new ContentMouseAdapter(messageBody.getMessage(), "IMG", contentjpanel, filepace));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
