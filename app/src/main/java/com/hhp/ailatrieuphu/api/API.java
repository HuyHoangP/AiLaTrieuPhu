package com.hhp.ailatrieuphu.api;

import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.api.request.ChatReq;
import com.hhp.ailatrieuphu.api.response.ChatRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    String API_KEY = "sk-proj-6K4xobHLAAZEyfs7S9MNT3BlbkFJesbU20p7X1s29v78QpDA";
    @POST("chat/completions")
    @Headers({"Content-Type: application/json","Authorization: Bearer " + API_KEY})
    Call<ChatRes> makeChatRequest(@Body ChatReq chatReq);
}
