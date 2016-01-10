package com.example.nate.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nate on 11/20/2015.
 */
public class SharedPreferencesHelper {

    public static String getMessage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myfirstapp", Context.MODE_PRIVATE);

        String message = sharedPreferences.getString("message", "");

        return message;
    }

    public static void setMessage(Context context, String message) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myfirstapp", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("message", message);
        editor.commit();
    }
}
