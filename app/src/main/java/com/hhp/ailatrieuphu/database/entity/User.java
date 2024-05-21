package com.hhp.ailatrieuphu.database.entity;

import java.util.Objects;

public class User {
    public static final String DEFAULT_NAME = "USER";
    private String name;
    private int bestScore;

    private String avatar;

    public User() {
        this.name = DEFAULT_NAME;
        this.bestScore = 0;
        this.avatar = null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return bestScore <= user.bestScore && name.equalsIgnoreCase(user.name);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", bestScore=" + bestScore +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
