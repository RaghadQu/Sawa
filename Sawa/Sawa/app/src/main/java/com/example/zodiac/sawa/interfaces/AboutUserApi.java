package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.AboutUserResponeModelOld;
import com.example.zodiac.sawa.models.GeneralStateResponeModel;

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
    Call<List<AboutUserResponeModelOld>> getAboutUser(@Path("user_id") int user_id);
    @POST("api/users/saveAbout")
    Call<GeneralStateResponeModel> addAboutUser(@Body AboutUserResponeModelOld aboutUserResponeModel);
    @POST("api/users/editAbout")
    Call<GeneralStateResponeModel> editAvoutUser(@Body AboutUserResponeModelOld aboutUserResponeModel);
}
