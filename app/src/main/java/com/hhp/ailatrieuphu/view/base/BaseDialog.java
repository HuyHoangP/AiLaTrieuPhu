package com.hhp.ailatrieuphu.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.hhp.ailatrieuphu.R;

public abstract class BaseDialog<T extends ViewBinding, V extends ViewModel, C> extends DialogFragment implements IView<T, V>, View.OnClickListener {
    protected T binding;
    protected V viewModel;
    protected C callBack;


    public BaseDialog(C callBack, V viewModel) {
        this.callBack = callBack;
        this.viewModel = viewModel;
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_FullScreen);

    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding();
        if (viewModel == null) {
            this.viewModel = new ViewModelProvider(this).get(initViewModel());
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_fade_in));
        clickView(view);
    }

    protected void clickView(View view) {
    }
}
