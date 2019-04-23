package com.example.testhandin;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileMain extends AppCompatActivity {

    TextView userEmail;
    Button userSignOut;
    Button userUpdate;
    TextView userNickname;
    TextView userAbout;
    Button userGotoSongs;
    Button userCreateSong;
    TextView songName;
    TextView songLyrics;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);



        userEmail = findViewById(R.id.userEmail);
        userSignOut = findViewById(R.id.btnSignOut);
        userUpdate = findViewById(R.id.btnUpdateInfo);
        userNickname = findViewById(R.id.nicknameText);
        userAbout = findViewById(R.id.aboutText);
        userGotoSongs = findViewById(R.id.btnGoToSongList);
        userCreateSong = findViewById(R.id.btnCreateSong);
        songName = findViewById(R.id.newSongName);
        songLyrics = findViewById(R.id.newSongLyrics);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid().toString());

        userEmail.setText(firebaseUser.getEmail());



        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userNickname.setText(user.nickname);
                userAbout.setText(user.about);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        userSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileMain.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });


        userUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               DatabaseReference userRef = mDatabase.child("users");

                Map<String, Object> userUpdates = new HashMap<>();
                String nickname = firebaseUser.getUid().toString()+"/nickname";
                String about = firebaseUser.getUid().toString()+"/about";

                userUpdates.put(nickname, userNickname.getText().toString());
                userUpdates.put(about, userAbout.getText().toString());

                userRef.updateChildren(userUpdates);
                Toast.makeText(ProfileMain.this, "Data changed", Toast.LENGTH_LONG).show();
               // userNickname.setText("");
               // userAbout.setText("");

            }


        });


        userCreateSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = mDatabase.child("users");
               // Map<String, Object> userUpdates = new HashMap<>();

                String songNameS = songName.getText().toString();           ///
                String songLyricsS = songLyrics.getText().toString();       //create song obj
                Song song = new Song(songNameS,songLyricsS);                ///


                String userPath = "users/" + firebaseUser.getUid().toString()+"/songs";  //path to particular user's song directory
                String key = mDatabase.child(userPath).push().getKey();                  //create key


                Map<String, Object> postValues = song.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(userPath +"/song" + key, postValues);

                mDatabase.updateChildren(childUpdates);

            }
        });


        userGotoSongs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ProfileMain.this, SongListActivity.class);
                startActivity(intent);

            }
        });


    }

    public void onBackPressed() {
        //do nothing
    }
}
