package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testhandin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.testhandin.MainActivity.progressBarMain;

public class fragment_song_list extends Fragment implements recycleAdapter.OnListItemClickListener{

    private static final String TAG = "________mylog______:";
  //  Toolbar toolbar;
     ProgressBar songListLoad;
    //ListView SlistView;
    RecyclerView mRecyclerViewList;
    RecyclerView.Adapter mRecycleAdapter;

  //  ProgressBar progressBar;
//    int noteAmount;
    TextView noteAmountView;
    String lastClicked;
   // TextView userEmail;
    TextView userNickname;
    //TextView userSongs;
    Button addBtn;


//    ArrayList<String> songArray;

    ArrayList<Song> songArray;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference userRef;
    private DatabaseReference userSongsRef;
    View view; //from onCreateView method


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_song_list, container, false);
       // toolbar = view.findViewById(R.id.toolbar2);
      //  progressBar = view.findViewById(R.id.login_progress2);
        mRecyclerViewList = view.findViewById(R.id.recycleViewSongs);
       // userEmail = view.findViewById(R.id.userEmailSL);
        userNickname = view.findViewById (R.id.userNicknameSL);
        noteAmountView = view.findViewById(R.id.notesAmount);
        addBtn = view.findViewById(R.id.addNewSong);
       // progressBar = view.findViewById(R.id.slProgress);

        final String lastClicked=null;

        songListLoad =  getActivity().findViewById(R.id.slProgress);



//        songArray = new ArrayList<String>();
        songArray = new ArrayList<Song>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        String usern = firebaseAuth.getInstance().getCurrentUser().getUid();;
//        userRef = FirebaseDatabase.getInstance().getReference("users/"+usern);
        userRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid().toString());
        userSongsRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs");


        mRecyclerViewList.hasFixedSize();
        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));  //this
        mRecycleAdapter = new recycleAdapter(songArray,this);
        mRecyclerViewList.setAdapter(mRecycleAdapter);


        userRef.addValueEventListener(new ValueEventListener() {   //update nickname
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userNickname.setText(user.nickname);
                Log.i(TAG, user.nickname);
                //userSongs.setText(user.songList.get(1).songName);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });



        userSongsRef.addValueEventListener(new ValueEventListener() {    //update song list


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exists = false;
                String keyLyrics = null;
                String keyName = null;
                songArray.clear();

//                ProgressBar songListLoad1 =  getActivity().findViewById(R.id.slProgress);
//                songListLoad1.setVisibility(View.VISIBLE);

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
              Collections.reverse(songArray);
                Log.i(TAG, songArray.get(0).songName+"_+_" + songArray.get(0).songID + "\n"+ songArray.get(1).songName+ "_+_" + songArray.get(1).songID );



                noteAmountView.setText(Integer.toString(songArray.size()));
//                songListLoad.setVisibility(View.GONE);

            }
            // userSongs.setText(songArray.toString());



            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(getActivity(),Pop.class));


            }
        });




         return view;
    }



//

    public void onListItemClick (int clickedItemIndex) {
        int songNum = clickedItemIndex;
        String name = songArray.get(songNum).songName;
        String sId = songArray.get(songNum).songID;
        lastClicked = sId;
        Log.i(TAG, sId);
/////////////////////////////////////////////////////////////////////////////////////////////////HERE!
        //Intent i = new Intent(getActivity(), PartSongActivity.class);
        Intent i = new Intent(getActivity(), ThisSongActivity.class);
//        i.putExtra("clickedSongName", name);
        i.putExtra("clickedSongID", sId);

        startActivityForResult(i,1);
        // finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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