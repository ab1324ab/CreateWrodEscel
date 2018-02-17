package com.myigou.view;

import com.myigou.client.FunctionInter;
import com.myigou.client.Impl.*;
import com.myigou.tool.HttpRequestEnter;
import com.myigou.tool.PropertiesTool;
import com.myigou.tool.UpdateClient;
import com.myigou.tool.WindowTool;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ab1324ab on 2017/5/7.
 */
public class WindowView extends JFrame implements Runnable{

    public static final String  VERSION_THIS="V1.0.13";
    public  String  SHOW_MAIN="set_1";
    private MuenBar muenBar=new MuenBar();
    private JPanel clientPanel=new JPanel();
    private JPanel titlePanel=new JPanel();
    private JPanel jPanelBar=null;
    private Timer timer=null;//进度条时间计数器
    private JProgressBar bar=null;//进度条
    private String version="";//请求版本
    public static  Map<String,String> contentMap=new HashMap<String, String>();
    public WindowView() {
        super();
        contentMap= PropertiesTool.redConfigFile("config.properties");
    }

    /**
     * 初始化窗口所有参数
     */
    public void init(){
        this.setSize(990,650);
        this.setTitle("综合工具"+VERSION_THIS);
        //this.setResizable(false);
        WindowTool.winConter(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        muenBar.menuBox(this);//添加菜单项
        if(contentMap.size()!=0){
            SHOW_MAIN=contentMap.get("showMain");
        }
        MuenBar.displayJpael=this.accessDisplay(SHOW_MAIN);

        //clientPanel.setBackground(Color.CYAN);
        JButton button2=new JButton("关于我们");
        this.add(MuenBar.displayJpael.get("titlePanel"),BorderLayout.NORTH);//添加标题面板入窗体
        this.add(MuenBar.displayJpael.get("clientPanel"));
        this.add(button2,BorderLayout.SOUTH);//关于我们 帮助
        this.setVisible(true);
    }

    /**
     * 替换当前面板显示功能
     * @param status
     * @return
     */
    public  HashMap<String,JPanel> accessDisplay(String status){
        HashMap<String,JPanel> mapJpanel=new HashMap<String,JPanel>();
        FunctionInter functionInter=null;
        //this.setLayout(new GridBagLayout());
        try {
        clientPanel.removeAll();
        titlePanel.removeAll();
        if("fun_1".equals(status)){//周计划生成
            functionInter=new WeekPlanMake();
        }else if("fun_2".equals(status)){//文件操作
            functionInter=new TextEditing();
        }else if("fun_3".equals(status)){//邮件发送
            functionInter=new SendEmail();
        }else if("set_1".equals(status)){//窗口设置
            functionInter=new WindowSetting();
        }else if("fun_1_2".equals(status)){//周计划生成_2
            functionInter=new WeekPlanMake2();
        }
        clientPanel=functionInter.getFunction(clientPanel,this);
        titlePanel=functionInter.getTitle(titlePanel,this);
        mapJpanel.put("clientPanel",clientPanel);
        mapJpanel.put("titlePanel",titlePanel);
        }catch (Exception e){
            //启动预警
            e.printStackTrace();
            MuenBar.windowView.repair( new JFrame("自动修复"));
        }
       return mapJpanel;
    }

    /**
     * 菜单选择功能入口
     * @param mapJpanel
     */
    public  void selectButton(HashMap<String,JPanel> mapJpanel){
        this.add(mapJpanel.get("titlePanel"),BorderLayout.NORTH);//添加标题面板入窗体
        this.add(mapJpanel.get("clientPanel"));
        mapJpanel.get("titlePanel").setVisible(false);
        mapJpanel.get("titlePanel").setVisible(true);
        mapJpanel.get("clientPanel").setVisible(false);
        mapJpanel.get("clientPanel").setVisible(true);
//        this.validate();
//        this.setVisible(false);
//        this.setVisible(true);
    }

    @Override
    public void run() {
        int i=1;
        System.out.println("更新模块启动");
        while ("".equals(version)&&i<=10){
            HttpRequestEnter httpRequestEnter=new HttpRequestEnter();
            try {
                version= HttpRequestEnter.versionEscel();
                UpdateClient.updateInstall(this,version);
                System.out.println("第"+i+"次请求版本！当前最新版本："+version);
            }catch (Exception ex){
                System.out.println("第"+i+"次请求版本！"+ex.getMessage());
            }
            i++;
        }
        System.out.println("版本结束请求·····");
    }
    public void repair(final JFrame jFrame){
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600,100);
        jPanelBar=new JPanel();
        bar = new JProgressBar();
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setValue(0);
        bar.setStringPainted(true);
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = bar.getValue();
                if(value < 100) {
                    value++;
                    bar.setValue(value);
                }else{
                    timer.stop();
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    PropertiesTool.initialization();
                    contentMap= PropertiesTool.redConfigFile("config.properties");
                    MuenBar.windowView.init();
                }
            }
        });
        bar.setPreferredSize(new Dimension(500, 20));
        jPanelBar.add(new JLabel("程序受损,正在自动修复......"));
        jPanelBar.add(bar);
        jPanelBar.setVisible(true);
        jFrame.add(jPanelBar,BorderLayout.CENTER);
        WindowTool.winConter(jFrame);
        //System.out.println(new ImageIcon(WindowView.class.getResource("/icon/restore.ico").getPath().substring(1)).getImage());
        this.setVisible(false);
        jFrame.setVisible(true);
        timer.start();
    }
}
