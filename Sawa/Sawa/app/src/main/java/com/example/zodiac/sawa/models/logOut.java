package com.example.zodiac.sawa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zodiac on 05/26/2017.
 */

public class logOut {

    @SerializedName("user_id")
    @Expose
    private int user_id;


    @SerializedName("device_id")
    @Expose
    private String device_id;

    public logOut(int user_id , String device_id)
    {
        this.user_id=user_id;
        this.device_id=device_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

}
