package com.example.zodiac.sawa.emailSender;

import android.os.AsyncTask;
import android.util.Log;

import com.example.zodiac.sawa.emailSender.GMailSender;

/**
 * Created by zodiac on 03/25/2017.
 */


    public class BackgroungSender extends AsyncTask<String, String,String> {

    static String uniqueID;
    static String recievedEmail;
<<<<<<< HEAD
    String sentEmail="SawaTeamG@gmail.com";
    String sentPassword="SawaTeam2017";
=======
    String sentEmail="Ibrahim.zahra.166@gmail.com";
    String sentPassword="hfvhidlogdg1";
>>>>>>> c42b4d03850a5173912929a2c76e570b5009c9df
    protected String doInBackground(String... urls) {

        GMailSender mailsender = new GMailSender(sentEmail, sentPassword);

        String[] toArr = {this.getRecievedEmail()};
        mailsender.set_to(toArr);
        mailsender.set_from(sentEmail);
        mailsender.set_subject("Password Recovery");
        mailsender.setBody("\nDear user,\n\n" +
                "We recieved a request to reset your password for Sawa account.\n\n" +
                "You can reset your password by entering the following code and then change a new password for your account.\n" +
                "The code is :" +this.getUniqueID()+
                "\n\n\n\n" +
                "If you did not request a password reset, please ignore this email and your current password will continue to work.\n\n" +
                "Sincerely,\n" +
                "Sawa Team.");

        try {
            mailsender.send();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MailApp", "Could not send email", e);
        }


    return "";

    }


    public static String getUniqueID() {
        return uniqueID;
    }

    public static void setUniqueID(String uniqueID) {
        BackgroungSender.uniqueID = uniqueID;
    }

    public static String getRecievedEmail() {
        return recievedEmail;
    }

    public static void setRecievedEmail(String recievedEmail) {
        BackgroungSender.recievedEmail = recievedEmail;
    }
}