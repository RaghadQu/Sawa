package com.example.zodiac.sawa;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zodiac.sawa.MenuActiviries.MyProfileActivity;
import com.example.zodiac.sawa.MyFriends.MyFriendsActivity;
import com.example.zodiac.sawa.MyRequests.MyRequestsActivity;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

/**
 * Created by raghadq on 4/26/2017.
 */

public class MyAdapter extends FastScrollRecyclerView.Adapter<MyAdapter.ViewHolder> {
    String[] mDataset;
    int [] images;
    Context contexts;



    public  MyAdapter(Context contexts , String [] mDataser ,int [] images)
    {
        this.contexts=contexts;
        this.mDataset=mDataser;
        this.images=images;
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexts).inflate(R.layout.settings_item_view, null);
        MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        String user = mDataset[position];
        holder.tvName.setText(user);
        holder.ivProfile.setImageBitmap(BitmapFactory.decodeResource(contexts.getResources(), images[position]));
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfile;
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.image);
            tvName = (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    if(getAdapterPosition()==0)
                    {
                        Intent i = new Intent(contexts, MyProfileActivity.class);
                        contexts.startActivity(i);

                    }
                    if(getAdapterPosition()==1)
                    {
                        Intent i = new Intent(contexts, MyFriendsActivity.class);
                        contexts.startActivity(i);

                    }
                    if(getAdapterPosition()==2)
                    {
                        Intent i = new Intent(contexts, MyRequestsActivity.class);
                        contexts.startActivity(i);

                    }


                }

            });
        }
    }
}
