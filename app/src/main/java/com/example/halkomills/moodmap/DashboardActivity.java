package com.example.halkomills.moodmap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class DashboardActivity extends Activity {

    MoodmapSqliteHelper db;
    ListView moodListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = new MoodmapSqliteHelper(this);

        db.deleteAll();
        insertABunchOfDumbMoods();
        List<Mood> moods = db.getAll();
        List<String> sMoods = new ArrayList<String>();
        for(Mood m : moods) { sMoods.add(m.getMood()); }

        moodListView = (ListView)findViewById(R.id.previousMoodsList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sMoods);
        moodListView.setAdapter(adapter);

    }

    private void insertABunchOfDumbMoods() {

        String[] moods = { "Mad","Sad","Glad" };

        for(String s: moods) {
            Mood mood = new Mood(s);
            db.createMood(mood);
        }
    }

    public String generateMoodString(Mood m) {
        return "You are " + m.getMood() + " at " + m.getTimestamp();
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
