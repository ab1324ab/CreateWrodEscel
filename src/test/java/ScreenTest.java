
import com.myigou.tool.WindowTool;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ScreenTest extends JFrame {

    private static int startX, startY, endX, endY;
    static boolean flag = true;

    public static void main(String[] args) throws Exception {
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 创建需要截取的矩形区域
        Rectangle rect = new Rectangle(0, 0, screenSize.width, screenSize.height);
        // 截屏操作
        BufferedImage bufImage = new Robot().createScreenCapture(rect);

        JFrame jFrame = new JFrame();

        JPanel beijing = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(bufImage, 0, 0, screenSize.width, screenSize.height, this);
            }
        };
        beijing.setLayout(new BorderLayout());
        JPanel jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.red);
                for (int i = 0; i < 2; i++) {
                    g.drawLine(startX, startY - i, endX, startY - i);
                    g.drawLine(startX, endY - i, endX, endY - i);
                    g.drawLine(startX + i, startY, startX + i, endY);
                    g.drawLine(endX - i, startY, endX - i, endY);
                }
                g.fillRect(startX - 5, startY - 5, 10, 10); // 上线的 起点描点
                g.fillRect((startX + endX) / 2 - 5, startY - 5, 10, 10); // 上线的 中间描点
                g.fillRect(endX - 5, startY - 5, 10, 10); // 上线的 终点描点

                g.fillRect(startX -5, endY -5, 10, 10); // 上线的 起点描点
//                g.fillRect(endX - 2, (startX + endX) / 2 - 2, 5, 5);
//                g.fillRect(startX - 2, startY - 2, 5, 5);
//                g.fillRect(startX - 2, endY - 2, 5, 5);
            }
        };
        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("点击");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("按下");
                if (flag) {
                    startX = e.getX();
                    startY = e.getY();
                }
//                flag = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("抬起");
                flag = false;
            }
        });
        jPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("移动 " + startX + "," + startY + "," + endX + "," + endY);
                endX = e.getX();
                endY = e.getY();
                jPanel.repaint();
            }
        });
        jFrame.setLayout(new BorderLayout());
        jPanel.setOpaque(false);


        beijing.add(jPanel,BorderLayout.CENTER);

        jFrame.getContentPane().add(beijing,BorderLayout.CENTER);
//        jFrame.setUndecorated(true);
        jFrame.setSize(800, 500);
//        jFrame.setBackground(new Color(0,0,0, 142));
        jFrame.setVisible(true);
        WindowTool.winConter(jFrame);

//
//        // 保存截取的图片
//        ImageIO.write(bufImage, "PNG", new File("capture.png"));
    }


}

