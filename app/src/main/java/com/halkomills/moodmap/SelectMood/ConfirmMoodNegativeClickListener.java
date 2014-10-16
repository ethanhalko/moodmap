package com.halkomills.moodmap.SelectMood;

import android.content.DialogInterface;
import android.util.Log;

import com.halkomills.moodmap.Models.MoodDTO;

public class ConfirmMoodNegativeClickListener implements DialogInterface.OnClickListener {

    private MoodDTO moodDTO;

    public ConfirmMoodNegativeClickListener(MoodDTO moodDTO) {
        this.moodDTO = moodDTO;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Log.d("ListenerLog", "confirm mood dialog - negative");
    }
}