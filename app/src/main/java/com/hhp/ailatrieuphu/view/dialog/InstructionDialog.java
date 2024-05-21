package com.hhp.ailatrieuphu.view.dialog;

import com.google.android.material.tabs.TabLayout;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.DialogInstructionBinding;
import com.hhp.ailatrieuphu.databinding.DialogVictoryBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

public class InstructionDialog extends BaseDialog<DialogInstructionBinding, CommonVM, OnPlayCallback> {
    public static final String TAG = InstructionDialog.class.getName();
    public InstructionDialog() {
        super(null, null);
    }

    @Override
    public void initView() {
        setCancelable(false);
        binding.btBack.setOnClickListener(view -> dismiss());
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    binding.tvRule.setText(R.string.rules_normal);
                } else {
                    binding.tvRule.setText(R.string.rules_ranked);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public DialogInstructionBinding initViewBinding() {
        return DialogInstructionBinding.inflate(getLayoutInflater());
    }
}
