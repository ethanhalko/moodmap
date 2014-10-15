package com.halkomills.moodmap;

/**
 * Created by mills on 2014-09-24.
 */
public class MoodmapSchemaStrings {

    public static String[] createTables = {
            "CREATE TABLE moods ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, default_mood INTEGER, created_at TIMESTAMP)",
            "CREATE TABLE recorded_moods (id INTEGER PRIMARY KEY AUTOINCREMENT, mood_id INTEGER, created_at TIMESTAMP)"
    };

    public static String[] dropTables = {
            "DROP TABLE moods",
            "DROP TABLE recorded_moods"
    };
}
