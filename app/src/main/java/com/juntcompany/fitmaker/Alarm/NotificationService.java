package com.juntcompany.fitmaker.Alarm;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }





    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        loadPref();


        int command = intent.getIntExtra("command", 0);
        String message;
        if (command == 0) {
            message = "back";
        } else if (command == 1) {
            message = "stop";
        } else {
            message = "next";
        }
        Toast.makeText(this, "command : " + message, Toast.LENGTH_SHORT).show();
        return Service.START_NOT_STICKY;
    }


    private void loadPref(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String switchPreference = sharedPreferences.getString("push", "");


    }
}
