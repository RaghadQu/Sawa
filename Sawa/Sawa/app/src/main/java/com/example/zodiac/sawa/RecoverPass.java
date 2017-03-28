package com.example.zodiac.sawa;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zodiac.sawa.emailSender.BackgroungSender;

import java.util.UUID;

/**
 * Created by zodiac on 03/18/2017.
 */

public class RecoverPass extends Activity {

    Dialog dialog;
    EditText recievedEmail, codeText;
    Button btn;
    String  uniqueID;
    int counter=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recover_pass);

        recievedEmail=(EditText)findViewById(R.id.userEmail);

        dialog= new Dialog(RecoverPass.this);
        dialog.setContentView(R.layout.pass_dialog);
        dialog.setTitle("Reset Password");
        codeText = (EditText)dialog.findViewById(R.id.code);
        btn=(Button) dialog.findViewById(R.id.btn);

     }


    public void resetPass( View arg0){

        uniqueID = UUID.randomUUID().toString();
        uniqueID=uniqueID.split("-")[0];
        if ((recievedEmail.getText().toString().trim().equals(""))) {
            recievedEmail.setError("Email is required");}
        else {
            BackgroungSender BS = new BackgroungSender();
            BS.setRecievedEmail(recievedEmail.getText().toString());
            BS.setUniqueID(uniqueID);
            BS.execute("");
            dialog.show();
            codeText.setHint("Code");
            codeText.setText("");
            codeText.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_CLASS_TEXT);
            codeText.setError(null);
            btn.setText("Next");
        }

    }

    public void VerifyCode (View arg0)
    {
        String codeString=codeText.getText().toString();
            if (codeString.equals(uniqueID)) {   nextStep();
            }
            else
            {
                codeText.setError("Incorrect Code");
                counter++;
            }
        if(counter==3)
        {
            dialog.dismiss();
            Toast.makeText(RecoverPass.this, "Wrong Code. Please try again", Toast.LENGTH_LONG).show();
            recievedEmail.setText("");

        }
   }

    public void nextStep()
    {
        codeText.setText("");
        codeText.setHint("New Password");
        codeText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        btn.setText("Change password");
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String password = codeText.getText().toString();
                if (password.length() < 8) {
                    codeText.setError("Password must contain at least 8 characters");
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(RecoverPass.this, "Your password has been reset successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);

                }
            }
        });
    }

}