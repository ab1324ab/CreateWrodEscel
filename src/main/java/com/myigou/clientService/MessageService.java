package com.myigou.clientService;

import com.myigou.clientService.messageThread.ServerThread;

import java.net.BindException;
import java.net.ServerSocket;

public class MessageService {

    // 启动服务器
    public void serverStart() throws java.net.BindException {
        try {
            ServerSocket serverSocket = new ServerSocket(18323);
            ServerThread serverThread = new ServerThread(serverSocket);
            serverThread.start();
        } catch (BindException e) {
            throw new BindException("端口号已被占用，请换一个！");
        } catch (Exception e1) {
            e1.printStackTrace();
            throw new BindException("启动服务器异常！");
        }
    }


}
