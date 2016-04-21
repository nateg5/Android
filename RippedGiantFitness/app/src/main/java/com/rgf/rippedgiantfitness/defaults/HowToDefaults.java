package com.rgf.rippedgiantfitness.defaults;

import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;

/**
 * Created by Nate on 4/8/2016.
 */
public class HowToDefaults {

    public static void create() {
        if(SharedPreferencesHelper.getLocalPreferences().containsKey(SharedPreferencesHelper.HOW_TO_WORKOUTS) &&
           SharedPreferencesHelper.getLocalPreferences().containsKey(SharedPreferencesHelper.HOW_TO_WORKOUT)) {
            LogHelper.debug("How To properties already exists.");
        } else {
            LogHelper.debug("Creating How To defaults.");
            SharedPreferencesHelper.getLocalPreferences().put(SharedPreferencesHelper.HOW_TO_WORKOUTS, "true");
            SharedPreferencesHelper.getLocalPreferences().put(SharedPreferencesHelper.HOW_TO_WORKOUT, "true");
            SharedPreferencesHelper.commit();
        }
    }
}
