package com.p90next.p90next;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        final List<String> workoutList = new ArrayList<String>() {{
            add(getString(R.string.chest_back));
            add(getString(R.string.plyometrics));
            add(getString(R.string.shoulders_arms));
            add(getString(R.string.yoga_x));
            add(getString(R.string.legs_back));
            add(getString(R.string.kenpo_x));
        }};

        final Map<String, String> workoutMap = new HashMap<String, String>() {{
            put(workoutList.get(0), "https://photos.app.goo.gl/CiWghgRQAxQzRmQl2");
            put(workoutList.get(1), "https://photos.app.goo.gl/M9A8eeFVDQV00Nz43");
            put(workoutList.get(2), "https://photos.app.goo.gl/aQBGujqV47RIxCal2");
            put(workoutList.get(3), "https://photos.app.goo.gl/QymWg6T7rVcKL4yy2");
            put(workoutList.get(4), "https://photos.app.goo.gl/WDmSXDpQlGU3K7EG3");
            put(workoutList.get(5), "https://photos.app.goo.gl/lBUCQYKnAzEZupc42");
        }};

        final TextView textView = (TextView)findViewById(R.id.textView);

        textView.setText(getWorkout());

        final Button buttonPlay = (Button)findViewById(R.id.buttonPlay);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(workoutMap.get(getWorkout())));
                startActivity(intent);
            }
        });

        final Button buttonFinish = (Button)findViewById(R.id.buttonFinish);

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int workoutIndex = workoutList.indexOf(getWorkout());
                workoutIndex++;
                if(workoutIndex > 5) {
                    workoutIndex = 0;
                }
                setWorkout(workoutList.get(workoutIndex));
                textView.setText(getWorkout());
            }
        });

        editor.apply();
    }

    private String getWorkout() {
        return sharedPreferences.getString("workout", getString(R.string.chest_back));
    }

    private void setWorkout(String workout) {
        editor.putString("workout", workout);
        editor.commit();
    }
}
