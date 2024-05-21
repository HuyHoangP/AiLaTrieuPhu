package com.hhp.ailatrieuphu.view.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.view.OnMainCallback;

import java.lang.reflect.Constructor;

public abstract class BaseActivity <T extends ViewBinding, V extends ViewModel> extends FragmentActivity implements IView<T,V>, OnMainCallback {
    protected T binding;
    protected V viewModel;
    public static final int SLIDE_UP_AND_DOWN = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        viewModel = new ViewModelProvider(this).get(initViewModel());
        setContentView(binding.getRoot());
        initView();
    }

    @Override
    public void showFragment(String tag, Object data, Boolean isBack) {
        showFragment(tag, data, isBack, 0);
    }

    @Override
    public void showFragment(String tag, Object data, Boolean isBack, int slideAnim) {
        try {
            Class<?> clazz = Class.forName(tag);
            Constructor<?> constructor = clazz.getConstructor();
            BaseFragment<?,?> fragment = (BaseFragment<?, ?>) constructor.newInstance();
            fragment.setCallback(this);
            fragment.setData(data);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if(slideAnim == SLIDE_UP_AND_DOWN){
                fragmentTransaction.setCustomAnimations(R.anim.item_slide_up_fade_in,
                        R.anim.item_fade_out,
                        R.anim.item_fade_in,
                        R.anim.item_slide_down_fade_out);
            }
            if(isBack){
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.replace(R.id.frMain, fragment, tag).commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
