package com.hhp.ailatrieuphu.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    private String id;
    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "level")
    private int level;
    @ColumnInfo(name = "casea")
    private String caseA;

    @ColumnInfo(name = "caseb")
    private String caseB;

    @ColumnInfo(name = "casec")
    private String caseC;

    @ColumnInfo(name = "cased")
    private String caseD;

    @ColumnInfo(name = "truecase")
    private String trueCase;

    public Question(@NonNull String id, @NonNull String question, int level, @NonNull String caseA, @NonNull String caseB, @NonNull String caseC, @NonNull String caseD, @NonNull String trueCase) {
        this.id = id;
        this.question = question;
        this.level = level;
        this.caseA = caseA;
        this.caseB = caseB;
        this.caseC = caseC;
        this.caseD = caseD;
        this.trueCase = trueCase;
    }


    public String getId() {
        return id;
    }


    public String getQuestion() {
        return question;
    }

    public int getLevel() {
        return level;
    }


    public String getCaseA() {
        return caseA;
    }


    public String getCaseB() {
        return caseB;
    }


    public String getCaseC() {
        return caseC;
    }


    public String getCaseD() {
        return caseD;
    }


    public String getTrueCase() {
        return trueCase;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", level=" + level +
                ", caseA='" + caseA + '\'' +
                ", caseB='" + caseB + '\'' +
                ", caseC='" + caseC + '\'' +
                ", caseD='" + caseD + '\'' +
                ", trueCase='" + trueCase + '\'' +
                '}';
    }
}
