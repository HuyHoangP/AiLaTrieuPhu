package com.hhp.ailatrieuphu.view.dialog;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hhp.ailatrieuphu.databinding.DialogChangePasswordBinding;
import com.hhp.ailatrieuphu.view.OnMainCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.listener.TextChangeListener;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

public class ChangePasswordDialog extends BaseDialog<DialogChangePasswordBinding, CommonVM, OnMainCallback> {
    public static final String TAG = ChangePasswordDialog.class.getName();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    boolean newPassword, newConfirmPassword;
    public ChangePasswordDialog(OnMainCallback callback) {
        super(callback, null);
    }

    @Override
    public void initView() {
        binding.btBack.setOnClickListener(view -> dismiss());
        binding.btConfirm.setOnClickListener(view -> checkValid());
        binding.edtOldPassword.setOnFocusChangeListener((view, b) -> binding.tvWarning1.setVisibility(View.GONE));

        binding.edtNewPassword.addTextChangedListener((TextChangeListener) editable -> {
            newPassword = editable.toString().length() > 6;
            boolean isTexted = editable.toString().isEmpty();
            binding.tvWarning2.setVisibility(newPassword || isTexted? View.GONE : View.VISIBLE);
        });
        binding.edtConfirmNewPassword.addTextChangedListener((TextChangeListener) editable -> {
            newConfirmPassword = editable.toString().length() > 0 && editable.toString().contentEquals(binding.edtNewPassword.getText());
            boolean isTexted = editable.toString().isEmpty();
            binding.tvWarning3.setVisibility(newConfirmPassword || isTexted ? View.GONE : View.VISIBLE);
        });
    }

    private void checkValid() {
        String oldPassword = binding.edtOldPassword.getText().toString();
        if(oldPassword.isEmpty()) return;
        boolean isSame = binding.edtNewPassword.getText().toString().equals(binding.edtConfirmNewPassword.getText().toString());
        binding.pbLoading.setVisibility(View.VISIBLE);

        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
        user.reauthenticate(credential).addOnCompleteListener(task -> {
                    if(task.isSuccessful() && newPassword && newConfirmPassword && isSame){
                        changePassword();
                    } else if (!isSame) {
                        binding.tvWarning3.setVisibility(View.VISIBLE);
                    } else { //Auth failed
                        binding.tvWarning1.setVisibility(View.VISIBLE);
                    }
                    binding.pbLoading.setVisibility(View.GONE);
                });
    }

    private void changePassword() {
        user.updatePassword(binding.edtConfirmNewPassword.getText().toString()).addOnCompleteListener(task -> {
            Toast.makeText(requireContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }


    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public DialogChangePasswordBinding initViewBinding() {
        return DialogChangePasswordBinding.inflate(getLayoutInflater());
    }

}
