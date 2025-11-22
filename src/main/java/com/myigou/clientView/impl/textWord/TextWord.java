package com.myigou.clientView.impl.textWord;

import com.myigou.clientView.FunctionInter;
import com.myigou.clientView.impl.textWord.model.filter.TXTfilter;
import com.myigou.clientView.impl.textWord.tool.TextTool;
import com.myigou.mainView.WindowView;
import com.myigou.tool.FilesTool;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 文件文本操作
 * 2023年2月18日15点31分
 * @author hawk
 */
public class TextWord implements FunctionInter {
    private JPanel title;
    private JPanel jpanel;
    private JPanel bottomJPanel;
    private JPanel rightJPanel;
    private JTextArea contentJTextArea;
    private JScrollPane jscrollpane;
    private JLabel lengthsJLabel;
    private JLabel linesJLabel;
    private JLabel progressJLabel;
    private JPanel progressJPanel;
    private JProgressBar progressBar;
    private JLabel charsetJLabel;
    private JLabel filenameJLabel;
    private JPanel rowNumJPanel;
    private JLabel leJLabel;
    private JLabel liJLbel;
    private JLabel colLeJLabel;
    private JLabel colLiJLabel;

    private static String jTextPaneText = "";
    private static String directoryURL = "";
    private static String status = "";
    private static String JTextPaneSelectedText = "";
    private static Box vBox = Box.createVerticalBox();

    public TextWord() {
        charsetJLabel.setText(Charset.defaultCharset().name());
        status = "new_txt";
        directoryURL = "";
        vBox.removeAll();
    }

    public TextWord(WindowView windowView, String filStatus) {
        status = filStatus;
        if (status.equals("open_txt")) {
            JFileChooser chooser = new JFileChooser("");
            // 设置只能选择文件
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("文本(.txt)", "txt");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(windowView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                directoryURL = chooser.getSelectedFile().getPath();
                filenameJLabel.setText("【" + getFileName() + "】");
            }
        }
    }

    /**
     * 文档文本保存*
     */
    public String saveFileTxt(WindowView windowView) {
        String content = contentJTextArea.getText();
        String savePath = "";
        if (StringUtils.isNotEmpty(directoryURL)) {
            savePath = directoryURL;
        } else {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new TXTfilter());
            chooser.setSelectedFile(new File("新建 文本文档.txt"));
            int ch = chooser.showDialog(windowView, "保存文本");
            if (ch == JFileChooser.APPROVE_OPTION) {
                File file = new File(chooser.getSelectedFile().getPath());
                String fileName = TextTool.createUniqueFileName(file.getParent(), file.getName());
                directoryURL = savePath = file.getParent() + File.separator + fileName;
            }
        }
        if (StringUtils.isNotEmpty(savePath)) {
            OutputStreamWriter fos = null;
            OutputStream os = null;
            try {
                os = new FileOutputStream(savePath);
                fos = new OutputStreamWriter(os, charsetJLabel.getText());
                fos.write(content);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (os != null && fos != null) {
                    try {
                        fos.close();
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        filenameJLabel.setText("【" + getFileName() + "】");
        return content;
    }

    /**
     * 文档文本另保存*
     */
    public String asSaveFileTxt(WindowView windowView) {
        String content = contentJTextArea.getText();
        String savePath = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new TXTfilter());
        chooser.setSelectedFile(new File(getFileName()));
        int ch = chooser.showDialog(windowView, "另存文本");
        if (ch == JFileChooser.APPROVE_OPTION) {
            File file = new File(chooser.getSelectedFile().getPath());
            String fileName = TextTool.createUniqueFileName(file.getParent(), file.getName());
            directoryURL = savePath = file.getParent() + File.separator + fileName;
            OutputStreamWriter fos = null;
            OutputStream os = null;
            try {
                os = new FileOutputStream(savePath);
                fos = new OutputStreamWriter(os, charsetJLabel.getText());
                fos.write(content);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (os != null && fos != null) {
                    try {
                        fos.close();
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return content;
    }

    /**
     * 重新给JTextArea赋值*
     */
    public void anewAssignment(String content) {
        filenameJLabel.setText("【" + getFileName() + "】");
        contentJTextArea.setText(content);
    }

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jpanel, BorderLayout.CENTER);
        jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
        rowNumJPanel.add(vBox);
        setRowNumJPanel(vBox.getComponentCount() + 1);
        contentJTextArea.setText("");
        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                setOpenTextPane();
            }
        }, 1000);//五百毫秒
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame, Font font) {
        title.getComponent(0).setFont(font);
        jPanel.add(title);
        return jPanel;
    }

    public void setOpenTextPane() {
        if (StringUtils.isBlank(directoryURL)) status = "edit_txt";
        if ("open_txt".equals(status)) {
            InputStreamReader fis = null;
            try {
                String charsetName = FilesTool.getFileCharsetName(directoryURL);
                charsetJLabel.setText(charsetName);
                InputStream is = new FileInputStream(directoryURL);
                fis = new InputStreamReader(is, charsetName);

                File file = new File(directoryURL);
                char[] bytes = new char[512];
                int length;
                long progress = 0;
                StringBuilder stringBuilder = new StringBuilder();
                // 添加进度改变通知
                progressBar.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
//                        System.out.println("当前进度值: " + progressBar.getValue() + "; " + "进度百分比: " + progressBar.getPercentComplete());
                        if (progressBar.getValue() == 100) {
                            contentJTextArea.setText(stringBuilder.toString());
                            long enterCount = stringBuilder.toString().split("\n").length;
                            for (int i = 1; i < enterCount; i++)
                                setRowNumJPanel(vBox.getComponentCount() + 1);
                            setRowNumJPanelVisible();
                        }
                    }
                });
                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    stringBuilder.append(bytes, 0, length);
                    progress += length;
                    progressBar.setValue((int) (100 * progress / file.length()));
                }
                progressBar.setValue(100);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRowNumJPanel(int rownum) {
        JPanel rowJPanel = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel(rownum + "", JLabel.CENTER);
        jLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jLabel.setForeground(new Color(131, 131, 131));
        rowJPanel.add(jLabel);
        vBox.add(rowJPanel);
    }

    // 刷新行号
    public void setRowNumJPanelVisible() {
        rowNumJPanel.setVisible(false);
        rowNumJPanel.setVisible(true);
    }

    /**
     * 统计字符长度以及行数*
     * @param ex
     */
    public void setRowsCount(DocumentEvent ex) {
        try {
            Document document = ex.getDocument();
            jTextPaneText = document.getText(0, document.getLength());
            lengthsJLabel.setText(jTextPaneText.length() + "");
            if (jTextPaneText.length() == 0) {
                vBox.removeAll();
                setRowNumJPanel(vBox.getComponentCount() + 1);
            }
            linesJLabel.setText(jTextPaneText.split("\n").length + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取打开的文件的文件名称
    public String getFileName() {
        if (StringUtils.isEmpty(directoryURL)) return "新建 文本文档.txt";
        String[] pStr = null;
        if (directoryURL.split("//").length > 1) pStr = directoryURL.split("//");
        else if (directoryURL.split("\\\\").length > 1) pStr = directoryURL.split("\\\\");
        return pStr[pStr.length - 1];
    }

    private void createUIComponents() {
        contentJTextArea = new JTextArea();
        contentJTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Document document = e.getDocument();
                try {
                    setRowsCount(e); // 统计字符长度以及行数
                    if (!"open_txt".equals(status)) {
                        if (StringUtils.isNotEmpty(JTextPaneSelectedText)) {
                            int componentCount = vBox.getComponentCount();
                            if (componentCount == 1) {
                                JTextPaneSelectedText = "";
                            } else {
                                long enterCount = Arrays.stream(JTextPaneSelectedText.split("")).filter(it -> it.equals("\n")).count();
                                for (int i = 0; i < enterCount; i++) {
                                    componentCount = vBox.getComponentCount();
                                    Component component = vBox.getComponent(componentCount - 1);
                                    vBox.remove(component);
                                }
                                JTextPaneSelectedText = "";
                            }
                        }
                        String text = document.getText(e.getOffset(), e.getLength());
                        long enterCount = text.split("\n").length - 1;
                        if (text.equals("\n")) enterCount = 1;
                        for (int i = 0; i < enterCount; i++)
                            setRowNumJPanel(vBox.getComponentCount() + 1);
                        setRowNumJPanelVisible();
//                        System.out.println("insertUpdate no open");
                    }
                    filenameJLabel.setText("【" + getFileName() + "】");
//                    System.out.println("insertUpdate");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//                System.out.println("removeUpdate");
                String text = jTextPaneText.substring(e.getOffset(), e.getOffset() + e.getLength());
                setRowsCount(e); // 统计字符长度以及行数
                int componentCount = vBox.getComponentCount();
                if (componentCount == 1) return;
                long enterCount = Arrays.stream(text.split("")).filter(it -> it.equals("\n")).count();
                for (int i = 0; i < enterCount; i++) {
                    componentCount = vBox.getComponentCount();
                    Component component = vBox.getComponent(componentCount - 1);
                    vBox.remove(component);
                }
                JTextPaneSelectedText = "";
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        contentJTextArea.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                try {
                    int offset = e.getDot();
                    int row = contentJTextArea.getLineOfOffset(offset);
                    int column = e.getDot() - contentJTextArea.getLineStartOffset(row);
                    liJLbel.setText((row + 1) + "");
                    leJLabel.setText((column + 1) + "");
                    if (contentJTextArea.getSelectedText() != null) {
                        String jText = contentJTextArea.getSelectedText();
                        JTextPaneSelectedText = jText;
                        colLeJLabel.setText(jText.length() + "");
                        colLiJLabel.setText(jText.split("\n").length + "");
                    } else {
                        colLeJLabel.setText("0");
                        colLiJLabel.setText("0");
                    }
                    filenameJLabel.setText("【" + getFileName() + " *】");
                    status = "edit_txt";
//                    System.out.println("caretUpdate");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
