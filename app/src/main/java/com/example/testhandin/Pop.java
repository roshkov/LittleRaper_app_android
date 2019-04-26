package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Pop extends AppCompatActivity {


    EditText sNameET;
    String sName;
    Button createbutton;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference songRef;
    private String TAG="==mylog============= ";


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_newsong);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //songRef= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs/"+clickedSid);
        createbutton = findViewById(R.id.confirmCreatenewSong);

        sNameET = findViewById(R.id.songTitleEditText);
        sName="";


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.7),(int) (height*0.4));

//        boolean a;
//        if (sNameET == null){ a = true; }
//        else{ a = false;}
//
//        Log.i(TAG,"equals null: "+ a );








        createbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sName = sNameET.getText().toString();
                 Song song = new Song(sName,"");

                  DatabaseReference userRef = mDatabase.child("users");


                String userPath = "users/" + firebaseUser.getUid().toString()+"/songs";  //path to particular user's song directory
                String key = mDatabase.child(userPath).push().getKey();                  //create key


                Map<String, Object> postValues = song.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put(userPath +"/song" + key, postValues);

                mDatabase.updateChildren(childUpdates);



                Intent i = new Intent(Pop.this, PartSongActivity.class);
                          i.putExtra("clickedSongID", "song"+key);
                startActivity(i);
                finish();
                        }
                    });




    }


//    sName = sNameET.getText().toString();
//    Song song = new Song(sName,"");
//
//    DatabaseReference userRef = mDatabase.child("users");
//
//
//    String userPath = "users/" + firebaseUser.getUid().toString()+"/songs";  //path to particular user's song directory
//    String key = mDatabase.child(userPath).push().getKey();                  //create key
//
//
//    Map<String, Object> postValues = song.toMap();
//
//    Map<String, Object> childUpdates = new HashMap<>();
//                childUpdates.put(userPath +"/song" + key, postValues);
//
//                mDatabase.updateChildren(childUpdates);
//
//
//
//    Intent i = new Intent(Pop.this, PartSongActivity.class);
//                i.putExtra("songName", sName);
//    startActivity(i);


}
