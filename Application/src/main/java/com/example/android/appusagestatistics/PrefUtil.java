package com.example.android.appusagestatistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by DMI on 29-08-2017.
 */

public class PrefUtil {

    // shared preferences key names
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final String USER_PASSWORD = "USER_PASSWORD";

    public static void saveLoginDetails(Context context, String password,boolean loginStatus) {
        setLogin(context, loginStatus);
        setPassword(context, password);
    }

    /*LOGIN STATUS*/
    public static boolean getLogin(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean restoredText = prefs.getBoolean(LOGIN_STATUS, false);
        return restoredText;
    }

    public static void setLogin(Context context, boolean data) {
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefs.putBoolean(LOGIN_STATUS, data).commit();
    }

    public static void setPassword(Context context, String data) {
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefs.putString(USER_PASSWORD, data).commit();
    }

    public static String getPassword(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String restoredText = prefs.getString(USER_PASSWORD, null);
        return restoredText;
    }

}
