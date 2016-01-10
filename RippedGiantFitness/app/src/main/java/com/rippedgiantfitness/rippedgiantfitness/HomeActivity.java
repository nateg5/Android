package com.rippedgiantfitness.rippedgiantfitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.rippedgiantfitness.rippedgiantfitness.helper.ActivityHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.SharedPreferencesHelper;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        final Context context = this;
        final AppCompatActivity activity = this;

        SharedPreferencesHelper.init(context);

        AppCompatButton buttonPrograms = ActivityHelper.createButton(context, SharedPreferencesHelper.PROGRAMS, true);
        buttonPrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProgramsActivity.class);
                startActivity(intent);
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonPrograms);

        AppCompatButton buttonHiit = ActivityHelper.createButton(context, SharedPreferencesHelper.HIIT_TIMER, true);
        buttonHiit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HiitSettingsActivity.class);
                startActivity(intent);
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonHiit);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
