package com.hhp.ailatrieuphu.view.dialog;

import android.view.View;

import androidx.core.content.ContextCompat;

import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.DialogHelpConfirmBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.fragmentvm.PlayFrgVM;

public class HelpConfirmDialog extends BaseDialog<DialogHelpConfirmBinding, PlayFrgVM, OnPlayCallback> {
    public static final String TAG = HelpConfirmDialog.class.getName();
    private final String key;
    private final int cost;
    private final int imageId;

    public HelpConfirmDialog(OnPlayCallback callback, PlayFrgVM viewModel, Object[] data) {
        super(callback, viewModel);
        key = (String) data[0];
        cost = (int) data[1];
        imageId =(int) data[2];
    }

    @Override
    public void initView() {
        binding.btNotYet.setOnClickListener(view -> dismiss());
        if(viewModel.checkMoney(cost)){
            binding.trUseHelp.setOnClickListener(view -> {
                callBack.callback(key, null);
                dismiss();
            });
        } else {
            binding.tvCost.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        }

        binding.tvHelpMessage.setText(String.format(getResources().getString(R.string.help_message) + " %s?", key));
        binding.tvCost.setText(String.format("%s", Math.abs(cost)));
        binding.ivHelp.setImageResource(imageId);
        if (key.equals(PlayFragment.HELP_AUDIENCE)) {
            binding.ivChart.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public Class<PlayFrgVM> initViewModel() {
        return PlayFrgVM.class;
    }

    @Override
    public DialogHelpConfirmBinding initViewBinding() {
        return DialogHelpConfirmBinding.inflate(getLayoutInflater());
    }

}
