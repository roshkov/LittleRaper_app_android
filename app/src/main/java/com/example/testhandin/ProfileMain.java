package com.example.testhandin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileMain extends AppCompatActivity {

    TextView userEmail;
    Button userSignOut;
    Button userUpdate;
    TextView userNickname;
    TextView userAbout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

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
                

            }


        });





    }
}
