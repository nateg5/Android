package com.touchtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Date startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            startTime = new Date();
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            Toast toast = Toast.makeText(this, "endTime = " + ((new Date()).getTime() - startTime.getTime()), Toast.LENGTH_LONG);
            toast.show();
        }
        return super.onTouchEvent(event);
    }
}
