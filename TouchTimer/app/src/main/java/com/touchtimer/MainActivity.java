package com.touchtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Date startTime;
    private Long bestTime = Long.valueOf(1000000);

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
            Long time = ((new Date()).getTime() - startTime.getTime());
            if(time < bestTime) {
                TextView textView = findViewById(R.id.text_view);
                String oldText = textView.getText().toString();
                oldText = oldText.replace("Best Time: ", "");
                textView.setText(oldText + "\nBest Time: " + time + " msec");
                System.out.println("onTouch");
                bestTime = time;
            }
        }
        return super.onTouchEvent(event);
    }

    public void onReset(View view) {
        TextView textView = findViewById(R.id.text_view);
        textView.setText("");
        bestTime = Long.valueOf(1000000);
    }
}
