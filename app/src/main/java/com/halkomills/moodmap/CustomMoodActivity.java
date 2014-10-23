package com.halkomills.moodmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.halkomills.moodmap.R;
import com.halkomills.moodmap.Database.Mood;
import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Database.RecordedMood;
import com.halkomills.moodmap.Models.MoodDTO;
import com.halkomills.moodmap.Models.RecordedMoodDTO;

import java.util.List;


public class CustomMoodActivity extends Activity {

    List<String> sMoods;
    AutoCompleteTextView textView;
    MoodmapSqliteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mood);

        sMoods = getIntent().getExtras().getStringArrayList("moods");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, sMoods);
        textView = (AutoCompleteTextView)
                findViewById(R.id.customMoodAutoCompleteTextView);
        textView.setAdapter(adapter);
        db = new MoodmapSqliteHelper(this);

        Button logButton = (Button)findViewById(R.id.logCustomMoodButton);
        final Activity activity = this;
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = textView.getText().toString();
                if(sMoods.contains(s)) {
                    Log.d("CUSTOMMOOD", "Existing mood selected, just logging");
                    Mood mood = new Mood(db.getReadableDatabase());
                    MoodDTO moodDTO = mood.getByName(s);
                    RecordedMood recordedMood = new RecordedMood(db.getWritableDatabase());
                    recordedMood.create(moodDTO.getId());

                } else {
                    Log.d("CUSTOMMOOD","New mood selected,inserting then logging");

                    MoodDTO moodDTO = new MoodDTO(s);
                    Mood mood = new Mood(db.getWritableDatabase());
                    long moodId = mood.create(moodDTO);
                    RecordedMood recordedMood = new RecordedMood(db.getWritableDatabase());
                    recordedMood.create((int)moodId);
                }

                //Go back to dashboard
                Intent intent = new Intent(activity,DashboardActivity.class);
                activity.startActivity(intent);
                activity.finish();

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_mood, menu);
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
