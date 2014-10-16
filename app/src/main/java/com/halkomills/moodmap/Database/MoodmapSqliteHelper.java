package com.halkomills.moodmap.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    public long createMood(MoodDTO moodDTO) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", moodDTO.getName());

        /*Date utilDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        cal.set(Calendar.MILLISECOND, 0);
        values.put("created_at",utilDate.getTime());*/

        long r = db.insert("moods", null, values);
        db.close();

        return r;
    }

    public int updateMood(MoodDTO moodDTO) {
        return -1;
    }

    public int deleteMood(int id) {

        return -1;
    }

    public MoodDTO getMood(int id) {

        MoodDTO moodDTO = new MoodDTO("lol");

        return moodDTO;
    }

    public List<MoodDTO> getAll() {
        Cursor cursor;
        List<MoodDTO> moodDTOs = new ArrayList<MoodDTO>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            //String query = "SELECT m.id, m.name FROM moods AS m JOIN recorded_moods AS rm ON m.id = rm.id GROUP BY m.id ORDER BY count(rm.id) DESC;";
            cursor = db.rawQuery("SELECT * FROM moods", null);

            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String m = cursor.getString(cursor.getColumnIndex("name"));

                    MoodDTO moodDTO = new MoodDTO();
                    moodDTO.setId(id);
                    moodDTO.setMood(m);


                    cursor.moveToNext();
                    moodDTOs.add(moodDTO);
                }
            }
        } catch (Exception e) {

            Log.d("error",e.getMessage());
        }

        return moodDTOs;
    }

    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM moods");
        return true;
    }

}
