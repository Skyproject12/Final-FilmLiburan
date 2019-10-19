package com.example.filmliburan.Data.Source.Local;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreference {
    private final SharedPreferences preferences;
    private static final String PREFS_NAME="notification_pref";
    public static final String TITLE_NOTIFICATION="title";
    public static final String KLIK_HARIAN="harian";
    public static final String KLIK_UPDATE="update";
    SharedPreferences.Editor editor;

    public NotificationPreference(Context context) {
        this.preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor= preferences.edit();
    }
    public void setTitle(String kunci, String nilai){
        editor.putString(kunci, nilai);
        editor.commit();
    }
    public void setKlik(String kunci, boolean nilai){
        editor.putBoolean(kunci, nilai);
        editor.commit();
    }
    public String getTitle(){
        return preferences.getString(TITLE_NOTIFICATION,"");
    }
    public Boolean getKlikHarian(){
        return preferences.getBoolean(KLIK_HARIAN,false);
    }
    public Boolean getKlikUpdate(){
        return preferences.getBoolean(KLIK_UPDATE, false);
    }
}
