package com.hhp.ailatrieuphu.view.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.WindowCompat;

import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.databinding.ActivityMainBinding;
import com.hhp.ailatrieuphu.view.base.BaseActivity;
import com.hhp.ailatrieuphu.view.fragment.SplashFragment;
import com.hhp.ailatrieuphu.viewmodel.activityvm.MainActVM;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActVM> {

    public static final String KEY_INIT_DB = "KEY_INIT_DB";

    @Override
    public void callback(String key, Object data) {
        if(key.equals(KEY_INIT_DB)){
            viewModel.initDB();
        }
    }


    @Override
    public void initView() {
        viewModel.initDB();
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        showFragment(SplashFragment.TAG, null, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        MediaManager.getInstance().pauseSong();
    }

    @Override
    public Class<MainActVM> initViewModel() {
        return MainActVM.class;
    }

    @Override
    public ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}