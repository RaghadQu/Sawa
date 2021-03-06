package com.example.zodiac.sawa.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zodiac.sawa.FriendProfile.FreindsFunctions;
import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.MenuActiviries.MyProfileActivity;
import com.example.zodiac.sawa.MenuActiviries.MyRequestsActivity;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.Spring.Models.FriendRequestModel;
import com.example.zodiac.sawa.SpringApi.FriendshipInterface;
import com.example.zodiac.sawa.interfaces.ConfirmFriendRequest;
import com.example.zodiac.sawa.interfaces.DeleteFriend;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RequestScroll extends RecyclerView.Adapter<RequestScroll.UserViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private Context mContext;
    DeleteFriend service;
    ConfirmFriendRequest service_confirm;
    FreindsFunctions friendFunction;

    ArrayList<MyRequestsActivity.friend> userList;

    public RequestScroll(Context mContext, ArrayList<MyRequestsActivity.friend> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.friend_request_view, null);
        UserViewHolder viewHolder = new UserViewHolder(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(DeleteFriend.class);
        service_confirm = retrofit.create(ConfirmFriendRequest.class);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final FreindsFunctions freindsFunctions = new FreindsFunctions();
        final MyRequestsActivity.friend user = userList.get(position);
        holder.tvName.setText(user.getUserName());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the same user enter his profile
                if (user.getId() == GeneralAppInfo.getUserID()) {
                    Intent i = new Intent(mContext, MyProfileActivity.class);
                    mContext.startActivity(i);
                } else {
                    try {
                        freindsFunctions.startFriend(mContext, user.getUserName(), user.getId(), user.getImageResourceId());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        String image;
        holder.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user.getId() == GeneralAppInfo.getUserID()) {
                    Intent i = new Intent(mContext, MyProfileActivity.class);
                    mContext.startActivity(i);
                } else {
                    try {
                        freindsFunctions.startFriend(mContext, user.getUserName(), user.getId(), user.getImageResourceId());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            image = user.getImageResourceId();
            String imageUrl = GeneralAppInfo.SPRING_URL + "/" + image;
            Picasso.with(mContext).load(imageUrl).into(holder.ivProfile);
        } catch (MalformedURLException e) {
            holder.ivProfile.setImageResource(R.drawable.account);
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return String.valueOf(userList.get(position).getUserName().charAt(0));
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivProfile;
        TextView tvName;
        Button remove;
        Button confirmReuqest;

        public UserViewHolder(View itemView) {
            super(itemView);
            ivProfile = (CircleImageView) itemView.findViewById(R.id.image);
            tvName = (TextView) itemView.findViewById(R.id.Name);
            remove = (Button) itemView.findViewById(R.id.deleteRequest);
            friendFunction = new FreindsFunctions();
            remove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(GeneralAppInfo.SPRING_URL)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    FriendshipInterface FriendApi = retrofit.create(FriendshipInterface.class);

                    final FriendRequestModel FriendRequest = new FriendRequestModel();
                    FriendRequest.setFriend1_id(GeneralAppInfo.getUserID());
                    FriendRequest.setFriend2_id(Integer.valueOf(userList.get(position).getId()));


                    final Call<Integer> deleteCall = FriendApi.deleteFriendship(FriendRequest);
                    deleteCall.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            MyRequestsActivity.recyclerView.removeViewAt(position);
                            MyRequestsActivity.FreindsList.remove(position);
                            MyRequestsActivity.LayoutFriendsList.remove(position);
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.d("fail to get friends ", "Failure to Get friends");

                        }


                    });


                }
            });


            confirmReuqest = (Button) itemView.findViewById(R.id.ConfirmRequest);


            confirmReuqest.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(GeneralAppInfo.SPRING_URL)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    FriendshipInterface service_confirm = retrofit.create(FriendshipInterface.class);

                    FriendRequestModel friendshipModel = new FriendRequestModel();
                    friendshipModel.setFriend1_id(GeneralAppInfo.getUserID());
                    friendshipModel.setFriend2_id(Integer.valueOf(userList.get(position).getId()));

                    final Call<Integer> confirmCall = service_confirm.confirmFriendship(friendshipModel);
                    confirmCall.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {

                            //    MyRequestsActivity.recyclerView.removeViewAt(position);
                            MyRequestsActivity.FreindsList.remove(position);
                            MyRequestsActivity.LayoutFriendsList.remove(position);
                            notifyItemRemoved(position);
                            Log.d("----- Remove ", "removed" + MyRequestsActivity.FreindsList.size());

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.d("fail to get friends ", "Failure to Get friends");

                        }


                    });


                }
            });
        }
    }
}