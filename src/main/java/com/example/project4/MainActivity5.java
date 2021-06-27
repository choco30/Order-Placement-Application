package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity5 extends AppCompatActivity {
Button submit;
    Button add;
LinearLayout layout;
RadioButton r1;
FirebaseAuth auth;
FirebaseFirestore firestore;
RadioGroup grp;
RadioButton r2;
RadioButton r3;
 RadioButton r4;
 EditText tf1;
 String key;
 ProgressBar bar;
    ProgressBar bar2;
 public static int count =0;
     DocumentReference doc;
    final HashMap<String,String> pole=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main5);
       submit=findViewById(R.id.button7);
       add=findViewById(R.id.button8);
       layout=findViewById(R.id.linearLayout);
       grp= findViewById(R.id.rgb);
       tf1=findViewById(R.id.editTextTextPersonName2);
bar=findViewById(R.id.progressBar5);
       firestore=FirebaseFirestore.getInstance();
       auth=FirebaseAuth.getInstance();

bar2=findViewById(R.id.progressBar4);


 submit.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         bar.setVisibility(View.VISIBLE);
         AlertDialog.Builder z=new AlertDialog.Builder(MainActivity5.this);
         z.setTitle("RESPONSE");
         z.setMessage("Are you sure want to submit the response..");
         z.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                      if(TextUtils.isEmpty(tf1.getText().toString())){
                          Toast.makeText(MainActivity5.this, "Enter  the pole Question ", Toast.LENGTH_SHORT).show();
                      }
             else {
                          final View v = getLayoutInflater().inflate(R.layout.options, null);
                      AlertDialog.Builder box= new AlertDialog.Builder(MainActivity5.this)
                              .setMessage("Enter the pole key")
                              .setView(v)
                              .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                  EditText text=    v.findViewById(R.id.opti);
                                  key=text.getText().toString();
                                        pole.put("question",tf1.getText().toString());
                                      doc=firestore.collection("pole").document(auth.getCurrentUser().getUid().substring(0,4)).collection("active pole").document(key);


                                      doc.set(pole).addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void unused) {
                                              Toast.makeText(MainActivity5.this, "pole has been initiated", Toast.LENGTH_SHORT).show();

                                              finish();

                                          }
                                      }).addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(Exception e) {
                                              Toast.makeText(MainActivity5.this, "can not able to create pole"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                          }
                                      });

                                  }

                              })
                              .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      bar.setVisibility(View.INVISIBLE);
                                  }
                              });
                      box.show();

                     



             }
             }

         });
         z.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

             }
         });
         z.show();
     }
 });
 add.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {


         r1=new RadioButton(MainActivity5.this);
      final View inflat=getLayoutInflater().inflate(R.layout.options,null);
         AlertDialog.Builder z=new AlertDialog.Builder(MainActivity5.this);
                    z.setMessage("Enter the option");
                    z.setView(inflat);

                 z.setPositiveButton("submit", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         count++;
                         EditText option=(EditText)inflat.findViewById(R.id.opti);
                         final  String opt=option.getText().toString();
                         r1.setText(opt);

                         pole.put(String.valueOf(count),opt);
                        grp.addView(r1);


                     }
                 });
                 z.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
       layout.removeView(r1);
                     }
                 });
         z.show();


     }
 });
    }
}