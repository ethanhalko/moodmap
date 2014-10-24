package com.halkomills.moodmap.SelectMood;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.halkomills.moodmap.DashboardActivity;
import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Database.RecordedMood;
import com.halkomills.moodmap.Models.MoodDTO;
import com.halkomills.moodmap.Models.RecordedMoodDTO;

import java.util.Date;

/**
 * Created by mills on 2014-10-15.
 */
public class ConfirmMoodPositiveClickListener implements DialogInterface.OnClickListener {

    private RecordedMoodDTO moodDTO;
    private DashboardActivity activity;
    private MoodmapSqliteHelper db;

    public ConfirmMoodPositiveClickListener(Activity activity,RecordedMoodDTO moodDTO) {

        this.activity = (DashboardActivity)activity;
        this.moodDTO = moodDTO;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Log.d("ListenerLog", "confirm mood dialog - positive");
        db = new MoodmapSqliteHelper(activity);

        RecordedMood recordedMood = new RecordedMood(db.getWritableDatabase());
        recordedMood.create(moodDTO);

        activity.setLatestMoodText();
        Log.d("ListenerLog","confirm mood dialog - recorded mood");


    }
}
