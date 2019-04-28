package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class ThisSongActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    public String clickedSid;
    public String test;
    public Intent intent;
    public Toolbar toolbar;
    private DatabaseReference mDatabase;
    DatabaseReference songRefDel;
    DatabaseReference songRef;
    boolean isDeleted = false;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String csID;
    private String TAG = "===============================thissongactivity: ";
    public int ColorT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.this_song_activity);

         intent = getIntent();
//        final String clickedSid = intent.getExtras().getString("clickedSongID");  //stores unique ID of song clicked


          clickedSid = intent.getExtras().getString("clickedSongID");
        csID = clickedSid;

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());






        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        songRef= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs/"+clickedSid);
        songRefDel = songRef;
        Log.i(TAG, clickedSid);

//        songRef= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs/"+clickedSid);
//        songRefDel = songRef;





        // Set up the ViewPager with the sections adapter.
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences mPrefs = getSharedPreferences("tb_main_color",getResources().getColor(R.color.colorPrimary));
        int defaultValue =getResources().getColor(R.color.colorPrimary);
        ColorT= mPrefs.getInt("ToolbarTheme", defaultValue);


        Log.i(TAG,"coloris=" + ColorT);
        toolbar.setBackgroundColor(ColorT);


    }





    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new this_fragment_tab_lyrics(), "Lyrics");
        adapter.addFragment(new this_fragment_tab_rhyme(), "Rhyme");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_songlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.editTitleBtn:

                Intent i = new Intent(ThisSongActivity.this, Pop_changetitle.class);
                i.putExtra("thisSongName", toolbar.getTitle());
//                startActivity(i);
                startActivityForResult(i,3);
                break;

            case R.id.deleteNote:
                songRefDel= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs/"+csID);
                songRefDel.removeValue();
                isDeleted = true;
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 3) {

            if(resultCode == Activity.RESULT_OK){


                toolbar.setTitle(data.getStringExtra("resultName"));
                Log.i(TAG, data.getStringExtra("resultName"));

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG,"reaches cancel");
                //Write your code if there's no result
            }
        }
    }



//    public void deleteSong(String csID)
//    {
//        songRefDel= FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid()+"/songs/"+csID);
//        songRefDel.removeValue();
//        isDeleted = true;
//        finish();
//
//    }

}
