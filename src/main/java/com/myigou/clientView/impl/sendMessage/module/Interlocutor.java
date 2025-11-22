package com.myigou.clientView.impl.sendMessage.module;

import com.myigou.clientView.impl.sendMessage.SendMessage;
import com.myigou.tool.DateTimeTool;
import com.myigou.tool.ImageIconTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

/**
 * 对话者面板
 * 12点34分
 */
public class Interlocutor {

    public JPanel jpanel;
    private JLabel avatarIco;
    private JLabel nameJLabel;
    private JLabel messageSimple;
    private JPanel jpanel1;
    private JLabel avatarTime;
    private JLabel alertCount;
    private SendMessage sendMessage;
    // 用户头像
    public String avatarUrl;
    public String name;
    // 新消息数量
    private int messagecount = 0;

    /**
     * 初始化新的用户*
     * @param sendMessage
     * @param mapObject
     */
    public Interlocutor initialize(SendMessage sendMessage, Map<String, String> mapObject) {
        this.sendMessage = sendMessage;
        avatarUrl = mapObject.get("avatarUrl");
        if (avatarUrl == null) {
            Random random = new Random();
            int number = random.nextInt(100);
            avatarUrl = number + ".jpeg";
        }
        avatarIco.setIcon(ImageIconTool.gitImageIcon("images/avatars/" + avatarUrl, 70, 70));
        mapObject.put("avatarUrl", avatarUrl);
        name = mapObject.get("name");
        nameJLabel.setText(name + "");
        alertCount.setOpaque(true);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendMessage.mouseClickedList.forEach(it -> ((JComponent) it).setBackground(new Color(240, 240, 240)));
                Color color = new Color(208, 208, 208);
                jpanel.setBackground(color);
                jpanel1.setBackground(color);
                sendMessage.mouseClickedList.clear();
                sendMessage.mouseClickedList.add(jpanel);
                sendMessage.mouseClickedList.add(jpanel1);

                sendMessage.chatNameJlabel.setText(nameJLabel.getText() + jpanel.getName());
                sendMessage.chatNameJlabel.setName(mapObject.get("soloId"));
                // 读取本地存储的消息
                sendMessage.messageDispose.showMessageList(mapObject.get("soloId"));
                alertCount.setVisible(false);
                messagecount = 0;
                sendMessage.aidedJPanelService.messageScrollJPaneVisible();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!sendMessage.mouseClickedList.contains(e.getComponent())) {
                    Color color = new Color(226, 226, 226);
                    jpanel.setBackground(color);
                    jpanel1.setBackground(color);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!sendMessage.mouseClickedList.contains(e.getComponent())) {
                    Color color = new Color(240, 240, 240);
                    jpanel.setBackground(color);
                    jpanel1.setBackground(color);
                }
            }
        };
        jpanel.addMouseListener(mouseAdapter);
        jpanel1.addMouseListener(mouseAdapter);

        sendMessage.interlocutorsGBC.gridy = sendMessage.aidedJPanelService.interlocutorsCentreCount();
        sendMessage.interlocutorsGBC.gridx = 0;
        sendMessage.interlocutorsGBC.weightx = 1;
        sendMessage.interlocutorsGBC.fill = GridBagConstraints.BOTH;
        sendMessage.interlocutorsGBL.setConstraints(jpanel, sendMessage.interlocutorsGBC);
        sendMessage.interlocutorsCentre.add(jpanel);
        sendMessage.aidedJPanelService.interlocutorCentresVisible();
        return this;
    }

    /**
     * 用户上线*
     */
    public void upline() {
        Color color = new Color(240, 240, 240);
        jpanel.setBackground(color);
        jpanel1.setBackground(color);
        nameJLabel.setBackground(color);
        messageSimple.setBackground(color);
        nameJLabel.setText(name + "");
        avatarIco.setIcon(ImageIconTool.gitImageIcon("images/avatars/" + avatarUrl, 70, 70));
        alertCount.setIcon(ImageIconTool.gitImageIcon("icons/alertCountShow.png", 20, 20));
    }

    /**
     * 用户下线*
     */
    public void logoff() {
        Color color = new Color(240, 240, 240);
        jpanel.setBackground(color);
        jpanel1.setBackground(color);
        nameJLabel.setBackground(color);
        messageSimple.setBackground(color);
        jpanel.setName(" 【离线】");
        ImageIcon icon = (ImageIcon) avatarIco.getIcon();
        BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        BufferedImage bufferedImage = ImageIconTool.getGrayImage(bi);
        avatarIco.setIcon(new ImageIcon(bufferedImage));
        alertCount.setIcon(ImageIconTool.gitImageIcon("icons/alertCountHide.png", 20, 20));
    }

    /**
     * 头像短消息*
     * @param messageSimple
     * @param messTime
     */
    public void avatarShortMessage(String soloId, String messageSimple, String messageType, String messTime, boolean initType) {
        if ("IMG".equals(messageType)) this.messageSimple.setText("[图片]");
        else if ("FILE".equals(messageType)) this.messageSimple.setText("[文件]");
        else this.messageSimple.setText(messageSimple);
        avatarTime.setText(DateTimeTool.dateConversion(messTime));
        if (!soloId.equals(sendMessage.chatNameJlabel.getName()) && initType) {
            alertCount.setVisible(true);
            alertCount.setText(++messagecount + "");
        }
    }

}
