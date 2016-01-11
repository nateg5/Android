package com.rippedgiantfitness.rippedgiantfitness;

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
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rippedgiantfitness.rippedgiantfitness.helper.ActivityHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.DialogHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.LogHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.SharedPreferencesHelper;
import com.rippedgiantfitness.rippedgiantfitness.interfaces.RGFActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WorkoutActivity extends AppCompatActivity implements RGFActivity {

    static ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    public void init() {
        final Context context = this;
        final AppCompatActivity activity = this;
        final String workoutIndex = getIntent().getStringExtra(SharedPreferencesHelper.WORKOUTS);

        final Map<String, Map<String, Boolean>> exerciseMap = new HashMap<>();

        List<String> exercises = SharedPreferencesHelper.getExercises(workoutIndex);

        for(String exercise : exercises) {
            final String exerciseIndex = exercise;

            final Map<String, Boolean> setMap = new HashMap<>();
            exerciseMap.put(exerciseIndex, setMap);

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

            //get all sets
            List<String> sets = SharedPreferencesHelper.getSets(exerciseIndex);

            /**
             * add warm-up sets
             */
            final int numberOfWarmupSets = Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.WARMUP_SETS));
            final int firstSetWeight = Integer.valueOf(SharedPreferencesHelper.getPreference(sets.get(0), SharedPreferencesHelper.WEIGHT));
            final int increment = Integer.valueOf(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.INCREMENT));

            for(int i = 0; i < numberOfWarmupSets; i++) {
                float halfWeight = (float)firstSetWeight / (float)2;
                float warmupIncrement = halfWeight / (float)numberOfWarmupSets;
                int warmupWeight = (int)((warmupIncrement * (float)i) + halfWeight);
                warmupWeight = warmupWeight - (warmupWeight % increment);

                final AppCompatButton buttonWarmupSet = ActivityHelper.createButton(context, "WU " + (i + 1), false);
                final AppCompatButton buttonWarmupWeight = ActivityHelper.createButton(context, warmupWeight + " Lbs", false);

                final AppCompatButton buttonWarmupReps = createRepsButton(exerciseIndex, null, null);

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
            for(String set : sets) {
                final String setIndex = set;

                setMap.put(setIndex, false);

                final AppCompatButton buttonSet = ActivityHelper.createButton(context, "Set " + String.valueOf(setNum++), false);
                final AppCompatButton buttonWeight = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(setIndex, SharedPreferencesHelper.WEIGHT) + " Lbs", false);

                final AppCompatButton buttonReps = createRepsButton(exerciseIndex, setMap, setIndex);

                final LinearLayoutCompat linearLayout = new LinearLayoutCompat(context);
                linearLayout.setOrientation(LinearLayoutCompat.HORIZONTAL);
                linearLayout.addView(buttonSet);
                linearLayout.addView(buttonWeight);
                linearLayout.addView(buttonReps);

                ((ViewGroup) findViewById(R.id.content_workout)).addView(linearLayout);
            }
            /**/
        }

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
                final AlertDialog dialog = DialogHelper.createDialog(context, "Finish Workout", DialogHelper.YES, DialogHelper.NO, "Finish workout and auto-update sets/reps/weights?");
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

    private AppCompatButton createRepsButton(final String exerciseIndex, final Map<String, Boolean> setMap, final String setIndex) {
        final Context context = this;
        AppCompatButton buttonReps = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.REPS) + " Reps", true);
        buttonReps.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1));
        buttonReps.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray));
        buttonReps.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (((AppCompatButton) v).getCurrentTextColor() == ContextCompat.getColor(context, R.color.colorWhite)) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccentDark));
                    } else {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGray));
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (((AppCompatButton) v).getCurrentTextColor() == ContextCompat.getColor(context, R.color.colorWhite)) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                    } else {
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

                if (textColor == ContextCompat.getColor(context, R.color.colorWhite)) {
                    if (reps == 0) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray));
                        ((AppCompatButton) v).setTextColor(ActivityHelper.getButton(context).getCurrentTextColor());
                        ((AppCompatButton) v).setText(SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.REPS) + " Reps");
                    } else {
                        ((AppCompatButton) v).setText((reps - 1) + " Reps");
                    }
                    if(setMap != null && setIndex != null) {
                        setMap.put(setIndex, false);
                    }
                } else {
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                    ((AppCompatButton) v).setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
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
