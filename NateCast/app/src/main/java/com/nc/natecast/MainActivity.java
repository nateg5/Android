package com.nc.natecast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextIpAddress = (EditText)findViewById(R.id.editTextIpAddress);
        final EditText editTextUrl = (EditText)findViewById(R.id.editTextUrl);
        final ToggleButton toggleButtonFullScreen = (ToggleButton)findViewById(R.id.toggleButtonFullScreen);
        final Button buttonLaunch = (Button)findViewById(R.id.buttonLaunch);
        final Button buttonClose = (Button)findViewById(R.id.buttonClose);

        final SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editTextIpAddress.setText(sharedPreferences.getString("editTextIpAddress", ""));
        editTextUrl.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));

        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences(editor, new HashMap<String, String>() {{
                    put("editTextIpAddress", editTextIpAddress.getText().toString());
                }});

                String requestedUrl = getRequestedUrl(editTextUrl);

                String url = "http://";
                url += editTextIpAddress.getText().toString();
                url += "?url=";
                url += requestedUrl;

                if(toggleButtonFullScreen.isChecked()) {
                    url += "&full=true";
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences(editor, new HashMap<String, String>() {{
                    put("editTextIpAddress", editTextIpAddress.getText().toString());
                }});

                String url = "http://";
                url += editTextIpAddress.getText().toString();
                url += "?url=close";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private String getRequestedUrl(EditText editTextUrl) {
        String requestedUrl = editTextUrl.getText().toString();

        int index = requestedUrl.indexOf(".com");
        if(index < 0) {
            index = requestedUrl.indexOf(".net");
        }
        if(index < 0) {
            index = requestedUrl.indexOf(".org");
        }
        if(index < 0) {
            index = requestedUrl.indexOf(".gov");
        }
        int space = lastIndexOfWhitespace(requestedUrl, index);
        if(space >= 0) {
            requestedUrl = requestedUrl.substring(space + 1);
        }
        space = indexOfWhitespace(requestedUrl);
        if(space >= 0) {
            requestedUrl = requestedUrl.substring(0, space);
        }
        if(!requestedUrl.contains("http")) {
            requestedUrl = "http://" + requestedUrl;
        }
        if(requestedUrl.contains("netflix.com/title")) {
            requestedUrl = requestedUrl.replace("netflix.com/title", "netflix.com/watch");
        } else if(requestedUrl.contains("amazon.com")) {
            if(requestedUrl.contains("?")) {
                requestedUrl += "&autoplay=1";
            } else {
                requestedUrl += "?autoplay=1";
            }
        }

        requestedUrl = requestedUrl.replace("&", "%26");

        return requestedUrl;
    }

    private void saveSharedPreferences(SharedPreferences.Editor editor, Map<String, String> map) {
        for(Map.Entry<String, String> entry : map.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }

    private int indexOfWhitespace(String string) {
        int index = -1;

        for(int i = 0; i < string.length(); i++) {
            String tempString = string.substring(i, i+1).trim();

            if(tempString.length() == 0) {
                index = i;
                break;
            }
        }

        return index;
    }

    private int lastIndexOfWhitespace(String string, int startIndex) {
        int index = -1;

        for(int i = startIndex; i >= 0; i--) {
            String tempString = string.substring(i, i+1).trim();

            if(tempString.length() == 0) {
                index = i;
                break;
            }
        }

        return index;
    }
}
