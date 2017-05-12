package com.example.zodiac.sawa.MyFriends;

import android.util.Log;
import android.widget.Button;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.interfaces.UserImageApi;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.userImageFromDb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rabee on 5/5/2017.
 */

public class FreindsFunctions {
    public void getFreindShipState(int friend1_id, int friend2_id, final Button button){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        GetFreinds getFreinds = retrofit.create(GetFreinds.class);
        Call<Authentication> call = getFreinds.getFriendshipState(1,2);
        call.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                Authentication authentication=response.body();
                Log.d("stateeee",""+authentication.getState());
                if(authentication.getState()==2){
                    button.setText("Add as freind");
                }else if(authentication.getState()==0){
                    button.setText("Pending");
                }else if(authentication.getState()==1){
                    button.setText("Freind");
                }
            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                Log.d("stateeee","fail nnnnnnn");

            }
        });
    }
}
