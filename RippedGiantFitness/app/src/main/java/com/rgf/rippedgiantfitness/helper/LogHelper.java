package com.rgf.rippedgiantfitness.helper;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nate on 11/22/2015.
 */
public class LogHelper {
    public final static String PREFIX = "RGF ";

    public static void debug(String string) {
        debug(string, false);
    }

    private static void debug(String string, boolean toast) {
        Log.d(PREFIX + "DEBUG", string);
        if(toast) {
            toast("DEBUG " + string);
        }
    }

    public static void error(String string) {
        error(string, true);
    }

    private static void error(String string, boolean toast) {
        Log.e(PREFIX + "ERROR", string);
        if(toast) {
            toast("ERROR " + string);
        }
    }

    public static void toast(String string) {
        Toast.makeText(SharedPreferencesHelper.getContext(), string, Toast.LENGTH_LONG).show();
    }
}
