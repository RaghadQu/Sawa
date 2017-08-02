package com.example.zodiac.sawa.models;

/**
 * Created by Rabee on 5/16/2017.
 */

public class UserIdWithDeviceIdModel {
    String device_id;
    int user_id;
    public UserIdWithDeviceIdModel(String device_id,int user_id){
        this.device_id=device_id;
        this.user_id=user_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
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
}
