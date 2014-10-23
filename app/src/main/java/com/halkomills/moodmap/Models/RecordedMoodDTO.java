package com.halkomills.moodmap.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordedMoodDTO {

    private int id;



    private int moodId;
    private String mood;
    private Date timestamp;

    public RecordedMoodDTO(int moodId) {
        this.moodId = moodId;
        this.timestamp = new Date();
    }
    public RecordedMoodDTO(int moodId, String mood, Date ts) {
        this.moodId = moodId;
        this.mood = mood;
        this.timestamp = ts;
    }

    public RecordedMoodDTO(int id, int moodId, Date d, String mood) {
        this.id = id;
        this.moodId = moodId;
        this.timestamp = d;
        this.mood = mood;
    }

    public RecordedMoodDTO() {

    }

    public int getId() { return id; }
    public String getMood() {
        return mood;
    }
    public Date getTimestamp() { return timestamp; }
    public int getMoodId() { return moodId;}

    public String getDisplayTimestamp() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-DD");
        return f.format(timestamp);
    }
    public void setId(int id) { this.id = id;}
    public void setMood(String mood) { this.mood = mood; }
    public void setTimestamp(Date ts) { timestamp = ts; }
    public void setMoodId(int moodId) { this.moodId = moodId; }

    public String toString() {
        return id + " , " + mood + ", " + timestamp.toString();
    }

    public String getLatestMoodString() {
        return "Last mood: " + mood + " at " + getDisplayTimestamp();
    }
}
