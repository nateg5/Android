package com.example.nate.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.transition.Scene;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);

        final TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        Button button = new Button(this);
        //button.setTextSize(40);
        button.setText("Clear Message");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferencesHelper.setMessage(view.getContext(), "");
                textView.setText("");
            }
        });

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(Color.LTGRAY);
                } else {
                    v.setBackgroundColor(Color.TRANSPARENT);
                }
                return false;
            }
        };

        Button buttonProgram = new Button(this);
        buttonProgram.setText("HyperWork1000asdfasdfasdf");
        //buttonProgram.setSupportAllCaps(false);
        //buttonProgram.setPadding(10, 10, 10, 10);
        buttonProgram.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        buttonProgram.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        buttonProgram.setBackgroundResource(0);
        //buttonProgram.setBackgroundColor(Color.LTGRAY);
        buttonProgram.setOnTouchListener(onTouchListener);

        Button buttonEdit = new Button(this);
        buttonEdit.setText("Edit");
        //buttonEdit.setSupportAllCaps(false);
        //buttonEdit.setPadding(10, 10, 10, 10);
        buttonEdit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
        buttonEdit.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        buttonEdit.setBackgroundResource(0);
        //buttonEdit.setBackgroundColor(Color.LTGRAY);
        buttonEdit.setOnTouchListener(onTouchListener);

        Button buttonRemove = new Button(this);
        buttonRemove.setText("Remove");
        //buttonRemove.setSupportAllCaps(false);
        //buttonRemove.setPadding(10, 10, 10, 10);
        buttonRemove.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
        buttonRemove.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        buttonRemove.setBackgroundResource(0);
        //buttonRemove.setBackgroundColor(Color.LTGRAY);
        buttonRemove.setOnTouchListener(onTouchListener);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(buttonProgram);
        linearLayout.addView(buttonEdit);
        linearLayout.addView(buttonRemove);

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("error", "focus = " + hasFocus);
            }
        };

        AppCompatTextView textViewProgram = new AppCompatTextView(this);
        textViewProgram.setText("HyperWork1000");
        textViewProgram.setPadding(10, 10, 10, 10);
        textViewProgram.setBackgroundColor(Color.LTGRAY);
        textViewProgram.setOnTouchListener(onTouchListener);
        textViewProgram.setOnFocusChangeListener(onFocusChangeListener);
        textViewProgram.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        AppCompatTextView textViewEdit = new AppCompatTextView(this);
        textViewEdit.setText("Edit");
        textViewEdit.setPadding(10, 10, 10, 10);
        textViewEdit.setBackgroundColor(Color.LTGRAY);
        textViewEdit.setOnTouchListener(onTouchListener);
        textViewEdit.setOnFocusChangeListener(onFocusChangeListener);
        textViewEdit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));

        AppCompatTextView textViewRemove = new AppCompatTextView(this);
        textViewRemove.setText("Remove");
        textViewRemove.setPadding(10, 10, 10, 10);
        textViewRemove.setBackgroundColor(Color.LTGRAY);
        textViewRemove.setOnTouchListener(onTouchListener);
        textViewRemove.setOnFocusChangeListener(onFocusChangeListener);
        textViewRemove.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));

        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.addView(textViewProgram);
        linearLayout2.addView(textViewEdit);
        linearLayout2.addView(textViewRemove);


        AppCompatButton buttonProgram2 = new AppCompatButton(this);
        buttonProgram2.setText("HyperWork1000");
        buttonProgram2.setMinWidth(0);
        buttonProgram2.setMinimumWidth(0);
        buttonProgram2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        buttonProgram2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        buttonProgram2.setSupportAllCaps(false);
        buttonProgram2.setBackgroundResource(0);
        buttonProgram2.setOnTouchListener(onTouchListener);

        AppCompatButton buttonEdit2 = new AppCompatButton(this);
        buttonEdit2.setText("Edit");
        buttonEdit2.setMinWidth(0);
        buttonEdit2.setMinimumWidth(0);
        buttonEdit2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
        buttonEdit2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        buttonEdit2.setSupportAllCaps(false);
        buttonEdit2.setBackgroundResource(0);
        buttonEdit2.setOnTouchListener(onTouchListener);

        AppCompatButton buttonRemove2 = new AppCompatButton(this);
        buttonRemove2.setText("Remove");
        buttonRemove2.setMinWidth(0);
        buttonRemove2.setMinimumWidth(0);
        buttonRemove2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
        buttonRemove2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        buttonRemove2.setSupportAllCaps(false);
        buttonRemove2.setBackgroundResource(0);
        buttonRemove2.setOnTouchListener(onTouchListener);

        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.addView(buttonProgram2);

        //LinearLayout linearLayout4 = new LinearLayout(this);
        linearLayout3.addView(buttonEdit2);
        linearLayout3.addView(buttonRemove2);



        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_display_message);
        viewGroup.addView(textView);
        viewGroup.addView(button);
        viewGroup.addView(linearLayout);
        viewGroup.addView(linearLayout2);
        viewGroup.addView(linearLayout3);
        //viewGroup.addView(linearLayout4);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void startActivity(Intent intent) {
        if(intent.getComponent().getClassName().equals(MyActivity.class.getName())) {
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
