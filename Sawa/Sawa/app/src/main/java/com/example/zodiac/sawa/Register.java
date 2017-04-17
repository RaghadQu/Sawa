package com.example.zodiac.sawa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.ProfileTabs.About;
import com.example.zodiac.sawa.interfaces.SignAuth;
import com.example.zodiac.sawa.models.SignRequest;
import com.example.zodiac.sawa.models.SignResponse;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    SignAuth service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);

        emailEditText = (EditText) findViewById(R.id.userEmailId);
        mobileEditText = (EditText) findViewById(R.id.mobileNumber);
        passEditText = (EditText) findViewById(R.id.password);
        confPassEditText = (EditText) findViewById(R.id.confirmPassword);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(SignAuth.class);

    }

    public void Login(View arg0) {
        finish();
        //  Intent i = new Intent(getApplicationContext(), MainActivity.class);
        // startActivity(i);

    }

    public void SignUp(View arg0) {
        SignRequest request = new SignRequest();
        request.setFirstName(first_name.getText().toString());
        request.setLastName(last_name.getText().toString());
        request.setMobile(mobileEditText.getText().toString());
        request.setEmail(emailEditText.getText().toString());
        request.setPassword(passEditText.getText().toString());
        Validation validation = new Validation();
        boolean isValide = validation.isDataValide(first_name, last_name, emailEditText, mobileEditText, passEditText, confPassEditText);
        if (isValide) {

            final Call<SignResponse> signResponse = service.getState(request);
            signResponse.enqueue(new Callback<com.example.zodiac.sawa.models.SignResponse>() {


                @Override
                public void onResponse(Call<SignResponse> call, Response<SignResponse> response) {
                    int statusCode = response.code();
                    SignResponse FinalRespone = response.body();
                    // add the user in the backend with empty recode
                    if (response.body().getState() == 1) {

                        About about = new About();

                        about.addNewAboutUserInDB(Integer.parseInt(FinalRespone.getUser_id()));
                        DBHandler dbHandler = new DBHandler(getApplicationContext());
                        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.profileimage);
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); // what 90 does ??
                        byte[] image=stream.toByteArray();
                        dbHandler.insertUserImage(Integer.parseInt(FinalRespone.getUser_id()), image);
                        Intent i = new Intent(getApplicationContext(), Home.class);
                         startActivity(i);
                    } else Log.d("valid", "already added");

                }

                @Override
                public void onFailure(Call<SignResponse> call, Throwable t) {
                    Log.d("notvalid", "valid");
                }
            });

        }


    }


}
