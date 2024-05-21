package com.hhp.ailatrieuphu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hhp.ailatrieuphu.api.request.ChatReq;
import com.hhp.ailatrieuphu.api.response.ChatRes;
import com.hhp.ailatrieuphu.database.entity.Message;
import com.hhp.ailatrieuphu.view.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class BotGPTDialogVM extends BaseViewModel {
    private MutableLiveData<Message> botMessageLD = new MutableLiveData<>();
    private MutableLiveData<Integer> timerLD;
    private Thread thread;

    public MutableLiveData<Message> getBotMessageLD() {
        return botMessageLD;
    }

    public void makeChatReq(String message){
        List<ChatReq.Messages> messages = new ArrayList<>();
        messages.add(new ChatReq.Messages("user", message));
        ChatReq chatReq = new ChatReq("gpt-3.5-turbo", 0.7, messages);

        getAPI().makeChatRequest(chatReq)
                .enqueue(handleResponse());
    }

    @Override
    protected void handleSuccess(ChatRes body) {
        Message botMessage = new Message(body.choices.get(0).message.content, false);
        botMessageLD.postValue(botMessage);
    }

    public void countDown(int timer) {
        timerLD = new MutableLiveData<>(timer);
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                while (timerLD.getValue() != null || timerLD.getValue() > 0) {
                    try {
                        Thread.sleep(1000);
                        timerLD.postValue(timerLD.getValue() - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            });
            thread.start();
        }
    }

    public MutableLiveData<Integer> getTimerLD() {
        return timerLD;
    }
}
