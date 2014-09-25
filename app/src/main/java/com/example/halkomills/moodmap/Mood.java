package com.example.halkomills.moodmap;
import java.util.Date;

/**
 * Created by mills on 2014-09-24.
 */
public class Mood {

    private String mood;
    private Date timestamp;

    public Mood(String mood) {
        mood = mood;
        timestamp = new Date();
    }

    public String getMood() {
        return mood;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
