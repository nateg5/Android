package com.rgf.rippedgiantfitness;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import java.util.Random;

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
                Intent intent = new Intent(context, MeasurementsActivity.class);
                startActivity(intent);
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

        AppCompatButton buttonMfp = ActivityHelper.createButton(context, SharedPreferencesHelper.MY_FITNESS_PAL, true);
        buttonMfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.myfitnesspal.com/food/diary");
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.myfitnesspal.android", "com.myfitnesspal.android.login.Welcome"));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonMfp);

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

        AppCompatButton buttonRateAmazon = ActivityHelper.createButton(context, SharedPreferencesHelper.RATE_AMAZON, true);
        buttonRateAmazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("amzn://apps/android?p=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonRateAmazon);

        AppCompatButton buttonRateGoogle = ActivityHelper.createButton(context, SharedPreferencesHelper.RATE_GOOGLE, true);
        buttonRateGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        //((ViewGroup)findViewById(R.id.content_home)).addView(buttonRateGoogle);

        AppCompatButton buttonSettings = ActivityHelper.createButton(context, SharedPreferencesHelper.SETTINGS, true);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonSettings);

        AppCompatButton buttonHelp = ActivityHelper.createButton(context, SharedPreferencesHelper.HELP, true);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HelpActivity.class);
                startActivity(intent);
            }
        });

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonHelp);

        ((ViewGroup)findViewById(R.id.content_home)).addView(ActivityHelper.getSeparatorView(context));

        String verse = "";
        int rand = new Random().nextInt(3);
        if(rand == 0) {
            verse = "\"Do you not know that you are a temple of God and that the Spirit of God dwells in you? If any man destroys the temple of God, God will destroy him, for the temple of God is holy, and that is what you are.\" - 1 Corinthians 3:16-17 (NASB)";
        } else if(rand == 1) {
            verse = "\"Or do you not know that your body is a temple of the Holy Spirit who is in you, whom you have from God, and that you are not your own? For you have been bought with a price: therefore glorify God in your body.\" - 1 Corinthians 6:19-20 (NASB)";
        } else {
            verse = "\"Therefore I run in such a way, as not without aim; I box in such a way, as not beating the air; but I discipline my body and make it my slave, so that, after I have preached to others, I myself will not be disqualified.\" - 1 Corinthians 9:26-27 (NASB)";
        }

        AppCompatButton buttonVerse = ActivityHelper.createButton(context, verse, false);
        buttonVerse.setTextColor(ContextCompat.getColor(context, R.color.colorGray));

        ((ViewGroup)findViewById(R.id.content_home)).addView(buttonVerse);
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
