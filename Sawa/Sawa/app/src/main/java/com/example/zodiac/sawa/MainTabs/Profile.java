package com.example.zodiac.sawa.MainTabs;

/**
 * Created by zodiac on 03/27/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zodiac.sawa.Home;
import com.example.zodiac.sawa.ProfileTabs.About;
import com.example.zodiac.sawa.ProfileTabs.Friends;
import com.example.zodiac.sawa.ProfileTabs.Posts;
import com.example.zodiac.sawa.ProfileTabs.Requests;
import com.example.zodiac.sawa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 8/21/2015.
 */
public class Profile extends AppCompatDialogFragment {


    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_user, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new Profile.CustomAdapter(getChildFragmentManager(), getActivity().getApplicationContext()));

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });


        return view;


    }


 class CustomAdapter extends FragmentPagerAdapter {

    private String fragments [] = {"Posts ","About","Friends","Requests"};

    public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Posts();
            case 1:
                return new About();
            case 2:
                return new Friends();
            case 3:
                return new Requests();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}

}