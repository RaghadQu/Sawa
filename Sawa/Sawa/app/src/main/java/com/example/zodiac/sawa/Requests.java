package com.example.zodiac.sawa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.zodiac.sawa.Spring.Models.LoginWIthGoogleModel;
import com.example.zodiac.sawa.Spring.Models.UserModel;
import com.example.zodiac.sawa.SpringApi.AuthInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rabee on 7/18/2017.
 */

public class Requests {
    /*AuthInterface service;

    public void loginWithGoogle(LoginWIthGoogleModel loginWIthGoogleModel) {
        final Call<UserModel> userModelCall = service.loginWithGoogle(loginWIthGoogleModel);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {


                int statusCode = response.code();
                Log.d("-----", " enter request " + statusCode);
                UserModel userModel = response.body();
                //SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                if (statusCode == 200) {
                    Log.d("-----", " enter here" + userModel.getId());

                    GeneralAppInfo.setUserID(Integer.valueOf(userModel.getId()));
                    sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", emailEditText.getText().toString());
                    editor.putString("password", passEditText.getText().toString());
                    editor.putInt("id", GeneralAppInfo.getUserID());
                    editor.putString("isLogined", "1");
                    editor.apply();

                    Intent i = new Intent(getApplicationContext(), HomeTabbedActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    finish();

                } else {
                    emailEditText.setError("Invalid Email or Password");
                }


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("----", " Error " + t.getMessage());


            }
        });

    }*/
}
