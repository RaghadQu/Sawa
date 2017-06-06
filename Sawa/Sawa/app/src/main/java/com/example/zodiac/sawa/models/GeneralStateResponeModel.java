package com.example.zodiac.sawa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rabee on 4/8/2017.
 */

public class GeneralStateResponeModel {
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
