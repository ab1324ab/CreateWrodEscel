package com.myigou.clientView.impl.sendMessage.module.AutomaticManual.module;

import com.myigou.clientView.impl.sendMessage.module.AutomaticManual.AutomaticManual;
import com.myigou.tool.ImageIconTool;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 添加的面板*
 */
public class AddInterlocutor {

    private JPanel jpanel;
    private JLabel avatarIco;
    private JLabel nameJLabel;
    private JLabel avatarTime;
    private JButton xButton;
    private JCheckBox checkBox1;
    private JPanel jpanel1;
    private AddInterlocutor addInterlocutorc = null;
    private String avatarUrl;
    private String name;
    private String addressIp;
    private String soloId;

    public JPanel getJpanel() {
        return jpanel;
    }

    public AddInterlocutor(String soloId, String avatarUrl, String name, String time) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.soloId = soloId;
        avatarIco.setIcon(ImageIconTool.gitImageIcon("images/avatars/" + avatarUrl, 70, 70));
        nameJLabel.setText(name);
        avatarTime.setText(time);
        xButton.setIcon(ImageIconTool.gitImageIcon("icons/LANMessage/close.png", 25, 25));
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color color = new Color(208, 208, 208);
                jpanel.setBackground(color);
                jpanel1.setBackground(color);
                checkBox1.setBackground(color);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Color color = new Color(226, 226, 226);
                jpanel.setBackground(color);
                jpanel1.setBackground(color);
                checkBox1.setBackground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Color color = new Color(240, 240, 240);
                jpanel.setBackground(color);
                jpanel1.setBackground(color);
                checkBox1.setBackground(color);
            }
        };
        jpanel.addMouseListener(mouseAdapter);
        jpanel1.addMouseListener(mouseAdapter);
        checkBox1.addMouseListener(mouseAdapter);
        xButton.addMouseListener(mouseAdapter);
        xButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("icons/LANMessage/close3.png", 25, 25));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("icons/LANMessage/close2.png", 25, 25));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("icons/LANMessage/close.png", 25, 25));
            }
        });
    }

    /**
     * 分离点击事件*
     * @param avatarUrl
     * @param name
     * @param time
     * @param manual
     */
    public void addToBeSelectListener(String soloId, String avatarUrl, String name, String time, String addressIp, AutomaticManual manual) {
        MouseAdapter mouseAdapter = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(e.getComponent() instanceof JCheckBox)) checkBox1.setSelected(!checkBox1.isSelected());
                if (checkBox1.isSelected()) {
                    addInterlocutorc = new AddInterlocutor(soloId, avatarUrl, name, time);
                    addInterlocutorc.checkBox1.setVisible(false);
                    addInterlocutorc.xButton.setVisible(true);
                    addInterlocutorc.setAddressIp(addressIp);
//                    addInterlocutorc.getJpanel().setName(addressIp);
                    addInterlocutorc.xButton.addActionListener(er -> {
                        checkBox1.setSelected(false);
                        manual.addInterlocutorList.remove(addInterlocutorc);
                        updateChecked(manual); // 更新选中的列表
                    });
                    manual.addInterlocutorList.add(addInterlocutorc);
                } else manual.addInterlocutorList.remove(addInterlocutorc);
                updateChecked(manual); // 更新选中的列表
            }
        };
        jpanel.addMouseListener(mouseAdapter);
        jpanel1.addMouseListener(mouseAdapter);
        checkBox1.addMouseListener(mouseAdapter);
    }

    /**
     * 更新选中的列表*
     * @param manual
     */
    public void updateChecked(AutomaticManual manual) {
        manual.added.removeAll();
        List<AddInterlocutor> list = manual.addInterlocutorList;
        list.forEach(it -> {
            manual.addedGBC.gridy = manual.added.getComponentCount();
            manual.addedGBC.fill = GridBagConstraints.HORIZONTAL;
            manual.addedGBC.gridx = 0;
            manual.addedGBC.weightx = 1;
            manual.addedGBL.setConstraints(it.getJpanel(), manual.addedGBC);
            manual.added.add(it.getJpanel());
        });
        manual.addedVisible();
        if (list.size() != 0) {
            manual.checkPromptJLabel.setText("已选择了 " + list.size() + " 个对象");
            manual.confirmButton.setEnabled(true);
        } else {
            manual.checkPromptJLabel.setText("请勾选需要添加的人");
            manual.confirmButton.setEnabled(false);
        }
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getAddressIp() {
        return addressIp;
    }

    public void setAddressIp(String addressIp) {
        this.addressIp = addressIp;
    }

    public String getSoloId() {
        return soloId;
    }

}
