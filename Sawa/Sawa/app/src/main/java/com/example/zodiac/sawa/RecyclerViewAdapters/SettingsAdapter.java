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

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.RecyclerViewHolder> {
    ArrayList<SettingsRecyclerViewDataProvider> settingsRecyclerViewDataProviders = new ArrayList<SettingsRecyclerViewDataProvider>();
    View view;

    public SettingsAdapter(ArrayList<SettingsRecyclerViewDataProvider> settingsRecyclerViewDataProviders) {
        this.settingsRecyclerViewDataProviders = settingsRecyclerViewDataProviders;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item_view, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        SettingsRecyclerViewDataProvider dataProvider = settingsRecyclerViewDataProviders.get(position);
        holder.image.setImageResource(dataProvider.getImage());
        holder.text.setText(dataProvider.getText());


    }

    @Override
    public int getItemCount() {
        return settingsRecyclerViewDataProviders.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageButton image;

        TextView text;

        public RecyclerViewHolder(View view) {
            super(view);
            image = (ImageButton) view.findViewById(R.id.image);
            text = (TextView) view.findViewById(R.id.text);


        }
    }
}
