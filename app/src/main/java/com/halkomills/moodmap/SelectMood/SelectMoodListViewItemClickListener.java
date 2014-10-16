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

        Date date = new Date();
        AlertDialog.Builder confirmDialog = createConfirmDialog(moodDTO,date);
        confirmDialog.show();
    }

    public AlertDialog.Builder createConfirmDialog(MoodDTO moodDTO,Date date) {
        String prompt = "You are " + moodDTO.getName() + " at " + date.toString();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                .setTitle("Log Mood")
                .setMessage(prompt)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, null)
                .setPositiveButton(android.R.string.yes,new ConfirmMoodPositiveClickListener(activity, moodDTO,date))
                .setNegativeButton(android.R.string.no, new ConfirmMoodNegativeClickListener(moodDTO));

        return alertDialog;
    }

}
