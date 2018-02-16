package com.myigou.client.Impl;

import com.myigou.client.FunctionInter;



import javax.swing.*;

/**
 * Created by ab1324ab on 2018/1/22.
 */
public class WeekPlanMake2  extends JPanel implements FunctionInter {
    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        this.xxxx(jPanel,jFrame);

        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame) {
        return null;
    }

    public JPanel xxxx(JPanel jPanel,JFrame jFrame){
       /* Container cot=jFrame.getContentPane();
        //setSize(700,500);
        jPanel =new BeiJingPane(this.getWidth(),this.getHeight());
        panBall=new XiaoQiuPane(this.getWidth(),this.getHeight());
        clearityPane=new JPanel();
        clearityPane.setVisible(true);
        clearityPane.setOpaque(false);  //透明效果
        clearityPane.setBounds(new Rectangle(700,450));
        panBall.setLayout(new BorderLayout());
        buttonBallPane.add(changeBackColor);
        buttonBallPane.add(changeBallColor);
        buttonBallPane.add(start_ball);
        buttonBall.setLayout(new BorderLayout());
        buttonBall.setBackground(Color.red);
        buttonBall.add(buttonBallPane, BorderLayout.SOUTH);

        label.setSize(100, 150);



        ball=new BallDemo(this.getSize().width-20,this.getSize().height-30,buttonBall);



        clearityPane.add(bbb);
        pane.setLayout(new BorderLayout());
        bbb.setVisible(false);

        panBall.add(clearityPane,BorderLayout.CENTER);  //添加透明面板
        buttonpane.add(changePicture);
        buttonpane.add(changeColor);
        buttonpane.add(start_retangle);


        panBall.add(buttonpane, BorderLayout.SOUTH);



        //cot.add(pane);
        panButton.add(bt);
        panButton.add(bt0);
        panButton.add(bt1);
        panButton.add(bt2);
        panButton.add(bt3);
        panButton.add(bt4);

        menuBar.add(menuOpen);
        menuBar.add(menuNative);
        menuBar.add(menuNet);
        menuBar.add(menuQuit);
        menuBar.add(menuSave);
        pane.add(panButton,BorderLayout.SOUTH);
        pane.add(menuBar,BorderLayout.NORTH);
        pane.add(label,BorderLayout.CENTER);





        changePicture.addActionListener(this);
        changeColor.addActionListener(this);
        changeBallColor.addActionListener(this);
        changeBackColor.addActionListener(this);
        start_retangle.addActionListener(this);  //给矩形小球启动按钮注册监听
        start_ball.addActionListener(this);      //给小球启动按钮注册监听

        pane.addMouseListener(this);

        bt0.addActionListener(this);
        bt.addActionListener(this);
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        bt3.addActionListener(this);
        bt4.addActionListener(this);
        label.addMouseListener(this);//给图片注册监听
        menuSave.addMouseListener(this);
        menuOpen.addMouseListener(this);
        menuNative.addMouseListener(this);
        menuNet.addMouseListener(this);
        menuQuit.addMouseListener(this);



        jtp.addChangeListener(this); //给选项卡注册变化接口监听
        jtp.add("默认面板",pane);
        jtp.add("矩形小球运动", panBall);
        jtp.add("小球运动", buttonBall);
        cot.add(jtp);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);*/

        return null;
    }
}
