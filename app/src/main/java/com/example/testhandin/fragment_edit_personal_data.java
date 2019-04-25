package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class fragment_edit_personal_data extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference userRef;
    EditText nickET;
    EditText aboutET;
    Button saveBtn;;
    String TAG = "mylog========";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_personal_data, container, false);
        ImageView avatar = view.findViewById(R.id.avatarProfile);
        avatar.setImageDrawable(getResources().getDrawable(R.drawable.sample_prof_pic));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser==null){
        Log.i(TAG,"null");}
        else
        {Log.i(TAG,"not null");}
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid().toString());

        saveBtn = view.findViewById(R.id.saveChangesBtn);
        TextView myemail = view.findViewById(R.id.actualEmail);
        myemail.setText(firebaseUser.getEmail());

         nickET = view.findViewById(R.id.nicknameEditText);
         aboutET = view.findViewById(R.id.aboutEditText);




        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                nickET.setText(user.nickname);
                aboutET.setText(user.about);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference userRef = mDatabase.child("users");

                Map<String, Object> userUpdates = new HashMap<>();
                String nickname = firebaseUser.getUid().toString()+"/nickname";
                String about = firebaseUser.getUid().toString()+"/about";

                userUpdates.put(nickname, nickET.getText().toString());
                userUpdates.put(about, aboutET.getText().toString());

                userRef.updateChildren(userUpdates);
                Toast.makeText(getActivity(), "Data changed", Toast.LENGTH_LONG).show();


            }
        });



                return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



//

}
