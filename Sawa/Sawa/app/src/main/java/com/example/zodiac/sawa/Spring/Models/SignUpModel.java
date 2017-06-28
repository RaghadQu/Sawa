package com.example.zodiac.sawa.Spring.Models;

/**
 * Created by Rabee on 6/27/2017.
 */

public class SignUpModel {
    String first_name;
    String last_name;
    String email;
    String password;
    Integer mobile;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getMobile() {
        return mobile;
    }
    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }
}
