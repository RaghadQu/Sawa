package com.example.zodiac.sawa.RegisterPkg;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.Validation;

import java.util.Date;

/**
 * Created by zodiac on 07/16/2017.
 */

public class BirthDateFragment extends android.app.Fragment {

    Button Nextbtn;
    DatePicker birthdatePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.register_birthdate_fragment, container, false);
        Nextbtn = (Button) view.findViewById(R.id.nextBtn);
        birthdatePicker = (DatePicker) view.findViewById(R.id.BirthDatePicker);



        Nextbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                birthdatePicker.getYear();
                birthdatePicker.getMonth();
                birthdatePicker.getDayOfMonth();

                Date userBirthDate = new Date(birthdatePicker.getYear(), birthdatePicker.getYear(), birthdatePicker.getYear());
                ((RegisterActivity) getActivity()).setUserBirthDate(userBirthDate);
                android.app.Fragment f = new GenderFragment();
                ((RegisterActivity) getActivity()).replaceFragmnets(f);


            }
        });
        return view;
    }
}

