package com.hhp.ailatrieuphu.view.fragment;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hhp.ailatrieuphu.database.entity.User;
import com.hhp.ailatrieuphu.databinding.FragmentSignupBinding;
import com.hhp.ailatrieuphu.view.base.BaseFragment;
import com.hhp.ailatrieuphu.view.listener.TextChangeListener;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;


public class SignUpFragment extends BaseFragment<FragmentSignupBinding, CommonVM> {
    public static final String TAG = SignUpFragment.class.getName();
    boolean password, confirmPassword;

    @Override
    public void initView() {
        binding.btSignUp.setOnClickListener(view -> signUp());

        binding.edtPassword.addTextChangedListener((TextChangeListener) editable -> {
            password = editable.toString().length() >= 6;
            boolean isTexted = editable.toString().length() == 0;
            binding.tvWarning1.setVisibility(password || isTexted? View.GONE : View.VISIBLE);
        });
        binding.edtConfirmPassword.addTextChangedListener((TextChangeListener) editable -> {
            confirmPassword = editable.toString().length() > 0 && editable.toString().contentEquals(binding.edtPassword.getText());
            boolean isTexted = editable.toString().length() == 0;
            binding.tvWarning2.setVisibility(confirmPassword || isTexted ? View.GONE : View.VISIBLE);
        });
    }

    private void signUp() {
        String email = binding.edtEmail.getText().toString().trim();
        if(email.isEmpty() || !password || !confirmPassword) return;
        String password = binding.edtPassword.getText().toString();
        binding.pbLoading.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        saveToDB();
                    } else {
                        binding.pbLoading.setVisibility(View.GONE);
                        Log.w(TAG, "createUserWithEmail: Failure", task.getException());
                        Toast.makeText(context, "Something is wrong. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveToDB() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("list_user");
        String path = user.getEmail().replace(".","-");
        dbRef.child(path).setValue(new User()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                requireActivity().getSupportFragmentManager().popBackStack();
                callback.showFragment(MenuFragment.TAG, null, false);
            }
        });
    }


    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public FragmentSignupBinding initViewBinding() {
        return FragmentSignupBinding.inflate(getLayoutInflater());
    }


}
