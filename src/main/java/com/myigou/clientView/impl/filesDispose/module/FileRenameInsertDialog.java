package com.myigou.clientView.impl.filesDispose.module;

import com.myigou.clientService.model.TextEditingFileType;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FileRenameInsertDialog {
    private JPanel jpanel;
    private JRadioButton insertTextJRadioButton;
    private JRadioButton insertNumberJRadioButton;
    private JTextField insertTextField;
    private JTextField initValueJTextField;
    private JTextField incrementValueJTextField;
    private JButton confirmJButton;
    private JButton cancelJButton;
    private JCheckBox supplementZeroJCheckBox;
    private JSlider characterBitsJSlider;
    private JTextField exampleInsertTextField;
    private JLabel messageJLabel;
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
        exampleInsertTextField.setText(example);
        confirmJButton.addActionListener(e -> {
            boolean textRadio = group.isSelected(insertTextJRadioButton.getModel());
            boolean numberRadio = group.isSelected(insertNumberJRadioButton.getModel());
            int toCount = 0;
            String text = insertTextField.getText();
            int incrementValue, initValue;
            try {
                incrementValue = Integer.parseInt(incrementValueJTextField.getText());
                initValue = Integer.parseInt(initValueJTextField.getText());
            } catch (NumberFormatException formatException) {
                incrementValue = 0;
                initValue = 0;
            }
            List<String> list = new ArrayList<>();
            for (int i = 0; i < pathList.size(); i++) {
                if (textRadio) {
                    if (StringUtils.isEmpty(text)) {
                        messageJLabel.setText("<html><font  style=\"color:red\"> 请填写插入文本</font ></html>");
                        return;
                    }
                    list.add(text);
                } else if (numberRadio) {
                    if (initValue == 0 && incrementValue == 0) {
                        messageJLabel.setText("<html><font  style=\"color:red\"> 请填写初始值和增量值</font ></html>");
                        return;
                    }
                    list.add(String.valueOf((initValue += incrementValue) - incrementValue));
                }
            }
            if (numberRadio && supplementZeroJCheckBox.isSelected()) {
                String lastly = list.get(list.size() - 1);
                for (int i = 0; i < list.size(); i++) {
                    int length = lastly.length() - list.get(i).length();
                    String complement = "";
                    for (int j = 0; j < length; j++) {
                        complement += "0";
                    }
                    list.set(i,complement + list.get(i));
                }
            }
            for (int i = 0; i < pathList.size(); i++) {
                String insertString = list.get(i);
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
            if (e.getActionCommand().equals("插入文本")) {
                insertTextField.setEnabled(true);
                initValueJTextField.setEnabled(false);
                incrementValueJTextField.setEnabled(false);
                supplementZeroJCheckBox.setEnabled(false);
            } else if (e.getActionCommand().equals("插入数字")) {
                insertTextField.setEnabled(false);
                initValueJTextField.setEnabled(true);
                incrementValueJTextField.setEnabled(true);
                supplementZeroJCheckBox.setEnabled(true);
            }
        };
        insertTextJRadioButton.addActionListener(radioListener);
        insertNumberJRadioButton.addActionListener(radioListener);
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
