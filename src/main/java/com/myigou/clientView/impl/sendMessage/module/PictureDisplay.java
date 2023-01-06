package com.myigou.clientView.impl.sendMessage.module;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;

public class PictureDisplay {
    private JPanel jpanel;
    private JLabel imgdisplay;
    private Image transferData = null;
    private int height,width;

    public JPanel getJpanel() {
        return jpanel;
    }

    public PictureDisplay(String message) {
        File file = new File(message);
        try {
            transferData = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        height = transferData.getHeight(null);
        width = transferData.getWidth(null);
//        int fenmu = 0;
//        while (height >= 850 || width >= 850) {
//            fenmu++;
//            height = transferData.getHeight(null) / fenmu;
//            width = transferData.getWidth(null) / fenmu;
//        }
        ImageIcon imageIcon = new ImageIcon(transferData.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        imgdisplay.setIcon(imageIcon);
        imgdisplay.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getWheelRotation()==1){
                    height = height - 30;
                    width = width - 30;
                    ImageIcon imageIcon = new ImageIcon(transferData.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                    imgdisplay.setIcon(imageIcon);
                }
                if(e.getWheelRotation()==-1){
                    height = height + 30;
                    width = width + 30;
                    ImageIcon imageIcon = new ImageIcon(transferData.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                    imgdisplay.setIcon(imageIcon);
                }
            }
        });
    }

}
