package com.example.testhandin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SongListActivity extends AppCompatActivity {


    TextView userEmail;
    TextView userNickname;
    TextView userSongs;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);


        userEmail = findViewById(R.id.userEmailSL);
        userNickname = findViewById (R.id.userNicknameSL);
        userSongs = findViewById(R.id.userSongsSL);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("users");





        userEmail.setText(firebaseUser.getEmail()); // email displaying




    }
}
