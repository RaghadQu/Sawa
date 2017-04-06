package com.example.zodiac.sawa.MainTabs;

/**
 * Created by zodiac on 03/27/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zodiac.sawa.R;

/**
 * Created by filip on 8/21/2015.
 */
public class Setting extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.setting_tab,container,false);
    }
}