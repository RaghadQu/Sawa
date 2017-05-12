package com.example.zodiac.sawa.MyFriends;

import android.Manifest;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.ImageConverter.uploadImage;
import com.example.zodiac.sawa.MenuActiviries.aboutUserActivity;
import com.example.zodiac.sawa.MyAdapter;
import com.example.zodiac.sawa.Profile.FriendsClass;
import com.example.zodiac.sawa.Profile.NotFriendProfileClass;
import com.example.zodiac.sawa.Profile.PendingFriendsClass;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecyclerViewAdapters.SettingsAdapter;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.Authentication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFriendProfileActivity extends AppCompatActivity {
    Uri imageuri;
    ImageView img;
    Dialog imgClick;
    Dialog ViewImgDialog;
    TextView  viewPic;
    ImageView imageView; // View image in dialog
    Button friendStatus;
    private static final int SELECTED_PICTURE = 100;
    SettingsAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    int image1 = R.drawable.image1;
    int image2 = R.drawable.friends_icon;
    int image3 = R.drawable.friends_icon;
    int image4 = R.drawable.image1;
    Button editBio;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = {"Profile", "Friends", "Friend Requests", "Log out"};
    int[] images = {image1, image2, image3, image4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend_profile);

        final FreindsFunctions freindsFunctions=new FreindsFunctions();
        friendStatus=(Button)findViewById(R.id.friendStatus);
        friendStatus.setText(" ");

        /*
        * Get state in order to determine what to show
        * */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        GetFreinds getFreinds = retrofit.create(GetFreinds.class);
        Call<Authentication> call = getFreinds.getFriendshipState(1,41);
        call.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                Authentication authentication=response.body();
                Log.d("stateeee",""+authentication.getState());
                if(authentication.getState()==2){
                    NotFriendProfileClass notFriendProfile=new NotFriendProfileClass();
                    notFriendProfile.SetFriendButtn(friendStatus,mRecyclerView);
                    //friendStatus.setText("Add as freind");
                }else if(authentication.getState()==0){
                    //friendStatus.setText("Pending");
                    PendingFriendsClass pendingFriendsClass=new PendingFriendsClass();
                    pendingFriendsClass.SetFriendButtn(friendStatus);
                }else if(authentication.getState()==1){
                    FriendsClass friendsClass=new FriendsClass();
                    friendsClass.SetFriendButtn(friendStatus,mRecyclerView);
                    //friendStatus.setText("Freind");
                }


            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                Log.d("stateeee","fail nnnnnnn");

            }
        });
        //


       // freindsFunctions.getFreindShipState(1,2,friendStatus);


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
                viewPic = (TextView) imgClick.findViewById(R.id.ViewPic);



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
