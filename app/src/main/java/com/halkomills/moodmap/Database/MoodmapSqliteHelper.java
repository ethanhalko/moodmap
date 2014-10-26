package com.halkomills.moodmap.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.halkomills.moodmap.Models.MoodDTO;

/**
* Helper class for maintaining the database
*/
public class MoodmapSqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Moodmap";

    public MoodmapSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create all tables
        String[] createTables = MoodmapSchemaStrings.createTables;
        for(String table : createTables) {
            db.execSQL(table);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        //drop all tables then recreate database
        String[] dropTables = MoodmapSchemaStrings.dropTables;
        for(String table : dropTables) {
            db.execSQL(table);
        }

        this.onCreate(db);
    }

    /**
     * Check to see if the default moods have been inserted into the
     * database
     * @return true if count > 0, false otherwise
     */
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

    /**
     * Inserts a list of moods into the database
     * @param moods
     */
    public void insertDefaultMoods(String[] moods) {
        for(String s: moods) {
            Mood mood = new Mood(this.getWritableDatabase());
            MoodDTO moodDTO = new MoodDTO(s);
            mood.create(moodDTO);
        }
    }

}
