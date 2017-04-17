package com.example.zodiac.sawa.ProfileTabs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.zodiac.sawa.models.AboutUser;
import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.interfaces.AboutUserApi;
import com.example.zodiac.sawa.models.AddAboutUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zodiac on 04/03/2017.
 */

public class About extends Fragment {
    TextView bio;
    TextView status;
    TextView song;
    View view;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.about_tab, container, false);
        bio = (TextView) view.findViewById(R.id.Bio);

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

        status = (TextView) view.findViewById(R.id.status);
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

        song = (TextView) view.findViewById(R.id.Song);
        editSong = (Button) view.findViewById(R.id.SongEdit);
        editSong = (Button) view.findViewById(R.id.SongEdit);

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
        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateBio = new Dialog(getActivity());
        updateBio.setContentView(R.layout.bio_update_dialog);

        bioDialog = (TextView) updateBio.findViewById(R.id.Bio);
        cancelBio = (Button) updateBio.findViewById(R.id.Cancel);
        saveBio = (Button) updateBio.findViewById(R.id.Save);


        updateStatus = new Dialog(getActivity());
        updateStatus.setContentView(R.layout.status_update_dialog);

        statusDialog = (TextView) updateStatus.findViewById(R.id.Status);
        cancelStatus = (Button) updateStatus.findViewById(R.id.Cancel);
        saveStatus = (Button) updateStatus.findViewById(R.id.Save);

        updateSong = new Dialog(getActivity());
        updateSong.setContentView(R.layout.song_update_dialog);

        songDialog = (TextView) updateSong.findViewById(R.id.Song);
        cancelSong = (Button) updateSong.findViewById(R.id.Cancel);
        saveSong = (Button) updateSong.findViewById(R.id.Save);

        final DBHandler dbHandler = new DBHandler(getContext());
        AboutUser aboutUser = dbHandler.getAboutUser(1);
        if (aboutUser != null) {

            bio.setText(aboutUser.getUser_bio());
            bioDialog.setText(aboutUser.getUser_bio());

            status.setText(aboutUser.getUser_status());
            statusDialog.setText(aboutUser.getUser_status());

            song = (TextView) view.findViewById(R.id.Song);
            song.setText(aboutUser.getUser_song());
            songDialog.setText(aboutUser.getUser_song());

        } else {
            getUserFromDB();
        }


    }

    public void getUserFromDB() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserApi aboutUserApi = retrofit.create(AboutUserApi.class);
        Call<List<AboutUser>> aboutUserResponse = aboutUserApi.getAboutUser(2);
        aboutUserResponse.enqueue(new Callback<List<AboutUser>>() {
            @Override
            public void onResponse(Call<List<AboutUser>> call, Response<List<AboutUser>> response) {
                List<AboutUser> aboutUser;
                aboutUser = response.body();
                bio = (TextView) view.findViewById(R.id.Bio);
           //     bio.setText(aboutUser.get(0).getUser_bio());
                status = (TextView) view.findViewById(R.id.status);

//                status.setText(aboutUser.get(0).getUser_status());

                song = (TextView) view.findViewById(R.id.Song);
  //              song.setText(aboutUser.get(0).getUser_song());
    //            AboutUser aboutUser1 = new AboutUser(2, aboutUser.get(0).getUser_bio(), aboutUser.get(0).getUser_status(), aboutUser.get(0).getUser_song());
                DBHandler dbHandler = new DBHandler(getContext());
      //          dbHandler.addAboutUser(aboutUser1);
            }

            @Override
            public void onFailure(Call<List<AboutUser>> call, Throwable t) {

            }

        });

    }

    public void addNewAboutUserInDB(int id) {
        AboutUser aboutUser = new AboutUser(id, "", "", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserApi aboutUserApi = retrofit.create(AboutUserApi.class);
        Call<AddAboutUserResponse> call = aboutUserApi.addAboutUser(aboutUser);
        call.enqueue(new Callback<AddAboutUserResponse>() {
            @Override
            public void onResponse(Call<AddAboutUserResponse> call, Response<AddAboutUserResponse> response) {
                int state = response.body().getState();
                Log.d("add about user ", "added");
            }

            @Override
            public void onFailure(Call<AddAboutUserResponse> call, Throwable t) {

            }
        });

    }

    public void updateBio(View arg0) {
        updateBio.show();
    }

    public void updateAbout(final String bioText, final String statusText, final String songText) {
        AboutUser aboutUser = new AboutUser(1, bioText, statusText, songText);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserApi aboutUserApi = retrofit.create(AboutUserApi.class);
        Call<AddAboutUserResponse> call = aboutUserApi.editAvoutUser(aboutUser);
        call.enqueue(new Callback<AddAboutUserResponse>() {
            @Override
            public void onResponse(Call<AddAboutUserResponse> call, Response<AddAboutUserResponse> response) {
                bio.setText(bioText);
                status.setText(statusText);
                song.setText(songText);
                int state = response.body().getState();
                Log.d("add about user ", "added");
                DBHandler dbHandler = new DBHandler(getContext());
                dbHandler.updateAboutSqlite(bioText, statusText, songText);
            }

            @Override
            public void onFailure(Call<AddAboutUserResponse> call, Throwable t) {

            }
        });

    }


}        // Setting ViewPager for each Tabs



