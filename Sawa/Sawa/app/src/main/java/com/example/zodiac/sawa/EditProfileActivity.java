package com.example.zodiac.sawa;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by exalt on 7/17/2017.
 */

public class EditProfileActivity extends Activity {

    TextView backEdit , saveEdit;
    EditText firstName, lastName, number;
    RadioButton maleBtn , femaleBtn;
    DatePicker birthDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        backEdit= (TextView) findViewById(R.id.backEdit);
        saveEdit= (TextView) findViewById(R.id.saveEdit);
        firstName= (EditText) findViewById(R.id.FirstName);
        lastName= (EditText) findViewById(R.id.LastName);
        number= (EditText) findViewById(R.id.userNumber);
        maleBtn = (RadioButton) findViewById(R.id.radioM);
        femaleBtn= (RadioButton) findViewById(R.id.radioF);
        birthDate = (DatePicker) findViewById(R.id.BirthDatePicker);





        backEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });



    }
}
