package com.hhp.ailatrieuphu.view.fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hhp.ailatrieuphu.databinding.FragmentSplashBinding;
import com.hhp.ailatrieuphu.view.base.BaseFragment;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SplashFragment extends BaseFragment<FragmentSplashBinding, CommonVM> {
    public static final String TAG = SplashFragment.class.getName();

    @Override
    public void initView() {
        goToMenu();
    }

    private void goToMenu() {
        //Use Executor instead of Handler
        ScheduledExecutorService backgroundExecutor = Executors.newSingleThreadScheduledExecutor();
        backgroundExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                callback.showFragment(user == null ? LoginFragment.TAG : MenuFragment.TAG, null, false);
            }
        }, 3, TimeUnit.SECONDS);
    }

    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public FragmentSplashBinding initViewBinding() {
        return FragmentSplashBinding.inflate(getLayoutInflater());
    }
}
