package com.hhp.ailatrieuphu.view.dialog;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hhp.ailatrieuphu.databinding.DialogSignOutConfirmBinding;
import com.hhp.ailatrieuphu.view.OnMainCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.LoginFragment;
import com.hhp.ailatrieuphu.viewmodel.fragmentvm.PlayFrgVM;

public class SignOutConfirmDialog extends BaseDialog<DialogSignOutConfirmBinding, PlayFrgVM, OnMainCallback> {
    public static final String TAG = SignOutConfirmDialog.class.getName();

    public SignOutConfirmDialog(OnMainCallback callback) {
        super(callback, null);
    }

    @Override
    public void initView() {
        binding.btNo.setOnClickListener(view -> dismiss());
        binding.btSignOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            callBack.showFragment(LoginFragment.TAG, null, false);
        });
    }


    @Override
    public Class<PlayFrgVM> initViewModel() {
        return PlayFrgVM.class;
    }

    @Override
    public DialogSignOutConfirmBinding initViewBinding() {
        return DialogSignOutConfirmBinding.inflate(getLayoutInflater());
    }

}
