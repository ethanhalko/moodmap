package com.halkomills.moodmap.SelectMood;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import com.halkomills.moodmap.Models.MoodDTO;

import java.util.Date;
import java.util.List;


public class SelectMoodListViewItemClickListener implements AdapterView.OnItemClickListener {

    private List<MoodDTO> moodDTOs;
    Activity activity;

    public SelectMoodListViewItemClickListener(Activity activity, List<MoodDTO> moodDTOs) {
        this.moodDTOs = moodDTOs;
        this.activity = activity;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //get selected mood
        MoodDTO moodDTO = moodDTOs.get(position);


    }


}
