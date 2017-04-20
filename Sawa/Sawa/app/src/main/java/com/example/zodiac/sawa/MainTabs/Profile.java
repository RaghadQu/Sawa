package com.example.zodiac.sawa.MainTabs;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.graphics.Bitmap;

=======
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
>>>>>>> d03ee4e0c7837ea73904aacb36669aed4aa32b47
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialogFragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
<<<<<<< HEAD
import com.example.zodiac.sawa.DB.DBHandler;
=======
import android.widget.Toast;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.GeneralFunctions;
import com.example.zodiac.sawa.Home;
>>>>>>> d03ee4e0c7837ea73904aacb36669aed4aa32b47
import com.example.zodiac.sawa.ImageConverter.ImageConverter;
import com.example.zodiac.sawa.ImageConverter.uploadImage;
import com.example.zodiac.sawa.ProfileTabs.About;
import com.example.zodiac.sawa.ProfileTabs.Friends.Friends;
import com.example.zodiac.sawa.ProfileTabs.Posts;
import com.example.zodiac.sawa.ProfileTabs.Requests;
import com.example.zodiac.sawa.R;


<<<<<<< HEAD
import java.io.IOException;

=======
import java.io.File;
import java.io.FileNotFoundException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

>>>>>>> d03ee4e0c7837ea73904aacb36669aed4aa32b47
import static android.app.Activity.RESULT_OK;
import static java.lang.System.out;

public class Profile extends AppCompatDialogFragment {


    TabLayout tabLayout;
    ViewPager viewPager;
    View view;
    Uri imageuri;
    ImageView img;
    Dialog imgClick;
    Dialog ViewImgDialog;
    TextView changePic, viewPic;
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

        imgClick = new Dialog(getActivity());
        imgClick.setContentView(R.layout.profile_picture_dialog);
        imgClick.getWindow().getAttributes().y = -130;
        imgClick.getWindow().getAttributes().x = 70;


        Log.d("Set", "s");
        ViewImgDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ViewImgDialog.setContentView(R.layout.view_profilepic_dialog);
        imageView = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);

        img = (ImageView) view.findViewById(R.id.user_profile_photo);
        // imageView.setImageURI();
        DBHandler dbHandler = new DBHandler(getContext());
<<<<<<< HEAD
        uploadImage uploadImage=new uploadImage();
       // String imageUrl=uploadImage.getUserImageFromDB(1,img,getContext());


      //  Bitmap bitmap = dbHandler.getUserImage(1);
=======
        uploadImage uploadImage = new uploadImage();
        String imageUrl = uploadImage.getUserImageFromDB(1, img, getContext());


        Bitmap bitmap = dbHandler.getUserImage(1);
       // bitmap = RotateBitmap(bitmap, -90);
        img.setImageBitmap(bitmap);

>>>>>>> d03ee4e0c7837ea73904aacb36669aed4aa32b47

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

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECTED_PICTURE) {
            imageuri = data.getData();
            try {
                verifyStoragePermissions(getActivity());
                GeneralFunctions generalFunctions = new GeneralFunctions();
                String path = generalFunctions.getRealPathFromURI(getContext(), imageuri);
                Log.d("Path", path);
                int rotate = generalFunctions.getPhotoOrientation(path);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageuri);
                bitmap = scaleDown(bitmap, 1000, true);
                bitmap = RotateBitmap(bitmap, rotate);
                img.setImageBitmap(bitmap);

                ImageConverter imageConverter = new ImageConverter();
                byte[] image = imageConverter.getBytes(bitmap);
                DBHandler dbHandler = new DBHandler(getContext());
                dbHandler.updateUserImage(1, image);
                String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
                uploadImage uploadImage = new uploadImage();
                uploadImage.uploadImagetoDB(1, encodedImage);

            } catch (Exception e) {
                Toast toast = Toast.makeText(getContext(), "Image is large", Toast.LENGTH_SHORT);
                toast.show();
            }
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

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        Log.d("width", "" + realImage.getWidth());
        Log.d("height", "" + realImage.getHeight());
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());


        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}