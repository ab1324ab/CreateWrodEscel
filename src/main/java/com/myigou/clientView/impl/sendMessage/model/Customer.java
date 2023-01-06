package com.myigou.clientView.impl.sendMessage.model;

import com.myigou.clientView.impl.sendMessage.module.Interlocutor;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户对象*
 * 2022年11月22日19点20分*
 */
public class Customer extends CustomerBasic {

    // 对象头像 面板数据
    private Interlocutor interlocutor;
    // 管道
    private PrintWriter writer;
    private Socket socket;

    public Customer(String soloId, String avatarUrl, String name, String addressIp) {
        this.soloId = soloId;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.addressIp = addressIp;
    }

    public Customer() {
    }

    public String getSoloId() {
        return soloId;
    }

    public void setSoloId(String soloId) {
        this.soloId = soloId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Interlocutor getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(Interlocutor interlocutor) {
        this.interlocutor = interlocutor;
    }

    public String getAddressIp() {
        return addressIp;
    }

    public void setAddressIp(String addressIp) {
        this.addressIp = addressIp;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
