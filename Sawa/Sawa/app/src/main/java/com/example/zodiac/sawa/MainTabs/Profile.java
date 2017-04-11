package com.example.zodiac.sawa.MainTabs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zodiac.sawa.Home;
import com.example.zodiac.sawa.ProfileTabs.About;
import com.example.zodiac.sawa.ProfileTabs.Friends;
import com.example.zodiac.sawa.ProfileTabs.Posts;
import com.example.zodiac.sawa.ProfileTabs.Requests;
import com.example.zodiac.sawa.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Profile extends AppCompatDialogFragment {


    TabLayout tabLayout;
    ViewPager viewPager;
    View view;
    Uri imageuri;
    ImageView img;
    Dialog imgClick;
    Dialog ViewImgDialog;
    TextView changePic , viewPic;
    ImageView imageView; // View image in dialog
    private static final int SELECTED_PICTURE = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_user, container, false);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imgClick=new Dialog(getActivity());
        imgClick.setContentView(R.layout.profile_picture_dialog);
        imgClick.getWindow().getAttributes().y=-130;
        imgClick.getWindow().getAttributes().x=70;

        ViewImgDialog=new Dialog(getActivity());
        ViewImgDialog.setContentView(R.layout.view_profilepic_dialog);
        ViewImgDialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView=(ImageView) ViewImgDialog.findViewById(R.id.ImageView);
       // imageView.setImageURI();

        img = (ImageView) view.findViewById(R.id.user_profile_photo);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imgClick.show();
                changePic = (TextView) imgClick.findViewById(R.id.EditPic);
                viewPic = (TextView) imgClick.findViewById(R.id.ViewPic);

                changePic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        imgClick.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, SELECTED_PICTURE);
                    }
                });

                viewPic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        imgClick.dismiss();
                        imageView.setImageDrawable(img.getDrawable());
                        ViewImgDialog.show();

                    }
                });
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECTED_PICTURE) {
            imageuri = data.getData();
            img.setImageURI(imageuri);

        }
    }

    class CustomAdapter extends FragmentPagerAdapter {

        private String fragments[] = {"Posts ", "About", "Friends", "Requests"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
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