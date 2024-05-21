package com.hhp.ailatrieuphu.view.dialog;

import android.view.View;

import com.hhp.ailatrieuphu.databinding.DialogBackToMenuBinding;
import com.hhp.ailatrieuphu.databinding.DialogVictoryBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

public class VictoryDialog extends BaseDialog<DialogVictoryBinding, CommonVM, OnPlayCallback> {
    public static final String TAG = VictoryDialog.class.getName();
    public VictoryDialog(OnPlayCallback callback) {
        super(callback, null);
    }

    @Override
    public void initView() {
        setCancelable(false);
        binding.btBack.setOnClickListener(view -> dismiss());
        binding.btGoToMenu.setOnClickListener(view -> callBack.callback(PlayFragment.BACK_TO_MENU, null));

    }

    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public DialogVictoryBinding initViewBinding() {
        return DialogVictoryBinding.inflate(getLayoutInflater());
    }
}
