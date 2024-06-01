package com.myigou.clientView.impl.filesDispose.module;

import com.myigou.clientService.model.TextEditingFileType;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FileRenameInsertDialog {
    private JPanel jpanel;
    private JRadioButton insertTextJRadioButton;
    private JRadioButton insertNumberJRadioButton;
    private JRadioButton insertAttributeJRadioButton;
    private JTextField insertTextField;
    private JTextField initValueJTextField;
    private JTextField incrementValueJTextField;
    private JButton confirmJButton;
    private JButton cancelJButton;
    private JCheckBox supplementZeroJCheckBox;
    private JSlider characterBitsJSlider;
    private JTextField exampleInsertTextField;
    private JLabel messageJLabel;
    private JCheckBox hoursMinutesSecondsJCheckBox;
    private JRadioButton createTimeJRadioButton;
    private JRadioButton modifyTimeJRadioButton;
    private JRadioButton accessTimeJRadioButton;
    // 模拟光标定时器
    private TimerTask displayCursorRunnable = null;
    private TimerTask hideCursorRunnable = null;

    public JPanel getJpanel() {
        return jpanel;
    }

    public FileRenameInsertDialog() {
    }

    public FileRenameInsertDialog(JDialog jDialog, JLabel progress, List<TextEditingFileType> pathList, String example) {
        ButtonGroup group = new ButtonGroup();
        group.add(insertTextJRadioButton);
        group.add(insertNumberJRadioButton);
        group.add(insertAttributeJRadioButton);
        ButtonGroup CMAGroup = new ButtonGroup();
        CMAGroup.add(createTimeJRadioButton);
        CMAGroup.add(modifyTimeJRadioButton);
        CMAGroup.add(accessTimeJRadioButton);
        exampleInsertTextField.setText(example);
        confirmJButton.addActionListener(e -> {
            boolean textRadio = group.isSelected(insertTextJRadioButton.getModel());
            boolean numberRadio = group.isSelected(insertNumberJRadioButton.getModel());
            boolean attributeRadio = group.isSelected(insertAttributeJRadioButton.getModel());
            int toCount = 0;
            int incrementValue, initValue, cumulativeValue;
            try {
                incrementValue = Integer.parseInt(incrementValueJTextField.getText());
                initValue = Integer.parseInt(initValueJTextField.getText());
            } catch (NumberFormatException formatException) {
                incrementValue = 0;
                initValue = 0;
            }
            cumulativeValue = initValue;
            for (int i = 0; i < pathList.size(); i++) {
                StringBuilder insertString = new StringBuilder();
                if (textRadio) {
                    insertString = new StringBuilder(insertTextField.getText());
                    if (StringUtils.isEmpty(insertString.toString())) {
                        messageJLabel.setText("<html><font  style=\"color:red\"> 请填写插入文本</font ></html>");
                        return;
                    }
                } else if (numberRadio) {
                    cumulativeValue += incrementValue;
                    insertString = new StringBuilder(String.valueOf(cumulativeValue));
                    if (initValue == 0 && incrementValue == 0) {
                        messageJLabel.setText("<html><font  style=\"color:red\"> 请填写初始值和增量值</font ></html>");
                        return;
                    }
                    if (supplementZeroJCheckBox.isSelected()) {
                        int pathLength = String.valueOf(pathList.size() + initValue).split("").length;
                        for (int j = 0; j < pathLength - insertString.length(); j++) {
                            insertString.insert(0, "0");
                        }
                    }
                } else if (attributeRadio) {
                    String actionCommand = CMAGroup.getSelection().getActionCommand();
                    try {
                        BasicFileAttributes basicFileAttributes = Files.readAttributes(pathList.get(i).getFilePath(), BasicFileAttributes.class);
                        SimpleDateFormat dateFormat;
                        FileTime fileTime = null;
                        if ("创建时间".equals(actionCommand)) fileTime = basicFileAttributes.creationTime();
                        else if ("修改时间".equals(actionCommand)) fileTime = basicFileAttributes.lastModifiedTime();
                        else if ("访问时间".equals(actionCommand)) fileTime = basicFileAttributes.lastAccessTime();
                        if (hoursMinutesSecondsJCheckBox.isSelected()) dateFormat = new SimpleDateFormat("yyyy-MM-dd HH时mm分");
                        else dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        insertString = new StringBuilder(dateFormat.format(fileTime.toMillis()));
                        insertString = new StringBuilder("（" + insertString + "）");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                JPanel jPanel = pathList.get(i).getRow();
                JTextPane jTextPane = ((JTextPane) jPanel.getComponent(1));
                if (characterBitsJSlider.getValue() <= jTextPane.getText().length()) {
                    StringBuilder stringBuilder = new StringBuilder(jTextPane.getText());
                    stringBuilder.insert(characterBitsJSlider.getValue(), insertString);
                    jTextPane.setText(stringBuilder.toString());
                    toCount++;
                }
            }
            progress.setText("<html><font  style=\"color:rgb(0 128 0)\"> 插入目标计数: 已插入 " + toCount + " 匹配项</font ></html>");
            progress.setVisible(false);
            progress.setVisible(true);
            jDialog.setVisible(false);
        });
        cancelJButton.addActionListener(e -> {
            jDialog.setVisible(false);
        });
        ActionListener radioListener = e -> {
            insertTextField.setEnabled(false);
            initValueJTextField.setEnabled(false);
            incrementValueJTextField.setEnabled(false);
            supplementZeroJCheckBox.setEnabled(false);
            hoursMinutesSecondsJCheckBox.setEnabled(false);
            createTimeJRadioButton.setEnabled(false);
            modifyTimeJRadioButton.setEnabled(false);
            accessTimeJRadioButton.setEnabled(false);
            if (e.getActionCommand().equals("插入文本")) {
                insertTextField.setEnabled(true);
            } else if (e.getActionCommand().equals("插入数字")) {
                initValueJTextField.setEnabled(true);
                incrementValueJTextField.setEnabled(true);
                supplementZeroJCheckBox.setEnabled(true);
            } else if (e.getActionCommand().equals("插入属性")) {
                hoursMinutesSecondsJCheckBox.setEnabled(true);
                createTimeJRadioButton.setEnabled(true);
                modifyTimeJRadioButton.setEnabled(true);
                accessTimeJRadioButton.setEnabled(true);
            }
        };
        insertTextJRadioButton.addActionListener(radioListener);
        insertNumberJRadioButton.addActionListener(radioListener);
        insertAttributeJRadioButton.addActionListener(radioListener);
        // 模拟输入光标
        enterCursor(example);
        int majorTickSpacing = 1;
        if (example.length() >= 20) majorTickSpacing = 5;
        if (example.length() >= 50) majorTickSpacing = 10;
        characterBitsJSlider.setMajorTickSpacing(majorTickSpacing);
        characterBitsJSlider.setMaximum(example.length());
        characterBitsJSlider.addChangeListener(e -> {
            enterCursor(example);
        });
    }

    /**
     * 模拟输入光标演示示例
     *
     * @param example
     */
    public void enterCursor(String example) {
        Timer timer = new Timer();
        if (hideCursorRunnable != null) hideCursorRunnable.cancel();
        hideCursorRunnable = new TimerTask() {
            @Override
            public void run() {
                exampleInsertTextField.setText(exampleInsertTextField.getText().replace("|", " "));
            }
        };
        timer.schedule(hideCursorRunnable, 0, 1000);
        if (displayCursorRunnable != null) displayCursorRunnable.cancel();
        displayCursorRunnable = new TimerTask() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder(example);
                stringBuilder.insert(characterBitsJSlider.getValue(), "|");
                exampleInsertTextField.setText(stringBuilder.toString());
            }
        };
        timer.schedule(displayCursorRunnable, 500, 1000);
    }
}
