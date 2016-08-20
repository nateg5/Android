package com.rgf.rippedgiantfitness.helper;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nate on 11/22/2015.
 *
 * Ripped Giant Fitness
 * RippedGiantFitness@gmail.com
 * https://www.instagram.com/rippedgiantfitness/
 */
public class LogHelper {
    private final static String PREFIX = "RGF ";

    public static void debug(String string) {
        Log.d(PREFIX + "DEBUG", string);
    }

    public static void error(String string) {
        Log.e(PREFIX + "ERROR", string);
        toast("ERROR " + string);
    }

    public static void toast(String string) {
        Toast.makeText(SharedPreferencesHelper.getContext(), string, Toast.LENGTH_LONG).show();
    }
}
