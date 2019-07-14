package com.WattanArt.Utils.SharedPrefTool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import com.WattanArt.MyApplication;
import com.google.gson.Gson;

import java.util.Locale;

/**
 * Created by Android Team on 1/31/2018.
 */

public class SharedPreferencesTool {

    public static final String fileName = MyApplication.getAppContext().getPackageName();

    public static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }

    /**
     * Reference from shared prefernce to read from it
     *
     * @param context app context
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static void saveObject(Context context, String key, Object myObject) {
        SharedPreferences.Editor editor = getEditor(context);
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        editor.putString(key, json);
        editor.commit();
    }
    public static <H> H getObject(Context context, String key, Class<H> className) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String json = sharedPreferences.getString(key, "");
        return gson.fromJson(json, className);
    }

    public static void setBoolean(Context context, boolean remmberMe , String key) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, remmberMe);
        editor.apply();
    }

    public static boolean getBoolean(Context context , String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public static boolean getBooleanlang(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(key, true);
    }
    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(key, -1);
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        editor.apply();
    }
    public static long getLong(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getLong(key, -1);
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(key,"");
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.apply();
    }
    public static void clearObject(Context context) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }
    public static void changeLocale(Context context, Locale locale, int langaugeId) {
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        Locale.setDefault(locale);
        new UserData().saveLocalization(context, langaugeId);
        conf.setLayoutDirection(conf.locale);
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
    }

}
