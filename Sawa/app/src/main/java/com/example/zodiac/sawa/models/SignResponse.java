package com.example.zodiac.sawa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zodiac on 03/21/2017.
 */

public class SignResponse {

    @SerializedName("state")
    @Expose
    private int state;

    public int getState() {
        return state;
    }

    public void setStatus(int state) {
        this.state = state;
    }

}
