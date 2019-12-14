package com.hackathon.optfit.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.optfit.entities.User;

public class SessionManager {

    public static final String USER_ID = "UserId";
    public static final String SOS_NUMBER = "SOS_Number";
    public static final String RV_THRESHOLD = "RV_THRESHOLD";
    public static final int DefaultRvThreshold = 25;
    private final Context Context;
    private final SharedPreferences Preferences;
    private int UserId;
    private String DefaultSosNumber = "9538031453";

    public SessionManager(Context context) {
        Context = context;

        Preferences = Context.getSharedPreferences(
                "com.hackathon.optfit", Context.MODE_PRIVATE);
        UserId = Preferences.getInt(USER_ID, 0);
    }

    public boolean exists() {
        return UserId != 0;
    }

    public int getUserId() {
        return UserId;
    }

    public void clear() {
        Preferences.edit().clear().apply();

        this.UserId = 0;
    }

    public void create(User user) {
        Preferences.edit().putInt(USER_ID, user.id).apply();
    }

    public void setSosNumber(String number) {
        try {
            Preferences.edit().putString(SOS_NUMBER, number).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSosNumber() {
        return Preferences.getString(SOS_NUMBER, DefaultSosNumber);
    }

    public void setThreshold(int threshold) {

        try {
            Preferences.edit().putInt(RV_THRESHOLD, threshold).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getThreshold() {
        return Preferences.getInt(RV_THRESHOLD, DefaultRvThreshold);
    }
}
