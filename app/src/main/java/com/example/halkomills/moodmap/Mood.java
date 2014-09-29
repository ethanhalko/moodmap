package com.example.halkomills.moodmap;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by mills on 2014-09-24.
 */
public class Mood {

    private int id;
    private String mood;
    private Date timestamp;


    public Mood() {}
    public Mood(String mood) {
        this.mood = mood;
        timestamp = new Date();
    }

    public Mood(int id, String mood, Date ts) {
        this.id = id;
        this.mood = mood;
        this.timestamp = ts;
    }

    public int getId() { return id; }
    public String getMood() {
        return mood;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setId(int id) { this.id = id;}
    public void setMood(String mood) { this.mood = mood; }
    public void setTimestamp(Date ts) { timestamp = ts;}


    public String toString() {
        return id + " , " + mood + ", " + timestamp.toString();
    }
}
