package com.rippedgiantfitness.rippedgiantfitness.helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.rippedgiantfitness.rippedgiantfitness.R;
import com.rippedgiantfitness.rippedgiantfitness.interfaces.RGFActivity;

/**
 * Created by Nate on 11/27/2015.
 */
public class ActivityHelper {

    public static AppCompatButton createButton(final Context context, String text, boolean clickable) {
        AppCompatButton button = new AppCompatButton(context);
        button.setText(text);
        button.setSupportAllCaps(false);
        button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
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
        AppCompatButton button = new AppCompatButton(context);

        return button;
    }

    public static AppCompatEditText createEditText(Context context, String text, String hint, int inputType) {
        AppCompatEditText editText = new AppCompatEditText(context);
        editText.setText(text);
        editText.setHint(hint);
        editText.setInputType(inputType);

        return editText;
    }

    public static AppCompatButton createEditButton(final AppCompatActivity activity, final int resId, final String index, final String label, final String preference, final int inputType, final boolean clickable) {
        final AppCompatButton button = createButton(activity, label + ": " + SharedPreferencesHelper.getPreference(index, preference), clickable);
        if(clickable) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AppCompatEditText editText = DialogHelper.createEditText(activity, SharedPreferencesHelper.getPreference(index, preference), label, inputType);

                    final AlertDialog dialogEdit = DialogHelper.createDialog(activity, DialogHelper.EDIT, DialogHelper.SAVE, DialogHelper.CANCEL, editText);

                    dialogEdit.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SharedPreferencesHelper.setPreference(index, preference, editText.getText().toString())) {
                                button.setText(label + ": " + editText.getText().toString());
                                dialogEdit.dismiss();
                            } else {
                                LogHelper.error("Failed to save " + SharedPreferencesHelper.buildPreferenceString(index, preference));
                            }
                        }
                    });
                }
            });
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AppCompatEditText editText = DialogHelper.createEditText(activity, SharedPreferencesHelper.getPreference(index, preference), label, inputType);

                    final AlertDialog dialogEdit = DialogHelper.createDialog(activity, DialogHelper.EDIT, DialogHelper.SAVE, DialogHelper.CANCEL, editText);

                    dialogEdit.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SharedPreferencesHelper.setPreference(index, preference, editText.getText().toString())) {
                                button.setText(label + ": " + editText.getText().toString());
                                dialogEdit.dismiss();
                            } else {
                                LogHelper.error("Failed to save " + SharedPreferencesHelper.buildPreferenceString(index, preference));
                            }
                        }
                    });
                    return true;
                }
            });
        }
        ((ViewGroup)activity.findViewById(resId)).addView(button);

        return button;
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
}
