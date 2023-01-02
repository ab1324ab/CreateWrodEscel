package SocketKent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class Client {

    private JFrame frame;
    public JList userList;
    public int i;
    public String soloId;
    private JTextArea textArea;
    private JTextArea systemTextArea;
    private JTextField textField;
    private JTextField txt_port;
    private JTextField txt_hostIp;
    private JTextField txt_name;
    //	private JComboBox select;
//	private JList jList;
    private JButton btn_start;
    private JButton btn_stop;
    private JButton btn_send;
    private JPanel northPanel;
    private JPanel southPanel;
    private JScrollPane rightScroll;
    private JScrollPane leftScroll;
    private JSplitPane centerSplit;

    private DefaultListModel listModel;
    private boolean isConnected = false;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private MessageThread messageThread;// 负责接收消息的线程
    private Map<String, User> onLineUsers = new HashMap<String, User>();// 所有在线用户

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    // 主方法,程序入口
    public static void main(String[] args) {
        new Client();
    }

    // 执行发送
    public void send(String c) {
        if (!isConnected) {
            JOptionPane.showMessageDialog(frame, "还没有连接服务器，无法发送消息！", "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String message = textField.getText().trim();
        if (message == null || message.equals("")) {
            JOptionPane.showMessageDialog(frame, "消息不能为空！", "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isConnected == false) {
            JOptionPane.showMessageDialog(frame, "您已被強制下线！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (c == "all") {
            String msage = "{ \"soloId\":\"" + serial + "\", \"messageId\":\"1234567890\", \"message\":\"" + message + "\", \"messageType\":\"COM\" }";
            sendMessage(msage);
            textField.setText(null);
            return;
        }
        if (c == "solo") {
            System.out.println(soloId + "soloId");
            sendMessage(frame.getTitle() + "@" + "SOLO" + "@" + message + "@" + soloId);
            textField.setText(null);
            return;
        }
    }

    // 构造方法
    public Client() {
        textArea = new JTextArea();
        systemTextArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setForeground(Color.BLACK);
        systemTextArea.setEditable(false);
        systemTextArea.setForeground(Color.blue);

        textField = new JTextField();
        txt_port = new JTextField(8);
        txt_port.setText("18353");//设置大小
        txt_hostIp = new JTextField(8);
        txt_hostIp.setText("127.0.0.1");
        txt_name = new JTextField(8);
        txt_name.setText("xiaohei");
        btn_start = new JButton("连接");
        btn_start.setPreferredSize(new Dimension(120, 10));
        btn_stop = new JButton("断开");
        btn_stop.setPreferredSize(new Dimension(120, 10));
        btn_send = new JButton("发送");
        btn_send.setPreferredSize(new Dimension(120, 10));
        listModel = new DefaultListModel();
        userList = new JList(listModel);


        northPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 15));
        Box bnorthPanel = Box.createHorizontalBox();    //创建横向Box容器
        northPanel.add(bnorthPanel);
        bnorthPanel.add(new JLabel("姓名:"));
        bnorthPanel.add(Box.createHorizontalStrut(20));
        bnorthPanel.add(txt_name);
        bnorthPanel.add(Box.createHorizontalStrut(150));
        bnorthPanel.add(new JLabel("服务器IP:"));
        bnorthPanel.add(Box.createHorizontalStrut(20));
        bnorthPanel.add(txt_hostIp);
        bnorthPanel.add(Box.createHorizontalStrut(130));
        bnorthPanel.add(new JLabel("端口:"));
        bnorthPanel.add(Box.createHorizontalStrut(20));
        bnorthPanel.add(txt_port);

        bnorthPanel.add(Box.createHorizontalStrut(30));
        bnorthPanel.add(btn_start);
        bnorthPanel.add(Box.createHorizontalStrut(10));
        bnorthPanel.add(btn_stop);
        northPanel.setBorder(new TitledBorder("连接信息"));

        rightScroll = new JScrollPane(textArea);
        textArea.add(systemTextArea);
        rightScroll.setBorder(new TitledBorder("消息显示区"));
        leftScroll = new JScrollPane(userList);
        leftScroll.setBorder(new TitledBorder("在线用户"));

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(textField, "Center");
//		southPanel.add(select,"Center");
        southPanel.add(btn_send, "East");
        southPanel.setBorder(new TitledBorder("写消息"));

        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, rightScroll);
        centerSplit.setDividerLocation(100);

        frame = new JFrame("客户机");
        // 更改JFrame的图标：
//		frame.setIconImage(Toolkit.getDefaultToolkit().createImage(Client.class.getResource("qq.png")));
        frame.setLayout(new BorderLayout());
        frame.add(northPanel, "North");
        frame.add(centerSplit, "Center");
        frame.add(southPanel, "South");

        frame.setSize(1050, 500);
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((screen_width - frame.getWidth()) / 2, (screen_height - frame.getHeight()) / 2);
        frame.setVisible(true);

        // 写消息的文本框中按回车键时事件
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                send("all");
            }
        });
        //单击在线用户事件
        userList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                int i = userList.getSelectedIndex();
                soloId = (String) listModel.get(i);
                send("solo");
//				for (int i = listModel.size() - 1; i >= 0; i--) {
//
//					System.out.println(listModel.get(i)+"111111111111");
//				}
//				new SeparateChat();

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }


        });
        // 单击发送按钮时事件
        btn_send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send("all");
            }
        });

        // 单击连接按钮时事件
        btn_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int port;
                if (isConnected) {
                    JOptionPane.showMessageDialog(frame, "已处于连接上状态，不要重复连接！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    try {
                        port = Integer.parseInt(txt_port.getText().trim());
                    } catch (NumberFormatException e2) {
                        throw new Exception("端口号不符合要求！端口为整数！");
                    }
                    String hostIp = txt_hostIp.getText().trim();
                    String name = txt_name.getText().trim();
                    if (name.equals("") || hostIp.equals("")) {
                        throw new Exception("姓名、服务器IP不能为空！");
                    }

//
                    boolean flag = connectServer(port, hostIp, name);
                    if (flag == false) {
                        throw new Exception("与服务器连接失败！");

                    }
                    frame.setTitle(name);
                    JOptionPane.showMessageDialog(frame, "成功连接!");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, exc.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 单击断开按钮时事件
        btn_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isConnected) {
                    JOptionPane.showMessageDialog(frame, "已处于断开状态，不要重复断开！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    boolean flag = closeConnection();// 断开连接
                    if (flag == false) {
                        throw new Exception("断开连接发生异常！");
                    }
                    JOptionPane.showMessageDialog(frame, "成功断开！");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, exc.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 关闭窗口时事件
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isConnected) closeConnection();// 关闭连接
                System.exit(0);// 退出程序
            }
        });


    }

    String serial = "";

    /**
     * 连接服务器
     *
     * @param port
     * @param hostIp
     * @param name
     */
    public boolean connectServer(int port, String hostIp, String name) {
        // 连接服务器
        try {
            socket = new Socket(hostIp, port);// 根据端口号和服务器ip建立连接
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            try {
                Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
                process.getOutputStream().close();
                Scanner sc = new Scanner(process.getInputStream());
                sc.next();
                serial = sc.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Random random = new Random();
            serial = serial + "-" + random.nextInt(10);
            String mess = "{" +
                    "    \"avatarUrl\":\"avatar-"+random.nextInt(100)+".jpeg\"," +
                    "    \"name\":\"" + random.nextInt(100) + InetAddress.getLocalHost().getHostName() + "\"," +
                    "    \"addressIp\":\"" + InetAddress.getLocalHost().getHostAddress() + "\"," +
                    "    \"message\":\"是的俗套为深入肌肤上的好几个\"," +
                    "    \"soloId\":\"" + serial + "\"," +
                    "    \"messageType\":\"INIT\"" +
                    "}";
            // 发送客户端用户基本信息(用户名和ip地址)
            sendMessage(mess);

            // 开启接收消息的线程
            messageThread = new MessageThread(reader, textArea);
            messageThread.start();

            isConnected = true;// 已经连接上了
            return true;
        } catch (Exception e) {
            textArea.append("【系统消息】与端口号为：" + port + "    IP地址为：" + hostIp + "   的服务器连接失败！" + "\r\n");
            isConnected = false;// 未连接上
            return false;
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    /**
     * 客户端主动关闭连接
     */
    @SuppressWarnings("deprecation")
    public synchronized boolean closeConnection() {
        try {
            sendMessage("CLOSE");// 发送断开连接命令给服务器
            listModel.removeAllElements();
            messageThread.stop();// 停止接受消息线程
            // 释放资源
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
            isConnected = false;
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
            isConnected = true;
            return false;
        }
    }

    // 不断接收消息的线程
    class MessageThread extends Thread {
        private BufferedReader reader;
        private JTextArea textArea;

        // 接收消息线程的构造方法
        public MessageThread(BufferedReader reader, JTextArea textArea) {
            this.reader = reader;
            this.textArea = textArea;
        }

        // 被动的关闭连接
        public synchronized void closeCon() throws Exception {
            // 清空用户列表
            listModel.removeAllElements();
            // 被动的关闭连接释放资源
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
            isConnected = false;// 修改状态为断开
        }

        public void run() {
            String message = "";
            while (true) {

                try {
                    message = reader.readLine();
                    if (message == null) continue;
                    StringTokenizer stringTokenizer = new StringTokenizer(message, "/@");
                    String command = stringTokenizer.nextToken();// 命令
                    System.out.println(command);


                    if (command.equals("CLOSE"))// 服务器已关闭命令
                    {
                        textArea.append("【系统消息】服务器已关闭！\r\n");
                        closeCon();// 被动的关闭连接
                        return;// 结束线程
                    } else if (command.equals("KickCLOSE")) {
                        closeCon();// 被动的关闭连接
                        return;// 结束线程
                    } else if (command.equals("NAME")) {
                        int size = Integer.parseInt(stringTokenizer.nextToken());
                        String username = null;
                        String userIp = null;
                        String thisName = txt_name.getText().trim();
                        for (int i = 0; i < size; i++) {
                            username = stringTokenizer.nextToken();
                            userIp = stringTokenizer.nextToken();
//							User user = new User(username, userIp);

                            if (username.equals(thisName)) {
//								isConnected = false;
                                sendMessage("CLOSE");// 发送断开连接命令给服务器
                                closeCon();// 被动的关闭连接
                                textArea.append("【系统消息】与端口号为：" + txt_port.getText().trim() + "    IP地址为：" + txt_hostIp.getText().trim() + "   的服务器连接失败！" + "\r\n");
                                JOptionPane.showMessageDialog(frame, "该姓名已被占用！", "错误", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

//						closeCon();// 被动的关闭连接
//						JOptionPane.showMessageDialog(frame, "该姓名已被占用！", "错误",
//								JOptionPane.ERROR_MESSAGE);
//						return;// 结束线程
                    } else if (command.equals("ADD")) {// 有用户上线更新在线列表
                        String username = "";
                        String userIp = "";
                        if ((username = stringTokenizer.nextToken()) != null && (userIp = stringTokenizer.nextToken()) != null) {
                            User user = new User(username, userIp);
                            onLineUsers.put(username, user);
                            listModel.addElement(username);

                        }
                    } else if (command.equals("DELETE")) {// 有用户下线更新在线列表
                        String username = stringTokenizer.nextToken();
                        User user = (User) onLineUsers.get(username);
                        onLineUsers.remove(user);
                        listModel.removeElement(username);

                    } else if (command.equals("USERLIST")) {// 加载在线用户列表
                        int size = Integer.parseInt(stringTokenizer.nextToken());
                        String username = null;
                        String userIp = null;
                        for (int i = 0; i < size; i++) {
                            username = stringTokenizer.nextToken();
                            userIp = stringTokenizer.nextToken();
                            User user = new User(username, userIp);
                            onLineUsers.put(username, user);
                            listModel.addElement(username);
                        }

                    } else if (command.equals("MAX")) {// 人数已达上限
                        textArea.append(stringTokenizer.nextToken() + stringTokenizer.nextToken() + "\r\n");
                        closeCon();// 被动的关闭连接
                        JOptionPane.showMessageDialog(frame, "服务器缓冲区已满！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;// 结束线程

                    } else {// 普通消息
                        textArea.append(message + "\r\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

