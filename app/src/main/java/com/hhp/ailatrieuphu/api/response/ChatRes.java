package com.hhp.ailatrieuphu.api.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRes {
    @SerializedName("choices")
    public List<Choices> choices;
    public static class Choices {
        @SerializedName("message")
        public Message message;
        public static class Message {
            @SerializedName("role")
            public String role;
            @SerializedName("content")
            public String content;

            @Override
            public String toString() {
                return "Messages{" +
                        "role='" + role + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }
        }
    }
}
