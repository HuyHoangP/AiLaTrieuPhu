package com.hhp.ailatrieuphu.view.dialog;

import android.view.View;

import com.hhp.ailatrieuphu.App;
import com.hhp.ailatrieuphu.databinding.DialogConsulterBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.view.fragment.PlayFragment;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;
import com.hhp.ailatrieuphu.viewmodel.fragmentvm.PlayFrgVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsulterDialog extends BaseDialog<DialogConsulterBinding, PlayFrgVM, OnPlayCallback> {
    public static final String TAG = ConsulterDialog.class.getName();
    private String key = PlayFragment.HELP_CONSULTER;


    public ConsulterDialog(PlayFrgVM viewModel) {
        super(null, viewModel);
    }
    public ConsulterDialog(PlayFrgVM viewModel, String key) {
        super(null, viewModel);
        this.key = key;
    }

    @Override
    public void initView() {
        setCancelable(false);
        binding.btBack.setOnClickListener(view -> dismiss());
        List<String> listConsulters = new ArrayList<>();
        List<String> listAnswers = new ArrayList<>();
        for(Map.Entry<String, String> consulter: viewModel.getConsulters().entrySet()){
            listConsulters.add(consulter.getKey());
            listAnswers.add(viewModel.getConsulters().get(consulter.getKey()));
        }

        String ans1 = App.getInstance().getStorage().keyAns.get(listAnswers.get(0));
        binding.consulter1.tvSuggestAns.setText(ans1);
        binding.consulter1.tvName.setText((CharSequence) listConsulters.get(0));

        if(key.equals(PlayFragment.HELP_CALL)){
            binding.consulter2.frConsulter.setVisibility(View.GONE);
            binding.consulter3.frConsulter.setVisibility(View.GONE);
            return;
        }

        String ans2 = App.getInstance().getStorage().keyAns.get(listAnswers.get(1));
        String ans3 = App.getInstance().getStorage().keyAns.get(listAnswers.get(2));

        binding.consulter2.tvSuggestAns.setText(ans2);
        binding.consulter3.tvSuggestAns.setText(ans3);

        binding.consulter2.tvName.setText((CharSequence) listConsulters.get(1));
        binding.consulter3.tvName.setText((CharSequence) listConsulters.get(2));
    }


    @Override
    public Class<PlayFrgVM> initViewModel() {
        return PlayFrgVM.class;
    }

    @Override
    public DialogConsulterBinding initViewBinding() {
        return DialogConsulterBinding.inflate(getLayoutInflater());
    }
}
