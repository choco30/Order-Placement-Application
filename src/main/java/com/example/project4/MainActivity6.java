package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.Document;

import java.util.HashMap;
import java.util.Map;

public class MainActivity6 extends AppCompatActivity {
public static final  FirebaseFirestore firestore=FirebaseFirestore.getInstance();;
public static final FirebaseAuth auth=FirebaseAuth.getInstance();;
    DocumentReference doc;
    String question;
    String choice1="0";
    String choice2="0";
    String choice3="0";
    String choice4="0";
    LinearLayout layout2;
    RadioGroup rg2;
    TextView tv1;
    String questionfield;
    RadioButton rb1;
    Button vote;
    ProgressBar bar;
    static int result;
    public static  boolean exist=false;
 public static int limit;
 public static HashMap<String ,Number> answer;
    int iw=1;

String creator;
String value;

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
           bar=findViewById(R.id.progressBar6);
             layout2=findViewById(R.id.linearLayout2);
             rg2=findViewById(R.id.rg2);
             tv1=findViewById(R.id.textView2);

             vote=findViewById(R.id.button9);
             Intent b=getIntent();

              value=b.getStringExtra("creator");
         creator=b.getStringExtra("key");
        doc=firestore.collection("pole").document(value).collection("active pole").document(creator);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot doci) {
               Map<String ,Object> pole=doci.getData();

                   question=pole.get("question").toString();
                tv1.setText(question);
                   limit=pole.size();
                  for(int i=1;i<pole.size();i++)
                  {rb1=new RadioButton(MainActivity6.this);
                      rb1.setText(pole.get(Integer.toString(i)).toString());
                      rb1.setId(i);

                      rg2.addView(rb1);



                  }

            }
        });


        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bar.setVisibility(View.VISIBLE);
                final int id = (int)rg2.getCheckedRadioButtonId();


              if (id != -1){
                    doc = firestore.collection("pole").document(creator).collection("userid").document(creator);
                doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot doci) {
                        if (doci.exists()) {

                            Map pole = doci.getData();
                            long res = (long)pole.get(String.valueOf(id));
                            res++;
                            HashMap pole2 = new HashMap(pole);
                            pole2.put(String.valueOf(id), res);
                            doc.set(pole2);
                        }
                        else{
                            {
                                HashMap<String, Number> abc = new HashMap<>();
                                for (int i = 1; i < limit; i++) {
                                    if (i == id)
                                        abc.put(String.valueOf(id), 1);
                                    else
                                        abc.put(String.valueOf(i), 0);
                                }
                                firestore.collection("pole").document(creator).collection("userid").document(creator).set(abc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(MainActivity6.this, "vote casted", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(MainActivity6.this, "vote  can not be casted please retry", Toast.LENGTH_LONG).show();
                                    }
                                });


                            }
                        }


                    }
                });
                if (exist == true) {
                    HashMap<String, Number> abc = new HashMap<>();
                    for (int i = 1; i < limit; i++) {
                        if (i == id)
                            abc.put(String.valueOf(id), 1);
                        else
                            abc.put(String.valueOf(i), 0);
                    }
                    firestore.collection("pole").document(creator).collection("userid").document(creator).set(abc).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity6.this, "vote casted", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(MainActivity6.this, "vote  can not be casted please retry", Toast.LENGTH_LONG).show();
                        }
                    });


                }


                 firestore.collection("pole").document(creator).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                     @Override
                     public void onSuccess(DocumentSnapshot documentSnapshot) {

                         int idno = 1;
                         if (documentSnapshot.exists()){
                             Map<String, Object> uid = null;
                         uid = documentSnapshot.getData();

                         if (documentSnapshot.contains(String.valueOf(idno))) {
                             int idd = Integer.valueOf(documentSnapshot.get(String.valueOf(0)).toString());
                             idd++;
                             uid.put(String.valueOf(idd), auth.getCurrentUser().getUid());
                             uid.put("0", idd);
                             firestore.collection("pole").document(creator).set(uid);
                         } else {
                             uid.put("0", idno);
                             uid.put(String.valueOf(idno), auth.getCurrentUser().getUid());
                             firestore.collection("pole").document(creator).set(uid);

                         }
                     }
                         else
                         {HashMap<String,Object> r=new HashMap<>();
                             r.put("0", idno);
                             r.put(String.valueOf(idno), auth.getCurrentUser().getUid());
                             firestore.collection("pole").document(creator).set(r);
                         }

                     }
                 });
            }
                else
              {Toast.makeText(MainActivity6.this, "select an option", Toast.LENGTH_SHORT).show();}

                finish();
            }


        });
    }
}