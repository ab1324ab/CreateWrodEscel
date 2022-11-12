package com.myigou.clientService.messageThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class ServerThread extends Thread {

    private ServerSocket serverSocket;

    // 服务器线程的构造方法
    public ServerThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public synchronized void run() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String Time = df.format(new Date());
        while (true) {// 不停的等待客户端的链接
            try {
                Socket socket = serverSocket.accept();

                BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter w = new PrintWriter(socket.getOutputStream());
                // 接收客户端的基本用户信息
                String inf = r.readLine();
                StringTokenizer st = new StringTokenizer(inf, "@");
                w.flush();
                // 释放资源
                r.close();
                w.close();
                socket.close();

                ClientThread client = new ClientThread(socket);
                client.start();// 开启对此客户端服务的线程
//                clients.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
