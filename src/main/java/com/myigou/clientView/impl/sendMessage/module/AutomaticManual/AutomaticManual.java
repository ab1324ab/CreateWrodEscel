package com.myigou.clientView.impl.sendMessage.module.AutomaticManual;

import com.myigou.clientView.impl.sendMessage.SendMessage;
import com.myigou.clientView.impl.sendMessage.model.Customer;
import com.myigou.clientView.impl.sendMessage.module.AutomaticManual.module.AddInterlocutor;
import com.myigou.clientView.impl.sendMessage.module.Interlocutor;
import com.myigou.clientView.impl.sendMessage.tool.MessageFileTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建待添加的客户*
 * 2022年11月25日12点38分*
 */
public class AutomaticManual {

    private JPanel jpanel;
    private JButton cancelButton;
    public JButton confirmButton;
    private JTextField textField1;
    private JPanel toBeAdded;
    public JPanel added;
    private JScrollPane toBeAddedJScrollPane;
    private JScrollPane addedJScrollPane;
    public JLabel checkPromptJLabel;

    public GridBagLayout addedGBL = new GridBagLayout();
    public GridBagConstraints addedGBC = new GridBagConstraints();
    public List<AddInterlocutor> addInterlocutorList = new ArrayList<>();

    public JPanel getJpanel() {
        return jpanel;
    }

    public AutomaticManual(SendMessage sendMessage, JDialog jDialog, List<Customer> customerList) {
        toBeAddedJScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        addedJScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        GridBagLayout toBeAddedGBL = new GridBagLayout();
        GridBagConstraints toBeAddedGBC = new GridBagConstraints();
        toBeAdded.setLayout(toBeAddedGBL);

        added.setLayout(addedGBL);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

//        customerList.add(new Customer("123456", "avatar-23.jpeg", "使法国1", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-24.jpeg", "奥卡福2", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-25.jpeg", "山豆根山豆根3", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-26.jpeg", "飞机开发4", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-27.jpeg", "使法国5", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-28.jpeg", "奥卡福6", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-29.jpeg", "山豆根山豆根7", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-30.jpeg", "飞机开发8", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-31.jpeg", "使法国9", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-32.jpeg", "奥卡福10", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-33.jpeg", "山豆根山豆根11", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-34.jpeg", "飞机开发12", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-35.jpeg", "使法国13", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-36.jpeg", "奥卡福14", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-37.jpeg", "山豆根山豆根15", "192.168.1.1"));
//        customerList.add(new Customer("123456", "avatar-38.jpeg", "飞机开发16", "192.168.1.1"));

        customerList.forEach(it -> {
            AddInterlocutor addInterlocutor = new AddInterlocutor(it.getSoloId(), it.getAvatarUrl(), it.getName(), df.format(new Date()));
            toBeAddedGBC.gridy = toBeAdded.getComponentCount();
            toBeAddedGBC.fill = GridBagConstraints.HORIZONTAL;
            toBeAddedGBC.gridx = 0;
            toBeAddedGBC.weightx = 1;
            addInterlocutor.addToBeSelectListener(it.getSoloId(), it.getAvatarUrl(), it.getName(), df.format(new Date()), it.getAddressIp(), this);
            toBeAddedGBL.setConstraints(addInterlocutor.getJpanel(), toBeAddedGBC);
            toBeAdded.add(addInterlocutor.getJpanel());
        });
        cancelButton.addActionListener(e -> jDialog.setVisible(false));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInterlocutorList.forEach(it -> {
                    try {
                        String addressIp = it.getAddressIp();
                        String soloId = it.getSoloId();
                        Map<String,String> stringMap = new HashMap<>();
                        stringMap.put("avatarUrl", it.getAvatarUrl());
                        stringMap.put("name", it.getName());
                        stringMap.put("soloId", soloId);
                        stringMap.put("addressIp", addressIp);
                        // 删除已经添加的对象
                        Customer customer = customerList.stream().filter(itc -> itc.getSoloId().equals(soloId)).collect(Collectors.toList()).get(0);
                        customerList.remove(customer);
                        // 保存基础连接信息
                        MessageFileTool.setBasic(stringMap);
                        Interlocutor interlocutor = new Interlocutor().initialize(sendMessage, stringMap);
                        interlocutor.logoff();
                        interlocutor.avatarShortMessage(soloId, "", "", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), false);
                        sendMessage.initInterlocutorsMap.put(stringMap.get("soloId"), interlocutor);
                        // 尝试创建链接
                        SocketAddress socketAddress = new InetSocketAddress(addressIp, sendMessage.port);
                        Socket socket = new Socket();
                        socket.connect(socketAddress, 100);
                        sendMessage.createConnectThread(socket);
                    } catch (IOException ioe) {

                    }
                });
                addInterlocutorList.clear();
                jDialog.setVisible(false);
            }
        });
    }

    /**
     * 切换显示
     */
    public void addedVisible() {
        added.setVisible(false);
        added.setVisible(true);
        added.setVisible(false);
        added.setVisible(true);
    }

}
