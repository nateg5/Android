package com.rippedgiantfitness.rippedgiantfitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.rippedgiantfitness.rippedgiantfitness.helper.ActivityHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.LogHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.SharedPreferencesHelper;
import com.rippedgiantfitness.rippedgiantfitness.interfaces.RGFActivity;

public class HiitSettingsActivity extends AppCompatActivity implements RGFActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiit_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    public void init() {
        final Context context = this;

        String go = SharedPreferencesHelper.getPreference(SharedPreferencesHelper.HIIT_GO);
        String rest = SharedPreferencesHelper.getPreference(SharedPreferencesHelper.HIIT_REST);
        String rounds = SharedPreferencesHelper.getPreference(SharedPreferencesHelper.HIIT_ROUNDS);

        final AppCompatEditText editTextGo = ActivityHelper.createEditText(context, go, "Go Time in Seconds", InputType.TYPE_CLASS_NUMBER);
        ((ViewGroup)findViewById(R.id.content_hiit_settings)).addView(editTextGo);

        final AppCompatEditText editTextRest = ActivityHelper.createEditText(context, rest, "Rest Time in Seconds", InputType.TYPE_CLASS_NUMBER);
        ((ViewGroup)findViewById(R.id.content_hiit_settings)).addView(editTextRest);

        final AppCompatEditText editTextRounds = ActivityHelper.createEditText(context, rounds, "Number of Rounds", InputType.TYPE_CLASS_NUMBER);
        ((ViewGroup)findViewById(R.id.content_hiit_settings)).addView(editTextRounds);

        AppCompatButton button = ActivityHelper.createButton(context, "Start", true);
        button.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        button.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        button.setSupportAllCaps(true);
        button.setGravity(Gravity.CENTER);
        button.setOnTouchListener(new View.OnTouchListener() {
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HiitTimerActivity.class);
                if(SharedPreferencesHelper.setPreference(SharedPreferencesHelper.HIIT_GO, editTextGo.getText().toString())
                        && SharedPreferencesHelper.setPreference(SharedPreferencesHelper.HIIT_REST, editTextRest.getText().toString())
                        && SharedPreferencesHelper.setPreference(SharedPreferencesHelper.HIIT_ROUNDS, editTextRounds.getText().toString())) {
                    int go = editTextGo.getText().toString().trim().length() > 0 ? Integer.valueOf(editTextGo.getText().toString()) : 0;
                    int rest = editTextRest.getText().toString().trim().length() > 0 ? Integer.valueOf(editTextRest.getText().toString()) : 0;
                    int rounds = editTextRounds.getText().toString().trim().length() > 0 ? Integer.valueOf(editTextRounds.getText().toString()) : 0;
                    intent.putExtra(SharedPreferencesHelper.HIIT_GO, go);
                    intent.putExtra(SharedPreferencesHelper.HIIT_REST, rest);
                    intent.putExtra(SharedPreferencesHelper.HIIT_ROUNDS, rounds);
                    startActivity(intent);
                } else {
                    LogHelper.error("Failed to save the HIIT settings");
                }
            }
        });
        ((ViewGroup)findViewById(R.id.content_hiit_settings)).addView(button);
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
