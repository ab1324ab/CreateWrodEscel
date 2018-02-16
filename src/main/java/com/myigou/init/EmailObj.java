package com.myigou.init;

import java.io.File;

/**
 * Created by Administrator on 2017-10-7.
 */
public class EmailObj {

    public String apiUser="";
    public String apiKey="";
    public String to="";//收件人地址. 多个地址使用';'分隔
    public String from="";
    public String fromName="";
    public String replyTo="";
    public String cc="";
    public String bcc="";
    public String subject="";
    public String html="";
    public File   attachments=null;

}
