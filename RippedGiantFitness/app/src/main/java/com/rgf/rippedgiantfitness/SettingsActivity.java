package com.rgf.rippedgiantfitness;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rgf.rippedgiantfitness.helper.ActivityHelper;
import com.rgf.rippedgiantfitness.helper.DialogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;
import com.rgf.rippedgiantfitness.interfaces.RGFActivity;

import java.util.List;

public class SettingsActivity extends AppCompatActivity implements RGFActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    public void init() {
        final Context context = this;
        final AppCompatActivity activity = this;

        List<String> settings =  SharedPreferencesHelper.getSettings();

        for(String setting : settings) {
            final String settingIndex = setting;

            final AppCompatButton buttonHeading = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(settingIndex, SharedPreferencesHelper.NAME), Typeface.DEFAULT_BOLD, false);

            ((ViewGroup)findViewById(R.id.content_settings)).addView(buttonHeading);

            ((ViewGroup)findViewById(R.id.content_settings)).addView(ActivityHelper.getSeparatorView(context));

            final AppCompatButton buttonSetting = ActivityHelper.createButton(context, SharedPreferencesHelper.getPreference(settingIndex, SharedPreferencesHelper.SETTING), true);
            buttonSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogHelper.createEditDialog(context, buttonSetting, settingIndex, SharedPreferencesHelper.getPreference(settingIndex, SharedPreferencesHelper.SETTING_HINT), "", "", SharedPreferencesHelper.SETTING, Integer.valueOf(SharedPreferencesHelper.getPreference(settingIndex, SharedPreferencesHelper.SETTING_TYPE)));
                }
            });
            buttonSetting.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogHelper.createEditDialog(context, buttonSetting, settingIndex, SharedPreferencesHelper.getPreference(settingIndex, SharedPreferencesHelper.SETTING_HINT), "", "", SharedPreferencesHelper.SETTING, Integer.valueOf(SharedPreferencesHelper.getPreference(settingIndex, SharedPreferencesHelper.SETTING_TYPE)));
                    return true;
                }
            });

            ((ViewGroup)findViewById(R.id.content_settings)).addView(buttonSetting);
        }
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
