package com.example.rabee.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rabee on 4/1/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    ArrayList<DataProvider> arrayList=new ArrayList<DataProvider>();
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }
    public RecyclerAdapter(ArrayList<DataProvider> arrayList){
        this.arrayList=arrayList;


    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        DataProvider dataProvider=arrayList.get(position);
        holder.image.setImageResource(dataProvider.getImg_res());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        ImageView image1;
        TextView post;
        public  RecyclerViewHolder(View view){
            super(view );
            image=(ImageView)view.findViewById(R.id.profile_image);



        }
    }
}
