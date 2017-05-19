package com.example.zodiac.sawa;

import com.example.zodiac.sawa.models.SignResponse;

/**
 * Created by Rabee on 4/8/2017.
 */

public class GeneralAppInfo {
    public static String  BACKEND_URL="http://d3c763cf.ngrok.io/Sawa/public/index.php/";
    public static String IMAGE_URL="http://d3c763cf.ngrok.io/Sawa/public/";
    public static int userID;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        GeneralAppInfo.userID = userID;
    }





}
