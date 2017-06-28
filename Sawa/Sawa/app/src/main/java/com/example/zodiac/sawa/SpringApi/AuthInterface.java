package com.example.zodiac.sawa.SpringApi;

import com.example.zodiac.sawa.Spring.Models.SignInModel;
import com.example.zodiac.sawa.Spring.Models.SignUpModel;
import com.example.zodiac.sawa.Spring.Models.UserModel;
import com.example.zodiac.sawa.models.AuthRequest;
import com.example.zodiac.sawa.models.AuthenticationResponeModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Rabee on 6/26/2017.
 */

public interface AuthInterface {
    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/user/signIn")
    Call<UserModel> signIn(@Body SignInModel signInModel);
    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/user/signUp")
    Call<UserModel> signUp(@Body SignUpModel signUpModel);
}
