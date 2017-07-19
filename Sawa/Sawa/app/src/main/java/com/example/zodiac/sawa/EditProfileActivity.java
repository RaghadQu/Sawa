package com.example.zodiac.sawa;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.zodiac.sawa.Spring.Models.UserModel;
import com.example.zodiac.sawa.SpringApi.AboutUserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by exalt on 7/17/2017.
 */

public class EditProfileActivity extends Activity {

    TextView backEdit, saveEdit;
    EditText firstName, lastName, number;
    RadioButton maleBtn, femaleBtn;
    DatePicker birthDate;
    AboutUserInterface service;
    UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        backEdit = (TextView) findViewById(R.id.backEdit);
        saveEdit = (TextView) findViewById(R.id.saveEdit);
        firstName = (EditText) findViewById(R.id.FirstName);
        lastName = (EditText) findViewById(R.id.LastName);
        number = (EditText) findViewById(R.id.userNumber);
        maleBtn = (RadioButton) findViewById(R.id.radioM);
        femaleBtn = (RadioButton) findViewById(R.id.radioF);
        birthDate = (DatePicker) findViewById(R.id.BirthDatePicker);

        FillUserInfo();


        backEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void FillUserInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(AboutUserInterface.class);


        final Call<UserModel> userModelCall = service.getUserInfo(GeneralAppInfo.getUserID());
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {


                int statusCode = response.code();
                if (statusCode == 200) {
                    userModel = response.body();
                    firstName.setText(userModel.getFirst_name());
                    lastName.setText(userModel.getLast_name());
                    number.setText(String.valueOf(userModel.getMobile()));
                    femaleBtn.setChecked(true);
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("----", " Error " + t.getMessage());
            }
        });

    }
}
