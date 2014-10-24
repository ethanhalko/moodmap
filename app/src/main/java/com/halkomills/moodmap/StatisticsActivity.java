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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class StatisticsActivity extends Activity {


    private PieChart pie;
    private TextView tv;
    private LinearLayout rel;

    private SQLiteDatabase db;
    final int[] colors = { Color.rgb(51, 181, 229),
                            Color.rgb(255, 68, 68),
                            Color.rgb(170, 102, 204)};

    //reduce
    private Map<String, Integer> reduce(List<RecordedMoodDTO> moods){
        Map<String, Integer> moodMap = new HashMap<String, Integer>();
        for( RecordedMoodDTO m : moods ){
            Integer count = moodMap.get(m.getMood());
            if( count == null ){
                moodMap.put(m.getMood(), 1 );
            }else{
                moodMap.put(m.getMood(), ++count );
            }
        }
        return moodMap;
    }


    //generatePieChart method
    //Accepts List of RecordedMoodDTO objects
    //Generates pie chart using param data
    private void generatePieChart(Map<String, Integer> moodMap ){
        List<Segment> segments = new ArrayList<Segment>();
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
    private void listMoods( Map<String, Integer> moodMap){
        int total = 0;
        rel = (LinearLayout)findViewById(R.id.percents);
        for( HashMap.Entry<String, Integer> m : moodMap.entrySet()){
            total += m.getValue();
        }
        for( HashMap.Entry<String, Integer> m : moodMap.entrySet()){

            double percent = (double)m.getValue()/total*100;
            tv = new TextView(this);
            tv.setText("You were " + m.getKey()+ " " + (int)Math.round(percent) + "%");
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setPadding(0, 30, 0, 0);
            rel.addView(tv);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        pie = (PieChart) findViewById(R.id.moodStatsPie);
        rel = (LinearLayout)findViewById(R.id.percents);
        //get all yr moods
        MoodmapSqliteHelper sql = new MoodmapSqliteHelper(this);
        List<RecordedMoodDTO> moods = sql.getMoods();
        Map<String, Integer> moodMap = reduce(moods);

        if( !moods.isEmpty() ){
            listMoods(moodMap);
            generatePieChart(moodMap);
        }
        else{
            pie.setVisibility(View.GONE);
            tv = new TextView(this);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setPadding(0, 400, 0, 0);
            tv.setText("You haven't recorded any moods!");
            rel.addView(tv);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.statistics, menu);

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
