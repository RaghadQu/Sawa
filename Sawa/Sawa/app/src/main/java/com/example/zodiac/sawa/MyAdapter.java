package com.example.zodiac.sawa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zodiac.sawa.MenuActiviries.MyProfileActivity;
import com.example.zodiac.sawa.MenuActiviries.aboutUserActivity;
import com.example.zodiac.sawa.MyFriends.MyFriendsActivity;
import com.example.zodiac.sawa.MyRequests.MyRequestsActivity;
import com.example.zodiac.sawa.interfaces.NotificationApi;
import com.example.zodiac.sawa.interfaces.logOutApi;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.Notification;
import com.example.zodiac.sawa.models.logOut;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by raghadq on 4/26/2017.
 */

public class MyAdapter extends FastScrollRecyclerView.Adapter<MyAdapter.ViewHolder> {
    String[] mDataset;
    int[] images;
    Context contexts;


    public MyAdapter(Context contexts, String[] mDataser, int[] images) {
        this.contexts = contexts;
        this.mDataset = mDataser;
        this.images = images;
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
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() == 0) {
                        Intent i = new Intent(contexts, MyProfileActivity.class);
                        contexts.startActivity(i);

                    }
                    if (getAdapterPosition() == 1) {
                        //  Intent i = new Intent(contexts, aboutUserActivity.class);
                        Intent i = new Intent(contexts, MyFriendsActivity.class);
                        contexts.startActivity(i);

                    }
                    if (getAdapterPosition() == 2) {
                        Intent i = new Intent(contexts, MyRequestsActivity.class);
                        contexts.startActivity(i);

                    }

                    if (getAdapterPosition() == 3) {
                        SharedPreferences preferences = contexts.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
////////////////////////////////////////////////
                        String android_id = Settings.Secure.getString(contexts.getContentResolver(), Settings.Secure.ANDROID_ID);
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(GeneralAppInfo.BACKEND_URL)
                                .addConverterFactory(GsonConverterFactory.create()).build();
                        logOutApi log_outApi = retrofit.create(logOutApi.class);

                        logOut log_out = new logOut(GeneralAppInfo.getUserID(), android_id);
                        Call<Authentication> logOutnResponse = log_outApi.getLogOut(log_out);

                        logOutnResponse.enqueue(new Callback<Authentication>() {
                            @Override
                            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                                Intent i = new Intent(contexts, MainActivity.class);
                                contexts.startActivity(i);
                                ActivityCompat.finishAffinity((Activity) contexts);

                            }

                            @Override
                            public void onFailure(Call<Authentication> call, Throwable t) {
                                Log.d("Fail", t.getMessage());
                            }

                        });
///////////////////////////////////////////////////////
                    }
                }

            });
        }
    }
}
