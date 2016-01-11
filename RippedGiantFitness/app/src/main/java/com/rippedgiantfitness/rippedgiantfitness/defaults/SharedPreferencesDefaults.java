package com.rippedgiantfitness.rippedgiantfitness.defaults;

import com.rippedgiantfitness.rippedgiantfitness.helper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nate on 11/22/2015.
 */
public class SharedPreferencesDefaults {

    public static Map<String,Object> getSharedPreferencesDefaults() {
        Map<String,Object> sharedPreferencesDefaults = new HashMap<String,Object>() {{
            put(SharedPreferencesHelper.PROGRAMS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Hypertrophy");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getHyperWorkout("Chest", "Bench Press", "45", "1000", "5", "10", "2", "45", "Incline Press", "45", "1000", "5", "10", "0", "45"));
                        add(getHyperWorkout("Back", "Deadlift", "45", "1000", "5", "10", "2", "45", "Bent Over Row", "45", "1000", "5", "10", "0", "45"));
                        add(getHyperWorkout("Shoulders", "Military Press", "45", "1000", "5", "10", "2", "45", "Upright Row", "45", "1000", "5", "10", "0", "45"));
                        add(getHyperWorkout("Legs", "Squat", "45", "1000", "5", "10", "2", "45", "Straight Leg Deadlift", "45", "1000", "5", "10", "0", "45"));
                        add(getHyperWorkout("Abs", "Sit Up", "0", "0", "0", "10", "0", "0", "Leg Raise", "0", "0", "0", "10", "0", "0"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Strength");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getStrengthWorkout("Chest", "Bench Press", "45", "1000", "5", "5", "3", "45", "Incline Press", "45", "1000", "5", "5", "1", "45"));
                        add(getStrengthWorkout("Back", "Deadlift", "45", "1000", "5", "5", "3", "45", "Bent Over Row", "45", "1000", "5", "5", "1", "45"));
                        add(getStrengthWorkout("Shoulders", "Military Press", "45", "1000", "5", "5", "3", "45", "Upright Row", "45", "1000", "5", "1", "3", "45"));
                        add(getStrengthWorkout("Legs", "Squat", "45", "1000", "5", "5", "3", "45", "Straight Leg Deadlift", "45", "1000", "5", "5", "1", "45"));
                        add(getHyperWorkout("Abs", "Sit Up", "0", "0", "0", "10", "0", "0", "Leg Raise", "0", "0", "0", "10", "0", "0"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Body Weight");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getHyperWorkout("Upper Body", "Pushup", "0", "0", "0", "10", "0", "0", "Pullup", "0", "0", "0", "10", "0", "0"));
                        add(getHyperWorkout("Lower Body", "Squat", "0", "0", "0", "10", "0", "0", "Lunge", "0", "0", "0", "10", "0", "0"));
                        add(getHyperWorkout("Abs", "Sit Up", "0", "0", "0", "10", "0", "0", "Leg Raise", "0", "0", "0", "10", "0", "0"));
                    }});
                }});
            }});
        }};

        return sharedPreferencesDefaults;
    }

    private static Map<String,Object> getHyperWorkout(final String workoutName,
                                                     final String exerciseName1, final String minWeight1, final String maxWeight1, final String increment1, final String incrementSets1, final String warmupSets1, final String weight1,
                                                     final String exerciseName2, final String minWeight2, final String maxWeight2, final String increment2, final String incrementSets2, final String warmupSets2, final String weight2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getHyperExercise(exerciseName1, minWeight1, maxWeight1, increment1, incrementSets1, warmupSets1, weight1));
                add(getHyperExercise(exerciseName2, minWeight2, maxWeight2, increment2, incrementSets2, warmupSets2, weight2));
            }});
        }};
    }

    private static Map<String,Object> getHyperExercise(final String exerciseName, final String minWeight, final String maxWeight, final String increment, final String incrementSets, final String warmupSets, final String weight) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, minWeight);
            put(SharedPreferencesHelper.MAX_WEIGHT, maxWeight);
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.FAILED_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, increment);
            put(SharedPreferencesHelper.INCREMENT_SETS, incrementSets);
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
                                                     final String exerciseName1, final String minWeight1, final String maxWeight1, final String increment1, final String incrementSets1, final String warmupSets1, final String weight1,
                                                     final String exerciseName2, final String minWeight2, final String maxWeight2, final String increment2, final String incrementSets2, final String warmupSets2, final String weight2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getStrengthExercise(exerciseName1, minWeight1, maxWeight1, increment1, incrementSets1, warmupSets1, weight1));
                add(getStrengthExercise(exerciseName2, minWeight2, maxWeight2, increment2, incrementSets2, warmupSets2, weight2));
            }});
        }};
    }

    private static Map<String,Object> getStrengthExercise(final String exerciseName, final String minWeight, final String maxWeight, final String increment, final String incrementSets, final String warmupSets, final String weight) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, minWeight);
            put(SharedPreferencesHelper.MAX_WEIGHT, maxWeight);
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.FAILED_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, increment);
            put(SharedPreferencesHelper.INCREMENT_SETS, incrementSets);
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
}
