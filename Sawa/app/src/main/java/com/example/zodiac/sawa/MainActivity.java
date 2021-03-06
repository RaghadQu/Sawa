package com.example.zodiac.sawa;

import android.content.Intent;
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
        // Address the email and password field
        emailEditText = (EditText) findViewById(R.id.username);
        passEditText = (EditText) findViewById(R.id.password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://0963c12e.ngrok.io/Sawa/public/index.php/")
                .addConverterFactory(GsonConverterFactory.create()).build();
         service = retrofit.create(LoginAuth.class);
    }

    public void checkLogin(View arg0) {
        int state;
        final AuthRequest request = new AuthRequest();
        request.setEmail(emailEditText.getText().toString());
        request.setPassword(passEditText.getText().toString());

        final Call<Authentication> AuthResponse = service.getState(request);
        AuthResponse.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                int statusCode = response.code();
                Authentication authResponse = response.body();
                Log.d("HERE","--------------------- "+request.getEmail()+" " +request.getPassword());
               if ( authResponse.getState()==1 ) {
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                }
                else
                {
                    emailEditText.setError("Invalid Email or Password");
                }
            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {

            }
        });
       // service.getAuth(request)
    /*    final String email = emailEditText.getText().toString();
        if (!isValidEmail(email)) {
            //Set error message for email field
            emailEditText.setError("Invalid Email");
        }

        final String pass = passEditText.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            passEditText.setError("Password cannot be empty");
        }

        if(isValidEmail(email) && isValidPassword(pass))
        {
            Intent i = new Intent(getApplicationContext(),Home.class);
            startActivity(i);
        }*/

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

    public void forgot_pass(View arg0){
        Intent i = new Intent(getApplicationContext(),RecoverPass.class);
        startActivity(i);
    }

    public void register(View arg0){
        Intent i = new Intent(getApplicationContext(),Register.class);
        startActivity(i);
    }
}