import com.myigou.tool.ImageIconTool;

import javax.swing.*;
import java.awt.*;

public class JFileChooserFrame {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(700,700);

        ImageIcon imageIcon =ImageIconTool.gitImageIcon("/images/jDialog.jpg",600,600);
        JLabel jLabel= new JLabel(imageIcon);
        jLabel.setBounds(new Rectangle(new Point(0,0),new Dimension(700,700)));
        jFrame.getContentPane().add(jLabel);

        JPanel jPanel=new JPanel();
        jPanel.add(new JButton("qungding"));
        jPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,6));
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }

}