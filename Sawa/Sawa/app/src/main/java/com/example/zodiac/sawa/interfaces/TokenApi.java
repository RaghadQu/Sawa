package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.DeleteFriendRequest;
import com.example.zodiac.sawa.models.UserIdWithDeviceIdModel;
import com.example.zodiac.sawa.models.UserTokenModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Rabee on 5/15/2017.
 */

public interface TokenApi {
    @POST("api/token/fcm")
    Call<Authentication> storeToken(@Body UserTokenModel userTokenModel);
    @POST("api/users/saveIdWithDeviceId")
    Call<Authentication> storeUserIdWithDeviceId(@Body UserIdWithDeviceIdModel userIdWithDeviceIdModel);
}
