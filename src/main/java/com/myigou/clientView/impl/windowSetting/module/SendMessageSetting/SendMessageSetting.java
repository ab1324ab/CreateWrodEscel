package com.myigou.clientView.impl.windowSetting.module.SendMessageSetting;

import com.myigou.clientView.impl.sendMessage.SendMessage;
import com.myigou.clientView.impl.sendMessage.model.MessConstant;
import com.myigou.clientView.impl.windowSetting.module.SendMessageSetting.module.PersonalInformation;
import com.myigou.tool.AudioPlayerTool;
import com.myigou.tool.ImageIconTool;
import com.myigou.tool.PropertiesTool;
import com.tulskiy.keymaster.common.Provider;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class SendMessageSetting {
    private JPanel jpanel;
    public JLabel avatarUrlJLabel;
    private JRadioButton addAutoRadioButton;
    private JRadioButton addHandRadioButton;
    private JCheckBox downloadedAuto;
    private JButton button1;
    public JLabel mynameJLabel;
    private JLabel mysoloIdJLabel;
    private JLabel audioJlabelStart;
    private JCheckBox retainCheckBox;
    private JCheckBox weekCheckBox;
    private JCheckBox immediateCheckBox;
    private JScrollPane jscrollpane;
    private JRadioButton activeScanRadioButton;
    private JRadioButton passiveConnectRadioButton;
    private JRadioButton enterRadioButton;
    private JRadioButton ctrlEnterRadioButton;
    private JComboBox keysComboBox;
    private JRadioButton noticeEnabled;
    private JRadioButton noticeDisable;
    private JComboBox audioComboBox;
    private JLabel audioJlabelStop;
    private JButton connectionTestButton;
    private JTextField localAddress;
    private JComboBox comboBox3;
    private JComboBox comboBox2;
    private JComboBox comboBox1;
    private JComboBox comboBox0;
    private JTextField testTextField;
    private JTextField localPort;
    private JLabel errorMsgLabel;
    public String myavatarUrl;
    private Map<String, String> contentMap;
    private TimerTask display = null;

    public JPanel getJpanel() {
        return jpanel;
    }

    public SendMessageSetting(JFrame jFrame) {
        contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
        accountPersonalInformation(jFrame);// 账户个人信息
        howToAddNewFriends();// 新好友添加方式
        quickHotkeys(jFrame);// 快捷键管理
        noticeAudio();// 提示音管理
        commonSettings();// 消息文件管理
        messageFileManage(); //文件自动下载
    }

    /**
     * 账户个人信息*
     * @param jFrame
     */
    public void accountPersonalInformation(JFrame jFrame) {
        String mysoloId = "";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            sc.next();
            mysoloId = sc.next();
            mysoloIdJLabel.setText(mysoloId);
        } catch (Exception e) {
        }
        myavatarUrl = contentMap.get("message.avatarUrl");
        if (StringUtils.isEmpty(myavatarUrl)) {
            Random random = new Random();
            int number = random.nextInt(100);
            myavatarUrl = "avatar-" + number + ".jpeg";
            PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, "message.avatarUrl", myavatarUrl);
        }
        String myname = contentMap.get("message.name");
        if (StringUtils.isEmpty(myname)) myname = "我的名称";
        avatarUrlJLabel.setIcon(ImageIconTool.gitImageIcon("images/avatars/" + myavatarUrl, 70, 70));
        mynameJLabel.setText(myname);
        button1.addActionListener(e -> {
            // 组装自定义 对话框
            JDialog dialog = new JDialog(jFrame, "  个人信息修改");
            dialog.setResizable(false);
            dialog.setSize(500, 460);
            dialog.setMinimumSize(new Dimension(500, 460));
            Container container = dialog.getContentPane();
            container.setLayout(new BorderLayout());
            container.add(new PersonalInformation(this, dialog, myavatarUrl, mynameJLabel.getText(), mysoloIdJLabel.getText()).getJpanel(), BorderLayout.CENTER);
            dialog.setModal(true);

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }

    /**
     * 新好友添加方式*
     */
    public void howToAddNewFriends() {
        ButtonGroup addTypeGroup = new ButtonGroup();
        addTypeGroup.add(addAutoRadioButton);
        addTypeGroup.add(addHandRadioButton);
        addAutoRadioButton.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.addType", MessConstant.MESSAGE_SETTING_ADDTYPE_AUTO));
        addHandRadioButton.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.addType", MessConstant.MESSAGE_SETTING_ADDTYPE_HAND));
        String addType = contentMap.get("message.setting.addType");
        if (MessConstant.MESSAGE_SETTING_ADDTYPE_AUTO.equals(addType)) addAutoRadioButton.setSelected(true);
        else if (MessConstant.MESSAGE_SETTING_ADDTYPE_HAND.equals(addType)) addHandRadioButton.setSelected(true);

        ButtonGroup foundTypeGroup = new ButtonGroup();
        foundTypeGroup.add(activeScanRadioButton);
        foundTypeGroup.add(passiveConnectRadioButton);
        activeScanRadioButton.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.foundType", MessConstant.MESSAGE_SETTING_FOUNDTYPE_ACTIVE));
        passiveConnectRadioButton.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.foundType", MessConstant.MESSAGE_SETTING_FOUNDTYPE_PASSIVE));
        String foundType = contentMap.get("message.setting.foundType");
        if (MessConstant.MESSAGE_SETTING_FOUNDTYPE_ACTIVE.equals(foundType)) activeScanRadioButton.setSelected(true);
        else if (MessConstant.MESSAGE_SETTING_FOUNDTYPE_PASSIVE.equals(foundType))
            passiveConnectRadioButton.setSelected(true);
    }

    /**
     * 快捷键管理*
     */
    public void quickHotkeys(JFrame jFrame) {
        ButtonGroup shortcutGroup = new ButtonGroup();
        shortcutGroup.add(enterRadioButton);
        shortcutGroup.add(ctrlEnterRadioButton);
        enterRadioButton.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.shortcutKey", MessConstant.MESSAGE_SETTING_SHORTCUTKEY_ENTER));
        ctrlEnterRadioButton.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.shortcutKey", MessConstant.MESSAGE_SETTING_SHORTCUTKEY_CTRLEnter));
        String shortcutKey = contentMap.get("message.setting.shortcutKey");
        if (MessConstant.MESSAGE_SETTING_SHORTCUTKEY_ENTER.equals(shortcutKey)) enterRadioButton.setSelected(true);
        else if (MessConstant.MESSAGE_SETTING_SHORTCUTKEY_CTRLEnter.equals(shortcutKey))
            ctrlEnterRadioButton.setSelected(true);
        String percentageText[] = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
        List<String> strings = Arrays.asList(percentageText);
        strings.forEach(it -> keysComboBox.addItem(it));
        String screenshotKey = contentMap.get("message.setting.screenshotKey");
        keysComboBox.setSelectedItem(screenshotKey);
        keysComboBox.addActionListener(e -> {
            String newkey = keysComboBox.getSelectedItem() + "";
            PropertiesTool.writeSet("config.properties", "message.setting.screenshotKey", newkey);
            // 注册设置热键
            Provider provider = Provider.getCurrentProvider(true);
            provider.reset();
            provider.register(KeyStroke.getKeyStroke("ctrl alt " + newkey), arg0 -> SendMessage.operateScreenshot(jFrame));// 当按下热键时调用截图
        });
    }

    /**
     * 提示音管理*
     */
    public void noticeAudio() {
        // 提示音开关
        ButtonGroup audioGroup = new ButtonGroup();
        audioGroup.add(noticeEnabled);
        audioGroup.add(noticeDisable);
        noticeDisable.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.noticeSwitch", MessConstant.DISABLE));
        noticeEnabled.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.noticeSwitch", MessConstant.ENABLED));
        String shortcutKey = contentMap.get("message.setting.noticeSwitch");
        if (MessConstant.DISABLE.equals(shortcutKey)) noticeDisable.setSelected(true);
        else if (MessConstant.ENABLED.equals(shortcutKey)) noticeEnabled.setSelected(true);
        // 提示音文件地址
        List<String> audioname = AudioPlayerTool.audioDirList();
        audioname.forEach(it -> audioComboBox.addItem(it));
        String noticeAudio = contentMap.get("message.setting.noticeAudio");
        audioComboBox.setSelectedItem(noticeAudio);
        audioComboBox.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.noticeAudio", audioComboBox.getSelectedItem() + ""));
        audioJlabelStart.setIcon(ImageIconTool.gitImageIcon("icons/audio3.png", 20, 20));
        audioJlabelStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                audioJlabelStart.setVisible(false);
                audioJlabelStop.setVisible(true);
                AudioPlayerTool.playerStartAudioWav("/audio/" + audioComboBox.getSelectedItem());
                Timer timer = new Timer();
                if (display != null) display.cancel();
                display = new TimerTask() {
                    @Override
                    public void run() {
                        audioJlabelStop.setVisible(false);
                        audioJlabelStart.setVisible(true);
                    }
                };
                timer.schedule(display, 2000);
            }
        });
        audioJlabelStop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                audioJlabelStop.setVisible(false);
                audioJlabelStart.setVisible(true);
                AudioPlayerTool.playerStopAudioWav();
            }
        });
    }

    /**
     * 通用设置*
     */
    public void commonSettings() {
        int it0 = 0, it1 = 0, it2 = 0, it3 = 0;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            it0 = addr.getAddress()[0] & 0xff;
            it1 = addr.getAddress()[1] & 0xff;
            it2 = addr.getAddress()[2] & 0xff;
            it3 = addr.getAddress()[3] & 0xff;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        localAddress.setText(it0 + "." + it1 + "." + it2 + "." + it3);
        for (int i = 0; i < 256; i++) {
            comboBox0.addItem(i);
            comboBox1.addItem(i);
            comboBox2.addItem(i);
            comboBox3.addItem(i);
        }
        comboBox0.setSelectedItem(it0);
        comboBox0.setEnabled(false);
        comboBox1.setSelectedItem(it1);
        comboBox1.setEnabled(false);
        comboBox2.setSelectedItem(it2);
        comboBox3.setSelectedItem(it3);
        String port = contentMap.get("message.setting.localPort");
        localPort.setText(port);
        localPort.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                PropertiesTool.writeSet("config.properties", "message.setting.localPort", localPort.getText());
            }
        });
        testTextField.setText(port);
        connectionTestButton.addActionListener(e -> {
            try {
                String addrip = comboBox0.getSelectedItem() + "." + comboBox1.getSelectedItem() + "." + comboBox2.getSelectedItem() + "." + comboBox3.getSelectedItem();
                SocketAddress socketAddress = new InetSocketAddress(addrip, Integer.parseInt(testTextField.getText()));
                Socket socket = new Socket();
                socket.connect(socketAddress, 1000);
                errorMsgLabel.setForeground(new Color(0, 128, 0));
                errorMsgLabel.setText("连接成功");
            } catch (Exception ex) {
                ex.printStackTrace();
                errorMsgLabel.setForeground(Color.red);
                errorMsgLabel.setText("无法连接或对方未打开端口");
            } finally {
                if (display != null) display.cancel();
                Timer timer = new Timer();
                display = new TimerTask() {
                    @Override
                    public void run() {
                        errorMsgLabel.setText("");
                    }
                };
                timer.schedule(display, 5000);
            }
        });
    }

    /**
     * 消息文件管理*
     */
    public void messageFileManage() {
        ButtonGroup messageFileGroup = new ButtonGroup();
        messageFileGroup.add(retainCheckBox);
        messageFileGroup.add(weekCheckBox);
        messageFileGroup.add(immediateCheckBox);
        retainCheckBox.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.messageFile", MessConstant.MESSAGE_SETTING_MESSAGEFILE_PERPETUAL));
        weekCheckBox.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.messageFile", MessConstant.MESSAGE_SETTING_MESSAGEFILE_ONEWEEK));
        immediateCheckBox.addActionListener(e -> PropertiesTool.writeSet("config.properties", "message.setting.messageFile", MessConstant.MESSAGE_SETTING_MESSAGEFILE_IMMEDIATE));
        String messageFile = contentMap.get("message.setting.messageFile");
        if (MessConstant.MESSAGE_SETTING_MESSAGEFILE_PERPETUAL.equals(messageFile)) retainCheckBox.setSelected(true);
        else if (MessConstant.MESSAGE_SETTING_MESSAGEFILE_ONEWEEK.equals(messageFile)) weekCheckBox.setSelected(true);
        else if (MessConstant.MESSAGE_SETTING_MESSAGEFILE_IMMEDIATE.equals(messageFile))
            immediateCheckBox.setSelected(true);
        // 自动下载文件
        downloadedAuto.addActionListener(e -> {
            if (((JCheckBox) e.getSource()).isSelected())
                PropertiesTool.writeSet("config.properties", "message.setting.fileAutoLoad", MessConstant.ENABLED);
            else
                PropertiesTool.writeSet("config.properties", "message.setting.fileAutoLoad", MessConstant.DISABLE);
        });
        String fileAutoLoad = contentMap.get("message.setting.fileAutoLoad");
        if (MessConstant.ENABLED.equals(fileAutoLoad)) downloadedAuto.setSelected(true);
        if (MessConstant.DISABLE.equals(fileAutoLoad)) downloadedAuto.setSelected(false);
    }

}
