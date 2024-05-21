package com.hhp.ailatrieuphu;

import android.app.Application;

import androidx.room.Room;

import com.hhp.ailatrieuphu.database.db.QuestionDB;

public class App extends Application {
    private static App instance;
    private Storage storage;
    private QuestionDB db;

    public QuestionDB getDb() {
        return db;
    }
    public static App getInstance(){
        return instance;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        storage = new Storage();
        synchronized (QuestionDB.class){
            db = Room.databaseBuilder(this, QuestionDB.class, "CSDL-BoCauHoi")
                    .createFromAsset("db/Question")
                    .build();
        }
    }
}
