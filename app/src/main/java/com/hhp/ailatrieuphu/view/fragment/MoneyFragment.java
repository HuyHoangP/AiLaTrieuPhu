package com.hhp.ailatrieuphu.view.fragment;

import android.view.View;
import android.view.animation.AnimationUtils;

import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.FragmentMoneyBinding;
import com.hhp.ailatrieuphu.view.base.BaseFragment;
import com.hhp.ailatrieuphu.view.dialog.ReadyDialog;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

public class MoneyFragment extends BaseFragment<FragmentMoneyBinding, CommonVM> {
    public static final String TAG = MoneyFragment.class.getName();
    @Override
    public void initView() {
        binding.lnMoney.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_left));
        binding.ivArrow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_in_out));
        binding.ivArrow.setOnClickListener(this);
        binding.btBack.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MediaManager.getInstance().playRule();
    }

    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public FragmentMoneyBinding initViewBinding() {
        return FragmentMoneyBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void clickView(View view) {
        if(view.getId()== R.id.ivArrow){
            MediaManager.getInstance().pauseSong();
            ReadyDialog dialog = new ReadyDialog(callback);
            dialog.show(getChildFragmentManager(), ReadyDialog.TAG);
        } else if (view.getId() == R.id.btBack) {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        }
    }
}
