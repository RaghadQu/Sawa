package com.example.zodiac.sawa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.zodiac.sawa.interfaces.LoginAuth;
import com.example.zodiac.sawa.models.AuthRequest;
import com.example.zodiac.sawa.models.Authentication;

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
    LoginAuth service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //check if the user is already signed in
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String isLogined = sharedPreferences.getString("isLogined", "");

        if ((isLogined.equals("aa"))) {
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            finish();
        }

        // Address the email  and password field
        emailEditText = (EditText) findViewById(R.id.username);
        passEditText = (EditText) findViewById(R.id.password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://b58799f1.ngrok.io/Sawa/public/index.php/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(LoginAuth.class);
    }

    public void checkLogin(View arg0) {
        int state;
        final AuthRequest request = new AuthRequest();
        request.setEmail(emailEditText.getText().toString());
        request.setPassword(passEditText.getText().toString());
        if (valid(request.getEmail(), request.getPassword()) == 0) {
            final Call<Authentication> AuthResponse = service.getState(request);
            AuthResponse.enqueue(new Callback<Authentication>() {
                @Override
                public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                    int statusCode = response.code();
                    Authentication authResponse = response.body();
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    String email = sharedPreferences.getString("email", "");
                    if (authResponse.getState() == 1) {
                        //Store user info in shared prefrences file
                        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", emailEditText.getText().toString());
                        editor.putString("password", passEditText.getText().toString());
                        editor.putString("isLogined", "1");
                        editor.apply();
                        Intent i = new Intent(getApplicationContext(), Home.class);
                        startActivity(i);
                        finish();
                    } else {
                        emailEditText.setError("Invalid Email or Password");
                    }

                }

                @Override
                public void onFailure(Call<Authentication> call, Throwable t) {

                }
            });
        }
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }

    public void forgot_pass(View arg0) {
        Intent i = new Intent(getApplicationContext(), RecoverPass.class);
        startActivity(i);
    }

    public void register(View arg0) {
        Intent i = new Intent(getApplicationContext(), Register.class);
        startActivity(i);
    }



    public int valid(String email,String password) {
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

}
