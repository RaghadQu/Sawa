package com.example.zodiac.sawa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zodiac.sawa.DB.AboutUser;
import com.example.zodiac.sawa.DB.DBHandler;

/**
 * Created by zodiac on 04/08/2017.
 */

public class EditAbout extends Activity {

    EditText bio;
    EditText status;
    EditText song;
    Button cancel;
    Button save;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_about);

        bio= (EditText) findViewById(R.id.Bio);
        status = (EditText)findViewById(R.id.status);
        song= (EditText) findViewById(R.id.Song);

        cancel= (Button) findViewById(R.id.Cancel);
        save= (Button) findViewById(R.id.Save);


        final DBHandler dbHandler = new DBHandler(getApplicationContext());
        AboutUser aboutUser = dbHandler.getAboutUser(2);
        if (aboutUser != null) {
        bio.setText(aboutUser.getUser_bio());
            status.setText(aboutUser.getUser_status());
            song.setText(aboutUser.getUser_song());

        }

        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
               finish();
            }
        });
    }
}