package com.example.zodiac.sawa.FriendProfile;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.GeneralFunctions;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecyclerViewAdapters.MyAdapter;
import com.example.zodiac.sawa.Spring.Models.AboutUserResponseModel;
import com.example.zodiac.sawa.SpringApi.AboutUserInterface;
import com.example.zodiac.sawa.SpringApi.FriendshipInterface;
import com.example.zodiac.sawa.models.AuthenticationResponeModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFriendProfileActivity extends AppCompatActivity {
    CircleImageView img;
    Dialog ViewImgDialog;
    Dialog AboutFriendDialog;
    TextView about_status, about_bio, about_song;
    TextView user_profile_name;
    TextView toolBarText;
    TextView aboutUsername;

    ImageView imageView; // View image in dialog
    Button friendStatus;
    private static final int SELECTED_PICTURE = 100;
    int image1 = R.drawable.image1;
    int image2 = R.drawable.friends_icon;
    int image3 = R.drawable.friends_icon;
    int image4 = R.drawable.image1;
    TextView editBio;
    ImageView aboutFriendIcon;


    private ProgressBar progressBar;
    public static ObjectAnimator anim;

    private ProgressBar progressBar_button;
    public static ObjectAnimator anim_button;
    AuthenticationResponeModel authentication;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = {"Profile", "Friends", "Friend Requests", "Log out"};
    int[] images = {image1, image2, image3, image4};
    ImageView coverPhoto;

    int Id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend_profile);
        toolBarText = (TextView) findViewById(R.id.ToolbarText);
        user_profile_name = (TextView) findViewById(R.id.user_profile_name);
        //get parameters
        Bundle b = getIntent().getExtras();
        int Id = -1; // or other values
        String mName = "";
        String mImageUrl = "";
        if (b != null) {
            Id = b.getInt("Id");
            Log.d("IDD", "" + Id);
            mName = b.getString("mName");
            mImageUrl = b.getString("mImageURL");
        }
        user_profile_name.setText(mName);
        toolBarText.setText(mName);

        friendStatus = (Button) findViewById(R.id.friendStatus);
        friendStatus.setText(" ");
        aboutFriendIcon = (ImageView) findViewById(R.id.aboutFriendIcon);
        coverPhoto = (ImageView) findViewById(R.id.coverPhoto);

        final Button confirmRequest = (Button) findViewById(R.id.ConfirmRequest);
        final Button deleteRequest = (Button) findViewById(R.id.deleteRequest);

        progressBar_button = (ProgressBar) findViewById(R.id.progressBar_button);
        progressBar_button.setProgress(0);
        progressBar_button.setMax(100);
        anim_button = ObjectAnimator.ofInt(progressBar_button, "progress", 0, 100);
        anim_button.setDuration(1000);
        anim_button.setInterpolator(new DecelerateInterpolator());
        anim_button.start();

        progressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        // progressBar.setVisibility(View.VISIBLE);
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        anim.setDuration(2000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();

        img = (CircleImageView) findViewById(R.id.user_profile_photo);
        String imageUrl = GeneralAppInfo.SPRING_URL + "/" + mImageUrl;
        Picasso.with(getApplicationContext()).load(imageUrl).into(img);

        Id1 = Id;
        Log.d("IDD1", "" + Id);
        GeneralFunctions generalFunctions = new GeneralFunctions();
        boolean isOnline = generalFunctions.isOnline(getApplicationContext());


        if (isOnline == false) {
            Toast.makeText(this, "no internet connection!",
                    Toast.LENGTH_LONG).show();
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GeneralAppInfo.SPRING_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            FriendshipInterface getFreindApi = retrofit.create(FriendshipInterface.class);
            Call<Integer> call = getFreindApi.getFriendShipState(GeneralAppInfo.getUserID(), Id);

            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Log.d("GetState", "get Friend State code is " + response.code());
                    Integer FriendshipState = response.body();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar_button.setVisibility(View.INVISIBLE);
                    FriendsClass friendsClass = new FriendsClass();

                    if (FriendshipState != null) {
                        Log.d("stateeee", "" + FriendshipState);
                        if (FriendshipState == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            GeneralAppInfo.friendMode = 0;
                            friendsClass.SetFriendButtn(friendStatus, mRecyclerView, MyFriendProfileActivity.this, Id1, getApplicationContext());

                        } else if (FriendshipState == 1) { // Friend
                            mRecyclerView.setVisibility(View.VISIBLE);
                            GeneralAppInfo.friendMode = 1;
                            friendsClass.SetFriendButtn(friendStatus, mRecyclerView, MyFriendProfileActivity.this, Id1, getApplicationContext());
                        } else if (FriendshipState == 3) { // Not a friend
                            mRecyclerView.setVisibility(View.GONE);
                            GeneralAppInfo.friendMode = 3;
                            friendsClass.SetFriendButtn(friendStatus, mRecyclerView, MyFriendProfileActivity.this, Id1, getApplicationContext());
                        } else if (FriendshipState == 2) { // user = friend_id 2, friend = friend_id 1  .. friend sends to user
                            mRecyclerView.setVisibility(View.GONE);
                            GeneralAppInfo.friendMode = 0;
                            friendsClass.setFriendRequestButton(friendStatus, confirmRequest, deleteRequest, Id1);
                            //  friendsClass.SetFriendButtn(friendStatus, mRecyclerView, MyFriendProfileActivity.this, Id1, getApplicationContext());
                        }
                        fillAbout(about_bio, about_status, about_song, Id1);


                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d("stateeee", "fail nnnnnnn");

                }
            });

            ViewImgDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            ViewImgDialog.setContentView(R.layout.view_profilepic_dialog);
            imageView = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);

            Log.d("Image Alpha", "  Here is  " + img.getImageAlpha());
            AboutFriendDialog = new Dialog(this);
            AboutFriendDialog.setContentView(R.layout.about_other_dialog);
            AboutFriendDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            about_bio = (TextView) AboutFriendDialog.findViewById(R.id.Bio);
            about_status = (TextView) AboutFriendDialog.findViewById(R.id.status);
            about_song = (TextView) AboutFriendDialog.findViewById(R.id.Song);
            aboutUsername = (TextView) AboutFriendDialog.findViewById(R.id.aboutUsername);
            aboutUsername.setText("About " + mName);
            editBio = (TextView) findViewById(R.id.editBio);


            /*    about_bio.setText(aboutUserResponeModel.getUser_bio());
                editBio.setText(aboutUserResponeModel.getUser_bio());
                about_status.setText(aboutUserResponeModel.getUser_status());
                about_song.setText(aboutUserResponeModel.getUser_song());

          */


            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    imageView.setImageDrawable(img.getDrawable());
                    ViewImgDialog.show();
                }
            });

            coverPhoto.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    imageView.setImageDrawable(coverPhoto.getDrawable());
                    ViewImgDialog.show();
                }
            });

            mRecyclerView = (RecyclerView) findViewById(R.id.Viewer);
            mRecyclerView.setVisibility(View.GONE);

            mRecyclerView.setNestedScrollingEnabled(false);

            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyAdapter(this, myDataset, images);
            mRecyclerView.setAdapter(mAdapter);


            aboutFriendIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //   Intent i = new Intent(getApplicationContext(), aboutUserActivity.class);
                    // Bundle b = new Bundle();
                    //b.putInt("IsMe", 0); //Your id
                    //i.putExtras(b);
                    //startActivity(i);
                    AboutFriendDialog.show();
                }
            });
        }

        toolBarText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("---", " Clicked on Text");

                final int DRAWABLE_LEFT = 0;

                if (event.getX() <= (toolBarText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                    Log.d("---", " Clicked on left");
                    finish();

                    return true;
                }

                return false;
            }
        });


    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {

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

    public void fillAbout(final TextView bio, final TextView status, final TextView song, final int ID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserInterface aboutUserApi = retrofit.create(AboutUserInterface.class);
        Call<AboutUserResponseModel> call = aboutUserApi.getAboutUser(ID);
        call.enqueue(new Callback<AboutUserResponseModel>() {
            @Override
            public void onResponse(Call<AboutUserResponseModel> call, Response<AboutUserResponseModel> response) {
                Log.d("AboutUserFill", "Success  " + response.code());

                if (response != null) {
                    if (response.body() != null) {
                        Log.d("AboutUserFill", "Success");

                        editBio.setText(response.body().getUserBio());
                        bio.setText(response.body().getUserBio());
                        status.setText(response.body().getUserStatus());
                        song.setText(response.body().getUserSong());
                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUserResponseModel> call, Throwable t) {
                Log.d("AboutUserFill", "Failure " + t.getMessage() + " " + ID);
            }
        });

    }

}
