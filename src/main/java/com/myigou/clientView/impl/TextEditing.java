package com.myigou.clientView.impl;

import com.myigou.clientService.model.TextEditingFileType;
import com.myigou.clientView.FunctionInter;
import com.myigou.tool.PropertiesTool;
import org.apache.commons.lang.StringUtils;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ab1324ab
 * Created by ab1324ab on 2017/6/29.
 */
public class TextEditing implements FunctionInter {
    // 选择按钮
    private JButton select = null;
    // 文件地址
    private JTextField fileAddress = null;
    // 创建文件地址
    private JTextField createFileAddress = null;
    // 抓取文件按钮
    private JButton extracting = null;
    // 创建文件按钮
    private JButton createExtracting = null;
    // 搜索按钮
    private JButton selectExtracting = null;
    // 批量文件搜索操作start
    private JButton batchFileButton = null;
    private JTextField batchFileAddress = null;
    private JTextField findTargetText = null;
    private JTextField ToReplacedText = null;
    private JDialog d = null;
    private JLabel prompt = null;
    private JCheckBox matchesCase = null;
    private ButtonGroup group = null;
    // 批量文件搜索按钮end
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 文本或图片显示
    private JLabel jLabel = null;
    // 总文件个数
    private int number = 0;
    // 预编译正则表达式
    private static Pattern NUMBER_PATTERN = Pattern.compile("[a-zA-Z]:(\\\\|/)");
    // 文字大小
    private Font fontTxT = new Font("微软雅黑", Font.PLAIN, 14);
    int[] position = {5, 5, 5, 5};
    // 组件数组
    List<Object> assemblyList = new ArrayList<Object>();

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        JPanel content = new JPanel(gridBagLayout);

        this.fileConcentrated(content);
        this.createFolders(content);
        this.createFolders1(content);
        this.createFolders2(content);
        this.registerListener(content, jFrame);
        jPanel.add(content, BorderLayout.NORTH);
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border, "", TitledBorder.LEFT, TitledBorder.CENTER, new Font("楷体", Font.PLAIN, 14), Color.BLACK);
        jPanel.setBorder(border);
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame, Font font) {
        JLabel title = new JLabel("文件操作", JLabel.CENTER);
        title.setFont(font);
        jPanel.add(title);
        return jPanel;
    }

    private void fileConcentrated(JPanel jPanel) {

        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jLabel = new JLabel("一、文件归集 集中放置桌面文件夹(Output files)中");
        jLabel.setFont(fontTxT);
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(8, 20, 8, 8);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        JLabel address = new JLabel("文件地址:");
        gridBagConstraints.insets = new Insets(8, 20, 8, 8);
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        address.setFont(fontTxT);
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        fileAddress = new JTextField();
        fileAddress.setFont(fontTxT);
        fileAddress.setColumns(60);
        gridBagConstraints.insets = new Insets(8, 8, 8, 8);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(fileAddress, gridBagConstraints);
        jPanel.add(fileAddress);

        select = new JButton("选择目录");
        select.setFont(fontTxT);
        select.setFocusPainted(false);
        gridBagConstraints.insets = new Insets(8, 8, 8, 8);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(select, gridBagConstraints);
        jPanel.add(select);

        extracting = new JButton("抓取文件");
        extracting.setFont(fontTxT);
        extracting.setFocusPainted(false);
        gridBagConstraints.insets = new Insets(8, 8, 8, 20);
        gridBagLayout.setConstraints(extracting, gridBagConstraints);
        jPanel.add(extracting);

    }

    public void createFolders(JPanel jPanel) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel jLabel = new JLabel("二、创建目录文件夹");
        jLabel.setFont(fontTxT);
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(8, 20, 8, 8);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        JLabel address = new JLabel("创建地址:");
        address.setFont(fontTxT);
        gridBagConstraints.insets = new Insets(8, 20, 8, 8);
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        createFileAddress = new JTextField();
        createFileAddress.setFont(fontTxT);
        createFileAddress.setColumns(60);
        gridBagConstraints.insets = new Insets(8, 8, 8, 8);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(createFileAddress, gridBagConstraints);
        jPanel.add(createFileAddress);

        createExtracting = new JButton("创建文件夹");
        createExtracting.setFont(fontTxT);
        createExtracting.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(createExtracting, gridBagConstraints);
        jPanel.add(createExtracting);

    }

    private void createFolders1(JPanel jPanel) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel jLabel = new JLabel("三、搜索文件（按条件搜索文件，只搜索文件；不搜索文件内容。）");
        jLabel.setFont(fontTxT);
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new Insets(8, 20, 8, 8);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        JLabel address = new JLabel("搜索地址:");
        address.setFont(fontTxT);
        gridBagConstraints.insets = new Insets(8, 20, 8, 8);
        gridBagConstraints.gridy = 5;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        JTextField fileAddress = new JTextField();
        fileAddress.setFont(fontTxT);
        createFileAddress.setColumns(60);
        gridBagConstraints.insets = new Insets(8, 8, 8, 8);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(fileAddress, gridBagConstraints);
        jPanel.add(fileAddress);
        assemblyList.add(fileAddress);

        selectExtracting = new JButton("搜索");
        selectExtracting.setFont(fontTxT);
        selectExtracting.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(selectExtracting, gridBagConstraints);
        jPanel.add(selectExtracting);

        JPanel select = new JPanel();
        select.setLayout(new GridLayout(1, 4));
        //select.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
        gridBagConstraints.insets = new Insets(8, 90, 8, 8);
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        JCheckBox searchAll = new JCheckBox("搜索所有文件名");
        searchAll.setFont(fontTxT);
        assemblyList.add(searchAll);
        JCheckBox searchNumber = new JCheckBox("搜索包含数字文件名");
        searchNumber.setFont(fontTxT);
        assemblyList.add(searchNumber);
        JCheckBox searchChinese = new JCheckBox("搜索包含中文文件名");
        searchChinese.setFont(fontTxT);
        assemblyList.add(searchChinese);
        JCheckBox searchEnglish = new JCheckBox("搜索包含英文文件名");
        searchEnglish.setFont(fontTxT);
        assemblyList.add(searchEnglish);
        select.add(searchAll);
        select.add(searchNumber);
        select.add(searchChinese);
        select.add(searchEnglish);
        gridBagLayout.setConstraints(select, gridBagConstraints);
        jPanel.add(select);

    }

    private void createFolders2(JPanel jPanel) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel jLabel = new JLabel("四、批量文件重命名");
        jLabel.setFont(fontTxT);
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new Insets(8, 20, 8, 8);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        JLabel address = new JLabel("文件地址:");
        address.setFont(fontTxT);
        gridBagConstraints.insets = new Insets(8, 20, 8, 8);
        gridBagConstraints.gridy = 8;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        batchFileAddress = new JTextField();
        batchFileAddress.setFont(fontTxT);
        createFileAddress.setColumns(60);
        gridBagConstraints.insets = new Insets(8, 8, 8, 8);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(batchFileAddress, gridBagConstraints);
        jPanel.add(batchFileAddress);

        batchFileButton = new JButton("选择目录");
        batchFileButton.setFont(fontTxT);
        batchFileButton.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(batchFileButton, gridBagConstraints);
        jPanel.add(batchFileButton);

    }

    /**
     * 按钮事件
     *
     * @param jFrame
     * @param jPanel
     */
    private void registerListener(final JPanel jPanel, final JFrame jFrame) {
        // 获取文件路径按钮
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDir(fileAddress, jFrame);
            }
        });
        // 抓取文件按钮
        extracting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isEmpty(fileAddress.getText())) {
                    JOptionPane.showMessageDialog(jFrame, "请先选择或输入路径", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (!new File(fileAddress.getText()).exists()) {
                    JOptionPane.showMessageDialog(jFrame, "目录不存在！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                List<String> fileNumber = getCountFile(fileAddress.getText(), new ArrayList<String>());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        extracting.setEnabled(false);
                        number = 0;
                        FileSystemView fsv = FileSystemView.getFileSystemView();
                        File com = fsv.getHomeDirectory();
                        File createDirectory = new File(com.getPath() + "\\Output files");
                        if (!createDirectory.exists()) {
                            createDirectory.mkdir();
                        }
                        byte[] barrel = new byte[1024];
                        File file = new File(fileAddress.getText());
                        File[] files = file.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            number = readWriteFile(createDirectory, files[i], barrel, number, jPanel, jFrame, fileNumber.size());
                        }
                        jLabel.setText("<html>一、文件归集 集中放置桌面文件夹(Output files)中 <font  style=\"color:red\"> 搜索结束共: " + number + " 个文件，不包括大小为0kb文件和文件夹</font ></html>");
                        extracting.setEnabled(true);
                    }
                };
                Thread t = new Thread(runnable);
                t.start();
            }
        });
        // 创建文件目录按钮
        createExtracting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pattern pattern = NUMBER_PATTERN;
                Matcher matcher = pattern.matcher(createFileAddress.getText());
                if (!matcher.find()) {
                    JOptionPane.showMessageDialog(jFrame, "请输入正确创建路径", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                System.out.println(createFileAddress.getText());
                String[] urlArr = null;
                if (createFileAddress.getText().split("\\\\").length > 1) {
                    urlArr = createFileAddress.getText().split("\\\\");
                } else if (createFileAddress.getText().split("/").length > 1) {
                    urlArr = createFileAddress.getText().split("/");
                } else {
                    JOptionPane.showMessageDialog(jFrame, "目录创建失败！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                for (int i = 1; i < urlArr.length; i++) {
                    File file = new File(urlArr[0] += "\\" + urlArr[i]);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
                int res = JOptionPane.showConfirmDialog(jFrame, "是否打开创建目录？", "创建成功", JOptionPane.YES_NO_OPTION);
                try {
                    if (res == JOptionPane.YES_OPTION) {
                        String vca = "cmd /c start \" \" \"" + createFileAddress.getText() + "\"";
                        Runtime.getRuntime().exec(vca);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(jFrame, "目录打开失败！", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        // 搜索文件
        selectExtracting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String term = "[";
                for (int i = 1; i < assemblyList.size(); i++) {
                    JCheckBox jCheckBox = (JCheckBox) assemblyList.get(i);
                    if ("搜索所有文件名".equals(jCheckBox.getText()) && jCheckBox.isSelected()) {
                        term = ".";
                        break;
                    } else {
                        if ("搜索包含数字文件名".equals(jCheckBox.getText()) && jCheckBox.isSelected()) {
                            term += "0-9";
                        }
                        if ("搜索包含英文文件名".equals(jCheckBox.getText()) && jCheckBox.isSelected()) {
                            term += "a-zA-Z";
                        }
                        if ("搜索包含中文文件名".equals(jCheckBox.getText()) && jCheckBox.isSelected()) {
                            term += "\u4E00-\u9FA5";
                        }
                    }
                }
                if ("[".equals(term)) {
                    JOptionPane.showMessageDialog(jFrame, "请选择搜索方式", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (!".".equals(term)) {
                    term += "]";
                }
                // 获取输入的路径
                String selectUrl = ((JTextField) assemblyList.get(0)).getText();
                if (StringUtils.isEmpty(selectUrl)) {
                    JFileChooser chooser = new JFileChooser();
                    // 设置只能选择目录
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int returnVal = chooser.showOpenDialog(jFrame);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        selectUrl = chooser.getSelectedFile().getPath();
                    }
                    if (StringUtils.isEmpty(selectUrl)) {
                        return;
                    }
                    ((JTextField) assemblyList.get(0)).setText(selectUrl);
                }
                Pattern pattern = NUMBER_PATTERN;
                Matcher matcher = pattern.matcher(selectUrl);
                if (!matcher.find()) {
                    JOptionPane.showMessageDialog(jFrame, "请输入正确搜索路径", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 动态正则 筛选文件
                Pattern compile = Pattern.compile(term);
                File selectFile = new File(selectUrl);
                List<String> pathList = new ArrayList<String>();
                final List<String>[] pathTemp = new List[]{new ArrayList<String>()};
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pathTemp[0] = getCountFile(selectFile.getAbsolutePath(), new ArrayList<>());
                    }
                });
                thread.run();
                for (int i = 0; i < pathTemp[0].size(); i++) {
                    String[] pStr = null;
                    if (pathTemp[0].get(i).split("//").length > 1) {
                        pStr = pathTemp[0].get(i).split("//");
                    } else if (pathTemp[0].get(i).split("\\\\").length > 1) {
                        pStr = pathTemp[0].get(i).split("\\\\");
                    }
                    String fileName = pStr[pStr.length - 1];
                    Matcher matcher1 = compile.matcher(fileName);
                    if (matcher1.find()) {
                        pathList.add(pathTemp[0].get(i));
                    }
                }
                // 组装自定义 对话框
                JDialog d = new JDialog(jFrame, selectUrl.toUpperCase());
                d.setSize(800, 500);
                Container container = d.getContentPane();
                container.setLayout(new BorderLayout());
                // 文件路径展示面板
                JPanel centre = new JPanel();
                GridBagLayout gridBagLayout = new GridBagLayout();
                centre.setLayout(gridBagLayout);
                GridBagConstraints gridBagConstraints = new GridBagConstraints();
                for (int i = 0; i < pathList.size(); i++) {
                    JLabel jLPath = new JLabel(pathList.get(i).replace(selectUrl, ""));
                    jLPath.setName(pathList.get(i));
                    jLPath.setFont(new Font("仿宋", Font.PLAIN, 20));
                    jLPath.setBorder(BorderFactory.createLineBorder(Color.white, 1));
                    jLPath.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                int ComponentCount = centre.getComponentCount();
                                for (int is = 0; is < ComponentCount; is++) {
                                    ((JLabel) centre.getComponent(is)).setBackground(Color.lightGray);
                                }
                                jLPath.setOpaque(true);
                                jLPath.setForeground(Color.BLACK);
                                jLPath.setBackground(Color.orange);
                                JLabel evnLab = (JLabel) e.getSource();
                                String vca = "explorer /select, " + evnLab.getName();
                                Runtime.getRuntime().exec(vca);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            jLPath.setOpaque(true);
                            jLPath.setForeground(Color.WHITE);
                            jLPath.setBackground(Color.BLUE);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            jLPath.setOpaque(false);
                            jLPath.setForeground(Color.BLACK);
                        }
                    });
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = i;
                    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
                    // 当窗口放大时，长度变
                    gridBagConstraints.weightx = 1;
                    gridBagConstraints.insets = new Insets(0, 10, 0, 10);
                    gridBagLayout.setConstraints(jLPath, gridBagConstraints);
                    centre.add(jLPath);
                }
                JPanel jPanel1 = new JPanel(new BorderLayout());
                jPanel1.add(centre, BorderLayout.NORTH);
                JScrollPane jScrollPane = new JScrollPane(jPanel1);
                // 顶部控制面板
                JPanel top = new JPanel(new FlowLayout());
                top.setBorder(BorderFactory.createLineBorder(new Color(149, 149, 149), 1));
                top.add(new JLabel("条件:", JLabel.CENTER));
                JTextField jTextField = new JTextField();
                jTextField.setFont(new Font("黑体", Font.PLAIN, 15));
                jTextField.setColumns(30);
                top.add(jTextField);
                JButton screenJButton = new JButton("筛选");
                top.add(screenJButton);
                String finalConvertUrl = selectUrl;
                // 输入框回车 调用筛选按钮
                jTextField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        super.keyTyped(e);
                        if (10 == e.getKeyCode()) {
                            screenJButton.doClick();
                        }
                    }
                });
                // 显示主面板
                JPanel mView = new JPanel();
                mView.setLayout(new BorderLayout());
                mView.add(top, BorderLayout.NORTH);
                mView.add(jScrollPane, BorderLayout.CENTER);
                container.add(mView);
                JTextField south = new JTextField("共 " + pathList.size() + " 个项目");
                south.setEnabled(false);
                south.setHorizontalAlignment(JTextField.RIGHT);
                container.add(south, BorderLayout.SOUTH);
                screenJButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String jText = jTextField.getText().toLowerCase();
                        int countFile = 0;
                        for (int i = 0; i < pathList.size(); i++) {
                            JLabel jLabel = (JLabel) centre.getComponent(i);
                            String sUrl = jLabel.getText().replace(finalConvertUrl, "").toLowerCase();
                            if (sUrl.indexOf(jText) != -1) {
                                countFile++;
                                jLabel.setVisible(true);
                            } else {
                                jLabel.setVisible(false);
                            }
                        }
                        south.setText("共 " + countFile + " 个项目");
                    }
                });
                d.setModal(true);

                d.setMinimumSize(new Dimension(700, 400));
                d.setLocationRelativeTo(null);
                d.setVisible(true);
            }
        });
        // 批量搜索文件
        batchFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDir(batchFileAddress, jFrame);
                if (StringUtils.isEmpty(batchFileAddress.getText())) return;
                // 组装自定义 对话框
                d = new JDialog(jFrame, batchFileAddress.getText().toUpperCase());
                d.setResizable(true);
                d.setSize(800, 500);
                Container container = d.getContentPane();
                container.setLayout(new BorderLayout());

                File selectFile = new File(batchFileAddress.getText());
                final List<String>[] pathTemp = new List[]{new ArrayList<String>()};
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pathTemp[0] = getCountFile(selectFile.getAbsolutePath(), new ArrayList<>());
                    }
                });
                thread.run();
                List<TextEditingFileType> pathList = new ArrayList<>();
                for (String filename : pathTemp[0]) {
                    File file = new File(filename);
                    try {
                        pathList.add(new TextEditingFileType(file.getName(), file.toPath(), Files.probeContentType(file.toPath()), file.getName(), file.length()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                JPanel centre = new JPanel();
                GridBagLayout gridBagLayout = new GridBagLayout();
                centre.setLayout(gridBagLayout);
                GridBagConstraints gridBagConstraints = new GridBagConstraints();
                JTextPane fileNewNameLabel = new JTextPane();
                for (int i = 0; i < pathList.size(); i++) {
                    gridBagConstraints.gridy = i;
                    gridBagConstraints.fill = GridBagConstraints.BOTH;
                    gridBagConstraints.weightx = 1;
                    gridBagConstraints.insets = new Insets(0, 1, 0, 1);
                    if (i == 0) {
                        JLabel fileNameLabel = new JLabel("文件名称", JLabel.CENTER);
                        fileNameLabel.setForeground(new Color(110, 110, 110));
                        gridBagConstraints.gridx = 0;
                        gridBagLayout.setConstraints(fileNameLabel, gridBagConstraints);
                        centre.add(fileNameLabel);

                        fileNewNameLabel.setBackground(new Color(240, 240, 240));
                        fileNewNameLabel.setText("新文件名称");
                        StyledDocument doc = fileNewNameLabel.getStyledDocument();
                        SimpleAttributeSet centerP = new SimpleAttributeSet();
                        StyleConstants.setAlignment(centerP, StyleConstants.ALIGN_CENTER);
                        SimpleAttributeSet centerC = new SimpleAttributeSet();
                        StyleConstants.setForeground(centerC, Color.red);
                        doc.setCharacterAttributes(0, doc.getLength(), centerC, false);
                        doc.setParagraphAttributes(0, doc.getLength(), centerP, false);
                        fileNewNameLabel.setEnabled(false);
                        gridBagConstraints.insets = new Insets(0, 1, 5, 1);
                        gridBagConstraints.gridx = 1;
                        gridBagLayout.setConstraints(fileNewNameLabel, gridBagConstraints);
                        centre.add(fileNewNameLabel);

                        gridBagConstraints.insets = new Insets(0, 1, 0, 1);
                        JLabel fileTypeLabel = new JLabel("文件类型", JLabel.CENTER);
                        fileTypeLabel.setForeground(new Color(110, 110, 110));
                        gridBagConstraints.gridx = 2;
                        gridBagLayout.setConstraints(fileTypeLabel, gridBagConstraints);
                        centre.add(fileTypeLabel);
                        JLabel fileSizeLabel = new JLabel("文件大小(KB)", JLabel.CENTER);
                        fileSizeLabel.setForeground(new Color(110, 110, 110));
                        gridBagConstraints.gridx = 3;
                        gridBagLayout.setConstraints(fileSizeLabel, gridBagConstraints);
                        centre.add(fileSizeLabel);
                    }
                    gridBagConstraints.ipady = 0;
                    JTextField fileName = new JTextField();
                    fileName.setEnabled(false);
                    fileName.setText(pathList.get(i).getFileName());
                    gridBagConstraints.gridy = i + 1;
                    gridBagConstraints.gridx = 0;
                    gridBagLayout.setConstraints(fileName, gridBagConstraints);
                    centre.add(fileName);
                    pathList.get(i).setFileNameField(fileName);

                    JTextPane fileNewName = new JTextPane();
                    fileNewName.setBorder(BorderFactory.createLineBorder(new Color(149, 149, 149), 1));
                    gridBagConstraints.insets = new Insets(0, 0, 0, 1);
                    gridBagConstraints.gridx = 1;
                    fileNewName.setText(pathList.get(i).getNewFileName());
                    gridBagLayout.setConstraints(fileNewName, gridBagConstraints);
                    centre.add(fileNewName);
                    pathList.get(i).setNewFileNameField(fileNewName);

                    JTextField fileType = new JTextField();
                    fileType.setEnabled(false);
                    gridBagConstraints.gridx = 2;
                    fileType.setText(pathList.get(i).getFileType());
                    gridBagLayout.setConstraints(fileType, gridBagConstraints);
                    centre.add(fileType);
                    pathList.get(i).setFileTypeField(fileType);

                    JTextField fileSize = new JTextField();
                    fileSize.setEnabled(false);
                    gridBagConstraints.gridx = 3;
                    fileSize.setText(pathList.get(i).getFileSize() + "");
                    gridBagLayout.setConstraints(fileSize, gridBagConstraints);
                    centre.add(fileSize);
                    pathList.get(i).setFileSizeField(fileSize);
                }
                // 设置光标到首行
                fileNewNameLabel.setCaretPosition(0);
                JPanel jPanel1 = new JPanel(new BorderLayout());
                jPanel1.add(centre, BorderLayout.NORTH);
                JScrollPane jScrollPane = new JScrollPane(jPanel1);
                jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                // 顶部控制面板
                GridBagLayout topGBL = new GridBagLayout();
                GridBagConstraints topGBC = new GridBagConstraints();
                JPanel top = new JPanel(topGBL);
                topGBC.gridx = 0;
                topGBC.gridy = 0;
                topGBC.fill = GridBagConstraints.BOTH;
                topGBC.weightx = 0;
                topGBC.insets = new Insets(10, 10, 5, 10);
                JLabel topPrompt = new JLabel("查找目标:", JLabel.RIGHT);
                topGBL.setConstraints(topPrompt, topGBC);
                top.add(topPrompt);

                findTargetText = new JTextField();
                findTargetText.setFont(new Font("黑体", Font.PLAIN, 15));
                findTargetText.setColumns(30);
                topGBC.insets = new Insets(10, 0, 5, 10);
                topGBC.gridx = 1;
                topGBC.weightx = 1;
                topGBL.setConstraints(findTargetText, topGBC);
                top.add(findTargetText);

                JButton screenJButton = new JButton("查找全部");
                topGBC.gridx = 2;
                topGBC.weightx = 0;
                topGBL.setConstraints(screenJButton, topGBC);
                top.add(screenJButton);
                setTopPanelButtonActionListener(screenJButton, "screen", pathList);

                JButton executeJButton = new JButton("执行文件修改(O)");
                topGBC.gridx = 3;
                topGBC.gridheight = 2;
                topGBC.weightx = 0;
                topGBL.setConstraints(executeJButton, topGBC);
                top.add(executeJButton);
                setTopPanelButtonActionListener(executeJButton, "execute", pathList);

                topGBC.gridheight = 1;
                JLabel ToReplaced = new JLabel("替换为:", JLabel.RIGHT);
                topGBC.insets = new Insets(5, 0, 5, 10);
                topGBC.gridy = 1;
                topGBC.gridx = 0;
                topGBC.weightx = 0;
                topGBL.setConstraints(ToReplaced, topGBC);
                top.add(ToReplaced);

                ToReplacedText = new JTextField();
                ToReplacedText.setFont(new Font("黑体", Font.PLAIN, 15));
                ToReplacedText.setColumns(30);
                topGBC.gridx = 1;
                topGBL.setConstraints(ToReplacedText, topGBC);
                top.add(ToReplacedText);

                JButton replacementJButton = new JButton("全部替换(A)");
                topGBC.gridx = 2;
                topGBC.weightx = 0;
                topGBL.setConstraints(replacementJButton, topGBC);
                top.add(replacementJButton);
                setTopPanelButtonActionListener(replacementJButton, "replacement", pathList);
                //
                JLabel findPatterns = new JLabel("查找模式:", JLabel.RIGHT);
                topGBC.gridy = 2;
                topGBC.gridx = 0;
                topGBC.weightx = 0;
                topGBL.setConstraints(findPatterns, topGBC);
                top.add(findPatterns);

                group = new ButtonGroup();
                JRadioButton c1 = new JRadioButton("普通字符串(N)", true);//只传了两个参数
                JRadioButton c2 = new JRadioButton("正则表达式(G)");
                group.add(c1);
                group.add(c2);
                topGBC.gridx = 1;
                ActionListener radioListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("普通字符串(N)")) matchesCase.setEnabled(true);
                        else if (e.getActionCommand().equals("正则表达式(G)")) {
                            matchesCase.setEnabled(false);
                            matchesCase.setSelected(false);
                        }
                    }
                };
                c1.addActionListener(radioListener);
                c2.addActionListener(radioListener);
                JPanel radioPanel = new JPanel(new BorderLayout());
                radioPanel.add(c1, BorderLayout.WEST);
                radioPanel.add(c2, BorderLayout.CENTER);
                topGBL.setConstraints(radioPanel, topGBC);
                top.add(radioPanel);
                matchesCase = new JCheckBox("匹配大小写(C)");
                topGBC.gridx = 2;
                topGBL.setConstraints(matchesCase, topGBC);
                top.add(matchesCase);

                JButton quashJButton = new JButton("撤销(Ctrl+Z)");
                topGBC.gridx = 3;
                topGBC.weightx = 0;
                quashJButton.setEnabled(false);
                topGBL.setConstraints(quashJButton, topGBC);
                top.add(quashJButton);
                setTopPanelButtonActionListener(quashJButton, "quash", pathList);

                GridBagLayout topTableTitleGBL = new GridBagLayout();
                GridBagConstraints topTableTitleGBC = new GridBagConstraints();
                JPanel tableTitleView = new JPanel(topTableTitleGBL);
                tableTitleView.setBorder(BorderFactory.createLineBorder(new Color(149, 149, 149), 1));
                JLabel fileNameLabel = new JLabel("文件名称");
                topTableTitleGBC.insets = new Insets(0, 50, 0, 50);
                topTableTitleGBC.gridy = 0;
                topTableTitleGBC.gridx = 0;
                topTableTitleGBC.gridwidth = 1;
                topTableTitleGBC.ipady = 5;
                topTableTitleGBC.fill = GridBagConstraints.CENTER;
                topTableTitleGBL.setConstraints(fileNameLabel, topTableTitleGBC);
                tableTitleView.add(fileNameLabel);
                // 显示主面板
                JPanel mView = new JPanel();
                mView.setLayout(new BorderLayout());
                mView.add(top, BorderLayout.NORTH);
                mView.add(jScrollPane, BorderLayout.CENTER);
                container.add(mView);

                JPanel bottomJPanel = new JPanel(new BorderLayout());
                prompt = new JLabel("");
                prompt.setPreferredSize(new Dimension(600, 25));
                prompt.setForeground(Color.red);
                container.add(prompt, BorderLayout.WEST);
                bottomJPanel.add(prompt, BorderLayout.WEST);
                JLabel south = new JLabel("共 " + pathList.size() + " 个项目  ", JLabel.RIGHT);
                south.setPreferredSize(new Dimension(200, 25));

                bottomJPanel.add(south, BorderLayout.EAST);
                container.add(bottomJPanel, BorderLayout.SOUTH);
                d.setModal(true);

                d.setMinimumSize(new Dimension(700, 400));
                d.setLocationRelativeTo(null);
                d.setVisible(true);
            }
        });

    }

    /**
     * 读取目录下所有文件
     *
     * @param fileDirectory
     * @param file
     * @param barrel
     */
    public int readWriteFile(File fileDirectory, File file, byte[] barrel, int number, JPanel jPanel, JFrame jFrame, int fileNumber) {
        try {
            FileInputStream inputFile = null;
            FileOutputStream outFile = null;
            if (file.isFile() && file.length() > 0) {
                inputFile = new FileInputStream(file);
                File fileTemp = new File(fileDirectory.getPath() + "\\" + file.getName());
                fileTemp.createNewFile();
                outFile = new FileOutputStream(fileTemp);
                if (file.length() > 0) {
                    while (inputFile.read(barrel) != -1) {
                        outFile.write(barrel, 0, barrel.length);
                    }
                }
                number++;
                inputFile.close();
                outFile.close();
            } else if (file.isDirectory()) {
                for (int i = 0; i < file.listFiles().length; i++) {
                    number = readWriteFile(fileDirectory, file.listFiles()[i], barrel, number, jPanel, jFrame, fileNumber);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        jLabel.setText("<html>一、文件归集 集中放置桌面文件夹(Output files)中 <font  style=\"color:red\"> 当前已获取: " + number + " 个,共: " + fileNumber + " 个;</font ></html>");
        return number;
    }

    /**
     * 获取目录下有多少文件
     *
     * @param FilePath
     * @param count
     * @return
     */
    public List<String> getCountFile(String FilePath, List<String> count) {
        File file = new File(FilePath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                count.add(f.getAbsolutePath());
            } else if (f.isDirectory()) {
                count = getCountFile(f.getPath(), count);
            }
        }
        return count;
    }

    /**
     * 文件夹选择
     *
     * @param address
     * @param jFrame
     */
    public void selectDir(JTextField address, JFrame jFrame) {
        String filePath = address.getText();
        JFileChooser chooser = new JFileChooser(filePath);
        // 设置只能选择目录
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String directoryURL = "";
        int returnVal = chooser.showOpenDialog(jFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            directoryURL = chooser.getSelectedFile().getPath();
        }
        if (directoryURL != null) {
            address.setText(directoryURL);
        }
    }

    /**
     * 设置控制面板按钮事件
     *
     * @param button
     */
    public void setTopPanelButtonActionListener(JButton button, String typeButton, List<TextEditingFileType> pathList) {
        if (typeButton.equals("screen")) { // 查找
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String findStr = findTargetText.getText();
                    if (StringUtils.isNotEmpty(findStr)) {
                        boolean check = false;
                        String findPatternStr = "";
                        Enumeration<AbstractButton> elements = group.getElements();
                        while (elements.hasMoreElements()) {
                            AbstractButton btn = elements.nextElement();
                            if (btn.isSelected()) {
                                findPatternStr = btn.getText();
                                break;
                            }
                        }
                        for (TextEditingFileType fileType : pathList) {
                            String text = fileType.getNewFileNameField().getText();
                            MutableAttributeSet myAttributeSet = fileType.getNewFileNameField().getInputAttributes();
                            StyleConstants.setBackground(myAttributeSet, Color.WHITE);
                            StyledDocument doc = fileType.getNewFileNameField().getStyledDocument();
                            doc.setCharacterAttributes(0, text.length(), myAttributeSet, true);
                            if ("普通字符串(N)".equals(findPatternStr)) {
                                int findStrIndex = text.indexOf(findStr);
                                if (matchesCase.isSelected()) {
                                    findStrIndex = text.indexOf(findStr.toLowerCase());
                                    if (findStrIndex == -1) findStrIndex = text.indexOf(findStr.toUpperCase());
                                    while (findStrIndex != -1) {
                                        int findStrIndexTmp = findStrIndex;
                                        StyleConstants.setBackground(myAttributeSet, new Color(188, 188, 191));
                                        doc.setCharacterAttributes(findStrIndex, findStr.length(), myAttributeSet, true);
                                        fileType.getNewFileNameField().setCaretPosition(0);
                                        findStrIndex = text.indexOf(findStr.toLowerCase(), findStrIndexTmp + 1);
                                        if (findStrIndex == -1) findStrIndex = text.indexOf(findStr.toUpperCase(), findStrIndexTmp + 1);
                                        check = true;
                                    }
                                } else if (text.contains(findStr)) {
                                    while (findStrIndex != -1) {
                                        StyleConstants.setBackground(myAttributeSet, new Color(188, 188, 191));
                                        doc.setCharacterAttributes(findStrIndex, findStr.length(), myAttributeSet, true);
                                        fileType.getNewFileNameField().setCaretPosition(0);
                                        findStrIndex = text.indexOf(findStr, findStrIndex + 1);
                                        check = true;
                                    }
                                }
                            } else if ("正则表达式(G)".equals(findPatternStr)) {
                                Pattern pattern = Pattern.compile(findStr);
                                Matcher matcher = pattern.matcher(text);
                                List<String> matcherStr = new ArrayList<>();
                                while (matcher.find()) matcherStr.add(matcher.group());
                                int findStrIndex = 0;
                                for (String str : matcherStr) {
                                    findStrIndex = text.indexOf(str, findStrIndex);
                                    StyleConstants.setBackground(myAttributeSet, new Color(188, 188, 191));
                                    doc.setCharacterAttributes(findStrIndex, str.length(), myAttributeSet, true);
                                    fileType.getNewFileNameField().setCaretPosition(0);
                                    check = true;
                                    findStrIndex += str.length();
                                }
                            }
                        }
                        if (!check) {
                            try {
                                prompt.setText("  查找：无法找到文本\"" + findStr + "\"");
                                prompt.setForeground(Color.red);
                                InputStream inputStream = PropertiesTool.class.getResourceAsStream("/audio/WindowsBackground.wav");
                                AudioStream audioStream = new AudioStream(inputStream);
                                AudioPlayer.player.start(audioStream);
                            } catch (Exception ec) {
                                ec.printStackTrace();
                            }
                        } else prompt.setText("");
                    }
                }
            });
        } else if (typeButton.equals("replacement")) { //全部替换
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String findStr = findTargetText.getText();
                    String toReplacedStr = ToReplacedText.getText();
                    String findPatternStr = "";
                    Enumeration<AbstractButton> elements = group.getElements();
                    while (elements.hasMoreElements()) {
                        AbstractButton btn = elements.nextElement();
                        if (btn.isSelected()) {
                            findPatternStr = btn.getText();
                            break;
                        }
                    }
                    int toCount = 0;
                    if (StringUtils.isNotEmpty(findStr)) {
                        for (TextEditingFileType fileType : pathList) {
                            String text = fileType.getNewFileNameField().getText();
                            MutableAttributeSet myAttributeSet = fileType.getNewFileNameField().getInputAttributes();
                            StyleConstants.setBackground(myAttributeSet, Color.WHITE);
                            StyledDocument doc = fileType.getNewFileNameField().getStyledDocument();
                            doc.setCharacterAttributes(0, text.length(), myAttributeSet, true);
                            if ("普通字符串(N)".equals(findPatternStr)) {
                                int findStrIndex = text.indexOf(findStr);
                                if (matchesCase.isSelected()) {
                                    findStrIndex = text.indexOf(findStr.toLowerCase());
                                    if (findStrIndex == -1) findStrIndex = text.indexOf(findStr.toUpperCase());
                                    while (findStrIndex != -1) {
                                        int findStrIndexTmp = findStrIndex;
                                        ++toCount;
                                        findStrIndex = text.indexOf(findStr.toLowerCase(), findStrIndexTmp + 1);
                                        if (findStrIndex == -1) findStrIndex = text.indexOf(findStr.toUpperCase(), findStrIndexTmp + 1);
                                    }
                                } else if (text.contains(findStr)) {
                                    while (findStrIndex != -1) {
                                        ++toCount;
                                        findStrIndex = text.indexOf(findStr, findStrIndex + 1);
                                    }
                                }
                                if (matchesCase.isSelected() && toCount != 0) {
                                    text = text.replace(findStr.toLowerCase(), toReplacedStr);
                                    text = text.replace(findStr.toUpperCase(), toReplacedStr);
                                    fileType.getNewFileNameField().setText(text);
                                } else if (text.contains(findStr) && toCount != 0)
                                    fileType.getNewFileNameField().setText(text.replace(findStr, toReplacedStr));
                            } else if ("正则表达式(G)".equals(findPatternStr)) {
                                Pattern pattern = Pattern.compile(findStr);
                                Matcher matcher = pattern.matcher(text);
                                List<String> matcherStr = new ArrayList<>();
                                while (matcher.find()) matcherStr.add(matcher.group());
                                for (String str : matcherStr) fileType.getNewFileNameField().setText(text.replace(str, toReplacedStr));
                                toCount += matcherStr.size();
                            }
                        }
                    }
                    prompt.setText("  全部替换：已替换" + toCount + "匹配项。");
                    prompt.setForeground(Color.blue);
                }
            });
        } else if (typeButton.equals("execute")) {// 执行修改
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int res = JOptionPane.showConfirmDialog(d, "是否确认修改文件", "确认", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (res == JOptionPane.YES_OPTION) {
                        int toCount = 0;
                        for (TextEditingFileType fileType : pathList) {
                            String parentPath = fileType.getFilePath().toFile().getParent();
                            String oldPath = parentPath + File.separator + fileType.getFileName();
                            String newPath = parentPath + File.separator + fileType.getNewFileNameField().getText();
                            if (!oldPath.equals(newPath)){
                                new File(oldPath).renameTo(new File(newPath));
                                ++toCount;
                            }
                        }
                        prompt.setText("  全部重命名：已修改" + toCount + "匹配项。");
                        prompt.setForeground(new Color(2, 128, 59));
                    }
                }
            });
        } else if (typeButton.equals("quash")) { // 撤销

        }
    }
}
