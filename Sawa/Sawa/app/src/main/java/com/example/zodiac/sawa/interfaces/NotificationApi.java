package com.example.zodiac.sawa.interfaces;

import com.example.zodiac.sawa.models.Notification;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotificationApi {

    @GET("api/users/getNotifications/{user_id}")
    Call<Notification> getNotification(@Path("user_id") int user_id);

    @FormUrlEncoded
    @POST("api/notifications/set_read_flag")
    Call<Void> setReadFlag(@Field("notification_id") int notification_id);
}
