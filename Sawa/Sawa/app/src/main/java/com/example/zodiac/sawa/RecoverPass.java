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
import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.example.zodiac.sawa.emailSender.BackgroungSender;

import java.util.UUID;

/**
 * Created by zodiac on 03/18/2017.
 */

public class RecoverPass extends Activity {

    Dialog dialog;
    EditText recievedEmail, codeText;
    Button btn;
    String uniqueID;
    int counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recover_pass);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.your_placeholder, new SendEmailFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
        /*recievedEmail = (EditText) findViewById(R.id.userEmail);

        dialog = new Dialog(RecoverPass.this);
        dialog.setContentView(R.layout.pass_dialog);
        dialog.setTitle("Reset Password");
        codeText = (EditText) dialog.findViewById(R.id.code);
        btn = (Button) dialog.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

<<<<<<< HEAD
     }
=======
            @Override
            public void onClick(View view) {
                String codeString = codeText.getText().toString();
                if (codeString.equals(uniqueID)) {
                    nextStep();
                } else {
                    codeText.setError("Incorrect Code");
                    counter++;
                }
                if (counter == 3) {
                    dialog.dismiss();
                    Toast.makeText(RecoverPass.this, "Wrong Code. Please try again", Toast.LENGTH_LONG).show();
                    recievedEmail.setText("");
>>>>>>> c42b4d03850a5173912929a2c76e570b5009c9df

                }

            }
        });
        */

    }





    public void nextStep() {
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
                } else {
                    dialog.dismiss();
                    Toast.makeText(RecoverPass.this, "Your password has been reset successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);

                }
            }
        });
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;

    }

    public String getUniqueID() {
        return this.uniqueID;
    }
  public void replaceFragmnets(android.app.Fragment f){
      FragmentManager fm=getFragmentManager();

      FragmentTransaction ft=fm.beginTransaction();
      ft.replace(R.id.your_placeholder,f);
      ft.addToBackStack(null);

// Commit the transaction
      ft.commit();
  }
}