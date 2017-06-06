package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.AuthenticationResponeModel;
import com.example.zodiac.sawa.models.DeleteFriendRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by raghadq on 5/4/2017.
 */

public interface DeleteFriend {
    @Headers("Cache-Control: max-age=64000")
    @POST("api/users/deleteFriendship")
    Call<AuthenticationResponeModel> getState(@Body DeleteFriendRequest deleteRequest);
}
