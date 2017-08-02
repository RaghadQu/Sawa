package com.example.zodiac.sawa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by raghadq on 5/4/2017.
 */

public class DeleteFriendRequest {

    @SerializedName("friend1_id")
    @Expose
    private int friend1_id;

    @SerializedName("friend2_id")
    @Expose
    private int friend2_id;

    public DeleteFriendRequest(int friend1_id, int friend2_id) {
        this.friend1_id = friend1_id;
        this.friend2_id = friend2_id;
    }

    public DeleteFriendRequest() {

    }

    public int getFriend1_id() {
        return friend1_id;
    }

    public void setFriend1_id(int friend1_id) {
        this.friend1_id = friend1_id;
    }

    public int getFriend2_id() {
        return friend2_id;
    }

    public void setFriend2_id(int friend2_id) {
        this.friend2_id = friend2_id;
    }
}
