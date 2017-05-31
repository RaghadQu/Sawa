package com.example.zodiac.sawa.MyRequests;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.MenuActiviries.MyProfileActivity;
import com.example.zodiac.sawa.MyFriends.FreindsFunctions;
import com.example.zodiac.sawa.MyFriends.MyFriendProfileActivity;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.interfaces.ConfirmFriendRequest;
import com.example.zodiac.sawa.interfaces.DeleteFriend;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.DeleteFriendRequest;
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
                if (Integer.parseInt(user.getId()) == GeneralAppInfo.getUserID()) {
                    Intent i = new Intent(mContext, MyProfileActivity.class);
                    mContext.startActivity(i);
                } else {
                    try {
                        freindsFunctions.startFriend(mContext, user.getUserName(), Integer.parseInt(user.getId()),user.getImageResourceId());
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

                if (Integer.parseInt(user.getId()) == GeneralAppInfo.getUserID()) {
                    Intent i = new Intent(mContext, MyProfileActivity.class);
                    mContext.startActivity(i);
                } else {
                    try {
                        freindsFunctions.startFriend(mContext, user.getUserName(), Integer.parseInt(user.getId()),user.getImageResourceId());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            image = user.getImageResourceId();
            String imageUrl = GeneralAppInfo.IMAGE_URL + image;
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
            remove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final DeleteFriendRequest request = new DeleteFriendRequest();
                    Log.d("------ Y ", "  :  " + Integer.valueOf(userList.get(position).getId()));
                    request.setFriend1_id(GeneralAppInfo.getUserID());
                    request.setFriend2_id(Integer.valueOf(userList.get(position).getId()));

                    final Call<Authentication> deleteResponse = service.getState(request);
                    deleteResponse.enqueue(new Callback<Authentication>() {
                        @Override
                        public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                            Authentication state = response.body();
                            Log.d("-----------", " Body" + response.code() + " : " + state.getState());
                            MyRequestsActivity.recyclerView.removeViewAt(position);
                            MyRequestsActivity.FreindsList.remove(position);
                            //notifyItemRemoved(position);
                            //  notifyDataSetChanged();
                            MyRequestsActivity.LayoutFriendsList.remove(position);
                            notifyItemRemoved(position);
                           if (MyRequestsActivity.FreindsList.size()==0)
                           {
                               Intent i = new Intent(mContext, MyRequestsActivity.class);
                               mContext.startActivity(i);
                           }
                            // notifyItemRangeChanged(position,MyFriendsActivity.FreindsList.size());

                            //MyFriendsActivity.recyclerView.setAdapter(MyFriendsActivity.adapter);
                            Log.d("----- Remove ", "removed" + MyRequestsActivity.FreindsList.size());

                        }

                        @Override
                        public void onFailure(Call<Authentication> call, Throwable t) {
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

                    final DeleteFriendRequest request = new DeleteFriendRequest();
                    Log.d("------ Y ", "  :  " + Integer.valueOf(userList.get(position).getId()));
                    request.setFriend1_id(GeneralAppInfo.getUserID());
                    request.setFriend2_id(Integer.valueOf(userList.get(position).getId()));

                    final Call<Authentication> deleteResponse = service_confirm.getState(request);
                    deleteResponse.enqueue(new Callback<Authentication>() {
                        @Override
                        public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                            Authentication state = response.body();
                            Log.d("-----------", " Body" + response.code() + " : " + state.getState());

                            MyRequestsActivity.recyclerView.removeViewAt(position);
                            MyRequestsActivity.FreindsList.remove(position);
                            //notifyItemRemoved(position);
                            //  notifyDataSetChanged();
                            MyRequestsActivity.LayoutFriendsList.remove(position);
                            notifyItemRemoved(position);
                            // notifyItemRangeChanged(position,MyFriendsActivity.FreindsList.size());

                            //MyFriendsActivity.recyclerView.setAdapter(MyFriendsActivity.adapter);
                            Log.d("----- Remove ", "removed" + MyRequestsActivity.FreindsList.size());

                        }

                        @Override
                        public void onFailure(Call<Authentication> call, Throwable t) {
                            Log.d("fail to get friends ", "Failure to Get friends");

                        }


                    });


                }
            });
        }
    }
}