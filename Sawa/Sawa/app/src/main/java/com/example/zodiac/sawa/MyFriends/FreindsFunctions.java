package com.example.zodiac.sawa.MyFriends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.MenuActiviries.MyProfileActivity;
import com.example.zodiac.sawa.interfaces.DeleteFriend;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.interfaces.UserImageApi;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.DeleteFriendRequest;
import com.example.zodiac.sawa.models.userImageFromDb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.zodiac.sawa.MyFriends.MyFriendsActivity.LayoutFriendsList;

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
    public void startMyProfile(Context mContext,String mName,int Id) {
        if (Id == 1) {
            Intent i = new Intent(mContext, MyProfileActivity.class);
            mContext.startActivity(i);
        } else {
            Intent i = new Intent(mContext, MyFriendProfileActivity.class);
            Bundle b = new Bundle();
            b.putString("mName", mName);
            b.putInt("Id", Id);

            i.putExtras(b);
            mContext.startActivity(i);
        }
    }

    public void startFriend(Context mContext,String mName,int Id,String ImageUrl) {
        if (Id == 1) {
            Intent i = new Intent(mContext, MyFriendProfileActivity.class);
            mContext.startActivity(i);
        } else {
            Intent i = new Intent(mContext, MyFriendProfileActivity.class);
            Bundle b = new Bundle();
            b.putString("mName", mName);
            b.putInt("Id", Id);
            b.putString("mImageURL", ImageUrl);


            i.putExtras(b);
            mContext.startActivity(i);
        }
    }


    public void DeleteFriend(int friend1_id, int friend2_id, final Button button){
        DeleteFriend service;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(DeleteFriend.class);

        final DeleteFriendRequest request = new DeleteFriendRequest();
        request.setFriend1_id(friend1_id);
        request.setFriend2_id(friend2_id);


        final Call<Authentication> deleteResponse = service.getState(request);
        deleteResponse.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                Authentication authentication = response.body();
                if(authentication.getState()==1){
                    button.setText("Add as freind");
                }

            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                Log.d("fail to get friends ", "Failure to Get friends");

            }


        });


    }

}
