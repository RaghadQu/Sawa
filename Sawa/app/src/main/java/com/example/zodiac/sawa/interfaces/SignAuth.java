package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.AuthRequest;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.SignRequest;
import com.example.zodiac.sawa.models.SignResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



    public interface SignAuth {
        @Headers("Cache-Control: max-age=64000")
        @POST("api/users/signup")
        Call<SignResponse> getState(@Body SignRequest ARequest);
    }


