package com.example.zodiac.sawa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.zodiac.sawa.interfaces.SignAuth;
import com.example.zodiac.sawa.models.SignRequest;
import com.example.zodiac.sawa.models.SignResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zodiac on 03/18/2017.
 */

public class Register extends Activity {


    private EditText nameEditText;
    private EditText emailEditText;
    private EditText mobileEditText;
    private EditText locationEditText;
    private EditText passEditText;
    private EditText confPassEditText;
    SignAuth service;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        nameEditText = (EditText) findViewById(R.id.fullName);
        emailEditText = (EditText) findViewById(R.id.userEmailId);
        mobileEditText = (EditText) findViewById(R.id.mobileNumber);
        locationEditText = (EditText) findViewById(R.id.location);
        passEditText = (EditText) findViewById(R.id.password);
        confPassEditText = (EditText) findViewById(R.id.confirmPassword);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://0963c12e.ngrok.io/Sawa/public/index.php/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(SignAuth.class);

    }

    public void Login(View arg0)
    {
       finish();
      //  Intent i = new Intent(getApplicationContext(), MainActivity.class);
       // startActivity(i);

    }

    public void SignUp (View arg0)
    {
        SignRequest request = new SignRequest();
        request.setName(nameEditText.getText().toString());
        request.setEmail(emailEditText.getText().toString());
        request.setLocation(locationEditText.getText().toString());
        request.setPassword(passEditText.getText().toString());

        final Call<SignResponse> signResponse = service.getState(request);
        signResponse.enqueue(new Callback<com.example.zodiac.sawa.models.SignResponse>() {


            @Override
            public void onResponse(Call<SignResponse> call, Response<SignResponse> response) {
                int statusCode = response.code();
                SignResponse FinalRespone = response.body();

                Log.d("HERE","---------------------"+statusCode+FinalRespone.getState());
            }

            @Override
            public void onFailure(Call<SignResponse> call, Throwable t) {
                Log.d("HERE","---------------------ERROR ");

            }
        });

    }

}
