package com.hhp.ailatrieuphu.database.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hhp.ailatrieuphu.database.QuestionDAO;
import com.hhp.ailatrieuphu.database.entity.Money;
import com.hhp.ailatrieuphu.database.entity.Question;

@Database(entities = {Question.class, Money.class},version = 1)
public abstract class QuestionDB extends RoomDatabase {
    public abstract QuestionDAO questionDAO();
}
