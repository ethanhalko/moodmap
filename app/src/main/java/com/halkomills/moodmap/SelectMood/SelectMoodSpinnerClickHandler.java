package com.halkomills.moodmap.SelectMood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.halkomills.moodmap.CustomMoodActivity;
import com.halkomills.moodmap.DashboardActivity;
import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Database.RecordedMood;
import com.halkomills.moodmap.Models.MoodDTO;
import com.halkomills.moodmap.Models.RecordedMoodDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mills on 14-10-21.
 */
public class SelectMoodSpinnerClickHandler implements AdapterView.OnItemSelectedListener{

    Activity activity;
    List<MoodDTO> moodDTOs;
    private boolean firstSelect;


    public SelectMoodSpinnerClickHandler(Activity activity,List<MoodDTO> moodDTOs) {
        this.activity = activity;
        this.moodDTOs = moodDTOs;
        this.firstSelect = true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        if(firstSelect) {
            firstSelect = false;
            return;
        }

        String mood = (String)adapterView.getItemAtPosition(position);
        if(mood == DashboardActivity.CUSTOM_VALUE) {
            Log.d("CLICKED","GOT CUSTOM!");

                 Intent intent = new Intent(activity.getApplicationContext(),CustomMoodActivity.class);
                 ArrayList<String> sMoods = new ArrayList<String>();
                 for(MoodDTO moodDTO:moodDTOs) sMoods.add(moodDTO.getName());
                 intent.putStringArrayListExtra("moods", sMoods);
                 activity.startActivity(intent);
                 activity.finish();
            } else {

            MoodDTO moodDTO = moodDTOs.get(position);
            MoodmapSqliteHelper db = new MoodmapSqliteHelper(activity);

            RecordedMoodDTO recordedMoodDTO;
            recordedMoodDTO = new RecordedMoodDTO();
            recordedMoodDTO.setMoodId(moodDTO.getId());
            recordedMoodDTO.setTimestamp(new Date());
            recordedMoodDTO.setMood(mood);

            AlertDialog.Builder confirmDialog = createConfirmDialog(recordedMoodDTO);
            confirmDialog.show();

            RecordedMood recordedMood = new RecordedMood(db.getWritableDatabase());
            recordedMood.create(recordedMoodDTO);

            Log.d("CLICKED","Recorded mood!");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public AlertDialog.Builder createConfirmDialog(RecordedMoodDTO moodDTO) {
        String prompt = "You are " + moodDTO.getMood() + " at " + moodDTO.getDisplayTimestamp();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                .setTitle("Log Mood")
                .setMessage(prompt)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new ConfirmMoodPositiveClickListener(activity, moodDTO))
                .setNegativeButton(android.R.string.cancel,null);

        return alertDialog;
    }
}
