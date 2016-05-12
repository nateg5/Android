package com.rgf.rippedgiantfitness.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.rgf.rippedgiantfitness.R;
import com.rgf.rippedgiantfitness.interfaces.RGFActivity;

/**
 * Created by Nate on 11/27/2015.
 *
 * Ripped Giant Fitness
 * RippedGiantFitness@gmail.com
 * https://www.instagram.com/rippedgiantfitness/
 */
public class ActivityHelper {

    public static AppCompatButton createButton(final Context context, String text, boolean clickable) {
        return createButton(context, text, Typeface.DEFAULT, clickable);
    }

    public static AppCompatButton createButton(final Context context, String text, Typeface typeface, boolean clickable) {
        AppCompatButton button = new AppCompatButton(context);
        button.setText(text);
        button.setSupportAllCaps(false);
        button.setTypeface(typeface);
        button.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        button.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
        if(clickable) {
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray));
                    } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                    }
                    return false;
                }
            });
        }
        button.setPadding(getButton(context).getPaddingLeft(), getButton(context).getPaddingTop(), getButton(context).getPaddingRight(), getButton(context).getPaddingBottom());

        return button;
    }

    public static AppCompatButton getButton(Context context) {
        return new AppCompatButton(context);
    }

    public static AppCompatEditText createEditText(Context context, String text, String hint) {
        AppCompatEditText editText = new AppCompatEditText(context);
        editText.setText(text);
        editText.setHint(hint);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        return editText;
    }

    public static void createExerciseEditButton(final AppCompatActivity activity, final String index, final String label, final String unit, final String preference, final int min, final int max) {
        final AppCompatButton button = createButton(activity, label + ": " + SharedPreferencesHelper.getPreference(index, preference) + " " + unit, true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AppCompatEditText editText = DialogHelper.createEditText(activity, SharedPreferencesHelper.getPreference(index, preference), label, InputType.TYPE_CLASS_NUMBER);

                final AlertDialog dialogEdit = DialogHelper.createDialog(activity, DialogHelper.EDIT, DialogHelper.SAVE, editText);

                dialogEdit.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SharedPreferencesHelper.setPreference(index, preference, editText.getText().toString(), min, max)) {
                            button.setText(activity.getString(R.string.label_text_unit, label, editText.getText().toString(), unit));
                            dialogEdit.dismiss();
                        } else {
                            LogHelper.error("Failed to save " + label);
                        }
                    }
                });
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AppCompatEditText editText = DialogHelper.createEditText(activity, SharedPreferencesHelper.getPreference(index, preference), label, InputType.TYPE_CLASS_NUMBER);

                final AlertDialog dialogEdit = DialogHelper.createDialog(activity, DialogHelper.EDIT, DialogHelper.SAVE, editText);

                dialogEdit.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SharedPreferencesHelper.setPreference(index, preference, editText.getText().toString(), min, max)) {
                            button.setText(activity.getString(R.string.label_text_unit, label, editText.getText().toString(), unit));
                            dialogEdit.dismiss();
                        } else {
                            LogHelper.error("Failed to save " + label);
                        }
                    }
                });
                return true;
            }
        });

        ((ViewGroup)activity.findViewById(R.id.content_exercise)).addView(button);
    }

    public static void moveUp(final AppCompatActivity activity, final int resId, final String index) {
        if (SharedPreferencesHelper.movePreferenceTreeUp(index)) {
            ((ViewGroup)activity.findViewById(resId)).removeAllViews();
            ((RGFActivity)activity).init();
        } else {
            LogHelper.error("Failed to move up " + index);
        }
    }

    public static void moveDown(final AppCompatActivity activity, final int resId, final String index) {
        if (SharedPreferencesHelper.movePreferenceTreeDown(index)) {
            ((ViewGroup)activity.findViewById(resId)).removeAllViews();
            ((RGFActivity)activity).init();
        } else {
            LogHelper.error("Failed to move down " + index);
        }
    }

    public static void copy(final AppCompatActivity activity, final int resId, final String index) {
        if (SharedPreferencesHelper.copyPreferenceTree(index)) {
            ((ViewGroup)activity.findViewById(resId)).removeAllViews();
            ((RGFActivity)activity).init();
        } else {
            LogHelper.error("Failed to copy " + index);
        }
    }

    public static View getSeparatorView(Context context) {
        View separatorView = new View(context);
        separatorView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray));
        separatorView.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, 1));

        return separatorView;
    }
}
