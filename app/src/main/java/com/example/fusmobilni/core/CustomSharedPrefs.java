package com.example.fusmobilni.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fusmobilni.model.users.User;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.google.gson.Gson;

public class CustomSharedPrefs {
    private static final String PREFS_NAME = "app_prefs";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;
    private static volatile CustomSharedPrefs instance;

    public static CustomSharedPrefs getInstance(Context context) {
        if (instance == null) {
            synchronized (CustomSharedPrefs.class) {
                if (instance == null) {
                    instance = new CustomSharedPrefs(context.getApplicationContext());
                }
            }
        }
        return instance;

    }

    public static CustomSharedPrefs getInstance() {
        return instance;
    }

    private CustomSharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveUser(LoginResponse user) {
        String userJson = gson.toJson(user);
        saveString("user", userJson);
    }

    public LoginResponse getUser() {
        String userJson = getString("user", null);
        if (userJson != null) {
            return gson.fromJson(userJson, LoginResponse.class);
        }
        return null;
    }

    public void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void saveInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void saveFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public void saveLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public void clearKey(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }
}
