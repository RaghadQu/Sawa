package com.example.zodiac.sawa.Spring.Models;

/**
 * Created by Rabee on 6/26/2017.
 */

public class UserModel {
    int id ;
    String first_name;
    String last_name;
    String email;
    Double birthdate;
    int mobile;
    String image;
    int sign_in_out;
    int anon_post;
    int public_post_view;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Double birthdate) {
        this.birthdate = birthdate;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSign_in_out() {
        return sign_in_out;
    }

    public void setSign_in_out(int sign_in_out) {
        this.sign_in_out = sign_in_out;
    }

    public int getAnon_post() {
        return anon_post;
    }

    public void setAnon_post(int anon_post) {
        this.anon_post = anon_post;
    }

    public int getPublic_post_view() {
        return public_post_view;
    }

    public void setPublic_post_view(int public_post_view) {
        this.public_post_view = public_post_view;
    }
}