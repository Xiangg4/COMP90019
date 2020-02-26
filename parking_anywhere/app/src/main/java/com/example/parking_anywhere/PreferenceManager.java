package com.example.parking_anywhere;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class PreferenceManager {


    private static SharedPreferences pref;

    // Shared preferences file name
    private static final String PREF_NAME = "tutorialwing-shared-preference";

    private static final String IS_FIRST_TIME_LAUNCH = "FIRST_TIME_LAUNCH";

    public static void init(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean shouldShowSlider() {
        return (pref == null) || pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public static void saveFirstTimeLaunch(boolean isFirstTime) {
        Log.d("savefirsttime", "in");
        if (pref == null)
            return;

        SharedPreferences.Editor editor = pref.edit();
        if (editor != null) {
            Log.d("savefirsttime", "input");
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
            editor.apply();
        }
    }
}