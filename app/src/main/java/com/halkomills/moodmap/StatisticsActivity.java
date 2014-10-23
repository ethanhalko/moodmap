package com.halkomills.moodmap;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.example.halkomills.moodmap.R;
import com.halkomills.moodmap.Database.Mood;
import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Models.RecordedMoodDTO;
import com.halkomills.moodmap.MoodLog.LogActivity;
import com.halkomills.moodmap.Models.MoodDTO;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class StatisticsActivity extends Activity {


    private PieChart pie;

    private SQLiteDatabase db;
    final int[] colors = { Color.rgb(51, 181, 229), Color.rgb(255, 68, 68), Color.rgb(170, 102, 204)};

    //generatePieChart method
    //Accepts List of RecordedMoodDTO objects
    //Generates pie chart using param data
    private void generatePieChart(List<RecordedMoodDTO> moods){
        List<Segment> segments = new ArrayList<Segment>();
        Map<String, Integer> moodMap = new HashMap<String, Integer>();
        for( RecordedMoodDTO m : moods ){
            Integer count = moodMap.get(m.getMood());
            if( count == null ){
                moodMap.put(m.getMood(), 1 );
            }else{
                moodMap.put(m.getMood(), ++count );
            }
        }
        int i = 0;
        //make pie segments
        for( HashMap.Entry<String, Integer> m : moodMap.entrySet() ){
            pie.addSeries(new Segment(m.getKey(), m.getValue()), new SegmentFormatter(colors[i]));
            ++i;
        }
        pie.setBackgroundPaint(null);
        pie.setBorderPaint(null);
        PieRenderer renderer = pie.getRenderer(PieRenderer.class);
        renderer.setDonutSize((float) 0 / 100, PieRenderer.DonutMode.PERCENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.statistics, menu);

        // initialize our XYPlot reference:
        pie = (PieChart) findViewById(R.id.moodStatsPie);

        //get all yr moods
        MoodmapSqliteHelper sql = new MoodmapSqliteHelper(this);
        List<RecordedMoodDTO> moods = sql.getMoods();
        generatePieChart(moods);
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

        if(id == R.id.return_dashboard) {
            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(intent);
            finish();

        }

        if(id == R.id.log_view) {
            Intent intent = new Intent(getApplicationContext(),LogActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
