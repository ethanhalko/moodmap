package com.halkomills.moodmap;

/**
 * Created by mills on 2014-09-24.
 */
public class MoodmapSchemaStrings {

    public static String[] createTables = {
            "CREATE TABLE moods ( id INTEGER PRIMARY KEY AUTOINCREMENT, mood TEXT,created_at TIMESTAMP)"
    };

    public static String[] dropTables = {
            "DROP TABLE moods"
    };
}
