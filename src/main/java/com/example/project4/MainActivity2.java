package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {
    EditText a,b,c,d;
    String   tf1,tf2,tf3,tf4;
    FirebaseAuth auth;
Button log;
ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        auth = FirebaseAuth.getInstance();
        log = (Button) findViewById(R.id.button4);
        a = (EditText) findViewById(R.id.tf1);
        b = (EditText) findViewById(R.id.tf2);
        c = (EditText) findViewById(R.id.tf3);
        d = (EditText) findViewById(R.id.tf4);
        bar = findViewById(R.id.progressBar2);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    bar.setVisibility(View.VISIBLE);
                    tf1 = a.getText().toString();
                    tf2 = b.getText().toString();
                    tf3 = c.getText().toString();
                    tf4 = d.getText().toString();
                    if (TextUtils.isEmpty(tf1)) {
                        Toast.makeText(MainActivity2.this, "NAME FIELD CAN NOT BE EMPTY", Toast.LENGTH_SHORT).show();
                    }
                    if (tf2.isEmpty() && tf2.length() < 10){
                        Toast.makeText(MainActivity2.this, "MOBILE NO SHOULD BE GREATER THAN 10 AND CAN NOT BE EMPTY", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.INVISIBLE);
                    }
                    if (TextUtils.isEmpty(tf3)) {
                        Toast.makeText(MainActivity2.this, "EmailID CAN NOT BE EMPTY", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.INVISIBLE);
                    }
                    if (TextUtils.isEmpty(tf4)){
                        Toast.makeText(MainActivity2.this, "PASSWORD FIELD CAN NOT BE EMPTY", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.INVISIBLE);
                    }
                    else{
                        auth.createUserWithEmailAndPassword(tf3, tf4).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(MainActivity2.this, "verification mail send to the given email", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    startActivity(new Intent(MainActivity2.this, MainActivity3.class));
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity2.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });

                }
            }
            }
        });



    }
}