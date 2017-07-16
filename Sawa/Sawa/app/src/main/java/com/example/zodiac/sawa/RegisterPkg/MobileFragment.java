package com.example.zodiac.sawa.RegisterPkg;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecoverPassword.CheckCodeFragment;
import com.example.zodiac.sawa.Register;
import com.example.zodiac.sawa.Validation;

/**
 * Created by zodiac on 07/10/2017.
 */

public class MobileFragment extends android.app.Fragment {
    EditText userMobile;
    Button Nextbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_mobile_fragment, container, false);
      //  InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imgr.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);

        InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(userMobile, InputMethodManager.SHOW_IMPLICIT);


        userMobile = (EditText) view.findViewById(R.id.userMobile);
        Nextbtn = (Button) view.findViewById(R.id.nextBtn);

        Nextbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userEmail = userMobile.getText().toString();
                if (userMobile.getText().toString().trim().equals("")) {
                    userMobile.setError("Mobile number is required");
                } else {
                    if (!Validation.isValidMobile(userMobile.getText().toString())) {
                        userMobile.setError("Mobile number is not valid");
                    } else {
                        ((RegisterActivity) getActivity()).setMobileNumber(Integer.parseInt(userMobile.getText().toString()));
                        android.app.Fragment f = new BirthDateFragment();
                        ((RegisterActivity) getActivity()).replaceFragmnets(f);
                    }

                }
            }
        });
        return view;
    }
}
