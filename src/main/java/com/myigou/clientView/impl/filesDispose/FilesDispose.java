package com.myigou.clientView.impl.filesDispose;

import com.myigou.clientService.enums.FileDisposeShowMainEnum;
import com.myigou.clientView.FunctionInter;
import com.myigou.clientView.impl.filesDispose.module.FileRename;
import com.myigou.clientView.impl.filesDispose.module.FileSearch;
import com.myigou.clientView.impl.filesDispose.module.FilesCollect;
import com.myigou.tool.PropertiesTool;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class FilesDispose implements FunctionInter {

    private JPanel jpanel;
    private JPanel title;
    private JPanel content;
    private JButton fileCollectionMenu;
    private JButton fileSearchMenu;
    private JButton fileBatchRenameMenu;
    private JButton 创建文件夹Button;

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jpanel,BorderLayout.CENTER);
        Map<String, String> contentMap = PropertiesTool.redConfigFile(PropertiesTool.CONFIG_FILE);
        // 文件收集
        fileCollectionMenu.addActionListener(e -> showContentJPanel(new FilesCollect(jFrame).getJpanel()));
        // 文件搜索
        fileSearchMenu.addActionListener(e -> showContentJPanel(new FileSearch(jFrame).getJpanel()));
        // 批量文件重命名
        fileBatchRenameMenu.addActionListener(e -> showContentJPanel(new FileRename(jFrame).getJpanel()));

        String btn = FileDisposeShowMainEnum.getName(contentMap.get("fileDispose.showMain"));
        if(fileCollectionMenu.getText().equals(btn)) fileCollectionMenu.doClick();
        if(fileSearchMenu.getText().equals(btn)) fileSearchMenu.doClick();
        if(fileBatchRenameMenu.getText().equals(btn)) fileBatchRenameMenu.doClick();
        if(创建文件夹Button.getText().equals(btn)) 创建文件夹Button.doClick();
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame, Font font) {
        title.getComponent(0).setFont(font);
        jPanel.add(title);
        return jPanel;
    }

    /**
     * 显示切换面板
     * @param jPanel
     */
    public void showContentJPanel(JPanel jPanel){
        content.removeAll();
        content.add(jPanel);
        content.setVisible(false);
        content.setVisible(true);
        content.setVisible(false);
        content.setVisible(true);
    }
}
