package com.example.zodiac.sawa.MyFriends;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.ImageConverter.uploadImage;
import com.example.zodiac.sawa.MenuActiviries.aboutUserActivity;
import com.example.zodiac.sawa.MyAdapter;
import com.example.zodiac.sawa.MyRequests.MyRequestsActivity;
import com.example.zodiac.sawa.Profile.FriendsClass;
import com.example.zodiac.sawa.Profile.NotFriendProfileClass;
import com.example.zodiac.sawa.Profile.PendingFriendsClass;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecyclerViewAdapters.SettingsAdapter;
import com.example.zodiac.sawa.Register;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.Authentication;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFriendProfileActivity extends AppCompatActivity {
    ImageView img;
    Dialog ViewImgDialog;
    TextView user_profile_name;
    ImageView imageView; // View image in dialog
    Button friendStatus;
    private static final int SELECTED_PICTURE = 100;
    int image1 = R.drawable.image1;
    int image2 = R.drawable.friends_icon;
    int image3 = R.drawable.friends_icon;
    int image4 = R.drawable.image1;
    Button editBio;

    private ProgressBar progressBar;
    public static ObjectAnimator anim;

    private ProgressBar progressBar_button;
    public static ObjectAnimator anim_button;
    Authentication authentication;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = {"Profile", "Friends", "Friend Requests", "Log out"};
    int[] images = {image1, image2, image3, image4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend_profile);
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

        friendStatus = (Button) findViewById(R.id.friendStatus);
        friendStatus.setText(" ");


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
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        anim.setDuration(2000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();


        final int Id1 = Id;
        Log.d("IDD1", "" + Id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();


        GetFreinds getFreinds = retrofit.create(GetFreinds.class);
        Call<Authentication> call = getFreinds.getFriendshipState(GeneralAppInfo.getUserID(), Id);
        call.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                authentication = response.body();
                progressBar_button.setVisibility(View.GONE);
                FriendsClass friendsClass = new FriendsClass();

                Log.d("stateeee", "" + authentication.getState());
                if (authentication.getState() == 2) {
                    mRecyclerView.setVisibility(View.GONE);
                    GeneralAppInfo.friendMode = 2;
                    friendsClass.SetFriendButtn(friendStatus, mRecyclerView, MyFriendProfileActivity.this, Id1, getApplicationContext());


                } else if (authentication.getState() == 0) {
                    mRecyclerView.setVisibility(View.GONE);

                    GeneralAppInfo.friendMode = 0;
                    friendsClass.SetFriendButtn(friendStatus, mRecyclerView, MyFriendProfileActivity.this, Id1, getApplicationContext());


                } else if (authentication.getState() == 1) {
                    mRecyclerView.setVisibility(View.VISIBLE);

                    GeneralAppInfo.friendMode = 1;

                    friendsClass.SetFriendButtn(friendStatus, mRecyclerView, MyFriendProfileActivity.this, Id1, getApplicationContext());
                }

            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                Log.d("stateeee", "fail nnnnnnn");

            }
        });
        ViewImgDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ViewImgDialog.setContentView(R.layout.view_profilepic_dialog);
        imageView = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);

        img = (ImageView) findViewById(R.id.user_profile_photo);

        mImageUrl = GeneralAppInfo.IMAGE_URL + mImageUrl;
        Log.d("mImageUrl", mImageUrl);
        Picasso.with(getApplicationContext()).load(mImageUrl).into(img);


        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageView.setImageDrawable(img.getDrawable());
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


        editBio = (Button) findViewById(R.id.editBio);
        editBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), aboutUserActivity.class);
                Bundle b = new Bundle();
                b.putInt("IsMe", 0); //Your id
                i.putExtras(b);
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

}
