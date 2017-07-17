package com.example.zodiac.sawa.SpringApi;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by exalt on 7/5/2017.
 */

public interface photoInterface {
    @Headers("Cache-Control: max-age=64000")
    @Multipart
    @POST("/api/v1/uploadFile")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);
}