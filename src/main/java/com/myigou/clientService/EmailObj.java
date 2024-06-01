package com.myigou.clientService;

import java.io.File;

/**
 * @author ab1324ab
 *         Created by Administrator on 2017-10-7.
 */
public class EmailObj {

    public String apiUser = "";
    public String apiKey = "";
    // 收件人地址. 多个地址使用';'分隔
    public String to = "";
    public String from = "";
    public String fromName = "";
    public String replyTo = "";
    public String cc = "";
    public String bcc = "";
    public String subject = "";
    public String html = "";
    public File attachments = null;
}
