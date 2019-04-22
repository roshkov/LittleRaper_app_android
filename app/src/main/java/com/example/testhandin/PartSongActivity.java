package com.example.testhandin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class PartSongActivity extends AppCompatActivity {

    private static final String TAG = "=====mylog";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference songRef;

    Button lyricsbtn;
    Button rhymebtn;
    Toolbar toolbar;
    TextView songLyrics;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_song);


        lyricsbtn = findViewById(R.id.btnLyrics);
        rhymebtn = findViewById(R.id.btnRhyme);
        toolbar = findViewById(R.id.toolbar2);
        progressBar = findViewById(R.id.login_progress2);
        songLyrics = findViewById(R.id.songLyricsTextView);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        songRef= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs");

//
        Intent intent = getIntent();
        final String clickedSName = intent.getExtras().getString("clickedSongName");  //stores name of song clicked

//        songLyrics.setText(clickedSName);

//  songRef.addValueEventListener(new ValueEventListener() {
//         @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
//                    for(DataSnapshot ds : dSnapshot.getChildren()) {
//
//
//                        String key = dSnapshot.getKey();
//                        songLyrics.append(key+"   ");
//
//
////                        if(ds.getKey().equals("songName")) {
////                            String key = ds.getValue(String.class);
////                            songLyrics.append(key+"   ");
////                        }
//
//
////                        Song song = dataSnapshot.getValue(Song.class);
////
////                        songLyrics.setText(song.songName+"   "+ song.songLyrics);
////                        if (song.songName.equals(clickedSName)) {
////                            toolbar.setTitle(song.songName);
////                            songLyrics.setText(song.songLyrics);
////                        break;
////                        }
////                        if (song == null) {Log.i(TAG, "song is null!");};
////                        if (song != null) {Log.i(TAG, "song is notnull!");};
////
////                        Log.i(TAG,song.songName);
//
////                        if(ds.getKey().equals("songName")) {
////                            String key = ds.getValue(String.class);
////                            if(key.equals(clickedSName)){}
//                            // userSongs.append("\n");
//                            //userSongs.append(key);
////                            songArray.add(key);
//                            //adapter.notifyDataSetChanged();
//                            //adapter.add(key);
////                            mRecycleAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                }
////                Log.i(TAG, songArray.toString());
////            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });



    }
}
