package com.myigou.clientView.impl.filesDispose.module;

import com.myigou.clientService.model.TextEditingFileType;
import com.myigou.tool.AudioPlayerTool;
import com.myigou.tool.BusinessTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRename {
    private JPanel jpanel;
    private JTextField address;
    private JButton selectFolderButton;
    private JPanel selectFileDir;
    private JPanel replacementFileName;
    private JTextField findTargetText;
    private JButton executeJButton;
    private JButton screenAllJButton;
    private JTextField toReplacedText;
    private JButton replacementJButton;
    private JRadioButton normalStringRadioButton;
    private JRadioButton regularExpressionRadioButton;
    private JCheckBox matchesCase;
    private JButton reselectButton;
    private JButton insertCharNumberJButton;
    private JLabel selectFilePathJlabel;
    private JPanel centre;
    private JScrollPane jscrollpane;
    private JLabel fileColumn;
    private JLabel newFileColumn;
    private JLabel typeColumn;
    private JLabel sizeColumn;
    private JLabel progress;
    private JLabel countNumber;
    private Font font = new Font("微软雅黑", Font.PLAIN, 12);
    private Border border = BorderFactory.createLineBorder(new Color(226, 224, 224), 1);
    private Border borderLR = BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(226, 224, 224));

    public JPanel getJpanel() {
        return jpanel;
    }

    public FileRename() {
    }

    public FileRename(JFrame jFrame) {
        GridBagLayout tableGBL = new GridBagLayout();
        centre.setLayout(tableGBL);
        GridBagConstraints tableGBC = new GridBagConstraints();
        // 滚动条设置
        jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
        JScrollBar jScrollBar = jscrollpane.getVerticalScrollBar();
        ButtonGroup group = new ButtonGroup();
        group.add(normalStringRadioButton);
        group.add(regularExpressionRadioButton);
        fileColumn.setBorder(border);
        newFileColumn.setBorder(border);
        typeColumn.setBorder(border);
        sizeColumn.setBorder(border);
        List<TextEditingFileType> pathList = new ArrayList<>();
        selectFolderButton.addActionListener(e -> {
            selectDir(address, jFrame);
            if (StringUtils.isNotEmpty(address.getText())) {
                selectFilePathJlabel.setText(address.getText());
                replacementFileName.setVisible(true);
                selectFileDir.setVisible(false);
                centre.removeAll();
                Runnable runnable = () -> {
                    centre.removeAll();
                    visible();
                    List<String> pathTemp = getCountFile(address.getText(), new ArrayList<>());
                    // 扩展把内容顶到上面(使其不居中)
                    placeholder(tableGBL, tableGBC, pathTemp.size());

                    for (int i = 0; i < pathTemp.size(); i++) {
                        progressTips("<html><font  style=\"color:red\"> 已整理文件: " + (i + 1) + " 个 , 共 " + pathTemp.size() + " 个项目</font ></html>");
                        File file = new File(pathTemp.get(i));
                        TextEditingFileType editingFileType = null;
                        try {
                            editingFileType = new TextEditingFileType(file.getName(), file.toPath(), Files.probeContentType(file.toPath()), file.getName(), file.length());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        assert editingFileType != null;
                        JPanel row = createRows(editingFileType);
                        //
                        tableGBC.gridwidth = 1;
                        tableGBC.weightx = 1;
                        tableGBC.weighty = 0;
                        tableGBC.fill = GridBagConstraints.HORIZONTAL;
                        tableGBC.gridy = i;
                        tableGBL.setConstraints(row, tableGBC);
                        centre.add(row);
                        pathList.add(editingFileType);
                        countNumber.setText("共 " + (i + 1) + " 个项目");
                    }
                    progress.setVisible(false);
                    visible();
                };
                Thread t = new Thread(runnable);
                t.start();
            }
        });
        reselectButton.addActionListener(e -> {
            selectFileDir.setVisible(true);
            replacementFileName.setVisible(false);
            pathList.clear();
        });
        ActionListener radioListener = e -> {
            if (e.getActionCommand().equals("普通字符串(N)")) matchesCase.setEnabled(true);
            else if (e.getActionCommand().equals("正则表达式(G)")) {
                matchesCase.setEnabled(false);
                matchesCase.setSelected(false);
            }
        };
        normalStringRadioButton.addActionListener(radioListener);
        regularExpressionRadioButton.addActionListener(radioListener);
        screenAllJButton.addActionListener(e -> {
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
                int matchCount = 0;
                for (TextEditingFileType fileType : pathList) {
                    JTextPane jTextPane = (JTextPane) fileType.getRow().getComponent(1);
                    String text = jTextPane.getText();
                    MutableAttributeSet myAttributeSet = jTextPane.getInputAttributes();
                    StyleConstants.setBackground(myAttributeSet, Color.WHITE);
                    StyledDocument doc = jTextPane.getStyledDocument();
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
                                jScrollBar.setValue(fileType.getRow().getY());
                                findStrIndex = text.indexOf(findStr.toLowerCase(), findStrIndexTmp + 1);
                                if (findStrIndex == -1) findStrIndex = text.indexOf(findStr.toUpperCase(), findStrIndexTmp + 1);
                                check = true;
                                matchCount++;
                            }
                        } else if (text.contains(findStr)) {
                            while (findStrIndex != -1) {
                                StyleConstants.setBackground(myAttributeSet, new Color(188, 188, 191));
                                doc.setCharacterAttributes(findStrIndex, findStr.length(), myAttributeSet, true);
                                jScrollBar.setValue(fileType.getRow().getY());
                                findStrIndex = text.indexOf(findStr, findStrIndex + 1);
                                check = true;
                                matchCount++;
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
                            jTextPane.setCaretPosition(0);
                            check = true;
                            findStrIndex += str.length();
                            matchCount++;
                        }
                    }
                }
                progressTips("<html><font  style=\"color:red\"> 查找目标计数: " + matchCount + " 匹配项</font ></html>");
                if (!check) {
                    AudioPlayerTool.playerStartAudioWav("/audio/Hardware Insert.wav");
                    JOptionPane.showMessageDialog(jFrame, "无法找到文本\"" + findStr + "\"", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        replacementJButton.addActionListener(e -> {
            String findStr = findTargetText.getText();
            if (StringUtils.isNotEmpty(findStr)) {
                String toReplacedStr = toReplacedText.getText();
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
                        JTextPane jTextPane = (JTextPane) fileType.getRow().getComponent(1);
                        String text = jTextPane.getText();
                        MutableAttributeSet myAttributeSet = jTextPane.getInputAttributes();
                        StyleConstants.setBackground(myAttributeSet, Color.WHITE);
                        StyledDocument doc = jTextPane.getStyledDocument();
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
                                jTextPane.setText(text);
                            } else if (text.contains(findStr) && toCount != 0)
                                jTextPane.setText(text.replace(findStr, toReplacedStr));
                        } else if ("正则表达式(G)".equals(findPatternStr)) {
                            Pattern pattern = Pattern.compile(findStr);
                            Matcher matcher = pattern.matcher(text);
                            List<String> matcherStr = new ArrayList<>();
                            while (matcher.find()) matcherStr.add(matcher.group());
                            for (String str : matcherStr) jTextPane.setText(text.replace(str, toReplacedStr));
                            toCount += matcherStr.size();
                        }
                    }
                }
                progressTips("<html><font  style=\"color:blue\"> 替换目标计数: 已替换 " + toCount + " 匹配项</font ></html>");
            }
        });
        executeJButton.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(jFrame, "是否确认修改文件", "确认", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                int toCount = 0;
                for (TextEditingFileType fileType : pathList) {
                    String parentPath = fileType.getFilePath().toFile().getParent();
                    String newFileName = ((JTextPane) fileType.getRow().getComponent(1)).getText();
                    String oldPath = parentPath + File.separator + fileType.getFileName();
                    String newPath = parentPath + File.separator + newFileName;
                    Pattern pattern = Pattern.compile("[\\\\/:*?\"<>|]");
                    Matcher matcher = pattern.matcher(newFileName);
                    if (StringUtils.isBlank(newFileName) || matcher.find()) {
                        JOptionPane.showMessageDialog(jFrame, "文件名不允许为空\n不允许包含下列字符\n \t \\ / : * ? \" < > |", "提示", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    if (!oldPath.equals(newPath)) {
                        new File(oldPath).renameTo(new File(newPath));
                        ++toCount;
                        String fileNameJLabel = (parentPath + File.separator + ((JTextPane) fileType.getRow().getComponent(1)).getText()).replace(address.getText(), "");
                        ((JLabel) fileType.getRow().getComponent(0)).setText(fileNameJLabel);
                        fileType.setFileName(((JTextPane) fileType.getRow().getComponent(1)).getText());
                    }
                }
                progressTips("<html><font  style=\"color:rgb(0 128 0)\"> 重命名目标计数: 已修改 " + toCount + " 匹配项</font ></html>");
            }
        });
        insertCharNumberJButton.addActionListener(e -> {
            String example = pathList.stream().map(it -> ((JTextPane) it.getRow().getComponent(1)).getText()).max(Comparator.comparingInt(String::length)).get();
            // 组装自定义 对话框
            JDialog dialog = new JDialog(jFrame, "列编辑");
            dialog.setResizable(true);
            dialog.setSize(525, 295);
            dialog.setMinimumSize(new Dimension(525, 295));
            Container container = dialog.getContentPane();
            container.setLayout(new BorderLayout());
            container.add(new FileRenameInsertDialog(dialog, progress, pathList, example).getJpanel(), BorderLayout.CENTER);
            dialog.setModal(true);

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }

    /**
     * 创建表格标题
     *
     * @param editingFileType
     */
    public JPanel createRows(TextEditingFileType editingFileType) {
        GridBagLayout rowGBL = new GridBagLayout();
        GridBagConstraints rowGBC = new GridBagConstraints();
        JPanel row = new JPanel(rowGBL);
        row.setBorder(border);
        rowGBC.gridwidth = 1;
        rowGBC.weightx = 1;
        rowGBC.weighty = 0;
        rowGBC.fill = GridBagConstraints.HORIZONTAL;
        rowGBC.anchor = GridBagConstraints.NORTH;
        rowGBC.gridy = 0;
        // 文件
        JLabel jLabelName = new JLabel(editingFileType.getFilePath().toString().replace(address.getText(), ""), JLabel.LEFT);
        jLabelName.setPreferredSize(new Dimension(200, 30));
        jLabelName.setBorder(borderLR);
        jLabelName.setFont(font);
        rowGBC.gridx = 0;
        rowGBL.setConstraints(jLabelName, rowGBC);
        row.add(jLabelName);
        // 新文件名
        JTextPane jLabelNewName = new JTextPane();
        jLabelNewName.setMargin(new Insets(8, 3, 3, 3));
        jLabelNewName.setPreferredSize(new Dimension(200, 30));
        jLabelNewName.setFont(font);
        rowGBC.gridx = 1;
        jLabelNewName.setText(editingFileType.getNewFileName());
        rowGBL.setConstraints(jLabelNewName, rowGBC);
        row.add(jLabelNewName);
        // 类型
        JLabel jLabelType = new JLabel(editingFileType.getFileType(), JLabel.LEFT);
        jLabelType.setPreferredSize(new Dimension(10, 30));
        jLabelType.setBorder(borderLR);
        jLabelType.setFont(font);
        rowGBC.gridx = 2;
        rowGBL.setConstraints(jLabelType, rowGBC);
        row.add(jLabelType);
        // 大小(KB)
        JLabel jLabelSize = new JLabel(BusinessTool.fileSizeCalculation(editingFileType.getFileSize()), JLabel.LEFT);
        jLabelSize.setPreferredSize(new Dimension(10, 30));
        jLabelSize.setBorder(borderLR);
        jLabelSize.setFont(font);
        rowGBC.gridx = 3;
        rowGBL.setConstraints(jLabelSize, rowGBC);
        row.add(jLabelSize);
        editingFileType.setRow(row);
        return row;
    }

    /**
     * 占位扩展把内容顶到上面(使其不居中)
     *
     * @param fileTableGBL
     * @param tableFilesGBC
     */
    public void placeholder(GridBagLayout fileTableGBL, GridBagConstraints tableFilesGBC, int length) {
        JPanel jLabelName = new JPanel();
        tableFilesGBC.gridy = length + 2;
        tableFilesGBC.gridx = 0;
        tableFilesGBC.gridwidth = 3;
        tableFilesGBC.weightx = 1;
        tableFilesGBC.weighty = 90;
        tableFilesGBC.fill = GridBagConstraints.BOTH;
        tableFilesGBC.anchor = GridBagConstraints.NORTH;
        fileTableGBL.setConstraints(jLabelName, tableFilesGBC);
        centre.add(jLabelName);
    }

    /**
     * 进度文字提示
     */
    public void progressTips(String tips) {
        progress.setVisible(true);
        progress.setText(tips);
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
     * 获取目录下有多少文件
     *
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
     * 切换显示
     */
    public void visible() {
        centre.setVisible(false);
        centre.setVisible(true);
        centre.setVisible(false);
        centre.setVisible(true);
    }

}

