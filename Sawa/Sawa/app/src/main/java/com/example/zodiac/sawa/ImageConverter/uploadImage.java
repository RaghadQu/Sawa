package com.example.zodiac.sawa.ImageConverter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.interfaces.UserImageApi;
import com.example.zodiac.sawa.models.AuthenticationResponeModel;
import com.example.zodiac.sawa.models.UserImage;
import com.example.zodiac.sawa.models.userImageFromDb;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rabee on 4/13/2017.
 */

public class uploadImage {
    String imageUrl;
    Bitmap encodedByte;

    public uploadImage() {

    }

    public void uploadImagetoDB(int user_id, String encodedImage) {
        UserImage userImage = new UserImage(user_id, encodedImage);
        Log.d("encoded image", encodedImage);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        UserImageApi userImageApi = retrofit.create(UserImageApi.class);
        Call<AuthenticationResponeModel> userImageResponse = userImageApi.saveImageUser(userImage);
        userImageResponse.enqueue(new Callback<AuthenticationResponeModel>() {
            @Override
            public void onResponse(Call<AuthenticationResponeModel> call, Response<AuthenticationResponeModel> response) {
                // int state = response.body().getState();
                //Log.d("Image is uploaded" + state, "" + state);
            }

            @Override
            public void onFailure(Call<AuthenticationResponeModel> call, Throwable t) {

            }
        });
    }


    public String getUserImageFromDB(int user_id, final ImageView img, final Context context, final int isAnim, final ObjectAnimator anim) {
        Log.d("Arrive to ge fro Db", "ss");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        UserImageApi userImageApi = retrofit.create(UserImageApi.class);
        Call<List<userImageFromDb>> userImageResponse = userImageApi.getUserImageFromDb(user_id);
        userImageResponse.enqueue(new Callback<List<userImageFromDb>>() {
            @Override
            public void onResponse(Call<List<userImageFromDb>> call, Response<List<userImageFromDb>> response) {
                List<userImageFromDb> userImageFromDbs;
                userImageFromDbs = response.body();
//     here  //
        imageUrl = userImageFromDbs.get(0).getUser_image();
                Log.d("Arrive to ge fro Db11", "s" + imageUrl);
                imageUrl = GeneralAppInfo.IMAGE_URL + imageUrl;
                Log.d("imageYtl", imageUrl);


                if (isAnim == 1) {

                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //here animation finished
                            Picasso.with(context).load(imageUrl).into(img);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });


                } else {
                    Picasso.with(context).load(imageUrl).into(img);
                    Log.d("Image", " image loaded");
                }
            }

            @Override
            public void onFailure(Call<List<userImageFromDb>> call, Throwable t) {
                Log.d("fail to get fro Db", "ss");

            }
        });
        return imageUrl;

    }
}
