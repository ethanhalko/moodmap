package com.halkomills.moodmap;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.example.halkomills.moodmap.R;
import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Database.RecordedMood;
import com.halkomills.moodmap.Models.RecordedMoodDTO;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class StatisticsActivity extends Activity {

    private PieChart pie;
    private TextView tv;
    private LinearLayout rel;

    final int[] colors = { Color.rgb(51, 181, 229),
                            Color.rgb(255, 68, 68),
                            Color.rgb(170, 102, 204),
                            Color.rgb(153, 204, 2),
                            Color.rgb(255, 187, 51)};
    int getRandomColor(){
        Random r = new Random();
        int red = r.nextInt(255 - 0);
        int green = r.nextInt(255 - 0);
        int blue = r.nextInt(255 - 0);

        return Color.rgb(red, green, blue);
    }
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
        SegmentFormatter seg;
        //make pie segments
        for( HashMap.Entry<String, Integer> m : moodMap.entrySet() ){
            if( i < colors.length ){
                seg = new SegmentFormatter(colors[i], colors[i]);
                ++i;
            }else{
                int col = getRandomColor();
                seg = new SegmentFormatter(col, col);

            }
            pie.addSeries(new Segment(m.getKey(), m.getValue()), seg);
        }
        pie.setBackgroundPaint(null);
        pie.setBorderPaint(null);
        PieRenderer renderer = pie.getRenderer(PieRenderer.class);
        //renderer.setDonutSize((float) 0 / 100, PieRenderer.DonutMode.PERCENT);
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
//        MoodmapSqliteHelper sql = new MoodmapSqliteHelper(this);
        RecordedMood recordedMood = new RecordedMood(new MoodmapSqliteHelper(this).getReadableDatabase());
        List<RecordedMoodDTO> moods = recordedMood.getAll();
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

        }
        if(id == R.id.log_view) {
            Intent intent = new Intent(getApplicationContext(),LogActivity.class);
            startActivity(intent);
        }
        if(id == R.id.about_view) {
            Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
