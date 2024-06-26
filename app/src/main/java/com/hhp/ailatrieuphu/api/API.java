package com.hhp.ailatrieuphu.api;

import com.hhp.ailatrieuphu.api.request.ChatReq;
import com.hhp.ailatrieuphu.api.response.ChatRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    String API_KEY = "sk-proj-nKhN8WUBBrwhWKeojRNKT3BlbkFJS0eSCPfRNHrXfZizlzgz";
    @POST("chat/completions")
    @Headers({"Content-Type: application/json","Authorization: Bearer " + API_KEY})
    Call<ChatRes> makeChatRequest(@Body ChatReq chatReq);
}
