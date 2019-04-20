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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileMain extends AppCompatActivity {

    TextView userEmail;
    Button userSignOut;
    Button userUpdate;
    TextView userNickname;
    TextView userAbout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);



        userEmail = findViewById(R.id.userEmail);
        userSignOut = findViewById(R.id.btnSignOut);
        userUpdate = findViewById(R.id.btnUpdateInfo);
        userNickname = findViewById(R.id.nicknameText);
        userAbout = findViewById(R.id.aboutText);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        userEmail.setText(firebaseUser.getEmail());
//        userEmail.setText(userEmail.getText().toString()+"\n"+ firebaseUser.get)


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
//                DatabaseReference particularUserRef = userRef.child(firebaseUser.getUid());

//                Map<String, User> users = new HashMap<>();
//                users.put(firebaseUser.getUid(), new User(userEmail.getText().toString(), userNickname.getText().toString(),userAbout.getText().toString()));
//                usersRef.setValue(users);

//                Map<String, Object> particularUserUpdates = new HashMap<>();
//                particularUserUpdates.put("")

                Map<String, Object> userUpdates = new HashMap<>();
                String nickname = firebaseUser.getUid().toString()+"/nickname";
                String about = firebaseUser.getUid().toString()+"/about";

                userUpdates.put(nickname, userNickname.getText().toString());
                userUpdates.put(about, userAbout.getText().toString());

                userRef.updateChildren(userUpdates);
                Toast.makeText(ProfileMain.this, "Data changed", Toast.LENGTH_LONG).show();
                userNickname.setText("");
                userAbout.setText("");

            }


        });





    }
}
