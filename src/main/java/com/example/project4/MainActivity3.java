package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity3 extends AppCompatActivity {
FirebaseAuth aut=FirebaseAuth.getInstance();
   String us,pas;
   ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
         bar=findViewById(R.id.progressBar3);


    }
    public void forget(View v){
        bar.setVisibility(View.VISIBLE);
           final View forget=getLayoutInflater().inflate(R.layout.forgetpassword,null);

     AlertDialog.Builder fors=new AlertDialog.Builder(MainActivity3.this)
              .setView(forget)
               .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       EditText forge = forget.findViewById(R.id.editTextTextPersonName3);
                       String email = forge.getText().toString();
                       if (TextUtils.isEmpty(email)) {
                           Toast.makeText(MainActivity3.this, "ENTER EMAIL ID", Toast.LENGTH_SHORT).show();
                           bar.setVisibility(View.INVISIBLE);
                       }
                           else{
                       aut.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               Toast.makeText(MainActivity3.this, "password reset mail is sent to the given email id", Toast.LENGTH_SHORT).show();
                               bar.setVisibility(View.INVISIBLE);
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(Exception e) {
                               Toast.makeText(MainActivity3.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                               bar.setVisibility(View.INVISIBLE);
                           }
                       });
                   }
                   }

               })

             .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     bar.setVisibility(View.INVISIBLE);
                 }
             });

           fors.show();

    }
    public void submit(View v){
        bar.setVisibility(View.VISIBLE);
        us=((EditText)findViewById(R.id.editTextTextPersonName)).getText().toString();
            pas=((EditText)findViewById(R.id.editTextTextPassword)).getText().toString();
            if(TextUtils.isEmpty(us)){
                Toast.makeText(this, "EMAIL ID CAN NOT BE EMPTY", Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.INVISIBLE);
            }
            if(TextUtils.isEmpty(us)){
                Toast.makeText(this, "PASSWORD CAN NOT BE EMPTY", Toast.LENGTH_SHORT).show();
            bar.setVisibility(View.INVISIBLE);}
            else{
            aut.signInWithEmailAndPassword(us, pas)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (aut.getCurrentUser().isEmailVerified()) {

                                startActivity(new Intent(MainActivity3.this,MainActivity4.class));
                                finish();

                            } else {
                                Toast.makeText(MainActivity3.this, "please check the verification mail sent on the given email id", Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity3.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

}