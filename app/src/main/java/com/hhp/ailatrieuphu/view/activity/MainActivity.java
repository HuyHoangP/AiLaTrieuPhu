package com.hhp.ailatrieuphu.view.activity;

import static com.hhp.ailatrieuphu.viewmodel.activityvm.MainActVM.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.ActivityMainBinding;
import com.hhp.ailatrieuphu.view.base.BaseActivity;
import com.hhp.ailatrieuphu.view.fragment.SplashFragment;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;
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
}