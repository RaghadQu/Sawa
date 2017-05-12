package com.example.zodiac.sawa.MyFriends;

/**
 * Created by raghadq on 5/2/2017.
 */

import android.app.Service;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.interfaces.DeleteFriend;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.DeleteFriendRequest;
import com.example.zodiac.sawa.models.getFriendsRequest;
import com.example.zodiac.sawa.models.getFriendsResponse;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.zodiac.sawa.R;

import static com.example.zodiac.sawa.MyFriends.MyFriendsActivity.LayoutFriendsList;

public class FastScrollAdapter extends RecyclerView.Adapter<FastScrollAdapter.UserViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private Context mContext;
    public View view;
    UserViewHolder viewHolder;
    ArrayList<MyFriendsActivity.friend> userList;
    DeleteFriend service;
    Button remove;
    public FastScrollAdapter(Context mContext, ArrayList<MyFriendsActivity.friend> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }



    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         view = LayoutInflater.from(mContext).inflate(R.layout.freinds_recycle_view, null);
        viewHolder = new UserViewHolder(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(DeleteFriend.class);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        MyFriendsActivity.friend user = userList.get(position);
        holder.tvName.setText(user.getUserName());
        String image ;
        try {
            image = user.getImageResourceId();
            String imageUrl="http://1ce63f59.ngrok.io/Sawa/public/"+image;
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

        public UserViewHolder(View itemView) {
            super(itemView);
            ivProfile = (CircleImageView) itemView.findViewById(R.id.image);
            tvName = (TextView) itemView.findViewById(R.id.Name);
            remove = (Button) view.findViewById(R.id.Remove);


            remove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {



                    final int position = getAdapterPosition();
                    final DeleteFriendRequest request = new DeleteFriendRequest();
                    Log.d("------ Y ","  :  "+ Integer.valueOf(userList.get(position).getId()));
                    request.setFriend1_id(1);
                    request.setFriend2_id(Integer.valueOf(userList.get(position).getId()));


                    final Call<Authentication> deleteResponse = service.getState(request);
                    deleteResponse.enqueue(new Callback<Authentication>() {
                        @Override
                        public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                            Authentication state = response.body();
                            Log.d("-----------"," Body"+ response.code()+ " : "+ state.getState());
                            Log.d("Position",position+"");
                            MyFriendsActivity.recyclerView.removeViewAt(position);
                            MyFriendsActivity.FreindsList.remove(position);
                            //notifyItemRemoved(position);
                          //  notifyDataSetChanged();
                            LayoutFriendsList.remove(position);
                            notifyItemRemoved(position);
                           // notifyItemRangeChanged(position,MyFriendsActivity.FreindsList.size());

                            //MyFriendsActivity.recyclerView.setAdapter(MyFriendsActivity.adapter);
                            Log.d("----- Remove ","removed" + MyFriendsActivity.FreindsList.size());

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