package com.example.zodiac.sawa.Profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.zodiac.sawa.MyFriends.FreindsFunctions;
import com.example.zodiac.sawa.MyFriends.MyFriendsActivity;
import com.example.zodiac.sawa.MyRequests.MyRequestsActivity;
import com.example.zodiac.sawa.R;

/**
 * Created by Rabee on 5/12/2017.
 */

public class FriendsClass {

    Dialog ConfirmDeletion;
    Button NoBtn;
    Button YesBtn;
    FreindsFunctions friendFunction;

    public void SetFriendButtn(final Button friendStatus, RecyclerView recyclerView, Context context , final int Id) {
        recyclerView.setVisibility(View.VISIBLE);
        friendStatus.setText("Friend");

        friendFunction = new FreindsFunctions();
        ConfirmDeletion = new Dialog(context);
        ConfirmDeletion.setContentView(R.layout.confirm_delete_friend_or_request_dialog);
        NoBtn = (Button) ConfirmDeletion.findViewById(R.id.NoBtn);
        YesBtn = (Button) ConfirmDeletion.findViewById(R.id.YesBtn);

        friendStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ConfirmDeletion.show();

                NoBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ConfirmDeletion.dismiss();

                    }
                });

                YesBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ConfirmDeletion.dismiss();
                        friendFunction.DeleteFriend(1,Id,friendStatus);
                      }
                });

                //    if(view==friendStatus){
                //friendStatus.setText("Pending");

                //  }
            }
        });
    }
}
/* anim_button.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //friendStatus.setText("Friend");
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
*/
