package com.example.zodiac.sawa.MenuActiviries;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.interfaces.AboutUserApi;
import com.example.zodiac.sawa.models.AboutUserResponeModel;
import com.example.zodiac.sawa.models.GeneralStateResponeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class aboutUserActivity extends AppCompatActivity {
    TextView bio;
    TextView status;
    TextView song;
    Button editSong;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if (b != null)
            value = b.getInt("IsMe");
        bio = (TextView) findViewById(R.id.Bio);
        status = (TextView) findViewById(R.id.status);
        song = (TextView) findViewById(R.id.Song);


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

        final DBHandler dbHandler = new DBHandler(this);
        AboutUserResponeModel aboutUserResponeModel = dbHandler.getAboutUser(GeneralAppInfo.getUserID());
        if (aboutUserResponeModel != null) {

            bio.setText(aboutUserResponeModel.getUser_bio());
            bioDialog.setText(aboutUserResponeModel.getUser_bio());

            status.setText(aboutUserResponeModel.getUser_status());
            statusDialog.setText(aboutUserResponeModel.getUser_status());

            song = (TextView) findViewById(R.id.Song);
            song.setText(aboutUserResponeModel.getUser_song());
            songDialog.setText(aboutUserResponeModel.getUser_song());

        } else {
            getUserFromDB();
        }

        bio = (TextView) findViewById(R.id.Bio);
        //Check if he is the same user
        if (value == 1) {
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

            song = (TextView) findViewById(R.id.Song);
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
        }


    }

    public void updateBio(View arg0) {
        updateBio.show();
    }

    public void updateAbout(final String bioText, final String statusText, final String songText) {
        AboutUserResponeModel aboutUserResponeModel = new AboutUserResponeModel(GeneralAppInfo.getUserID(), bioText, statusText, songText);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserApi aboutUserApi = retrofit.create(AboutUserApi.class);
        Call<GeneralStateResponeModel> call = aboutUserApi.editAvoutUser(aboutUserResponeModel);
        call.enqueue(new Callback<GeneralStateResponeModel>() {
            @Override
            public void onResponse(Call<GeneralStateResponeModel> call, Response<GeneralStateResponeModel> response) {
                bio.setText(bioText);
                status.setText(statusText);
                song.setText(songText);
                int state = response.body().getState();
                Log.d("add about user ", "added");
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.updateAboutSqlite(bioText, statusText, songText);
            }

            @Override
            public void onFailure(Call<GeneralStateResponeModel> call, Throwable t) {

            }
        });

    }

    public void getUserFromDB() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserApi aboutUserApi = retrofit.create(AboutUserApi.class);
        Call<List<AboutUserResponeModel>> aboutUserResponse = aboutUserApi.getAboutUser(GeneralAppInfo.getUserID());
        aboutUserResponse.enqueue(new Callback<List<AboutUserResponeModel>>() {
            @Override
            public void onResponse(Call<List<AboutUserResponeModel>> call, Response<List<AboutUserResponeModel>> response) {
                List<AboutUserResponeModel> aboutUserResponeModel;
                aboutUserResponeModel = response.body();
                bio = (TextView) findViewById(R.id.Bio);
                bio.setText(aboutUserResponeModel.get(0).getUser_bio());
                status = (TextView) findViewById(R.id.status);
                status.setText(aboutUserResponeModel.get(0).getUser_status());
                song = (TextView) findViewById(R.id.Song);

              //  song.setText(aboutUserResponeModel.get(0).getUser_song());
                song.setText(aboutUserResponeModel.get(0).getUser_song());
                //       AboutUserResponeModel aboutUserResponeModel1 = new AboutUserResponeModel(1, aboutUserResponeModel.get(0).getUser_bio(), aboutUserResponeModel.get(0).getUser_status(), aboutUserResponeModel.get(0).getUser_song());

                //              song.setText(aboutUserResponeModel.get(0).getUser_song());
                //            AboutUserResponeModel aboutUserResponeModel1 = new AboutUserResponeModel(2, aboutUserResponeModel.get(0).getUser_bio(), aboutUserResponeModel.get(0).getUser_status(), aboutUserResponeModel.get(0).getUser_song());
                song.setText(aboutUserResponeModel.get(0).getUser_song());
                AboutUserResponeModel aboutUserResponeModel1 = new AboutUserResponeModel(GeneralAppInfo.getUserID(), aboutUserResponeModel.get(0).getUser_bio(), aboutUserResponeModel.get(0).getUser_status(), aboutUserResponeModel.get(0).getUser_song());
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.addAboutUser(aboutUserResponeModel1);
            }

            @Override
            public void onFailure(Call<List<AboutUserResponeModel>> call, Throwable t) {

            }

        });

    }
}

