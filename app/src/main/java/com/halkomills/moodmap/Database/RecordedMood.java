package com.halkomills.moodmap.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.halkomills.moodmap.Models.MoodDTO;
import com.halkomills.moodmap.Models.RecordedMoodDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 *  Contains functions for accessing the mood table
 *  in the sqlite database
 */
public class RecordedMood {

    private SQLiteDatabase db;

    public RecordedMood(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Inserts a RecordedMood
     * @param moodId
     * @return the generated id
     */
    public long create(int moodId) {
        RecordedMoodDTO recordedMoodDTO = new RecordedMoodDTO(moodId);
        return create(recordedMoodDTO);
    }

    /**
     * Inserts a RecordedMood
     * @param recordedMoodDTO
     * @return the generated id
     */
    public long create(RecordedMoodDTO recordedMoodDTO) {

        long result = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("mood_id", recordedMoodDTO.getMoodId());

            Date utilDate = new Date();
            values.put("created_at", utilDate.getTime());

            result = db.insert("recorded_moods", null, values);
            db.close();
        } catch(Exception e) {
            Log.e("recorded_mood","CREATE: " + e.getMessage());
        }
        return result;
    }

    /**
     * Gets the last mood recorded
     * @return RecordedMoodDTO
     */
    public RecordedMoodDTO getLatestMood() {

        Cursor cursor;

        try {
            cursor = db.rawQuery("SELECT * FROM recorded_moods AS rm JOIN moods AS m ON rm.mood_id = m.id ORDER BY created_at DESC LIMIT 1", null);

            if(cursor.moveToFirst()) {
                return getMoodFromCursor(cursor);
            }

        } catch(Exception e) {
            Log.d("ERROR",e.getMessage());
        }

        return null;
    }


    /**
     * Returns the number of recorded moods
     * @return int
     */
    public int count() {
        Cursor cursor;
        int result = -1;

        try {
           cursor = db.rawQuery("SELECT COUNT(*) FROM recorded_moods", null);
            if(cursor.moveToFirst()) {
                result = cursor.getInt(cursor.getColumnIndex("COUNT(*)"));
            }

        }catch(Exception e) {
            Log.d("error",e.getMessage());
        }

        return result;
    }

    /**
     * Gets all recorded moods
     * @return List<RecordedMoodDTO>
     */
     public List<RecordedMoodDTO> getAll() {

        List<RecordedMoodDTO> recordedMoods = new ArrayList<RecordedMoodDTO>();
        Cursor cursor;

        try {

            cursor = db.rawQuery("SELECT * FROM recorded_moods AS rm JOIN moods AS m ON rm.mood_id = m.id ORDER BY rm.created_at DESC", null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    recordedMoods.add(getMoodFromCursor(cursor));
                    cursor.moveToNext();
                }
            }

        } catch(Exception e) {
            Log.e("recorded_mood","GET ALL: " + e.getMessage());
        }

        return recordedMoods;
    }

    /**
     * Converts a database row into a RecordedMoodDTO
     * @param cursor
     * @return RecordedMoodDTO
     */
    private RecordedMoodDTO getMoodFromCursor(Cursor cursor) {
        //get database results
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        int mood_id = cursor.getInt(cursor.getColumnIndex("mood_id"));
        String mood = cursor.getString(cursor.getColumnIndex(("name")));

        String rawStamp = cursor.getString(cursor.getColumnIndex("created_at"));
        long stamp = Long.parseLong(rawStamp);
        Date d = new Date(stamp);

        //Setup recorded mood dto
        return new RecordedMoodDTO(id,mood_id,d,mood);
    }
}
