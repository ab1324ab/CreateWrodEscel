package com.myigou.clientView.impl.sendMessage.service;

import com.myigou.tool.ImageIconTool;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 面板辅助功能*
 * 2022年11月22日18点45分*
 */
public class AidedJPanelService {

    private Timer errorTimer = null;
    private JPanel interlocutorsCentre;
    private JPanel messageJPane;
    private JLabel errorMessJlabel;

    public AidedJPanelService(JPanel interlocutorsCentre, JPanel messageJPane, JLabel errorMessJlabel) {
        this.interlocutorsCentre = interlocutorsCentre;
        this.messageJPane = messageJPane;
        this.errorMessJlabel = errorMessJlabel;
    }

    /**
     * 获取用户古列表里的下一位的位置*
     * @return
     */
    public int interlocutorsCentreCount() {
        int count = interlocutorsCentre.getComponentCount();
        if (count == 0) return count;
        else return count + 1;
    }

    /**
     * 消息列表切换显示
     */
    public void messageScrollJPaneVisible() {
        messageJPane.setVisible(false);
        messageJPane.setVisible(true);
        messageJPane.setVisible(false);
        messageJPane.setVisible(true);
    }

    /**
     * 用户列表切换显示
     */
    public void interlocutorCentresVisible() {
        interlocutorsCentre.setVisible(false);
        interlocutorsCentre.setVisible(true);
        interlocutorsCentre.setVisible(false);
        interlocutorsCentre.setVisible(true);
    }

    /**
     * 错误提示延时自动关闭*
     */
    public void timerErrorMessJLabel(String errmess, long delay) {
        if (errorTimer != null) errorTimer.cancel();
        errorMessJlabel.setText(errmess);
        errorMessJlabel.setIcon(ImageIconTool.gitImageIcon("images/errmess.png", 150, 44));
        errorMessJlabel.setVisible(true);
        errorTimer = new Timer();
        errorTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                errorMessJlabel.setVisible(false);
                errorTimer.cancel();
            }
        }, delay);
    }

}