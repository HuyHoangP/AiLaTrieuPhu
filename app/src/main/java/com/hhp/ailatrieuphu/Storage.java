package com.hhp.ailatrieuphu;

import androidx.lifecycle.LiveData;

import com.hhp.ailatrieuphu.database.entity.Money;
import com.hhp.ailatrieuphu.database.entity.Question;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Storage {
    public List<Question> listQuestion;
    public List<Question> listRankedQuestions;
    public String[] consulterInv = {"Mr.0", "Mr.1","Mr.2","Mr.3","Mr.4","Mr.5","Mr.6","Mr.7","Mr.8","Mr.9","Mr.10"};
    public HashMap<String, String> keyAns;

    public Storage() {
        keyAns = new HashMap<>();
        keyAns.put("1","A");
        keyAns.put("2","B");
        keyAns.put("3","C");
        keyAns.put("4","D");
    }
}
