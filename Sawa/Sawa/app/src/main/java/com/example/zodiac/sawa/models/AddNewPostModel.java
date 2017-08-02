package com.example.zodiac.sawa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zodiac on 06/08/2017.
 */

public class AddNewPostModel {

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("to_user_id")
    @Expose
    private int to_user_id;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("is_anon")
    @Expose
    private int is_anon;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getIs_Anon() {
        return is_anon;
    }

    public void setIs_Anon(int is_Anon) {
        this.is_anon = is_Anon;
    }


}
