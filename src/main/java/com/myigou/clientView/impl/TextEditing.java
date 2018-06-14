package com.myigou.clientView.impl;

import com.myigou.clientView.FunctionInter;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/6/29.
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
    JButton selectExtracting = null;
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 文本或图片显示
    private JLabel jLabel = null;
    // 总文件个数
    private int number = 0;
    // 预编译正则表达式
    private static Pattern NUMBER_PATTERN = Pattern.compile("[a-zA-Z]:(\\\\|/)");
    // 文字大小
    private Font fontTxT = new Font("楷体",Font.BOLD,18);
    int[]  position = {5,5,5,5};
    // 组件数组
    List<Object> assemblyList = new ArrayList<Object>();

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(gridBagLayout);
        this.fileConcentrated(jPanel);
        this.createFolders(jPanel);
        this.createFolders1(jPanel);
        this.createFolders2(jPanel);
        this.registerListener(jPanel, jFrame);
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame,Font font) {
        JLabel title= new JLabel("文件操作");
        title.setFont(font);
        jPanel.add(title);
        return jPanel;
    }

    private void fileConcentrated(JPanel jPanel) {
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border, "",
                TitledBorder.LEFT, TitledBorder.CENTER, new Font("楷体", Font.PLAIN, 18), Color.BLACK);
        jPanel.setBorder(border);
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jLabel = new JLabel("一、取出目录下所有文件集中放置在一个桌面文件夹(Output files)中");
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(position[0], position[1], position[2], position[3]);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        JLabel address = new JLabel("文件地址:");
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        fileAddress = new JTextField();
        fileAddress.setFont(fontTxT);
        fileAddress.setColumns(60);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(fileAddress, gridBagConstraints);
        jPanel.add(fileAddress);

        select = new JButton("选择目录");
        select.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(select, gridBagConstraints);
        jPanel.add(select);

        extracting = new JButton("抓取文件");
        extracting.setFocusPainted(false);
        gridBagLayout.setConstraints(extracting, gridBagConstraints);
        jPanel.add(extracting);

    }

    public void createFolders(JPanel jPanel) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel jLabel = new JLabel("二、输入目录创建文件夹");
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(position[0], position[1], position[2], position[3]);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        JLabel address = new JLabel("创建地址:");
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        createFileAddress = new JTextField();
        createFileAddress.setFont(fontTxT);
        createFileAddress.setColumns(60);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(createFileAddress, gridBagConstraints);
        jPanel.add(createFileAddress);

        createExtracting = new JButton("创建文件夹");
        createExtracting.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(createExtracting, gridBagConstraints);
        jPanel.add(createExtracting);

    }

    private void createFolders1(JPanel jPanel) {
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel jLabel = new JLabel("三、搜索文件（按条件搜索文件，只搜索文件；不搜索文件内容。）");
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new Insets(position[0], position[1], position[2], position[3]);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        JLabel address = new JLabel("创建地址:");
        gridBagConstraints.gridy = 5;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        JTextField fileAddress = new JTextField();
        fileAddress.setFont(fontTxT);
        createFileAddress.setColumns(60);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(fileAddress, gridBagConstraints);
        jPanel.add(fileAddress);
        assemblyList.add(fileAddress);

        selectExtracting = new JButton("搜索");
        selectExtracting.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(selectExtracting, gridBagConstraints);
        jPanel.add(selectExtracting);

        JPanel select = new JPanel();
        select.setLayout(new GridLayout(1,4));
        //select.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
        gridBagConstraints.insets = new Insets(5, 70, 5, 5);
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        JCheckBox searchAll = new JCheckBox("搜索所有文件名");
        assemblyList.add(searchAll);
        JCheckBox searchNumber = new JCheckBox("搜索包含数字文件名");
        assemblyList.add(searchNumber);
        JCheckBox searchChinese = new JCheckBox("搜索包含中文文件名");
        assemblyList.add(searchChinese);
        JCheckBox searchEnglish = new JCheckBox("搜索包含英文文件名");
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
        JLabel jLabel = new JLabel("四、功能开发中。。");
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new Insets(position[0], position[1], position[2], position[3]);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        JLabel address = new JLabel("创建地址:");
        gridBagConstraints.gridy = 8;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        JTextField fileAddress = new JTextField();
        fileAddress.setFont(fontTxT);
        createFileAddress.setColumns(60);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagLayout.setConstraints(fileAddress, gridBagConstraints);
        jPanel.add(fileAddress);

        JButton extracting = new JButton("创建文件夹");
        extracting.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(extracting, gridBagConstraints);
        jPanel.add(extracting);

    }

    /**
     * 按钮事件
     *
     * @param jFrame
     * @param jPanel
     */
    private void registerListener(final JPanel jPanel, final JFrame jFrame) {
        /**
         * 获取文件路径按钮
         */
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = fileAddress.getText();
                JFileChooser chooser = new JFileChooser(filePath);
                // 设置只能选择目录
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                String directoryURL = "";
                int returnVal = chooser.showOpenDialog(jFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    directoryURL = chooser.getSelectedFile().getPath();
                }
                if (directoryURL != null) {
                    fileAddress.setText(directoryURL);
                }
            }
        });
        /**
         * 抓取文件按钮
         */
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
                            number = readWriteFile(createDirectory, files[i], barrel, number, jPanel, jFrame,fileNumber.size());
                        }
                        extracting.setEnabled(true);
                    }
                };
                Thread t = new Thread(runnable);
                t.start();
            }
        });
        /**
         * 创建文件目录按钮
         */
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
                if(createFileAddress.getText().split("\\\\").length > 1){
                    urlArr = createFileAddress.getText().split("\\\\");
                }else if(createFileAddress.getText().split("/").length > 1){
                    urlArr = createFileAddress.getText().split("/");
                }else{
                    JOptionPane.showMessageDialog(jFrame, "目录创建失败！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                for(int i = 1; i < urlArr.length; i++){
                    File file = new File(urlArr[0] += "\\"+urlArr[i]);
                    if (!file.exists()){
                        file.mkdir();
                    }
                }
                int res = JOptionPane.showConfirmDialog(jFrame, "是否打开创建目录？", "创建成功", JOptionPane.YES_NO_OPTION);
                try {
                    if (res == JOptionPane.YES_OPTION) {
                        String vca = "cmd /c start \" \" \""+createFileAddress.getText()+"\"" ;
                        Runtime.getRuntime().exec(vca);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(jFrame, "目录打开失败！", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        /**
         * 搜索文件
         */
        selectExtracting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String term = "[";
                for(int i = 1; i < assemblyList.size(); i++){
                    JCheckBox jCheckBox = (JCheckBox)assemblyList.get(i);
                    if("搜索所有文件名".equals(jCheckBox.getText()) && jCheckBox.isSelected()){
                        term = ".";
                        break;
                    }else{
                        if("搜索包含数字文件名".equals(jCheckBox.getText()) && jCheckBox.isSelected()){
                            term += "0-9";
                        }
                        if("搜索包含英文文件名".equals(jCheckBox.getText()) && jCheckBox.isSelected()){
                            term += "a-zA-Z";
                        }
                        if("搜索包含中文文件名".equals(jCheckBox.getText()) && jCheckBox.isSelected()){
                            term += "\u4E00-\u9FA5";
                        }
                    }
                }
                if("[".equals(term)){
                    JOptionPane.showMessageDialog(jFrame, "请选择搜索方式", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }else if(!".".equals(term)){
                    term += "]";
                }
                // 获取输入的路径
                String selectUrl = ((JTextField)assemblyList.get(0)).getText();
                if(StringUtils.isEmpty(selectUrl)){
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
                    ((JTextField)assemblyList.get(0)).setText(selectUrl);
                }
                Pattern pattern = NUMBER_PATTERN;
                Matcher matcher = pattern.matcher(selectUrl);
                if(!matcher.find()){
                    JOptionPane.showMessageDialog(jFrame, "请输入正确搜索路径", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 动态正则 筛选文件
                Pattern compile = Pattern.compile(term);
                File selectFile = new File(selectUrl);
                List<String> pathList = new ArrayList<String>();
                List<String> pathTemp = getCountFile(selectFile.getAbsolutePath(),new ArrayList<>());
                for(int i=0 ; i<pathTemp.size() ; i++){
                    String[] pStr = null;
                    if(pathTemp.get(i).split("//").length > 1){
                        pStr = pathTemp.get(i).split("//");
                    }else if(pathTemp.get(i).split("\\\\").length > 1){
                        pStr = pathTemp.get(i).split("\\\\");
                    }
                    String fileName = pStr[pStr.length - 1];
                    Matcher matcher1 = compile.matcher(fileName);
                    if(matcher1.find()){
                        pathList.add(pathTemp.get(i));
                    }
                }
                // 组装自定义 对话框
                JDialog d = new JDialog(jFrame,selectUrl.toUpperCase());
                d.setSize(700, 400);
                Container container = d.getContentPane();
                container.setLayout(new BorderLayout());
                // 文件路径展示面板
                JPanel centre = new JPanel();
                GridBagLayout gridBagLayout= new GridBagLayout();
                centre.setLayout(gridBagLayout);
                GridBagConstraints gridBagConstraints = new GridBagConstraints();
                for (int i=0 ; i<pathList.size(); i++){
                    JLabel jLPath = new JLabel(pathList.get(i).replace(selectUrl,""));
                    jLPath.setName(pathList.get(i));
                    jLPath.setFont(new Font("仿宋",Font.PLAIN,20));
                    jLPath.setBorder(BorderFactory.createLineBorder(Color.white,1));
                    jLPath.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                int ComponentCount = centre.getComponentCount();
                                for(int is=0 ; is<ComponentCount ; is++){
                                    ((JLabel)centre.getComponent(is)).setBackground(Color.lightGray);
                                }
                                jLPath.setOpaque(true);
                                jLPath.setForeground(Color.BLACK);
                                jLPath.setBackground(Color.orange);
                                JLabel evnLab = (JLabel)e.getSource();
                                String vca = "explorer /select, "+ evnLab.getName();
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
                    gridBagConstraints.gridx = 0 ;
                    gridBagConstraints.gridy = i;
                    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL ;
                    // 当窗口放大时，长度变
                    gridBagConstraints.weightx = 1;
                    gridBagConstraints.insets = new Insets(0, 10, 0, 10);
                    gridBagLayout.setConstraints(jLPath,gridBagConstraints);
                    centre.add(jLPath);
                }
                JPanel jPanel1 = new JPanel(new BorderLayout());
                jPanel1.add(centre,BorderLayout.NORTH);
                JScrollPane jScrollPane = new JScrollPane(jPanel1);
                // 顶部控制面板
                JPanel top = new JPanel(new FlowLayout());
                top.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
                top.add(new JLabel("条件:",JLabel.CENTER));
                JTextField jTextField= new JTextField();
                jTextField.setFont(new Font("黑体",Font.PLAIN,15));
                jTextField.setColumns(30);
                top.add(jTextField);
                JButton screenJButton = new JButton("筛选");
                JLabel jFileCount = new JLabel("共 "+pathList.size()+" 个文件");
                top.add(jFileCount);
                top.add(screenJButton);
                String finalConvertUrl = selectUrl;
                screenJButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String jText = jTextField.getText().toLowerCase();
                        int countFile = 0;
                        for(int i=0 ; i<pathList.size() ; i++){
                            JLabel jLabel = (JLabel)centre.getComponent(i);
                            String sUrl = jLabel.getText().replace(finalConvertUrl,"").toLowerCase();
                            if(sUrl.indexOf(jText) != -1){
                                countFile++;
                                jLabel.setVisible(true);
                            }else{
                                jLabel.setVisible(false);
                            }
                        }
                        jFileCount.setText("共 "+countFile+" 个文件");
                    }
                });
                // 显示主面板
                JPanel mView = new JPanel();
                mView.setLayout(new BorderLayout());
                mView.add(top,BorderLayout.NORTH);
                mView.add(jScrollPane,BorderLayout.CENTER);
                container.add(mView);
                d.setModal(true);

                d.setMinimumSize(new Dimension(700,400));
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
    public int readWriteFile(File fileDirectory, File file, byte[] barrel, int number, JPanel jPanel, JFrame jFrame,int fileNumber) {
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
                    number = readWriteFile(fileDirectory, file.listFiles()[i], barrel, number, jPanel, jFrame,fileNumber);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        jLabel.setText("<html>一、取出目录下所有文件集中放置在一个桌面文件夹(Output files)中 <font  style=\"color:red\"> 当前已获取: " + number + " 个,共: "+fileNumber+" 个;</font ></html>");
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
}
