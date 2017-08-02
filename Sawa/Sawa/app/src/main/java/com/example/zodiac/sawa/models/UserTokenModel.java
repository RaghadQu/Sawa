package com.example.zodiac.sawa.models;

/**
 * Created by Rabee on 5/15/2017.
 */

public class UserTokenModel {
    String device_id;
    String token;

    public UserTokenModel(String device_id, String token) {
        this.device_id = device_id;
        this.token = token;
    }


    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getToken() {
        return token;
    }
}
