package com.example.zodiac.sawa.MenuActiviries;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.GeneralFunctions;
import com.example.zodiac.sawa.ImageConverter.ImageConverter;
import com.example.zodiac.sawa.ImageConverter.uploadImage;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.Spring.Models.AboutUserResponseModel;
import com.example.zodiac.sawa.Spring.Models.AboutUserRequestModel;
import com.example.zodiac.sawa.SpringApi.AboutUserInterface;
import com.example.zodiac.sawa.interfaces.AboutUserApi;
import com.example.zodiac.sawa.models.AboutUserResponeModelOld;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class aboutUserActivity extends AppCompatActivity {

    EditText bio;
    EditText status;
    EditText song;
    EditText userName;
    TextView saveEdit, editPicture, toolBarText;
    TextView changePic, viewPic, removePic;

    Dialog editPictureDialog;
    ImageView aboutPicture;
    Dialog updateBio;
    TextView bioDialog;
    Button cancelBio;
    Button saveBio;
    Dialog updateStatus;
    TextView statusDialog;
    Button cancelStatus;
    Button saveStatus;

    Dialog updateSong;
    TextView songDialog;
    Button cancelSong;
    Button saveSong;
    private static final int SELECTED_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if (b != null)
            value = b.getInt("IsMe");
        bio = (EditText) findViewById(R.id.Bio);
        status = (EditText) findViewById(R.id.status);
        song = (EditText) findViewById(R.id.Song);
        userName = (EditText) findViewById(R.id.userName);
        saveEdit = (TextView) findViewById(R.id.saveEdit);
        editPicture = (TextView) findViewById(R.id.editPicture);
        aboutPicture = (ImageView) findViewById(R.id.AboutPicture);

        updateBio = new Dialog(this);
        updateBio.setContentView(R.layout.bio_update_dialog);

        bioDialog = (TextView) updateBio.findViewById(R.id.Bio);
        cancelBio = (Button) updateBio.findViewById(R.id.Cancel);
        saveBio = (Button) updateBio.findViewById(R.id.Save);


        updateStatus = new Dialog(this);
        updateStatus.setContentView(R.layout.status_update_dialog);

        statusDialog = (TextView) updateStatus.findViewById(R.id.Status);
        cancelStatus = (Button) updateStatus.findViewById(R.id.Cancel);
        saveStatus = (Button) updateStatus.findViewById(R.id.Save);

        updateSong = new Dialog(this);
        updateSong.setContentView(R.layout.song_update_dialog);

        songDialog = (TextView) updateSong.findViewById(R.id.Song);
        cancelSong = (Button) updateSong.findViewById(R.id.Cancel);
        saveSong = (Button) updateSong.findViewById(R.id.Save);

        fillAbout();


        //Check if he is the same user
     /*   if (value == 1) {
            bio.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    updateBio.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    String bioText = bio.getText().toString();
                    bioDialog.setText(bioText);
                    updateBio.show();

                    saveBio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String statusText = statusDialog.getText().toString();
                            String bioText = bioDialog.getText().toString();
                            String songText = songDialog.getText().toString();

                            updateAbout(bioText, statusText, songText);
                            updateBio.dismiss();
                        }
                    });
                    cancelBio.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            updateBio.dismiss();
                        }
                    });
                }
            });

            status.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    updateStatus.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    String statusText = status.getText().toString();
                    statusDialog.setText(statusText);
                    updateStatus.show();

                    saveStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String statusText = statusDialog.getText().toString();
                            String bioText = bioDialog.getText().toString();
                            String songText = songDialog.getText().toString();

                            updateAbout(bioText, statusText, songText);
                            updateStatus.dismiss();
                        }
                    });
                    cancelStatus.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            updateStatus.dismiss();
                        }
                    });
                }
            });

            song = (EditText) findViewById(R.id.Song);
            editSong = (Button) findViewById(R.id.SongEdit);
            editSong = (Button) findViewById(R.id.SongEdit);

            editSong.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    updateSong.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                    String songText = song.getText().toString();
                    songDialog.setText(songText);
                    updateSong.show();

                    saveSong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String statusText = statusDialog.getText().toString();
                            String bioText = bioDialog.getText().toString();
                            String songText = songDialog.getText().toString();

                            updateAbout(bioText, statusText, songText);
                            updateSong.dismiss();
                        }
                    });
                    cancelSong.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            updateSong.dismiss();
                        }
                    });
                }
            });
*/

        editPictureDialog = new Dialog(this);
        editPictureDialog.setContentView(R.layout.profile_picture_dialog);
        editPictureDialog.getWindow().getAttributes().y = -210;
        editPictureDialog.getWindow().getAttributes().x = 85;


        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusText = status.getText().toString();
                String bioText = bio.getText().toString();
                String songText = song.getText().toString();
                updateAbout(bioText, statusText, songText);
                finish();
            }
        });

        editPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Here", " Done 2");
                editPictureDialog.show();
                changePic = (TextView) editPictureDialog.findViewById(R.id.EditPic);
                viewPic = (TextView) editPictureDialog.findViewById(R.id.ViewPic);
                viewPic.setVisibility(View.GONE);
                removePic = (TextView) editPictureDialog.findViewById(R.id.RemovePic);

                changePic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        editPictureDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, SELECTED_PICTURE);
                    }
                });

                removePic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        editPictureDialog.dismiss();
                        aboutPicture.setImageResource(R.drawable.account);
                    }
                });
            }
        });

        toolBarText = (TextView) findViewById(R.id.toolBarText);
        toolBarText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;

                if (event.getX() <= (toolBarText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECTED_PICTURE) {
            Uri imageuri = data.getData();
            try {
                MyProfileActivity.verifyStoragePermissions(this);
                GeneralFunctions generalFunctions = new GeneralFunctions();
                String path = generalFunctions.getRealPathFromURI(this, imageuri);
                Log.d("Path", path);
                int rotate = generalFunctions.getPhotoOrientation(path);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                // bitmap = MyProfileActivity.scaleDown(bitmap, 1000, true);
                bitmap = MyProfileActivity.RotateBitmap(bitmap, rotate);
                aboutPicture.setImageBitmap(bitmap);
                ImageConverter imageConverter = new ImageConverter();
                byte[] image = imageConverter.getBytes(bitmap);
                String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
                uploadImage uploadImage = new uploadImage();
                uploadImage.uploadImagetoDB(GeneralAppInfo.getUserID(), encodedImage);


            } catch (Exception e) {
                Toast toast = Toast.makeText(this, "Image is large", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void updateBio(View arg0) {
        updateBio.show();
    }

    public void fillAbout() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserInterface aboutUserApi = retrofit.create(AboutUserInterface.class);
        Call<AboutUserResponseModel> call = aboutUserApi.getAboutUser(GeneralAppInfo.getUserID());
        call.enqueue(new Callback<AboutUserResponseModel>() {
            @Override
            public void onResponse(Call<AboutUserResponseModel> call, Response<AboutUserResponseModel> response) {
                if (response != null) {
                    if (response.body() != null) {
                        bio.setText(response.body().getUserBio());
                        status.setText(response.body().getUserStatus());
                        song.setText(response.body().getUserSong());
                    }
                }
            }
            @Override
            public void onFailure(Call<AboutUserResponseModel> call, Throwable t) {
                Log.d("AboutUserFill","Failure "+ t.getMessage());
            }
        });

    }

    public static void updateAbout(final String bioText, final String statusText, final String songText) {
        AboutUserRequestModel aboutUserModel = new AboutUserRequestModel(GeneralAppInfo.getUserID(), bioText, statusText, songText);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserInterface aboutUserApi = retrofit.create(AboutUserInterface.class);

        Call<AboutUserResponseModel> call = aboutUserApi.addNewAboutUser(aboutUserModel);
        call.enqueue(new Callback<AboutUserResponseModel>() {
            @Override
            public void onResponse(Call<AboutUserResponseModel> call, Response<AboutUserResponseModel> response) {
                Log.d("AboutUserUpdate","Done successfully");
            }
            @Override
            public void onFailure(Call<AboutUserResponseModel> call, Throwable t) {
                Log.d("AboutUserUpdate","Failure "+ t.getMessage());
            }
        });

    }


}

