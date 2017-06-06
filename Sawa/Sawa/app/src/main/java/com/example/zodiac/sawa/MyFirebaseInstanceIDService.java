package com.example.zodiac.sawa;

import android.provider.Settings;
import android.util.Log;

import com.example.zodiac.sawa.interfaces.TokenApi;
import com.example.zodiac.sawa.models.AuthenticationResponeModel;
import com.example.zodiac.sawa.models.UserTokenModel;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Rabee on 5/13/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(refreshedToken);
        storeToken(refreshedToken);
    }
    public void storeToken(String token) {
        Log.d("Arrive",token);
         String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UserTokenModel userTokenModel = new UserTokenModel(android_id, token);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        TokenApi tokenApi = retrofit.create(TokenApi.class);
        Call<AuthenticationResponeModel> call = tokenApi.storeToken(userTokenModel);
        call.enqueue(new Callback<AuthenticationResponeModel>() {


            @Override
            public void onResponse(Call<AuthenticationResponeModel> call, Response<AuthenticationResponeModel> response) {
                Log.d("Arrive",""+response.body().getState());

            }

            @Override
            public void onFailure(Call<AuthenticationResponeModel> call, Throwable t) {

            }
        });
    }
}
