package com.example.zodiac.sawa.MyFriends;

/**
 * Created by raghadq on 5/2/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.zodiac.sawa.R;

public class FastScrollAdapter extends RecyclerView.Adapter<FastScrollAdapter.UserViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private Context mContext;
    ArrayList<MyFriendsActivity.friend> userList;

    public FastScrollAdapter(Context mContext, ArrayList<MyFriendsActivity.friend> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.freinds_recycle_view, null);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        MyFriendsActivity.friend user = userList.get(position);
        holder.tvName.setText(user.getUserName());
        String image ;
        try {
            image = user.getImageResourceId();
            String imageUrl="http://0bdb1326.ngrok.io/Sawa/public/"+image;
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
        }
    }
}