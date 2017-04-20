package com.example.zodiac.sawa.ProfileTabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zodiac.sawa.R;

/**
 * Created by zodiac on 04/03/2017.
 */

public class Requests  extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.friends_tab, container, false);
        return view;
    }

}
