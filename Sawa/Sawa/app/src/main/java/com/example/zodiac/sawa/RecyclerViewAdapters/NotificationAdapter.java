package com.example.zodiac.sawa.RecyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.models.SettingsRecyclerViewDataProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rabee on 4/24/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.RecyclerViewHolder> {
    ArrayList<NotificationRecyclerViewDataProvider> notificationRecyclerViewDataProviders = new ArrayList<NotificationRecyclerViewDataProvider>();
    View view;

    public NotificationAdapter(ArrayList<SettingsRecyclerViewDataProvider> settingsRecyclerViewDataProviders) {
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
        holder.image.setImageResource(dataProvider.getImage());
        holder.text.setText(dataProvider.getText());


    }

    @Override
    public int getItemCount() {
        return notificationRecyclerViewDataProviders.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageButton image;
        TextView time;
        TextView text;

        public RecyclerViewHolder(View view) {
            super(view);
            image = (ImageButton) view.findViewById(R.id.image);
            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

    public class NotificationRecyclerViewDataProvider {
        int image;
        String text;
        String time;

        public NotificationRecyclerViewDataProvider(int image, String text, String time) {
            this.image = image;
            this.text = text;
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }


        public void setImage(int image) {
            this.image = image;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getImage() {
            return image;
        }

        public String getText() {
            return text;
        }
    }

}
