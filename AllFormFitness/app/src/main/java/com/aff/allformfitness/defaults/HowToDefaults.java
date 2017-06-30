package com.aff.allformfitness.defaults;

import com.aff.allformfitness.constants.Constants;
import com.aff.allformfitness.helper.LogHelper;
import com.aff.allformfitness.helper.SharedPreferencesHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 4/8/2016.
 *
 * All Form Fitness
 * AllFormFitness@gmail.com
 * https://www.instagram.com/allformfitness/
 */
@SuppressWarnings("unchecked")
public class HowToDefaults {

    public static void create() {
        if(!SharedPreferencesHelper.instance.getLocalPreferences().containsKey(SharedPreferencesHelper.HOW_TO_HOME) ||
                !SharedPreferencesHelper.instance.getLocalPreferences().containsKey(SharedPreferencesHelper.HOW_TO_WORKOUTS) ||
                !SharedPreferencesHelper.instance.getLocalPreferences().containsKey(SharedPreferencesHelper.HOW_TO_WORKOUT)) {
            LogHelper.debug("Creating How To defaults.");
            create("", getHowToDefaults());
            SharedPreferencesHelper.instance.commit();
        } else {
            LogHelper.debug("How To already exists.");
        }
    }

    private static void create(String parent, Map<String,Object> map) {
        if(SharedPreferencesHelper.instance.isParentIndexValid(parent, true)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    String preferenceString = SharedPreferencesHelper.instance.buildPreferenceString(parent, entry.getKey());
                    if(!SharedPreferencesHelper.instance.getLocalPreferences().containsKey(preferenceString)) {
                        SharedPreferencesHelper.instance.setPreference(preferenceString, (String) entry.getValue(), Constants.MIN, Constants.MAX, false);
                    }
                } else {
                    List<Object> list = (List<Object>) entry.getValue();

                    for (int i = 0; i < list.size(); i++) {
                        create(SharedPreferencesHelper.instance.buildPreferenceString(parent, entry.getKey(), String.valueOf(i)), (Map<String, Object>) list.get(i));
                    }
                }
            }
        }
    }

    private static Map<String,Object> getHowToDefaults() {
        return new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.HOW_TO_HOME, "true");
            put(SharedPreferencesHelper.HOW_TO_WORKOUTS, "true");
            put(SharedPreferencesHelper.HOW_TO_WORKOUT, "true");
        }};
    }
}
