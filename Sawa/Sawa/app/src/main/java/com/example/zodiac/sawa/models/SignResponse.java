package com.example.zodiac.sawa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zodiac on 03/21/2017.
 */

public class SignResponse {


    private int state;
    private int user_id;

    public int getState() {
        return state;
    }

    public void setStatus(int state) {
        this.state = state;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }
}
