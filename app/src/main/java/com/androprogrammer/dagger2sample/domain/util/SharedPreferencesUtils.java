package com.androprogrammer.dagger2sample.domain.util;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Application level preference work.
 * Created by wasim on 7/28/2016.
 */

public class SharedPreferencesUtils {

    private static final String TAG = "SharedPreferencesUtils";

    /*@Inject
    SharedPreferences mSharedPreferences;


    private static SharedPreferences.Editor getSharedPreferencesEditor(){

        if (mSharedPreferences != null) {
            return mSharedPreferences.edit();
        }

        return null;
    }

    public static void preferencePutInteger(String key, int value) {
        getSharedPreferencesEditor().putInt(key, value);
        getSharedPreferencesEditor().commit();
    }

    public static int preferenceGetInteger(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public static void preferencePutBoolean(String key, boolean value) {
        getSharedPreferencesEditor().putBoolean(key, value);
        getSharedPreferencesEditor().commit();
    }

    public static boolean preferenceGetBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public static void preferencePutString(String key, String value) {
        getSharedPreferencesEditor().putString(key, value);
        getSharedPreferencesEditor().commit();
    }

    public static String preferenceGetString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public static void preferencePutLong(String key, long value) {
        getSharedPreferencesEditor().putLong(key, value);
        getSharedPreferencesEditor().commit();
    }

    public static long preferenceGetLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public static void preferenceRemoveKey(String key) {
        getSharedPreferencesEditor().remove(key);
        getSharedPreferencesEditor().commit();
    }

    public static void clearPreference() {
        getSharedPreferencesEditor().clear();
        getSharedPreferencesEditor().commit();
    }
*/
}
