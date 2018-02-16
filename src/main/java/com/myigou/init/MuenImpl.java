package com.myigou.init;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ab1324ab on 2017/6/29.
 */
public class MuenImpl  {
    protected JMenuItem documentFile=null;//创建文本编辑

    public void MuenImpl(){
        documentFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ffffffffffffff");
                JOptionPane.showMessageDialog(null, "保存成功！", "提示",JOptionPane.WARNING_MESSAGE);
            }
        });

    }
}
