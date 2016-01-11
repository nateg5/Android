package com.rippedgiantfitness.rippedgiantfitness;

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

import com.rippedgiantfitness.rippedgiantfitness.helper.ActivityHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.DialogHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.LogHelper;
import com.rippedgiantfitness.rippedgiantfitness.helper.SharedPreferencesHelper;
import com.rippedgiantfitness.rippedgiantfitness.interfaces.RGFActivity;

import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements RGFActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;
        final AppCompatActivity activity = this;
        final String exerciseIndex = getIntent().getStringExtra(SharedPreferencesHelper.EXERCISES);
        final String exerciseName = SharedPreferencesHelper.getPreference(exerciseIndex, SharedPreferencesHelper.NAME);

        if(exerciseName.trim().length() > 0) {
            setTitle(exerciseName);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppCompatEditText editText = DialogHelper.createEditText(context, "", DialogHelper.SET_WEIGHT, InputType.TYPE_CLASS_NUMBER);

                final AlertDialog dialog = DialogHelper.createDialog(context, DialogHelper.CREATE, DialogHelper.CREATE, DialogHelper.CANCEL, editText);

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SharedPreferencesHelper.addSet(exerciseIndex, editText.getText().toString())) {
                            ((ViewGroup)findViewById(R.id.content_exercise)).removeAllViews();
                            init();
                            dialog.dismiss();
                        } else {
                            LogHelper.error("Failed to add the set");
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
        final String exerciseIndex = getIntent().getStringExtra(SharedPreferencesHelper.EXERCISES);

        List<String> sets = SharedPreferencesHelper.getSets(exerciseIndex);

        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.CURRENT_VOLUME, SharedPreferencesHelper.CURRENT_VOLUME, InputType.TYPE_CLASS_NUMBER, false);
        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.FAILED_VOLUME, SharedPreferencesHelper.FAILED_VOLUME, InputType.TYPE_CLASS_NUMBER, false);
        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.WEIGHT_INCREMENT, SharedPreferencesHelper.INCREMENT, InputType.TYPE_CLASS_NUMBER, true);
        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.SETS_INCREMENT, SharedPreferencesHelper.INCREMENT_SETS, InputType.TYPE_CLASS_NUMBER, true);
        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.SETS_WARMUP, SharedPreferencesHelper.WARMUP_SETS, InputType.TYPE_CLASS_NUMBER, true);
        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.NUMBER_OF_REPS, SharedPreferencesHelper.REPS, InputType.TYPE_CLASS_NUMBER, true);
        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.REST_IN_SECONDS, SharedPreferencesHelper.REST, InputType.TYPE_CLASS_NUMBER, true);
        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.MIN_WEIGHT, SharedPreferencesHelper.MIN_WEIGHT, InputType.TYPE_CLASS_NUMBER, true);
        ActivityHelper.createEditButton(activity, R.id.content_exercise, exerciseIndex, DialogHelper.MAX_WEIGHT, SharedPreferencesHelper.MAX_WEIGHT, InputType.TYPE_CLASS_NUMBER, true);

        int setNum = 1;
        for(String set : sets) {
            final String setIndex = set;
            final String setNumber = "Set " + String.valueOf(setNum++) + ": ";

            final AppCompatButton buttonSet = ActivityHelper.createButton(context, setNumber + SharedPreferencesHelper.getPreference(setIndex, SharedPreferencesHelper.WEIGHT), true);
            buttonSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogHelper.createEditDialog(context, buttonSet, setIndex, DialogHelper.SET_WEIGHT, setNumber, SharedPreferencesHelper.WEIGHT, InputType.TYPE_CLASS_NUMBER);
                }
            });
            buttonSet.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog dialog = DialogHelper.createDialog(context, setNumber + SharedPreferencesHelper.getPreference(setIndex, SharedPreferencesHelper.WEIGHT), DialogHelper.MENU);
                    dialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String text = ((AppCompatTextView) view).getText().toString();
                            switch (text) {
                                case DialogHelper.MOVE_UP:
                                    ActivityHelper.moveUp(activity, R.id.content_exercise, setIndex);
                                    dialog.dismiss();
                                    break;
                                case DialogHelper.MOVE_DOWN:
                                    ActivityHelper.moveDown(activity, R.id.content_exercise, setIndex);
                                    dialog.dismiss();
                                    break;
                                case DialogHelper.EDIT:
                                    DialogHelper.createEditDialog(context, buttonSet, setIndex, DialogHelper.SET_WEIGHT, setNumber, SharedPreferencesHelper.WEIGHT, InputType.TYPE_CLASS_NUMBER);
                                    dialog.dismiss();
                                    break;
                                case DialogHelper.REMOVE:
                                    DialogHelper.createRemoveDialog(activity, R.id.content_exercise, setIndex, setNumber, SharedPreferencesHelper.WEIGHT);
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

            ((ViewGroup)findViewById(R.id.content_exercise)).addView(buttonSet);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if(intent.getComponent().getClassName().equals(ExercisesActivity.class.getName())) {
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
