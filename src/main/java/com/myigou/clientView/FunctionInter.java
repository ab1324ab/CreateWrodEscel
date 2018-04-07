package com.myigou.clientView;

import javax.swing.*;
import java.awt.*;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/6/25.
 */
public interface FunctionInter {

    /**
     * 获取功能
     *
     * @return
     */
    JPanel getFunction(JPanel jPanel, JFrame jFrame);

    /**
     * 获取标题
     *
     * @param jPanel
     * @return
     */
    JPanel getTitle(JPanel jPanel, JFrame jFrame,Font font);
}
