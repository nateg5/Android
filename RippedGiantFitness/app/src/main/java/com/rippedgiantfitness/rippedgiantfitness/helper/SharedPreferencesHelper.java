package com.rippedgiantfitness.rippedgiantfitness.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.rippedgiantfitness.rippedgiantfitness.R;
import com.rippedgiantfitness.rippedgiantfitness.defaults.SharedPreferencesDefaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate on 11/22/2015.
 */
public class SharedPreferencesHelper {
    public final static String PACKAGE = SharedPreferencesHelper.class.getName();

    public final static String PROGRAMS = "Programs";
    public final static String WORKOUTS = "Workouts";
    public final static String EXERCISES = "Exercises";
    public final static String SETS = "Sets";
    public final static String NAME = "Name";
    public final static String REPS = "Reps";
    public final static String REST = "Rest";
    public final static String MIN_WEIGHT = "Min Weight";
    public final static String MAX_WEIGHT = "Max Weight";
    public final static String CURRENT_VOLUME = "Current Volume";
    public final static String FAILED_VOLUME = "Failed Volume";
    public final static String INCREMENT = "Increment";
    public final static String INCREMENT_SETS = "Increment Sets";
    public final static String WARMUP_SETS = "Warmup Sets";
    public final static String WEIGHT = "Weight";
    public final static String SEPARATOR = ".";
    public final static String SWAP = "Swap";

    public final static String HIIT_TIMER = "HIIT Timer";
    public final static String HIIT_GO = "HIIT Go";
    public final static String HIIT_REST = "HIIT Rest";
    public final static String HIIT_ROUNDS = "HIIT Rounds";

    private static Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Map<String, String> localPreferences;


    public static void init(Context c) {
        context = c;
        sharedPreferences = context.getSharedPreferences(PACKAGE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        localPreferences = (Map<String, String>) sharedPreferences.getAll();

        if(getPrograms().isEmpty()) {
            LogHelper.debug("Programs are empty, creating defaults.");

            createDefaultSharedPreferences();
        } else {
            LogHelper.debug("Found " + getPrograms().size() + " existing programs.");
        }
    }

    public static Context getContext() {
        return context;
    }

    private static boolean commit() {
        LogHelper.debug("Committing localPreferences changes to sharedPreferences");

        boolean success = true;

        editor.clear();
        success = (success && editor.commit());

        for(Map.Entry<String, String> entry : localPreferences.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        success = (success && editor.commit());

        return success;
    }

    public static List<String> getPrograms() {
        return getList("", PROGRAMS, NAME);
    }

    public static boolean addProgram(String name) {
        List<String> programs = getPrograms();

        return setPreference(buildPreferenceString(PROGRAMS, String.valueOf(programs.size())), NAME, name);
    }

    public static List<String> getWorkouts(String program) {
        return getList(program, WORKOUTS, NAME);
    }

    public static boolean addWorkout(String program, String name) {
        List<String> workouts = getWorkouts(program);

        return setPreference(buildPreferenceString(program, WORKOUTS, String.valueOf(workouts.size())), NAME, name);
    }

    public static boolean finishWorkout(String exercise, boolean success) {
        boolean retVal = true;

        int minWeight = Integer.valueOf(getPreference(exercise, MIN_WEIGHT));
        int maxWeight = Integer.valueOf(getPreference(exercise, MAX_WEIGHT));
        int increment = Integer.valueOf(getPreference(exercise, INCREMENT));
        int incrementSets = Integer.valueOf(getPreference(exercise, INCREMENT_SETS));
        int reps = Integer.valueOf(getPreference(exercise, REPS));

        List<String> sets = getSets(exercise);

        if(success) {
            for(int inc = 0; inc < incrementSets; inc++) {
                int lastSetWeight = Integer.valueOf(getPreference(sets.get(sets.size() - 1), WEIGHT));
                if (lastSetWeight < maxWeight) {
                    for (int i = sets.size() - 1; i >= 0; i--) {
                        int setWeight = Integer.valueOf(getPreference(sets.get(i), WEIGHT));
                        if (i == 0 || setWeight < Integer.valueOf(getPreference(sets.get(i - 1), WEIGHT))) {
                            setWeight = setWeight + increment;
                            if (setWeight > maxWeight) {
                                setWeight = maxWeight;
                            }
                            retVal = (retVal && setPreference(sets.get(i), WEIGHT, String.valueOf(setWeight), false));
                            break;
                        }
                    }
                } else {
                    retVal = (retVal && setPreference(exercise, REPS, String.valueOf(reps + 1), false));
                    retVal = (retVal && addSet(exercise, String.valueOf(maxWeight)));
                    break;
                }
            }
        } else {
            retVal = (retVal && setPreference(exercise, FAILED_VOLUME, String.valueOf(getVolume(exercise))));
            int firstSetWeight = Integer.valueOf(getPreference(sets.get(0), WEIGHT));
            if(firstSetWeight > minWeight) {
                for(int i = sets.size() - 1; i >= 0; i--) {
                    int setWeight = Integer.valueOf(getPreference(sets.get(i), WEIGHT));
                    setWeight = (int)(setWeight * 0.9) - ((int)(setWeight * 0.9) % increment);
                    if(setWeight < minWeight) {
                        setWeight = minWeight;
                    }
                    retVal = (retVal && setPreference(sets.get(i), WEIGHT, String.valueOf(setWeight), false));
                }
            } else {
                if(reps - 1 > 0) {
                    retVal = (retVal && setPreference(exercise, REPS, String.valueOf(reps - 1), false));
                }
                if(sets.size() - 1 > 0) {
                    retVal = (retVal && removePreferenceTree(sets.get(sets.size() - 1)));
                }
            }
        }

        retVal = (retVal && setPreference(exercise, CURRENT_VOLUME, String.valueOf(getVolume(exercise))));

        retVal = (retVal && commit());

        return retVal;
    }

    public static int getVolume(String exercise) {
        int volume = 0;

        int reps = Integer.valueOf(getPreference(exercise, REPS));

        List<String> sets = getSets(exercise);

        for(int i = 0; i < sets.size(); i++) {
            int setWeight = Integer.valueOf(getPreference(sets.get(i), WEIGHT));

            volume += setWeight * reps;
        }

        return volume;
    }

    public static List<String> getExercises(String workout) {
        return getList(workout, EXERCISES, NAME);
    }

    public static boolean addExercise(String workout, String name, String increment, String incrementSets, String warmupSets, String reps, String rest, String minWeight, String maxWeight) {
        List<String> exercises = getExercises(workout);

        if(name.trim().length() == 0
                || increment.trim().length() == 0
                || incrementSets.trim().length() == 0
                || warmupSets.trim().length() == 0
                || reps.trim().length() == 0
                || rest.trim().length() == 0
                || minWeight.trim().length() == 0
                || maxWeight.trim().length() == 0) {
            LogHelper.error("All preference must contain a value");
            return false;
        }

        return (setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), NAME, name)
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), INCREMENT, increment)
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), INCREMENT_SETS, incrementSets)
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), WARMUP_SETS, warmupSets)
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), REPS, reps)
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), REST, rest)
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), MIN_WEIGHT, minWeight)
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), MAX_WEIGHT, maxWeight)
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), CURRENT_VOLUME, "0")
                && setPreference(buildPreferenceString(workout, EXERCISES, String.valueOf(exercises.size())), FAILED_VOLUME, "0"));
    }

    public static List<String> getSets(String exercise) {
        return getList(exercise, SETS, WEIGHT);
    }

    public static boolean addSet(String exercise, String weight) {
        List<String> sets = getSets(exercise);

        return setPreference(buildPreferenceString(exercise, SETS, String.valueOf(sets.size())), WEIGHT, weight);
    }

    private static List<String> getList(String parent, String child, String preference) {
        List<String> list = new ArrayList<>();

        if(isParentIndexValid(parent, true)) {
            for (int i = 0; ; i++) {
                if (localPreferences.containsKey(buildPreferenceString(parent, child, String.valueOf(i), preference))) {
                    list.add(buildPreferenceString(parent, child, String.valueOf(i)));
                } else {
                    break;
                }
            }
        }

        return list;
    }

    public static String getPreference(String parent, String child) {
        String preference = "";

        if(isParentIndexValid(parent, true)) {
            String preferenceString = buildPreferenceString(parent, child);
            preference = getPreference(preferenceString);
        }

        return preference;
    }

    public static String getPreference(String key) {
        String preference = "";

        if (localPreferences.containsKey(key)) {
            preference = localPreferences.get(key);
        } else {
            LogHelper.error("Invalid preference " + key);
        }

        return preference;
    }

    public static boolean setPreference(String parent, String child, String value) {
        return setPreference(parent, child, value, true);
    }

    private static boolean setPreference(String parent, String child, String value, boolean commit) {
        LogHelper.debug("Setting preference " + parent + ", " + child + ", " + value);

        boolean success = false;

        if (isParentIndexValid(parent, true)) {
            String preferenceString = buildPreferenceString(parent, child);
            success = setPreference(preferenceString, value, commit);
        }

        return success;
    }

    public static boolean setPreference(String key, String value) {
        return setPreference(key, value, true);
    }

    private static boolean setPreference(String key, String value, boolean commit) {
        boolean success = false;

        if(value.trim().length() > 0) {
            localPreferences.put(key, value);

            if(commit) {
                success = commit();

                if (!success) {
                    LogHelper.error("Commit failed for setting preference " + key);
                }
            } else {
                success = true;
            }
        } else {
            LogHelper.error("Value for " + key + " cannot be empty");
        }

        return success;
    }

    private static boolean removePreference(String key, boolean commit) {
        boolean success = false;

        if(!isParentIndexValid(key, false)) {
            if (localPreferences.containsKey(key)) {
                localPreferences.remove(key);

                if(commit) {
                    success = commit();

                    if (!success) {
                        LogHelper.error("Commit failed for removing preference " + key);
                    }
                } else {
                    success = true;
                }
            } else {
                LogHelper.error("Unable to remove preference that does not exist: " + key);
            }
        } else {
            LogHelper.error("Unable to remove preference " + key + ". Try using removePreferenceTree.");
        }

        return success;
    }

    public static boolean removePreferenceTree(String parent) {
        LogHelper.debug("Removing preference tree for " + parent);

        boolean success = true;

        if(parent.length() > 0 && isParentIndexValid(parent, true)) {
            String parentRoot = parent.substring(0, parent.lastIndexOf(SEPARATOR));
            int parentIndex = Integer.valueOf(parent.substring(parent.lastIndexOf(SEPARATOR) + 1));

            int i;
            for(i = parentIndex; parentExists(buildPreferenceString(parentRoot, String.valueOf(i+1))); i++) {
                success = (success
                        && swap(buildPreferenceString(parentRoot, String.valueOf(i)), buildPreferenceString(parentRoot, String.valueOf(i + 1)), false));
            }

            for(Map.Entry<String,String> entry : new HashMap<>(localPreferences).entrySet()) {
                if(entry.getKey().contains(buildPreferenceString(parentRoot, String.valueOf(i)))) {
                    success = (success
                            && removePreference(entry.getKey(), false));
                }
            }
            success = (success
                    && commit());
        } else {
            LogHelper.error("Failed to remove preference tree for " + parent + " because it is not an index");
            success = false;
        }

        return success;
    }

    public static boolean movePreferenceTreeUp(String parent) {
        return movePreferenceTree(parent, -1);
    }

    public static boolean movePreferenceTreeDown(String parent) {
        return movePreferenceTree(parent, 1);
    }

    private static boolean movePreferenceTree(String parent, int direction) {
        LogHelper.debug("Moving preference tree for " + parent);

        boolean success = true;

        if(parent.length() > 0 && isParentIndexValid(parent, true)) {
            String parentRoot = parent.substring(0, parent.lastIndexOf(SEPARATOR));
            int parentIndex = Integer.valueOf(parent.substring(parent.lastIndexOf(SEPARATOR) + 1));

            if(parentExists(buildPreferenceString(parentRoot, String.valueOf(parentIndex + direction)))) {
                success = (swap(buildPreferenceString(parentRoot, String.valueOf(parentIndex)), buildPreferenceString(parentRoot, String.valueOf(parentIndex + direction)), false)
                        && commit());
            }
        } else {
            LogHelper.error("Failed to move preference tree for " + parent + " because it is not an index");
            success = false;
        }

        return success;
    }

    private static boolean parentExists(String parent) {
        for(Map.Entry<String,String> entry : localPreferences.entrySet()) {
            if(entry.getKey().contains(parent)) {
                return true;
            }
        }

        return false;
    }

    private static boolean swap(String parent1, String parent2, boolean commit) {
        LogHelper.debug("Swapping " + parent1 + " and " + parent2);

        return (move(parent1, buildPreferenceString(parent1, SWAP, "0"), commit)
                && move(parent2, parent1, commit)
                && move(buildPreferenceString(parent1, SWAP, "0"), parent2, commit));
    }

    private static boolean move(String fromParent, String toParent, boolean commit) {
        LogHelper.debug("Moving " + fromParent + " to " + toParent);

        boolean success = true;

        if(fromParent.length() > 0 && isParentIndexValid(fromParent, true) && toParent.length() > 0 && isParentIndexValid(toParent, true)) {
            for (Map.Entry<String, String> entry : new HashMap<>(localPreferences).entrySet()) {
                if (entry.getKey().contains(fromParent)) {
                    String tempKey = entry.getKey().replace(fromParent, toParent);
                    success = (success
                            && setPreference(tempKey, entry.getValue(), commit)
                            && removePreference(entry.getKey(), commit));
                }
            }
        } else {
            LogHelper.error("Move aborted. Invalid parents " + fromParent + " " + toParent);
            success = false;
        }

        return success;
    }

    private static boolean isParentIndexValid(String parent, boolean reportError) {
        boolean valid = true;

        try {
            if(parent.length() > 0) {
                Integer.valueOf(parent.substring(parent.lastIndexOf(SEPARATOR)+1));
            }
        } catch(NumberFormatException e) {
            if(reportError) {
                LogHelper.error("Invalid parent index " + parent + ". " + e.toString());
            }
            valid = false;
        }

        return valid;
    }

    public static String buildPreferenceString(String ... strings) {
        String preferenceString = "";
        for(int i = 0; i < strings.length; i++) {
            if(preferenceString.length() > 0) {
                preferenceString += SEPARATOR;
            }
            preferenceString += strings[i];
        }
        return preferenceString;
    }

    private static void createDefaultSharedPreferences() {
        createDefaultSharedPreferences("", SharedPreferencesDefaults.getSharedPreferencesDefaults());
        localPreferences.put(HIIT_GO, "");
        localPreferences.put(HIIT_REST, "");
        localPreferences.put(HIIT_ROUNDS, "");
        commit();
        printSharedPreferences();
    }

    private static void createDefaultSharedPreferences(String parent, Map<String,Object> map) {
        if(isParentIndexValid(parent, true)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    setPreference(parent, entry.getKey(), (String) entry.getValue(), false);
                } else {
                    List<Object> list = (List<Object>) entry.getValue();

                    for (int i = 0; i < list.size(); i++) {
                        createDefaultSharedPreferences(buildPreferenceString(parent, entry.getKey(), String.valueOf(i)), (Map<String, Object>) list.get(i));
                    }
                }
            }
        }
    }

    public static void printSharedPreferences() {
        LogHelper.debug("********** Printing Shared Preferences **********");
        List<String> sharedPreferencesList = new ArrayList<String>();
        for(Map.Entry<String,String> entry : localPreferences.entrySet()) {
            sharedPreferencesList.add(entry.getKey() + " = " + entry.getValue());
        }
        Collections.sort(sharedPreferencesList);
        for(String sharedPreference : sharedPreferencesList) {
            LogHelper.debug(sharedPreference);
        }
        LogHelper.debug("********** Finished Shared Preferences **********");
    }
}
