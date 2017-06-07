package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.AddNewPostModel;
import com.example.zodiac.sawa.models.GeneralStateResponeModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by zodiac on 06/08/2017.
 */

public interface AddPostApi {
        @Headers("Cache-Control: max-age=64000")
        @POST("api/post/addNewPost")
        Call<GeneralStateResponeModel> addPost(@Body AddNewPostModel addNewPost);

}
