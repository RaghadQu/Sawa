package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.AuthRequest;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.getFriendsRequest;
import com.example.zodiac.sawa.models.getFriendsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zodiac on 04/18/2017.
 */

public interface GetFreinds {
    @Headers("Cache-Control: max-age=64000")
    @GET("api/users/getFriends/{user_id}/{type}")
    Call<List<getFriendsResponse>> getState(@Path("user_id") int user_id, @Path("type") int type);

    //Get freindship state
    @GET("api/users/getFreindShipState/{friend1_id}/{friend2_id}")
    Call<Authentication> getFriendshipState(@Path("friend1_id") int friend1_id, @Path("friend2_id") int friend2_id);

}
