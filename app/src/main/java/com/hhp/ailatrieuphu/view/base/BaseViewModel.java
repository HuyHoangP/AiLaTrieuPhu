package com.hhp.ailatrieuphu.view.base;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.hhp.ailatrieuphu.api.API;
import com.hhp.ailatrieuphu.api.request.ChatReq;
import com.hhp.ailatrieuphu.api.response.ChatRes;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseViewModel extends ViewModel {
    private static final String TAG = BaseViewModel.class.getName();
    private static final String BASE_URL = "https://api.openai.com/v1/";

    protected API getAPI(){
        Retrofit rs = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS).build())
                .build();
        return rs.create(API.class);
    }

    protected Callback<ChatRes> handleResponse(){
        return new Callback<ChatRes>() {
            @Override
            public void onResponse(Call<ChatRes> call, Response<ChatRes> response) {
                if(response.isSuccessful()){
                    handleSuccess(response.body());
                } else {
                    Log.i(TAG, "onFail: " + response.code());
                    Log.i(TAG, "onFail: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ChatRes> call, Throwable t) {

            }
        };
    }

    protected void handleSuccess(ChatRes body) {
        Log.i(TAG, "handleSuccess: " + body.choices.get(0).message.toString());
    }
}
