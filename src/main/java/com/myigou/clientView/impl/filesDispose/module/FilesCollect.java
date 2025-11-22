package com.myigou.clientView.impl.filesDispose.module;

import com.myigou.tool.BusinessTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FilesCollect {
    private JPanel jpanel;
    private JTextField address;
    private JButton selectFolderButton;
    private JPanel topCtrl;
    private JPanel centralCtrl;
    private JPanel bottomCtrl;
    private JButton startCollection;
    private JLabel countNumber;
    private JPanel fileTable;
    private JScrollPane jscrollpane;
    private JLabel fileColumn;
    private JLabel typeColumn;
    private JLabel sizeColumn;
    private JLabel progress;
    private List<String> fileNumber;
    private Font font = new Font("微软雅黑", Font.PLAIN, 12);
    private Border border = BorderFactory.createLineBorder(new Color(226, 224, 224), 1);
    // 总文件个数
    private int number = 0;

    public JPanel getJpanel() {
        return jpanel;
    }

    public FilesCollect() {
    }

    public FilesCollect(JFrame jFrame) {
        GridBagLayout fileTableGBL = new GridBagLayout();
        fileTable.setLayout(fileTableGBL);
        GridBagConstraints tableFilesGBC = new GridBagConstraints();
        jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
        fileColumn.setBorder(border);
        typeColumn.setBorder(border);
        sizeColumn.setBorder(border);
        // 选择文件夹
        selectFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDir(address, jFrame);
            }
        });
        // 抓取文件按钮
        startCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isEmpty(address.getText())) {
                    JOptionPane.showMessageDialog(jFrame, "请先选择或输入路径", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (!new File(address.getText()).exists()) {
                    JOptionPane.showMessageDialog(jFrame, "目录不存在！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                selectFolderButton.setEnabled(false);
                startCollection.setEnabled(false);
                fileNumber = getCountFile(address.getText(), new ArrayList<String>());
                countNumber.setText("共 " + fileNumber.size() + " 个项目");

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        fileTable.removeAll();
                        visible();
                        // 进度显示
                        progressTips("<html><font  style=\"color:red\"> 当前已获取: " + number + " 个 , 共: " + fileNumber.size() + " 个项目</font ></html>");
                        number = 0;
                        FileSystemView fsv = FileSystemView.getFileSystemView();
                        File com = fsv.getHomeDirectory();
                        File createDirectory = new File(com.getPath() + "\\Output files");
                        if (!createDirectory.exists()) {
                            createDirectory.mkdir();
                        }
                        byte[] barrel = new byte[1024];
                        File file = new File(address.getText());
                        File[] files = file.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            number = readWriteFile(createDirectory, files[i], barrel, number);
                        }
                        for (int i = 0; i < fileNumber.size(); i++) {
                            String fileAddr = fileNumber.get(i);
                            File temp = new File(fileAddr);
                            tableFilesGBC.gridwidth = 1;
                            tableFilesGBC.weightx = 1;
                            tableFilesGBC.weighty = 0;
                            tableFilesGBC.fill = GridBagConstraints.HORIZONTAL;
                            tableFilesGBC.gridy = i;
                            JLabel jLabelName = new JLabel(temp.getPath().replace(address.getText(), ""), JLabel.LEFT);
                            jLabelName.setBorder(border);
                            jLabelName.setFont(font);
                            jLabelName.setPreferredSize(new Dimension(400, 30));
                            tableFilesGBC.gridx = 0;
                            fileTableGBL.setConstraints(jLabelName, tableFilesGBC);
                            fileTable.add(jLabelName);
                            try {
                                JLabel jLabelType = new JLabel();
                                jLabelType.setBorder(border);
                                jLabelType.setFont(font);
                                jLabelType.setPreferredSize(new Dimension(10, 30));
                                String probeContentType = Files.probeContentType(temp.toPath());
                                jLabelType.setText(probeContentType);
                                tableFilesGBC.gridx = 1;
                                fileTableGBL.setConstraints(jLabelType, tableFilesGBC);
                                fileTable.add(jLabelType);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel jLabelSize = new JLabel(BusinessTool.fileSizeCalculation(temp.length()), JLabel.LEFT);
                            jLabelSize.setBorder(border);
                            jLabelSize.setFont(font);
                            jLabelSize.setPreferredSize(new Dimension(10, 30));
                            tableFilesGBC.gridx = 2;
                            fileTableGBL.setConstraints(jLabelSize, tableFilesGBC);
                            fileTable.add(jLabelSize);
                        }
                        // 扩展把内容顶到上面(使其不居中)
                        placeholder(fileTableGBL, tableFilesGBC);
                        countNumber.setText("共 " + number + " 个项目");
                        // 显示内容切换
                        startCollection.setEnabled(true);
                        selectFolderButton.setEnabled(true);
                        progress.setVisible(false);
                        visible();
                    }
                };
                Thread t = new Thread(runnable);
                t.start();
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
     * 占位扩展把内容顶到上面(使其不居中)
     * @param fileTableGBL
     * @param tableFilesGBC
     */
    public void placeholder(GridBagLayout fileTableGBL, GridBagConstraints tableFilesGBC) {
        JLabel jLabelName = new JLabel("", JLabel.LEFT);
        jLabelName.setFont(font);
        jLabelName.setBorder(border);
        tableFilesGBC.gridy = fileNumber.size() + 1;
        tableFilesGBC.gridx = 0;
        tableFilesGBC.gridwidth = 3;
        tableFilesGBC.weightx = 1;
        tableFilesGBC.weighty = 1;
        tableFilesGBC.fill = GridBagConstraints.BOTH;
        fileTableGBL.setConstraints(jLabelName, tableFilesGBC);
        fileTable.add(jLabelName);
    }

    /**
     * 读取目录下所有文件
     * @param fileDirectory
     * @param file
     * @param barrel
     */
    public int readWriteFile(File fileDirectory, File file, byte[] barrel, int number) {
        try {
            FileInputStream inputFile = null;
            FileOutputStream outFile = null;
            if (file.isFile() && file.length() > 0) {
                inputFile = new FileInputStream(file);
                File fileTemp = new File(fileDirectory.getPath() + "\\" + file.getName());
                fileTemp.createNewFile();
                outFile = new FileOutputStream(fileTemp);
                if (file.length() > 0) {
                    while (inputFile.read(barrel) != -1) outFile.write(barrel, 0, barrel.length);
                }
                number++;
                inputFile.close();
                outFile.close();
            } else if (file.isDirectory()) {
                for (int i = 0; i < file.listFiles().length; i++) {
                    number = readWriteFile(fileDirectory, file.listFiles()[i], barrel, number);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        progressTips("<html><font  style=\"color:red\"> 当前已获取: " + number + " 个 , 共: " + fileNumber.size() + " 个项目</font ></html>");
        return number;
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
            } else if (f.isDirectory()) {
                count = getCountFile(f.getPath(), count);
            }
        }
        return count;
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
        fileTable.setVisible(false);
        fileTable.setVisible(true);
        fileTable.setVisible(false);
        fileTable.setVisible(true);
    }

}
