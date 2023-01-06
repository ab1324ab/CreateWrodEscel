package com.myigou.clientView.impl.windowSetting.module.SendMessageSetting.module;

import com.myigou.clientView.impl.windowSetting.module.SendMessageSetting.SendMessageSetting;
import com.myigou.tool.ImageIconTool;
import com.myigou.tool.PropertiesTool;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class PersonalInformation {

    private JPanel jpanel;
    private JTextField mynameJLabel;
    private JButton saveJButton;
    private JLabel avatarUrlJLabel;
    private JTextField mysoloIdJLabel;
    private JPanel avatarsList;
    private JScrollPane jscrollpane;

    public JPanel getJpanel() {
        return jpanel;
    }

    public PersonalInformation(SendMessageSetting sendMessageSetting, JDialog dialog, String myavatarUrl, String myname, String mysoloId) {
        jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
        avatarUrlJLabel.setIcon(ImageIconTool.gitImageIcon("/images/avatars/" + myavatarUrl, 100, 100));
        mynameJLabel.setText(myname);
        mysoloIdJLabel.setText(mysoloId);
        GridBagLayout avatarsListGBL = new GridBagLayout();
        GridBagConstraints avatarsListGBC = new GridBagConstraints();
        avatarsList.setLayout(avatarsListGBL);
        int gridx = 0;
        final String[] avaurl = {""};
        for (int i = 0; i < 100; i++) {
            String iLength = i + "";
            if (iLength.length() == 1) iLength = "0" + iLength;
            String avatarimg = "avatar-" + iLength + ".jpeg";
            ImageIcon imageIcon = ImageIconTool.gitImageIcon("/images/avatars/" + avatarimg, 70, 70);
            JLabel jLabel = new JLabel();
            jLabel.setPreferredSize(new Dimension(70, 70));
            jLabel.setIcon(imageIcon);
            jLabel.setName(avatarimg);
            jLabel.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 3));
            jLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Color color = new Color(102, 102, 102);
                    Border border = BorderFactory.createLineBorder(color, 3);
                    JLabel ava = ((JLabel) e.getComponent());
                    ava.setBorder(border);
                    avaurl[0] = ava.getName();
                    avatarUrlJLabel.setIcon(ImageIconTool.gitImageIcon("/images/avatars/" + ava.getName(), 100, 100));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Color color = new Color(184, 184, 184);
                    Border border = BorderFactory.createLineBorder(color, 3);
                    ((JLabel) e.getComponent()).setBorder(border);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Color color = new Color(240, 240, 240);
                    Border border = BorderFactory.createLineBorder(color, 3);
                    ((JLabel) e.getComponent()).setBorder(border);
                }
            });
            if (gridx == 6) gridx = 0;
            avatarsListGBC.gridy = i / 6;
            avatarsListGBC.gridx = gridx;
            avatarsListGBC.fill = GridBagConstraints.HORIZONTAL;
            avatarsListGBC.insets = new Insets(2, 2, 2, 2);
            avatarsListGBL.setConstraints(jLabel, avatarsListGBC);
            avatarsList.add(jLabel);
            gridx++;
        }
        saveJButton.addActionListener(e -> {
            String name = mynameJLabel.getText();
            sendMessageSetting.avatarUrlJLabel.setIcon(ImageIconTool.gitImageIcon("/images/avatars/" + avaurl[0], 70, 70));
            sendMessageSetting.mynameJLabel.setText(name);
            sendMessageSetting.myavatarUrl = avaurl[0];
            PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, "message.avatarUrl", avaurl[0]);
            PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, "message.name", name);
            dialog.setVisible(false);
        });
    }

}
