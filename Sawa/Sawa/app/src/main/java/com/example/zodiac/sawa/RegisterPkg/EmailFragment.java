package com.example.zodiac.sawa.RegisterPkg;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.RecoverPassword.RecoverPass;
import com.example.zodiac.sawa.RecoverPassword.newPasswordFragment;
import com.example.zodiac.sawa.Validation;

/**
 * Created by zodiac on 07/10/2017.
 */

public class EmailFragment extends android.app.Fragment {

    Button nextbtn;
    EditText email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_email_fragment, container, false);
        email=(EditText)view.findViewById(R.id.userEmail);
        email.setFocusable(true);
        nextbtn=(Button)view.findViewById(R.id.nextBtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                if ((userEmail.equals(""))) {
                    email.setError("Email is required");
                } else if (!(Validation.isEmailValid(email.getText().toString()))) {
                    email.setError("Email is not valid");
                }

                else
                {
                    ((RegisterActivity) getActivity()).setUserEmail(email.getText().toString());
                    android.app.Fragment f=new MobileFragment();
                    ((RegisterActivity)getActivity()).replaceFragmnets(f);
                }


            }
        });
        return view;
    }

}
