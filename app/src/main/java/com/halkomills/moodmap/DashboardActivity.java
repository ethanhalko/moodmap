package com.halkomills.moodmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.halkomills.moodmap.R;
import com.halkomills.moodmap.Database.Mood;
import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Database.RecordedMood;
import com.halkomills.moodmap.Models.MoodDTO;
import com.halkomills.moodmap.Models.RecordedMoodDTO;
import com.halkomills.moodmap.SelectMood.SelectMoodSpinnerClickHandler;

import java.util.ArrayList;
import java.util.List;


public class DashboardActivity extends Activity {

    MoodmapSqliteHelper db;
    TextView latestMoodText;
    Spinner selectMoodSpinner;
    List<MoodDTO> moods;
    List<String> sMoods;
    public static final String CUSTOM_VALUE = "Custom...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = new MoodmapSqliteHelper(this);

        //if the database has no moods, insert the default ones
        if(!db.hasDefaultMoods()) {
            Log.d("DATABASE", "No default moods detected. Inserting defaults.");
            db.insertDefaultMoods(getResources().getStringArray(R.array.default_moods));
        }

        setLatestMoodText();

        //get data for spinner
        Mood mood = new Mood(db.getReadableDatabase());
        moods = mood.getAllOrderedByFrequency();
        sMoods = new ArrayList<String>();

        for(MoodDTO moodDTO : moods) {
            sMoods.add(moodDTO.getName());
        }

        //add custom option
        sMoods.add(CUSTOM_VALUE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sMoods);
        selectMoodSpinner = (Spinner)findViewById(R.id.selectMoodSpinner);
        selectMoodSpinner.setAdapter(adapter);
        selectMoodSpinner.setOnItemSelectedListener(new SelectMoodSpinnerClickHandler(this,moods));

        //bind statsButton event handler
        Button statsButton = (Button)findViewById(R.id.statisticsButton);
        statsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                 Intent intent = new Intent(getApplicationContext(),StatisticsActivity.class);
                 startActivity(intent);
                 return true;
            }
        });


    }

    /**
     *  Gets the latest recorded mood
     *  and sets it's content to a label on the dashboard
     */
    public void setLatestMoodText() {

        latestMoodText = (TextView)findViewById(R.id.latestMoodText);
        RecordedMood recordedMood = new RecordedMood(db.getReadableDatabase());
        RecordedMoodDTO recordedMoodDTO = recordedMood.getLatestMood();

        if(recordedMoodDTO == null) {
            latestMoodText.setText("You haven't logged any moods yet.");
        } else {
            latestMoodText.setText(recordedMoodDTO.getLatestMoodString());
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

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.log_view) {
            Intent intent = new Intent(getApplicationContext(),LogActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        if(id == R.id.about_view) {
            Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
