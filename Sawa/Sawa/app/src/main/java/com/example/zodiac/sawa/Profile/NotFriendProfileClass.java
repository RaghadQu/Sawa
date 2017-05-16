package com.example.zodiac.sawa.Profile;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.interfaces.DeleteFriend;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.DeleteFriendRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rabee on 5/12/2017.
 */

public class NotFriendProfileClass {
    public void SetFriendButtn(final Button friendStatus, RecyclerView recyclerView,final int Id) {
        //recyclerView.setVisibility(View.GONE);
        friendStatus.setText("Add");
        friendStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view == friendStatus && friendStatus.getText().equals("Add")) {
                    friendStatus.setText("Pending");
                    addNewFriendShip(1, Id);
                    addNewFriendShip(1, Id);

                } else
                    Log.d("Add friend ship", "Already sent");


            }
        });
    }

    public void addNewFriendShip(int friend1_id, int friend2_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        GetFreinds getFreinds = retrofit.create(GetFreinds.class);
        DeleteFriendRequest deleteFriendRequest = new DeleteFriendRequest(friend1_id, friend2_id);
        Call<Authentication> call = getFreinds.addNewFriendShip(deleteFriendRequest);
        call.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                Authentication authentication = response.body();
                Log.d("Add friend ship", "" + response.body());

            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                Log.d("stateeee", "fail nnnnnnn");

            }
        });

    }
}
