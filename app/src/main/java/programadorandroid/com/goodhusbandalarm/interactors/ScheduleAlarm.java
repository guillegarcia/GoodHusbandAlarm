package programadorandroid.com.goodhusbandalarm.interactors;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import programadorandroid.com.goodhusbandalarm.broadcastReceiver.AlarmReceiver;

public class ScheduleAlarm {
    private final static String LOG_TAG = "ScheduleAlarm";
    private static final int ALARM_REQUEST_CODE = 1;

    public void execute(Context context, int day, int month){
        Log.d(LOG_TAG,"execute");

        //Next alarm for next event
        Calendar nextEvent = Calendar.getInstance();
        nextEvent.set(Calendar.DAY_OF_MONTH,day);
        nextEvent.set(Calendar.MONTH,month);

        Calendar currentTimeCalendar = Calendar.getInstance();
        int currentYear = currentTimeCalendar.get(Calendar.YEAR);
        Log.d(LOG_TAG,"currentYear = "+currentYear);

        if(currentTimeCalendar.MONTH > day){
            nextEvent.set(Calendar.YEAR,currentYear+1);
        } else {
            nextEvent.set(Calendar.YEAR,currentYear);
        }
        Log.d(LOG_TAG,"nextEvent year: "+ nextEvent.get(Calendar.YEAR));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent  = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, nextEvent.getTimeInMillis(), pendingIntent);
    }
}
