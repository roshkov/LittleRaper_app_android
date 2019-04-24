package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class PartSongActivity extends AppCompatActivity {

    private static final String TAG = "=====mylog";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference songRef;
    private String csID;

    Button lyricsbtn;
    Button rhymebtn;
    Toolbar toolbar;
    TextView songLyrics;
    ProgressBar progressBar;
    Button btnRhyme;
    TextView rhymesTV;

    static final String REQUEST_URL = "https://api.datamuse.com/words?rel_rhy=wallet";
//    static final String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_song);


        lyricsbtn = findViewById(R.id.btnLyrics);
        rhymebtn = findViewById(R.id.btnRhyme);
        toolbar = findViewById(R.id.toolbar2);
        progressBar = findViewById(R.id.login_progress2);
        songLyrics = findViewById(R.id.songLyricsTextView);
        btnRhyme = findViewById(R.id.btnRhyme);
        rhymesTV = findViewById(R.id.rhymesTextView);

        Intent intent = getIntent();
        final String clickedSid = intent.getExtras().getString("clickedSongID");  //stores unique ID of song clicked

        //csID = clickedSid;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        songRef= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs/"+clickedSid);

//
//        Intent intent = getIntent();
//        final String clickedSName = intent.getExtras().getString("clickedSongName");  //stores name of song clicked
//        final String clickedSid = intent.getExtras().getString("clickedSongID");  //stores name of song clicked

//        songLyrics.setText(clickedSid);

        songRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Song song = dataSnapshot.getValue(Song.class);
                songLyrics.setText(song.songLyrics);
                toolbar.setTitle(song.songName);
                //userSongs.setText(user.songList.get(1).songName);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });


            btnRhyme.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                GetRhymeAsync task = new GetRhymeAsync();
                task.execute(REQUEST_URL);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onStop();
//                Intent intent = new Intent(PartSongActivity.this, SongListActivity.class); //in order to lyrics field to be updated
//                startActivity(intent);
//
//             finish();
                onBackPressed();
            }
        });



    }
/////////////////////////////////////////////////////////////////////////////////////////////
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream is = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                is = urlConnection.getInputStream();
                jsonResponse = readFromStream(is);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (is != null)
                is.close();
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream is) throws IOException {
        StringBuilder output = new StringBuilder();
        if (is != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private class GetRhymeAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String jsonResponse = "";
            try {
                url = new URL(strings[0]);
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject root = null;
            try {

                JSONArray json = new JSONArray(s); //array of rhymes
//                JSONObject e = json.getJSONObject(0);

//                String name = e.getString("word");

//                rhymesTV.setText(name);
                ArrayList<String> rhymelist = new ArrayList<String>();

                if (json.length()==0){
                    rhymelist.add("No rhymes found");
                }
                else{
                for(int i=0;i<json.length();i++){

                    JSONObject e = json.getJSONObject(i);
                    rhymelist.add(e.getString("word"));
                    rhymesTV.append(rhymelist.get(i)+ "\n");
                } }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void onStop () {

        String songNameS = (String) toolbar.getTitle();///because name may changed in process
        String songLyricsS = songLyrics.getText().toString();

        DatabaseReference hopperRef = songRef;
        Map<String, Object> hopperUpdates = new HashMap<>();

        hopperUpdates.put("songName", songNameS);
        hopperUpdates.put("songLyrics", songLyricsS);

        hopperRef.updateChildren(hopperUpdates);



        super.onStop();
    }

    @Override
    public void onBackPressed() {

        onStop();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",songLyrics.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();



    }

}
