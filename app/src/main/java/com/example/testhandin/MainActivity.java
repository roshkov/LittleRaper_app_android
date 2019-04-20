package com.example.testhandin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

   // DatabaseReference myRef;
    private static final String TAG = "MainActivity";

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    private void writeNewUser(String userId, String name, String email) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        User user = new User(email);
        mDatabase.child("users").child(userId).setValue(user);


    }




    public void sendData(View v)
    {

        EditText tv = (EditText) findViewById(R.id.messageTextView);
        String mes = (String) tv.getText().toString();
        TextView tvtest = (TextView) findViewById(R.id.textViewTEST);
        tvtest.setText(mes);


        writeNewUser("22", "Jury", "suki@gmail.com");


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference();




//
//        myRef.setValue(mes);
//
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//                TextView tv3 = (TextView) findViewById(R.id.textView3);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });



    }



}



