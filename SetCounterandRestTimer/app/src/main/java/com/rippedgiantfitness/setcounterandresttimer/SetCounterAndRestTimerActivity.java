package com.rippedgiantfitness.setcounterandresttimer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class SetCounterAndRestTimerActivity extends AppCompatActivity {


    static int sets = 0;
    static int rest = 0;
    static int setsCompleted = 0;
    static int setsRemaining = 0;
    static boolean resting = false;

    static AppCompatTextView tSets;
    static AppCompatTextView tRest;
    static AppCompatTextView tSetsCompleted;
    static AppCompatTextView tSetsRemaining;
    static AppCompatButton bRest;
    static LinearLayout linearLayout;
    static ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_counter_and_rest_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sets = getIntent().getIntExtra(HomeActivity.SETS, 0);
        rest = getIntent().getIntExtra(HomeActivity.REST, 0);

        setsCompleted = 0;
        setsRemaining = sets;

        tSets = new AppCompatTextView(this);
        tSets.setText("Total Sets: " + sets);
        tSets.setPadding(5, 0, 0, 10);

        tRest = new AppCompatTextView(this);
        tRest.setText("Rest Time: " + rest + " seconds");
        tRest.setPadding(5, 0, 0, 10);

        tSetsCompleted = new AppCompatTextView(this);
        tSetsCompleted.setText("Sets Completed: " + setsCompleted);
        tSetsCompleted.setPadding(5, 0, 0, 10);

        tSetsRemaining = new AppCompatTextView(this);
        tSetsRemaining.setText("Sets Remaining: " + setsRemaining);
        tSetsRemaining.setPadding(5, 0, 0, 10);

        bRest = new AppCompatButton(this);
        bRest.setText("Completed Set 1");
        bRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resting) {
                    if (setsRemaining > 0) {
                        resting = true;

                        setsCompleted += 1;
                        setsRemaining -= 1;

                        tSetsCompleted.setText("Sets Completed: " + setsCompleted);
                        tSetsRemaining.setText("Sets Remaining: " + setsRemaining);

                        timer(rest);
                    } else {
                        finish();
                    }
                }
            }
        });

        linearLayout = (LinearLayout)findViewById(R.id.content_set_counter_and_rest_timer);
        linearLayout.addView(tSets);
        linearLayout.addView(tRest);
        linearLayout.addView(tSetsCompleted);
        linearLayout.addView(tSetsRemaining);
        linearLayout.addView(bRest);
    }

    void timer(final int seconds) {
        Log.d("DEBUG", "timer");
        if(resting) {
            if (seconds > 0 && setsRemaining > 0) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                bRest.setText("Resting " + String.valueOf(seconds) + " seconds");
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
                if (setsRemaining > 0) {
                    bRest.setText("Completed Set " + (setsCompleted + 1));
                } else {
                    bRest.setText("Done");
                }
                beep(2);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                resting = false;
            }
        }
    }

    void beep(final int times) {
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
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("DEBUG", "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putInt("sets", sets);
        outState.putInt("rest", rest);
        outState.putInt("setsCompleted", setsCompleted);
        outState.putInt("setsRemaining", setsRemaining);
        outState.putBoolean("resting", resting);

        outState.putString("bRest", bRest.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("DEBUG", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);

        sets = savedInstanceState.getInt("sets");
        rest = savedInstanceState.getInt("rest");
        setsCompleted = savedInstanceState.getInt("setsCompleted");
        setsRemaining = savedInstanceState.getInt("setsRemaining");
        resting = savedInstanceState.getBoolean("resting");

        tSetsCompleted.setText("Sets Completed: " + setsCompleted);
        tSetsRemaining.setText("Sets Remaining: " + setsRemaining);

        bRest.setText(savedInstanceState.getString("bRest"));
    }

    @Override
    public void startActivity(Intent intent) {
        if(intent.getComponent().getClassName().equals(HomeActivity.class.getName())) {
            finish();
        } else {
            super.startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void finish() {
        Log.d("DEBUG", "finish");
        super.finish();
        resting = false;
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
