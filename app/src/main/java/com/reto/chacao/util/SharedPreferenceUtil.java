package com.reto.chacao.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by ULISES HARRIS on 21/05/2015.
 */


public class SharedPreferenceUtil {

    public static SharedPreferences settings;
    public static Editor editor;

    public static final String PREFS_NAME = "MOVIDA_PREFS";

    public SharedPreferenceUtil() {
        super();
    }

    public static void saveString(Context context, String key, String value) {

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveInt(Context context, String key, int value) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void save(Context context, String key, int value) {

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void save(Context context, String key, float value) {

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void save(Context context, String key, boolean value) {

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void save(Context context, String key, long value) {

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, null);
    }

    public static int getInt(Context context, String key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }

    public static float getFloat(Context context, String key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, 0.0f);
    }

    public static boolean getBoolean(Context context, String key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }

    public static long getLong(Context context, String key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, 0);
    }

    public static void clearSharedPreference(Context context) {

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public static void removeValue(Context context, String key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.commit();
    }
}