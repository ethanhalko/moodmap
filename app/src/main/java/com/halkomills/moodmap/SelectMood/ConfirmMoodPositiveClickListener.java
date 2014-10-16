package com.halkomills.moodmap.SelectMood;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

import com.halkomills.moodmap.Database.MoodmapSqliteHelper;
import com.halkomills.moodmap.Database.RecordedMood;
import com.halkomills.moodmap.Models.MoodDTO;
import com.halkomills.moodmap.Models.RecordedMoodDTO;

import java.util.Date;

/**
 * Created by mills on 2014-10-15.
 */
public class ConfirmMoodPositiveClickListener implements DialogInterface.OnClickListener {

    private MoodDTO moodDTO;
    private Date timestamp;
    private Activity activity;
    private MoodmapSqliteHelper db;

    public ConfirmMoodPositiveClickListener(Activity activity,MoodDTO moodDTO,Date timestamp) {

        this.activity = activity;
        this.moodDTO = moodDTO;
        this.timestamp = timestamp;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Log.d("ListenerLog", "confirm mood dialog - positive");
        RecordedMoodDTO recordedMoodDTO = new RecordedMoodDTO(moodDTO.getId(), moodDTO.getName(),timestamp);
        db = new MoodmapSqliteHelper(activity);

        RecordedMood recordedMood = new RecordedMood(db.getWritableDatabase());
        recordedMood.create(recordedMoodDTO);

        Log.d("ListenerLog","confirm mood dialog - recorded mood");
    }
}
