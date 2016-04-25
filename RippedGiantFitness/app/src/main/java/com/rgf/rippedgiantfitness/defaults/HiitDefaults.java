package com.rgf.rippedgiantfitness.defaults;

import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 1/18/2016.
 */
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
        Map<String,Object> hiitDefaults = new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.HIIT_GO, "");
            put(SharedPreferencesHelper.HIIT_REST, "");
            put(SharedPreferencesHelper.HIIT_ROUNDS, "");
        }};

        return hiitDefaults;
    }
}
