package com.hhp.ailatrieuphu.viewmodel.fragmentvm;

import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.ANSWER_CORRECT;
import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.ANSWER_UNSELECTED;
import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.ANSWER_WRONG;
import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.AVAILABLE;
import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.BEST_SCORE;
import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.CHECKPOINT_1;
import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.CHECKPOINT_2;
import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.TAG;
import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.USING;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hhp.ailatrieuphu.App;
import com.hhp.ailatrieuphu.CommonUtils;
import com.hhp.ailatrieuphu.database.entity.Money;
import com.hhp.ailatrieuphu.view.dialog.AudienceDialog;
import com.hhp.ailatrieuphu.view.dialog.ConsulterDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class PlayFrgVM extends AndroidViewModel {
    private Thread thread;
    private final List<TextView> tvAnswers = new ArrayList<>();
    private HashMap<String, Integer> helpUsage;
    private ConsulterDialog consulterDialog, calledDialog;
    private AudienceDialog audienceDialog;
    private HashMap<String, String> consulters;
    private boolean onClickEnabled;
    private final MutableLiveData<Integer> indexLD = new MutableLiveData<>(0);
    private int checkpoint = 0;
    private MutableLiveData<Integer> timerLD;
    private final MutableLiveData<String> isCorrectLD = new MutableLiveData<>(ANSWER_UNSELECTED);
    private final LiveData<Money> moneyLD;
    private String answer = ANSWER_UNSELECTED;
    private String correctAnswer;
    private int currentMoney;
    private final MutableLiveData<Integer> percentA = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> percentB = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> percentC = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> percentD = new MutableLiveData<>(0);
    private final int[] percents = {0, 0, 0, 0};
    private String status5050 = AVAILABLE;
    private String statusAudience = AVAILABLE;
    private boolean isRanked;

    public boolean isRanked() {
        return isRanked;
    }

    public void setRanked(boolean ranked) {
        isRanked = ranked;
    }

    public PlayFrgVM(@NonNull Application application) {
        super(application);
        moneyLD = App.getInstance().getDb().questionDAO().getMoney();
    }

    public void setStatus5050(String status5050) {
        this.status5050 = status5050;
    }

    public String getStatusAudience() {
        return statusAudience;
    }

    public void setStatusAudience(String statusAudience) {
        this.statusAudience = statusAudience;
    }
    public int[] getPercents() {
        return percents;
    }

    public HashMap<String, String> getConsulters() {
        return consulters;
    }

    public HashMap<String, Integer> getHelpUsage() {
        return helpUsage;
    }

    public void setHelpUsage(HashMap<String, Integer> helpUsage) {
        this.helpUsage = helpUsage;
    }

    public MutableLiveData<Integer> getPercentA() {
        return percentA;
    }

    public MutableLiveData<Integer> getPercentB() {
        return percentB;
    }

    public MutableLiveData<Integer> getPercentC() {
        return percentC;
    }

    public MutableLiveData<Integer> getPercentD() {
        return percentD;
    }

    public ConsulterDialog getConsulterDialog() {
        return consulterDialog;
    }

    public void setConsulterDialog(ConsulterDialog consulterDialog) {
        this.consulterDialog = consulterDialog;
    }

    public ConsulterDialog getCalledDialog() {
        return calledDialog;
    }

    public void setCalledDialog(ConsulterDialog calledDialog) {
        this.calledDialog = calledDialog;
    }

    public AudienceDialog getAudienceDialog() {
        return audienceDialog;
    }

    public void setAudienceDialog(AudienceDialog audienceDialog) {
        this.audienceDialog = audienceDialog;
    }

    public List<TextView> getTvAnswers() {
        return tvAnswers;
    }

    public int getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }

    public boolean isOnClickEnabled() {
        return onClickEnabled;
    }

    public void setOnClickEnabled(boolean onClickEnabled) {
        this.onClickEnabled = onClickEnabled;
    }

    public MutableLiveData<String> getIsCorrectLD() {
        return isCorrectLD;
    }

    public LiveData<Money> getMoneyLD() {
        return moneyLD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public MutableLiveData<Integer> getTimerLD() {
        return timerLD;
    }

    public MutableLiveData<Integer> getIndexLD() {
        return indexLD;
    }


    public void countDown(int timer) {
        timerLD = new MutableLiveData<>(timer);
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                while (timerLD.getValue() != null || timerLD.getValue() > 0) {
                    try {
                        Thread.sleep(1000);
                        timerLD.postValue(timerLD.getValue() - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            });
            thread.start();
        }
    }

    public void checkAnswer(View view) {
        if (answer.equals(correctAnswer)) {
            isCorrectLD.postValue(ANSWER_CORRECT);
        } else {
            isCorrectLD.postValue(ANSWER_WRONG);
        }
    }

    public void setConsulters() {
        List<String> consulterInv = Arrays.asList(App.getInstance().getStorage().consulterInv);
        Collections.shuffle(consulterInv);
        consulters = new HashMap<>();
        consulters.put(consulterInv.get(0), correctAnswer);
        consulters.put(consulterInv.get(1), correctAnswer);
        consulters.put(consulterInv.get(2), correctAnswer);
    }

    public void nextQuestion() {
        indexLD.postValue(indexLD.getValue() + 1);
        if (indexLD.getValue() > CHECKPOINT_2) {
            setCheckpoint(CHECKPOINT_2);
        } else if (indexLD.getValue() > CHECKPOINT_1) {
            setCheckpoint(CHECKPOINT_1);
        }
    }

    public boolean checkMoney(int amount) {
        currentMoney = moneyLD.getValue().getAmount();
        return amount >= 0 || currentMoney >= Math.abs(amount);
    }

    public void updateMoney(int amount) {
        new Thread(() -> {
            int newMoney = currentMoney + amount;
            App.getInstance().getDb().questionDAO().updateMoney(newMoney);
        }).start();
    }

    public void drawColumn() {
        int hA = AudienceDialog.MAX_COLUMN_HEIGHT * percents[0] / 100;
        int hB = AudienceDialog.MAX_COLUMN_HEIGHT * percents[1] / 100;
        int hC = AudienceDialog.MAX_COLUMN_HEIGHT * percents[2] / 100;
        int hD = AudienceDialog.MAX_COLUMN_HEIGHT * percents[3] / 100;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < AudienceDialog.MAX_COLUMN_HEIGHT; i++) {
                    if (i <= hA) {
                        percentA.postValue(i);
                    }
                    if (i <= hB) {
                        percentB.postValue(i);
                    }
                    if (i <= hC) {
                        percentC.postValue(i);
                    }
                    if (i <= hD) {
                        percentD.postValue(i);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void initABCDPercent() {
        int rightIndex = Integer.parseInt(correctAnswer) - 1;
        Random rd = new Random();
        percents[rightIndex] = rd.nextInt(70) + 30;
        int wrong1 = rd.nextInt(100 - percents[rightIndex]);
        int wrong2 = rd.nextInt(100 - wrong1 - percents[rightIndex]);
        int wrong3 = 100 - wrong1 - wrong2 - percents[rightIndex];
        int[] wrongPercents = {wrong1, wrong2, wrong3};
        int j = 0;
        for (int i = 0; i < percents.length; i++) {
            if (i == rightIndex) continue;
            if (percents[i] == 0) {
                percents[i] = status5050.equals(USING) ? 100 - percents[rightIndex] : wrongPercents[j];
            } else if (percents[i] == -1) {
                percents[i] = 0;
            }
            j++;
        }
        Log.i(TAG, "initABCDPercent: " + Arrays.toString(percents));
    }


    public void updateScore() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("list_user");
        String path = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "-");
        dbRef.child(path).child("bestScore").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    int best = Math.toIntExact((long) task.getResult().getValue());
                    int score = indexLD.getValue();
                    if(score > best) {
                        Map<String, Object> mapUpdate = new HashMap<>();
                        mapUpdate.put("bestScore", score);
                        dbRef.child(path).updateChildren(mapUpdate);
                    }
                }
            }
        });
    }
}
