package com.example.zodiac.sawa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class newPasswordFragment extends android.app.Fragment {

    Button btn;
    EditText newPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);
        newPassword=(EditText)view.findViewById(R.id.newPassword);
        btn =(Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String password = newPassword.getText().toString();
                if (password.length() < 8) {
                    newPassword.setError("Password less than 8 characters");
                } else {

                    Intent i = new Intent((RecoverPass)getActivity(), Home.class);
                    startActivity(i);

                }
            }
        });
        return view;
    }


}
