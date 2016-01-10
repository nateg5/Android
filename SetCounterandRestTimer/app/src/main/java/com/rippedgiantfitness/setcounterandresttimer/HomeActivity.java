package com.rippedgiantfitness.setcounterandresttimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    public static String SETS = "SETS";
    public static String REST = "REST";

    static AppCompatEditText eSets;
    static AppCompatEditText eRest;
    static AppCompatButton bStart;
    static LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        eSets = new AppCompatEditText(this);
        eSets.setHint("Number of Sets");
        eSets.setInputType(InputType.TYPE_CLASS_NUMBER);

        eRest = new AppCompatEditText(this);
        eRest.setHint("Rest Time");
        eRest.setInputType(InputType.TYPE_CLASS_NUMBER);

        bStart = new AppCompatButton(this);
        bStart.setText("Start");
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int sets = Integer.valueOf(eSets.getText().toString());
                    int rest = Integer.valueOf(eRest.getText().toString());

                    Intent intent = new Intent(v.getContext(), SetCounterAndRestTimerActivity.class);
                    intent.putExtra(SETS, sets);
                    intent.putExtra(REST, rest);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    //do nothing
                }
            }
        });

        linearLayout = (LinearLayout)findViewById(R.id.content_home);
        linearLayout.addView(eSets);
        linearLayout.addView(eRest);
        linearLayout.addView(bStart);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("DEBUG", "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putString("eSets", eSets.getText().toString());
        outState.putString("eRest", eRest.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("DEBUG", "onResotreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);

        eSets.setText(savedInstanceState.getString("eSets"));
        eRest.setText(savedInstanceState.getString("eRest"));
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
