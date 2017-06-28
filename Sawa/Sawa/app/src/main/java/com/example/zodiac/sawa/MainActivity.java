package com.example.zodiac.sawa;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zodiac.sawa.RecoverPassword.RecoverPass;

import com.example.zodiac.sawa.Spring.Models.SignInModel;
import com.example.zodiac.sawa.Spring.Models.UserModel;
import com.example.zodiac.sawa.SpringApi.AuthInterface;
import com.example.zodiac.sawa.interfaces.LoginAuth;
import com.example.zodiac.sawa.models.AuthRequest;
import com.example.zodiac.sawa.models.AuthenticationResponeModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    AuthInterface service;
    private ProgressBar logInProfress;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        //check if the user is already signed in
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", -1);
        String isLogined = sharedPreferences.getString("isLogined", "");
        GeneralAppInfo.setUserID(id);


        if ((isLogined.equals("1"))) {
            Intent i = new Intent(getApplicationContext(), HomeTabbedActivity.class);
            startActivity(i);
            finish();
        }

        logInProfress = (ProgressBar) findViewById(R.id.LogInProgress);
        logInProfress.setVisibility(ProgressBar.INVISIBLE);
        emailEditText = (EditText) findViewById(R.id.username);
        passEditText = (EditText) findViewById(R.id.password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(AuthInterface.class);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void checkLogin(View arg0) {

        GeneralFunctions generalFunctions = new GeneralFunctions();
        boolean isOnline = generalFunctions.isOnline(getApplicationContext());


        if (isOnline == false) {
            Toast.makeText(this, "no internet connection!",
                    Toast.LENGTH_LONG).show();
        } else {

            final SignInModel signInModel = new SignInModel();
            signInModel.setEmail(emailEditText.getText().toString());
            signInModel.setPassword(passEditText.getText().toString());
            if (valid(signInModel.getEmail(), signInModel.getPassword()) == 0) {
                logInProfress.setVisibility(ProgressBar.VISIBLE);
                final Call<UserModel> userModelCall = service.signIn(signInModel);
                userModelCall.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                        logInProfress.setVisibility(ProgressBar.INVISIBLE);

                        int statusCode = response.code();
                        Log.d("-----", " enter request " + statusCode);
                        UserModel userModel = response.body();
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                        if (statusCode==200) {
                            Log.d("-----", " enter here"+userModel.getId());

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
            }
        }
    }


    public void forgot_pass(View arg0) {
        Intent i = new Intent(getApplicationContext(), RecoverPass.class);
        startActivity(i);

    }

    public void register(View arg0) {
        Intent i = new Intent(getApplicationContext(), Register.class);
        startActivity(i);

    }


    public int valid(String email, String password) {
        int flag = 0;
        if ((password.trim().equals(""))) {
            passEditText.setError("Password is required");
            flag = 1;
        } else if (password.length() < 8) {
            passEditText.setError("Password must contain at least 8 characters");
            flag = 1;
        }
        if ((email.trim().equals(""))) {
            emailEditText.setError("Email is required");
            flag = 1;
        } else {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            Pattern p = Pattern.compile(emailPattern);
            Matcher m = p.matcher(email);
            boolean b = m.matches();
            if (!b) {
                emailEditText.setError("Email is not valid");
                flag = 1;
            }
        }
        return flag;
    }

    public void onBackPressed() {
        finish();

    }

}
