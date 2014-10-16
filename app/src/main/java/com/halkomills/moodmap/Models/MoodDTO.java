package com.halkomills.moodmap.Models;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MoodDTO {

    private int id;
    private String mood;
    private boolean isDefault;

    public MoodDTO() {}
    public MoodDTO(String mood) {
        this.mood = mood;
        this.isDefault = false;
    }

    public MoodDTO(int id, String mood, boolean isDefault) {
        this.id = id;
        this.mood = mood;
        this.isDefault = isDefault;
    }

    public int getId() { return id; }
    public String getName() { return mood; }

    public void setId(int id) { this.id = id;}
    public void setMood(String mood) { this.mood = mood; }

}
