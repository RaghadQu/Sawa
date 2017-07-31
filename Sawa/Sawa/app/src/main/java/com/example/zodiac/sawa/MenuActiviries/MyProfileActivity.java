package com.example.zodiac.sawa.MenuActiviries;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zodiac.sawa.AddPostActivity;
import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.EditProfileActivity;
import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.GeneralFunctions;
import com.example.zodiac.sawa.ImageConverter.ImageConverter;
import com.example.zodiac.sawa.ImageConverter.uploadImage;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecyclerViewAdapters.MyAdapter;
import com.example.zodiac.sawa.Spring.Models.AboutUserRequestModel;
import com.example.zodiac.sawa.Spring.Models.AboutUserResponseModel;
import com.example.zodiac.sawa.Spring.Models.UserModel;
import com.example.zodiac.sawa.SpringApi.AboutUserInterface;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyProfileActivity extends AppCompatActivity {


    TextView friendsTxt, requestsTxt, newPostTxt;
    TextView profileBio;
    CircleImageView editProfile,editSong;
    Button saveAbout, saveSong;
    static UserModel userInfo;
    Uri imageuri;
    static ImageView img;
    static ImageView coverImage;
    EditText bioTxt, statusTxt, songTxt;
    Dialog imgClick;
    Dialog ViewImgDialog;
    Dialog editMyBio , editMySong;
    TextView changePic, viewPic, RemovePic, toolBarText;
    ImageView imageView; // View image in dialog
    private static final int SELECTED_PICTURE = 100;
    int image1 = R.drawable.image1;
    int image2 = R.drawable.friends_icon;
    int image3 = R.drawable.friends_icon;
    int image4 = R.drawable.image1;
    static TextView userName;
    TextView editBio;
    static Context context;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = {"Profile", "Friends", "Friend Requests", "Log out"};
    int[] images = {image1, image2, image3, image4};

    private ProgressBar progressBar;
    public static ObjectAnimator anim;

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
        fillAbout();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserInfo();
        setContentView(R.layout.activity_my_profile);
        context=this;
        GeneralFunctions generalFunctions = new GeneralFunctions();
        boolean isOnline = generalFunctions.isOnline(getApplicationContext());
        friendsTxt = (TextView) findViewById(R.id.friendsTxt);
        requestsTxt = (TextView) findViewById(R.id.requestTxt);
        newPostTxt = (TextView) findViewById(R.id.newPostTxt);
        editProfile = (CircleImageView) findViewById(R.id.editProfile);
        editSong = (CircleImageView) findViewById(R.id.editSong);
        coverImage = (ImageView) findViewById(R.id.coverImage);
        profileBio = (TextView) findViewById(R.id.profileBio);
        userName = (TextView) findViewById(R.id.user_profile_name);


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

            ViewImgDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            ViewImgDialog.setContentView(R.layout.view_profilepic_dialog);

            editMyBio = new Dialog(this);
            editMyBio.setContentView(R.layout.edit_my_bio_dialog);
            editMyBio.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            saveAbout = (Button) editMyBio.findViewById(R.id.saveAbout);
            bioTxt = (EditText) editMyBio.findViewById(R.id.bioTxt);
            statusTxt = (EditText) editMyBio.findViewById(R.id.statusTxt);
            fillAbout();

            editMySong = new Dialog(this);
            editMySong.setContentView(R.layout.edit_song_profile_dialog);
            editMySong.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            songTxt= (EditText)editMySong.findViewById(R.id.songTxt);
            saveSong= (Button) editMySong.findViewById(R.id.saveSong);

            imageView = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);

            img = (ImageView) findViewById(R.id.user_profile_photo);
            // imageView.setImageURI();
            final DBHandler dbHandler = new DBHandler(this);
            final uploadImage uploadImage = new uploadImage();
          //  String imageUrl = uploadImage.getUserImageFromDB(GeneralAppInfo.getUserID(), img, MyProfileActivity.this, 1, anim);
            mRecyclerView = (RecyclerView) findViewById(R.id.Viewer);
            mRecyclerView.setNestedScrollingEnabled(false);

            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyAdapter(this, myDataset, images);
            mRecyclerView.setAdapter(mAdapter);
            //set click listener for edit bio
            editBio = (TextView) findViewById(R.id.editBio);
            toolBarText = (TextView) findViewById(R.id.toolBarText);

            //Profile Picture
            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    imgClick.getWindow().getAttributes().y = -90;
                    imgClick.getWindow().getAttributes().x = 130;
                    imgClick.show();
                    changePic = (TextView) imgClick.findViewById(R.id.EditPic);
                    changePic.setText("Change Profile Picture");
                    viewPic = (TextView) imgClick.findViewById(R.id.ViewPic);
                    viewPic.setText("View Profile Picture");
                    RemovePic = (TextView) imgClick.findViewById(R.id.RemovePic);
                    RemovePic.setText("Remove Profile Picture");
                    changePic.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            imgClick.dismiss();
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 100);
                        }
                    });

                    viewPic.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            imgClick.dismiss();
                            imageView.setImageDrawable(img.getDrawable());
                            ImageView coverImageDialog = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);
                            coverImageDialog.setImageDrawable(img.getDrawable());
                            ViewImgDialog.show();

                        }
                    });

                    RemovePic.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            imgClick.dismiss();
                            img.setImageResource(R.drawable.default_profile);
                            //  imageView.setImageDrawable(img.getDrawable());
                            // ViewImgDialog.show();

                        }
                    });
                }
            });

            coverImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    imgClick.getWindow().getAttributes().y = -280;
                    imgClick.getWindow().getAttributes().x = 30;
                    imgClick.show();
                    changePic = (TextView) imgClick.findViewById(R.id.EditPic);
                    changePic.setText("Change Cover Picture");
                    viewPic = (TextView) imgClick.findViewById(R.id.ViewPic);
                    viewPic.setText("View Cover Picture");
                    RemovePic = (TextView) imgClick.findViewById(R.id.RemovePic);
                    RemovePic.setText("Remove Cover Picture");

                    changePic.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            imgClick.dismiss();
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 200);
                        }
                    });

                    viewPic.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            imgClick.dismiss();
                            coverImage.setImageDrawable(coverImage.getDrawable());
                            ImageView coverImageDialog = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);
                            coverImageDialog.setImageDrawable(coverImage.getDrawable());
                            ViewImgDialog.show();


                        }
                    });

                    RemovePic.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            imgClick.dismiss();
                            coverImage.setImageDrawable(null);

                            //  img.setImageResource(R.drawable.default_profile);
                            //  coverImage.setImageDrawable(img.getDrawable());
                            // ViewImgDialog.show();

                        }
                    });
                }
            });

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

            editProfile.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                    startActivity(i);
                }
            });
            editBio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editMyBio.show();

                }
            });

            saveAbout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String bioText, statusText,songText;
                    bioText = bioTxt.getText().toString();
                    songText= songTxt.getText().toString();
                    statusText = statusTxt.getText().toString();
                    updateAbout(bioText, statusText,songText );
                    editMyBio.dismiss();

                }
            });

            editSong.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    editMySong.show();

                }
            });

            saveSong.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String bioText, statusText,songText;
                    bioText = bioTxt.getText().toString();
                    songText= songTxt.getText().toString();
                    statusText = statusTxt.getText().toString();
                    updateAbout(bioText, statusText,songText );
                    editMySong.dismiss();

                }
            });

          /*  editMyBio.setOnKeyListener(new Dialog.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface arg0, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Log.d("Enter"," Flag is " + flag);
                        Log.d("TEXTS", bioBeforeUpdate + " " + bioTxt.getText().toString() + " " + statusBeforeUpdate + statusTxt.getText().toString());

                        if (flag==1 ||( bioTxt.getText().toString().equals(bioBeforeUpdate) && statusTxt.getText().toString().equals(statusBeforeUpdate))) {
                            editMyBio.dismiss();
                            flag=0;
                            return true;
                        } else {
                            Toast.makeText(MyProfileActivity.this, "Please save changes or press back again to discard.", Toast.LENGTH_SHORT).show();
                            flag=1;
                            return false ;
                        }
                    }
                    return true;
                }
            });*/
        }


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
        if (resultCode == RESULT_OK && (requestCode == 100 || requestCode == 200)) {
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
                if (requestCode == 100) {
                    img.setImageBitmap(bitmap);
                }
                if (requestCode == 200){
                    coverImage.setImageBitmap(bitmap);
                }
                    ImageConverter imageConverter = new ImageConverter();
                byte[] image = imageConverter.getBytes(bitmap);
                DBHandler dbHandler = new DBHandler(this);
                // dbHandler.updateUserImage(GeneralAppInfo.getUserID(), image);
                String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
                uploadImage uploadImage = new uploadImage();
                Log.d("XX","arrive");

                uploadImage.uploadImagetoDB(GeneralAppInfo.getUserID(), encodedImage,path,bitmap,requestCode
                );


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
                        profileBio.setText(response.body().getUserBio());
                        bioTxt.setText(response.body().getUserBio());
                        statusTxt.setText(response.body().getUserStatus());
                        songTxt.setText(response.body().getUserSong());

                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUserResponseModel> call, Throwable t) {
                Log.d("AboutUserFill", "Failure " + t.getMessage());
            }
        });

    }

    public void updateAbout(final String bioText, final String statusText, final String songText) {
        AboutUserRequestModel aboutUserModel = new AboutUserRequestModel(GeneralAppInfo.getUserID(), bioText, statusText, songText);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserInterface aboutUserApi = retrofit.create(AboutUserInterface.class);

        Call<AboutUserResponseModel> call = aboutUserApi.addNewAboutUser(aboutUserModel);
        call.enqueue(new Callback<AboutUserResponseModel>() {
            @Override
            public void onResponse(Call<AboutUserResponseModel> call, Response<AboutUserResponseModel> response) {
                Log.d("AboutUserUpdate", "Done successfully");
                fillAbout();
            }

            @Override
            public void onFailure(Call<AboutUserResponseModel> call, Throwable t) {
                Log.d("AboutUserUpdate", "Failure " + t.getMessage());
            }
        });

    }

    public static void getUserInfo() {
        Log.d("InfoUser", " Enter here ");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserInterface service = retrofit.create(AboutUserInterface.class);
        Log.d("InfoUser", " Enter before call ");

        final Call<UserModel> userModelCall = service.getUserInfo(GeneralAppInfo.getUserID());
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                Log.d("InfoUser", " " + response.code());
                int statusCode = response.code();
                if (statusCode == 200) {
                    MyProfileActivity.userInfo = response.body();
                    userName.setText((userInfo.getFirst_name()+ " "+userInfo.getLast_name()));
                    String imageUrl = GeneralAppInfo.SPRING_URL + "/"+ userInfo.getImage();
                    Log.d("InfoUser", " " + imageUrl);

                      Picasso.with(context).load(imageUrl).into(img);
                    String coverUrl = GeneralAppInfo.SPRING_URL+"/"+userInfo.getCover_image();
                    Picasso.with(context).load(coverUrl).into(coverImage);


                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("----", " Error " + t.getMessage());
            }
        });

    }


}
