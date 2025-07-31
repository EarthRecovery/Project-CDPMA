package com.cdpma.common.pojo.pojo;

import java.util.Date;

public class SysNotification {

    private Long notificationId;
    private Long operatorId;
    private Date timestamp;
    private Long sender;
    private Long receiver;
    private Date sendTime;
    private Integer notificationType;
    private String content;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Integer notificationType) {
        this.notificationType = notificationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SysNotification{" +
                "notificationId=" + notificationId +
                ", operatorId=" + operatorId +
                ", timestamp=" + timestamp +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", sendTime=" + sendTime +
                ", notificationType=" + notificationType +
                ", content='" + content + '\'' +
                '}';
    }
}
