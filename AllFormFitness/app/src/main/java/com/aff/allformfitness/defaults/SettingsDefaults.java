package com.aff.allformfitness.defaults;

import android.text.InputType;

import com.aff.allformfitness.constants.Constants;
import com.aff.allformfitness.helper.LogHelper;
import com.aff.allformfitness.helper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 2/8/2016.
 *
 * All Form Fitness
 * AllFormFitness@gmail.com
 * https://www.instagram.com/allformfitness/
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

    private static Map<String,Object> getSettingsDefaults() {
        return new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.SETTINGS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Weight Unit");
                    put(SharedPreferencesHelper.SETTING, "Lbs");
                    put(SharedPreferencesHelper.SETTING_HINT, "Weight Unit (Lbs, Kgs)");
                    put(SharedPreferencesHelper.SETTING_TYPE, String.valueOf(InputType.TYPE_CLASS_TEXT));
                    put(SharedPreferencesHelper.SETTING_MIN, "0");
                    put(SharedPreferencesHelper.SETTING_MAX, "0");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Weight Decrement %");
                    put(SharedPreferencesHelper.SETTING, "10");
                    put(SharedPreferencesHelper.SETTING_HINT, "Weight Decrement % (Recommended: 10)");
                    put(SharedPreferencesHelper.SETTING_TYPE, String.valueOf(InputType.TYPE_CLASS_NUMBER));
                    put(SharedPreferencesHelper.SETTING_MIN, "1");
                    put(SharedPreferencesHelper.SETTING_MAX, "100");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Weight Increment %");
                    put(SharedPreferencesHelper.SETTING, "1");
                    put(SharedPreferencesHelper.SETTING_HINT, "Weight Increment % (Recommended: 1)");
                    put(SharedPreferencesHelper.SETTING_TYPE, String.valueOf(InputType.TYPE_CLASS_NUMBER));
                    put(SharedPreferencesHelper.SETTING_MIN, "1");
                    put(SharedPreferencesHelper.SETTING_MAX, "100");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Starting Warmup %");
                    put(SharedPreferencesHelper.SETTING, "20");
                    put(SharedPreferencesHelper.SETTING_HINT, "Starting Warmup % (Recommended: 50)");
                    put(SharedPreferencesHelper.SETTING_TYPE, String.valueOf(InputType.TYPE_CLASS_NUMBER));
                    put(SharedPreferencesHelper.SETTING_MIN, "1");
                    put(SharedPreferencesHelper.SETTING_MAX, "100");
                }});
            }});
        }};
    }
}
