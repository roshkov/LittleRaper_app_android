package com.example.testhandin;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Song {

   public String songID;
   public String songName;
   public String songLyrics;


    public Song() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

//    public Song(String songName)
//    {
//        this.songName = songName;
//    }

    public Song(String songName,String songLyrics){
        this.songName = songName;
        this.songLyrics = songLyrics;
    }


    public Song(String songID, String songName, int num){
        this.songID = songID;
        this.songName = songName;
    }

    //for adding only
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("songName", songName);
        result.put("songLyrics", songLyrics);

        return result;
    }

}
