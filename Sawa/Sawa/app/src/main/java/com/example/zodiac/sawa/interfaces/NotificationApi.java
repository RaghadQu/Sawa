package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.AboutUser;
import com.example.zodiac.sawa.models.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface  NotificationApi {

    @GET("api/users/getNotifications/{user_id}")
    Call<Notification> getNotification(@Path("user_id") int user_id);
}
