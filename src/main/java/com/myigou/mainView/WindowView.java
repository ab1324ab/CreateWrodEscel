package com.myigou.mainView;

import com.myigou.clientView.FunctionInter;
import com.myigou.clientView.impl.*;
import com.myigou.clientView.impl.filesDispose.FilesDispose;
import com.myigou.clientView.impl.sendMessage.SendMessage;
import com.myigou.clientView.impl.textWord.TextWord;
import com.myigou.clientView.impl.windowSetting.WindowSetting;
import com.myigou.tool.*;
import com.tulskiy.keymaster.common.Provider;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ab1324ab
 * Created by ab1324ab on 2017/5/7.
 */
public class WindowView extends JFrame implements Runnable {

    public static final String VERSION_THIS = "V1.0.15";
    public String SHOW_MAIN = "set_1";
    private JLabel bottomJLabel = new JLabel(" ");
    private ViewMain muenBar = new ViewMain();
    private JPanel clientPanel = new JPanel();
    private JPanel titlePanel = new JPanel();
    private JPanel jPanelBar = null;
    // 进度条时间计数器
    private Timer timer = null;
    // 进度条
    private JProgressBar bar = null;
    // 请求版本
    private String version = "";
    public static Map<String, String> contentMap = new HashMap<String, String>();
    public TextWord textWord = null; // 辅助操作文档文本对象

    public WindowView() {
        super();
        contentMap = PropertiesTool.redConfigFile("config.properties");
    }

    /**
     * 初始化窗口所有参数
     */
    public void init() {
        this.setSize(1080, 630);
        this.setMinimumSize(new Dimension(1080, 630));
        this.setTitle("综合工具" + VERSION_THIS);
        // this.setResizable(false);
        WindowTool.winConter(this);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 添加菜单项
        muenBar.menuBox(this);
        if (contentMap.size() != 0) SHOW_MAIN = contentMap.get("showMain");
        ViewMain.displayJpael = this.accessDisplay(SHOW_MAIN);

        // 添加标题面板入窗体
        this.add(ViewMain.displayJpael.get("titlePanel"), BorderLayout.NORTH);
        this.add(ViewMain.displayJpael.get("clientPanel"), BorderLayout.CENTER);
        // 关于我们 帮助
        this.add(bottomJLabel, BorderLayout.SOUTH);
        // 注册设置热键
        Provider provider = Provider.getCurrentProvider(true);
        provider.reset();
        String screenshotKey = contentMap.get("message.setting.screenshotKey");
        provider.register(KeyStroke.getKeyStroke("ctrl alt " + screenshotKey), arg0 -> SendMessage.operateScreenshot(this));// 当按下热键时调用截图
        this.setVisible(true);
    }

    /**
     * 替换当前面板显示功能
     *
     * @param status
     * @return
     */
    public HashMap<String, JPanel> accessDisplay(String status) {
        HashMap<String, JPanel> mapJpanel = new HashMap<String, JPanel>();
        FunctionInter functionInter = null;
        String content = "";
        try {
            clientPanel.removeAll();
            titlePanel.removeAll();
            // 启动优先显示项
            if ("fun_1".equals(status)) functionInter = new WeekPlanMake();         //周计划生成
            else if ("fun_2".equals(status)) functionInter = new FilesDispose();    //文件操作
            else if ("fun_3".equals(status)) functionInter = new SendMessage();     //消息发送
            else if ("set_1".equals(status)) functionInter = new WindowSetting();   //窗口设置
            else if ("fun_1_2".equals(status)) functionInter = new WeekPlanMake2(); //周计划生成_2
            else if ("txt_1".equals(status)) functionInter = textWord = new TextWord(); //文本
            else if ("txt_2".equals(status)) functionInter = textWord = new TextWord(this, "open_txt"); //打开文本
            else if ("txt_3".equals(status)) content = textWord.saveFileTxt(this); /*保存新建和打开的文本*/
            else if ("txt_4".equals(status)) content = textWord.asSaveFileTxt(this); /*另保存新建和打开的文本*/
            // 特殊条件的菜单处理项
            bottomJLabel.setVisible(!status.equals("txt_1") && !status.equals("txt_2") && !status.equals("txt_3") && !status.equals("txt_4"));
            if (status.equals("txt_3") || status.equals("txt_4")) functionInter = textWord;
            // 存入切换显示对象
            Font font = new Font("楷体", Font.BOLD, 18);
            clientPanel = functionInter.getFunction(clientPanel, this);
            titlePanel = functionInter.getTitle(titlePanel, this, font);
            mapJpanel.put("clientPanel", clientPanel);
            mapJpanel.put("titlePanel", titlePanel);
            // 写入文本框内容放在元素重新创建之后
            if (status.equals("txt_3") || status.equals("txt_4")) textWord.anewAssignment(content);
        } catch (Exception e) {
            // 启动预警
            e.printStackTrace();
            ViewMain.windowView.repair(new JFrame("自动修复"));
        }
        return mapJpanel;
    }

    /**
     * 菜单选择功能入口
     *
     * @param mapJpanel
     */
    public void selectButton(HashMap<String, JPanel> mapJpanel) {
        // 添加标题面板入窗体
        this.add(mapJpanel.get("titlePanel"), BorderLayout.NORTH);
        this.add(mapJpanel.get("clientPanel"), BorderLayout.CENTER);
        mapJpanel.get("titlePanel").setVisible(false);
        mapJpanel.get("titlePanel").setVisible(true);
        mapJpanel.get("clientPanel").setVisible(false);
        mapJpanel.get("clientPanel").setVisible(true);
    }

    @Override
    public void run() {
        int i = 1;
        System.out.println("更新模块启动");
        while (StringUtils.isEmpty(version) && i <= 10) {
            try {
                version = HttpRequestEnter.doGetStr("http://www.nacei.cn/version");
                UpdateClient.updateInstall(this, version);
                System.out.println("第" + i + "次请求版本！当前最新版本：" + version);
            } catch (Exception ex) {
                System.out.println("第" + i + "次请求版本！" + ex.getMessage());
            }
            i++;
        }
        System.out.println("版本结束请求·····");
    }

    public void repair(final JFrame jFrame) {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600, 120);
        jPanelBar = new JPanel();
        bar = new JProgressBar();
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setValue(0);
        bar.setStringPainted(true);
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = bar.getValue();
                if (value < 100) {
                    value++;
                    bar.setValue(value);
                } else {
                    timer.stop();
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    PropertiesTool.initialization();
                    contentMap = PropertiesTool.redConfigFile("config.properties");
                    ViewMain.windowView.init();
                }
            }
        });
        bar.setPreferredSize(new Dimension(500, 20));
        jPanelBar.add(new JLabel("程序受损,正在自动修复......"));
        jPanelBar.add(bar);
        jPanelBar.setVisible(true);
        jFrame.add(jPanelBar, BorderLayout.CENTER);
        JLabel bottom = new JLabel("");
        jFrame.add(bottom, BorderLayout.SOUTH);
        WindowTool.winConter(jFrame);
        // System.out.println(new ImageIcon(WindowView.class.getResource("restore.ico").getPath().substring(1)).getImage());
        this.setVisible(false);
        jFrame.setVisible(true);
        timer.start();
    }
}
