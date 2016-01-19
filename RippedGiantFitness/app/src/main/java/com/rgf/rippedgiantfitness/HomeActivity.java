package com.rgf.rippedgiantfitness;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.rgf.rippedgiantfitness.helper.ActivityHelper;
import com.rgf.rippedgiantfitness.helper.DialogHelper;
import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;

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

        AppCompatButton buttonMeasurements = ActivityHelper.createButton(context, SharedPreferencesHelper.MEASUREMENTS, true);
        buttonMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonMeasurements);

        AppCompatButton buttonHiit = ActivityHelper.createButton(context, SharedPreferencesHelper.HIIT_TIMER, true);
        buttonHiit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HiitSettingsActivity.class);
                startActivity(intent);
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonHiit);

        ((ViewGroup)findViewById(R.id.content_home)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonInstagram = ActivityHelper.createButton(context, SharedPreferencesHelper.INSTAGRAM, true);
        buttonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/_u/rippedgiantfitness/");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonInstagram);

        ((ViewGroup)findViewById(R.id.content_home)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonBackup = ActivityHelper.createButton(context, SharedPreferencesHelper.BACKUP, true);
        buttonBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = DialogHelper.createDialog(context, SharedPreferencesHelper.BACKUP, DialogHelper.YES, DialogHelper.NO, "Are you sure you want to backup all of your data to " + SharedPreferencesHelper.BACKUP_FILE + "? This will overwrite the current backup if it exists. Continue?");
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SharedPreferencesHelper.backup()) {
                            LogHelper.toast("Data backup complete!");
                            dialog.dismiss();
                        } else {
                            LogHelper.error("Data backup failed.");
                        }
                    }
                });
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonBackup);

        AppCompatButton buttonRestore = ActivityHelper.createButton(context, SharedPreferencesHelper.RESTORE, true);
        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = DialogHelper.createDialog(context, SharedPreferencesHelper.RESTORE, DialogHelper.YES, DialogHelper.NO, "Are you sure you want to restore all of your data from " + SharedPreferencesHelper.BACKUP_FILE + "? This will overwrite all current app data. Continue?");
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SharedPreferencesHelper.restore()) {
                            LogHelper.toast("Data restore complete!");
                            dialog.dismiss();
                        } else {
                            LogHelper.error("Data restore failed.");
                        }
                    }
                });
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonRestore);

        ((ViewGroup)findViewById(R.id.content_home)).addView(ActivityHelper.getSeparatorView(context));

        AppCompatButton buttonHelp = ActivityHelper.createButton(context, SharedPreferencesHelper.HELP, true);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonHelp);
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
