package com.example.zodiac.sawa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.ImageConverter.ImageConverter;
import com.example.zodiac.sawa.ImageConverter.uploadImage;
import com.example.zodiac.sawa.MenuActiviries.MyFriendsActivity;
import com.example.zodiac.sawa.MenuActiviries.MyProfileActivity;
import com.example.zodiac.sawa.RecyclerViewAdapters.AddPostImagesAdapter;
import com.example.zodiac.sawa.interfaces.AddPostApi;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.AddNewPostModel;
import com.example.zodiac.sawa.models.GeneralStateResponeModel;
import com.example.zodiac.sawa.models.getFriendsRequest;
import com.example.zodiac.sawa.models.getFriendsResponse;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zodiac on 06/07/2017.
 */

public class AddPostActivity extends Activity {

    private static final int SELECTED_PICTURE = 100;


    Button Cancelbtn, PostBtn;
    CircleButton anonymousBtn;
    EditText PostText;
    TextView AddImage;
    ImageView PostImage;
    static int ReceiverID;
    static public CircleImageView senderImage, receiverImage;
    static String postImage;
    FastScrollRecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    public static ArrayList<MyFriendsActivity.friend> FriendPostList = new ArrayList<>();
    GetFreinds service;
    List<getFriendsResponse> FreindsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_activity);
        anonymousBtn = (CircleButton) findViewById(R.id.anonymous);
        Cancelbtn = (Button) findViewById(R.id.CancelBtn);
        PostBtn = (Button) findViewById(R.id.PostBtn);
        senderImage = (CircleImageView) findViewById(R.id.senderImage);
        receiverImage = (CircleImageView) findViewById(R.id.receiverImage);
        PostText = (EditText) findViewById(R.id.PostText);
        PostImage = (ImageView) findViewById(R.id.PostImage);
        AddImage = (TextView) findViewById(R.id.AddImage);

        uploadImage uploadImage = new uploadImage();
        uploadImage.getUserImageFromDB(GeneralAppInfo.getUserID(), senderImage, AddPostActivity.this, 0, null);

        adapter = new AddPostImagesAdapter(this, FriendPostList);
        recyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        final int[] flag = {0};
        anonymousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag[0] == 0) {
                    flag[0] = 1;
                    anonymousBtn.setColor(getResources().getColor(R.color.green));
                } else {
                    flag[0] = 0;
                    anonymousBtn.setColor(getResources().getColor(android.R.color.darker_gray));
                }
            }
        });

        PostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewPost(flag[0]);
            }
        });

        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeTabbedActivity.class);
                startActivity(i);
            }
        });

        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECTED_PICTURE);
                //    PostImage.setImageResource(R.drawable.image2);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(GetFreinds.class);

        final getFriendsRequest request = new getFriendsRequest();
        request.setId(GeneralAppInfo.getUserID());
        final Call<List<getFriendsResponse>> FriendsResponse = service.getState(request.getId(), 1);
        Log.d("AddPostActivity", " Add post after request");
        FriendsResponse.enqueue(new Callback<List<getFriendsResponse>>() {
            @Override
            public void onResponse(Call<List<getFriendsResponse>> call, Response<List<getFriendsResponse>> response) {
                Log.d("AddPostActivity", " Add post after request with code " + response.code());
                FreindsList = response.body();
                Log.d("AddPostActivity", " Add post after request with size " + FreindsList.size());

                FriendPostList.clear();
                for (int i = 0; i < FreindsList.size(); i++) {
                    FriendPostList.add(new MyFriendsActivity.friend(FreindsList.get(i).getId(), FreindsList.get(i).getUser_image(),
                            FreindsList.get(i).getFirstName() + " " + FreindsList.get(i).getLast_name()));
                    recyclerView.setAdapter(new AddPostImagesAdapter(AddPostActivity.this, FriendPostList));
                }
            }

            @Override
            public void onFailure(Call<List<getFriendsResponse>> call, Throwable t) {
                Log.d("fail to get friends ", "Failure to Get friends in AddPostActivity");

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECTED_PICTURE) {
            Uri imageuri = data.getData();
            try {
                setPostImage(imageuri);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);

                int newWidth = (int) (bitmap.getWidth());
                int newHeight = (int) (bitmap.getHeight());
                if (newHeight >= 500) {
                    if (newWidth > newHeight) {
                        double scale = 920.0 / newWidth;
                        newWidth = (int) (newWidth * scale);
                        newHeight = (int) (newHeight * scale);
                    } else {
                        double scale = 950.0 / newHeight;
                        newWidth = (int) (newWidth * scale);
                        newHeight = (int) (newHeight * scale);
                    }
                }
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                PostImage.setImageBitmap(resized);

            } catch (IOException e) {
                e.printStackTrace();
            }
            // PostImage.setImageURI(imageuri);

        }
    }

    public void AddNewPost(int is_anon) {
        AddPostApi Postservice;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Postservice = retrofit.create(AddPostApi.class);


        final AddNewPostModel request = new AddNewPostModel();
        request.setImage(getPostImage());
        request.setIs_Anon(is_anon);
        request.setLink(" ");
        request.setTo_user_id(getReceiverID());
        request.setUser_id(GeneralAppInfo.getUserID());

        String PostBody = PostText.getText().toString();
        if (getReceiverID() == 0) {
            Toast.makeText(AddPostActivity.this, "Please select a friend.",
                    Toast.LENGTH_SHORT).show();
        } else if (!PostBody.trim().matches("")) {
            request.setText(PostBody.trim());
            final Call<GeneralStateResponeModel> PostRespone = Postservice.addPost(request);
            Log.d("AddPostActivity", " Add post after request");
            PostRespone.enqueue(new Callback<GeneralStateResponeModel>() {
                @Override
                public void onResponse(Call<GeneralStateResponeModel> call, Response<GeneralStateResponeModel> response) {
                    Log.d("AddPost", " Add Post done with code " + response.code() + " " + response.body().getState());
                    Intent i = new Intent(getApplicationContext(), HomeTabbedActivity.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<GeneralStateResponeModel> call, Throwable t) {
                    Log.d("fail to get friends ", "Failure to Get friends in AddPostActivity  ... " + t.getMessage());
                    Toast.makeText(AddPostActivity.this, "Oops! Something went wrong, please try again.",
                            Toast.LENGTH_SHORT).show();
                }

            });

        }


    }

    public static String getPostImage() {
        return postImage;
    }

    public void setPostImage(Uri imageuri) throws IOException {
        GeneralFunctions generalFunctions = new GeneralFunctions();
        String path = generalFunctions.getRealPathFromURI(this, imageuri);
        int rotate = generalFunctions.getPhotoOrientation(path);
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
        bitmap = MyProfileActivity.scaleDown(bitmap, 1000, true);
        bitmap = MyProfileActivity.RotateBitmap(bitmap, rotate);
        ImageConverter imageConverter = new ImageConverter();
        byte[] image = imageConverter.getBytes(bitmap);
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.updateUserImage(GeneralAppInfo.getUserID(), image);
        String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
        postImage = encodedImage;

    }

    public static void setRecieverID(int ID) {
        ReceiverID = ID;
    }

    public static int getReceiverID() {
        return ReceiverID;
    }

    public static void setRecieverImage(String image, Context context) {
        String imageURL = GeneralAppInfo.IMAGE_URL + image;
        Picasso.with(context).load(imageURL).into(receiverImage);
        Log.d("AdapterImage", " reciever image is : " + image);

    }

}
