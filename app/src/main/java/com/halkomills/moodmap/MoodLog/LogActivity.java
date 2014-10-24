package com.halkomills.moodmap.MoodLog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.halkomills.moodmap.R;
import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Database.RecordedMood;
import com.halkomills.moodmap.Models.RecordedMoodDTO;
import com.halkomills.moodmap.SelectMood.SelectMoodListViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends Activity {

    RecordedMood recordedMood;
    ListView logListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        MoodmapSqliteHelper db = new MoodmapSqliteHelper(this);
        recordedMood = new RecordedMood(db.getWritableDatabase());

        List<RecordedMoodDTO> moodDTOs = recordedMood.getAll();
        logListView = (ListView) findViewById(R.id.logListView);

        List<String> sMoods = new ArrayList<String>();
        for (RecordedMoodDTO m : moodDTOs) {
            sMoods.add(m.getLogString());
        }

        logListView = (ListView) findViewById(R.id.logListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sMoods);
        logListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
