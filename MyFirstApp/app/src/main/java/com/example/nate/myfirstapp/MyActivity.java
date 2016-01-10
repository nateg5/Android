package com.example.nate.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.nate.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE);
                
                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                });
                EditText editText = (EditText)findViewById(R.id.edit_message);
                Button button = (Button)findViewById(R.id.button_keep_awake);
                try {
                    int snackbarTime = Integer.valueOf(editText.getText().toString());
                    snackbar.show();
                    snackbarTimer(snackbar, snackbarTime);
                    if(button.getText().equals(getString(R.string.keep_awake_on))) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                } catch(NumberFormatException e) {
                    //do nothing
                }
            }
        });
    }

    private void snackbarTimer(final Snackbar snackbar, final int seconds) {
        if(snackbar.isShown()) {
            if (seconds > 0) {
                snackbar.setText(String.valueOf(seconds));
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                snackbarTimer(snackbar, seconds - 1);
                            }
                        });
                    }
                }, 1000);
            } else {
                snackbar.dismiss();
                new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME).startTone(ToneGenerator.TONE_PROP_BEEP2);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        String message = SharedPreferencesHelper.getMessage(this) + editText.getText().toString();
        SharedPreferencesHelper.setMessage(this, message);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void toggleKeepAwake(View view) {
        Button button = (Button)view;

        if(button.getText().equals(getString(R.string.keep_awake_off))) {
            button.setText(R.string.keep_awake_on);
        } else {
            button.setText(R.string.keep_awake_off);
        }
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
