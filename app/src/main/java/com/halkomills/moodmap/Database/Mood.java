package com.halkomills.moodmap.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.halkomills.moodmap.Models.MoodDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *  Contains functions for accessing the mood table
 *  in the sqlite database
 */
public class Mood {

    private SQLiteDatabase db;

    public Mood(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Inserts a new mood
     * @param moodDTO
     * @return the generated id
     */
    public long create(MoodDTO moodDTO) {

        ContentValues values = new ContentValues();
        values.put("name", moodDTO.getName());

        long r = db.insert("moods", null, values);
        db.close();

        return r;
    }

    /**
     * Gets all moods, ordered by popularity
     * @return All moods
     */
    public List<MoodDTO> getAllOrderedByFrequency() {

        Cursor cursor;
        ArrayList<MoodDTO> moods = new ArrayList<MoodDTO>();
        try {
            String query = "";

            //check to see if any moods have been recorded yet, set query depending on results
            RecordedMood recordedMood = new RecordedMood(db);
            if(recordedMood.count() == 0)
                query = "SELECT * FROM moods";
            else
                query = "SELECT m.id, m.name FROM moods AS m JOIN recorded_moods AS rm ON m.id = rm.id GROUP BY m.id ORDER BY count(rm.id) DESC";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                while(!cursor.isAfterLast()) {

                    //convert row to DTO
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String n = cursor.getString(cursor.getColumnIndex("name"));
                    moods.add(new MoodDTO(id, n));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        return moods;
    }


    /**
     * Finds a single mood by name
     * @param name of mood
     * @return MoodDTO or null if not found
     */
    public MoodDTO getByName(String name) {
        Cursor cursor;
        try {
            cursor = db.rawQuery("SELECT * FROM moods WHERE name = \"" + name + "\"", null);

            if (cursor.moveToFirst()) {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String n = cursor.getString(cursor.getColumnIndex("name"));

                    return new MoodDTO(id,n);
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        return null;
    }
}
