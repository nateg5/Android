package com.rgf.rippedgiantfitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.rgf.rippedgiantfitness.helper.ActivityHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;
import com.rgf.rippedgiantfitness.interfaces.RGFActivity;

import java.util.List;

public class HistActivity extends AppCompatActivity implements RGFActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String historyIndex = getIntent().getStringExtra(SharedPreferencesHelper.HISTORY);
        final String historyName = SharedPreferencesHelper.getPreference(historyIndex, SharedPreferencesHelper.NAME);
        final String historyDate = SharedPreferencesHelper.getPreference(historyIndex, SharedPreferencesHelper.DATE);

        if(historyName.trim().length() > 0 && historyDate.trim().length() > 0) {
            setTitle(historyName + " " + historyDate);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    public void init() {
        final Context context = this;
        final AppCompatActivity activity = this;
        final String historyIndex = getIntent().getStringExtra(SharedPreferencesHelper.HISTORY);

        List<String> exercises = SharedPreferencesHelper.getExercises(historyIndex);

        for(String exercise : exercises) {
            final String exerciseIndex = exercise;

            final AppCompatButton buttonExercise = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.NAME), false);
            buttonExercise.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            buttonExercise.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));

            ((ViewGroup) findViewById(R.id.content_hist)).addView(buttonExercise);

            List<String> sets = SharedPreferencesHelper.getSets(exerciseIndex);

            for(String set : sets) {
                final String setIndex = set;

                final AppCompatButton buttonSet = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(setIndex, SharedPreferencesHelper.NAME), false);
                final AppCompatButton buttonWeight = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(setIndex, SharedPreferencesHelper.WEIGHT), false);
                final AppCompatButton buttonReps = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(setIndex, SharedPreferencesHelper.REPS), false);

                final LinearLayoutCompat linearLayout = new LinearLayoutCompat(context);
                linearLayout.setOrientation(LinearLayoutCompat.HORIZONTAL);
                linearLayout.addView(buttonSet);
                linearLayout.addView(buttonWeight);
                linearLayout.addView(buttonReps);

                ((ViewGroup) findViewById(R.id.content_hist)).addView(linearLayout);
            }
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if(intent.getComponent().getClassName().equals(HistoryActivity.class.getName())) {
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
