package com.example.zodiac.sawa.Profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.HomeTabbedActivity;
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
    TextView textMsg;

    public void SetFriendButtn(final Button friendStatus, final RecyclerView recyclerView, final Context context, final int Id, Context c) {
        recyclerView.setVisibility(View.VISIBLE);
        if (GeneralAppInfo.friendMode == 0)
            friendStatus.setText("Pending");
        if (GeneralAppInfo.friendMode == 1)
            friendStatus.setText("Friend");
        if (GeneralAppInfo.friendMode == 2)
            friendStatus.setText("Follow");

        //
        friendFunction = new FreindsFunctions();
        ConfirmDeletion = new Dialog(context);
        ConfirmDeletion.setContentView(R.layout.confirm_delete_friend_or_request_dialog);
        NoBtn = (Button) ConfirmDeletion.findViewById(R.id.NoBtn);
        YesBtn = (Button) ConfirmDeletion.findViewById(R.id.YesBtn);
        //

        textMsg = (TextView) ConfirmDeletion.findViewById(R.id.TextMsg);

        friendStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Pending state
                if (GeneralAppInfo.friendMode == 0) {
                    textMsg.setText("Are you sure you want to delete this request ?");
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
                           // recyclerView.setVisibility(View.GONE);

                            GeneralAppInfo.friendMode = 2;
                            friendStatus.setText("Follow");
                            ConfirmDeletion.dismiss();
                            friendFunction.DeleteFriend(GeneralAppInfo.getUserID(), Id, friendStatus);


                        }
                    });

                }
                //Friend state
                else if (GeneralAppInfo.friendMode == 1) {
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
                            GeneralAppInfo.friendMode = 2;
                            recyclerView.setVisibility(View.GONE);

                            friendStatus.setText("Follow");
                            ConfirmDeletion.dismiss();
                            friendFunction.DeleteFriend(GeneralAppInfo.getUserID(), Id, friendStatus);
                        }
                    });
                }
                //not friend state
                else if (GeneralAppInfo.friendMode == 2) {
                    GeneralAppInfo.friendMode = 0;
                    friendStatus.setText("Pending");
                    NotFriendProfileClass notFriendProfileClass = new NotFriendProfileClass();
                    notFriendProfileClass.addNewFriendShip(GeneralAppInfo.getUserID(), Id);
                }


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
