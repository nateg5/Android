package com.aff.allformfitness.defaults;

import com.aff.allformfitness.constants.Constants;
import com.aff.allformfitness.helper.DialogHelper;
import com.aff.allformfitness.helper.LogHelper;
import com.aff.allformfitness.helper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 11/22/2015.
 *
 * All Form Fitness
 * AllFormFitness@gmail.com
 * https://www.instagram.com/allformfitness/
 */
@SuppressWarnings("unchecked")
public class ProgramsDefaults {

    public static void create() {
        if(SharedPreferencesHelper.getPrograms().size() == 0) {
            LogHelper.debug("Creating Programs defaults.");
            create("", getProgramsDefaults());
            SharedPreferencesHelper.commit();
        } else {
            LogHelper.debug("Programs already exists.");
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

    private static Map<String,Object> getProgramsDefaults() {
        return new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.PROGRAMS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Light Free Weight");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getFreeWeightWorkout("Chest", "Bench Press", "45", "Incline Press", "45"));
                        add(getFreeWeightWorkout("Back", "Deadlift", "45", "Bent Over Row", "45"));
                        add(getFreeWeightWorkout("Shoulders", "Overhead Press", "45", "Upright Row", "45"));
                        add(getFreeWeightWorkout("Legs", "Squat", "45", "Straight Leg Deadlift", "45"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Moderate Free Weight");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getFreeWeightWorkout("Chest", "Bench Press", "135", "Incline Press", "95"));
                        add(getFreeWeightWorkout("Back", "Deadlift", "225", "Bent Over Row", "135"));
                        add(getFreeWeightWorkout("Shoulders", "Overhead Press", "95", "Upright Row", "65"));
                        add(getFreeWeightWorkout("Legs", "Squat", "185", "Straight Leg Deadlift", "115"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Heavy Free Weight");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getFreeWeightWorkout("Chest", "Bench Press", "225", "Incline Press", "135"));
                        add(getFreeWeightWorkout("Back", "Deadlift", "405", "Bent Over Row", "225"));
                        add(getFreeWeightWorkout("Shoulders", "Overhead Press", "135", "Upright Row", "95"));
                        add(getFreeWeightWorkout("Legs", "Squat", "315", "Straight Leg Deadlift", "185"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Body Weight");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getBodyWeightWorkout("Upper Body", "Pushup", "Pullup"));
                        add(getBodyWeightWorkout("Lower Body", "Squat", "Lunge"));
                    }});
                }});
            }});
        }};
    }

    private static Map<String,Object> getFreeWeightWorkout(final String workoutName,
                                                           final String exerciseName1, final String weight1,
                                                           final String exerciseName2, final String weight2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getFreeWeightExercise(exerciseName1, weight1));
                add(getFreeWeightExercise(exerciseName2, weight2));
            }});
        }};
    }

    private static Map<String,Object> getFreeWeightExercise(final String exerciseName, final String weight) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, "45");
            put(SharedPreferencesHelper.MAX_WEIGHT, "1000");
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.SUCCESS_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, "10");
            put(SharedPreferencesHelper.WARMUP_SETS, "4");
            put(SharedPreferencesHelper.REPS, "10");
            put(SharedPreferencesHelper.REST, "60");
            put(SharedPreferencesHelper.EXERCISE_TYPE, DialogHelper.FREE_WEIGHT);

            put(SharedPreferencesHelper.SETS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, weight);
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, weight);
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, weight);
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, weight);
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, weight);
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, weight);
                }});
            }});
        }};
    }

    private static Map<String,Object> getBodyWeightWorkout(final String workoutName,
                                                           final String exerciseName1,
                                                           final String exerciseName2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getBodyWeightExercise(exerciseName1));
                add(getBodyWeightExercise(exerciseName2));
            }});
        }};
    }

    private static Map<String,Object> getBodyWeightExercise(final String exerciseName) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, "0");
            put(SharedPreferencesHelper.MAX_WEIGHT, "0");
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.SUCCESS_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, "0");
            put(SharedPreferencesHelper.WARMUP_SETS, "0");
            put(SharedPreferencesHelper.REPS, "10");
            put(SharedPreferencesHelper.REST, "60");
            put(SharedPreferencesHelper.EXERCISE_TYPE, DialogHelper.BODY_WEIGHT);

            put(SharedPreferencesHelper.SETS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.REPS, "10");
                }});
            }});
        }};
    }
}
