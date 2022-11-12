package com.myigou.clientService.messageThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class ClientThread extends Thread {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    // 客户端线程的构造方法
    public ClientThread(Socket socket) {
        try {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            // 接收客户端的基本用户信息
            String inf = reader.readLine();
            StringTokenizer st = new StringTokenizer(inf, "@");
            String temp = "";

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String Time = df.format(new Date());

            // 反馈连接成功信息
//            writer.println("【系统消息】" + user.getName() + user.getIp() + "与服务器连接成功！");
//            writer.flush();

            // 反馈当前在线用户信息
//            if (clients.size() > 0) {
//                for (int i = clients.size() - 1; i >= 0; i--) {
//                    temp += (clients.get(i).getUser().getName() + "/" + clients.get(i).getUser().getIp()) + "@";
//                    if (clients.get(i).getUser().getName().equals(user.getName())) {
//                        writer.println("NAME@" + clients.size() + "@" + temp);//判断姓名是否重复
//                        writer.flush();
//                    }
//                }
//                writer.println("USERLIST@" + clients.size() + "@" + temp);
//                writer.flush();
//            }
//            // 向所有在线用户发送该用户上线命令
//            for (int i = clients.size() - 1; i >= 0; i--) {
//                clients.get(i).getWriter().println("ADD@" + user.getName() + user.getIp());
//                clients.get(i).getWriter().flush();
//            }
//            for (int i = clients.size() - 1; i >= 0; i--) {
//                clients.get(i).getWriter().println("【系统消息】用户" + user.getName() + "上线了！\r\n");
//                clients.get(i).getWriter().flush();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void run() {// 不断接收客户端的消息，进行处理。
        String message = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String Time = df.format(new Date());
        while (true) {
            try {
                message = reader.readLine();// 接收客户端消息
                // 下线命令
                if (message.equals("CLOSE")) {
//                    contentArea.append(Time + "  " + this.getUser().getName() + this.getUser().getIp() + "下线了！\r\n");
                    // 断开连接释放资源
                    reader.close();
                    writer.close();
                    socket.close();
                    // 向所有在线用户发送该用户的下线命令
//                    for (int i = clients.size() - 1; i >= 0; i--) {
//                        clients.get(i).getWriter().println( "DELETE@" + user.getName());
//                        clients.get(i).getWriter().flush();
//                    }
//                    for (int i = clients.size() - 1; i >= 0; i--) {
//                        clients.get(i).getWriter().println( "【系统消息】用户" + user.getName() + "下线了！\r\n");
//                        clients.get(i).getWriter().flush();
//                    }
//                    listModel.removeElement(user.getName());// 更新在线列表
                    // 删除此条客户端服务线程
//                    for (int i = clients.size() - 1; i >= 0; i--) {
//                        if (clients.get(i).getUser() == user) {
//                            ClientThread temp = clients.get(i);
//                            clients.remove(i);// 删除此用户的服务线程
//                            temp.stop();// 停止这条服务线程
//                            return;
//                        }
//                    }
                } else {
                    dispatcherMessage(message);// 多人转发消息

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    // 转发消息
    public void dispatcherMessage(String message) {
        StringTokenizer stringTokenizer = new StringTokenizer(message, "@");
        String source = stringTokenizer.nextToken();
        String owner = stringTokenizer.nextToken();
        String content = stringTokenizer.nextToken();
        String soloId = stringTokenizer.nextToken();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String Time = df.format(new Date());

        if (owner.equals("ALL")) {// 群发
//            message = Time + "  " + source + "：" + content;
//            contentArea.append(message + "\r\n");
//            for (int i = clients.size() - 1; i >= 0; i--) {
//                clients.get(i).getWriter().println(message);
//                clients.get(i).getWriter().flush();
//            }
        } else if (owner.equals("SOLO")) {
//            message = Time + "  " + source + "对" + soloId + "：" + content;
//            contentArea.append(message + "\r\n");
//            for (int i = clients.size() - 1; i >= 0; i--) {
//                if (clients.get(i).getUser().getName().equals(user.getName())) {//当前用户一对一发送
//                    message = Time + "  " + "你对" + soloId + "：" + content;
//                    clients.get(i).getWriter().println(message);
//                    clients.get(i).getWriter().flush();
//                }
//                if (clients.get(i).getUser().getName().equals(soloId)) {//一对一发送给soloId
//                    message = Time + "  " + source + "对你：" + content;
//                    clients.get(i).getWriter().println(message);
//                    clients.get(i).getWriter().flush();
//                }
//            }
        }
    }
}
