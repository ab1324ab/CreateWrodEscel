package com.myigou.clientView.impl.sendMessage.model;

import com.myigou.clientView.impl.sendMessage.module.PictureDisplay;
import com.myigou.tool.WindowTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * 鼠标效果点击提示*
 */
public class ContentMouseAdapter extends MouseAdapter {

    String message;
    String messageType;
    JPanel contentjpanel;
    JLabel filepace;

    public ContentMouseAdapter(String message, String messageType, JPanel contentjpanel, JLabel filepace) {
        this.message = message;
        this.messageType = messageType;
        this.contentjpanel = contentjpanel;
        this.filepace = filepace;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if ("FILE".equals(messageType)) {
            File file = new File(message);
            if (!file.exists()) filepace.setText("文件已被清理!");
            else {
                String vca = "explorer /select, " + file.getPath();
                try {
                    Runtime.getRuntime().exec(vca);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if("IMG".equals(messageType)){
            // 组装自定义 对话框
            JDialog dialog = new JDialog();
            try {
                dialog.setIconImage(ImageIO.read(WindowTool.class.getResource("/icons/tubiao.png")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dialog.setResizable(true);
            dialog.setMinimumSize(new Dimension(830, 660));
            dialog.setMaximumSize(new Dimension(830, 660));
            dialog.setPreferredSize(new Dimension(830, 660));
            Container container = dialog.getContentPane();
            container.setLayout(new BorderLayout());
            container.add(new PictureDisplay(message).getJpanel(), BorderLayout.CENTER);
            dialog.setModal(true);

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        contentjpanel.setBackground(new Color(211, 211, 211));
        if ("COM".equals(messageType))
            contentjpanel.getComponents()[0].setBackground(new Color(211, 211, 211));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        contentjpanel.setBackground(Color.WHITE);
        if ("COM".equals(messageType))
            contentjpanel.getComponents()[0].setBackground(Color.WHITE);
    }
}
