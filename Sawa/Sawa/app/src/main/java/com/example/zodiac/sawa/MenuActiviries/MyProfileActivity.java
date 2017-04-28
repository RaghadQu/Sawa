package com.example.zodiac.sawa.MenuActiviries;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.GeneralFunctions;
import com.example.zodiac.sawa.ImageConverter.ImageConverter;
import com.example.zodiac.sawa.ImageConverter.uploadImage;
import com.example.zodiac.sawa.MyAdapter;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecyclerViewAdapters.SettingsAdapter;

public class MyProfileActivity extends AppCompatActivity {
    Uri imageuri;
    ImageView img;
    Dialog imgClick;
    Dialog ViewImgDialog;
    TextView changePic, viewPic;
    ImageView imageView; // View image in dialog
    private static final int SELECTED_PICTURE = 100;
    SettingsAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    int image1 = R.drawable.image1;
    int image2 = R.drawable.image2;
    int image3 = R.drawable.image1;
    ImageButton editBio;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = {"Profile", "Friends", "Friend Requests", "Log out", "Profile", "Friends", "Friend Requests", "Log out", "Profile", "Friends", "Friend Requests", "Log out", "Profile", "Friends", "Friend Requests", "Log out"};
    int[] images = {image1, image2, image3, image1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        imgClick = new Dialog(this);
        imgClick.setContentView(R.layout.profile_picture_dialog);
        imgClick.getWindow().getAttributes().y = -130;
        imgClick.getWindow().getAttributes().x = 70;


        Log.d("Set", "s");
        ViewImgDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ViewImgDialog.setContentView(R.layout.view_profilepic_dialog);
        imageView = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);

        img = (ImageView) findViewById(R.id.user_profile_photo);
        // imageView.setImageURI();
        DBHandler dbHandler = new DBHandler(this);
        uploadImage uploadImage = new uploadImage();
        // String imageUrl=uploadImage.getUserImageFromDB(1,img,getContext());


        //  Bitmap bitmap = dbHandler.getUserImage(1);
        String imageUrl = uploadImage.getUserImageFromDB(1, img, this);


        Bitmap bitmap = dbHandler.getUserImage(1);
        // bitmap = RotateBitmap(bitmap, -90);
        img.setImageBitmap(bitmap);


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
        mRecyclerView = (RecyclerView) findViewById(R.id.Viewer);
        mRecyclerView.setNestedScrollingEnabled(false);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(this, myDataset, images);
        mRecyclerView.setAdapter(mAdapter);
        //set click listener for edit bio
        editBio=(ImageButton)findViewById(R.id.editBio);
        editBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), aboutUserActivity.class);
                startActivity(i);

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
                verifyStoragePermissions(this);
                GeneralFunctions generalFunctions = new GeneralFunctions();
                String path = generalFunctions.getRealPathFromURI(this, imageuri);
                Log.d("Path", path);
                int rotate = generalFunctions.getPhotoOrientation(path);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                bitmap = scaleDown(bitmap, 1000, true);
                bitmap = RotateBitmap(bitmap, rotate);
                img.setImageBitmap(bitmap);

                ImageConverter imageConverter = new ImageConverter();
                byte[] image = imageConverter.getBytes(bitmap);
                DBHandler dbHandler = new DBHandler(this);
                dbHandler.updateUserImage(1, image);
                String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
                uploadImage uploadImage = new uploadImage();
                uploadImage.uploadImagetoDB(1, encodedImage);

            } catch (Exception e) {
                Toast toast = Toast.makeText(this, "Image is large", Toast.LENGTH_SHORT);
                toast.show();
            }
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