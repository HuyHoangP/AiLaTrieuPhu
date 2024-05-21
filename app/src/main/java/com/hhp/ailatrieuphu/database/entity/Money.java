package com.hhp.ailatrieuphu.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Money {
    @PrimaryKey
    @ColumnInfo(name = "Amount")
    private int amount;

    public int getAmount() {
        return amount;
    }

    public Money(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount='" + amount + '\'' +
                '}';
    }
}
