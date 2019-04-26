package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Pop_changetitle extends AppCompatActivity {

    EditText sNameET;
    Button changeBtn;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_changetitle);

        sNameET = findViewById(R.id.songTitleet);
        changeBtn = findViewById(R.id.changeTitlebtn);
        Intent intent = getIntent();
        String thisSongName = intent.getExtras().getString("thisSongName");

        sNameET.setText(thisSongName);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.7),(int) (height*0.4));


        changeBtn.setOnClickListener(new View.OnClickListener(){
            private String TAG = "popChangeTitle====================";

            @Override
            public void onClick(View v) {


                String res = sNameET.getText().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("resultName", res);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
            });

    }

}
