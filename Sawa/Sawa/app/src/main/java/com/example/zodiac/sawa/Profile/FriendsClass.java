package com.example.zodiac.sawa.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

/**
 * Created by Rabee on 5/12/2017.
 */

public class FriendsClass {
    public void SetFriendButtn(final Button friendStatus, RecyclerView recyclerView){
        recyclerView.setVisibility(View.VISIBLE);
        friendStatus.setText("Friend");
        friendStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(view==friendStatus){
                    //friendStatus.setText("Pending");

                }
            }
        });
    }
}
