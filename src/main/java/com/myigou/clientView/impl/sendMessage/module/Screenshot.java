package com.myigou.clientView.impl.sendMessage.module;

import com.myigou.clientView.impl.sendMessage.model.BrushLine;
import com.myigou.clientView.impl.sendMessage.model.filter.BMPfilter;
import com.myigou.clientView.impl.sendMessage.model.filter.GIFfilter;
import com.myigou.clientView.impl.sendMessage.model.filter.JPGfilter;
import com.myigou.clientView.impl.sendMessage.model.filter.PNGfilter;
import com.myigou.tool.ImageIconTool;
import com.myigou.tool.WindowTool;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * * 截图工具
 * 2022年12月5日18点48分*
 * @author hawk*
 */
public class Screenshot {

    private JPanel beijing;
    private JPanel right, top, left, bottom;// 上下左右面板
    private JPanel controlPanel;
    private JButton doCopyButton, doSaveButton, closeButton, ctrlZButton;// 截图操作按钮
    private JRadioButton mindian, defaultdian, maxdian;// 字体线条粗细单选框
    private JRadioButton ashkuang, bluekuang, greenkuang, redkuang, whitekuang, yellowkuang; // 颜色单选框
    private JButton boxButton, circleButton, brushButton, textButton;// 形状画图按钮
    private ButtonGroup groupsize, groupcolor;// 单选按钮组
    private int startX, startY, endX, endY;// 框选移动位置
    private int startXTemp, startYTemp, endXTemp, endYTemp;// 临时存储移动位置
    private int smoveX, smoveY, emoveX, emoveY;// 移动的位置
    private int boxStartX, boxStartY, boxEndX, boxEndY; // 画笔画框的大小
    private boolean topMove = false, leftMove = false;
    private boolean move = false;
    private Dimension screenSize;
    private BufferedImage bufImage;
    private boolean isShowTip = true;//是否显示提示.如果鼠标左键一按,则提示就不再显示了
    private int isShowTipCount = 0;// 显示提示计数
    private Point showTipPoint = new Point();
    private boolean isbBox = false, isCircle = false, isBrush = false, isText = false, isCtrlZ = false;
    private JPanel frameSelection;
    private int mouseEvent = 0;
    private List<List<Object>> boxArr = new ArrayList<>();// 保存矩形数组
    private List<List<Object>> circleArr = new ArrayList<>();// 保存圆形数组
    private List<BrushLine> brushArr = new ArrayList<>(); // 保存画笔画形
    private List<Map<String, Object>> textArr = new ArrayList<>(); // 保存画笔画形
    private List<Point> brush = null; // 当前画笔画形
    private List<Object> ctrlZList = new ArrayList<>();
    boolean[] clickedMoveStatus = {false, false, false, false};// 按下/移动/抬起/点击
    private int[] frameSelectionArea = {0, 0}; // 选择框的面积0w 1h
    private String paintbrush = "";

    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private JPanel toptext, lefttext, righttext, buttomtext;

    public static int width = 800;
    public static int height = 500;
    private JFrame superFrame;

    public JPanel getBeijing() {
        return beijing;
    }

    public Screenshot(JFrame jFrame, JFrame superFrame) {
        this.superFrame = superFrame;
        addButtonGroup();
        // 获取屏幕尺寸
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
        // 创建需要截取的矩形区域
        Rectangle rect = new Rectangle(0, 0, screenSize.width, screenSize.height);
        try {
            bufImage = new Robot().createScreenCapture(rect);// 截屏操作
        } catch (AWTException e) {
            e.printStackTrace();
        }
        beijing.add(createSelectionPanel(jFrame));
        boxButton.addActionListener(e -> {
            restoreReleasedDragged();
            isbBox = true;
            paintbrush = "box";
            controlPanel.setSize(new Dimension(controlPanel.getWidth(), 80));
            ctrlZButton.setEnabled(true);
            ctrlZButton.requestFocus(true);
        });
        circleButton.addActionListener(e -> {
            restoreReleasedDragged();
            isCircle = true;
            paintbrush = "circle";
            controlPanel.setSize(new Dimension(controlPanel.getWidth(), 80));
            ctrlZButton.setEnabled(true);
            ctrlZButton.requestFocus(true);
        });
        brushButton.addActionListener(e -> {
            restoreReleasedDragged();
            isBrush = true;
            paintbrush = "brush";
            controlPanel.setSize(new Dimension(controlPanel.getWidth(), 80));
            ctrlZButton.setEnabled(true);
            ctrlZButton.requestFocus(true);
        });
        textButton.addActionListener(e -> {
            restoreReleasedDragged();
            isText = true;
            paintbrush = "text";
            controlPanel.setSize(new Dimension(controlPanel.getWidth(), 80));
            ctrlZButton.setEnabled(true);
            ctrlZButton.requestFocus(true);
        });
        ctrlZButton.addActionListener(e -> ctrlZClear());
        closeButton.addActionListener(e -> {
            jFrame.dispose();
            superFrame.setVisible(true);
        });
        doSaveButton.addActionListener(e -> doSaveBufImage(jFrame));
        doCopyButton.addActionListener(e -> doCopyBufImage(jFrame));
    }

    // 判断画笔状态
    public boolean isPaintComponent() {
        return isCtrlZ || isText || isBrush || isCircle || isbBox;
    }

    // 判断画笔状态
    public boolean isReleasedDragged() {
        return !isbBox && !isCircle && !isBrush && !isText;
    }

    // 还原画笔状态
    public void restoreReleasedDragged() {
        isText = false;
        isBrush = false;
        isCircle = false;
        isbBox = false;
    }

    /**
     * 框选选中面板*
     * @param jFrame
     * @return
     */
    public JPanel createSelectionPanel(JFrame jFrame) {
        // 创建选择帧
        frameSelection = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
//                System.out.println("正在绘画....paintComponent = " + (clickedMoveStatus[0] && clickedMoveStatus[1] || isPaintComponent()));
                if (clickedMoveStatus[0] && clickedMoveStatus[1] || isPaintComponent()) {
                    g.setColor(Color.green);
                    for (int i = 0; i < 2; i++) {
                        g.drawLine(startX, startY - i, endX, startY - i);
                        g.drawLine(startX, endY - i, endX, endY - i);
                        g.drawLine(startX + i, startY, startX + i, endY);
                        g.drawLine(endX - i, startY, endX - i, endY);
                    }
                    g.fillRect(startX - 5, startY - 5, 10, 10); // 上线的 起点描点
                    g.fillRect((startX + endX) / 2 - 5, startY - 5, 10, 10); // 上线的 中间描点
                    g.fillRect(endX - 5, startY - 5, 10, 10); // 上线的 终点描点

                    g.fillRect(startX - 5, endY - 5, 10, 10); // 下线的 起点描点
                    g.fillRect((startX + endX) / 2 - 5, endY - 5, 10, 10); // 下线的 中间描点
                    g.fillRect(endX - 5, endY - 5, 10, 10); // 下线的 终点描点

                    g.fillRect(startX - 5, (startY + endY) / 2 - 5, 10, 10); // 右线的 中间描点
                    g.fillRect(endX - 5, (startY + endY) / 2 - 5, 10, 10); // 左线的 中间描点
                    boxWrite(g); // 画出存量图形
                    Integer lineSize;
                    if (paintbrush.equals("box")) {
//                        System.out.println("box画笔 boxStartX = " + boxStartX + ", boxStartY = " + boxStartY + ", boxEndX = " + boxEndX + ", boxEndY = " + boxEndY);
                        g.setColor(Color.decode(groupcolor.getSelection().getActionCommand())); //设置颜色
                        lineSize = Integer.parseInt(groupsize.getSelection().getActionCommand());
                        BasicStroke stokeLine = new BasicStroke(1.0f);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(stokeLine);
                        for (int i = 0; i < lineSize; i++) {
                            g.drawLine(boxStartX, boxStartY - i, boxEndX, boxStartY - i);
                            g.drawLine(boxStartX, boxEndY - i, boxEndX, boxEndY - i);
                            g.drawLine(boxStartX + i, boxStartY, boxStartX + i, boxEndY);
                            g.drawLine(boxEndX - i, boxStartY, boxEndX - i, boxEndY);
                        }
                    } else if (paintbrush.equals("circle")) {
                        g.setColor(Color.decode(groupcolor.getSelection().getActionCommand())); //设置颜色
                        lineSize = Integer.parseInt(groupsize.getSelection().getActionCommand());
                        BasicStroke stokeLine = new BasicStroke(2.0f);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(stokeLine);
                        for (int i = 0; i < lineSize; i++) {
                            if (boxStartX < boxEndX)
                                g.drawOval(boxStartX + i, boxStartY + i, (boxEndX - i * 2) - boxStartX, (boxEndY - i * 2) - boxStartY);
                            if (boxStartX > boxEndX)
                                g.drawOval(boxEndX + i, boxStartY + i, (boxStartX - i * 2) - boxEndX, (boxEndY - i * 2) - boxStartY);
                            if (boxEndY < boxStartY)
                                g.drawOval(boxStartX + i, boxEndY + i, (boxEndX - i * 2) - boxStartX, (boxStartY - i * 2) - boxEndY);
                            if (boxStartY > boxEndY && boxStartX > boxEndX)
                                g.drawOval(boxEndX + i, boxEndY + i, (boxStartX - i * 2) - boxEndX, (boxStartY - i * 2) - boxEndY);
                        }
                    } else if (paintbrush.equals("brush")) {
                        g.setColor(Color.decode(groupcolor.getSelection().getActionCommand())); //设置颜色
                        lineSize = Integer.parseInt(groupsize.getSelection().getActionCommand());
                        BasicStroke stokeLine = new BasicStroke(lineSize);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(stokeLine);
                        if (brush.size() >= 2) {
                            for (int i = 1; i < brush.size(); i++) {
                                g.drawLine(brush.get(i - 1).x, brush.get(i - 1).y, brush.get(i).x, brush.get(i).y);
                            }
                        }
                    } else if (paintbrush.equals("text")) {
                        // TODO NULL
                    }
                }
                if (!topMove && !leftMove) bottomRight();
                if (topMove && !leftMove) topLeft();
                if (!topMove && leftMove) bottomLeft();
                if (topMove && leftMove) topRight();
                if (endX > startX)
                    controlPanel.setBounds(endX - controlPanel.getWidth(), 0, controlPanel.getWidth(), controlPanel.getHeight());
                else
                    controlPanel.setBounds(startX - controlPanel.getWidth(), 0, controlPanel.getWidth(), controlPanel.getHeight());
//                System.out.println("提示测试 " + isShowTip);
                if (isShowTip) {
                    int height = 30;
                    if (isShowTipCount == 2) height = 50;
                    g.setColor(Color.WHITE);
                    g.fillRect(showTipPoint.x + 10, showTipPoint.y + 25, 230, height);
                    g.setColor(new Color(62, 62, 62));
                    g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawRoundRect(showTipPoint.x + 10, showTipPoint.y + 25, 230, height, 15, 15);
                    g.drawString("按住鼠标左键选择截图区", showTipPoint.x + 15, showTipPoint.y + 47);
                    if (isShowTipCount == 2)
                        g.drawString("再按一次右键退出截图", showTipPoint.x + 15, showTipPoint.y + 67);
                }
            }
        };
        frameSelection.setOpaque(false);
        bottom.setBackground(new Color(0, 0, 0, 0));
        controlPanel.setBackground(new Color(0, 0, 0, 0));
        bottom.setOpaque(false);
        frameSelection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println("点击 ex = " + e.getX() + " ey =" + e.getY());
                if (e.getButton() == MouseEvent.BUTTON3) {
//                    System.out.println("移动 " + startX + "," + startY + "," + endX + "," + endY);
                    if ("true".equals(controlPanel.getName())) {
                        beijing.remove(frameSelection);
                        bottom.setBackground(new Color(0, 0, 0, 0));
                        beijing.setVisible(false);
                        beijing.setVisible(true);
                        beijing.setVisible(false);
                        beijing.setVisible(true);
                        startX = 0;
                        startY = 0;
                        endX = 0;
                        endY = 0;
                        beijing.add(createSelectionPanel(jFrame));
                        controlPanel.setName("false");
                        move = false;
                        restoreReleasedDragged(); // 还原画笔状态
                        boxArr.clear();
                        circleArr.clear();
                        textArr.clear();
                        brushArr.clear();
                        if (brush != null) brush.clear();
                        boxStartX = 0;
                        boxStartY = 0;
                        boxEndX = 0;
                        boxEndY = 0;
                        isShowTip = true;
                        isShowTipCount = 2;
                    } else {
                        jFrame.dispose();
                        superFrame.setVisible(true);
                    }
                }
//                System.out.println("点击 次数= " + e.getClickCount());
                if (e.getClickCount() == 2 && topLeftMoveCheck(e)) doCopyBufImage(jFrame);
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                System.out.println("按下 ex = " + e.getX() + " ey =" + e.getY());
                if (e.getButton() == MouseEvent.BUTTON3) {
                    mouseEvent = MouseEvent.BUTTON3;
                    return;
                } else mouseEvent = e.getButton();
                isShowTip = false;
                clickedMoveStatus[0] = true;
                if (topLeftMoveCheck(e) && isReleasedDragged()) {
//                    System.out.println("方框拖动" + "按下 smoveX= " + e.getX() + " smoveY= " + e.getY());
                    move = true;
                    smoveX = e.getX();
                    smoveY = e.getY();
                } else if (!move && isReleasedDragged()) {
//                    System.out.println("大小拖动" + "按下 startX= " + e.getX() + " startY= " + e.getY());
                    controlPanel.setName("true");
                    right.setBackground(new Color(0, 0, 0, 100));
                    top.setBackground(new Color(0, 0, 0, 100));
                    left.setBackground(new Color(0, 0, 0, 100));
                    bottom.setBackground(new Color(0, 0, 0, 100));
                    bottom.setOpaque(true);
                    startX = e.getX();
                    startY = e.getY();
                    startXTemp = e.getX();
                    startYTemp = e.getY();
                } else if (isbBox || isCircle || isBrush || isText) {
                    if (!topLeftMoveCheck(e)) return;
//                    System.out.println("画笔图形画出" + "按下 boxStartX= " + e.getX() + " boxStartY= " + e.getY());
                    boxStartX = e.getX();
                    boxStartY = e.getY();
                    brush = new ArrayList<>();
                    if (isBrush) brush.add(e.getPoint());
                    if (isText) {
                        toptext.setLayout(gridBagLayout);
                        JTextPane jTextField = new JTextPane();
                        jTextField.setMinimumSize(new Dimension(100, 30));
                        int fontSize = Integer.parseInt(groupsize.getSelection().getActionCommand());
                        jTextField.setFont(new Font("微软雅黑", Font.BOLD, 20 + fontSize));
                        String color = groupcolor.getSelection().getActionCommand();
                        jTextField.setForeground(Color.decode(color));
                        jTextField.setMargin(new Insets(15, 5, 5, 5));
                        JPanel contenttext = new JPanel();
                        contenttext.add(jTextField);
                        contenttext.setOpaque(false);
                        jTextField.setOpaque(false);
                        jTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        gridBagConstraints.gridx = 0;
                        gridBagConstraints.gridy = 0;
                        gridBagConstraints.weighty = 1;
                        gridBagConstraints.weightx = 1;
                        gridBagConstraints.anchor = GridBagConstraints.WEST;
                        int startY = boxStartY;
                        int startX = boxStartX;
                        gridBagConstraints.insets = new Insets(startY, startX, 0, 0);
                        gridBagLayout.setConstraints(contenttext, gridBagConstraints);
                        toptext.add(contenttext);
                        jTextField.requestFocus(true);
                        jTextField.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                if (StringUtils.isNotBlank(jTextField.getText())) {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("str", jTextField.getText());
                                    map.put("size", fontSize);
                                    map.put("color", color);
                                    map.put("boxStartY", startY + 30);
                                    map.put("boxStartX", startX + 10);
                                    textArr.add(map);
                                    ctrlZList.add(map);
                                }
                                toptext.removeAll();
                            }
                        });
                        toptext.updateUI();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                System.out.println("抬起 x= " + e.getX() + " y= " + e.getY()
//                        + " mouseReleased:smoveX= " + (smoveX == e.getX() && smoveY == e.getY())
//                        + " ;startX= " + (startX == e.getX() && startY == e.getY())
//                        + " ;boxStartX= " + (boxStartX == e.getX() && boxStartY == e.getY())
//                );
                if (smoveX == e.getX() && smoveY == e.getY()) return;
                if (startX == e.getX() && startY == e.getY()) return;
                if (boxStartX == e.getX() && boxStartY == e.getY()) return;
                startXTemp = startX;
                startYTemp = startY;
                endXTemp = endX;
                endYTemp = endY;
                move = false;
                if (isbBox && paintbrush.equals("box")) {
//                    JSONArray array = new JSONArray();
                    List<Object> array = new ArrayList<>();
                    array.add(boxStartX);
                    array.add(boxStartY);
                    array.add(boxEndX);
                    array.add(boxEndY);
                    array.add(groupcolor.getSelection().getActionCommand());
                    array.add(groupsize.getSelection().getActionCommand());
                    boxArr.add(array);
                    ctrlZList.add(array);
                } else if (isCircle && paintbrush.equals("circle")) {
                    List<Object> array = new ArrayList<>();
                    int circleSize = Integer.parseInt(groupsize.getSelection().getActionCommand());
                    for (int i = 0; i < circleSize; i++) {
                        if (boxStartX < boxEndX && boxStartY < boxEndY) {
//                            System.out.println("boxStartX < boxEndX && boxStartY < boxEndY1");
                            array.add(boxStartX + i);
                            array.add(boxStartY + i);
                            array.add((boxEndX - i * 2) - boxStartX);
                            array.add((boxEndY - i * 2) - boxStartY);
                        } else if (boxStartX > boxEndX && boxStartY < boxEndY) {
//                            System.out.println("boxStartX > boxEndX && boxStartY < boxEndY2");
                            array.add(boxEndX + i);
                            array.add(boxStartY + i);
                            array.add((boxStartX - i * 2) - boxEndX);
                            array.add((boxEndY - i * 2) - boxStartY);
                        } else if (boxEndY < boxStartY && boxStartX < boxEndX) {
//                            System.out.println("boxEndY < boxStartY && boxStartX < boxEndX3");
                            array.add(boxStartX + i);
                            array.add(boxEndY + i);
                            array.add((boxEndX - i * 2) - boxStartX);
                            array.add((boxStartY - i * 2) - boxEndY);
                        } else if (boxStartY > boxEndY && boxStartX > boxEndX) {
//                            System.out.println("boxStartY > boxEndY && boxStartX > boxEndX4");
                            array.add(boxEndX + i);
                            array.add(boxEndY + i);
                            array.add((boxStartX - i * 2) - boxEndX);
                            array.add((boxStartY - i * 2) - boxEndY);
                        }
                    }
                    if (array.size() != 0) {
                        array.add(groupcolor.getSelection().getActionCommand());
                        array.add(circleSize);
                        circleArr.add(array);
                        ctrlZList.add(array);
                    }
                } else if (isBrush && paintbrush.equals("brush")) {
                    int size = Integer.parseInt(groupsize.getSelection().getActionCommand());
                    BrushLine brushLine = new BrushLine(groupcolor.getSelection().getActionCommand(), size, brush);
                    brushArr.add(brushLine);
                    ctrlZList.add(brushLine);
                } else if (isText && paintbrush.equals("text")) {
                    // TODO NULL
                }
                Arrays.fill(clickedMoveStatus, false);
            }
        });

        frameSelection.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseEvent == 3) return;// 右键不操作
                clickedMoveStatus[1] = true;
                if (!move && isReleasedDragged()) {
//                    System.out.println("大小拖动 startX = " + startX + ", startY = " + startY + ", endX = " + endX + ", endY = " + endY);
                    endX = e.getX();
                    endY = e.getY();
                    endXTemp = e.getX();
                    endYTemp = e.getY();
                    if (endY > startY) { // 鼠标向上移动 超过初始点
                        topMove = false;
                        if (endX > startX) {// 鼠标向左右移动 超过初始点
                            bottomRight(); // 向下移动 并向右
                            leftMove = false;
                            frameSelectionArea[0] = endX - startX;
                            frameSelectionArea[1] = endY - startY;
//                            System.out.println("长度 = " + (endX - startX) + " 高度 = " + (endY - startY));
                        } else {
                            bottomLeft();
                            leftMove = true;
                            frameSelectionArea[0] = startX - endX;
                            frameSelectionArea[1] = endY - startY;
//                            System.out.println("长度 = " + (startX - endX) + " 高度 = " + (endY - startY));
                        }
                    } else {
                        topMove = true;
                        if (endX > startX) {// 鼠标向上并向左移动 超过初始点
                            topLeft();
                            leftMove = false;
                            frameSelectionArea[0] = endX - startX;
                            frameSelectionArea[1] = startY - endY;
//                            System.out.println("长度 = " + (endX - startX) + " 高度 = " + (startY - endY));
                        } else {
                            topRight();
                            leftMove = true;
                            frameSelectionArea[0] = startX - endX;
                            frameSelectionArea[1] = startY - endY;
//                            System.out.println("长度 = " + (startX - endX) + " 高度 = " + (startY - endY));
                        }
                    }
                } else if (topLeftMoveCheck(e) && isReleasedDragged()) {
//                    System.out.println("方框拖动 smoveX = " + smoveX + ", smoveY = " + smoveY + ", emoveX = " + emoveX + ", emoveY = " + emoveY);
                    emoveX = e.getX();
                    emoveY = e.getY();
                    startX = startXTemp + (emoveX - smoveX);
                    if (startX < 0) startX = 0;
                    endX = endXTemp + (emoveX - smoveX);
                    if (endX >= width - 1) endX = width - 1;
                    startY = startYTemp + (emoveY - smoveY);
                    if (startY < 0) startY = 0;
                    endY = endYTemp + (emoveY - smoveY);
                    if (endY >= height - 40) endY = height - 40;
                    if (!topMove && !leftMove) bottomRight();
                    if (topMove && !leftMove) topLeft();
                    if (!topMove && leftMove) bottomLeft();
                    if (topMove && leftMove) topRight();
                } else if (isbBox || isCircle || isBrush || isText) {
                    if (!topLeftMoveCheck(e)) return;
//                    System.out.println("方框拖动 boxStartX = " + boxStartX + ", boxStartY = " + boxStartY + ", boxEndX = " + boxEndX + ", boxEndY = " + boxEndY);
                    boxEndX = e.getX();
                    boxEndY = e.getY();
                    if (isBrush) brush.add(e.getPoint());
                    frameSelection.updateUI();
                }
                controlPanel.setVisible(true);
                if (endX > startX)
                    controlPanel.setBounds(endX - controlPanel.getWidth(), 0, controlPanel.getWidth(), controlPanel.getHeight());
                else
                    controlPanel.setBounds(startX - controlPanel.getWidth(), 0, controlPanel.getWidth(), controlPanel.getHeight());
                frameSelection.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (isShowTip) {
//                    System.out.println("鼠标移动 mouseMoved ex= " + e.getX() + " ey= " + e.getY());
                    showTipPoint = e.getPoint();
                    frameSelection.repaint();
                }
            }
        });

        ctrlZButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getModifiers() == KeyEvent.CTRL_MASK) {
                    int keycode = e.getKeyCode();
                    if (keycode == KeyEvent.VK_Z) {
                        ctrlZClear();
                    }
                }
            }
        });
        toptext = new JPanel();
        lefttext = new JPanel();
        righttext = new JPanel();
        buttomtext = new JPanel();
        frameSelection.setLayout(new BorderLayout());
        frameSelection.add(toptext, BorderLayout.NORTH);
        frameSelection.add(lefttext, BorderLayout.WEST);
        frameSelection.add(righttext, BorderLayout.EAST);
        frameSelection.add(buttomtext, BorderLayout.SOUTH);
        toptext.setOpaque(false);
        lefttext.setOpaque(false);
        righttext.setOpaque(false);
        buttomtext.setOpaque(false);
        return frameSelection;
    }

    // 上下左右检测
    public boolean topLeftMoveCheck(MouseEvent e) {
//        System.out.println("topLeftMoveCheck");
        if (e.getX() > startX && e.getX() < endX && e.getY() > startY && e.getY() < endY) return true;
        else if (topMove && e.getX() > startX && e.getX() < endX && e.getY() < startY && e.getY() > endY) return true;
        else if (leftMove && e.getX() < startX && e.getX() > endX && e.getY() > startY && e.getY() < endY) return true;
        else if (e.getX() < startX && e.getX() > endX && e.getY() < startY && e.getY() > endY) return true;
        return false;
    }

    // 向下移动 并向右
    public void bottomRight() {
//        System.out.println("bottomRight");
        top.setBounds(0, 0, top.getWidth(), startY);
        bottom.setBounds(0, endY, top.getWidth(), height - endY);
        left.setBounds(0, startY, startX, endY - startY);
        right.setBounds(endX, startY, width - endX, endY - startY);
    }

    // 向上移动 并向右
    public void topRight() {
//        System.out.println("topRight");
        top.setBounds(0, 0, top.getWidth(), endY);
        bottom.setBounds(0, startY, top.getWidth(), height - endY);
        left.setBounds(0, endY, endX, startY - endY);
        right.setBounds(startX, endY, width - endX, startY - endY);
    }

    // 向下移动 并向左
    public void bottomLeft() {
//        System.out.println("bottomLeft");
        top.setBounds(0, 0, top.getWidth(), startY);
        bottom.setBounds(0, endY, top.getWidth(), height - endY);
        left.setBounds(0, startY, endX, endY - startY);
        right.setBounds(startX, startY, width - startX, endY - startY);
    }

    // 向上移动 并向左
    public void topLeft() {
//        System.out.println("topLeft");
        top.setBounds(0, 0, top.getWidth(), endY);
        bottom.setBounds(0, startY, top.getWidth(), height - endY);
        left.setBounds(0, endY, startX, startY - endY);
        right.setBounds(endX, endY, width - endX, startY - endY);
    }

    /**
     * 保存的画笔数据写出*
     * @param g
     */
    public void boxWrite(Graphics g) {
        Integer lineSize;
        for (List<Object> array : boxArr) {
            int boxStartX = Integer.parseInt(array.get(0) + "");
            int boxStartY = Integer.parseInt(array.get(1) + "");
            int boxEndX = Integer.parseInt(array.get(2) + "");
            int boxEndY = Integer.parseInt(array.get(3) + "");
            Object color = array.get(4);
            g.setColor(Color.decode(color + "")); //设置颜色
            lineSize = Integer.parseInt(array.get(5) + "");
            for (int i = 0; i < lineSize; i++) {
                g.drawLine(boxStartX, boxStartY - i, boxEndX, boxStartY - i);
                g.drawLine(boxStartX, boxEndY - i, boxEndX, boxEndY - i);
                g.drawLine(boxStartX + i, boxStartY, boxStartX + i, boxEndY);
                g.drawLine(boxEndX - i, boxStartY, boxEndX - i, boxEndY);
            }
        }
        BasicStroke stokeLine = new BasicStroke(2.0f);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(stokeLine);
        for (List<Object> array : circleArr) {
            int circleNum = Integer.parseInt(array.get(array.size() - 1) + "");
            g.setColor(Color.decode(array.get(array.size() - 2) + "")); //设置颜色
            for (int i = 0; i < circleNum; i++) {
                int startX = Integer.parseInt(array.get(i * 4 + 0) + "");//0  4  8   12
                int startY = Integer.parseInt(array.get(i * 4 + 1) + "");//1  5  9   13
                int endX = Integer.parseInt(array.get(i * 4 + 2) + "");  //2  6  10  14
                int endY = Integer.parseInt(array.get(i * 4 + 3) + "");  //3  7  11  15
                g.drawOval(startX, startY, endX, endY);
            }
        }
        for (BrushLine bline : brushArr) {
            g.setColor(Color.decode(bline.getColor())); //设置颜色
            BasicStroke brushLine = new BasicStroke(bline.getSize());
            Graphics2D brushLineG2 = (Graphics2D) g;
            brushLineG2.setStroke(brushLine);
            List<Point> ap = bline.getApoint();
            for (int i = 0; i < bline.getApoint().size(); i++) {
                int endi = i + 1 < bline.getApoint().size() ? i + 1 : i;
                g.drawLine(ap.get(i).x, ap.get(i).y, ap.get(endi).x, ap.get(endi).y);
            }
        }
        for (Map<String, Object> map : textArr) {
            g.setColor(Color.decode(map.get("color") + ""));
            int fontSize = 20 + Integer.parseInt(map.get("size") + "");
            Font font = new Font("微软雅黑", Font.BOLD, fontSize);
            g.setFont(font);
            int boxStartY = Integer.parseInt(map.get("boxStartY") + "");
            int boxStartX = Integer.parseInt(map.get("boxStartX") + "");
            String[] strings = (map.get("str") + "").split("\r\n");
            for (int i = 0; i < strings.length; i++) {
                g.drawString(strings[i], boxStartX, boxStartY + (i * fontSize));
            }

        }
    }

    /**
     * 撤销清除画笔 *
     */
    public void ctrlZClear() {
        if (ctrlZList.size() > 0) {
            Object array = ctrlZList.get(ctrlZList.size() - 1);
            boxArr.remove(array);
            circleArr.remove(array);
            brushArr.remove(array);
            textArr.remove(array);
            ctrlZList.remove(array);
        }
        boxStartX = 0;
        boxStartY = 0;
        boxEndX = 0;
        boxEndY = 0;
        if (brush != null) brush.clear();
        isCtrlZ = true;
        frameSelection.repaint();
    }

    /**
     * 保存图片到磁盘 *
     * @param jFrame
     */
    public void doSaveBufImage(JFrame jFrame) {
        try {
            Rectangle rect = new Rectangle(startX + 5, startY + 5, frameSelectionArea[0] - 10, frameSelectionArea[1] - 10);
            BufferedImage bufImage = new Robot().createScreenCapture(rect);// 截屏操作
            JFileChooser jfc = new JFileChooser(".");
            jfc.addChoosableFileFilter(new GIFfilter());
            jfc.addChoosableFileFilter(new BMPfilter());
            jfc.addChoosableFileFilter(new JPGfilter());
            jfc.setFileFilter(new PNGfilter());
            DateFormat dft = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = dft.format(new Date());
            File file = new File("截图工具_" + format + ".png");
            jfc.setSelectedFile(file);
            AtomicReference<String> about = new AtomicReference<>("PNG");
            jfc.addPropertyChangeListener(evt -> {
                if (JFileChooser.FILE_FILTER_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
                    if (evt.getNewValue() instanceof JPGfilter) about.set("JPG");
                    if (evt.getNewValue() instanceof BMPfilter) about.set("BMP");
                    if (evt.getNewValue() instanceof GIFfilter) about.set("GIF");
                    if (evt.getNewValue() instanceof PNGfilter) about.set("PNG");
                    jfc.setSelectedFile(new File("截图工具_" + format + "." + about.get().toLowerCase()));
                }
            });
            int ch = jfc.showDialog(jFrame, "保存文件");
            if (ch == JFileChooser.APPROVE_OPTION) ImageIO.write(bufImage, about.get(), jfc.getSelectedFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jFrame.dispose();
            superFrame.setVisible(true);
        }
    }

    /**
     * 复制图片到内存*
     * @param jFrame
     */
    public void doCopyBufImage(JFrame jFrame) {
        try {
            Rectangle rect = new Rectangle(startX + 5, startY + 5, frameSelectionArea[0] - 10, frameSelectionArea[1] - 10);
            BufferedImage bufImage = new Robot().createScreenCapture(rect);// 截屏操作
            Transferable transferable = new Transferable() {
                @Override
                public DataFlavor[] getTransferDataFlavors() {
                    return new DataFlavor[]{DataFlavor.imageFlavor};
                }

                @Override
                public boolean isDataFlavorSupported(DataFlavor flavor) {
                    return DataFlavor.imageFlavor.equals(flavor);
                }

                @Override
                public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                    if (isDataFlavorSupported(flavor)) return bufImage;
                    throw new UnsupportedFlavorException(flavor);
                }
            };
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jFrame.dispose();
            superFrame.setVisible(true);
        }
    }

    /**
     * 添加单选按钮组图*
     */
    public void addButtonGroup() {
        groupsize = new ButtonGroup();
        mindian.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/圆点小1.png", 20, 20));
        mindian.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/圆点小.png", 20, 20));
        mindian.setActionCommand("2");
        groupsize.add(mindian);
        defaultdian.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/圆点中1.png", 20, 20));
        defaultdian.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/圆点中.png", 20, 20));
        defaultdian.setActionCommand("4");
        groupsize.add(defaultdian);
        maxdian.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/圆点大1.png", 20, 20));
        maxdian.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/圆点大.png", 20, 20));
        maxdian.setActionCommand("8");
        groupsize.add(maxdian);

        groupcolor = new ButtonGroup();
        ashkuang.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/ash多选框.png", 20, 20));
        ashkuang.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/ash多选框-选中.png", 20, 20));
        ashkuang.setActionCommand("#707070");
        groupcolor.add(ashkuang);
        bluekuang.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/blue多选框.png", 20, 20));
        bluekuang.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/blue多选框-选中.png", 20, 20));
        bluekuang.setActionCommand("#0000ff");
        groupcolor.add(bluekuang);
        greenkuang.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/green多选框.png", 20, 20));
        greenkuang.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/green多选框-选中.png", 20, 20));
        greenkuang.setActionCommand("#2AA515");
        groupcolor.add(greenkuang);
        redkuang.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/red多选框.png", 20, 20));
        redkuang.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/red多选框-选中.png", 20, 20));
        redkuang.setActionCommand("#ff0000");
        groupcolor.add(redkuang);
        whitekuang.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/white多选框.png", 20, 20));
        whitekuang.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/white多选框-选中.png", 20, 20));
        whitekuang.setActionCommand("#FFFFFF");
        groupcolor.add(whitekuang);
        yellowkuang.setIcon(ImageIconTool.gitImageIcon("icons/capterScreen/yellow多选框.png", 20, 20));
        yellowkuang.setSelectedIcon(ImageIconTool.gitImageIcon("icons/capterScreen/yellow多选框-选中.png", 20, 20));
        yellowkuang.setActionCommand("#EFB336");
        groupcolor.add(yellowkuang);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        beijing = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(bufImage, 0, 0, screenSize.width, screenSize.height, this);
            }
        };
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        Screenshot screenTest = new Screenshot(jFrame, new JFrame());
        jFrame.getContentPane().add(screenTest.beijing, BorderLayout.CENTER);
        jFrame.setUndecorated(true);
        jFrame.setSize(width, height);
        jFrame.setVisible(true);
        WindowTool.winConter(jFrame);
    }

}