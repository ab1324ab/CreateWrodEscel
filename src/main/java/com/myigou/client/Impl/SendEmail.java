package com.myigou.client.Impl;

import com.myigou.client.FunctionInter;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * Created by Administrator on 2017-10-7.
 */
public class SendEmail implements FunctionInter {

    final String url = "http://api.sendcloud.net/apiv2/mail/send";
    final String apiUser = "myEscel_test_XU4ODn";
    final String apiKey = "myEscel_test_XU4ODn";
    final String from = "service@sendcloud.im";
    final String fromName = "myEscel_test_XU4ODn";

    private GridBagLayout gridBagLayout=new GridBagLayout();//网格布局
    @Override
    public JPanel getFunction(JPanel jPanel, JFrame jFrame) {
        jPanel.setLayout(gridBagLayout);
        this.editMsg(jPanel);
        //this.createFolders(jPanel);
        //this.createFolders1(jPanel);
        //this.createFolders2(jPanel);
        //this.registerListener(jPanel,jFrame);
        return jPanel;
    }

    @Override
    public JPanel getTitle(JPanel jPanel, JFrame jFrame) {
        jPanel.add(new JLabel("邮件发送"));
        return jPanel;
    }

    /**
     * 编辑邮件发送信息
     * @param jPanel
     */
    public void editMsg(JPanel jPanel){
        GridBagConstraints gridBagConstraints=new GridBagConstraints();//控制按钮显示组件
        gridBagConstraints.insets=new Insets(5,5,5,5);

        JLabel toUserName=new JLabel("收件人：");
        jPanel.add(toUserName);
        JTextField toUser=new JTextField();//文件地址
        toUser.setColumns(20);
        gridBagConstraints.gridwidth=2;
        gridBagLayout.setConstraints(toUser,gridBagConstraints);
        jPanel.add(toUser);
    }


    public String sendEMS(){
        String returnStr="error";
        try{
            HttpClient httpclient = new DefaultHttpClient();
            //ContentType.MULTIPART_FORM_DATA;
            HttpPost httPost = new HttpPost(url);
            List params = new ArrayList();
            // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
            params.add(new BasicNameValuePair("apiUser", apiUser));
            params.add(new BasicNameValuePair("apiKey", apiKey));
            params.add(new BasicNameValuePair("from", from));
            params.add(new BasicNameValuePair("fromName", fromName));
            params.add(new BasicNameValuePair("to", "收件人地址"));
            params.add(new BasicNameValuePair("subject", "来自SendCloud的第一封邮件！"));
            params.add(new BasicNameValuePair("html", "你太棒了！你已成功的从SendCloud发送了一封测试邮件，接下来快登录前台去完善账户信息吧！"));
            httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            // 请求
            HttpResponse response = httpclient.execute(httPost);
            // 处理响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
                // 读取xml文档
                String result = EntityUtils.toString(response.getEntity());
                returnStr=result;
            }
            httPost.releaseConnection();
        }catch (Exception ex){}
        return returnStr;
    }
}
