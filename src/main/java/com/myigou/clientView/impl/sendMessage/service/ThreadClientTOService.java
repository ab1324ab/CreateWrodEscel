package com.myigou.clientView.impl.sendMessage.service;

import com.alibaba.fastjson2.JSON;
import com.myigou.clientView.impl.sendMessage.SendMessage;
import com.myigou.clientView.impl.sendMessage.model.Customer;
import com.myigou.clientView.impl.sendMessage.model.MessConstant;
import com.myigou.clientView.impl.sendMessage.module.Interlocutor;
import com.myigou.clientView.impl.sendMessage.tool.MessageFileTool;
import com.myigou.tool.PropertiesTool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端连接服务端*
 * 接收客户端发送消息*
 */
public class ThreadClientTOService extends Thread {

    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;
    private Map<String, Interlocutor> initInterlocutorsMap;
    private SendMessage sendMessage;
    private Interlocutor interlocutor;
    private Map<String, String> contentMap;

    public ThreadClientTOService(Socket socket, Map<String, String> map, Map<String, Interlocutor> initInterlocutorsMap, SendMessage sendMessage) throws IOException {
        contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        this.initInterlocutorsMap = initInterlocutorsMap;
        this.sendMessage = sendMessage;
        this.socket = socket;
        writer = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer.println(JSON.toJSONString(map));
        writer.flush();
    }

    @Override
    public void run() {
        String message = null;
        while (true) {
            try {
                if (MessageDispose.messagerct == null) {
                    message = reader.readLine();// 接收客户端消息
                    if (message == null) continue;
                } else {
                    String messageType = MessageDispose.messagerct.getMessageType();
                    if (messageType.equals("INIT")) {
                        initialToService();
                        continue;
                    } else if ("FILE".equals(messageType) || "IMG".equals(messageType)) {
                        MessageDispose.dispatcherMessageFile(messageType, socket);
                    }
                }
                if (message.equals("CLOSE")) {
                    stopThreadClientTOService();
                } else {
                    sendMessage.messageDispose.dispatcherMessage(message, interlocutor);// 接受用户消息
                }
            } catch (SocketException e) {
                stopThreadClientTOService();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initialToService() throws IOException {
        String name = MessageDispose.messagerctInit.getName();
        String avatarUrl = MessageDispose.messagerctInit.getAvatarUrl();
        String addressIp = MessageDispose.messagerctInit.getAddressIp();
        String soloId = MessageDispose.messagerctInit.getSoloId();
        interlocutor = initInterlocutorsMap.get(soloId);
        if (interlocutor == null) {
            String addtype = contentMap.get("message.setting.addType");
            if (MessConstant.MESSAGE_SETTING_ADDTYPE_AUTO.equals(addtype)) {
                Map<String, String> basicMap = new HashMap<>();
                basicMap.put("avatarUrl", avatarUrl);
                basicMap.put("name", name);
                basicMap.put("soloId", soloId);
                MessageFileTool.setBasic(basicMap);// 保存基础连接信息
                interlocutor = new Interlocutor().initialize(sendMessage, basicMap);
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
        Customer customer = new Customer();
        customer.setSoloId(soloId);
        customer.setName(name);
        customer.setAvatarUrl(avatarUrl);
        customer.setAddressIp(addressIp);
        customer.setInterlocutor(interlocutor);
        customer.setWriter(writer);
        customer.setSocket(socket);
        sendMessage.messageUser.put(soloId, customer);
        MessageDispose.messagerct = null;
    }

    /**
     * 停止当前的线程*
     * 关闭消息进程*
     * 下线客户*
     */
    public void stopThreadClientTOService() {
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
