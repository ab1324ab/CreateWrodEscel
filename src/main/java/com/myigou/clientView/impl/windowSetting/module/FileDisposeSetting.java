package com.myigou.clientView.impl.windowSetting.module;

import com.myigou.clientService.enums.ColorEnum;
import com.myigou.clientService.enums.FileDisposeShowMainEnum;
import com.myigou.tool.PropertiesTool;

import javax.swing.*;
import java.util.Enumeration;
import java.util.Map;

public class FileDisposeSetting {
    private JPanel jpanel;
    private JRadioButton 文件归集RadioButton;
    private JRadioButton 文件搜索RadioButton;
    private JRadioButton 批量文件重命名RadioButton;
    private JRadioButton 创建文件夹RadioButton;

    public JPanel getJpanel() {
        return jpanel;
    }

    public FileDisposeSetting() {
    }

    public FileDisposeSetting(JFrame jFrame) {
        Map<String, String> contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        ButtonGroup group = new ButtonGroup();
        group.add(文件归集RadioButton);
        group.add(文件搜索RadioButton);
        group.add(批量文件重命名RadioButton);
        group.add(创建文件夹RadioButton);
        Enumeration<AbstractButton> elements = group.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton btn = elements.nextElement();
            String btnName = FileDisposeShowMainEnum.getName(contentMap.get("fileDispose.showMain"));
            if (btnName.equals(btn.getText())) btn.setSelected(true);
            btn.addActionListener(e -> {
                String value = FileDisposeShowMainEnum.getKeyValue(btn.getText());
                PropertiesTool.writeSet(PropertiesTool.CONFIG_FILE, "fileDispose.showMain", value);
            });
        }
    }
}
