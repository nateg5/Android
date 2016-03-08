package com.rgf.rippedgiantfitness.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.rgf.rippedgiantfitness.defaults.HiitDefaults;
import com.rgf.rippedgiantfitness.defaults.MeasurementsDefaults;
import com.rgf.rippedgiantfitness.defaults.ProgramsDefaults;
import com.rgf.rippedgiantfitness.defaults.SettingsDefaults;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public final static String WARMUP_SETS = "Warmup Sets";
    public final static String WEIGHT = "Weight";
    public final static String SEPARATOR = ".";
    public final static String SWAP = "Swap";

    public final static String MEASUREMENTS = "Measurements";
    public final static String ENTRIES = "Entries";
    public final static String DATE = "Date";
    public final static String ENTRY = "Entry";

    public final static String HIIT_TIMER = "HIIT Timer";
    public final static String HIIT_GO = "HIIT Go";
    public final static String HIIT_REST = "HIIT Rest";
    public final static String HIIT_ROUNDS = "HIIT Rounds";

    public final static String INSTAGRAM = "Instagram";
    public final static String MY_FITNESS_PAL = "MyFitnessPal";

    public final static String BACKUP_FOLDER = "RippedGiantFitness";
    public final static String BACKUP_FILE = "RippedGiantFitness.bak";
    public final static String BACKUP = "Data Backup";
    public final static String RESTORE = "Data Restore";

    public final static String SETTINGS = "Settings";
    public final static String SETTING = "Setting";
    public final static String SETTING_HINT = "Setting Hint";
    public final static String SETTING_TYPE = "Setting Type";

    public final static String HELP = "Help";

    private static Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Map<String, String> localPreferences;


    public static void init(Context c) {
        context = c;
        sharedPreferences = context.getSharedPreferences(PACKAGE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        localPreferences = (Map<String, String>) sharedPreferences.getAll();

        ProgramsDefaults.create();
        MeasurementsDefaults.create();
        HiitDefaults.create();
        SettingsDefaults.create();
    }

    public static Context getContext() {
        return context;
    }

    public static Map<String,String> getLocalPreferences() {
        return localPreferences;
    }

    public static boolean commit() {
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

        if(success) {
            retVal = (retVal && increaseVolumeOnePercent(exercise));
        } else {
            retVal = (retVal
                    && setPreference(exercise, FAILED_VOLUME, String.valueOf(getVolume(exercise)))
                    && decreaseVolumeTenPercent(exercise));
        }

        retVal = (retVal
                && setPreference(exercise, CURRENT_VOLUME, String.valueOf(getVolume(exercise)))
                && commit());

        return retVal;
    }

    private static boolean increaseVolumeOnePercent(String exercise) {
        boolean success = true;

        double incrementPercent = Double.valueOf(getPreference(buildPreferenceString(SETTINGS, SettingsDefaults.INCREMENT, SETTING)));
        incrementPercent = 1 + (incrementPercent / 100);

        double targetVolume = getVolume(exercise) * incrementPercent;

        while(getVolume(exercise) < targetVolume) {
            success = (success && increaseVolume(exercise));
        }

        return success;
    }

    private static boolean decreaseVolumeTenPercent(String exercise) {
        boolean success = true;

        double decrementPercent = Double.valueOf(getPreference(buildPreferenceString(SETTINGS, SettingsDefaults.DECREMENT, SETTING)));
        decrementPercent = 1 - (decrementPercent / 100);

        double targetVolume = getVolume(exercise) * decrementPercent;

        while(getVolume(exercise) > targetVolume && getVolume(exercise) > getVolumeMin(exercise)) {
            success = (success && decreaseVolume(exercise));
        }

        return success;
    }

    private static boolean increaseVolume(String exercise) {
        boolean success = true;

        int minWeight = Integer.valueOf(getPreference(exercise, MIN_WEIGHT));
        int maxWeight = Integer.valueOf(getPreference(exercise, MAX_WEIGHT));
        int increment = Integer.valueOf(getPreference(exercise, INCREMENT));
        int reps = Integer.valueOf(getPreference(exercise, REPS));

        List<String> sets = getSets(exercise);

        int lastSetWeight = Integer.valueOf(getPreference(sets.get(sets.size() - 1), WEIGHT));
        if (lastSetWeight < maxWeight && increment > 0) {
            for (int i = sets.size() - 1; i >= 0; i--) {
                int setWeight = Integer.valueOf(getPreference(sets.get(i), WEIGHT));
                if (i == 0 || setWeight < Integer.valueOf(getPreference(sets.get(i - 1), WEIGHT))) {
                    setWeight = setWeight + increment;
                    if (setWeight > maxWeight) {
                        setWeight = maxWeight;
                    }
                    success = (success && setPreference(sets.get(i), WEIGHT, String.valueOf(setWeight), false));
                    break;
                }
            }
        } else {
            if(reps < sets.size()) {
                success = (success && setPreference(exercise, REPS, String.valueOf(reps + 1), false));
            } else if (sets.size() < reps) {
                success = (success && addSet(exercise, String.valueOf(lastSetWeight)));
            } else {
                success = (success && setPreference(exercise, REPS, String.valueOf(reps + 1), false));
                success = (success && addSet(exercise, String.valueOf(lastSetWeight)));
            }
            if(minWeight < maxWeight) {
                decreaseVolumeTenPercent(exercise);
            }
        }

        return success;
    }

    private static boolean decreaseVolume(String exercise) {
        boolean success = true;

        int minWeight = Integer.valueOf(getPreference(exercise, MIN_WEIGHT));
        int maxWeight = Integer.valueOf(getPreference(exercise, MAX_WEIGHT));
        int increment = Integer.valueOf(getPreference(exercise, INCREMENT));
        int reps = Integer.valueOf(getPreference(exercise, REPS));

        List<String> sets = getSets(exercise);

        int firstSetWeight = Integer.valueOf(getPreference(sets.get(0), WEIGHT));
        if (firstSetWeight > minWeight && increment > 0) {
            for (int i = 0; i < sets.size(); i++) {
                int setWeight = Integer.valueOf(getPreference(sets.get(i), WEIGHT));
                if (i == (sets.size() - 1) || setWeight > Integer.valueOf(getPreference(sets.get(i + 1), WEIGHT))) {
                    setWeight = setWeight - increment;
                    if (setWeight < minWeight) {
                        setWeight = minWeight;
                    }
                    success = (success && setPreference(sets.get(i), WEIGHT, String.valueOf(setWeight), false));
                    break;
                }
            }
        } else {
            if(reps > sets.size() && reps > 1) {
                success = (success && setPreference(exercise, REPS, String.valueOf(reps - 1), false));
            } else if (sets.size() > reps && sets.size() > 1) {
                success = (success && removePreferenceTree(sets.get(sets.size() - 1)));
            } else {
                if(reps > 1) {
                    success = (success && setPreference(exercise, REPS, String.valueOf(reps - 1), false));
                }
                if(sets.size() > 1) {
                    success = (success && removePreferenceTree(sets.get(sets.size() - 1)));
                }
            }
        }

        return success;
    }

    public static int getVolume(String exercise) {
        int volume = 0;

        int reps = Integer.valueOf(getPreference(exercise, REPS));

        List<String> sets = getSets(exercise);

        for(int i = 0; i < sets.size(); i++) {
            int setWeight = Integer.valueOf(getPreference(sets.get(i), WEIGHT));

            if(setWeight <= 0) {
                setWeight = 1;
            }

            volume += setWeight * reps;
        }

        return volume;
    }

    private static int getVolumeMin(String exercise) {
        int volumeMin = Integer.valueOf(getPreference(exercise, MIN_WEIGHT));

        if(volumeMin <= 0) {
            volumeMin = 1;
        }

        return volumeMin;
    }

    public static List<String> getExercises(String workout) {
        return getList(workout, EXERCISES, NAME);
    }

    public static boolean addExercise(String workout, String name, String increment, String warmupSets, String reps, String rest, String minWeight, String maxWeight) {
        List<String> exercises = getExercises(workout);

        if(name.trim().length() == 0
                || increment.trim().length() == 0
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

    public static List<String> getMeasurements() {
        return getList("", MEASUREMENTS, NAME);
    }

    public static boolean addMeasurement(String name) {
        List<String> measurements = getMeasurements();

        return setPreference(buildPreferenceString(MEASUREMENTS, String.valueOf(measurements.size())), NAME, name);
    }

    public static List<String> getEntries(String measurement) {
        return getList(measurement, ENTRIES, DATE);
    }

    public static boolean addEntry(String measurement, String date, String entry) {
        List<String> entries = getEntries(measurement);

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date dateObj = dateFormat.parse(date);
            if(!date.equals(dateFormat.format(dateObj))) {
                LogHelper.error("Invalid date format: " + date);
                return false;
            }
        } catch(ParseException e) {
            LogHelper.error("Invalid date format: " + date + ". " + e.toString());
            return false;
        }

        if(date.trim().length() == 0 || entry.trim().length() == 0) {
            LogHelper.error("All preference must contain a value");
            return false;
        }

        return (setPreference(buildPreferenceString(measurement, ENTRIES, String.valueOf(entries.size())), DATE, date)
                && setPreference(buildPreferenceString(measurement, ENTRIES, String.valueOf(entries.size())), ENTRY, entry));
    }

    public static List<String> getSettings() {
        return getList("", SETTINGS, NAME);
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

    public static boolean setPreference(String parent, String child, String value, boolean commit) {
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

    public static boolean setPreference(String key, String value, boolean commit) {
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

    public static boolean copyPreferenceTree(String parent) {
        LogHelper.debug("Copying preference tree for " + parent);

        boolean success = true;

        if(parent.length() > 0 && isParentIndexValid(parent, true)) {
            String parentRoot = parent.substring(0, parent.lastIndexOf(SEPARATOR));
            int parentIndex = Integer.valueOf(parent.substring(parent.lastIndexOf(SEPARATOR) + 1));

            for(int i = parentIndex + 1; ; i++) {
                if (!parentExists(buildPreferenceString(parentRoot, String.valueOf(i)))) {
                    success = (copy(buildPreferenceString(parentRoot, String.valueOf(parentIndex)), buildPreferenceString(parentRoot, String.valueOf(i)), false)
                            && commit());
                    break;
                }
            }
        } else {
            LogHelper.error("Failed to copy preference tree for " + parent + " because it is not an index");
            success = false;
        }

        return success;
    }

    private static boolean copy(String fromParent, String toParent, boolean commit) {
        LogHelper.debug("Copying " + fromParent + " to " + toParent);

        boolean success = true;

        if(fromParent.length() > 0 && isParentIndexValid(fromParent, true) && toParent.length() > 0 && isParentIndexValid(toParent, true)) {
            for (Map.Entry<String, String> entry : new HashMap<>(localPreferences).entrySet()) {
                if (entry.getKey().contains(fromParent)) {
                    String tempKey = entry.getKey().replace(fromParent, toParent);
                    success = (success
                            && setPreference(tempKey, entry.getValue(), commit));
                }
            }
        } else {
            LogHelper.error("Copy aborted. Invalid parents " + fromParent + " " + toParent);
            success = false;
        }

        return success;
    }

    public static boolean isParentIndexValid(String parent, boolean reportError) {
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

    public static boolean backup() {
        boolean success = false;

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalStorage = Environment.getExternalStorageDirectory();
            File backupFolder = new File(externalStorage, BACKUP_FOLDER);
            File backupFile = new File(backupFolder, BACKUP_FILE);

            try {
                backupFolder.mkdirs();
                backupFile.createNewFile();

                FileOutputStream oStream = new FileOutputStream(backupFile);

                for (Map.Entry<String, String> entry : localPreferences.entrySet()) {
                    oStream.write(entry.getKey().getBytes());
                    oStream.write("\n".getBytes());
                    oStream.write(entry.getValue().getBytes());
                    oStream.write("\n".getBytes());
                }

                oStream.close();

                success = true;
            } catch (FileNotFoundException e) {
                LogHelper.error(e.toString());
            } catch (IOException e) {
                LogHelper.error(e.toString());
            }
        } else {
            LogHelper.error("SD Card is not mounted.");
        }

        return success;
    }

    public static boolean restore() {
        boolean success = false;

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalStorage = Environment.getExternalStorageDirectory();
            File backupFolder = new File(externalStorage, BACKUP_FOLDER);
            File backupFile = new File(backupFolder, BACKUP_FILE);

            try {
                FileInputStream iStream = new FileInputStream(backupFile);

                byte[] bytes = new byte[(int) backupFile.length()];
                iStream.read(bytes);
                iStream.close();

                localPreferences.clear();

                boolean savingKey = true;
                String key = "";
                String value = "";
                for (byte b : bytes) {
                    if (b == '\n') {
                        if (savingKey) {
                            savingKey = false;
                        } else {
                            localPreferences.put(key, value);
                            savingKey = true;
                            key = "";
                            value = "";
                        }
                    } else {
                        if (savingKey) {
                            key += (char) b;
                        } else {
                            value += (char) b;
                        }
                    }
                }

                success = commit();
            } catch (FileNotFoundException e) {
                LogHelper.toast("The data backup " + BACKUP_FILE + " does not exist. First use " + BACKUP + ". " + e.toString());
            } catch (IOException e) {
                LogHelper.error(e.toString());
            }
        } else {
            LogHelper.error("SD Card is not mounted.");
        }

        return success;
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
