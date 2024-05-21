package com.hhp.ailatrieuphu.view.dialog;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.lifecycle.Observer;

import com.hhp.ailatrieuphu.database.entity.Message;
import com.hhp.ailatrieuphu.databinding.DialogGptBotBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.adapter.BotGPTAdapter;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.viewmodel.BotGPTDialogVM;

public class BotGPTDialog extends BaseDialog<DialogGptBotBinding, BotGPTDialogVM, OnPlayCallback> {
    public static final String TAG = BotGPTDialog.class.getName();
    private BotGPTAdapter adapter;
    private boolean isHidden;

    public BotGPTDialog() {
        super(null, null);
    }

    @Override
    public void initView() {
        if (adapter == null) {
            adapter = new BotGPTAdapter(requireContext());
            binding.rvChat.setAdapter(adapter);
        }
        binding.frTimerBar.setOnClickListener(view -> {
            binding.rvChat.setVisibility(isHidden ? View.VISIBLE : View.GONE);
            binding.trRequest.setVisibility(isHidden ? View.VISIBLE : View.GONE);
            binding.cvBotDialog.setLayoutParams(new FrameLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    isHidden ? LinearLayout.LayoutParams.MATCH_PARENT : LinearLayout.LayoutParams.WRAP_CONTENT));
            isHidden = !isHidden;
        });

        binding.ivSend.setOnClickListener(view -> {
            String textReq = binding.edtRequest.getText().toString().trim();
            adapter.sendMessage(new Message(textReq, true));
            binding.edtRequest.getText().clear();
            adapter.sendMessage(new Message("Typing...", false));
            viewModel.makeChatReq(textReq);
        });

        viewModel.getBotMessageLD().observe(this, botMessage -> {
            adapter.removeLastMessage();
            adapter.sendMessage(botMessage);
            binding.rvChat.scrollToPosition(adapter.getItemCount());
        });
        countDown();
    }

    private void countDown() {
        viewModel.countDown(30);
        viewModel.getTimerLD().observe(this, timer -> {
            if (timer < 0) dismiss();
            binding.tvTimer.setText(String.format("%s", timer));
        });
    }

    @Override
    public Class<BotGPTDialogVM> initViewModel() {
        return BotGPTDialogVM.class;
    }

    @Override
    public DialogGptBotBinding initViewBinding() {
        return DialogGptBotBinding.inflate(getLayoutInflater());
    }
}
