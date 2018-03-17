package com.myigou.clientView.impl;

import com.myigou.clientView.FunctionInter;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
    //创建文件地址
    private JTextField createFileAddress = null;
    // 抓取文件按钮
    private JButton extracting = null;
    // 创建文件按钮
    private JButton createExtracting = null;
    // 网格布局
    private GridBagLayout gridBagLayout = new GridBagLayout();
    // 文本或图片显示
    private JLabel jLabel = null;
    // 总文件个数
    private int number = 0;
    // Timer 事件，Timer 控件可以有规律地隔一段时间执行一次代码
    private Timer timer;
    // 预编译正则表达式
    private static Pattern NUMBER_PATTERN = Pattern.compile("[a-zA-Z]:(\\\\|/)");

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
    public JPanel getTitle(JPanel jPanel, JFrame jFrame) {
        jPanel.add(new JLabel("文件操作"));
        return jPanel;
    }

    private void fileConcentrated(JPanel jPanel) {
        int prompt = 0;
        int function = 1;
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 10, 5);
        jLabel = new JLabel("一、取出目录下所有文件集中放置在一个桌面文件夹(Output files)中");
        gridBagConstraints.gridy = prompt;
        gridBagConstraints.gridwidth = 2;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        gridBagConstraints.insets = new Insets(5, 5, 30, 5);
        JLabel address = new JLabel("文件地址:");
        gridBagConstraints.gridy = function;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        fileAddress = new JTextField();
        fileAddress.setColumns(40);
        gridBagConstraints.gridwidth = 2;
        gridBagLayout.setConstraints(fileAddress, gridBagConstraints);
        jPanel.add(fileAddress);

        select = new JButton("选择目录");
        select.setPreferredSize(new Dimension(100, 25));
        select.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(select, gridBagConstraints);
        jPanel.add(select);

        extracting = new JButton("抓取文件");
        extracting.setPreferredSize(new Dimension(100, 25));
        extracting.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(extracting, gridBagConstraints);
        jPanel.add(extracting);

    }

    public void createFolders(JPanel jPanel) {
        int prompt = 2;
        int function = 3;
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 10, 5);
        JLabel jLabel = new JLabel("二、输入目录创建文件夹");
        gridBagConstraints.gridy = prompt;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        gridBagConstraints.insets = new Insets(5, 5, 30, 5);
        JLabel address = new JLabel("创建地址:");
        gridBagConstraints.gridy = function;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        createFileAddress = new JTextField();
        createFileAddress.setColumns(40);
        gridBagConstraints.gridwidth = 2;
        gridBagLayout.setConstraints(createFileAddress, gridBagConstraints);
        jPanel.add(createFileAddress);

        createExtracting = new JButton("创建文件夹");
        createExtracting.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(createExtracting, gridBagConstraints);
        jPanel.add(createExtracting);

    }

    private void createFolders1(JPanel jPanel) {
        int prompt = 4;
        int function = 5;
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 10, 5);
        JLabel jLabel = new JLabel("三、功能开发中。。");
        gridBagConstraints.gridy = prompt;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        gridBagConstraints.insets = new Insets(5, 5, 30, 5);
        JLabel address = new JLabel("创建地址:");
        gridBagConstraints.gridy = function;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        JTextField fileAddress = new JTextField();
        fileAddress.setColumns(40);
        gridBagConstraints.gridwidth = 2;
        gridBagLayout.setConstraints(fileAddress, gridBagConstraints);
        jPanel.add(fileAddress);

        JButton extracting = new JButton("创建文件夹");
        extracting.setFocusPainted(false);
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(extracting, gridBagConstraints);
        jPanel.add(extracting);

    }

    private void createFolders2(JPanel jPanel) {
        int prompt = 6;
        int function = 7;
        // 控制按钮显示组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 10, 5);
        JLabel jLabel = new JLabel("四、功能开发中。。");
        gridBagConstraints.gridy = prompt;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        jPanel.add(jLabel);

        gridBagConstraints.insets = new Insets(5, 5, 30, 5);
        JLabel address = new JLabel("创建地址:");
        gridBagConstraints.gridy = function;
        gridBagConstraints.gridwidth = 1;
        gridBagLayout.setConstraints(address, gridBagConstraints);
        jPanel.add(address);
        // 文件地址
        JTextField fileAddress = new JTextField();
        fileAddress.setColumns(40);
        gridBagConstraints.gridwidth = 2;
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
                JFileChooser chooser = new JFileChooser();
                // 设置只能选择目录
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                String directoryURL = "";
                int returnVal = chooser.showOpenDialog(jFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    directoryURL = chooser.getSelectedFile().getPath();
                    chooser.hide();
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
                if ("".equals(fileAddress.getText())) {
                    JOptionPane.showMessageDialog(jFrame, "请先选择或输入路径", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (!new File(fileAddress.getText()).exists()) {
                    JOptionPane.showMessageDialog(jFrame, "目录不存在！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                final int fileNumber = getCountFile(fileAddress.getText(), 0);
                final JPanel jPanelBar = new JPanel();
                final JProgressBar bar = new JProgressBar();
                // jPanelBar.setBackground(Color.CYAN);
                bar.setMinimum(0);
                bar.setMaximum(fileNumber);
                bar.setValue(0);
                bar.setStringPainted(true);
                timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int value = bar.getValue();
                        if (value < fileNumber) {
                            value++;
                            bar.setValue(value);
                        } else {
                            timer.stop();
                            jPanelBar.setVisible(false);
                        }
                    }
                });
                bar.setPreferredSize(new Dimension(500, 20));
                jPanelBar.add(bar);
                jPanel.setVisible(true);
                jFrame.add(jPanelBar, BorderLayout.CENTER);
                jPanelBar.updateUI();
                jPanelBar.setVisible(true);
                jPanel.revalidate();
                jPanel.repaint();
                jFrame.revalidate();
                jFrame.repaint();
                timer.start();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
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
                            number = readWriteFile(createDirectory, files[i], barrel, number, jPanel, jFrame);
                        }
                    }
                };
                runnable.run();
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
                File file = new File(createFileAddress.getText());
                if (file.exists()) {
                    JOptionPane.showMessageDialog(jFrame, "目录已存在！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (file.mkdir()) {
                    int res = JOptionPane.showConfirmDialog(jFrame, "是否打开创建目录？", "创建成功", JOptionPane.YES_NO_OPTION);
                    try {
                        if (res == JOptionPane.YES_OPTION) {
                            Runtime.getRuntime().exec("cmd /c start " + createFileAddress.getText());
                        }
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(jFrame, "目录打开失败！", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(jFrame, "目录创建失败！", "提示", JOptionPane.WARNING_MESSAGE);
                }
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
    public int readWriteFile(File fileDirectory, File file, byte[] barrel, int number, JPanel jPanel, JFrame jFrame) {
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
                    number = readWriteFile(fileDirectory, file.listFiles()[i], barrel, number, jPanel, jFrame);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        jLabel.setText("<html>一、取出目录下所有文件集中放置在一个桌面文件夹(Output files)中 <font  style=\"color:red\"> 当前已获取 : " + number + " 个</font ></html>");
        return number;
    }

    /**
     * 获取目录下有多少文件
     *
     * @param FilePath
     * @param count
     * @return
     */
    public int getCountFile(String FilePath, int count) {
        File file = new File(FilePath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                count++;
            } else if (f.isDirectory()) {
                count = getCountFile(f.getPath(), count);
            }
        }
        return count;
    }
}
