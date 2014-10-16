package com.halkomills.moodmap.Database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mills on 2014-10-15.
 */
public class Mood {

    private SQLiteDatabase db;

    public Mood(SQLiteDatabase db) {

        this.db = db;
    }

}
