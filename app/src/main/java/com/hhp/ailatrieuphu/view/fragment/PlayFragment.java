package com.hhp.ailatrieuphu.view.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.hhp.ailatrieuphu.App;
import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.database.entity.Question;
import com.hhp.ailatrieuphu.databinding.FragmentPlayBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.activity.MainActivity;
import com.hhp.ailatrieuphu.view.base.BaseFragment;
import com.hhp.ailatrieuphu.view.dialog.AudienceDialog;
import com.hhp.ailatrieuphu.view.dialog.BackToMenuDialog;
import com.hhp.ailatrieuphu.view.dialog.BotGPTDialog;
import com.hhp.ailatrieuphu.view.dialog.CallDialog;
import com.hhp.ailatrieuphu.view.dialog.ConsulterDialog;
import com.hhp.ailatrieuphu.view.dialog.HelpConfirmDialog;
import com.hhp.ailatrieuphu.view.dialog.SurrenderDialog;
import com.hhp.ailatrieuphu.view.dialog.VictoryDialog;
import com.hhp.ailatrieuphu.viewmodel.fragmentvm.PlayFrgVM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayFragment extends BaseFragment<FragmentPlayBinding, PlayFrgVM> implements OnPlayCallback {
    public static final String TAG = PlayFragment.class.getName();
    public static final String ANSWER_UNSELECTED = "unselected";
    public static final String ANSWER_SELECTED = "selected";
    public static final String ANSWER_CORRECT = "correct";
    public static final String ANSWER_WRONG = "wrong";
    public static final int COST_5050 = -1500;
    public static final int COST_CALL = -3000;
    public static final int COST_CONSULTER = -2000;
    public static final int COST_AUDIENCE = -2500;
    public static final String HELP_5050 = "50:50";
    public static final String HELP_CALL = "gọi điện thoại cho người thân";
    public static final String HELP_CONSULTER = "hỏi ý kiến tổ tư vấn";
    public static final String HELP_AUDIENCE = "hỏi ý kiến khán giả trong trường quay";
    public static final int CHECKPOINT_1 = 4;
    public static final int CHECKPOINT_2 = 9;
    public static final int[] MONEY_CP = {0, 2000, 4000, 6000, 10000, 20000, 30000, 60000, 100000, 140000, 220000, 300000, 400000, 600000, 850000, 1500000};
    public static final int[] AUDIO_TRUE_ID = {R.raw.true_a, R.raw.true_b, R.raw.true_c, R.raw.true_d};
    public static final int[] AUDIO_LOSE_ID = {R.raw.lose_a, R.raw.lose_b, R.raw.lose_c, R.raw.lose_d};
    public static final int[] AUDIO_QUES_ID = {R.raw.ques1, R.raw.ques2, R.raw.ques3, R.raw.ques4, R.raw.ques5, R.raw.ques6, R.raw.ques7, R.raw.ques8, R.raw.ques9, R.raw.ques10, R.raw.ques11, R.raw.ques12, R.raw.ques13, R.raw.ques14, R.raw.ques15};
    public static final String OPEN_CALLED_DIALOG = "Create called dialog using consulter dialog";
    public static final String BACK_TO_MENU = "Back to Menu";
    public static final String SURRENDER = "Surrender";
    public static final String AVAILABLE = "Available";
    public static final String USING = "Using";
    public static final String UNAVAILABLE = "Unavailable";
    public static final String BEST_SCORE = "bestScore";


    @Override
    public void callback(String key, Object data) {
        switch (key) {
            case HELP_5050:
                use5050();
                break;
            case HELP_CALL:
                useCall();
                break;
            case HELP_CONSULTER:
                useConsulter();
                break;
            case HELP_AUDIENCE:
                useAudience();
                break;
            case OPEN_CALLED_DIALOG:
                openCalledDialog();
                break;
            case BACK_TO_MENU:
                goToMenu();
                break;
            case SURRENDER:
                surrender();
                break;
        }
    }

    @Override
    public void initView() {
        if(data!=null){
            viewModel.setRanked((Boolean) data);
        }
        setHelperData(viewModel.isRanked()?0:1);
        setMoneyUI();
        initQuestion();
        clickListenersHandler();
        backPressHandler();
        if(viewModel.isRanked()){
            binding.ivBackground.setElevation(0);
            Glide.with(context).load(R.mipmap.bg_space).centerCrop().into(binding.ivBackground);
        }
    }

    private void resetData() {
        viewModel.setAnswer(ANSWER_UNSELECTED);
        viewModel.getTvAnswers().clear();
        viewModel.getTvAnswers().add(binding.tvKeyA);
        viewModel.getTvAnswers().add(binding.tvKeyB);
        viewModel.getTvAnswers().add(binding.tvKeyC);
        viewModel.getTvAnswers().add(binding.tvKeyD);

        viewModel.setConsulterDialog(null);
        viewModel.setCalledDialog(null);
        viewModel.setAudienceDialog(null);
    }

    private void setHelperData(int usage) {
        binding.iv5050.setTag(new Object[]{HELP_5050, COST_5050, R.mipmap.ic_5050});
        binding.ivCall.setTag(new Object[]{HELP_CALL, COST_CALL, R.mipmap.ic_call});
        binding.ivConsulter.setTag(new Object[]{HELP_CONSULTER, COST_CONSULTER, R.mipmap.ic_consult});
        binding.ivAudience.setTag(new Object[]{HELP_AUDIENCE, COST_AUDIENCE, R.mipmap.bg_help});

        HashMap<String, Integer> helpUsage = new HashMap<>();
        helpUsage.put(HELP_5050, usage);
        helpUsage.put(HELP_CALL, usage);
        helpUsage.put(HELP_CONSULTER, usage);
        helpUsage.put(HELP_AUDIENCE, usage);
        viewModel.setHelpUsage(helpUsage);
    }

    private void clickListenersHandler() {
        viewModel.setOnClickEnabled(true);
        binding.tvKeyA.setOnClickListener(this);
        binding.tvKeyB.setOnClickListener(this);
        binding.tvKeyC.setOnClickListener(this);
        binding.tvKeyD.setOnClickListener(this);
        binding.btNext.setOnClickListener(this);
        binding.ivStop.setOnClickListener(this);
        binding.iv5050.setOnClickListener(this);
        binding.ivAudience.setOnClickListener(this);
        binding.ivCall.setOnClickListener(this);
        binding.ivConsulter.setOnClickListener(this);
        binding.ivStop.setOnClickListener(this);
        binding.tvTimer.setOnClickListener(this);
        binding.tvQuestionNum.setOnClickListener(this);
        binding.ivBack.setOnClickListener(view -> {
            BackToMenuDialog dialog = new BackToMenuDialog(PlayFragment.this);
            dialog.show(getChildFragmentManager(), BackToMenuDialog.TAG);
        });
    }

    private void goToMenu() {
        callback.callback(MainActivity.KEY_INIT_DB, null);
        callback.showFragment(MenuFragment.TAG, null, false);
    }

    @Override
    public Class<PlayFrgVM> initViewModel() {
        return PlayFrgVM.class;
    }

    @Override
    public FragmentPlayBinding initViewBinding() {
        return FragmentPlayBinding.inflate(getLayoutInflater());
    }

    private void setMoneyUI() {
        if(viewModel.isRanked()){
            binding.trMoney.setVisibility(View.GONE);
            binding.tvScore.setVisibility(View.VISIBLE);
            return;
        }
        viewModel.getMoneyLD().observe(this, money -> binding.tvMoney.setText(String.format("%s", money.getAmount())));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(viewModel.isRanked()){
            MediaManager.getInstance().playRanked();
        } else {
            MediaManager.getInstance().playGame();
        }
    }

    private void backPressHandler() {
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });
    }

    private void initQuestion() {
        viewModel.getIndexLD().observe(this, index -> {
            if (index >= 15 && !viewModel.isRanked()) {
                victory();
                return;
            }
            resetData();
            setAnswerUI(index);
            checkHelpUsage();
        });
    }

    private void checkHelpUsage() {
        if (viewModel.getHelpUsage().get(HELP_5050) <= 0) {
            binding.iv5050.setEnabled(false);
            binding.iv5050.setImageResource(R.mipmap.bg_help);
            viewModel.setStatus5050(UNAVAILABLE);
        } else {
            viewModel.setStatus5050(AVAILABLE);
        }
        if (viewModel.getHelpUsage().get(HELP_CALL) <= 0) {
            binding.ivCall.setEnabled(false);
            binding.ivCall.setImageResource(R.mipmap.bg_help);
        }
        if (viewModel.getHelpUsage().get(HELP_CONSULTER) <= 0) {
            binding.ivConsulter.setEnabled(false);
            binding.ivConsulter.setImageResource(R.mipmap.bg_help);
        }
        if (viewModel.getHelpUsage().get(HELP_AUDIENCE) <= 0) {
            binding.ivAudience.setEnabled(false);
            binding.ivAudience.setImageResource(R.mipmap.bg_help);
            viewModel.setStatusAudience(UNAVAILABLE);
        } else {
            viewModel.setStatusAudience(AVAILABLE);
        }
    }

    private void victory() {
        viewModel.setOnClickEnabled(false);
        updateMoney(MONEY_CP[MONEY_CP.length - 1]);
        VictoryDialog dialog = new VictoryDialog(this);
        dialog.show(getChildFragmentManager(), VictoryDialog.TAG);
    }

    private void setAnswerUI(Integer index) {
        if(viewModel.isRanked()){
            countDown();
        } else {
            binding.frTimer.setVisibility(View.INVISIBLE);
        }
        if(index < AUDIO_QUES_ID.length){
            MediaManager.getInstance().playMC(AUDIO_QUES_ID[index], null);
        }
        viewModel.getIsCorrectLD().setValue(ANSWER_SELECTED);
        Question question = viewModel.isRanked()?
                App.getInstance().getStorage().listRankedQuestions.get(index):
                App.getInstance().getStorage().listQuestion.get(index);
        viewModel.setCorrectAnswer(question.getTrueCase());
        binding.tvScore.setText(String.format("Score: %s", index));
        binding.tvQuestionNum.setText(String.format("Câu %s", index + 1));
        binding.tvQuestion.setText(String.format("%s", question.getQuestion()));
        binding.tvKeyA.setText(String.format("%s", question.getCaseA()));
        binding.tvKeyB.setText(String.format("%s", question.getCaseB()));
        binding.tvKeyC.setText(String.format("%s", question.getCaseC()));
        binding.tvKeyD.setText(String.format("%s", question.getCaseD()));
        resetAnswerUI(false);
    }

    private TextView findCorrectAnswer() {
        return viewModel.getTvAnswers().get(Integer.parseInt(viewModel.getCorrectAnswer()) - 1);
    }

    private void countDown() {
        viewModel.countDown(30);
        viewModel.getTimerLD().observe(this, timer -> {
            if (timer < 0) return;
            binding.tvTimer.setText(String.format("%s", timer));
            if (timer == 0) {
                gameOver(null);
            }
        });
    }

    @Override
    protected void clickView(View view) {
        if (!viewModel.isOnClickEnabled()) return;
        if (view.getId() == R.id.tvKeyA || view.getId() == R.id.tvKeyB || view.getId() == R.id.tvKeyC || view.getId() == R.id.tvKeyD) {
            selectAnswer(view);
        } else if (view.getId() == R.id.btNext) {
            checkAnswer(view);
        } else if (view.getId() == R.id.ivStop) {
            openSurrenderDialog();
        } else if (view.getId() == R.id.iv5050 || view.getId() == R.id.ivCall || view.getId() == R.id.ivConsulter || view.getId() == R.id.ivAudience) {
            openConfirmDialog((Object[]) view.getTag());
        } else if (view.getId() == R.id.tvTimer) {
            updateMoney(1000);
        } else if (view.getId() == R.id.tvQuestionNum) {
            Toast.makeText(context, viewModel.getCorrectAnswer(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswer(View view) {
        if (viewModel.getAnswer().equals(ANSWER_UNSELECTED)) {
            //Snackbar
            final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
            View customSnackView = getLayoutInflater().inflate(R.layout.item_alert_unselected, null);
            snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
            Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
            snackbarLayout.setPadding(0, 0, 0, 0);
            snackbarLayout.addView(customSnackView, 0);
            snackbar.show();
        } else {
            viewModel.checkAnswer(view);
        }
    }

    private void openSurrenderDialog() {
            SurrenderDialog dialog = new SurrenderDialog(this, viewModel);
            dialog.show(getChildFragmentManager(), SurrenderDialog.TAG);

    }

    private void surrender() {
        viewModel.setCheckpoint(viewModel.getIndexLD().getValue());
        gameOver(null);
    }

    private void updateMoney(int amount) {
        if(viewModel.isRanked()){
            viewModel.updateScore();
        } else if (viewModel.checkMoney(amount)) {
            viewModel.updateMoney(amount);
        }
    }

    private void useAudience() {
        if (viewModel.getAudienceDialog() == null) {
            updateMoney(COST_AUDIENCE);
            viewModel.getHelpUsage().replace(HELP_AUDIENCE, viewModel.getHelpUsage().get(HELP_AUDIENCE) - 1);
            viewModel.initABCDPercent();
            AudienceDialog dialog = new AudienceDialog(viewModel);
            viewModel.setAudienceDialog(dialog);
        }
        viewModel.getAudienceDialog().show(getChildFragmentManager(), AudienceDialog.TAG);
    }

    private void useConsulter() {
        if (viewModel.getConsulterDialog() == null) {
            updateMoney(COST_CONSULTER);
            viewModel.setConsulters();
            viewModel.getHelpUsage().replace(HELP_CONSULTER, viewModel.getHelpUsage().get(HELP_CONSULTER) - 1);
            viewModel.setOnClickEnabled(false);
            MediaManager.getInstance().playMC(R.raw.help_consulters, mediaPlayer -> {
                ConsulterDialog dialog = new ConsulterDialog(viewModel);
                viewModel.setConsulterDialog(dialog);
                viewModel.setOnClickEnabled(true);
                viewModel.getConsulterDialog().show(getChildFragmentManager(), ConsulterDialog.TAG);
            });
        } else {
            viewModel.getConsulterDialog().show(getChildFragmentManager(), ConsulterDialog.TAG);
        }
    }

    private void useCall() {
        if (viewModel.getCalledDialog() == null) {
            updateMoney(COST_CALL);
            viewModel.setConsulters();
            viewModel.getHelpUsage().replace(HELP_CALL, viewModel.getHelpUsage().get(HELP_CALL) - 1);
            CallDialog dialog = new CallDialog(this, viewModel);
            dialog.show(getChildFragmentManager(), CallDialog.TAG);
        } else {
            viewModel.getCalledDialog().show(getChildFragmentManager(), ConsulterDialog.TAG);
        }
    }

    private void use5050() {
        viewModel.setOnClickEnabled(false);
        binding.iv5050.setEnabled(false);
        viewModel.setStatus5050(USING);
        updateMoney(COST_5050);
        viewModel.getHelpUsage().replace(HELP_5050, viewModel.getHelpUsage().get(HELP_5050) - 1);
        int rightIndex = Integer.parseInt(viewModel.getCorrectAnswer()) - 1;
        List<Integer> answerIndex = new ArrayList<>();
        for (int i = 0; i < viewModel.getTvAnswers().size(); i++) {
            answerIndex.add(i);
        }
        answerIndex.remove(rightIndex);
        Collections.shuffle(answerIndex);
        int wrongIndex1 = answerIndex.get(0);
        int wrongIndex2 = answerIndex.get(1);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            hideAnswer(wrongIndex1);
            hideAnswer(wrongIndex2);
            viewModel.setOnClickEnabled(true);
        }, 3, TimeUnit.SECONDS);
        executor.shutdown();
        MediaManager.getInstance().playMC(R.raw.help_5050, null);
    }

    private void hideAnswer(int index) {
        viewModel.getTvAnswers().get(index).setEnabled(false);
        viewModel.getTvAnswers().get(index).setTextColor(ContextCompat.getColor(context, android.R.color.transparent));
        viewModel.getTvAnswers().get(index).getBackground().setLevel(0);
        viewModel.getTvAnswers().set(index, null);
        viewModel.getPercents()[index] = -1;
    }

    private void openCalledDialog() {
        viewModel.setOnClickEnabled(false);
        MediaManager.getInstance().playMC(R.raw.help_called, mediaPlayer -> {
            BotGPTDialog dialog = new BotGPTDialog();
            dialog.show(getChildFragmentManager(), BotGPTDialog.TAG);
            viewModel.setOnClickEnabled(true);
        });
        binding.ivCall.setEnabled(false);
    }

    private void openConfirmDialog(Object[] data) {
        String keyHelp = (String) data[0];
        if (checkHelpDialog(keyHelp)) {
            HelpConfirmDialog dialog = new HelpConfirmDialog(this, viewModel, data);
            dialog.show(getChildFragmentManager(), HelpConfirmDialog.TAG);
        } else {
            callback(keyHelp, null);
        }
    }

    private boolean checkHelpDialog(String key) {
        switch (key) {
            case HELP_CONSULTER:
                return viewModel.getConsulterDialog() == null;
            case HELP_CALL:
                return viewModel.getCalledDialog() == null;
            case HELP_AUDIENCE:
                return viewModel.getAudienceDialog() == null;
        }
        return true;
    }

    private void selectAnswer(View view) {
        viewModel.setAnswer(view.getTag().toString());
        viewModel.getIsCorrectLD().observe(this, answerStatus -> {
            switch (answerStatus) {
                case ANSWER_SELECTED:
                    setAnswerBackground(view, 1);
                    break;
                case ANSWER_CORRECT:
                    nextQuestion(view);
                    break;
                case ANSWER_WRONG:
                    gameOver(view);
                    break;
            }
        });
    }

    private void nextQuestion(View view) {
        int rightIndex = Integer.parseInt(viewModel.getCorrectAnswer()) - 1;
        viewModel.setOnClickEnabled(false);
        MediaManager.getInstance().playMC(AUDIO_TRUE_ID[rightIndex], mediaPlayer -> {
            viewModel.nextQuestion();
            viewModel.setOnClickEnabled(true);
        });
        setAnswerBackground(view, 2);
    }

    private void gameOver(View view) {
        if (view != null) setAnswerBackground(view, 3);
        if(!viewModel.isRanked()) viewModel.setOnClickEnabled(false);

        int rightIndex = Integer.parseInt(viewModel.getCorrectAnswer()) - 1;
        MediaManager.getInstance().playMC(AUDIO_LOSE_ID[rightIndex], null);
        MediaManager.getInstance().pauseSong();
        TextView correctView = findCorrectAnswer();
        correctView.getBackground().setLevel(2);
        updateMoney(MONEY_CP[viewModel.getCheckpoint()]);
    }

    private void setAnswerBackground(View view, int lv) {
        int index = Integer.parseInt((String) view.getTag()) - 1;
        if (viewModel.getTvAnswers().get(index) != null) {
            resetAnswerUI(true);
            view.getBackground().setLevel(lv);
        }
    }

    private void resetAnswerUI(boolean onlyBg) {
        for (TextView answer : viewModel.getTvAnswers()) {
            if (answer == null) continue;
            answer.getBackground().setLevel(0);
            if (!onlyBg) {
                answer.setEnabled(true);
                answer.setTextColor(ContextCompat.getColor(context, R.color.grey));
            }
        }
    }

}
