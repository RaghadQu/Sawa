package com.example.zodiac.sawa;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zodiac.sawa.emailSender.BackgroungSender;

import java.util.UUID;


public class SendEmailFragment extends android.app.Fragment {

    Dialog dialog;
    EditText recievedEmail, codeText;
    Button btn;
    String uniqueID;
    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);
        recievedEmail = (EditText) view.findViewById(R.id.userEmail);
        uniqueID = UUID.randomUUID().toString();
        uniqueID = uniqueID.split("-")[0];
        btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (recievedEmail.getText().toString().equals("")) {
                    recievedEmail.setError("Email is required");
                } else {
                    BackgroungSender BS = new BackgroungSender();
                    BS.setRecievedEmail(recievedEmail.getText().toString());
                    BS.setUniqueID(uniqueID);
                    BS.execute("");
                    ((RecoverPass) getActivity()).setUniqueID(uniqueID);
                    android.app.Fragment f=new CheckCodeFragment();
                    ((RecoverPass) getActivity()).replaceFragmnets(f);

                }
            }
        });
        return view;

    }




}
