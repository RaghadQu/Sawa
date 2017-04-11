package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.AboutUser;
import com.example.zodiac.sawa.models.AddAboutUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Rabee on 4/7/2017.
 */

public interface AboutUserApi {
    @GET("api/users/getAbout/{user_id}")
    Call<List<AboutUser>> getAboutUser(@Path("user_id") int user_id);
    @POST("api/users/saveAbout")
    Call<AddAboutUserResponse> addAboutUser(@Body AboutUser aboutUser);
    @POST("api/users/editAbout")
    Call<AddAboutUserResponse> editAvoutUser(@Body AboutUser aboutUser);
}