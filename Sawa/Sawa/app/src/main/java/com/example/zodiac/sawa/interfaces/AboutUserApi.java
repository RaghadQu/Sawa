package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.DB.AboutUser;
import com.example.zodiac.sawa.models.AddAboutUserResponse;
import com.example.zodiac.sawa.models.SignRequest;
import com.example.zodiac.sawa.models.SignResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rabee on 4/7/2017.
 */

public interface AboutUserApi {
    @GET("api/users/getAbout/{user_id}")
    Call<List<AboutUser>> getAboutUser(@Path("user_id") int user_id);
    @POST("api/users/saveAbout")
    Call<AddAboutUserResponse> addAboutUser(@Body AboutUser aboutUser);
}
