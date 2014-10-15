package com.halkomills.moodmap;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordedMood {

    private int id;
    private String mood;
    private Date timestamp;

    public RecordedMood() {}
    public RecordedMood(String mood) {
        this.mood = mood;
        timestamp = new Date();
    }

    public RecordedMood(int id, String mood, Date ts) {
        this.id = id;
        this.mood = mood;
        this.timestamp = ts;
    }

    public int getId() { return id; }
    public String getMood() {
        return mood;
    }
    public Date getTimestamp() { return timestamp; }

    public String getDisplayTimestamp() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-DD");
        return f.format(timestamp);
    }
    public void setId(int id) { this.id = id;}
    public void setMood(String mood) { this.mood = mood; }
    public void setTimestamp(Date ts) { timestamp = ts; }


    public String toString() {
        return id + " , " + mood + ", " + timestamp.toString();
    }
}
