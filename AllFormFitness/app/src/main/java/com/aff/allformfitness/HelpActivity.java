package com.aff.allformfitness;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.aff.allformfitness.helper.ActivityHelper;
import com.aff.allformfitness.helper.SharedPreferencesHelper;
import com.aff.allformfitness.interfaces.AFFActivity;

public class HelpActivity extends AppCompatActivity implements AFFActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        init();
    }

    public void init() {
        Context context = this;

        AppCompatButton buttonGeneralTitle = ActivityHelper.createButton(
                context,
                "General",
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonGeneralTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonGeneralContent = ActivityHelper.createButton(
                context,
                "On any screen you can touch an item to select it. On most screens you can also " +
                        "touch and hold an item to bring up a menu of actions that can be performed " +
                        "on that item.<br><br>" +
                        "The first time the app is started default Programs and Measurements are " +
                        "created. These can be modified, removed or used as is. The default Programs " +
                        "are intended to be used in an \"N on 1 off\" schedule, where N is the number " +
                        "of Workouts in the Programs. For example, the \"Free Weight\" Programs would be " +
                        "schedule 4 days on and 1 day off.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonGeneralContent);

        AppCompatButton buttonProgramsTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.PROGRAMS,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonProgramsTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonProgramsContent = ActivityHelper.createButton(
                context,
                "Programs store all of your Workout and Exercise information. You can create, remove " +
                        "and reorder Programs. Each Program can have multiple Workouts, and each " +
                        "Workout can have multiple Exercises. The Exercise settings allow you to " +
                        "create a custom workout that fits your specific needs.<br><br>" +
                        "Touch <img src='" + R.drawable.ic_message_play + "'> to start a workout. After each set touch the Reps button " +
                        "for that set to select how many reps you completed. A rest timer will " +
                        "automatically be started between each set. Once you have completed all sets " +
                        "touch the Finish Workout button. This will auto increment or decrement your " +
                        "weights and reps based on how many sets and reps you completed.<br><br>" +
                        "By default, all Workouts will use a 20/1 progressive overload approach for auto " +
                        "decrementing and " +
                        "incrementing the weights and reps. This means that if you fail to " +
                        "complete " +
                        "all of the sets and reps on an Exercise the total volume (sets * reps * " +
                        "weights) of that Exercise will be decreased by 20%. If you succeed at " +
                        "completing all of the " +
                        "sets and reps on an Exercise the total volume of that Exercise will be " +
                        "increased by 1%. The auto increment and decrement percentages can be modified " +
                        "in the Settings. This will prevent overtraining plateaus and allow for " +
                        "continuous progression.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonProgramsContent);

        AppCompatButton buttonMeasurementsTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.MEASUREMENTS,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonMeasurementsTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonMeasurementsContent = ActivityHelper.createButton(
                context,
                "Measurements are used to track your body's progress, such as weight or body fat " +
                        "percentage. Measurements can be added and removed and will provide you " +
                        "with the current, 7 day average, 7 day high and 7 day low value for each " +
                        "Measurement.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonMeasurementsContent);

        AppCompatButton buttonHiitTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.HIIT_TIMER,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonHiitTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonHiitContent = ActivityHelper.createButton(
                context,
                "HIIT Timer is a simple high intensity interval training timer to be used for " +
                        "cardio. Enter the Go time, Rest time, number of Rounds and touch the Start " +
                        "button. The timer will beep at the beginning of each time interval so that " +
                        "you can focus on your workout and not your phone.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonHiitContent);

        /*AppCompatButton buttonInstagramTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.INSTAGRAM,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonInstagramTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonInstagramContent = ActivityHelper.createButton(
                context,
                "Instagram will open the All Form Fitness Instagram page in the Instagram app. " +
                        "If you do not have the Instagram app installed then it will open in your " +
                        "web browser.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonInstagramContent);*/

        /*AppCompatButton buttonMfpTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.MY_FITNESS_PAL,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonMfpTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonMfpContent = ActivityHelper.createButton(
                context,
                "MyFitnessPal will open the MyFitnessPal app. " +
                        "If you do not have the MyFitnessPal app installed then it will open in your " +
                        "web browser.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonMfpContent);*/

        AppCompatButton buttonDataBackupTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.BACKUP,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonDataBackupTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonDataBackupContent = ActivityHelper.createButton(
                context,
                "Data Backup will backup all of the user data including Programs, Measurements and " +
                        "HIIT Timer settings to phone storage at " +
                        "AllFormFitness/AllFormFitness.bak. This backup file can be used " +
                        "to transfer settings to a new phone or backup your settings in case the app " +
                        "settings are lost.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonDataBackupContent);

        AppCompatButton buttonDataRestoreTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.RESTORE,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonDataRestoreTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonDataRestoreContent = ActivityHelper.createButton(
                context,
                "Data Restore will restore all of the user data including Programs, Measurements and " +
                        "HIIT Timer settings from phone storage at " +
                        "AllFormFitness/AllFormFitness.bak. This restore will overwrite all " +
                        "current user settings in the app.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonDataRestoreContent);

        AppCompatButton buttonRateAmazonTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.RATE_AMAZON,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonRateAmazonTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonRateAmazonContent = ActivityHelper.createButton(
                context,
                "Rate on Amazon Appstore will open the app page in Amazon Appstore. From here you can " +
                        "rate the app and provide a written review of the things you like and " +
                        "dislike about the app. Feedback from reviews will be used to make future " +
                        "changes and improvements to the app.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonRateAmazonContent);

        /*AppCompatButton buttonRateGoogleTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.RATE_GOOGLE,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonRateGoogleTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonRateGoogleContent = ActivityHelper.createButton(
                context,
                "Rate on Google Play will open the app page in Google Play. From here you can " +
                        "rate the app and provide a written review of the things you like and " +
                        "dislike about the app. Feedback from reviews will be used to make future " +
                        "changes and improvements to the app.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonRateGoogleContent);*/

        AppCompatButton buttonSettingsTitle = ActivityHelper.createButton(
                context,
                SharedPreferencesHelper.SETTINGS,
                Typeface.DEFAULT_BOLD,
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonSettingsTitle);

        ((ViewGroup)findViewById(R.id.content_help)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonSettingsContent = ActivityHelper.createButton(
                context,
                "Settings will open the global settings for the app. The global settings will " +
                        "apply to all Programs, Workouts and Exercises.",
                false);

        ((ViewGroup)findViewById(R.id.content_help)).addView(buttonSettingsContent);
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
