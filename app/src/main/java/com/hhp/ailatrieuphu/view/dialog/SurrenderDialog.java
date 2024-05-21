package com.hhp.ailatrieuphu.view.dialog;

import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.BEST_SCORE;

import android.view.View;

import com.hhp.ailatrieuphu.CommonUtils;
import com.hhp.ailatrieuphu.databinding.DialogSurrenderBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.fragmentvm.PlayFrgVM;

public class SurrenderDialog extends BaseDialog<DialogSurrenderBinding, PlayFrgVM, OnPlayCallback> {
    public static final String TAG = SurrenderDialog.class.getName();

    public SurrenderDialog(OnPlayCallback callback, PlayFrgVM viewModel) {
        super(callback, viewModel);
    }

    @Override
    public void initView() {
        setCancelable(false);
        binding.btBack.setOnClickListener(view -> dismiss());
        if (viewModel.isRanked()) {
            String best = CommonUtils.getInstance().getPref(BEST_SCORE);
            binding.tvDesc.setText(String.format("Điểm cao nhất của bạn: %s", best == null ? "0": best));
            binding.tvDesc.setPadding(20,40,20,20);
            binding.trBounty.setVisibility(View.GONE);
            binding.btStop.setVisibility(View.GONE);
        } else {
            binding.tvBounty.setText(String.format("%s", PlayFragment.MONEY_CP[viewModel.getIndexLD().getValue()]));
            binding.btStop.setOnClickListener(view -> {
                dismiss();
                callBack.callback(PlayFragment.SURRENDER, null);
            });
        }

    }


    @Override
    public Class<PlayFrgVM> initViewModel() {
        return PlayFrgVM.class;
    }

    @Override
    public DialogSurrenderBinding initViewBinding() {
        return DialogSurrenderBinding.inflate(getLayoutInflater());
    }
}
