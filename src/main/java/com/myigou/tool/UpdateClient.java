package com.myigou.tool;

import com.myigou.view.WindowView;

import javax.swing.*;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/6/27.
 */
public class UpdateClient {

    /**
     * 更新程序
     *
     * @param clientPanel
     */
    public static void updateInstall(JFrame clientPanel, String version) {
        if ("error".equals(version)) {
            JOptionPane.showMessageDialog(clientPanel, "当前管理员禁止使用 ! ", "提示", JOptionPane.QUESTION_MESSAGE);
            System.exit(0);
        } else if (!WindowView.VERSION_THIS.equals(version) && !"".equals(version)) {
            JOptionPane.showMessageDialog(clientPanel, "当前版本有更新" + version, "提示", JOptionPane.WARNING_MESSAGE);
            // JFrame jFrameGenx=new JFrame("dd");
            // jFrameGenx.setSize(300,100);
            // conter(jFrameGenx);
            // String s= "<html><a href=\"http://www.baidu.com\">点击下载</a></html>";
            // jFrameGenx.add(new JLabel(s,JLabel.CENTER));
            // jFrameGenx.setVisible(true);
        }

    }


}
