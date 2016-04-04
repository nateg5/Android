package com.rgf.rippedgiantfitness.defaults;

import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 11/22/2015.
 */
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
                    SharedPreferencesHelper.setPreference(parent, entry.getKey(), (String) entry.getValue(), false);
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
        Map<String,Object> programsDefaults = new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.PROGRAMS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "HIT");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getHitWorkout("Chest", "Bench Press", "45", "1000", "5", "5", "45", "Incline Press", "45", "1000", "5", "5", "45"));
                        add(getHitWorkout("Back", "Deadlift", "45", "1000", "5", "5", "45", "Bent Over Row", "45", "1000", "5", "5", "45"));
                        add(getHitWorkout("Shoulders", "Military Press", "45", "1000", "5", "5", "45", "Upright Row", "45", "1000", "5", "5", "45"));
                        add(getHitWorkout("Legs", "Squat", "45", "1000", "5", "5", "45", "Straight Leg Deadlift", "45", "1000", "5", "5", "45"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "5 x 5");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getStrengthWorkout("Chest", "Bench Press", "45", "1000", "5", "3", "45", "Incline Press", "45", "1000", "5", "1", "45"));
                        add(getStrengthWorkout("Back", "Deadlift", "45", "1000", "5", "3", "45", "Bent Over Row", "45", "1000", "5", "1", "45"));
                        add(getStrengthWorkout("Shoulders", "Military Press", "45", "1000", "5", "3", "45", "Upright Row", "45", "1000", "5", "1", "45"));
                        add(getStrengthWorkout("Legs", "Squat", "45", "1000", "5", "3", "45", "Straight Leg Deadlift", "45", "1000", "5", "1", "45"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "10 x 10");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getHyperWorkout("Chest", "Bench Press", "45", "1000", "5", "2", "45", "Incline Press", "45", "1000", "5", "0", "45"));
                        add(getHyperWorkout("Back", "Deadlift", "45", "1000", "5", "2", "45", "Bent Over Row", "45", "1000", "5", "0", "45"));
                        add(getHyperWorkout("Shoulders", "Military Press", "45", "1000", "5", "2", "45", "Upright Row", "45", "1000", "5", "0", "45"));
                        add(getHyperWorkout("Legs", "Squat", "45", "1000", "5", "2", "45", "Straight Leg Deadlift", "45", "1000", "5", "0", "45"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Body Weight");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getHyperWorkout("Upper Body", "Pushup", "0", "0", "0", "0", "0", "Pullup", "0", "0", "0", "0", "0"));
                        add(getHyperWorkout("Lower Body", "Squat", "0", "0", "0", "0", "0", "Lunge", "0", "0", "0", "0", "0"));
                    }});
                }});
            }});
        }};

        return programsDefaults;
    }

    private static Map<String,Object> getHyperWorkout(final String workoutName,
                                                     final String exerciseName1, final String minWeight1, final String maxWeight1, final String increment1, final String warmupSets1, final String weight1,
                                                     final String exerciseName2, final String minWeight2, final String maxWeight2, final String increment2, final String warmupSets2, final String weight2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getHyperExercise(exerciseName1, minWeight1, maxWeight1, increment1, warmupSets1, weight1));
                add(getHyperExercise(exerciseName2, minWeight2, maxWeight2, increment2, warmupSets2, weight2));
            }});
        }};
    }

    private static Map<String,Object> getHyperExercise(final String exerciseName, final String minWeight, final String maxWeight, final String increment, final String warmupSets, final String weight) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, minWeight);
            put(SharedPreferencesHelper.MAX_WEIGHT, maxWeight);
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.FAILED_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, increment);
            put(SharedPreferencesHelper.WARMUP_SETS, warmupSets);
            put(SharedPreferencesHelper.REPS, "10");
            put(SharedPreferencesHelper.REST, "60");
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

    private static Map<String,Object> getStrengthWorkout(final String workoutName,
                                                     final String exerciseName1, final String minWeight1, final String maxWeight1, final String increment1, final String warmupSets1, final String weight1,
                                                     final String exerciseName2, final String minWeight2, final String maxWeight2, final String increment2, final String warmupSets2, final String weight2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getStrengthExercise(exerciseName1, minWeight1, maxWeight1, increment1, warmupSets1, weight1));
                add(getStrengthExercise(exerciseName2, minWeight2, maxWeight2, increment2, warmupSets2, weight2));
            }});
        }};
    }

    private static Map<String,Object> getStrengthExercise(final String exerciseName, final String minWeight, final String maxWeight, final String increment, final String warmupSets, final String weight) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, minWeight);
            put(SharedPreferencesHelper.MAX_WEIGHT, maxWeight);
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.FAILED_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, increment);
            put(SharedPreferencesHelper.WARMUP_SETS, warmupSets);
            put(SharedPreferencesHelper.REPS, "5");
            put(SharedPreferencesHelper.REST, "120");
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
            }});
        }};
    }

    private static Map<String,Object> getHitWorkout(final String workoutName,
                                                      final String exerciseName1, final String minWeight1, final String maxWeight1, final String increment1, final String warmupSets1, final String weight1,
                                                      final String exerciseName2, final String minWeight2, final String maxWeight2, final String increment2, final String warmupSets2, final String weight2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getHitExercise(exerciseName1, minWeight1, maxWeight1, increment1, warmupSets1, weight1));
                add(getHitExercise(exerciseName2, minWeight2, maxWeight2, increment2, warmupSets2, weight2));
            }});
        }};
    }

    private static Map<String,Object> getHitExercise(final String exerciseName, final String minWeight, final String maxWeight, final String increment, final String warmupSets, final String weight) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, minWeight);
            put(SharedPreferencesHelper.MAX_WEIGHT, maxWeight);
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.FAILED_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, increment);
            put(SharedPreferencesHelper.WARMUP_SETS, warmupSets);
            put(SharedPreferencesHelper.REPS, "10");
            put(SharedPreferencesHelper.REST, "120");
            put(SharedPreferencesHelper.SETS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, weight);
                }});
            }});
        }};
    }
}
