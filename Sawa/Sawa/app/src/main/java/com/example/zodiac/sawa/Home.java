package com.example.zodiac.sawa;

        import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

        import com.example.zodiac.sawa.MainTabs.HomeTab;
        import com.example.zodiac.sawa.MainTabs.Profile;
        import com.example.zodiac.sawa.MainTabs.Notification;
        import com.example.zodiac.sawa.MainTabs.Setting;

public class Home extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            int iconId = -1;
            switch (i) {
                case 0:
                    iconId = R.drawable.home;
                    break;
                case 1:
                    iconId = R.drawable.profile;
                    break;
                case 2:
                    iconId = R.drawable.notification;
                    break;
                case 3:
                    iconId = R.drawable.setting;
                    break;
            }
            tabLayout.getTabAt(i).setIcon(iconId);
        }

// Needed since support libraries version 23.0.0
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
    }




    private class CustomAdapter extends FragmentPagerAdapter {

        private String fragments [] = {"Home ","Profile","Not","Set"};

       public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new HomeTab();
                case 1:
                    return new Profile();
                case 2:
                    return new Notification();
                case 3:
                    return new Setting();
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
            return "";
        }
            }

}