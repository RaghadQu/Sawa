package com.example.zodiac.sawa;

import com.example.zodiac.sawa.models.SignResponse;

/**
 * Created by Rabee on 4/8/2017.
 */

public class GeneralAppInfo {
    public static String BACKEND_URL = "http://faf7c217.ngrok.io/Sawa/public/index.php/";
    public static String IMAGE_URL = "http://faf7c217.ngrok.io/Sawa/public/";

    public static String SPRING_URL="http://d4a17a6c.ngrok.io";
    public static int notifications_counter = 0;
    public static int home_tab_position = 0;
    public static int notifications_tab_position = 1;
    public static int setting_tab_position = 2;
    public static int friendMode = -1;
    public static int userID;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        GeneralAppInfo.userID = userID;
    }

}
