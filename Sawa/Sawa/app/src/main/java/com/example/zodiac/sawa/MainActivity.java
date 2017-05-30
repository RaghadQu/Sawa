package com.example.zodiac.sawa;

import android.app.ActivityOptions;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zodiac.sawa.MenuActiviries.MyProfileActivity;
import com.example.zodiac.sawa.MyFriends.MyFriendProfileActivity;
import com.example.zodiac.sawa.RecoverPassword.RecoverPass;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.interfaces.LoginAuth;
import com.example.zodiac.sawa.interfaces.NotificationApi;
import com.example.zodiac.sawa.models.AboutUser;
import com.example.zodiac.sawa.models.AuthRequest;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.Notification;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    LoginAuth service;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setContentView(R.layout.activity_main);


        //check if the user is already signed in
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        int id = sharedPreferences.getInt("id",-1);
        String isLogined = sharedPreferences.getString("isLogined", "");
        GeneralAppInfo.setUserID(id);


        if ((isLogined.equals("1"))) {
            Intent i = new Intent(getApplicationContext(), HomeTabbedActivity.class);
            startActivity(i);
            finish();
        }

    /*   DBHandler dbHandler = new DBHandler(getApplicationContext());
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.profileimage);
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); // what 90 does ??
        byte[] image=stream.toByteArray();
        dbHandler.insertUserImage(1, image);
        AboutUser aboutUser=new AboutUser(1,"","","");
        dbHandler.addAboutUser(aboutUser);*/

        // Address the email  and password field


        emailEditText = (EditText) findViewById(R.id.username);
        passEditText = (EditText) findViewById(R.id.password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(LoginAuth.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void checkLogin(View arg0) {
        GeneralFunctions generalFunctions = new GeneralFunctions();
        boolean isOnline = generalFunctions.isOnline(getApplicationContext());


        if (isOnline == false) {
            Toast.makeText(this, "no internet connection!",
                    Toast.LENGTH_LONG).show();
        } else {


            final AuthRequest request = new AuthRequest();
            request.setEmail(emailEditText.getText().toString());
            request.setPassword(passEditText.getText().toString());
            if (valid(request.getEmail(), request.getPassword()) == 0) {
                final Call<Authentication> AuthResponse = service.getState(request);
                AuthResponse.enqueue(new Callback<Authentication>() {
                    @Override
                    public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                        int statusCode = response.code();
                        Log.d("-----", " enter request " + statusCode);

                        Authentication authResponse = response.body();
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        String email = sharedPreferences.getString("email", "");

                        if (authResponse.getState() == 1) {
                            Log.d("-----", " enter here");

                            GeneralAppInfo.setUserID(Integer.valueOf(authResponse.getUser_id()));
                            //Store user info in shared prefrences file
                            sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", emailEditText.getText().toString());
                            editor.putString("password", passEditText.getText().toString());
                            editor.putInt("id",GeneralAppInfo.getUserID());
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
                    public void onFailure(Call<Authentication> call, Throwable t) {
                        Log.d("----", " Error " + t.getMessage());


                    }
                });
            }
        }
        // Intent i = new Intent(getApplicationContext(), MyFriendProfileActivity.class);


        //   Intent i = new Intent(getApplicationContext(), HomeTabbedActivity.class);
        //startActivity(i);

        //   finish();
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
