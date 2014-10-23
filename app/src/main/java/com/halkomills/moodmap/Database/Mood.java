package com.halkomills.moodmap.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.halkomills.moodmap.Models.MoodDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mills on 2014-10-15.
 */
public class Mood {

    private SQLiteDatabase db;

    public Mood(SQLiteDatabase db) {

        this.db = db;
    }

    public long create(MoodDTO moodDTO) {

        ContentValues values = new ContentValues();
        values.put("name", moodDTO.getName());

        long r = db.insert("moods", null, values);
        db.close();

        return r;
    }


    public List<String> getAllNames() {
        List<MoodDTO> moodDTOs = getAll();
        List<String> names = new ArrayList<String>();

        for(MoodDTO mood : moodDTOs) {
            names.add(mood.getName());
        }

        return names;
    }

    public List<MoodDTO> getAll() {

        Cursor cursor;
        List<MoodDTO> moodDTOs = new ArrayList<MoodDTO>();
        try {
            //way to order based on most frequently used?
            //String query = "SELECT m.id, m.name FROM moods AS m JOIN recorded_moods AS rm ON m.id = rm.id GROUP BY m.id ORDER BY count(rm.id) DESC;";
            cursor = db.rawQuery("SELECT * FROM moods", null);

            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String m = cursor.getString(cursor.getColumnIndex("name"));

                    MoodDTO moodDTO = new MoodDTO();
                    moodDTO.setId(id);
                    moodDTO.setName(m);

                    cursor.moveToNext();
                    moodDTOs.add(moodDTO);
                }
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        return moodDTOs;
    }

    public MoodDTO getByName(String name) {
        Cursor cursor;
        try {
            //way to order based on most frequently used?
            //String query = "SELECT m.id, m.name FROM moods AS m JOIN recorded_moods AS rm ON m.id = rm.id GROUP BY m.id ORDER BY count(rm.id) DESC;";
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
