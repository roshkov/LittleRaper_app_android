package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SongListActivity extends AppCompatActivity implements recycleAdapter.OnListItemClickListener{


    private static final String TAG = "________mylog______:";
    Toolbar toolbar;
    ProgressBar progressBar;
    //ListView SlistView;
    RecyclerView mRecyclerViewList;
    RecyclerView.Adapter mRecycleAdapter;

    String lastClicked;
    TextView userEmail;
    TextView userNickname;
    //TextView userSongs;


//    ArrayList<String> songArray;

    ArrayList<Song> songArray;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference userRef;
    private DatabaseReference userSongsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        toolbar = findViewById(R.id.toolbar2);
        progressBar = findViewById(R.id.login_progress2);

        userEmail = findViewById(R.id.userEmailSL);
        userNickname = findViewById (R.id.userNicknameSL);
        //userSongs = findViewById(R.id.userSongsSL);
        //SlistView = findViewById(R.id.SongListView);
        final String lastClicked=null;


//        songArray = new ArrayList<String>();
        songArray = new ArrayList<Song>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid().toString());
        userSongsRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs");


        toolbar.setTitle("My Lyrics");
        userEmail.setText(firebaseUser.getEmail());

        //set recycle view
        mRecyclerViewList = findViewById(R.id.recycleViewSongs);
        mRecyclerViewList.hasFixedSize();
        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        mRecycleAdapter = new recycleAdapter(songArray,this);
        mRecyclerViewList.setAdapter(mRecycleAdapter);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userNickname.setText(user.nickname);
                //userSongs.setText(user.songList.get(1).songName);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

    });



        userSongsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exists = false;
                String keyLyrics = null;
                String keyName = null;
                songArray.clear();

                for(DataSnapshot dSnapshot : dataSnapshot.getChildren()) {

                    String id = dSnapshot.getKey();


                    if (exists) { continue;}

                    for(DataSnapshot ds : dSnapshot.getChildren()) {


                        if(ds.getKey().equals("songName")) {
                            keyName = ds.getValue(String.class);
//                            Song song = new Song (id,keyName, 1);
                        }
                        if(ds.getKey().equals("songLyrics")) {
                            keyLyrics = ds.getValue(String.class);
//                            Song song = new Song (id,keyName, 1);
                        }

                       }
                    //Log.i(TAG,keyName+" , "+keyLyrics);
                    Song song = new Song (id,keyName, keyLyrics);


                    songArray.add(song);
                    mRecycleAdapter.notifyDataSetChanged();
                    }
//                Log.i(TAG, songArray.get(0).songName+"_+_" + songArray.get(0).songID + "\n"+ songArray.get(1).songName+ "_+_" + songArray.get(1).songID );
                }
                   // userSongs.setText(songArray.toString());


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);
                  finish();
            }
        });





    }

    public void onListItemClick (int clickedItemIndex) {
        int songNum = clickedItemIndex;
        String name = songArray.get(songNum).songName;
        String sId = songArray.get(songNum).songID;
        lastClicked = sId;
        Log.i(TAG, sId);
//        Toast.makeText(this, "Song name: " + name, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Song Number: " + songNum, Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(SongListActivity.this, PartSongActivity.class));

        Intent i = new Intent(SongListActivity.this, PartSongActivity.class);
//        i.putExtra("clickedSongName", name);
        i.putExtra("clickedSongID", sId);


        startActivityForResult(i,1);
       // finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                for (int i = 0; i< songArray.size(); i++) {
                    if (songArray.get(i).songID.equals(lastClicked)) {
                        songArray.get(i).songLyrics = data.getStringExtra("result");

//                        Toast toast = Toast.makeText(SongListActivity.this, "size: "+songArray.size(), Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult


}
