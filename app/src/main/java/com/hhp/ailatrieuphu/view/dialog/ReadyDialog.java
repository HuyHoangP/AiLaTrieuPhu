package com.hhp.ailatrieuphu.view.dialog;

import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.databinding.DialogReadyBinding;
import com.hhp.ailatrieuphu.view.OnMainCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

public class ReadyDialog extends BaseDialog<DialogReadyBinding, CommonVM, OnMainCallback> {
    public static final String TAG = ReadyDialog.class.getName();
    public ReadyDialog(OnMainCallback callback) {
        super(callback, null);
    }

    @Override
    public void initView() {
        setCancelable(false);
        MediaManager.getInstance().playReady();
        binding.btReady.setOnClickListener(view -> callBack.showFragment(PlayFragment.TAG, null, false));
        binding.btNo.setOnClickListener(view -> dismiss());
    }

    @Override
    public void onStop() {
        super.onStop();
        MediaManager.getInstance().pauseSong();
    }

    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public DialogReadyBinding initViewBinding() {
        return DialogReadyBinding.inflate(getLayoutInflater());
    }
}
