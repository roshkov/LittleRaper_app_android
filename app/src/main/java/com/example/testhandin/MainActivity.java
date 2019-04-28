package com.example.testhandin;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testhandin.R;
import com.example.testhandin.fragment_profile_main;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static ProgressBar progressBarMain;
//    TextView logoutTV;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference userRef;
    Toolbar toolbar;
    TextView nicknameN;
    TextView emailN;
    TextView circleName1;
    SharedPreferences sharedPreferences;
    private String TAG = "mainActivityTag================================";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        NavigationView navv = (NavigationView) findViewById(R.id.nav_view);
        View header = navv.getHeaderView(0);
         emailN =  header.findViewById(R.id.emailNAV);
         nicknameN =  header.findViewById(R.id.nicknameNAV);
         circleName1 = header.findViewById(R.id.circleName);



//








//        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if ( getApplication().getTheme().toString().equals("AppTheme")) {
//                    getApplication().setTheme(R.style.AppTheme_CustomMode2);
//                } else {
//                    getApplication().setTheme(R.style.AppTheme);
//                }
//
//            }
//        });

        userRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid());
        emailN.setText(firebaseUser.getEmail());

////////////////////////////////////////////////////////////////
        Context mContext = MainActivity.this;
//        sharedPreferences = ((MainActivity) mContext).getPreferences(Context.MODE_PRIVATE);

//
        sharedPreferences = mContext.getSharedPreferences("tb_main_color", Context.MODE_PRIVATE);
        //  String defaultValue = String.valueOf(getResources().getColor(R.color.colorPrimary));
        int defaultValue =getResources().getColor(R.color.colorPrimary); //default color if there s nothing at shared preferences
        Log.i(TAG, "defaultValue int= "+defaultValue);
        // String colorT = sharedPreferences.getString("ToolbarTheme", defaultValue);  /
        int colorT = sharedPreferences.getInt("ToolbarTheme", defaultValue);  //retrive current color
        Log.i(TAG, "colorT= "+colorT);


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         progressBarMain = findViewById(R.id.slProgress);
        toolbar.setBackgroundColor((colorT));
        //toolbar.setBackgroundColor(Integer.parseInt(colorT));



        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_profile_main()).commit();
            navigationView.setCheckedItem(R.id.nav_main);
        }

        userRef.addValueEventListener(new ValueEventListener() {
            private String TAG = "==____===========TAG: ";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                nicknameN.setText(user.nickname);
                String s = nicknameN.getText().toString();
//                Log.i(TAG,s);
                circleName1.setText(s.substring(0, 1));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });





    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_profile_main()).commit();
                break;
            case R.id.nav_mylyrics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_song_list()).commit();
                break;
            case R.id.nav_inspiration:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_inspiration()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_edit_personal_data()).commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    public void onClickLogout(View v)
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void mainCreateNote(View view)
    {
        startActivity(new Intent(MainActivity.this,Pop.class));
    }


    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));


//                Uri.parse(   "https://youtube.com" ));

        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

}