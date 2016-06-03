package com.rgf.rippedgiantfitness;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rgf.rippedgiantfitness.constants.Constants;
import com.rgf.rippedgiantfitness.defaults.SettingsDefaults;
import com.rgf.rippedgiantfitness.helper.ActivityHelper;
import com.rgf.rippedgiantfitness.helper.DialogHelper;
import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;
import com.rgf.rippedgiantfitness.interfaces.RGFActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WorkoutActivity extends AppCompatActivity implements RGFActivity {

    private final static ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

        final String workoutIndex = getIntent().getStringExtra(SharedPreferencesHelper.WORKOUTS);
        final String workoutName = SharedPreferencesHelper.getPreference(workoutIndex, SharedPreferencesHelper.NAME);

        if(workoutName.trim().length() > 0) {
            setTitle(workoutName);
        }

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        init();

        String howToWorkout = SharedPreferencesHelper.getPreference(SharedPreferencesHelper.HOW_TO_WORKOUT);
        if(howToWorkout.equals("true")) {
            SharedPreferencesHelper.setPreference(SharedPreferencesHelper.HOW_TO_WORKOUT, "false", Constants.MIN, Constants.MAX);

            DialogHelper.createDialog(
                    context,
                    DialogHelper.HOW_TO,
                    DialogHelper.CLOSE,
                    "",
                    "After completing a set touch the Reps button to enter the number of reps you completed. A rest timer will be started automatically.<br><br>" +
                            "Once you have completed the workout touch the Finish Workout button to save it to the History."
            );
        }
    }

    public void init() {
        final String weightUnit = SharedPreferencesHelper.getPreference(SharedPreferencesHelper.buildPreferenceString(SharedPreferencesHelper.SETTINGS, SettingsDefaults.UNIT, SharedPreferencesHelper.SETTING));

        final Context context = this;
        final String workoutIndex = getIntent().getStringExtra(SharedPreferencesHelper.WORKOUTS);

        final Map<String, Map<String, Boolean>> exerciseMap = new HashMap<>();
        final Map<String, String> historyMap = new HashMap<>();

        historyMap.put(SharedPreferencesHelper.DATE, Constants.DATE_FORMAT.format(new Date()));
        historyMap.put(SharedPreferencesHelper.NAME, getTitle().toString());
        historyMap.put(SharedPreferencesHelper.NOTES, "");

        List<String> exercises = SharedPreferencesHelper.getExercises(workoutIndex);

        for(String exerciseIndex : exercises) {
            final Map<String, Boolean> setMap = new HashMap<>();
            exerciseMap.put(exerciseIndex, setMap);

            final String historyExerciseIndex = exerciseIndex.substring(exerciseIndex.lastIndexOf(SharedPreferencesHelper.EXERCISES));

            historyMap.put(SharedPreferencesHelper.buildPreferenceString(historyExerciseIndex, SharedPreferencesHelper.NAME), SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.NAME));

            /**
             * add exercise title
             */
            String PR = "";
            if(SharedPreferencesHelper.getVolume(exerciseIndex) >= Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.FAILED_VOLUME))) {
                PR = " PR";
            }

            final AppCompatButton buttonExercise = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.NAME) + PR, false);
            buttonExercise.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            buttonExercise.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));

            ((ViewGroup) findViewById(R.id.content_workout)).addView(buttonExercise);
            /**/

            int historySetCount = 0;

            //get all sets
            List<String> sets = SharedPreferencesHelper.getSets(exerciseIndex);

            /**
             * add warm-up sets
             */
            final int numberOfWarmupSets = Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.WARMUP_SETS));
            final int firstSetWeight = Integer.valueOf(SharedPreferencesHelper.getPreference(sets.get(0), SharedPreferencesHelper.WEIGHT));
            final int increment = Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.INCREMENT));
            final int minWeight = Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.MIN_WEIGHT));

            final double warmupPercent = Double.valueOf(SharedPreferencesHelper.getPreference(SharedPreferencesHelper.buildPreferenceString(SharedPreferencesHelper.SETTINGS, SettingsDefaults.WARMUP, SharedPreferencesHelper.SETTING)));

            for(int i = 0; i < numberOfWarmupSets; i++) {
                double halfWeight = firstSetWeight / (100.0 / warmupPercent);
                double warmupIncrement = (firstSetWeight - halfWeight) / numberOfWarmupSets;
                int warmupWeight = (int)((warmupIncrement * i) + halfWeight);
                warmupWeight = increment > 0 ? warmupWeight - (warmupWeight % increment) : warmupWeight;
                warmupWeight = warmupWeight < minWeight ? minWeight : warmupWeight;

                final AppCompatButton buttonWarmupSet = ActivityHelper.createButton(context, "WU " + (i + 1), false);
                final AppCompatButton buttonWarmupWeight = ActivityHelper.createButton(context, warmupWeight + " " + weightUnit, false);

                final String historySetIndex = SharedPreferencesHelper.buildPreferenceString(historyExerciseIndex, SharedPreferencesHelper.SETS, String.valueOf(historySetCount++));
                historyMap.put(SharedPreferencesHelper.buildPreferenceString(historySetIndex, SharedPreferencesHelper.NAME), buttonWarmupSet.getText().toString());
                historyMap.put(SharedPreferencesHelper.buildPreferenceString(historySetIndex, SharedPreferencesHelper.WEIGHT), buttonWarmupWeight.getText().toString());

                final AppCompatButton buttonWarmupReps = createRepsButton(exerciseIndex, null, null, historyMap, historySetIndex);

                final LinearLayoutCompat linearLayout = new LinearLayoutCompat(context);
                linearLayout.setOrientation(LinearLayoutCompat.HORIZONTAL);
                linearLayout.addView(buttonWarmupSet);
                linearLayout.addView(buttonWarmupWeight);
                linearLayout.addView(buttonWarmupReps);

                ((ViewGroup) findViewById(R.id.content_workout)).addView(linearLayout);
            }
            /**/

            /**
             * add working sets
             */
            int setNum = 1;
            for(String setIndex : sets) {
                setMap.put(setIndex, false);

                final AppCompatButton buttonSet = ActivityHelper.createButton(context, "Set " + String.valueOf(setNum++), false);
                final AppCompatButton buttonWeight = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(setIndex, SharedPreferencesHelper.WEIGHT) + " " + weightUnit, false);

                final String historySetIndex = SharedPreferencesHelper.buildPreferenceString(historyExerciseIndex, SharedPreferencesHelper.SETS, String.valueOf(historySetCount++));
                historyMap.put(SharedPreferencesHelper.buildPreferenceString(historySetIndex, SharedPreferencesHelper.NAME), buttonSet.getText().toString());
                historyMap.put(SharedPreferencesHelper.buildPreferenceString(historySetIndex, SharedPreferencesHelper.WEIGHT), buttonWeight.getText().toString());

                final AppCompatButton buttonReps = createRepsButton(exerciseIndex, setMap, setIndex, historyMap, historySetIndex);

                final LinearLayoutCompat linearLayout = new LinearLayoutCompat(context);
                linearLayout.setOrientation(LinearLayoutCompat.HORIZONTAL);
                linearLayout.addView(buttonSet);
                linearLayout.addView(buttonWeight);
                linearLayout.addView(buttonReps);

                ((ViewGroup) findViewById(R.id.content_workout)).addView(linearLayout);
            }
            /**/
        }

        final AppCompatButton buttonNotes = ActivityHelper.createButton(context, SharedPreferencesHelper.NOTES, false);
        buttonNotes.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        buttonNotes.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));

        ((ViewGroup) findViewById(R.id.content_workout)).addView(buttonNotes);

        final AppCompatEditText editTextNotes = ActivityHelper.createEditText(context, "", "Add notes", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editTextNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                historyMap.put(SharedPreferencesHelper.NOTES, s.toString());
            }
        });

        ((ViewGroup) findViewById(R.id.content_workout)).addView(editTextNotes);

        final AppCompatButton buttonFinish = ActivityHelper.createButton(context, "Finish Workout", true);
        buttonFinish.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        buttonFinish.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        buttonFinish.setSupportAllCaps(true);
        buttonFinish.setGravity(Gravity.CENTER);
        buttonFinish.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                }
                return false;
            }
        });
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = DialogHelper.createDialog(context, "Finish Workout", DialogHelper.YES, DialogHelper.NO, "Finish workout and auto-update weights/reps?");
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean finishWorkout = true;
                        for (Map.Entry<String, Map<String, Boolean>> exerciseEntry : exerciseMap.entrySet()) {
                            boolean success = true;
                            String exerciseIndex = exerciseEntry.getKey();
                            for (Map.Entry<String, Boolean> setEntry : exerciseEntry.getValue().entrySet()) {
                                success = (success && setEntry.getValue());
                            }
                            finishWorkout = (finishWorkout && SharedPreferencesHelper.finishWorkout(exerciseIndex, success));
                        }
                        finishWorkout = (finishWorkout && SharedPreferencesHelper.addHistory(workoutIndex, historyMap));
                        dialog.dismiss();
                        if (finishWorkout) {
                            finish();
                        } else {
                            LogHelper.error("Failed to save and update the workout");
                        }
                    }
                });
            }
        });

        ((ViewGroup) findViewById(R.id.content_workout)).addView(buttonFinish);
    }

    private AppCompatButton createRepsButton(final String exerciseIndex, final Map<String, Boolean> setMap, final String setIndex, final Map<String, String> historyMap, final String historySetIndex) {
        final Context context = this;
        int reps = Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.REPS));
        AppCompatButton buttonReps = ActivityHelper.createButton(context, getResources().getQuantityString(R.plurals.reps, reps, reps), true);
        buttonReps.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1));
        buttonReps.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray));

        historyMap.put(SharedPreferencesHelper.buildPreferenceString(historySetIndex, SharedPreferencesHelper.REPS), getResources().getQuantityString(R.plurals.reps, 0, 0));

        buttonReps.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (((AppCompatButton) v).getCurrentTextColor() == ContextCompat.getColor(context, R.color.colorAccentText)) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccentDark));
                    } else if (((AppCompatButton) v).getCurrentTextColor() == ContextCompat.getColor(context, R.color.colorGreenText)) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreenDark));
                    } else {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGray));
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (((AppCompatButton) v).getCurrentTextColor() == ContextCompat.getColor(context, R.color.colorAccentText)) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                    } else if (((AppCompatButton) v).getCurrentTextColor() == ContextCompat.getColor(context, R.color.colorGreenText)) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
                    }else {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray));
                    }
                }
                return false;
            }
        });
        buttonReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int textColor = ((AppCompatButton) v).getCurrentTextColor();
                String text = ((AppCompatButton) v).getText().toString();
                int reps = Integer.valueOf(text.substring(0, text.indexOf(" ")));

                if (textColor == ContextCompat.getColor(context, R.color.colorAccentText) || textColor == ContextCompat.getColor(context, R.color.colorGreenText)) {
                    if (reps == 0) {
                        reps = Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.REPS));

                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray));
                        ((AppCompatButton) v).setTextColor(ActivityHelper.getButton(context).getCurrentTextColor());
                        ((AppCompatButton) v).setText(getResources().getQuantityString(R.plurals.reps, reps, reps));

                        historyMap.put(SharedPreferencesHelper.buildPreferenceString(historySetIndex, SharedPreferencesHelper.REPS), getResources().getQuantityString(R.plurals.reps, 0, 0));
                    } else {
                        reps = reps - 1;

                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                        ((AppCompatButton) v).setTextColor(ContextCompat.getColor(context, R.color.colorAccentText));
                        ((AppCompatButton) v).setText(getResources().getQuantityString(R.plurals.reps, reps, reps));

                        historyMap.put(SharedPreferencesHelper.buildPreferenceString(historySetIndex, SharedPreferencesHelper.REPS), ((AppCompatButton) v).getText().toString());
                    }
                    if(setMap != null && setIndex != null) {
                        setMap.put(setIndex, false);
                    }
                } else {
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
                    ((AppCompatButton) v).setTextColor(ContextCompat.getColor(context, R.color.colorGreenText));

                    historyMap.put(SharedPreferencesHelper.buildPreferenceString(historySetIndex, SharedPreferencesHelper.REPS), ((AppCompatButton) v).getText().toString());

                    if(setMap != null && setIndex != null) {
                        setMap.put(setIndex, true);
                    }
                }

                final Snackbar snackbar = Snackbar.make(v, "Rest Time: " + SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.REST), Snackbar.LENGTH_INDEFINITE);

                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                });

                snackbar.show();
                snackbarTimer(snackbar, Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.REST)));
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });

        return buttonReps;
    }

    private void snackbarTimer(final Snackbar snackbar, final int seconds) {
        if(snackbar.isShownOrQueued()) {
            if (seconds > 0) {
                snackbar.setText("Rest Time: " + String.valueOf(seconds));
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                snackbarTimer(snackbar, seconds - 1);
                            }
                        });
                    }
                }, 1000);
            } else {
                snackbar.dismiss();
                beep(2);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        } else {
            LogHelper.debug("snackbar is not shown");
        }
    }

    private void beep(final int times) {
        if(times > 0) {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    beep(times - 1);
                }
            }, 1000);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if(intent.getComponent().getClassName().equals(WorkoutsActivity.class.getName())) {
            finish();
        } else {
            super.startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
