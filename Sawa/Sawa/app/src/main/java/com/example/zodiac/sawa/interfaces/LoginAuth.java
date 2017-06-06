package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.AuthRequest;
import com.example.zodiac.sawa.models.AuthenticationResponeModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by zodiac on 03/19/2017.
 */

public interface LoginAuth {
    @Headers("Cache-Control: max-age=64000")
    @POST("api/users/signin")
    Call<AuthenticationResponeModel> getState(@Body AuthRequest ARequest);
}
