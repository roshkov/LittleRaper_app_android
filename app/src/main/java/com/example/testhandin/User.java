package com.example.testhandin;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties

public class User {


    public String email;
    public String nickname;
    public String about;
    public ArrayList<Song> songList;
    public String userPic;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email)
    {
        this.email = email;
        songList = new ArrayList<Song>();
    }


    public User(String email,String nickname, String about)
    {
        this.nickname = nickname;
        this.email = email;
        this.about = about;
        songList = new ArrayList<Song>();
    }

}


