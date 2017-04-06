package com.example.zodiac.sawa.ProfileTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zodiac.sawa.R;

/**
 * Created by zodiac on 04/03/2017.
 */

public class About extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.about_tab,container,false);
    }

        // Setting ViewPager for each Tabs
}
