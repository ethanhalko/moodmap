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

    public RecordedMoodDTO() {}

    public int getId() { return id; }
    public String getMood() { return mood; }
    public int getMoodId() { return moodId; }

    public String fromNow() {
        Date mDate = new Date();

        long diff = mDate.getTime() - timestamp.getTime();

        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;

        String result = "";

        if(weeks > 0) {
            result = weeks + " " + pluralize("week",weeks) + " ago";
        } else if(days > 0 ) {
            result = days + " " + pluralize("day",days)+ " ago";
        } else if(hours > 0) {
            result = hours + " " + pluralize("hour",hours) + " ago";
        } else if(minutes > 0 ) {
            result = minutes + " " + pluralize("minute",minutes) + " ago";
        } else if(seconds > 0 ) {
            result = seconds + " " + pluralize("second",seconds) + " ago";
        } else {
            result = "a few moments ago";
        }

        return result;
    }

    private String pluralize(String word,long num) {
       return (num > 1 ) ? word + "s" : word;
    }

    public String getDisplayTimestamp() {
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yy hh:mm:ss a");
        return f.format(timestamp);
    }
    public void setId(int id) { this.id = id;}
    public void setMood(String mood) { this.mood = mood; }
    public void setTimestamp(Date ts) { timestamp = ts; }
    public void setMoodId(int moodId) { this.moodId = moodId; }

    public String getLogString() {
        return mood + " logged " + fromNow() + "\n" + getDisplayTimestamp();
    }
    public String toString() {
        return id + " , " + mood + ", " + timestamp.toString();
    }
    String s = mood;
    public String getLatestMoodString() {
        return "You were " + mood.toLowerCase() + " " + fromNow();
    }
}
