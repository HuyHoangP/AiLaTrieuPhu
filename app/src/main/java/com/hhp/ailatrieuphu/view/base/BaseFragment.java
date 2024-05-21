package com.hhp.ailatrieuphu.view.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.hhp.ailatrieuphu.view.OnMainCallback;


public abstract class BaseFragment <T extends ViewBinding, V extends ViewModel> extends Fragment implements View.OnClickListener,IView<T,V> {
    protected Context context;
    protected T binding;
    protected V viewModel;
    protected OnMainCallback callback;
    protected Object data;

    public void setData(Object data){
        this.data = data;
    }
    public void setCallback(OnMainCallback callback){
        this.callback = callback;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding();
        viewModel = new ViewModelProvider(this).get(initViewModel());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        clickView(view);
    }

    protected void clickView(View view) {

    }
}
