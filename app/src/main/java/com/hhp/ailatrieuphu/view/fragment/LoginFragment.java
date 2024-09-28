package com.hhp.ailatrieuphu.view.fragment;

import static com.hhp.ailatrieuphu.view.base.BaseActivity.SLIDE_UP_AND_DOWN;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.FragmentLoginBinding;
import com.hhp.ailatrieuphu.view.base.BaseFragment;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;


public class LoginFragment extends BaseFragment<FragmentLoginBinding, CommonVM> {
    public static final String TAG = LoginFragment.class.getName();
    @Override
    public void initView() {
        binding.edtEmail.setOnFocusChangeListener((view, b) -> binding.tvWarning.setVisibility(View.GONE));

        binding.btLogin.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);
        binding.tvForgotPass.setOnClickListener(this);
    }

    @Override
    protected void clickView(View view) {
        if(view.getId() == R.id.btLogin){
            signIn();
        } else if (view.getId() == R.id.tvSignUp) {
            goToSignUp();
        } else if (view.getId() == R.id.tvForgotPass) {
            forgotPassword();
        }
    }

    private void forgotPassword() {
        String email = binding.edtEmail.getText().toString().trim();
        if(email.isEmpty()){
            binding.tvWarning.setVisibility(View.VISIBLE);
        } else {
            binding.pbLoading.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    final Snackbar snackbar = Snackbar.make(binding.tvSignUp, "VERIFICATION HAS BEEN SENT TO YOUR EMAIL", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("DISMISS", view -> snackbar.dismiss());
                    snackbar.show();
                } else {
                    Toast.makeText(context, "Something is wrong, please try again.", Toast.LENGTH_SHORT).show();
                }
                binding.pbLoading.setVisibility(View.GONE);
            });
        }
    }

    private void  goToSignUp() {
        callback.showFragment(SignUpFragment.TAG, null, true, SLIDE_UP_AND_DOWN);
    }

    private void signIn() {
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        if(email.isEmpty() || password.isEmpty()) return;
        binding.pbLoading.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            binding.pbLoading.setVisibility(View.GONE);
            if(task.isSuccessful()){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.i(TAG, "onComplete: " + user.getEmail());
                callback.showFragment(MenuFragment.TAG, null, false);
            } else {
                Log.w(TAG, "createUserWithEmail: Failure", task.getException());
                Toast.makeText(context, "Something is wrong. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public FragmentLoginBinding initViewBinding() {
        return FragmentLoginBinding.inflate(getLayoutInflater());
    }
}
