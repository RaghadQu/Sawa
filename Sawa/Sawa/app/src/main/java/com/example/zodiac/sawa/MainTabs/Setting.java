package com.example.zodiac.sawa.MainTabs;

/**
 * Created by zodiac on 03/27/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecyclerViewAdapters.SettingsAdapter;
import com.example.zodiac.sawa.models.SettingsRecyclerViewDataProvider;

import java.util.ArrayList;

/**
 * Created by filip on 8/21/2015.
 */
public class Setting extends Fragment {
    View view;
    RecyclerView recyclerView;
    SettingsAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    int image1=R.drawable.image1;
    int image2=R.drawable.image2;
    int image3=R.drawable.image1;
    ArrayList<SettingsRecyclerViewDataProvider> dataProviders=new ArrayList<SettingsRecyclerViewDataProvider>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=  inflater.inflate(R.layout.setting_tab,container,false);



        return view;
    }

}