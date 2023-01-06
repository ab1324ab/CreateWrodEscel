package com.myigou.clientView.impl.sendMessage.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.myigou.clientView.impl.sendMessage.SendMessage;
import com.myigou.clientView.impl.sendMessage.model.CustomerBasic;
import com.myigou.clientView.impl.sendMessage.model.MessConstant;
import com.myigou.clientView.impl.sendMessage.model.MessageBody;
import com.myigou.clientView.impl.sendMessage.module.*;
import com.myigou.clientView.impl.sendMessage.tool.MessageFileTool;
import com.myigou.tool.AudioPlayerTool;
import com.myigou.tool.JSONConvertTool;
import com.myigou.tool.PropertiesTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 消息处理方法*
 * 2022年11月22日17点48分*
 */
public class MessageDispose {

    private SendMessage sendMessage;
    private GridBagLayout messageScrollJPaneGBL;
    private GridBagConstraints messageScrollJPaneGBC;
    private JPanel messageJPane;
    // 自己的基础信息
    public String mysoloId = "";
    public String myavatarUrl = "";
    public String myname = "";
    public static Map<String, String> contentMap;
    // 消息变量体
    public static MessageBody messagerct = null;
    public static CustomerBasic messagerctInit = null;

    public MessageDispose(SendMessage sendMessage, GridBagLayout messageScrollJPaneGBL, GridBagConstraints messageScrollJPaneGBC, JPanel messageJPane) {
        this.sendMessage = sendMessage;
        this.messageScrollJPaneGBL = messageScrollJPaneGBL;
        this.messageScrollJPaneGBC = messageScrollJPaneGBC;
        this.messageJPane = messageJPane;
        contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        // 服务器启动配置
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            sc.next();
            mysoloId = sc.next();
            myavatarUrl = contentMap.get("message.avatarUrl");
            if (StringUtils.isEmpty(myavatarUrl)) {
                Random random = new Random();
                int number = random.nextInt(100);
                myavatarUrl = "avatar-" + number + ".jpeg";
                PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, "message.avatarUrl", myavatarUrl);
            }
            myname = contentMap.get("message.name");
            if (StringUtils.isEmpty(myname)) myname = "我的名称";
        } catch (Exception e) {
        }

    }

    /**
     * 发送用户消息
     *
     * @param messageBody
     */
    public void sendTransmitMessage(MessageBody messageBody, Socket socketWriter) throws IOException, InterruptedException {
        String message = messageBody.getMessage();
        String messageType = messageBody.getMessageType();
        String soloId = messageBody.getMessageObject();
        // 显示发送信息
        Object theirown = showTransmitMessage(myavatarUrl, myname, messageBody.getMessageType(), messageBody);
        // 保存消息来源
        messageBody.setMessageObject(mysoloId);
        JSONConvertTool.writeJson(MessageFileTool.getSaveMessageFilePath(soloId), JSON.toJSONString(messageBody, JSONWriter.Feature.NullAsDefaultValue), true);
        new Thread(() -> {
            FileInputStream fis = null;
            try {
                OutputStream writer = socketWriter.getOutputStream();
                writer.write(JSON.toJSONString(messageBody).getBytes(StandardCharsets.UTF_8));
                writer.write("\r\n".getBytes(StandardCharsets.UTF_8));
                writer.flush();
                Thread.sleep(500);

                if ("FILE".equals(messageType) || "IMG".equals(messageType)) {
                    File file = new File(message);
                    fis = new FileInputStream(file);
                    DataOutputStream dos = new DataOutputStream(writer);
                    dos.writeUTF(file.getName());
                    dos.flush();
                    dos.writeLong(file.length());
                    dos.flush();
                    // 开始传输文件
                    System.out.println("======== 开始传输文件 ========");
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    long progress = 0;
                    TheirownFile theiF = null;
                    TheirownIMG theiI = null;
                    if (theirown instanceof TheirownFile) {
                        theiF = ((TheirownFile) theirown);
                        theiF.filepace.setVisible(true);
                        theiF.filepace.setText("0%");
                    } else if (theirown instanceof TheirownIMG) {
                        theiI = ((TheirownIMG) theirown);
                        theiI.filepace.setVisible(true);
                        theiI.filepace.setText("0%");
                    }
                    while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                        dos.write(bytes, 0, length);
                        dos.flush();
                        progress += length;
                        if (theiF != null) theiF.filepace.setText((100 * progress / file.length()) + "%");
                        if (theiI != null) theiI.filepace.setText((100 * progress / file.length()) + "%");
                    }
                    System.out.println();
                    System.out.println("======== 文件传输成功 ========");
                    if (theiF != null) theiF.filepace.setVisible(false);
                    if (theiI != null) theiI.filepace.setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 接收用户消息
     *
     * @param messageJson
     */
    public void dispatcherMessage(String messageJson, Interlocutor interlocutor) {
        if (messagerct != null) {
            String message = messagerct.getMessage();
            String messageType = messagerct.getMessageType();
            String soloId = messagerct.getMessageObject();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String Time = df.format(new Date());
            // 处理头像的消息 提醒
            interlocutor.avatarShortMessage(soloId, message, messageType, Time, true);
            showReceiveMessage(soloId, interlocutor.avatarUrl + "", interlocutor.name + "", messagerct);
            // 保存消息来源
            JSONConvertTool.writeJson(MessageFileTool.getSaveMessageFilePath(soloId), JSON.toJSONString(messagerct, JSONWriter.Feature.NullAsDefaultValue), true);
            String shortcutKey = contentMap.get("message.setting.noticeSwitch");
            // 新消息提示音
            if (MessConstant.ENABLED.equals(shortcutKey)) {
                String noticeAudio = contentMap.get("message.setting.noticeAudio");
                AudioPlayerTool.playerStartAudioWav("/audio/" + noticeAudio);
            }
            messagerct = null;
        } else {
            try {
                messagerctInit = JSON.parseObject(messageJson, CustomerBasic.class);
                messagerct = JSON.parseObject(messageJson, MessageBody.class);
            } catch (Exception e) {
                e.printStackTrace();
                messagerct = null;
            }
        }
    }

    /**
     * 接收消息*文件*
     */
    public static void dispatcherMessageFile(String messageType, Socket socket) {
        try {
            byte[] bytes = new byte[512];
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String filename = dis.readUTF();
            long fileLength = dis.readLong();
            String soloId = MessageDispose.messagerct.getMessageObject();
            String savepathroot = "";
            if ("FILE".equals(messageType)) savepathroot = MessageFileTool.getSaveFilePath(soloId);
            else if ("IMG".equals(messageType)) savepathroot = MessageFileTool.getSaveImagePath(soloId);
            String saveFilePath = savepathroot + filename;
            File file = new File(saveFilePath);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                saveFilePath = savepathroot + System.currentTimeMillis() + "_" + filename;
                file = new File(saveFilePath);
                if (!file.exists()) file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(saveFilePath);
            int sizeall = 0, length;
            String fileAutoLoad = contentMap.get("message.setting.fileAutoLoad");
            while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
                if (MessConstant.ENABLED.equals(fileAutoLoad) || !"FILE".equals(messageType)) {
                    fos.write(bytes, 0, length);
                    fos.flush();
                }
                sizeall += length;
                if (sizeall >= fileLength) break;
            }
            fos.close();
            MessageDispose.messagerct.setMessage(saveFilePath);
            if ("FILE".equals(messageType) && MessConstant.DISABLE.equals(fileAutoLoad)) {
                file.delete();
                MessageDispose.messagerct.setMessageRemark("自动下载文件已关闭!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送单个消息面板
     *
     * @param avatarUrl
     * @param name
     * @param messageBody
     */
    private Object showTransmitMessage(String avatarUrl, String name, String messageType, MessageBody messageBody) {
        int count = messageJPane.getComponentCount();
        messageScrollJPaneGBC.gridy = count + 1;
        messageScrollJPaneGBC.gridx = 0;
        messageScrollJPaneGBC.weightx = 1;
        messageScrollJPaneGBC.fill = GridBagConstraints.HORIZONTAL;
        // 判断当前显示面板对话对象是否是发送对象.. 不是则不显示
        JPanel theirown = null;
        Object thei = null;
        if ("COM".equals(messageType)) {
            Theirown theirow = new Theirown(avatarUrl, name, messageBody);
            theirown = theirow.getJpanel();
            thei = theirow;
        } else if ("FILE".equals(messageType)) {
            TheirownFile theirownFile = new TheirownFile(avatarUrl, name, messageBody);
            theirown = theirownFile.getJpanel();
            thei = theirownFile;
        } else if ("IMG".equals(messageType)) {
            TheirownIMG theirownIMG = new TheirownIMG(avatarUrl, name, messageBody);
            theirown = theirownIMG.getJpanel();
            thei = theirownIMG;
        }
        messageScrollJPaneGBL.setConstraints(theirown, messageScrollJPaneGBC);
        messageJPane.add(theirown);
        sendMessage.aidedJPanelService.messageScrollJPaneVisible();
        return thei;
    }

    /**
     * 接收单个消息面板
     *
     * @param soloId
     * @param avatarUrl
     * @param name
     * @param messagerct
     */
    private void showReceiveMessage(String soloId, String avatarUrl, String name, MessageBody messagerct) {
        int count = messageJPane.getComponentCount();
        messageScrollJPaneGBC.gridy = count + 1;
        messageScrollJPaneGBC.gridx = 0;
        messageScrollJPaneGBC.weightx = 1;
        messageScrollJPaneGBC.fill = GridBagConstraints.HORIZONTAL;
        // 判断当前显示面板对话对象是否是发送对象.. 不是则不显示
        if (!soloId.equals(sendMessage.chatNameJlabel.getName())) return;
        JPanel counterpart = null;
        if ("COM".equals(messagerct.getMessageType())) {
            counterpart = new Counterpart(avatarUrl, name, messagerct).getJpanel();
        } else if ("FILE".equals(messagerct.getMessageType())) {
            counterpart = new CounterpartFile(avatarUrl, name, messagerct).getJpanel();
        } else if ("IMG".equals(messagerct.getMessageType())) {
            counterpart = new CounterpartIMG(avatarUrl, name, messagerct).getJpanel();
        }
        messageScrollJPaneGBL.setConstraints(counterpart, messageScrollJPaneGBC);
        messageJPane.add(counterpart);
        endLineJScroll(count);
        sendMessage.aidedJPanelService.messageScrollJPaneVisible();
    }

    /**
     * 生成消息滚动面板数据
     *
     * @param soloId
     */
    public void showMessageList(String soloId) {
        messageJPane.removeAll();
        String saveMessageFilePath = MessageFileTool.getSaveMessageFilePath(soloId);
        if (!new File(saveMessageFilePath).exists()) return;
        String m = JSONConvertTool.readJson(saveMessageFilePath);
        String[] ms = m.split("\r\n");
        messageScrollJPaneGBC.gridx = 0;
        messageScrollJPaneGBC.weightx = 1;
        messageScrollJPaneGBC.fill = GridBagConstraints.HORIZONTAL;
        // 没有消息不显示
        if (StringUtils.isEmpty(m)) return;
        for (int i = 0; i < ms.length; i++) {
            messageScrollJPaneGBC.gridy = i;
            MessageBody messageBody = JSON.parseObject(ms[i], MessageBody.class);
            String tasoloId = messageBody.getMessageObject();
            String messageType = messageBody.getMessageType();
            JPanel jPanel = null;
            if (!tasoloId.equals(mysoloId)) {
                Map<String, String> basMap = MessageFileTool.getBasicInfo(tasoloId);
                if ("COM".equals(messageType)) {
                    jPanel = new Counterpart(basMap.get("avatarUrl"), basMap.get("name"), messageBody).getJpanel();
                } else if ("FILE".equals(messageType)) {
                    jPanel = new CounterpartFile(basMap.get("avatarUrl"), basMap.get("name"), messageBody).getJpanel();
                } else if ("IMG".equals(messageType)) {
                    jPanel = new CounterpartIMG(basMap.get("avatarUrl"), basMap.get("name"), messageBody).getJpanel();
                }
            } else {
                if ("COM".equals(messageType)) {
                    jPanel = new Theirown(myavatarUrl, myname, messageBody).getJpanel();
                } else if ("FILE".equals(messageType)) {
                    jPanel = new TheirownFile(myavatarUrl, myname, messageBody).getJpanel();
                } else if ("IMG".equals(messageType)) {
                    jPanel = new TheirownIMG(myavatarUrl, myname, messageBody).getJpanel();
                }
            }
            messageScrollJPaneGBL.setConstraints(jPanel, messageScrollJPaneGBC);
            messageJPane.add(jPanel);
        }
        endLineJScroll(ms.length);
        sendMessage.aidedJPanelService.messageScrollJPaneVisible();
    }

    /**
     * 定位滚动条到行尾
     *
     * @param index
     */
    public void endLineJScroll(int index) {
        JTextArea jTextArea = new JTextArea(" ");
        jTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 1));
        jTextArea.setEditable(false);
        jTextArea.setBackground(new Color(240, 240, 240));
        jTextArea.setCaretPosition(0);
        messageScrollJPaneGBC.gridy = index;
        messageScrollJPaneGBL.setConstraints(jTextArea, messageScrollJPaneGBC);
        messageJPane.add(jTextArea);
    }

}
