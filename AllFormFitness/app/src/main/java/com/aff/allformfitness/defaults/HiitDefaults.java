package com.aff.allformfitness.defaults;

import com.aff.allformfitness.helper.LogHelper;
import com.aff.allformfitness.helper.SharedPreferencesHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 1/18/2016.
 *
 * All Form Fitness
 * AllFormFitness@gmail.com
 * https://www.instagram.com/allformfitness/
 */
@SuppressWarnings("unchecked")
public class HiitDefaults {

    public static void create() {
        LogHelper.debug("Creating HIIT defaults.");
        create("", getHiitDefaults());
        SharedPreferencesHelper.commit();
    }

    private static void create(String parent, Map<String,Object> map) {
        if(SharedPreferencesHelper.isParentIndexValid(parent, true)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    String preferenceString = SharedPreferencesHelper.buildPreferenceString(parent, entry.getKey());
                    if(!SharedPreferencesHelper.getLocalPreferences().containsKey(preferenceString)) {
                        SharedPreferencesHelper.getLocalPreferences().put(preferenceString, (String) entry.getValue());
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

    private static Map<String,Object> getHiitDefaults() {
        return new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.HIIT_GO, "");
            put(SharedPreferencesHelper.HIIT_REST, "");
            put(SharedPreferencesHelper.HIIT_ROUNDS, "");
        }};
    }
}
