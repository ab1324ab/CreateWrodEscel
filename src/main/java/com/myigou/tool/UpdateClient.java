package com.myigou.tool;

import com.myigou.mainView.WindowView;
import org.apache.commons.lang.StringUtils;

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
        if (version.indexOf("error") != -1) {
            JOptionPane.showMessageDialog(clientPanel, "当前管理员禁止使用 ! ", "提示", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else if (!WindowView.VERSION_THIS.equals(version)) {
            int flag = JOptionPane.showConfirmDialog(clientPanel, "有新版本 "+version+" 是否更新?" , "更新", JOptionPane.YES_NO_OPTION);
            // JFrame jFrameGenx=new JFrame("dd");
            // jFrameGenx.setSize(300,100);
            // conter(jFrameGenx);
            // String s= "<html><a href=\"http://www.baidu.com\">点击下载</a></html>";
            // jFrameGenx.add(new JLabel(s,JLabel.CENTER));
            // jFrameGenx.setVisible(true);
        }

    }


}
