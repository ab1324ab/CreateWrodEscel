package com.myigou.clientView.impl.filesDispose.module;

import com.myigou.tool.BusinessTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSearch {
    private JPanel jpanel;
    private JTextField address;
    private JButton selectFolderButton;
    private JCheckBox searchAll;
    private JCheckBox searchNumber;
    private JCheckBox searchChinese;
    private JCheckBox searchEnglish;
    private JTextField searchFileTextField;
    private JButton reselectButton;
    private JPanel searchName;
    private JPanel selectFileDir;
    private JPanel topCtrl;
    private JPanel bottomCtrl;
    private JButton searchFileButton;
    private JLabel selectFilePathJlabel;
    private JPanel centre;
    private JScrollPane jscrollpane;
    private JLabel countNumber;
    private JLabel progress;
    private JLabel fileColumn;
    private JLabel typeColumn;
    private JLabel sizeColumn;
    private Font font = new Font("微软雅黑", Font.PLAIN, 12);
    private Border border = BorderFactory.createLineBorder(new Color(226, 224, 224), 1);

    public JPanel getJpanel() {
        return jpanel;
    }

    public FileSearch() {
    }

    public FileSearch(JFrame jFrame) {
        GridBagLayout tableGBL = new GridBagLayout();
        centre.setLayout(tableGBL);
        GridBagConstraints tableGBC = new GridBagConstraints();
        jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
        fileColumn.setBorder(border);
        typeColumn.setBorder(border);
        sizeColumn.setBorder(border);
        selectFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDir(address, jFrame);
                if (StringUtils.isNotEmpty(address.getText())) {
                    selectFileDir.setVisible(false);
                    searchName.setVisible(true);
                    selectFilePathJlabel.setText(address.getText());
                    String term = "[";
                    if (searchAll.isSelected()) term += ".";
                    if (searchNumber.isSelected()) term = term.replace(".", "") + "0-9";
                    if (searchChinese.isSelected()) term = term.replace(".", "") + "\u4E00-\u9FA5";
                    if (searchEnglish.isSelected()) term = term.replace(".", "") + "a-zA-Z";
                    if ("[".equals(term)) {
                        selectFileDir.setVisible(true);
                        searchName.setVisible(false);
                        JOptionPane.showMessageDialog(jFrame, "请选择搜索方式", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    term += "]";
                    // 动态正则 筛选文件
                    Pattern compile = Pattern.compile(term);
                    File selectFile = new File(address.getText());
                    reselectButton.setEnabled(false);
                    searchFileButton.setEnabled(false);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            centre.removeAll();
                            visible();
                            // 获取目录下有多少文件
                            List<String> pathTemp = getCountFile(selectFile.getAbsolutePath(), new ArrayList<>());
                            int matcherCount = 0;
                            for (int i = 0; i < pathTemp.size(); i++) {
                                progressTips("<html><font  style=\"color:red\"> 已整理文件: " + (i + 1) + " 个 , 共 " + pathTemp.size() + " 个项目</font ></html>");
                                String[] pStr = null;
                                if (pathTemp.get(i).split("//").length > 1) pStr = pathTemp.get(i).split("//");
                                else if (pathTemp.get(i).split("\\\\").length > 1) pStr = pathTemp.get(i).split("\\\\");
                                String fileName = pStr[pStr.length - 1];
                                Matcher matcher = compile.matcher(fileName);
                                if (matcher.find()) {
                                    matcherCount++;
                                    // 创建行内容
                                    JPanel row = createRows(pathTemp.get(i));
                                    tableGBC.weightx = 1;
                                    tableGBC.weighty = 0;
                                    tableGBC.gridwidth = 3;
                                    tableGBC.fill = GridBagConstraints.HORIZONTAL;
                                    tableGBC.gridy = i;
                                    tableGBL.setConstraints(row, tableGBC);
                                    centre.add(row);
                                    row.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            try {
                                                int ComponentCount = centre.getComponentCount();
                                                for (int is = 0; is < ComponentCount; is++) {
                                                    ((JPanel) centre.getComponent(is)).setBackground(new Color(240, 240, 240));
                                                    ((JPanel) centre.getComponent(is)).setName("false");
                                                }
                                                row.setOpaque(true);
                                                row.setName("true");
                                                row.setBackground(new Color(198, 225, 246));
                                                JLabel evnLab = (JLabel) row.getComponent(0);
                                                String vca = "explorer /select, " + address.getText() + evnLab.getText();
                                                Runtime.getRuntime().exec(vca);
                                            } catch (Exception e1) {
                                                e1.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void mouseEntered(MouseEvent e) {
                                            if (row.getName() == null || !row.getName().equals("true")) {
                                                row.setOpaque(true);
                                                row.setBackground(new Color(230, 244, 255));
                                            }
                                        }

                                        @Override
                                        public void mouseExited(MouseEvent e) {
                                            if (row.getName() == null || !row.getName().equals("true")) {
                                                row.setOpaque(false);
                                                row.setBackground(new Color(240, 240, 240));
                                            }
                                        }
                                    });
                                }
                                countNumber.setText("共 " + matcherCount + " 个项目");
                            }
                            progress.setVisible(false);
                            // 扩展把内容顶到上面(使其不居中)
                            placeholder(tableGBL, tableGBC, matcherCount);
                            visible();
                            reselectButton.setEnabled(true);
                            searchFileButton.setEnabled(true);
                        }
                    };
                    Thread t = new Thread(runnable);
                    t.start();
                }
            }
        });
        reselectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFileDir.setVisible(true);
                searchName.setVisible(false);
            }
        });
        searchFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jText = searchFileTextField.getText();
                int countFile = 0;
                int countNUll = 0;
                for (int i = 0; i < centre.getComponents().length; i++) {
                    JPanel jPanel = (JPanel) centre.getComponent(i);
                    if (jPanel.getComponentCount() != 0) {
                        String sUrl = ((JLabel) jPanel.getComponent(0)).getText();
                        if (sUrl.indexOf(jText) != -1) {
                            countFile++;
                            jPanel.setVisible(true);
                        } else {
                            jPanel.setVisible(false);
                        }
                    }
                    visible();
                }
                countNumber.setText("共 " + countFile + " 个项目");
            }
        });
        // 输入框回车 调用筛选按钮
        searchFileTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (10 == e.getKeyCode()) searchFileButton.doClick();
            }
        });
    }

    /**
     * 文件夹选择
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
     * 获取目录下有多少文件
     * @param FilePath
     * @param count
     * @return
     */
    public List<String> getCountFile(String FilePath, List<String> count) {
        File file = new File(FilePath);
        File[] files = file.listFiles();
        if (files == null) files = new File[0];
        for (File f : files) {
            if (f.isFile()) {
                count.add(f.getAbsolutePath());
                progressTips("<html><font  style=\"color:red\"> 当前已找到: " + count.size() + " 个项目</font ></html>");
            } else if (f.isDirectory()) {
                count = getCountFile(f.getPath(), count);
            }
        }
        return count;
    }

    /**
     * 占位扩展把内容顶到上面(使其不居中)
     * @param fileTableGBL
     * @param tableFilesGBC
     */
    public void placeholder(GridBagLayout fileTableGBL, GridBagConstraints tableFilesGBC, int length) {
        JPanel jLabelName = new JPanel();
        jLabelName.setBorder(border);
        tableFilesGBC.gridy = length + 1;
        tableFilesGBC.gridx = 0;
        tableFilesGBC.gridwidth = 3;
        tableFilesGBC.weightx = 1;
        tableFilesGBC.weighty = 1;
        tableFilesGBC.fill = GridBagConstraints.BOTH;
        fileTableGBL.setConstraints(jLabelName, tableFilesGBC);
        centre.add(jLabelName);
    }

    /**
     * 创建表格标题
     * @param filePath
     */
    public JPanel createRows(String filePath) {
        GridBagLayout rowGBL = new GridBagLayout();
        GridBagConstraints rowGBC = new GridBagConstraints();
        JPanel row = new JPanel(rowGBL);
        rowGBC.weightx = 1;
        rowGBC.weighty = 0;
        rowGBC.fill = GridBagConstraints.HORIZONTAL;
        rowGBC.gridy = 0;
        File file = new File(filePath);
        // 文件
        JLabel jLabelName = new JLabel(filePath.replace(address.getText(), ""), JLabel.LEFT);
        jLabelName.setPreferredSize(new Dimension(400, 30));
        jLabelName.setBorder(border);
        jLabelName.setFont(font);
        rowGBC.gridx = 0;
        rowGBL.setConstraints(jLabelName, rowGBC);
        row.add(jLabelName);
        // 类型
        JLabel jLabelType = new JLabel();
        jLabelType.setPreferredSize(new Dimension(10, 30));
        jLabelType.setBorder(border);
        jLabelType.setFont(font);
        jLabelType.setHorizontalAlignment(JLabel.LEFT);
        String probeContentType = null;
        try {
            probeContentType = Files.probeContentType(file.toPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        jLabelType.setText(probeContentType);
        rowGBC.gridx = 1;
        rowGBL.setConstraints(jLabelType, rowGBC);
        row.add(jLabelType);
        // 大小(KB)
        JLabel jLabelSize = new JLabel(BusinessTool.fileSizeCalculation(file.length()), JLabel.LEFT);
        jLabelSize.setPreferredSize(new Dimension(10, 30));
        jLabelSize.setBorder(border);
        jLabelSize.setFont(font);
        rowGBC.gridx = 2;
        rowGBL.setConstraints(jLabelSize, rowGBC);
        row.add(jLabelSize);
        return row;
    }

    /**
     * 进度文字提示
     */
    public void progressTips(String tips) {
        progress.setVisible(true);
        progress.setText(tips);
    }

    /**
     * 切换显示
     */
    public void visible() {
        centre.setVisible(false);
        centre.setVisible(true);
        centre.setVisible(false);
        centre.setVisible(true);
    }

}
