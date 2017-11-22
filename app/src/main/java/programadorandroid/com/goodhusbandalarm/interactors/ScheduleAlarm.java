package programadorandroid.com.goodhusbandalarm.interactors;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import programadorandroid.com.goodhusbandalarm.broadcastReceiver.AlarmReceiver;

public class ScheduleAlarm {
    private final static String LOG_TAG = "ScheduleAlarm";
    private static final int ALARM_REQUEST_CODE = 1;

    public void execute(Context context, int day, int month){
        Log.d(LOG_TAG,"execute");

        GetAlarmDate getAlarmDate = new GetAlarmDate();
        Calendar nextEventCalendar = getAlarmDate.execute(day,month);

        //Schedule the alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent  = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, nextEventCalendar.getTimeInMillis(), pendingIntent);
    }
}
