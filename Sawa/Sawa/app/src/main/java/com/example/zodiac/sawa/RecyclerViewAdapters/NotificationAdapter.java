package com.example.zodiac.sawa.RecyclerViewAdapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.MyFriends.FreindsFunctions;
import com.example.zodiac.sawa.NotificationTab;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.models.Notification;
import com.example.zodiac.sawa.models.SettingsRecyclerViewDataProvider;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rabee on 4/24/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.RecyclerViewHolder> {
    ArrayList<NotificationRecyclerViewDataProvider> notificationRecyclerViewDataProviders = new ArrayList<NotificationRecyclerViewDataProvider>();
    View view;

    public NotificationAdapter(ArrayList<NotificationRecyclerViewDataProvider> notificationRecyclerViewDataProviders) {
        this.notificationRecyclerViewDataProviders = notificationRecyclerViewDataProviders;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_recycle_view, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        NotificationRecyclerViewDataProvider dataProvider = notificationRecyclerViewDataProviders.get(position);

        String imageUrl = GeneralAppInfo.IMAGE_URL + dataProvider.getImage();
        Picasso.with(dataProvider.getContext()).load(imageUrl).into(holder.image);
        holder.text.setText(dataProvider.getText() + " sent you a friend request.");
        holder.time.setText(dataProvider.getTime());
        // 0 new   1 not new
        if (dataProvider.getReadFlag() == 0) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(dataProvider.getContext(), R.color.notify));
            Log.d("--- 0 ---", " enter type 0 ");
        } else {
            Log.d("--- 1 ---", " enter type 1 ");

            holder.layout.setBackgroundColor(ContextCompat.getColor(dataProvider.getContext(), R.color.white));
        }


    }

    @Override
    public int getItemCount() {
        return notificationRecyclerViewDataProviders.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView time;
        TextView text;
        RelativeLayout layout;
        int position;

        public RecyclerViewHolder(View view) {
            super(view);
            layout = (RelativeLayout) view.findViewById(R.id.NotificationLayout);
            image = (CircleImageView) view.findViewById(R.id.image);
            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition();
                    if (NotificationTab.NotificationList.get(position).getType().equals("3")) {
                        String name = NotificationTab.NotificationList.get(position).getText();
                        String image = NotificationTab.NotificationList.get(position).getImage();
                        int friend_id = NotificationTab.NotificationList.get(position).getFriend_id();
                        Context context = NotificationTab.NotificationList.get(position).getContext();

                        final FreindsFunctions freindsFunctions = new FreindsFunctions();
                        freindsFunctions.startFriend(context, name, friend_id, image);
                    }
                }
            });
        }
    }

    public static class NotificationRecyclerViewDataProvider {
        String image;
        String text;
        String time;
        Context context;
        int readFlag;
        int friend_id;
        String type;
        String notificatioId;


        public NotificationRecyclerViewDataProvider(Context context, int friend_id, String image, String text, String time, int readFlag) {
            this.image = image;
            this.friend_id = friend_id;
            this.text = text;
            this.time = time;
            this.context = context;
            this.readFlag = readFlag;
        }

        public String getNotificatioId() {
            return notificatioId;
        }

        public void setNotificatioId(String notificatioId) {
            this.notificatioId = notificatioId;
        }

        public Context getContext() {
            return context;
        }

        public int getReadFlag() {
            return readFlag;
        }

        public void setReadFlag(int readFlag) {
            this.readFlag = readFlag;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getFriend_id() {
            return friend_id;
        }

        public void setFriend_id(int friend_id) {
            this.friend_id = friend_id;
        }


        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }


        public void setImage(String image) {
            this.image = image;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImage() {
            return image;
        }

        public String getText() {
            return text;
        }
    }

}
