package com.example.zodiac.sawa;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zodiac.sawa.RecyclerViewAdapters.NotificationAdapter;
import com.example.zodiac.sawa.layout.homeFragment;
import com.example.zodiac.sawa.layout.settingsFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

import static com.example.zodiac.sawa.R.id.container;


public class HomeTabbedActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    ImageView searchImage;
    EditText searchText;


    @Override
    protected void onResume() {
        super.onResume();
        NotificationTab.getUserNotifications(getApplicationContext());

     }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_tabbed2);
        ImageView searchImage = (ImageView) findViewById(R.id.serachImage);
        LinearLayout searchLayout= (LinearLayout) findViewById(R.id.SearchLayout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("C","Clicked");
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Refresh", token);
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ;
        GeneralFunctions generalFunctions = new GeneralFunctions();
        generalFunctions.storeUserIdWithDeviceId(GeneralAppInfo.getUserID(), android_id);


        // Set up the action bar.

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(10);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
               if(position==GeneralAppInfo.notifications_tab_position){

                   editor.putInt("notifications_counter",0);

                   editor.apply();
                   Log.d("notifications_tab_position",""+sharedPreferences.getInt("notifications_counter", 0));
               }else if (position==GeneralAppInfo.home_tab_position){
                   Log.d("home_tab_position",""+sharedPreferences.getInt("notifications_counter", 0));
               }else if (position==GeneralAppInfo.setting_tab_position) {
                    Log.d("setting_tab_position",""+sharedPreferences.getInt("notifications_counter", 0));
               }


               }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            int iconId = -1;
            switch (i) {
                case 0:
                    iconId = R.drawable.home;
                    break;
                case 1:
                    iconId = R.drawable.notification;
                    break;
                case 2:

                    iconId = R.drawable.setting_small;
                    break;
            }
            tabLayout.getTabAt(i).setIcon(iconId);

        }
        final LayoutInflater factory = getLayoutInflater();
        View v =factory.inflate(R.layout.notification_tab, null);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("Hello" ,"enter notification bar");

                return false;
            }
        });


         BadgeView badge;
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.setIcon(R.drawable.notification);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
       // tab.setCustomView(imageView);
        badge = new BadgeView(getApplicationContext(), imageView);
        badge.setText("7");
        badge.getOffsetForPosition(120,30);
        badge.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment {


        int image1 = R.drawable.image1;
        int image2 = R.mipmap.friends_setting;
        int image3 = R.drawable.request_friend_setting;
        int image4 = R.drawable.log;
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        String[] myDataset = {"Profile", "Friends", "Friend Requests", "Log out"};
        int[] images = {image1, image2, image3, image4};
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                GeneralFunctions.getSharedPreferences(getContext());
                View rootView = inflater.inflate(R.layout.fragment_home, container, false);
                return rootView;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                //NotificationTab;
                View rootView = inflater.inflate(R.layout.notification_tab, container, false);
                NotificationTab.NotificationList = new ArrayList<>();
                NotificationTab.adapter = new NotificationAdapter(NotificationTab.NotificationList);
                NotificationTab.recyclerView=(FastScrollRecyclerView)rootView.findViewById(R.id.recyclerNotification);
                NotificationTab.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                NotificationTab.recyclerView.setAdapter(NotificationTab.adapter);
                NotificationTab.getUserNotifications(getContext());
                return rootView;

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                GeneralFunctions.getSharedPreferences(getContext());
                View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
                mRecyclerView = (RecyclerView) rootView.findViewById(R.id.Viewer);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                MyAdapter settingAdapter = new MyAdapter(getContext(), myDataset, images);
                mRecyclerView.setAdapter(settingAdapter);
                return rootView;

            } else {
                View rootView = inflater.inflate(R.layout.fragment_home, container, false);
                return rootView;
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return " ";
        }
    }
}
