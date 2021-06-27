package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.Document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity {

    Button a, b,c;
    String key;
    FirebaseFirestore firestore;
    DocumentReference document;
    FirebaseAuth auth;
     String keyss;
 ProgressBar bar;



    public static boolean value=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        firestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        bar=findViewById(R.id.progressBar4);
        a = findViewById(R.id.button5);
        b = findViewById(R.id.button6);
        c= findViewById(R.id.button10);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setVisibility(View.VISIBLE);
                final View v = getLayoutInflater().inflate(R.layout.paa, null);
                final View vc=getLayoutInflater().inflate(R.layout.options,null);
                final AlertDialog.Builder creator=new AlertDialog.Builder(MainActivity4.this);
                 creator.setMessage("Enter creator key")
                         .setView(vc)
                         .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 EditText text1=(EditText)vc.findViewById(R.id.opti);
                                 keyss=text1.getText().toString();
                                 AlertDialog.Builder z = new AlertDialog.Builder(MainActivity4.this);

                                    z.setView(v);

                                 z.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialogInterface, int i) {
                                         EditText text=(EditText)v.findViewById(R.id.pass);
                                         final String keys=text.getText().toString().trim();

                                         firestore.collection("pole").document(keyss).collection("active pole").document(keys).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                             @Override
                                             public void onComplete(Task<DocumentSnapshot> task) {
                                                 if(task.isSuccessful())
                                                 {
                                                     DocumentSnapshot  doc=task.getResult();
                                                     if(doc.exists() )
                                                     {


                                                      Task aaa=  firestore.collection("pole").document(keys).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                             @Override
                                                             public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                 if (documentSnapshot.exists()){
                                                                     Map ab = documentSnapshot.getData();
                                                                 if (ab.containsValue(auth.getCurrentUser().getUid())) {
                                                                     value = true;
                                                                     Toast.makeText(MainActivity4.this, "vote already been casted", Toast.LENGTH_SHORT).show();

                                                                 }
                                                                 else{
                                                                     Intent a=  new Intent(MainActivity4.this,MainActivity6.class);
                                                                     a.putExtra("creator",keyss);
                                                                     a.putExtra("key",keys);

                                                                     startActivity(a);

                                                                 }


                                                             }
                                                                 else{
                                                                     Intent a=  new Intent(MainActivity4.this,MainActivity6.class);
                                                                     a.putExtra("creator",keyss);
                                                                     a.putExtra("key",keys);

                                                                     startActivity(a);
                                                                 }


                                                             }
                                                         }).addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure( Exception e) {
                                                              Toast.makeText(MainActivity4.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      });

                                                        }
                                                     else{
                                                         Toast.makeText(MainActivity4.this, "NO ACTIVE POLL WITH GIVEN KEY", Toast.LENGTH_SHORT).show();
                                                     }
                                                 }

                                             }
                                         });



                                     }
                                 });
                                 z.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialogInterface, int i) {

                                     }
                                 });


                           z.show();


                             }
                         }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         bar.setVisibility(View.INVISIBLE);
                     }
                 });
        creator.show();


            }

        });
       c.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               bar.setVisibility(View.VISIBLE);
               final View vv= getLayoutInflater().inflate(R.layout.paa, null);

               final AlertDialog.Builder creator=new AlertDialog.Builder(MainActivity4.this);
               creator
                       .setView(vv)
                       .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               EditText answer=vv.findViewById(R.id.pass);
                               String ans=answer.getText().toString();
                                         firestore.collection("pole").document(ans).collection("userid").document(ans).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                             @Override
                                             public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if(documentSnapshot.exists()) {
                                                    Map data = documentSnapshot.getData();
                                                    HashMap<String,Object> d=new HashMap(data);
                                                    Intent c = new Intent(MainActivity4.this, MainActivity7.class);

                                                    c.putExtra("data",d);
                                                    startActivity(c);
                                                }
                                                else
                                                    Toast.makeText(MainActivity4.this, "Invalid Poll Key", Toast.LENGTH_SHORT).show();
                                             }
                                         })   ;
                           }
                       });

               creator.show();
           }
       });
        bar.setVisibility(View.INVISIBLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         switch(item.getItemId()){
             case R.id.logout:
                 AlertDialog.Builder logout=new AlertDialog.Builder(MainActivity4.this)
                         .setTitle("LogOut")
                         .setMessage("Are u Sure Want to Logout")
                         .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                auth.signOut();
                               startActivity(new Intent(MainActivity4.this,MainActivity.class));
                                finish();
                             }
                         });
                 logout.show();
                 break;
             case R.id.creatorid: AlertDialog.Builder logosut=new AlertDialog.Builder(MainActivity4.this).setMessage(auth.getCurrentUser().getUid().substring(0,4)).setTitle("CreatorKey").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                 }
             });
          logosut.show();

         }

        return super.onOptionsItemSelected(item);
    }

    public void create(View view) {
        bar.setVisibility(View.VISIBLE);
        startActivity(new Intent(MainActivity4.this,MainActivity5.class));
        bar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}