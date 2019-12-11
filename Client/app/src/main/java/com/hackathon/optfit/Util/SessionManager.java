package com.hackathon.optfit.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.optfit.entities.User;

public class SessionManager {

    public static final String USER_ID = "UserId";
    private final Context Context;
    private final SharedPreferences Preferences;
    private int UserId;

    public SessionManager(Context context){
        Context = context;

        Preferences = Context.getSharedPreferences(
                "com.hackathon.optfit", Context.MODE_PRIVATE);
        UserId =  Preferences.getInt(USER_ID,0);
    }

    public boolean exists(){
        return  UserId != 0;
    }

    public int getUserId() {
        return UserId;
    }

    public void clear() {
        Preferences.edit().clear().apply();

        this.UserId = 0;
    }

    public void create(User user) {
        Preferences.edit().putInt(USER_ID,user.id).apply();
    }
}
