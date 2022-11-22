package com.myigou.clientView.impl.windowSetting.module;

import com.myigou.clientService.enums.FileDisposeShowMainEnum;
import com.myigou.tool.PropertiesTool;

import javax.swing.*;
import java.util.Enumeration;
import java.util.Map;

public class FileDisposeSetting {
    private JPanel jpanel;
    private JRadioButton filesCollectRadioButton;
    private JRadioButton fileSearchRadioButton;
    private JRadioButton fileRenameRadioButton;
    private JRadioButton createDirRadioButton;

    public JPanel getJpanel() {
        return jpanel;
    }

    public FileDisposeSetting() {
    }

    public FileDisposeSetting(JFrame jFrame) {
        Map<String, String> contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        ButtonGroup group = new ButtonGroup();
        group.add(filesCollectRadioButton);
        group.add(fileSearchRadioButton);
        group.add(fileRenameRadioButton);
        group.add(createDirRadioButton);
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
