package com.eventor.smartshopapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    Button saveBtn;
    EditText sName, soName, pass, address, phn;
    String NAME, OWNER_NAME, PASSWORD, ADDRESS, PHONENO;
    TextView loginlink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        saveBtn = (Button) findViewById(R.id.save);
        sName = (EditText) findViewById(R.id.editText1);
        soName = (EditText) findViewById(R.id.editText2);
        pass = (EditText) findViewById(R.id.editText5);
        address = (EditText) findViewById(R.id.editText3);
        phn = (EditText) findViewById(R.id.editText4);
        loginlink=(TextView)findViewById(R.id.slogin);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDeatails();
            }
        });
        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplication(), Login.class);
                startActivity(i);
            }
        });
    }
    public void saveDeatails() {
        NAME = sName.getText().toString().trim();
        OWNER_NAME = soName.getText().toString().trim();
        PASSWORD = pass.getText().toString().trim();
        ADDRESS = address.getText().toString().trim();
        PHONENO = phn.getText().toString().trim();
        if(!NAME.isEmpty()&&!OWNER_NAME.isEmpty()&&!PASSWORD.isEmpty()&&!ADDRESS.isEmpty()&&!PHONENO.isEmpty()) {
            SharedPreferences sp = this.getApplicationContext().getSharedPreferences("appData", 0);
            SharedPreferences.Editor editor;
            editor = sp.edit();
            editor.putString("logout"+"","no");
            editor.putString("SavedShopName", NAME);
            editor.putString("SavedShopOwnerName", OWNER_NAME);
            editor.putString("SavedPassword", PASSWORD);
            editor.putString("SavedAddress", ADDRESS);
            editor.putString("SavedPhoneNo", PHONENO);
            editor.commit();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else
        {   if(NAME.equals(""))
         {
           sName.setHint("required");
             sName.setHintTextColor(Color.RED);
         }
            if(OWNER_NAME.equals(""))
            {
                soName.setHint("required");
                soName.setHintTextColor(Color.RED);
            }
            if(PASSWORD.equals(""))
            {
                pass.setHint("required");
                pass.setHintTextColor(Color.RED);
            }
            if(ADDRESS.equals(""))
            {
                address.setHint("required");
                address.setHintTextColor(Color.RED);
            }
            if(PHONENO.equals(""))
            {
                phn.setHint("required");
                phn.setHintTextColor(Color.RED);
            }
        }
    }
}
