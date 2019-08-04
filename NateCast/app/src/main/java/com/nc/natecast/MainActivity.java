package com.nc.natecast;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        final EditText editTextIpAddress = (EditText)findViewById(R.id.editTextIpAddress);
        final EditText editTextUrl = (EditText)findViewById(R.id.editTextUrl);
        final ToggleButton toggleButtonFullScreen = (ToggleButton)findViewById(R.id.toggleButtonFullScreen);
        final Button buttonLaunch = (Button)findViewById(R.id.buttonLaunch);
        final Button buttonClose = (Button)findViewById(R.id.buttonClose);
        final Button buttonReboot = (Button)findViewById(R.id.buttonReboot);
        final Button buttonMouseMove = (Button)findViewById(R.id.buttonMouseMove);
        final Button buttonSpace = (Button)findViewById(R.id.buttonSpace);
        final Button buttonUp = (Button)findViewById(R.id.buttonUp);
        final Button buttonLeft = (Button)findViewById(R.id.buttonLeft);
        final Button buttonRight = (Button)findViewById(R.id.buttonRight);
        final Button buttonDown = (Button)findViewById(R.id.buttonDown);
        final ToggleButton toggleButtonX2 = (ToggleButton)findViewById(R.id.toggleButtonX2);
        final ToggleButton toggleButtonX3 = (ToggleButton)findViewById(R.id.toggleButtonX3);
        final ToggleButton toggleButtonX4 = (ToggleButton)findViewById(R.id.toggleButtonX4);

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

                sendRequest(context, url);
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

                sendRequest(context, url);
            }
        });

        buttonReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Are you sure you want to reboot?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveSharedPreferences(editor, new HashMap<String, String>() {{
                            put("editTextIpAddress", editTextIpAddress.getText().toString());
                        }});

                        String url = "http://";
                        url += editTextIpAddress.getText().toString();
                        url += "?url=reboot";

                        sendRequest(context, url);
                    }
                });
                alertDialog.setNegativeButton("No", null);
                alertDialog.show();
            }
        });

        buttonMouseMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences(editor, new HashMap<String, String>() {{
                    put("editTextIpAddress", editTextIpAddress.getText().toString());
                }});

                String url = "http://";
                url += editTextIpAddress.getText().toString();
                url += "?url=mousemove";

                sendRequest(context, url);
            }
        });

        buttonSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences(editor, new HashMap<String, String>() {{
                    put("editTextIpAddress", editTextIpAddress.getText().toString());
                }});

                String url = "http://";
                url += editTextIpAddress.getText().toString();
                url += "?key=space";

                sendRequest(context, url);
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences(editor, new HashMap<String, String>() {{
                    put("editTextIpAddress", editTextIpAddress.getText().toString());
                }});

                String url = "http://";
                url += editTextIpAddress.getText().toString();
                url += "?key=" + getKey(toggleButtonX2, toggleButtonX3, toggleButtonX4, "Up");

                sendRequest(context, url);
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences(editor, new HashMap<String, String>() {{
                    put("editTextIpAddress", editTextIpAddress.getText().toString());
                }});

                String url = "http://";
                url += editTextIpAddress.getText().toString();
                url += "?key=" + getKey(toggleButtonX2, toggleButtonX3, toggleButtonX4, "Left");

                sendRequest(context, url);
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences(editor, new HashMap<String, String>() {{
                    put("editTextIpAddress", editTextIpAddress.getText().toString());
                }});

                String url = "http://";
                url += editTextIpAddress.getText().toString();
                url += "?key=" + getKey(toggleButtonX2, toggleButtonX3, toggleButtonX4, "Right");

                sendRequest(context, url);
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences(editor, new HashMap<String, String>() {{
                    put("editTextIpAddress", editTextIpAddress.getText().toString());
                }});

                String url = "http://";
                url += editTextIpAddress.getText().toString();
                url += "?key=" + getKey(toggleButtonX2, toggleButtonX3, toggleButtonX4, "Down");

                sendRequest(context, url);
            }
        });

        findPi(editTextIpAddress);
    }

    private String getKey(ToggleButton toggleButtonX2, ToggleButton toggleButtonX3, ToggleButton toggleButtonX4, String key) {
        if(toggleButtonX2.isChecked()) {
            key += "+" + key;
        }
        if(toggleButtonX3.isChecked()) {
            key += "+" + key + "+" + key;
        }
        if(toggleButtonX4.isChecked()) {
            key += "+" + key + "+" + key + "+" + key;
        }

        return key;
    }

    private void findPi(final EditText editTextIpAddress) {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = "192.168.0,0";
        if(wm != null) {
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        }
        if(ip.equals("0.0.0.0")) {
            ip = "192.168.0.0";
        }
        ip = ip.substring(0, ip.lastIndexOf(".") + 1);
        for(int i = 0; i < 256; i++) {
            final String url = "http://" + ip + i + "?ping=true";

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        URL uRL = new URL(url);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream()));

                        String inputLine;
                        while ((inputLine = bufferedReader.readLine()) != null) {
                            if(inputLine.equals("ping")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        editTextIpAddress.setText(url.substring(7, url.indexOf("?")));
                                    }
                                });
                            }
                        }
                        bufferedReader.close();
                    } catch(final Exception e) {
                        // do nothing
                    }
                }
            }, 100);
        }
    }

    private void sendRequest(final Context context, final String url) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    URL uRL = new URL(url);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream()));

                    String inputLine;
                    while((inputLine = bufferedReader.readLine()) != null) {
                        final String message = inputLine;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    bufferedReader.close();
                } catch(final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "ERROR: " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }, 100);
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
        if(index < 0) {
            index = requestedUrl.indexOf(".tv");
        }
        if(index < 0) {
            index = requestedUrl.indexOf(".co");
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

        requestedUrl = requestedUrl.replace(":", "%3A");
        requestedUrl = requestedUrl.replace("?", "%3F");
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
