import com.myigou.tool.ImageIconTool;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
public class D {
    D(){
        JDialog jDialog = new JDialog();
        jDialog.setSize(400, 200);

        Dimension dimension = new Dimension(jDialog.getWidth(), jDialog.getHeight());
        JLayeredPane lp = new JLayeredPane();
        lp.setSize(dimension);

        Point origin = new Point(0, 0);
        Rectangle rectangle = new Rectangle(origin, dimension);

        JPanel panelBg=new JPanel();
        panelBg.setBounds(rectangle);
        panelBg.setLayout(new BorderLayout());
        ImageIcon imageIcon = ImageIconTool.gitImageIcon("/images/jDialog.jpg",dimension.width,dimension.height);
        JLabel jl1 = new JLabel(imageIcon);
        panelBg.add(jl1,BorderLayout.CENTER);
        lp.add(panelBg,new Integer(0));

        JPanel panelContent=new JPanel();
        panelContent.setLayout(new BorderLayout());
        panelContent.setBounds(rectangle);
        panelContent.setOpaque(false);
        JPanel jp1 = new JPanel();
        jp1.add(new JLabel("正在测量......"));
        jp1.setOpaque(false);
        JPanel jp2 = new JPanel();
        jp2.add(new JButton("取消"));
        jp2.setOpaque(false);
        panelContent.add(jp1,BorderLayout.CENTER);
        panelContent.add(jp2,BorderLayout.SOUTH);
        lp.add(panelContent, new Integer(1));

        Container container = jDialog.getContentPane();
        container.add(lp, BorderLayout.CENTER);
        jDialog.setUndecorated(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
    }
    public static void main(String[] args) {
        new D();
    }
}