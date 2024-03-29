package SocketKent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class Server {

    private JFrame frame;
    private JTextArea contentArea;
    private JTextField txt_message;
    private JTextField txt_max;
    private JTextField txt_port;
    private JTextField kickOutUser;
    private JButton btn_start;
    private JButton btn_stop;
    private JButton btn_send;
    private JButton btn_kick;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel eastPanel;
    private JPanel eastUpPanel;
    private JPanel eastDownPanel;
    private JPanel westPanel;
    private JScrollPane rightPanel;
    private JScrollPane leftPanel;
    private JSplitPane centerSplit;
    private JList userList;
    private DefaultListModel listModel;

    private ServerSocket serverSocket;
    private ServerThread serverThread;
    private ArrayList<ClientThread> clients;

    private boolean isStart = false;
    private Client client;

    //	Client client = new Client();
    // 主方法,程序执行入口
    public static void main(String[] args) {
        new Server();
    }

    // 执行消息发送
    public void send() {
        if (!isStart) {
            JOptionPane.showMessageDialog(frame, "服务器还未启动,不能发送消息！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (clients.size() == 0) {
            JOptionPane.showMessageDialog(frame, "没有用户在线,不能发送消息！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String message = txt_message.getText().trim();
        if (message == null || message.equals("")) {
            JOptionPane.showMessageDialog(frame, "消息不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormatrmat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String Time = dateFormat.format(new Date());

        sendServerMessage(message);// 群发服务器消息
        contentArea.append(Time + "  " + "服务器说：" + txt_message.getText() + "\r\n");
        txt_message.setText(null);
    }

    // 构造放法
    public Server() {
//		client = new Client();
        frame = new JFrame("服务器");
        // 更改JFrame的图标：
        //frame.setIconImage(Toolkit.getDefaultToolkit().createImage(Client.class.getResource("qq.png")));
//		frame.setIconImage(Toolkit.getDefaultToolkit().createImage(Server.class.getResource("/KeTang/WebContent/img/bg.jpg")));
        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setForeground(Color.black);
        txt_message = new JTextField();
        txt_max = new JTextField(8);
        txt_max.setText("6");
        txt_port = new JTextField(8);
        txt_port.setText("18353");
        kickOutUser = new JTextField(8);
        kickOutUser.setText("xiaohei");
        btn_start = new JButton("启动");
        btn_start.setPreferredSize(new Dimension(120, 10));
        btn_stop = new JButton("停止");
        btn_stop.setPreferredSize(new Dimension(120, 10));
        btn_send = new JButton("发送");
        btn_send.setPreferredSize(new Dimension(120, 10));
        btn_kick = new JButton("踢");
        btn_stop.setEnabled(false);
        listModel = new DefaultListModel();
        userList = new JList(listModel);

        southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(new TitledBorder("写消息"));
        southPanel.add(txt_message, "Center");
        southPanel.add(btn_send, "East");

        leftPanel = new JScrollPane(userList);
        leftPanel.setBorder(new TitledBorder("在线用户"));
        rightPanel = new JScrollPane(contentArea);
        rightPanel.setBorder(new TitledBorder("消息显示区"));

        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        centerSplit.setDividerLocation(100);

        northPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 15));
//		northPanel.setLayout(new GridLayout(1, 6));
        Box bnorthPanel = Box.createHorizontalBox();    //创建横向Box容器
        northPanel.add(bnorthPanel);
        bnorthPanel.add(new JLabel("人数上限："));
        bnorthPanel.add(Box.createHorizontalStrut(20));
        bnorthPanel.add(txt_max);
        bnorthPanel.add(Box.createHorizontalStrut(150));
        bnorthPanel.add(new JLabel("端口："));
        bnorthPanel.add(Box.createHorizontalStrut(20));
        bnorthPanel.add(txt_port);
        bnorthPanel.add(Box.createHorizontalStrut(30));
        bnorthPanel.add(btn_start);
        bnorthPanel.add(Box.createHorizontalStrut(10));
        bnorthPanel.add(btn_stop);
        northPanel.setBorder(new TitledBorder("配置信息"));

        eastUpPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 3, 15));
        eastUpPanel.add(new JLabel("被踢用户:"));
        eastUpPanel.add(kickOutUser);
        eastUpPanel.add(btn_kick);

        eastDownPanel = new JPanel();
        Box beastDownPanel = Box.createVerticalBox();    //创建纵向Box容器
        eastDownPanel.add(beastDownPanel);
        beastDownPanel.add(Box.createVerticalStrut(150));
        beastDownPanel.add(new JLabel("未完待续..."));

        eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(eastUpPanel, "North");
        eastPanel.add(eastDownPanel, "Center");
        eastPanel.setBorder(new TitledBorder("服务器功能操作"));

        westPanel = new JPanel(new BorderLayout());
        westPanel.add(northPanel, "North");
        westPanel.add(centerSplit, "Center");
        westPanel.add(southPanel, "South");

        frame.setLayout(new BorderLayout());

        frame.add(westPanel, "West");
        frame.add(eastPanel, "East");
        frame.setSize(1050, 500);
        //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());//设置全屏
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((screen_width - frame.getWidth()) / 2, (screen_height - frame.getHeight()) / 2);
        frame.setVisible(true);

        // 关闭窗口时事件
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (isStart) {
                    closeServer();// 关闭服务器
                }
                System.exit(0);// 退出程序
            }
        });
        //单击踢出用户
        btn_kick.addActionListener(arg0 -> {
            String a = kickOutUser.getText().trim();
            kickOutUser(a);
        });

        // 文本框按回车键时事件
        txt_message.addActionListener(e -> send());

        // 单击发送按钮时事件
        btn_send.addActionListener(arg0 -> send());

        // 单击启动服务器按钮时事件
        btn_start.addActionListener(e -> {
            if (isStart) {
                JOptionPane.showMessageDialog(frame, "服务器已处于启动状态，不要重复启动！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int max;
            int port;
            try {
                try {
                    max = Integer.parseInt(txt_max.getText());
                } catch (Exception e1) {
                    throw new Exception("人数上限为正整数！");
                }
                if (max <= 0) {
                    throw new Exception("人数上限为正整数！");
                }
                try {
                    port = Integer.parseInt(txt_port.getText());
                } catch (Exception e1) {
                    throw new Exception("端口号为正整数！");
                }
                if (port <= 0) {
                    throw new Exception("端口号 为正整数！");
                }
                serverStart(max, port);
                contentArea.append("服务器已成功启动！人数上限：" + max + ",端口：" + port + "\r\n");
                JOptionPane.showMessageDialog(frame, "服务器成功启动！");
                btn_start.setEnabled(false);
                txt_max.setEnabled(false);
                txt_port.setEnabled(false);
                btn_stop.setEnabled(true);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(frame, exc.getMessage(),
                        "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        // 单击停止服务器按钮时事件
        btn_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isStart) {
                    JOptionPane.showMessageDialog(frame, "服务器还未启动，无需停止！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    closeServer();
                    btn_start.setEnabled(true);
                    txt_max.setEnabled(true);
                    txt_port.setEnabled(true);
                    btn_stop.setEnabled(false);
                    contentArea.append("服务器成功停止！\r\n");
                    JOptionPane.showMessageDialog(frame, "服务器成功停止！");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, "停止服务器发生异常！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // 踢出用户方法
    public void kickOutUser(String kickOutUser) {

        User user = new User(kickOutUser, kickOutUser);
        boolean exist = false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String Time = df.format(new Date());
        //当前是否有启动服务器
        if (!isStart) {
            JOptionPane.showMessageDialog(frame, "服务器还未启动,不能踢出！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //当前是否有用户
        if (clients.size() == 0) {
            JOptionPane.showMessageDialog(frame, "没有用户在线,踢出失败！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = clients.size() - 1; i >= 0; i--) {
            ClientThread temp = clients.get(i);
            if (clients.get(i).getUser().getName().equals(kickOutUser)) {
                //服务器窗口显示该用户强制下线消息
                contentArea.append(Time + "  " + clients.get(i).getUser().getName() + clients.get(i).getUser().getIp() + "被强制下线!\r\n");
                //给该用户发送强制下线消息
                clients.get(i).getWriter().println("【系统消息】用户" + clients.get(i).getUser().getName() + "您已被强制下线!\r\n");
                clients.get(i).getWriter().flush();
                // 更新在线列表
                listModel.removeElement(clients.get(i).getUser().getName());

                clients.get(i).getWriter().println("KickCLOSE");
                clients.get(i).getWriter().flush();

                clients.remove(i);// 删除此用户的服务线程
                temp.stop();// 停止这条服务线程
                exist = true;
                //						client.setConnected(false);
            }
        }

        if (exist == false) {
            JOptionPane.showMessageDialog(frame, "该用户不在线,踢出失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }

        for (int i = clients.size() - 1; i >= 0; i--) {
            // 向所有在线用户发送该用户的下线命令
            clients.get(i).getWriter().println("DELETE@" + user.getName());
            clients.get(i).getWriter().println("【系统消息】用户" + user.getName() + "被强制下线！\r\n");
            clients.get(i).getWriter().flush();

        }


    }


    // 启动服务器
    public void serverStart(int max, int port) throws java.net.BindException {
        try {
            clients = new ArrayList<ClientThread>();
            serverSocket = new ServerSocket(port);
            serverThread = new ServerThread(serverSocket, max);
            serverThread.start();
            isStart = true;
        } catch (BindException e) {
            isStart = false;
            throw new BindException("端口号已被占用，请换一个！");
        } catch (Exception e1) {
            e1.printStackTrace();
            isStart = false;
            throw new BindException("启动服务器异常！");
        }
    }

    // 关闭服务器
    @SuppressWarnings("deprecation")
    public void closeServer() {
        try {
            if (serverThread != null)
                serverThread.stop();// 停止服务器线程

            for (int i = clients.size() - 1; i >= 0; i--) {
                // 给所有在线用户发送关闭命令
                clients.get(i).getWriter().println("CLOSE");
                clients.get(i).getWriter().flush();
                // 释放资源
                clients.get(i).stop();// 停止此条为客户端服务的线程
                clients.get(i).reader.close();
                clients.get(i).writer.close();
                clients.get(i).socket.close();
                clients.remove(i);
            }
            if (serverSocket != null) {
                serverSocket.close();// 关闭服务器端连接
            }
            listModel.removeAllElements();// 清空用户列表
            isStart = false;
        } catch (IOException e) {
            e.printStackTrace();
            isStart = true;
        }
    }

    // 群发服务器消息
    public void sendServerMessage(String message) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String Time = df.format(new Date());
        for (int i = clients.size() - 1; i >= 0; i--) {
            clients.get(i).getWriter().println("【系统消息】" + "服务器：" + message);
            clients.get(i).getWriter().flush();
        }
    }

    // 服务器线程
    class ServerThread extends Thread {
        private ServerSocket serverSocket;
        private int max;// 人数上限

        // 服务器线程的构造方法
        public ServerThread(ServerSocket serverSocket, int max) {
            this.serverSocket = serverSocket;
            this.max = max;
        }

        public synchronized void run() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String Time = df.format(new Date());
            while (true) {// 不停的等待客户端的链接
                try {
                    Socket socket = serverSocket.accept();
                    if (clients.size() == max) {// 如果已达人数上限
                        BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter w = new PrintWriter(socket.getOutputStream());
                        // 接收客户端的基本用户信息
                        String inf = r.readLine();
                        StringTokenizer st = new StringTokenizer(inf, "@");
                        User user = new User(st.nextToken(), st.nextToken());
                        // 反馈连接成功信息
                        w.println("MAX@【系统消息】服务器：对不起，" + user.getName() + user.getIp() + "，服务器在线人数已达上限，请稍后尝试连接！");
                        w.flush();
                        // 释放资源
                        r.close();
                        w.close();
                        socket.close();
                        continue;
                    }


                    ClientThread client = new ClientThread(socket);
                    client.start();// 开启对此客户端服务的线程
                    clients.add(client);
                    listModel.addElement(client.getUser().getName());// 更新在线列表

                    contentArea.append(Time + "  " + client.getUser().getName() + client.getUser().getIp() + "上线了！\r\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // 为一个客户端服务的线程
    class ClientThread extends Thread {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private User user;

        public BufferedReader getReader() {
            return reader;
        }

        public PrintWriter getWriter() {
            return writer;
        }

        public User getUser() {
            return user;
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
                user = new User(st.nextToken(), st.nextToken());
                String temp = "";

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String Time = df.format(new Date());

                // 反馈连接成功信息
                writer.println("【系统消息】" + user.getName() + user.getIp() + "与服务器连接成功！");
                writer.flush();

                // 反馈当前在线用户信息
                if (clients.size() > 0) {
                    for (int i = clients.size() - 1; i >= 0; i--) {
                        temp += (clients.get(i).getUser().getName() + "/" + clients.get(i).getUser().getIp()) + "@";
                        if (clients.get(i).getUser().getName().equals(user.getName())) {
                            writer.println("NAME@" + clients.size() + "@" + temp);//判断姓名是否重复
                            writer.flush();
                        }
                    }


                    writer.println("USERLIST@" + clients.size() + "@" + temp);
                    writer.flush();
                }


                // 向所有在线用户发送该用户上线命令
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println("ADD@" + user.getName() + user.getIp());
//					clients.get(i).getWriter().flush();
//					clients.get(i).getWriter().println(
//							"【系统消息】用户"+user.getName()+"上线了！\r\n");
                    clients.get(i).getWriter().flush();
                }
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println("【系统消息】用户" + user.getName() + "上线了！\r\n");
                    clients.get(i).getWriter().flush();
                }
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

                    if (message.equals("CLOSE"))// 下线命令
                    {
                        contentArea.append(Time + "  " + this.getUser().getName() + this.getUser().getIp() + "下线了！\r\n");
                        // 断开连接释放资源
                        reader.close();
                        writer.close();
                        socket.close();

                        // 向所有在线用户发送该用户的下线命令
                        for (int i = clients.size() - 1; i >= 0; i--) {
                            clients.get(i).getWriter().println("DELETE@" + user.getName());
                            clients.get(i).getWriter().flush();
                        }
                        for (int i = clients.size() - 1; i >= 0; i--) {
                            clients.get(i).getWriter().println("【系统消息】用户" + user.getName() + "下线了！\r\n");
                            clients.get(i).getWriter().flush();
                        }

                        listModel.removeElement(user.getName());// 更新在线列表

                        // 删除此条客户端服务线程
                        for (int i = clients.size() - 1; i >= 0; i--) {
                            if (clients.get(i).getUser() == user) {
                                ClientThread temp = clients.get(i);
                                clients.remove(i);// 删除此用户的服务线程
                                temp.stop();// 停止这条服务线程
                                return;
                            }
                        }
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
                message = Time + "  " + source + "：" + content;
                contentArea.append(message + "\r\n");
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println(message);
                    clients.get(i).getWriter().flush();
                }
            } else if (owner.equals("SOLO")) {
                message = Time + "  " + source + "对" + soloId + "：" + content;
                contentArea.append(message + "\r\n");
                for (int i = clients.size() - 1; i >= 0; i--) {
                    if (clients.get(i).getUser().getName().equals(user.getName())) {//当前用户一对一发送
                        message = Time + "  " + "你对" + soloId + "：" + content;
                        clients.get(i).getWriter().println(message);
                        clients.get(i).getWriter().flush();
                    }
                    if (clients.get(i).getUser().getName().equals(soloId)) {//一对一发送给soloId
                        message = Time + "  " + source + "对你：" + content;
                        clients.get(i).getWriter().println(message);
                        clients.get(i).getWriter().flush();
                    }
                }
            }


        }
        //		public void dispatcherSoloMessage(String message) {
//			StringTokenizer stringTokenizer = new StringTokenizer(message, "@");
//			String source = stringTokenizer.nextToken();
//			String owner = stringTokenizer.nextToken();
//			String content = stringTokenizer.nextToken();
//			String soloId = stringTokenizer.nextToken();
//			message = source + "：" + content;
//			contentArea.append(message + "\r\n");
//
//        String soloString = client.soloId;
//				System.out.println("2222222222");
//				System.out.println(soloString+"11111111111");
//				for (int i = clients.size() - 1; i >= 0; i--) {
//            if(clients.get(i).getName().equals(soloString)) {
//                clients.get(i).getWriter().println(message + "(单独发送)");
//                clients.get(i).getWriter().flush();
//            }
//
//		}
    }
}

