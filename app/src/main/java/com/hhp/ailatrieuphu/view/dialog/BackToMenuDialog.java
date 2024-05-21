package com.hhp.ailatrieuphu.view.dialog;

import android.view.View;

import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.databinding.DialogBackToMenuBinding;
import com.hhp.ailatrieuphu.databinding.DialogReadyBinding;
import com.hhp.ailatrieuphu.view.OnMainCallback;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

public class BackToMenuDialog extends BaseDialog<DialogBackToMenuBinding, CommonVM, OnPlayCallback> {
    public static final String TAG = BackToMenuDialog.class.getName();
    public BackToMenuDialog(OnPlayCallback callback) {
        super(callback, null);
    }

    @Override
    public void initView() {
        setCancelable(false);
        binding.btBack.setOnClickListener(view -> dismiss());
        binding.btGoToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.callback(PlayFragment.BACK_TO_MENU, null);
            }
        });

    }

    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public DialogBackToMenuBinding initViewBinding() {
        return DialogBackToMenuBinding.inflate(getLayoutInflater());
    }
}
