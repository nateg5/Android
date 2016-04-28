package com.rgf.rippedgiantfitness.defaults;

import android.text.InputType;

import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 2/8/2016.
 *
 * Ripped Giant Fitness
 * RippedGiantFitness@gmail.com
 * https://www.instagram.com/rippedgiantfitness/
 */
@SuppressWarnings("unchecked")
public class SettingsDefaults {

    public static final String UNIT = "0";
    public static final String DECREMENT = "1";
    public static final String INCREMENT = "2";
    public static final String WARMUP = "3";

    public static void create() {
        LogHelper.debug("Creating Settings defaults.");
        create("", getSettingsDefaults());
        SharedPreferencesHelper.commit();
    }

    private static void create(String parent, Map<String,Object> map) {
        if(SharedPreferencesHelper.isParentIndexValid(parent, true)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    String preferenceString = SharedPreferencesHelper.buildPreferenceString(parent, entry.getKey());
                    if(!SharedPreferencesHelper.getLocalPreferences().containsKey(preferenceString)) {
                        SharedPreferencesHelper.setPreference(preferenceString, (String) entry.getValue(), false);
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

    private static Map<String,Object> getSettingsDefaults() {
        return new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.SETTINGS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Weight Unit");
                    put(SharedPreferencesHelper.SETTING, "Lbs");
                    put(SharedPreferencesHelper.SETTING_HINT, "Weight Unit (Lbs, Kgs)");
                    put(SharedPreferencesHelper.SETTING_TYPE, String.valueOf(InputType.TYPE_CLASS_TEXT));
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Weight Decrement %");
                    put(SharedPreferencesHelper.SETTING, "10");
                    put(SharedPreferencesHelper.SETTING_HINT, "Weight Decrement % (Recommended: 10)");
                    put(SharedPreferencesHelper.SETTING_TYPE, String.valueOf(InputType.TYPE_CLASS_NUMBER));
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Weight Increment %");
                    put(SharedPreferencesHelper.SETTING, "1");
                    put(SharedPreferencesHelper.SETTING_HINT, "Weight Increment % (Recommended: 1)");
                    put(SharedPreferencesHelper.SETTING_TYPE, String.valueOf(InputType.TYPE_CLASS_NUMBER));
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Starting Warmup %");
                    put(SharedPreferencesHelper.SETTING, "50");
                    put(SharedPreferencesHelper.SETTING_HINT, "Starting Warmup % (Recommended: 50)");
                    put(SharedPreferencesHelper.SETTING_TYPE, String.valueOf(InputType.TYPE_CLASS_NUMBER));
                }});
            }});
        }};
    }
}
