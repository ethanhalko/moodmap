package com.halkomills.moodmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public long createMood(Mood mood) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", mood.getName());

        /*Date utilDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        cal.set(Calendar.MILLISECOND, 0);
        values.put("created_at",utilDate.getTime());*/

        long r = db.insert("moods", null, values);
        db.close();

        return r;
    }

    public int updateMood(Mood mood) {
        return -1;
    }

    public int deleteMood(int id) {

        return -1;
    }

    public Mood getMood(int id) {

        Mood mood = new Mood("lol");

        return mood;
    }

    public List<Mood> getAll() {
        Cursor cursor;
        List<Mood> moods = new ArrayList<Mood>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            //String query = "SELECT m.id, m.name FROM moods AS m JOIN recorded_moods AS rm ON m.id = rm.id GROUP BY m.id ORDER BY count(rm.id) DESC;";
            cursor = db.rawQuery("SELECT * FROM moods", null);

            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String m = cursor.getString(cursor.getColumnIndex("name"));

                    Mood mood = new Mood();
                    mood.setId(id);
                    mood.setMood(m);


                    cursor.moveToNext();
                    moods.add(mood);
                }
            }
        } catch (Exception e) {

            Log.d("error",e.getMessage());
        }



        return moods;
    }

    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM moods");
        return true;
    }

}
