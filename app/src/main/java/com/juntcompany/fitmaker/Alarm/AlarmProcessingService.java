package com.juntcompany.fitmaker.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.juntcompany.fitmaker.Manager.DataManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

public class AlarmProcessingService extends Service {

    AlarmManager alarmManager;
    public AlarmProcessingService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        processAlarmData();
        setAlarmTimer();

        return Service.START_NOT_STICKY;
    }

    private void processAlarmData(){
        long currentTime = System.currentTimeMillis();
        List<Integer> items = DataManager.getInstance().getItems();
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.setTimeInMillis(currentTime);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        if(items.contains(hour)){
            Log.i("AlarmProcessSevice", "success");
        }
    }


    private void setAlarmTimer(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 00);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmProcessingService.class);
        PendingIntent pendingIntent =PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                24*60*60*1000, pendingIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
