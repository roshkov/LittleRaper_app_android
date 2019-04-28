package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2/28/2017.
 */

public class this_fragment_tab_lyrics extends Fragment {

    private static final String TAG = "=====mylog";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    DatabaseReference songRef;
     String csID;
    DatabaseReference songRefDel;
    boolean isDeleted = false;

    ThisSongActivity activityReferenceVar;
    Button lyricsbtn;
    Button rhymebtn;
    Toolbar toolbar;
    TextView songLyrics;
    ProgressBar progressBar;
    Button btnRhyme;
    TextView rhymesTV;

    static final String REQUEST_URL = "https://api.datamuse.com/words?rel_rhy=table";

    private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.this_tab_lyrics,container,false);
       // btnTEST = (Button) view.findViewById(R.id.btnTEST);

//        btnTEST.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 1", Toast.LENGTH_SHORT).show();
//            }
//        });


         activityReferenceVar = (ThisSongActivity) getActivity();

        Intent intent = activityReferenceVar.intent;
        final String clickedSid = intent.getExtras().getString("clickedSongID");  //stores unique ID of song clicked


        lyricsbtn = view.findViewById(R.id.btnLyrics);
        rhymebtn = view.findViewById(R.id.btnRhyme);
//        toolbar = view.findViewById(R.id.toolbar2);
        toolbar = activityReferenceVar.toolbar;
        progressBar = view.findViewById(R.id.login_progress2);
        songLyrics = view.findViewById(R.id.songLyricsTextView);
        btnRhyme = view.findViewById(R.id.btnRhyme);
        rhymesTV = view.findViewById(R.id.rhymesTextView);







        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        songRef= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs/"+clickedSid);
        songRefDel = songRef;

    //    setSupportActionBar(toolbar);


        songRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Song song = dataSnapshot.getValue(Song.class);

                if (song!=null) {
                    songLyrics.setText(song.songLyrics);
                    toolbar.setTitle(song.songName);
                    //userSongs.setText(user.songList.get(1).songName);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });


//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        return view;
    }




//    private String makeHttpRequest(URL url) throws IOException {
//        String jsonResponse = "";
//
//        if (url == null)
//            return jsonResponse;
//
//        HttpURLConnection urlConnection = null;
//        InputStream is = null;
//
//        try {
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setReadTimeout(10000);
//            urlConnection.setConnectTimeout(15000);
//            urlConnection.connect();
//            if (urlConnection.getResponseCode() == 200) {
//                is = urlConnection.getInputStream();
//                jsonResponse = readFromStream(is);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (urlConnection != null)
//                urlConnection.disconnect();
//            if (is != null)
//                is.close();
//        }
//        return jsonResponse;
//    }
//
//    private String readFromStream(InputStream is) throws IOException {
//        StringBuilder output = new StringBuilder();
//        if (is != null) {
//            InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
//            BufferedReader reader = new BufferedReader(inputStreamReader);
//            String line = reader.readLine();
//            while (line != null) {
//                output.append(line);
//                line = reader.readLine();
//            }
//        }
//        return output.toString();
//    }
//
//    private class GetRhymeAsync extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            URL url = null;
//            String jsonResponse = "";
//            try {
//                url = new URL(strings[0]);
//                jsonResponse = makeHttpRequest(url);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return jsonResponse;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            JSONObject root = null;
//            try {
//
//                JSONArray json = new JSONArray(s); //array of rhymes
////                JSONObject e = json.getJSONObject(0);
//
////                String name = e.getString("word");
//
////                rhymesTV.setText(name);
//                ArrayList<String> rhymelist = new ArrayList<String>();
//
//                if (json.length()==0){
//                    rhymelist.add("No rhymes found");
//                }
//                else{
//                    for(int i=0;i<json.length();i++){
//
//                        JSONObject e = json.getJSONObject(i);
//                        rhymelist.add(e.getString("word"));
//                        rhymesTV.append(rhymelist.get(i)+ "\n");
//                    } }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }








//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        int id = item.getItemId();
//
//        switch (item.getItemId()) {
//            case R.id.editTitleBtn:
//
//                Intent i = new Intent(getActivity(), Pop_changetitle.class);
//                i.putExtra("thisSongName", toolbar.getTitle());
////                startActivity(i);
//                startActivityForResult(i,3);
//
//                break;
//            case R.id.deleteImgBtn:
////                songRefDel= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs/"+csID);
////                songRefDel.removeValue();
////                isDeleted = true;
////                getActivity().finish();
//                ((ThisSongActivity)getActivity()).deleteSong(csID);
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void onStop () {
        isDeleted =  activityReferenceVar.isDeleted;
        if (!isDeleted) {
            String songNameS = (String) toolbar.getTitle();///because name may changed in process
            String songLyricsS = songLyrics.getText().toString();

            DatabaseReference hopperRef = songRef;
            Map<String, Object> hopperUpdates = new HashMap<>();

            hopperUpdates.put("songName", songNameS);
            hopperUpdates.put("songLyrics", songLyricsS);

            hopperRef.updateChildren(hopperUpdates);

        }

        super.onStop();
    }

   // @Override
    public void onBackPressed() {

        onStop();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",songLyrics.getText().toString());
        getActivity().setResult(Activity.RESULT_OK,returnIntent);
        getActivity().finish();

    }

}
