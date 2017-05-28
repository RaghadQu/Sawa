package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.DeleteFriendRequest;
import com.example.zodiac.sawa.models.logOut;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by zodiac on 05/26/2017.
 */

public interface logOutApi {
    @Headers("Cache-Control: max-age=64000")
    @POST("api/users/logOut")
    Call<Void> getLogOut(@Body logOut log_out);
}


