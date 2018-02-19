package com.myigou.client.Impl;

import com.myigou.client.FunctionInter;
import com.myigou.module.OnTop;
import com.myigou.tool.PropertiesTool;
import com.myigou.view.WindowView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ab1324ab on 2017/9/6.
 */
public class WindowSetting implements FunctionInter{
    private GridBagLayout gridBagLayout=new GridBagLayout();//网格布局
    private Map<String,String> contentMap=new HashMap<String, String>();

    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(new  BorderLayout());
        this.settingMain(jPanel);
        /*this.fileConcentrated(jPanel);

        this.createFolders1(jPanel);
        this.createFolders2(jPanel);
        this.registerListener(jPanel,jFrame);*/

        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame) {
        jPanel.add(new JLabel("窗口设置"));
        return jPanel;
    }

     public void settingMain(final JPanel jPanel){
         contentMap= WindowView.contentMap;//初始化资源文件
         JPanel jPanel1=new JPanel(new  BorderLayout());
         JPanel showMain=new JPanel();
         showMain.add(new JLabel("启动主页显示选项："));
         JRadioButton createExcel=new JRadioButton("周计划生成");
         JRadioButton fileManage=new JRadioButton("文件编辑");
         JRadioButton sendEmail=new JRadioButton("邮件发送");
         createExcel.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 PropertiesTool.writeSet(null,"showMain","fun_1");//周计划生成
                 System.out.println("fun_1");
             }
         });
         fileManage.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 PropertiesTool.writeSet(null,"showMain","fun_2");//文件编辑
                 System.out.println("fun_2");
             }
         });
         sendEmail.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 PropertiesTool.writeSet(null,"showMain","fun_3");//邮件编辑
                 System.out.println("fun_3");
             }
         });
         if("fun_1".equals(contentMap.get("showMain"))){
             createExcel.setSelected(true);
         }else if ("fun_2".equals(contentMap.get("showMain"))){
             fileManage.setSelected(true);
         }else if ("fun_3".equals(contentMap.get("showMain"))){
             sendEmail.setSelected(true);
         }
         ButtonGroup group= new ButtonGroup();
         group.add(createExcel);
         group.add(fileManage);
         group.add(sendEmail);
         showMain.add(createExcel);
         showMain.add(fileManage);
         showMain.add(sendEmail);

            jPanel1.add(showMain,BorderLayout.NORTH);

            OnTop onTop=new OnTop();
            Map<String,Map<String,JPanel>> jPanelMap=new HashMap<String,Map<String,JPanel>>();
            Map<String,JPanel> panelHashMap=new HashMap<String,JPanel>();
            for (int i=0;i<2;i++){
                jPanelMap.put(String.valueOf(i),getJPanelMap(i,panelHashMap));
            };

            jPanel1.add(onTop.getJContentPane(2,jPanelMap),BorderLayout.CENTER);


            jPanel.add(jPanel1,BorderLayout.CENTER);
    }

    public Map<String,JPanel> getJPanelMap(int i,Map<String,JPanel> jPanelMap){
        if (i==0){
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("Panel 假的" );
            JPanel panel = new JPanel();
            panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            jPanelMap.put("第一页",panel);
        }else if(i==1){
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(151, 99, 163, 113));
            jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setForeground(new Color(255, 51, 51));
            jLabel.setText("Panel 也假的" );
            JPanel panel = new JPanel();
            panel.setBackground(Color.red);
            panel.setLayout(null);
            panel.add(jLabel, null);
            jPanelMap.put("第二页",panel);
        }


        return jPanelMap;
    }
}
