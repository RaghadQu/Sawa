package com.example.zodiac.sawa;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zodiac.sawa.RecoverPassword.RecoverPass;

import com.example.zodiac.sawa.RegisterPkg.RegisterActivity;
import com.example.zodiac.sawa.Spring.Models.LoginWIthGoogleModel;
import com.example.zodiac.sawa.Spring.Models.LoginWithFacebookModel;
import com.example.zodiac.sawa.Spring.Models.SignInModel;
import com.example.zodiac.sawa.Spring.Models.UserModel;
import com.example.zodiac.sawa.SpringApi.AuthInterface;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    LoginButton loginButton;
    private EditText emailEditText;
    private EditText passEditText;
    AuthInterface service;
    private ProgressBar logInProfress;
    CallbackManager callbackManager;
    SignInButton signInButton;
    GoogleApiClient googleApiClient;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.gc();
        System.gc();
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
       /* try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.zodiac.sawa", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions("first_name");
        // loginButton.setReadPermissions("last_name");


        //
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d("Facebook user id ", "" + loginResult.getAccessToken().getUserId());
                Log.d("Facebook token ", "" + loginResult.getAccessToken().getToken());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        // Bundle bFacebookData = getFacebookData(object);
                        try {
                            LoginWithFacebookModel loginWithFacebookModel=new LoginWithFacebookModel();
                            loginWithFacebookModel.setEmail(object.getString("email"));
                            loginWithFacebookModel.setFirstName(object.getString("first_name"));
                            loginWithFacebookModel.setLastName(object.getString("last_name"));
                            loginWithFacebookModel.setId(loginResult.getAccessToken().getUserId());
                            loginWithFacebookModel.setAccessToken(loginResult.getAccessToken().getToken());
                            loginWithFacebookModel.setImage("");
                            loginWithFacebook(loginWithFacebookModel);
                            Log.d("Facebook email", "" + object.getString("email"));
                            Log.d("Facebook email", "" + object.getString("first_name"));
                            Log.d("Facebook email", "" + object.getString("last_name"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Log.d("Facebook token ", "Canceld");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        //Sign in with google section
        signInButton = (SignInButton) findViewById(R.id.loginWithGoogleBtn);
        signInButton.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().
                requestIdToken(getString(R.string.default_web_client_id)).
                requestServerAuthCode(getString(R.string.default_web_client_id)).requestScopes(new Scope(Scopes.DRIVE_APPFOLDER)).build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        //end section

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
            }
        }
    }


    public void forgot_pass(View arg0) {
        Intent i = new Intent(getApplicationContext(), RecoverPass.class);
        startActivity(i);

    }

    public void register(View arg0) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
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

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (requestCode == 9001) {
           // data.getStringExtra("")
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        } else
            callbackManager.onActivityResult(requestCode, responseCode, data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginWithGoogleBtn:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, 9001);
    }

    public void handleResult(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            String email = account.getEmail();
            String userId=account.getId();
            LoginWIthGoogleModel loginWIthGoogleModel=new LoginWIthGoogleModel();
            loginWIthGoogleModel.setEmail(email);
            loginWIthGoogleModel.setAccessToken(account.getIdToken());
            loginWIthGoogleModel.setFirstName(account.getGivenName());
            loginWIthGoogleModel.setLastName(account.getFamilyName());
            loginWIthGoogleModel.setId(userId);

            loginWIthGoogleModel.setImage(account.getPhotoUrl().toString());

            loginWithGoogle(loginWIthGoogleModel);
            Log.d("account.photo", account.getPhotoUrl().toString());
            Log.d("Google email", email);

            Log.d("account.getIdToken();", account.getIdToken());
            Log.d("account.getIdToken();", account.getServerAuthCode());


        }
    }
    public void loginWithGoogle(LoginWIthGoogleModel loginWIthGoogleModel) {
        Log.d("-----", " enter here" );

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Log.d("-----", " enter here" );

        final Call<UserModel> userModelCall = service.loginWithGoogle(loginWIthGoogleModel);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {


                int statusCode = response.code();
                Log.d("-----", " enter request " + statusCode);
                UserModel userModel = response.body();
                //SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                if (statusCode == 200||statusCode == 202) {
                    Log.d("-----", " enter here" + userModel.getId());

                    GeneralAppInfo.setUserID(Integer.valueOf(userModel.getId()));
                    //sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
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
    public void loginWithFacebook(LoginWithFacebookModel loginWithFacebookModel) {
        Log.d("-----", " enter here" );

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Log.d("-----", " enter here" );

        final Call<UserModel> userModelCall = service.loginWithFacebook(loginWithFacebookModel);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {


                int statusCode = response.code();
                Log.d("-----", " enter request " + statusCode);
                UserModel userModel = response.body();
                //SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                if (statusCode == 200||statusCode == 202) {
                    Log.d("-----", " enter here" + userModel.getId());

                    GeneralAppInfo.setUserID(Integer.valueOf(userModel.getId()));
                    //sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
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
