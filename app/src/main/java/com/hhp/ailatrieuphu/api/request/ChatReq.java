package com.hhp.ailatrieuphu.api.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatReq {
    @SerializedName("model")
    public String model;
    @SerializedName("temperature")
    public double temperature;
    @SerializedName("messages")
    public List<Messages> messages;
    public static class Messages {
        @SerializedName("role")
        public String role;
        @SerializedName("content")
        public String content;
        public Messages(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    public ChatReq(String model, double temperature, List<Messages> messages) {
        this.model = model;
        this.temperature = temperature;
        this.messages = messages;
    }
}
