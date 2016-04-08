package com.rgf.rippedgiantfitness.defaults;

import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;

/**
 * Created by Nate on 4/8/2016.
 */
public class FirstDefaults {

    public static void create() {
        if(SharedPreferencesHelper.getLocalPreferences().containsKey(SharedPreferencesHelper.FIRST_WORKOUT)) {
            LogHelper.debug("First Workout already exists.");
        } else {
            LogHelper.debug("Creating First Workout default.");
            SharedPreferencesHelper.getLocalPreferences().put(SharedPreferencesHelper.FIRST_WORKOUT, "true");
            SharedPreferencesHelper.commit();
        }
    }
}
