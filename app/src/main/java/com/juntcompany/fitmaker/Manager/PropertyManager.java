package com.juntcompany.fitmaker.Manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.juntcompany.fitmaker.FitMaker;


/**
 * Created by EOM on 2016-02-28.
 */
public class PropertyManager {

    private static PropertyManager instance;

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;
    private PropertyManager(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(FitMaker.getContext());
        mEditor =mPrefs.edit();
    }
    private static final String FIELD_USER_ID = "userId";
    private static final String FIELD_PASSWORD = "password";

    public void setUserId(String userId){
        mEditor.putString(FIELD_USER_ID, userId);
        mEditor.commit();
    }

    public String getUserId(){
        return mPrefs.getString(FIELD_USER_ID, "");
    }

    public void setPassword(String password){
        mEditor.putString(FIELD_PASSWORD, password);
        mEditor.commit();
    }

    public String getPassword(String password){
        return mPrefs.getString(FIELD_PASSWORD, "");
    }
}
