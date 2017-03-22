package com.example.zodiac.sawa.models;

/**
 * Created by zodiac on 03/21/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignRequest {


    @SerializedName("first_name")
    @Expose
    private String name;

    @SerializedName("last_name")
    @Expose
    private String location;
    @SerializedName("email")
    @Expose
    private String email;
 //   @SerializedName("mobile")
    // @Expose
   // private String mobile;

    @SerializedName("password")
    @Expose
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 //   public String getMobile() {

    //return mobile;
  //  }

  /*  public void setMobile(String mobile) {
        this.mobile = mobile;
    }*/

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

