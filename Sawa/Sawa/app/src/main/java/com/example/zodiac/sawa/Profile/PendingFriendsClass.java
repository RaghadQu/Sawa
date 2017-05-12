package com.example.zodiac.sawa.Profile;

import android.view.View;
import android.widget.Button;

/**
 * Created by Rabee on 5/12/2017.
 */

public class PendingFriendsClass {
    public void SetFriendButtn(final Button friendStatus){
        friendStatus.setText("Pending");
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
