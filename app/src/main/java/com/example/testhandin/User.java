package com.example.testhandin;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class User {


    public String email;
    public String nickname;
    public String about;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email,String nickname, String about) {
        this.nickname = nickname;
        this.email = email;
        this.about = about;
    }

}