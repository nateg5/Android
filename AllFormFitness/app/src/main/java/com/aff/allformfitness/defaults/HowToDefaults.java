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
        LogHelper.debug("Creating How To defaults.");
        create("", getHowToDefaults());
        SharedPreferencesHelper.commit();
    }

    private static void create(String parent, Map<String,Object> map) {
        if(SharedPreferencesHelper.isParentIndexValid(parent, true)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    String preferenceString = SharedPreferencesHelper.buildPreferenceString(parent, entry.getKey());
                    if(!SharedPreferencesHelper.getLocalPreferences().containsKey(preferenceString)) {
                        SharedPreferencesHelper.setPreference(preferenceString, (String) entry.getValue(), Constants.MIN, Constants.MAX, false);
                    }
                } else {
                    List<Object> list = (List<Object>) entry.getValue();

                    for (int i = 0; i < list.size(); i++) {
                        create(SharedPreferencesHelper.buildPreferenceString(parent, entry.getKey(), String.valueOf(i)), (Map<String, Object>) list.get(i));
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
