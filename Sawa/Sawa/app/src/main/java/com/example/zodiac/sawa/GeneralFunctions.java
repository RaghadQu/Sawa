package com.example.zodiac.sawa;

import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;

import com.example.zodiac.sawa.interfaces.TokenApi;
import com.example.zodiac.sawa.models.Authentication;
import com.example.zodiac.sawa.models.UserIdWithDeviceIdModel;
import com.example.zodiac.sawa.models.UserTokenModel;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rabee on 4/17/2017.
 */

public class GeneralFunctions {
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int getPhotoOrientation(String imagePath) {
        int rotate = 0;
        try {


            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public void storeUserIdWithDeviceId(int user_id, String deviceId) {

        UserIdWithDeviceIdModel userIdWithDeviceIdModel = new UserIdWithDeviceIdModel(deviceId, user_id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        TokenApi tokenApi = retrofit.create(TokenApi.class);
        Call<Authentication> call = tokenApi.storeUserIdWithDeviceId(userIdWithDeviceIdModel);
        call.enqueue(new Callback<Authentication>() {

            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {

            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {

            }
        });
    }

}
