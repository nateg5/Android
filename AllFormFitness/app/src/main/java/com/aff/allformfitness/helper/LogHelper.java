package com.aff.allformfitness.helper;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nate on 11/22/2015.
 *
 * All Form Fitness
 * AllFormFitness@gmail.com
 * https://www.instagram.com/allformfitness/
 */
public class LogHelper {
    private final static String PREFIX = "AFF ";

    public static void debug(String string) {
        Log.d(PREFIX + "DEBUG", string);
    }

    public static void error(String string) {
        Log.e(PREFIX + "ERROR", string);
        toast("ERROR " + string);
    }

    public static void toast(String string) {
        Toast.makeText(SharedPreferencesHelper.instance.getContext(), string, Toast.LENGTH_LONG).show();
    }
}
