package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.getFriendsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rabee on 5/26/2017.
 */

public interface SerachApi {
    @GET("/api/v1/serach/{word}")
    Call<List<getFriendsResponse>> getSearchResult(@Path("word") String word);
}
