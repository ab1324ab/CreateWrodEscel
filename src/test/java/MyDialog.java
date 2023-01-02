import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyDialog {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton jButton = new JButton("打开");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog(frame, "ffffffffffffff");
                JPanel jPanel = new JPanel();
                jPanel.add(new JLabel("sssssssf"));
                jPanel.add(new JTextField());
                jPanel.add(new JButton("fag"));
                d.add(jPanel);
                d.setModal(true);
                d.setSize(200, 100);
                d.setVisible(true);
            }
        });
        frame.add(jButton);
        frame.setVisible(true);

        Provider provider = Provider.getCurrentProvider(true);
        provider.reset();
        final HotKeyListener listener = new HotKeyListener() {
            @Override
            public void onHotKey(HotKey arg0) {
                // 这里写按下热键时执行的功能
                System.out.println("这里写按下热键时执行的功能");
            }
        };
        // 注册一个热键Ctrl+LEFT，当按下这个组合时调用listener
        provider.register(KeyStroke.getKeyStroke("ctrl alt A"), listener);

    }
}