package com.hhp.ailatrieuphu.view.fragment;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.FragmentMenuBinding;
import com.hhp.ailatrieuphu.view.base.BaseFragment;
import com.hhp.ailatrieuphu.view.dialog.InstructionDialog;
import com.hhp.ailatrieuphu.view.dialog.LeaderBoardDialog;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

public class MenuFragment extends BaseFragment<FragmentMenuBinding, CommonVM> {
    public static final String TAG = MenuFragment.class.getName();
    @Override
    public void initView() {
        binding.trPlayRank.setOnClickListener(this);
        binding.ivPlay.setOnClickListener(this);
        binding.ivInfo.setOnClickListener(this);
        binding.ivLeaderBoard.setOnClickListener(this);
        binding.ivSetting.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        MediaManager.getInstance().playIntro();
    }

    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public FragmentMenuBinding initViewBinding() {
        return FragmentMenuBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void clickView(View view) {
        if(view.getId() == R.id.ivPlay){
            callback.showFragment(MoneyFragment.TAG, null, true);
        } else if (view.getId() == R.id.trPlayRank) {
            goToPlayRank();
        } else if (view.getId() == R.id.ivInfo) {
            InstructionDialog dialog = new InstructionDialog();
            dialog.show(getChildFragmentManager(), InstructionDialog.TAG);
        } else if (view.getId() == R.id.ivLeaderBoard) {
            LeaderBoardDialog dialog = new LeaderBoardDialog();
            dialog.show(getChildFragmentManager(), LeaderBoardDialog.TAG);
        } else if (view.getId() == R.id.ivSetting) {
            callback.showFragment(SettingFragment.TAG, null, true);
        }
    }

    private void goToPlayRank() {
        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        if(username == null || username.isEmpty()){
            callback.showFragment(SettingFragment.TAG, null, true);
        } else {
            callback.showFragment(PlayFragment.TAG, true, true);
        }
    }
}
