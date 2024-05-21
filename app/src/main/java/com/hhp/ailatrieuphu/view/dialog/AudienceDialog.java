package com.hhp.ailatrieuphu.view.dialog;

import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;

import androidx.lifecycle.MutableLiveData;

import com.hhp.ailatrieuphu.App;
import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.DialogAudienceBinding;
import com.hhp.ailatrieuphu.databinding.ItemColumnBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.fragmentvm.PlayFrgVM;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AudienceDialog extends BaseDialog<DialogAudienceBinding, PlayFrgVM, OnPlayCallback> {
    public static final String TAG = AudienceDialog.class.getName();

    public static final int MAX_COLUMN_HEIGHT = (int) App.getInstance().getResources().getDimension(R.dimen.d_300);


    public AudienceDialog(PlayFrgVM viewModel) {
        super(null, viewModel);
    }

    @Override
    public void initView() {
        setCancelable(false);
        binding.columnB.ivAnswer.setText("B");
        binding.columnC.ivAnswer.setText("C");
        binding.columnD.ivAnswer.setText("D");
        binding.btBack.setOnClickListener(view -> dismiss());
        initChart();
    }

    private void initChart() {
        if (viewModel.getStatusAudience().equals(PlayFragment.AVAILABLE)) {
            binding.btBack.setEnabled(false);
            MediaManager.getInstance().playMC(R.raw.help_audience, null);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    setColumn();
                    binding.btBack.setEnabled(true);
                }
            },5000);
            viewModel.setStatusAudience(PlayFragment.USING);
        } else if(viewModel.getStatusAudience().equals(PlayFragment.USING)) {
            setColumn();
        }
    }

    private void setColumn() {
        viewModel.drawColumn();
        updateColumn(binding.columnA, viewModel.getPercentA());
        updateColumn(binding.columnB, viewModel.getPercentB());
        updateColumn(binding.columnC, viewModel.getPercentC());
        updateColumn(binding.columnD, viewModel.getPercentD());
    }

    private void updateColumn(ItemColumnBinding column, MutableLiveData<Integer> percentLD) {
        percentLD.observe(this, height -> {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) column.viewColumn.getLayoutParams();
            params.height = height;
            float percent = (float) height * 100 / MAX_COLUMN_HEIGHT;
            column.viewColumn.setLayoutParams(params);
            column.viewColumn.postInvalidate();
            column.tvPercent.setText(String.format("%s%%", Math.round(percent)));

        });
    }


    @Override
    public Class<PlayFrgVM> initViewModel() {
        return PlayFrgVM.class;
    }

    @Override
    public DialogAudienceBinding initViewBinding() {
        return DialogAudienceBinding.inflate(getLayoutInflater());
    }
}
