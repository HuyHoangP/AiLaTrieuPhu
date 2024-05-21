package com.hhp.ailatrieuphu.viewmodel.activityvm;

import android.util.Log;

import com.hhp.ailatrieuphu.App;
import com.hhp.ailatrieuphu.database.entity.Question;
import com.hhp.ailatrieuphu.view.base.BaseViewModel;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActVM extends BaseViewModel {
    public static final String TAG = MainActVM.class.getName();
    public void initDB(){
        new Thread(() -> {
            App.getInstance().getStorage().listQuestion = App.getInstance().getDb().questionDAO().getAllQuestion();
            List<Question> listRankedQuestions = App.getInstance().getDb().questionDAO().getRankedQuestions();
            Collections.shuffle(listRankedQuestions);
            App.getInstance().getStorage().listRankedQuestions = listRankedQuestions;
        }).start();
    }

}
