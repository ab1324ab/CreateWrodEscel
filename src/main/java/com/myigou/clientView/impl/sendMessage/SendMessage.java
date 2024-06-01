package com.myigou.clientView.impl.sendMessage;

import com.myigou.clientView.FunctionInter;
import com.myigou.clientView.impl.sendMessage.model.Customer;
import com.myigou.clientView.impl.sendMessage.model.MessConstant;
import com.myigou.clientView.impl.sendMessage.model.MessageBody;
import com.myigou.clientView.impl.sendMessage.module.AutomaticManual.AutomaticManual;
import com.myigou.clientView.impl.sendMessage.module.Interlocutor;
import com.myigou.clientView.impl.sendMessage.module.Screenshot;
import com.myigou.clientView.impl.sendMessage.service.ThreadServiceTOClient;
import com.myigou.clientView.impl.sendMessage.service.AidedJPanelService;
import com.myigou.clientView.impl.sendMessage.service.MessageDispose;
import com.myigou.clientView.impl.sendMessage.service.ThreadClientTOService;
import com.myigou.clientView.impl.sendMessage.tool.MessageFileTool;
import com.myigou.tool.BusinessTool;
import com.myigou.tool.DateTimeTool;
import com.myigou.tool.ImageIconTool;
import com.myigou.tool.PropertiesTool;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * Created by ab1324ab on 2018/8/6.
 */
public class SendMessage extends Thread implements FunctionInter {
    private JPanel jpanel;
    private JPanel topJPanel;
    private JLabel emailJlabel;
    private JTextField textField1;
    private JPanel leftJpanel;
    private JPanel messageJpanel;
    private JPanel ctrl;
    private JButton sendButton;
    private JButton expressionButton;
    private JButton adduserButton;
    private JButton updateFileButton;
    private JButton messageButton;
    private JTextPane sendJTextPane;
    private JScrollPane jscrollpane;
    public JLabel chatNameJlabel;
    // 消息滚动面板
    public GridBagLayout messageScrollJPaneGBL = new GridBagLayout();
    public GridBagConstraints messageScrollJPaneGBC = new GridBagConstraints();
    public JPanel messageJPane;
    private JScrollPane messageJScroll;
    // 用户滚动面板
    public GridBagLayout interlocutorsGBL = new GridBagLayout();
    public GridBagConstraints interlocutorsGBC = new GridBagConstraints();
    public JPanel interlocutorsCentre;
    private JLabel errorMessJlabel;
    private JButton screenshotButton;
    // 鼠标选中的零时保存对象
    public List<Object> mouseClickedList = new ArrayList<>();

    public Map<String, Customer> messageUser = new HashMap<>();
    public Map<String, Interlocutor> initInterlocutorsMap = new HashMap<>();
    public List<Customer> toBeAddedCustomer = new ArrayList<>();
    private static ServerSocket serverSocket = null;
    public int port = 18353;

    public MessageDispose messageDispose;
    public AidedJPanelService aidedJPanelService;
    private Map<String, String> contentMap = null;
    private StyledDocument sendJTextPaneDoc = null;

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {

        jPanel.setLayout(new BorderLayout());
        jPanel.add(jpanel, BorderLayout.CENTER);
        jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
        messageJScroll.getVerticalScrollBar().setUnitIncrement(16);
        buttonAddMouseListener(jFrame); // 初始话按钮得事件
        messageJPane.setLayout(messageScrollJPaneGBL);
        interlocutorsCentre.setLayout(interlocutorsGBL);
        errorMessJlabel.setOpaque(true);
        messageDispose = new MessageDispose(this, messageScrollJPaneGBL, messageScrollJPaneGBC, messageJPane);
        aidedJPanelService = new AidedJPanelService(interlocutorsCentre, messageJPane, errorMessJlabel);
        contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        sendJTextPaneDoc = sendJTextPane.getStyledDocument();

        SimpleAttributeSet arrSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(arrSet, StyleConstants.ALIGN_LEFT);

        //设置已经输入的文字属性
        sendJTextPaneDoc.setCharacterAttributes(0, sendJTextPaneDoc.getLength(), arrSet, false);
        //设置将输入的属性
        sendJTextPane.setCharacterAttributes(arrSet, false);

        try {
            // 用户列表添加
            List<Map<String, String>> alluserMap = MessageFileTool.getMessageAllList();
            for (Map<String, String> map : alluserMap) {
                Interlocutor inter = new Interlocutor().initialize(this, map);
                inter.logoff();
                inter.avatarShortMessage(map.get("soloId"), map.get("message"), map.get("messageType"), map.get("messageTime"), false);
                initInterlocutorsMap.put(map.get("soloId"), inter);
            }
            if (serverSocket == null) serverSocket = new ServerSocket(port);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jFrame, "连接异常", "提示", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jFrame, "启动服务器异常！", "提示", JOptionPane.WARNING_MESSAGE);
        }
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Set<String> keys = messageUser.keySet();
                for (String key : keys) {
                    PrintWriter writer = messageUser.get(key).getWriter();
                    writer.println("CLOSE");
                    writer.flush();
                }
                String messageFile = contentMap.get("message.setting.messageFile");
                Set<String> soloIds = initInterlocutorsMap.keySet();
                if (MessConstant.MESSAGE_SETTING_MESSAGEFILE_PERPETUAL.equals(messageFile)) System.out.println();
                else if (MessConstant.MESSAGE_SETTING_MESSAGEFILE_ONEWEEK.equals(messageFile)) {
                    for (String key : soloIds) {
                        String messagePath = MessageFileTool.getSaveMessagePath(key);
                        File[] files = new File(messagePath).listFiles();
                        for (File file : files) {
                            BasicFileAttributeView basicview = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);//通过文件的属性来获取文件的创建时间
                            try {
                                FileTime fileTime = basicview.readAttributes().lastModifiedTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                                long diff = DateTimeTool.todayTimeDiff(df.format(new Date(fileTime.toMillis())));
                                if (diff > 7) MessageFileTool.deleteMessagePath(file.getPath());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println(messagePath);
                    }
                } else if (MessConstant.MESSAGE_SETTING_MESSAGEFILE_IMMEDIATE.equals(messageFile)) {
                    for (String key : soloIds) MessageFileTool.deleteMessagePath(MessageFileTool.getSaveMessagePath(key));
                }
            }
        });
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame, Font font) {
        jPanel.setLayout(new BorderLayout());
        emailJlabel.setFont(font);
        jPanel.add(topJPanel, BorderLayout.CENTER);
        return jPanel;
    }

    @Override
    public synchronized void run() {
        // 不停的等待客户端的链接
        while (true) {
            try {
                // 不停的扫描ip
                if (MessConstant.MESSAGE_SETTING_FOUNDTYPE_ACTIVE.equals(contentMap.get("message.setting.foundType"))) connectServer();
                Socket socket = serverSocket.accept();
                ThreadServiceTOClient client = new ThreadServiceTOClient(socket, initInterlocutorsMap, this);
                client.start(); // 开启对此客户端服务的线程
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接其他服务器
     * 屏蔽本地地址*
     */
    public boolean connectServer() {
        InetAddress addr = null;
        List<Socket> list = new ArrayList();
        int it0 = 0, it1 = 0, it2 = 0, it3 = 0;
        try {
            addr = InetAddress.getLocalHost();
            it0 = addr.getAddress()[0] & 0xff;
            it1 = addr.getAddress()[1] & 0xff;
            it2 = addr.getAddress()[2] & 0xff;
            it3 = addr.getAddress()[3] & 0xff;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        List<String> addressIpList = messageUser.values().stream().map(Customer::getAddressIp).collect(Collectors.toList());
        addressIpList.add(it0 + "." + it1 + "." + it2 + "." + it3);
        int cpuNubmer = Runtime.getRuntime().availableProcessors();
        CountDownLatch downLatch = new CountDownLatch(cpuNubmer / 2);
        for (int i = 0; i < 256; i++) {
            String hostIp = it0 + "." + it1 + "." + it2 + "." + i;
            if (addressIpList.contains(hostIp)) continue;
            new Thread(() -> {
                try {
                    SocketAddress socketAddress = new InetSocketAddress(hostIp, port);
                    Socket socket = new Socket();
                    socket.connect(socketAddress, 100);
                    if (socket.isConnected()) list.add(socket);
                } catch (IOException e) {
                } finally {
                    downLatch.countDown();
                }
            }).start();
        }
        try {
            downLatch.await();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.forEach(this::createConnectThread);
        return true;
    }

    /**
     * 创建连接线程*
     *
     * @param socket
     */
    public void createConnectThread(Socket socket) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("avatarUrl", messageDispose.myavatarUrl);
            map.put("name", messageDispose.myname);
            map.put("addressIp", InetAddress.getLocalHost().getHostAddress());
            map.put("message", "");
            map.put("soloId", messageDispose.mysoloId);
            map.put("messageType", "INIT");
            ThreadClientTOService messageThread = new ThreadClientTOService(socket, map, initInterlocutorsMap, this);
            messageThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按钮添加事件*
     */
    public void buttonAddMouseListener(JFrame jFrame) {
        final boolean[] combo = {false};
        sendButton.addActionListener(e -> pressButtonSend());
        sendJTextPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (MessConstant.MESSAGE_SETTING_SHORTCUTKEY_ENTER.equals(contentMap.get("message.setting.shortcutKey"))) {
                    if (e.getKeyChar() == KeyEvent.VK_ENTER && !combo[0]) pressButtonSend();
                    combo[0] = false;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (MessConstant.MESSAGE_SETTING_SHORTCUTKEY_ENTER.equals(contentMap.get("message.setting.shortcutKey"))) {
                    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        combo[0] = true;
                        // 对文本进行追加 回车符号
                        // 获得文本对象
                        Document docs = sendJTextPane.getDocument();
                        try {
                            docs.insertString(docs.getLength(), "\r\n", new SimpleAttributeSet());
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (MessConstant.MESSAGE_SETTING_SHORTCUTKEY_CTRLEnter.equals(contentMap.get("message.setting.shortcutKey")))
                    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) pressButtonSend();
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable cc = sysc.getContents(null);
                    try {
                        if (cc.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                            Image transferData = (Image) cc.getTransferData(DataFlavor.imageFlavor);
                            String filepath = System.getProperty("user.dir") + File.separator + "all tmps";
                            File fileff = new File(filepath);
                            if (!fileff.exists()) fileff.mkdirs();
                            Image img = transferData;
                            int width = img.getWidth(null);
                            int height = img.getHeight(null);
                            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                            Graphics g = bi.getGraphics();
                            g.drawImage(img, 0, 0, width, height, null);
                            g.dispose();
                            File filepathc = new File(filepath + File.separator + System.currentTimeMillis() + ".png");
                            if (!filepathc.exists()) filepathc.createNewFile();
                            ImageIO.write(bi, "png", filepathc);
                            imgFilePaste(transferData, "IMG", filepathc.getName(), filepathc.getPath());
                        } else if (cc.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            List<File> transferData = (List<File>) cc.getTransferData(DataFlavor.javaFileListFlavor);
                            for (int i = 0; i < transferData.size(); i++) {
                                File file = transferData.get(i);
                                if(!file.isFile()){
                                    aidedJPanelService.timerErrorMessJLabel("只能发送文件!", 3000);
                                    sendJTextPane.setText("");
                                    return;
                                }
                                String probeContentType = Files.probeContentType(file.toPath());
                                if (probeContentType != null && probeContentType.contains("image")) {
                                    imgFilePaste(ImageIO.read(file), "IMG", file.getName(), file.getPath());
                                } else {
                                    ImageIcon imageIcon = new ImageIcon(sun.awt.shell.ShellFolder.getShellFolder(file).getIcon(true));
                                    String filename = file.getName() + " " + BusinessTool.fileSizeCalculation(file.length());
                                    imgFilePaste(imageIcon.getImage(), "FILE", filename, file.getPath());
                                }
                            }
                            System.out.println(transferData);
                        }
                    } catch (UnsupportedFlavorException | IOException | BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        expressionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/expression3.png", 30, 30));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/expression2.png", 30, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/expression.png", 30, 30));
            }
        });
        updateFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/updateFile3.png", 30, 30));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/updateFile2.png", 30, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/updateFile.png", 30, 30));
            }
        });
        messageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/message3.png", 30, 30));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/message2.png", 30, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/message.png", 30, 30));
            }
        });
        screenshotButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/screenshot3.png", 30, 30));
                operateScreenshot(jFrame);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/screenshot2.png", 30, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/screenshot.png", 30, 30));
            }
        });
        adduserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/adduser3.png", 30, 30));
                // 组装自定义 对话框
                JDialog dialog = new JDialog(jFrame, "  待添加用户");

                dialog.setResizable(false);
                dialog.setMinimumSize(new Dimension(530, 460));
                dialog.setMaximumSize(new Dimension(530, 460));
                dialog.setPreferredSize(new Dimension(530, 460));
                Container container = dialog.getContentPane();
                container.setLayout(new BorderLayout());
                container.add(new AutomaticManual(SendMessage.this, dialog, toBeAddedCustomer).getJpanel(), BorderLayout.CENTER);
                dialog.setModal(true);

                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/adduser2.png", 30, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getComponent()).setIcon(ImageIconTool.gitImageIcon("/icons/LANMessage/adduser.png", 30, 30));
            }
        });
    }

    int vk_vimgcount = 0;

    /**
     * 发送消息*
     */
    public void pressButtonSend() {
        // 选择聊天对象
        String soloId = chatNameJlabel.getName();
        Customer messUser = messageUser.get(soloId);
        if (initInterlocutorsMap.size() == 0 && messageUser.size() == 0) {
            aidedJPanelService.timerErrorMessJLabel("没有聊天对象可发送！", 3000);
            return;
        } else if (messUser == null) {
            aidedJPanelService.timerErrorMessJLabel("对方今日没有上线！", 3000);
            return;
        }

        int elementCount = sendJTextPaneDoc.getRootElements()[0].getElement(0).getElementCount();
        List<Map<String, String>> sendListObject = new ArrayList<>();
        int imgcount = 0, filecount = 0;
        for (int i = 0; i < elementCount; i++) {
            try {
                AttributeSet attributes = sendJTextPaneDoc.getRootElements()[0].getElement(0).getElement(i).getAttributes();
                Map<String, String> messobj = new HashMap<>();
                int p0 = ((AbstractDocument.LeafElement) attributes).getStartOffset();
                int p1 = ((AbstractDocument.LeafElement) attributes).getEndOffset();
//                System.out.println("大小 p1:" + p1 + " - p0:" + p0 + " = " + (p1 - p0));
                String message = sendJTextPane.getText(p0, p1 - p0);
//                System.out.println("message = " + message);
                Component component = StyleConstants.getComponent(sendJTextPaneDoc.getRootElements()[0].getElement(0).getElement(i).getAttributes());
//                System.out.println("component = " + component);
                if (StringUtils.isNotBlank(message)) {
                    messobj.put("messageType", "COM");
                    messobj.put("message", message);
                    sendListObject.add(messobj);
                } else if (component != null) {
                    JLabel componentJlabel = (JLabel) component;
                    String[] tname = componentJlabel.getName().split("---");
                    if ("IMG".equals(tname[0])) {
                        messobj.put("messageType", "IMG");
                        messobj.put("message", tname[1]);
                        imgcount++;
                    } else if ("FILE".equals(tname[0])) {
                        messobj.put("messageType", "FILE");
                        messobj.put("message", new File(tname[1]).getPath());
                        filecount++;
                    }
                    sendListObject.add(messobj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (imgcount > 3) {
            aidedJPanelService.timerErrorMessJLabel("每次仅可发送图片数量3", 3000);
            return;
        }
        if (filecount > 1) {
            aidedJPanelService.timerErrorMessJLabel("每次仅可发送文件数量1", 3000);
            return;
        }
        if (sendListObject.size() == 0) {
            aidedJPanelService.timerErrorMessJLabel("不能发送空白信息！", 3000);
            return;
        }
        sendJTextPane.setText("");
        try {
            for (int i = 0; i < sendListObject.size(); i++) {
                Map<String, String> messobj = sendListObject.get(i);
                MessageBody messageBody = new MessageBody();
                messageBody.setMessage(messobj.get("message"));
                messageBody.setMessageObject(soloId);
                messageBody.setMessageId(System.currentTimeMillis() + "");
                messageBody.setMessageType(messobj.get("messageType"));
                messageBody.setMessageStatus("1");// 消息状态 1:正常 2:删除 3:撤回
                messageBody.setMessageTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                messageDispose.sendTransmitMessage(messageBody, messUser.getSocket());
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        // 结束位
        messageDispose.endLineJScroll(messageJPane.getComponentCount() + 1);
    }

    /**
     * 截图操作*
     *
     * @param jFrame
     */
    public static void operateScreenshot(JFrame jFrame) {
        jFrame.setVisible(false);
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        JFrame isscreen = new JFrame();
        Screenshot screenTest = new Screenshot(isscreen, jFrame);
        isscreen.getContentPane().add(screenTest.getBeijing(), BorderLayout.CENTER);
        isscreen.setUndecorated(true);
        isscreen.setSize(Screenshot.width, Screenshot.height);
        isscreen.setVisible(true);
    }

    /**
     * 粘贴图片文件*
     */
    public void imgFilePaste(Image transferData, String type, String filename, String filePath) throws BadLocationException {
        int fenmu = 0;
        int height = transferData.getHeight(null);
        int width = transferData.getWidth(null);
        while (height >= 150 || width >= 150) {
            fenmu++;
            height = transferData.getHeight(null) / fenmu;
            width = transferData.getWidth(null) / fenmu;
        }
        ImageIcon imageIcon = new ImageIcon(transferData.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        JLabel jLabel = new JLabel();
        jLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        jLabel.setBackground(Color.WHITE);
        jLabel.setIcon(imageIcon);
        jLabel.setBorder(BorderFactory.createLineBorder(new Color(205, 205, 205), 1));
        jLabel.setOpaque(true);
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
//              if (e.getClickCount() == 2 )
            }
        });
        sendJTextPane.insertComponent(jLabel);
        if (type.equals("FILE")) {
            jLabel.setPreferredSize(new Dimension(250, 50));
            jLabel.setSize(250, 50);
            sendJTextPaneDoc.insertString(sendJTextPaneDoc.getLength(), " ", new SimpleAttributeSet());
            jLabel.setText(filename);
            jLabel.setName(type + "---" + filePath);
        }
        if (type.equals("IMG")) {
            jLabel.setName(type + "---" + filePath);
        }
        vk_vimgcount++;
        if (vk_vimgcount % 5 == 0) sendJTextPaneDoc.insertString(sendJTextPaneDoc.getLength(), " ", new SimpleAttributeSet());
    }
}