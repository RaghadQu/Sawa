package com.example.zodiac.sawa.models;

/**
 * Created by Rabee on 4/5/2017.
 */

public class AboutUser {
    private int user_id;
    private String user_bio;
    private String user_status;
    private String user_song;

    public AboutUser(int user_id, String user_bio, String user_status, String user_song) {
        this.user_id = user_id;
        this.user_bio = user_bio;
        this.user_status = user_status;
        this.user_song = user_song;

    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_bio(String user_bio) {
        this.user_bio = user_bio;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public void setUser_song(String user_song) {
        this.user_song = user_song;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_bio() {
        return user_bio;
    }

    public String getUser_status() {
        return user_status;
    }

    public String getUser_song() {
        return user_song;
    }
}
