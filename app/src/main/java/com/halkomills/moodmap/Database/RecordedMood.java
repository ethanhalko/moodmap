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
 * Created by mills on 2014-10-15.
 */
public class RecordedMood {

    private SQLiteDatabase db;

    public RecordedMood(SQLiteDatabase db) {
        this.db = db;
    }

    public long create(RecordedMoodDTO recordedMoodDTO) {
        long result = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("mood_id", recordedMoodDTO.getMoodId());

            Date utilDate = new Date();
/*      Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        cal.set(Calendar.MILLISECOND, 0);*/
            values.put("created_at", utilDate.getTime());

            result = db.insert("recorded_moods", null, values);
            db.close();
        } catch(Exception e) {
            Log.e("recorded_mood","CREATE: " + e.getMessage());
        }
        return result;
    }

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

    public List<RecordedMoodDTO> getAll() {

        List<RecordedMoodDTO> recordedMoods = new ArrayList<RecordedMoodDTO>();
        Cursor cursor;

        try {

            cursor = db.rawQuery("SELECT * FROM recorded_moods AS rm JOIN moods AS m ON rm.mood_id = m.id", null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    String[] cols = cursor.getColumnNames();
                    recordedMoods.add(getMoodFromCursor(cursor));

                    cursor.moveToNext();
                }
            }

        } catch(Exception e) {
            Log.e("recorded_mood","GET ALL: " + e.getMessage());
        }

        return recordedMoods;
    }

    private RecordedMoodDTO getMoodFromCursor(Cursor cursor) {
        //get database results
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        int mood_id = cursor.getInt(cursor.getColumnIndex("mood_id"));
        String mood = cursor.getString(cursor.getColumnIndex(("name")));

        String rawStamp = cursor.getString(cursor.getColumnIndex("created_at"));
        long stamp = Long.parseLong(rawStamp);
        Date d = new Date(stamp);

        //Setup recorded mood dto
        RecordedMoodDTO moodDTO = new RecordedMoodDTO();
        moodDTO.setId(id);
        moodDTO.setMoodId(mood_id);
        moodDTO.setTimestamp(d);
        moodDTO.setMood(mood);

        return moodDTO;
    }
}
