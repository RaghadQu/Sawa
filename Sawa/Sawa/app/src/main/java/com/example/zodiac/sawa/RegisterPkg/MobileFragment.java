package com.example.zodiac.sawa.RegisterPkg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecoverPassword.CheckCodeFragment;
import com.example.zodiac.sawa.Register;
import com.example.zodiac.sawa.Validation;

/**
 * Created by zodiac on 07/10/2017.
 */

public class MobileFragment extends android.app.Fragment  {
    EditText userMobile;
    Button Nextbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_mobile_fragment, container, false);
        userMobile=(EditText)view.findViewById(R.id.userMobile);
        Nextbtn=(Button)view.findViewById(R.id.nextBtn);

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
                        android.app.Fragment f = new PasswordFragment();
                        ((RegisterActivity) getActivity()).replaceFragmnets(f);
                    }

                }
            }
        });
        return view;
    }
}
