package com.halkomills.moodmap.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.halkomills.moodmap.Models.MoodDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mills on 2014-09-24.
 */
public class MoodmapSqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Moodmap";

    public MoodmapSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] createTables = MoodmapSchemaStrings.createTables;
        for(String table : createTables) {
            db.execSQL(table);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        String[] dropTables = MoodmapSchemaStrings.dropTables;
        for(String table : dropTables) {
            db.execSQL(table);
        }

        this.onCreate(db);
    }

    public boolean hasDefaultMoods() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        boolean result = false;

        try {
           cursor = db.rawQuery("SELECT COUNT(*) FROM moods", null);
            if(cursor.moveToFirst()) {
                int val = cursor.getInt(cursor.getColumnIndex("COUNT(*)"));
                result = val > 0;
            }

        }catch(Exception e) {
            Log.d("error",e.getMessage());
        }
        return result;
    }

    public void insertDefaultMoods() {

        String[] moods = { "Mad","Sad","Glad" };
        for(String s: moods) {
            Mood mood = new Mood(this.getWritableDatabase());
            MoodDTO moodDTO = new MoodDTO(s);
            mood.create(moodDTO);
        }
    }
}
