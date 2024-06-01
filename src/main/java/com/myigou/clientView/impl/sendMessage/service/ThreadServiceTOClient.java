package com.myigou.clientView.impl.sendMessage.service;

import com.alibaba.fastjson2.TypeReference;
import com.myigou.clientView.impl.sendMessage.SendMessage;
import com.myigou.clientView.impl.sendMessage.model.Customer;
import com.myigou.clientView.impl.sendMessage.model.MessConstant;
import com.myigou.clientView.impl.sendMessage.module.Interlocutor;
import com.myigou.clientView.impl.sendMessage.tool.MessageFileTool;
import com.myigou.tool.PropertiesTool;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson2.JSON;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

/**
 * 交互对象维护线程
 * 10点33分*
 */
public class ThreadServiceTOClient extends Thread {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Interlocutor interlocutor;
    private SendMessage sendMessage;
    private Map<String, String> contentMap;

    // 客户端线程的构造方法
    public ThreadServiceTOClient(Socket socket, Map<String, Interlocutor> initInterlocutorsMap, SendMessage sendMessage) {
        contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        this.sendMessage = sendMessage;
        try {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            // 接收客户端的基本用户信息
            String init = reader.readLine();
            if (StringUtils.isBlank(init)) return;
            HashMap<String, String> jsonObject = JSON.parseObject(init, new TypeReference<HashMap<String, Object>>() {
            });
            // 保存基础连接信息
            MessageFileTool.setBasic(jsonObject);
            String avatarUrl = jsonObject.get("avatarUrl");
            String name = jsonObject.get("name");
            String soloId = jsonObject.get("soloId");
            String addressIp = jsonObject.get("addressIp");
            interlocutor = initInterlocutorsMap.get(soloId);
            if (interlocutor == null) {
                String addtype = contentMap.get("message.setting.addType");
                if (MessConstant.MESSAGE_SETTING_ADDTYPE_AUTO.equals(addtype)) {
                    interlocutor = new Interlocutor().initialize(sendMessage, jsonObject);
                } else if (MessConstant.MESSAGE_SETTING_ADDTYPE_HAND.equals(addtype)) {
                    Customer customer = new Customer();
                    customer.setSoloId(soloId);
                    customer.setName(name);
                    customer.setAvatarUrl(avatarUrl);
                    customer.setAddressIp(addressIp);
                    sendMessage.toBeAddedCustomer.add(customer);
                }
            } else {
                interlocutor.avatarUrl = avatarUrl;
                interlocutor.name = name;
                interlocutor.upline();
            }
            interlocutor.jpanel.setName(" 【在线】");
            // 发送我的基础资料
            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("messageId", System.currentTimeMillis());
            sendMap.put("avatarUrl", sendMessage.messageDispose.myavatarUrl);
            sendMap.put("name", sendMessage.messageDispose.myname);
            sendMap.put("addressIp", InetAddress.getLocalHost().getHostAddress());
            sendMap.put("message", "");
            sendMap.put("soloId", sendMessage.messageDispose.mysoloId);
            sendMap.put("messageType", "INIT");
            writer.println(JSON.toJSONString(sendMap));
            writer.flush();

            Customer customer = new Customer();
            customer.setSoloId(soloId);
            customer.setName(name);
            customer.setAvatarUrl(avatarUrl);
            customer.setAddressIp(addressIp);
            customer.setInterlocutor(interlocutor);
            customer.setWriter(writer);
            customer.setSocket(socket);
            sendMessage.messageUser.put(soloId, customer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {// 不断接收客户端的消息，进行处理。
        String message = null;
        while (true) {
            try {
                if (MessageDispose.messagerct == null) {
                    message = reader.readLine();// 接收客户端消息
                    if (message == null) continue;
                } else {
                    String messageType = MessageDispose.messagerct.getMessageType();
                    if ("FILE".equals(messageType) || "IMG".equals(messageType)) {
                        MessageDispose.dispatcherMessageFile(messageType, socket);
                    }
                }
                if ("CLOSE".equals(message)) {
                    stopThreadServiceTOClient();
                } else {
                    sendMessage.messageDispose.dispatcherMessage(message, interlocutor);// 接收到用户消息
                }
            } catch (SocketException e) {
                stopThreadServiceTOClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止当前的线程*
     * 关闭消息进程*
     * 下线客户*
     */
    public void stopThreadServiceTOClient() {
        try {
            if (interlocutor != null) interlocutor.logoff();// 下线用户图标
            if (reader != null) reader.close();// 断开连接释放资源
            if (writer != null) writer.close();
            if (socket != null) socket.close();
            this.stop(); //停止线程();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
