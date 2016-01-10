package com.rippedgiantfitness.rippedgiantfitness;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rippedgiantfitness.rippedgiantfitness.helper.ActivityHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.LogHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.SharedPreferencesHelper;
import com.rippedgiantfitness.rippedgiantfitness.interfaces.RGFActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HiitTimerActivity extends AppCompatActivity implements RGFActivity {

    boolean finished = false;
    int go;
    int rest;
    int rounds;
    AppCompatButton buttonRound;
    AppCompatButton buttonAction;
    AppCompatButton buttonTime;
    List<Map<String, Integer>> list = new ArrayList<>();
    ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiit_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        init();
    }

    public void init() {
        final Context context = this;

        go = getIntent().getIntExtra(SharedPreferencesHelper.HIIT_GO, 0);
        rest = getIntent().getIntExtra(SharedPreferencesHelper.HIIT_REST, 0);
        rounds = getIntent().getIntExtra(SharedPreferencesHelper.HIIT_ROUNDS, 0);

        for(int i = 0; i < rounds; i++) {
            Map<String, Integer> map = new HashMap<>();
            map.put(SharedPreferencesHelper.HIIT_GO, go);
            map.put(SharedPreferencesHelper.HIIT_REST, rest);

            list.add(map);
        }

        buttonRound = ActivityHelper.createButton(context, "Round: ", false);
        ((ViewGroup)findViewById(R.id.content_hiit_timer)).addView(buttonRound);

        buttonAction = ActivityHelper.createButton(context, "Action: ", false);
        ((ViewGroup)findViewById(R.id.content_hiit_timer)).addView(buttonAction);

        buttonTime = ActivityHelper.createButton(context, "Time: ", false);
        ((ViewGroup)findViewById(R.id.content_hiit_timer)).addView(buttonTime);

        continueTimer();
    }

    private void continueTimer() {
        if(list.size() > 0) {
            buttonRound.setText("Round: " + (rounds - list.size() + 1));
            int time = 0;
            if(list.get(0).get(SharedPreferencesHelper.HIIT_GO) != null) {
                time = list.get(0).get(SharedPreferencesHelper.HIIT_GO);
                list.get(0).remove(SharedPreferencesHelper.HIIT_GO);
                buttonAction.setText("Action: Go");
            } else {
                time = list.get(0).get(SharedPreferencesHelper.HIIT_REST);
                list.remove(0);
                buttonAction.setText("Action: Rest");
            }
            if(time > 0) {
                timer(time);
            } else {
                continueTimer();
            }
        } else {
            finish();
        }
    }

    private void timer(final int seconds) {
        if(!finished) {
            if (seconds > 0) {
                buttonTime.setText("Time: " + String.valueOf(seconds));
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timer(seconds - 1);
                            }
                        });
                    }
                }, 1000);
            } else {
                beep(2);
                continueTimer();
            }
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
        if(intent.getComponent().getClassName().equals(HiitSettingsActivity.class.getName())) {
            finish();
        } else {
            super.startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void finish() {
        finished = true;
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
