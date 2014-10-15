package com.halkomills.moodmap;

import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by mills on 2014-10-15.
 */
public class ConfirmMoodNegativeClickListener implements DialogInterface.OnClickListener {


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Log.d("ListenerLog", "confirm mood dialog - negative");
    }
}
