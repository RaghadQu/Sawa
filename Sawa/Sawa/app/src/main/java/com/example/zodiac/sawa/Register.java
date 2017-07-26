package com.example.zodiac.sawa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.HomeTabbedActivity;
import com.example.zodiac.sawa.MenuActiviries.aboutUserActivity;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.Spring.Models.SignUpModel;
import com.example.zodiac.sawa.Spring.Models.UserModel;
import com.example.zodiac.sawa.SpringApi.AuthInterface;
import com.example.zodiac.sawa.Validation;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zodiac on 03/18/2017.
 */

public class Register extends Activity {


    private EditText first_name;
    private EditText last_name;
    private EditText emailEditText;
    private EditText mobileEditText;
    private EditText passEditText;
    private EditText confPassEditText;
    AuthInterface authInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_name_fragment);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);

      //  emailEditText = (EditText) findViewById(R.id.userEmailId);
      //  mobileEditText = (EditText) findViewById(R.id.mobileNumber);
       // passEditText = (EditText) findViewById(R.id.password);
        //confPassEditText = (EditText) findViewById(R.id.confirmPassword);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        authInterface = retrofit.create(AuthInterface.class);

    }

    public void Login(View arg0) {
        finish();
        //  Intent i = new Intent(getApplicationContext(), MainActivity.class);
        // startActivity(i);

    }

    public void SignUp(View arg0) {
        SignUpModel signUpModel = new SignUpModel();
        signUpModel.setFirst_name(first_name.getText().toString());
        signUpModel.setLast_name(last_name.getText().toString());
        signUpModel.setMobile(Long.parseLong(mobileEditText.getText().toString()));
        signUpModel.setEmail(emailEditText.getText().toString());
        signUpModel.setPassword(passEditText.getText().toString());
        Validation validation = new Validation();
        boolean isValide = validation.isDataValide(first_name, last_name, emailEditText, mobileEditText, passEditText, confPassEditText);
        if (isValide) {

            final Call<UserModel> signResponse = authInterface.signUp(signUpModel);
            signResponse.enqueue(new Callback<UserModel>() {


                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    int statusCode = response.code();
                    UserModel userModel = response.body();
                    // add the user in the backend with empty recode
                    if (response.code() == 200) {

                        aboutUserActivity.updateAbout("","","");
                        aboutUserActivity about = new aboutUserActivity();

                       // about.addNewAboutUserInDB(Integer.parseInt(FinalRespone.getUser_id()));
                        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.profileimage);
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); // what 90 does ??
                        byte[] image=stream.toByteArray();
                       // dbHandler.insertUserImage(Integer.parseInt(userModel.getId()), image);
                        Intent i = new Intent(getApplicationContext(), HomeTabbedActivity.class);
                         startActivity(i);
                    } else Log.d("valid", "already added");

                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.d("notvalid", "valid");
                }
            });

        }


    }


}
