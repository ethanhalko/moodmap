package com.halkomills.moodmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SelectMoodListViewItemClickListener implements AdapterView.OnItemClickListener {

    private List<Mood> moods;
    Activity activity;

    public SelectMoodListViewItemClickListener(Activity activity, List<Mood> moods) {
        this.moods = moods;
        this.activity = activity;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //get selected mood
        Mood mood = moods.get(position);
        Date date = new Date();
        AlertDialog.Builder confirmDialog = createConfirmDialog(mood,date);
        confirmDialog.show();
    }

    public AlertDialog.Builder createConfirmDialog(Mood mood,Date date) {
        String prompt = "You are " + mood.getName() + " at " + date.toString();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                .setTitle("Log Mood")
                .setMessage(prompt)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, null)
                .setPositiveButton(android.R.string.yes,new ConfirmMoodPositiveClickListener())
                .setNegativeButton(android.R.string.no, new ConfirmMoodNegativeClickListener());

        return alertDialog;
    }

}
