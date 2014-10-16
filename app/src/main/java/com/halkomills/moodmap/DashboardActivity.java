package com.halkomills.moodmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.halkomills.moodmap.R;
import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Models.MoodDTO;
import com.halkomills.moodmap.SelectMood.SelectMoodActivity;


public class DashboardActivity extends Activity {

    MoodmapSqliteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //deleteDatabase("Moodmap");

        db = new MoodmapSqliteHelper(this);
        db.deleteAll();

        insertABunchOfDumbMoods();

        Button statsButton = (Button)findViewById(R.id.statisticsButton);
        statsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                 Intent intent = new Intent(getApplicationContext(),StatisticsActivity.class);
                 startActivity(intent);
                 finish();
                 return true;
            }
        });

        Button moodButton = (Button)findViewById(R.id.openMoodsButton);
        moodButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                 Intent intent = new Intent(getApplicationContext(),SelectMoodActivity.class);
                 startActivity(intent);
                 finish();
                 return true;
            }
        });

    }

    private void insertABunchOfDumbMoods() {

        String[] moods = { "Mad","Sad","Glad" };
        for(String s: moods) {
            MoodDTO moodDTO = new MoodDTO(s);
            db.createMood(moodDTO);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
