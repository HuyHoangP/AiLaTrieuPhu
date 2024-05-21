package com.hhp.ailatrieuphu.database.entity;

public class Message {
    private String text;
    private boolean isReq;

    public Message(String text, boolean isReq) {
        this.text = text;
        this.isReq = isReq;
    }


    public String getText() {
        return text;
    }

    public boolean isReq() {
        return isReq;
    }
}
