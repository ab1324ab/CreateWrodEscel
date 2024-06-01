import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class MainFrame extends JFrame {
    final JPanel panel1 = new JPanel();
    final JPanel panel2 = new JPanel();
    final JPanel maskJPanel = new JPanel();

    public MainFrame() {
        this.setSize(900, 600);
        this.setLayout(null);
        this.add(panel1);
        this.add(panel2);
        this.add(maskJPanel);
        panel1.setBounds(0, 0, 100, 100);
        panel2.setBounds(200, 0, 100, 100);
        panel1.setBackground(new Color(0));
        panel2.setBackground(new Color(1));
        ImageIcon bgiIcon = new ImageIcon("mask.png");
        JLabel bg = new JLabel(bgiIcon);
        maskJPanel.add(bg);
        bg.setBounds(0, 0, 100, 100);
        this.setVisible(true);

        Drag drag = new Drag();
        addMouseListener(drag);
        addMouseMotionListener(drag);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MainFrame mainFrame = new MainFrame();
    }

    class Drag implements MouseInputListener {
        int moving = 0;  //显示Panel是否处于移动过程中，否则只要单击便会调用press里面的方法
        int xinit = 0;	 //鼠标按下时所处的X坐标
        int yinit = 0;   //鼠标按下时所处的Y坐标
        int x0 = 0;		//拖拽过程中鼠标的位置
        int y0 = 0;
        Timer timer;

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        //鼠标按下时初始化线程
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            xinit = e.getX();
            yinit = e.getY();
            timer = new Timer(10, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    maskJPanel.setBounds(x0 - xinit + panel2.getX(), y0 - yinit
                            + panel2.getY(), 100, 100);
                }
            });
        }

        //鼠标松开时结束线程
        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            if (moving == 1) {
                int x1 = e.getX();
                int y1 = e.getY();
                panel2.setBounds(x1 - xinit + panel2.getX(),
                        y1 - yinit + panel2.getY(), 100, 100);
                moving = 0;
                panel2.setVisible(true);
                maskJPanel.setVisible(false);
                timer.stop();
            }
        }

        //鼠标拖动过程中，不断更新maskPanel的位置
        @Override
        public void mouseDragged(MouseEvent arg0) {
            // TODO Auto-generated method stub
            x0 = arg0.getX();
            y0 = arg0.getY();
            if (x0 > panel2.getX() && x0 < (panel2.getX() + panel2.getWidth())
                    && y0 > panel2.getY()
                    && y0 < (panel2.getY() + panel2.getHeight())) {
                moving = 1;
                panel2.setVisible(false);
                maskJPanel.setVisible(true);
                timer.start();
            }

        }

        @Override
        public void mouseMoved(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

    }

}