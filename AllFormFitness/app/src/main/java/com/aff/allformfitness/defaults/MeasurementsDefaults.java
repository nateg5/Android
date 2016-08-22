package com.aff.allformfitness.defaults;

import com.aff.allformfitness.constants.Constants;
import com.aff.allformfitness.helper.LogHelper;
import com.aff.allformfitness.helper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 1/22/2016.
 *
 * All Form Fitness
 * AllFormFitness@gmail.com
 * https://www.instagram.com/allformfitness/
 */
@SuppressWarnings("unchecked")
public class MeasurementsDefaults {
    public static void create() {
        if(SharedPreferencesHelper.getMeasurements().size() == 0) {
            LogHelper.debug("Creating Measurements defaults.");
            create("", getMeasurementsDefaults());
            SharedPreferencesHelper.commit();
        } else {
            LogHelper.debug("Measurements already exists.");
        }
    }

    private static void create(String parent, Map<String,Object> map) {
        if(SharedPreferencesHelper.isParentIndexValid(parent, true)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    SharedPreferencesHelper.setPreference(parent, entry.getKey(), (String) entry.getValue(), Constants.MIN, Constants.MAX, false);
                } else {
                    List<Object> list = (List<Object>) entry.getValue();

                    for (int i = 0; i < list.size(); i++) {
                        create(SharedPreferencesHelper.buildPreferenceString(parent, entry.getKey(), String.valueOf(i)), (Map<String, Object>) list.get(i));
                    }
                }
            }
        }
    }

    private static Map<String,Object> getMeasurementsDefaults() {
        return new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.MEASUREMENTS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Weight (Lbs)");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Body Fat (%)");
                }});
            }});
        }};
    }
}
