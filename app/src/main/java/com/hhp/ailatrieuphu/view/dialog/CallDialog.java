package com.hhp.ailatrieuphu.view.dialog;

import android.view.View;

import com.hhp.ailatrieuphu.MediaManager;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.DialogCallBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.fragmentvm.PlayFrgVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallDialog extends BaseDialog<DialogCallBinding, PlayFrgVM, OnPlayCallback> {
    public static final String TAG = CallDialog.class.getName();


    public CallDialog(OnPlayCallback callback, PlayFrgVM viewModel) {
        super(callback, viewModel);
    }

    @Override
    public void initView() {
        setCancelable(false);
        MediaManager.getInstance().playMC(R.raw.help_call, null);
        List<String> listConsulters = new ArrayList<>();
        List<String> listAnswers = new ArrayList<>();
        HashMap<String, String> consulters = viewModel.getConsulters();
        for(Map.Entry<String, String> consulter: viewModel.getConsulters().entrySet()){
            listConsulters.add(consulter.getKey());
            listAnswers.add(viewModel.getConsulters().get(consulter.getKey()));
        }

        HashMap<String, String> caller1 = new HashMap<>();
        caller1.put(listConsulters.get(0), listAnswers.get(0));
        HashMap<String, String> caller2 = new HashMap<>();
        caller2.put(listConsulters.get(1), listAnswers.get(1));
        HashMap<String, String> caller3 = new HashMap<>();
        caller3.put(listConsulters.get(2), listAnswers.get(2));

        binding.caller1.ivCall.setTag(caller1);
        binding.caller2.ivCall.setTag(caller2);
        binding.caller3.ivCall.setTag(caller3);

        binding.caller1.ivCall.setOnClickListener(this);
        binding.caller2.ivCall.setOnClickListener(this);
        binding.caller3.ivCall.setOnClickListener(this);

        binding.caller1.tvName.setText((CharSequence) listConsulters.get(0));
        binding.caller2.tvName.setText((CharSequence) listConsulters.get(1));
        binding.caller3.tvName.setText((CharSequence) listConsulters.get(2));
    }


    @Override
    public Class<PlayFrgVM> initViewModel() {
        return PlayFrgVM.class;
    }

    @Override
    public DialogCallBinding initViewBinding() {
        return DialogCallBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void clickView(View view) {
        if(view.getId() == R.id.ivCall){
            dismiss();
            callBack.callback(PlayFragment.OPEN_CALLED_DIALOG, view.getTag());
        }
    }
}
