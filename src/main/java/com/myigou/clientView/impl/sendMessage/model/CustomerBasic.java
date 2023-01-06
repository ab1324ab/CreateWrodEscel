package com.myigou.clientView.impl.sendMessage.model;

/**
 * 用户基础信息*
 * 2022年12月31日17点27分*
 */
public class CustomerBasic {

    // 聊天对象唯一id
    protected String soloId;
    // 头像图片地址
    protected String avatarUrl;
    // 聊天对象名称
    protected String name;
    // 聊天对象ip
    protected String addressIp;

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

    public String getAddressIp() {
        return addressIp;
    }

    public void setAddressIp(String addressIp) {
        this.addressIp = addressIp;
    }
}
