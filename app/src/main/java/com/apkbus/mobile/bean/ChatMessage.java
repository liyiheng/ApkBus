package com.apkbus.mobile.bean;

/**
 * Created by liyiheng on 16/10/19.
 */

public class ChatMessage {
    private TYPE type;
    private String content;
    private long time;

    public ChatMessage() {
    }

    public ChatMessage(String content, long time, TYPE type) {
        this.content = content;
        this.time = time;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public boolean isSend() {
        return type == TYPE.SEND;
    }

    public enum TYPE {
        SEND, RECEIVE
    }
}
