package com.eventor.smartshopapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lenovo on 21-05-2017.
 */

public class Login extends AppCompatActivity {
    EditText ephone,epass;
    String PHONE,PASS;
    Button login;
    TextView txt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.login);
        ephone=(EditText)findViewById(R.id.loginphone);
        epass=(EditText)findViewById(R.id.loginpass);
        txt=(TextView)findViewById(R.id.err);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PHONE=ephone.getText().toString().trim();
                PASS=epass.getText().toString().trim();
                checkDetails();
            }
        });
    }
    public void checkDetails(){
        SharedPreferences prefs = this.getSharedPreferences("appData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = prefs.edit();
        Toast.makeText(this,prefs.getString("SavedPhoneNo",""),Toast.LENGTH_LONG).show();
        Toast.makeText(this,prefs.getString("SavedPassword",""),Toast.LENGTH_LONG).show();
        if(PHONE.equals(prefs.getString("SavedPhoneNo",""))&&PASS.equals(prefs.getString("SavedPassword","")))
        {
            editor.commit();

            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
        else{
        txt.setVisibility(View.VISIBLE);
        }
    }
}
