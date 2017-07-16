package com.example.zodiac.sawa.MenuActiviries;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zodiac.sawa.AddPostActivity;
import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.GeneralFunctions;
import com.example.zodiac.sawa.ImageConverter.ImageConverter;
import com.example.zodiac.sawa.ImageConverter.uploadImage;
import com.example.zodiac.sawa.RecyclerViewAdapters.MyAdapter;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecyclerViewAdapters.SettingsAdapter;

public class MyProfileActivity extends AppCompatActivity {


    TextView friendsTxt , requestsTxt , newPostTxt;
    Uri imageuri;
    ImageView img;
    Dialog imgClick;
    Dialog ViewImgDialog;
    Dialog editMyBio;
    TextView changePic, viewPic, RemovePic, toolBarText;
    ImageView imageView; // View image in dialog
    private static final int SELECTED_PICTURE = 100;
    SettingsAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    int image1 = R.drawable.image1;
    int image2 = R.drawable.friends_icon;
    int image3 = R.drawable.friends_icon;
    int image4 = R.drawable.image1;
    TextView editBio;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = {"Profile", "Friends", "Friend Requests", "Log out"};
    int[] images = {image1, image2, image3, image4};

    private ProgressBar progressBar;
    public static ObjectAnimator anim;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        GeneralFunctions generalFunctions = new GeneralFunctions();
        boolean isOnline = generalFunctions.isOnline(getApplicationContext());
        friendsTxt= (TextView) findViewById(R.id.friendsTxt);
        requestsTxt= (TextView) findViewById(R.id.requestTxt);
        newPostTxt= (TextView) findViewById(R.id.newPostTxt);


        if (isOnline == false) {
            Toast.makeText(this, "no internet connection!",
                    Toast.LENGTH_LONG).show();
        } else {
            progressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
            anim.setDuration(2000);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();

            imgClick = new Dialog(this);
            imgClick.setContentView(R.layout.profile_picture_dialog);
            imgClick.getWindow().getAttributes().y = -130;
            imgClick.getWindow().getAttributes().x = 70;


            Log.d("Set", "s");
            ViewImgDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            ViewImgDialog.setContentView(R.layout.view_profilepic_dialog);

            editMyBio = new Dialog(this);
            editMyBio.setContentView(R.layout.edit_my_bio_dialog);
            editMyBio.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            imageView = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);

            img = (ImageView) findViewById(R.id.user_profile_photo);
            // imageView.setImageURI();
            final DBHandler dbHandler = new DBHandler(this);
            final uploadImage uploadImage = new uploadImage();
            String imageUrl = uploadImage.getUserImageFromDB(GeneralAppInfo.getUserID(), img, MyProfileActivity.this, 1, anim);


            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    imgClick.show();
                    changePic = (TextView) imgClick.findViewById(R.id.EditPic);
                    viewPic = (TextView) imgClick.findViewById(R.id.ViewPic);
                    RemovePic = (TextView) imgClick.findViewById(R.id.RemovePic);

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

                    RemovePic.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            imgClick.dismiss();
                            img.setImageResource(R.drawable.account);
                            //  imageView.setImageDrawable(img.getDrawable());
                            // ViewImgDialog.show();

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
            editBio = (TextView) findViewById(R.id.editBio);
            editBio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Intent i = new Intent(getApplicationContext(), aboutUserActivity.class);
                    //startActivity(i);

                    editMyBio.show();

                }
            });
        }
        toolBarText = (TextView) findViewById(R.id.toolBarText);
        toolBarText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;

                if (event.getX() <= (toolBarText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                    finish();
                    return true;
                }

                if (event.getX() >= 900) {
                    return true;
                }


                return false;
            }
        });



        friendsTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MyFriendsActivity.class);
                startActivity(i);
            }
        });

        requestsTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MyRequestsActivity.class);
                startActivity(i);
            }
        });

        newPostTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AddPostActivity.class);
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
                // dbHandler.updateUserImage(GeneralAppInfo.getUserID(), image);
                String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
                uploadImage uploadImage = new uploadImage();
                uploadImage.uploadImagetoDB(GeneralAppInfo.getUserID(), encodedImage);


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
