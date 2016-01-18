package com.rippedgiantfitness.rippedgiantfitness.defaults;

import com.rippedgiantfitness.rippedgiantfitness.helper.SharedPreferencesHelper;

/**
 * Created by Nate on 1/18/2016.
 */
public class HiitDefaults {

    public static void create() {
        SharedPreferencesHelper.getLocalPreferences().put(SharedPreferencesHelper.HIIT_GO, "");
        SharedPreferencesHelper.getLocalPreferences().put(SharedPreferencesHelper.HIIT_REST, "");
        SharedPreferencesHelper.getLocalPreferences().put(SharedPreferencesHelper.HIIT_ROUNDS, "");
    }
}
