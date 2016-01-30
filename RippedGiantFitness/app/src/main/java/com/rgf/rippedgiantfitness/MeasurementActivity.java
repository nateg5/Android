package com.rgf.rippedgiantfitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.rgf.rippedgiantfitness.helper.ActivityHelper;
import com.rgf.rippedgiantfitness.helper.DialogHelper;
import com.rgf.rippedgiantfitness.helper.LogHelper;
import com.rgf.rippedgiantfitness.helper.SharedPreferencesHelper;
import com.rgf.rippedgiantfitness.interfaces.RGFActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MeasurementActivity extends AppCompatActivity implements RGFActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;
        final AppCompatActivity activity = this;
        final String measurementIndex = getIntent().getStringExtra(SharedPreferencesHelper.MEASUREMENTS);
        final String measurementName = SharedPreferencesHelper.getPreference(measurementIndex, SharedPreferencesHelper.NAME);

        if(measurementName.trim().length() > 0) {
            setTitle(measurementName);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd");
                final AppCompatEditText editTextDate = DialogHelper.createEditText(context, dateFormat.format(new Date()), SharedPreferencesHelper.DATE, InputType.TYPE_CLASS_DATETIME);
                final AppCompatEditText editTextEntry = DialogHelper.createEditText(context, "", getTitle().toString(), InputType.TYPE_CLASS_NUMBER);

                final AlertDialog dialog = DialogHelper.createDialog(context, DialogHelper.CREATE, DialogHelper.CREATE, DialogHelper.CANCEL, editTextDate, editTextEntry);

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SharedPreferencesHelper.addEntry(measurementIndex, editTextDate.getText().toString(), editTextEntry.getText().toString())) {
                            ((ViewGroup) findViewById(R.id.content_measurement)).removeAllViews();
                            init();
                            dialog.dismiss();
                        } else {
                            LogHelper.error("Failed to add the entry");
                        }
                    }
                });
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    public void init() {
        final Context context = this;
        final AppCompatActivity activity = this;
        final String measurementIndex = getIntent().getStringExtra(SharedPreferencesHelper.MEASUREMENTS);

        List<String> entries = SharedPreferencesHelper.getEntries(measurementIndex);

        int entryCount = 0;
        int entryTotal = 0;
        int entryCurrent = 0;
        Date entryCurrentDate = null;
        int entryAverage = 0;
        int entryHigh = 0;
        int entryLow = 0;
        for(String entry : entries) {
            String entryIndex = entry;
            String entryDate = SharedPreferencesHelper.getPreference(entryIndex, SharedPreferencesHelper.DATE);
            String entryValue = SharedPreferencesHelper.getPreference(entryIndex, SharedPreferencesHelper.ENTRY);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

            try {
                int oneWeek = (7 * 24 * 60 * 60 * 1000);

                Date dateEntry = dateFormat.parse(entryDate);
                Date dateWeek = new Date(System.currentTimeMillis() - oneWeek);

                if(dateEntry.after(dateWeek)) {
                    entryCount++;
                    entryTotal += Integer.valueOf(entryValue);
                    entryHigh = entryHigh == 0 || Integer.valueOf(entryValue) > entryHigh ? Integer.valueOf(entryValue) : entryHigh;
                    entryLow = entryLow == 0 || Integer.valueOf(entryValue) < entryLow ? Integer.valueOf(entryValue) : entryLow;
                }

                if(entryCurrentDate == null || dateEntry.after(entryCurrentDate) || dateEntry.equals(entryCurrentDate)) {
                    entryCurrent = Integer.valueOf(entryValue);
                    entryCurrentDate = dateEntry;
                }

            } catch(ParseException e) {
                LogHelper.error(e.toString());
            }
        }
        entryAverage = entryCount > 0 ? entryTotal / entryCount : entryAverage;

        AppCompatButton buttonCurrent = ActivityHelper.createButton(context, DialogHelper.CURRENT + ": " + entryCurrent, false);
        AppCompatButton buttonAverage = ActivityHelper.createButton(context, DialogHelper.AVERAGE + ": " + entryAverage, false);
        AppCompatButton buttonHigh = ActivityHelper.createButton(context, DialogHelper.HIGH + ": " + entryHigh, false);
        AppCompatButton buttonLow = ActivityHelper.createButton(context, DialogHelper.LOW + ": " + entryLow, false);

        ((ViewGroup)findViewById(R.id.content_measurement)).addView(buttonCurrent);
        ((ViewGroup)findViewById(R.id.content_measurement)).addView(buttonAverage);
        ((ViewGroup)findViewById(R.id.content_measurement)).addView(buttonHigh);
        ((ViewGroup)findViewById(R.id.content_measurement)).addView(buttonLow);

        for(String entry : entries) {
            final String entryIndex = entry;
            final String date = SharedPreferencesHelper.getPreference(entryIndex, SharedPreferencesHelper.DATE) + ": ";

            final AppCompatButton buttonEntry = ActivityHelper.createButton(context, date + SharedPreferencesHelper.getPreference(entryIndex, SharedPreferencesHelper.ENTRY), true);
            buttonEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogHelper.createEditDialog(context, R.id.content_measurement, entryIndex, getTitle().toString(), SharedPreferencesHelper.ENTRY, InputType.TYPE_CLASS_NUMBER);
                }
            });
            buttonEntry.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog dialog = DialogHelper.createDialog(context, date + SharedPreferencesHelper.getPreference(entryIndex, SharedPreferencesHelper.ENTRY), DialogHelper.MENU);
                    dialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String text = ((AppCompatTextView) view).getText().toString();
                            switch (text) {
                                case DialogHelper.MOVE_UP:
                                    ActivityHelper.moveUp(activity, R.id.content_measurement, entryIndex);
                                    dialog.dismiss();
                                    break;
                                case DialogHelper.MOVE_DOWN:
                                    ActivityHelper.moveDown(activity, R.id.content_measurement, entryIndex);
                                    dialog.dismiss();
                                    break;
                                case DialogHelper.EDIT:
                                    DialogHelper.createEditDialog(context, R.id.content_measurement, entryIndex, getTitle().toString(), SharedPreferencesHelper.ENTRY, InputType.TYPE_CLASS_NUMBER);
                                    dialog.dismiss();
                                    break;
                                case DialogHelper.REMOVE:
                                    DialogHelper.createRemoveDialog(activity, R.id.content_measurement, entryIndex, date, SharedPreferencesHelper.ENTRY);
                                    dialog.dismiss();
                                    break;
                                default:
                                    LogHelper.error("Received unknown menu selection: " + text);
                            }
                        }
                    });
                    return true;
                }
            });

            ((ViewGroup)findViewById(R.id.content_measurement)).addView(buttonEntry);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if(intent.getComponent().getClassName().equals(MeasurementsActivity.class.getName())) {
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
