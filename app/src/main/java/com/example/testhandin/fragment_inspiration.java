package com.example.testhandin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

@SuppressLint("ValidFragment")
public class fragment_inspiration extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_inspiration, container, false);


        Button chill = view.findViewById(R.id.chillBtn);
        Button pop = view.findViewById(R.id.popBtn);
        Button oldschool = view.findViewById(R.id.oldSchoolBtn);
        Button aRock = view.findViewById(R.id.alternativeRockBtn);
        Button trap = view.findViewById(R.id.trapBtn);



        chill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.youtube.com/watch?v=ah8e7RZ0tWU&list=RDah8e7RZ0tWU&start_radio=1";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String url = "https://www.youtube.com/watch?v=xCsoNnXy5ys&list=PLOKcKTBaXJlp7qD0199Unek0LYjb2yn51&index=1";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });


        oldschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=R4nAvDXpvHk&list=RDR4nAvDXpvHk&start_radio=1&t=0";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        aRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=GVL6JA7UMc8&list=PLOKcKTBaXJlqROhwhFPuFqeUnZYHRONLd";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        trap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=hCEpvWUKDOE&list=PLCQa53megBt6RfHnpBdlqYrertWS44esK";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        return view;
    }





}
