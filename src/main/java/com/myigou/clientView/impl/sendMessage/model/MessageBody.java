package com.myigou.clientView.impl.sendMessage.model;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * 消息实体类*
 */
public class MessageBody {

    // 消息产生时间
    @JSONField(ordinal = 1)
    private String messageTime;
    // 消息编号
    @JSONField(ordinal = 2)
    private String messageId;
    // 消息状态 1:正常 2:删除 3:撤回
    @JSONField(ordinal = 3)
    private String messageStatus;
    // 消息对象
    @JSONField(ordinal = 4)
    private String messageObject;
    // 消息类型
    @JSONField(ordinal = 5)
    private String messageType;
    // 消息内容
    @JSONField(ordinal = 6)
    private String message;
    // 消息备注说明
    @JSONField(ordinal = 7)
    private String messageRemark;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageObject() {
        return messageObject;
    }

    public void setMessageObject(String messageObject) {
        this.messageObject = messageObject;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageRemark() {
        return messageRemark;
    }

    public void setMessageRemark(String messageRemark) {
        this.messageRemark = messageRemark;
    }
}
