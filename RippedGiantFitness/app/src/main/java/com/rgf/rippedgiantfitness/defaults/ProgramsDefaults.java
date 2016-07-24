package com.rgf.rippedgiantfitness.defaults;

import com.rgf.rippedgiantfitness.constants.Constants;
import com.rgf.rippedgiantfitness.helper.DialogHelper;
import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 11/22/2015.
 *
 * Ripped Giant Fitness
 * RippedGiantFitness@gmail.com
 * https://www.instagram.com/rippedgiantfitness/
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
                    put(SharedPreferencesHelper.NAME, "HIT");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getHitWorkout("Chest", "Bench Press", "Incline Press"));
                        add(getHitWorkout("Back", "Deadlift", "Bent Over Row"));
                        add(getHitWorkout("Shoulders", "Military Press", "Upright Row"));
                        add(getHitWorkout("Legs", "Squat", "Straight Leg Deadlift"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "5 x 5");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getStrengthWorkout("Chest", "Bench Press", "Incline Press"));
                        add(getStrengthWorkout("Back", "Deadlift", "Bent Over Row"));
                        add(getStrengthWorkout("Shoulders", "Military Press", "Upright Row"));
                        add(getStrengthWorkout("Legs", "Squat", "Straight Leg Deadlift"));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "10 x 10");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getHyperWorkout("Chest", "Bench Press", "45", "1000", "5", "2", "45", DialogHelper.FREE_WEIGHT, "Incline Press", "45", "1000", "5", "45", DialogHelper.FREE_WEIGHT));
                        add(getHyperWorkout("Back", "Deadlift", "45", "1000", "5", "2", "45", DialogHelper.FREE_WEIGHT, "Bent Over Row", "45", "1000", "5", "45", DialogHelper.FREE_WEIGHT));
                        add(getHyperWorkout("Shoulders", "Military Press", "45", "1000", "5", "2", "45", DialogHelper.FREE_WEIGHT, "Upright Row", "45", "1000", "5", "45", DialogHelper.FREE_WEIGHT));
                        add(getHyperWorkout("Legs", "Squat", "45", "1000", "5", "2", "45", DialogHelper.FREE_WEIGHT, "Straight Leg Deadlift", "45", "1000", "5", "45", DialogHelper.FREE_WEIGHT));
                    }});
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.NAME, "Body Weight");
                    put(SharedPreferencesHelper.WORKOUTS, new ArrayList<Object>() {{
                        add(getHyperWorkout("Upper Body", "Pushup", "0", "0", "0", "0", "0", DialogHelper.BODY_WEIGHT, "Pullup", "0", "0", "0", "0", DialogHelper.BODY_WEIGHT));
                        add(getHyperWorkout("Lower Body", "Squat", "0", "0", "0", "0", "0", DialogHelper.BODY_WEIGHT, "Lunge", "0", "0", "0", "0", DialogHelper.BODY_WEIGHT));
                    }});
                }});
            }});
        }};
    }

    private static Map<String,Object> getHyperWorkout(final String workoutName,
                                                     final String exerciseName1, final String minWeight1, final String maxWeight1, final String increment1, final String warmupSets1, final String weight1, final String exerciseType1,
                                                     final String exerciseName2, final String minWeight2, final String maxWeight2, final String increment2, final String weight2, final String exerciseType2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getHyperExercise(exerciseName1, minWeight1, maxWeight1, increment1, warmupSets1, weight1, exerciseType1));
                add(getHyperExercise(exerciseName2, minWeight2, maxWeight2, increment2, "0", weight2, exerciseType2));
            }});
        }};
    }

    private static Map<String,Object> getHyperExercise(final String exerciseName, final String minWeight, final String maxWeight, final String increment, final String warmupSets, final String weight, final String exerciseType) {
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
            put(SharedPreferencesHelper.EXERCISE_TYPE, exerciseType);

            if(exerciseType.equals(DialogHelper.FREE_WEIGHT)) {
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
            } else {
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
            }
        }};
    }

    private static Map<String,Object> getStrengthWorkout(final String workoutName,
                                                     final String exerciseName1,
                                                     final String exerciseName2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getStrengthExercise(exerciseName1, "3"));
                add(getStrengthExercise(exerciseName2, "1"));
            }});
        }};
    }

    private static Map<String,Object> getStrengthExercise(final String exerciseName, final String warmupSets) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, "45");
            put(SharedPreferencesHelper.MAX_WEIGHT, "1000");
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.FAILED_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, "5");
            put(SharedPreferencesHelper.WARMUP_SETS, warmupSets);
            put(SharedPreferencesHelper.REPS, "5");
            put(SharedPreferencesHelper.REST, "120");
            put(SharedPreferencesHelper.EXERCISE_TYPE, DialogHelper.FREE_WEIGHT);
            put(SharedPreferencesHelper.SETS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, "45");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, "45");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, "45");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, "45");
                }});
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, "45");
                }});
            }});
        }};
    }

    private static Map<String,Object> getHitWorkout(final String workoutName,
                                                      final String exerciseName1,
                                                      final String exerciseName2) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, workoutName);
            put(SharedPreferencesHelper.EXERCISES, new ArrayList<Object>() {{
                add(getHitExercise(exerciseName1));
                add(getHitExercise(exerciseName2));
            }});
        }};
    }

    private static Map<String,Object> getHitExercise(final String exerciseName) {
        return new HashMap<String, Object>() {{
            put(SharedPreferencesHelper.NAME, exerciseName);
            put(SharedPreferencesHelper.MIN_WEIGHT, "45");
            put(SharedPreferencesHelper.MAX_WEIGHT, "1000");
            put(SharedPreferencesHelper.CURRENT_VOLUME, "0");
            put(SharedPreferencesHelper.FAILED_VOLUME, "0");
            put(SharedPreferencesHelper.INCREMENT, "5");
            put(SharedPreferencesHelper.WARMUP_SETS, "5");
            put(SharedPreferencesHelper.REPS, "10");
            put(SharedPreferencesHelper.REST, "120");
            put(SharedPreferencesHelper.EXERCISE_TYPE, DialogHelper.FREE_WEIGHT);
            put(SharedPreferencesHelper.SETS, new ArrayList<Object>() {{
                add(new HashMap<String, Object>() {{
                    put(SharedPreferencesHelper.WEIGHT, "45");
                }});
            }});
        }};
    }
}
