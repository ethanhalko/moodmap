package com.halkomills.moodmap;
import com.example.halkomills.moodmap.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SelectMoodActivity extends Activity {

    MoodmapSqliteHelper db;
    List<Mood> moods;
    ListView moodListView;

    SelectMoodActivity wut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_select_mood);
        wut = this;
        db = new MoodmapSqliteHelper(this);
        //db.deleteAll();

        moods = db.getAll();
        List<String> sMoods = new ArrayList<String>();
        for(Mood m : moods) { sMoods.add(m.getName()); }

        moodListView = (ListView)findViewById(R.id.moodListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sMoods);
        moodListView.setAdapter(adapter);

        moodListView.setOnItemClickListener(new SelectMoodListViewItemClickListener(this,moods));
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_mood, menu);
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
        } else if(id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
