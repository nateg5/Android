package com.rgf.rippedgiantfitness.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.rgf.rippedgiantfitness.R;
import com.rgf.rippedgiantfitness.interfaces.RGFActivity;

/**
 * Created by Nate on 11/28/2015.
 *
 * Ripped Giant Fitness
 * RippedGiantFitness@gmail.com
 * https://www.instagram.com/rippedgiantfitness/
 */
public class DialogHelper {

    public final static String START_WORKOUT = "Start Workout";
    public final static String HISTORY = "History";
    public final static String MOVE_UP = "Move Up";
    public final static String MOVE_DOWN = "Move Down";
    public final static String COPY = "Copy";
    public final static String EDIT = "Edit";
    public final static String REMOVE = "Remove";
    public final static String PROGRAM_NAME = "Program Name";
    public final static String WORKOUT_NAME = "Workout Name";
    public final static String EXERCISE_NAME = "Exercise Name";
    //public final static String CURRENT_VOLUME = "Current Volume";
    //public final static String FAILED_VOLUME = "Failed Volume";
    public final static String WEIGHT_INCREMENT = "Weight Increment";
    public final static String SETS_WARMUP = "Number of Warmup Sets";
    public final static String NUMBER_OF_REPS = "Number of Reps";
    public final static String REST_IN_SECONDS = "Rest in Seconds";
    public final static String MIN_WEIGHT = "Minimum Weight";
    public final static String MAX_WEIGHT = "Maximum Weight";
    public final static String SET_WEIGHT = "New Set Weight";
    public final static String MEASUREMENT_NAME = "Measurement Name";
    public final static String CURRENT = "Current";
    public final static String AVERAGE = "7 Day Average";
    public final static String HIGH = "7 Day High";
    public final static String LOW = "7 Day Low";
    public final static String CREATE = "Create";
    public final static String SAVE = "Save";
    private final static String CANCEL = "Cancel";
    public final static String YES = "Yes";
    public final static String NO = "No";
    public final static String CLOSE = "Close";
    public final static String HOW_TO = "How To";

    public final static String[] MENU = {MOVE_UP, MOVE_DOWN, COPY, EDIT, REMOVE};
    public final static String[] WORKOUT_MENU = {START_WORKOUT, HISTORY, MOVE_UP, MOVE_DOWN, COPY, EDIT, REMOVE};
    public final static String[] HISTORY_MENU = {MOVE_UP, MOVE_DOWN, REMOVE};
    public final static String[] MEASUREMENT_MENU = {MOVE_UP, MOVE_DOWN, EDIT, REMOVE};

    public static AppCompatEditText createEditText(Context context, String text, String hint, int inputType) {
        AppCompatEditText editText = new AppCompatEditText(context);
        editText.setText(text);
        editText.setHint(hint);
        editText.setInputType(inputType);

        return editText;
    }

    public static AlertDialog createDialog(Context context, String title, String posBtnText, View ... views) {
        LinearLayoutCompat linearLayout = new LinearLayoutCompat(context);
        linearLayout.setOrientation(LinearLayoutCompat.VERTICAL);
        for(View view : views) {
            linearLayout.addView(view);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(linearLayout);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(scrollView, getPadding(context).getPaddingLeft(), getPadding(context).getPaddingTop(), getPadding(context).getPaddingRight(), getPadding(context).getPaddingBottom())
                .setPositiveButton(posBtnText, null)
                .setNegativeButton(CANCEL, null)
                .create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

        return dialog;
    }

    public static AlertDialog createDialog(Context context, String title, String posBtnText, String negBtnText, String message) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(posBtnText, null)
                .setNegativeButton(negBtnText, null)
                .create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

        return dialog;
    }

    public static AlertDialog createDialog(Context context, String title, String[] items) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(items, null)
                .setNegativeButton(CLOSE, null)
                .create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

        return dialog;
    }

    public static void createEditDialog(final Context context, final AppCompatButton button, final String index, final String label1, final String label2, final String label3, final String preference, final int inputType, final int min, final int max) {
        final AppCompatEditText editText = createEditText(context, SharedPreferencesHelper.getPreference(index, preference), label1, inputType);

        final AlertDialog dialogEdit = createDialog(context, EDIT, SAVE, editText);

        dialogEdit.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean outOfRange = false;

                try {
                    int value = Integer.valueOf(editText.getText().toString());

                    if(value < min || value > max) {
                        outOfRange = true;
                    }
                } catch(NumberFormatException ignored) { }

                if(!outOfRange) {
                    if (SharedPreferencesHelper.setPreference(index, preference, editText.getText().toString())) {
                        button.setText(context.getString(R.string.label_text_label, label2, editText.getText().toString(), label3));
                        dialogEdit.dismiss();
                    } else {
                        LogHelper.error("Failed to save " + SharedPreferencesHelper.buildPreferenceString(index, preference));
                    }
                } else {
                    LogHelper.error("Value is out of range. Min = " + min + ", Max = " + max);
                }
            }
        });
    }

    public static void createMeasurementEditDialog(final Context context, final String index, final String hint, final int inputType) {
        final AppCompatEditText editText = createEditText(context, SharedPreferencesHelper.getPreference(index, SharedPreferencesHelper.ENTRY), hint, inputType);

        final AlertDialog dialogEdit = createDialog(context, EDIT, SAVE, editText);

        dialogEdit.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferencesHelper.setPreference(index, SharedPreferencesHelper.ENTRY, editText.getText().toString())) {
                    ((ViewGroup)((AppCompatActivity)context).findViewById(R.id.content_measurement)).removeAllViews();
                    ((RGFActivity)context).init();
                    dialogEdit.dismiss();
                } else {
                    LogHelper.error("Failed to save " + SharedPreferencesHelper.buildPreferenceString(index, SharedPreferencesHelper.ENTRY));
                }
            }
        });
    }

    public static void createRemoveDialog(final AppCompatActivity activity, final int resId, final String index, final String label, final String unit, final String preference) {
        String message = "Are you sure you want to permanently remove " + label + SharedPreferencesHelper.getPreference(index, preference) + " " + unit + "?";

        final AlertDialog dialogRemove = createDialog(activity, REMOVE, YES, NO, message);

        dialogRemove.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferencesHelper.removePreferenceTree(index)) {
                    ((ViewGroup)activity.findViewById(resId)).removeAllViews();
                    ((RGFActivity)activity).init();
                    dialogRemove.dismiss();
                } else {
                    LogHelper.error("Failed to remove " + SharedPreferencesHelper. buildPreferenceString(index, preference));
                }
            }
        });
    }

    private static View getPadding(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .create();

        dialog.show();
        dialog.dismiss();

        return dialog.findViewById(android.R.id.message);
    }
}
