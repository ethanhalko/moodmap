package com.halkomills.moodmap.Models;


public class MoodDTO {

    private int id;
    private String name;

    public MoodDTO() {}
    public MoodDTO(String name) {
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setId(int id) { this.id = id;}
    public void setName(String name) { this.name = name; }

}
