package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
Button a,b;
FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
            startActivity(new Intent(this,MainActivity4.class));

        a=(Button) findViewById(R.id.button);
        b=(Button) findViewById(R.id.button2);


    }
    public void login(View v){
        Intent aa=new Intent(this,MainActivity3.class);
        startActivity(aa);


    }
    public void signup(View V){
        Intent aa=new Intent(this,MainActivity2.class);
        startActivity(aa);

    }

}