package programadorandroid.com.goodhusbandalarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import programadorandroid.com.goodhusbandalarm.interactors.GetAlarmDate;
import programadorandroid.com.goodhusbandalarm.interactors.ScheduleAlarm;

class MainPresenter {
    private final static String LOG_TAG = "MainPresenter";
    private final static String PREFERENCE_ANNIVERSARY_DAY = "d";
    private final static String PREFERENCE_ANNIVERSARY_MONTH = "m";

    private View view;

    MainPresenter(View view){
        this.view = view;
    }

    void saveAlarmChanges(AppCompatActivity context, int day, int month){
        Log.d(LOG_TAG,"saveAlarmChanges");

        //Schedule alarm
        ScheduleAlarm scheduleAlarm = new ScheduleAlarm();
        scheduleAlarm.execute(context,day,month);

        //Save the new date
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(PREFERENCE_ANNIVERSARY_DAY, day);
        editor.putInt(PREFERENCE_ANNIVERSARY_MONTH, month);
        editor.apply();

        //Show the new date
        view.showAnniversaryDate(day,month);

        //Refresh state message
        checkState(day,month);

        //Show message to user
        view.showMessage(R.string.changesSaved);
    }

    void onCreate(AppCompatActivity context) {
        //GetAnniversary
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        int anniversaryDay = sharedPref.getInt(PREFERENCE_ANNIVERSARY_DAY, 0);
        int anniversaryMonth = sharedPref.getInt(PREFERENCE_ANNIVERSARY_MONTH, 0);

        if(anniversaryDay != 0 && anniversaryMonth != 0){
            view.showAnniversaryDate(anniversaryDay,anniversaryMonth);
            checkState(anniversaryDay,anniversaryMonth);
        }
    }

    private void checkState(int anniversaryDay, int anniversaryMonth) {
        Log.d(LOG_TAG,"checkState");
        GetAlarmDate getAlarmDate = new GetAlarmDate();
        Calendar alarmCalendar = getAlarmDate.execute(anniversaryDay,anniversaryMonth);

        Calendar currentCalendar=Calendar.getInstance();

        Calendar alarmCalendarOneWeekBefore = Calendar.getInstance();
        alarmCalendarOneWeekBefore.setTime(alarmCalendar.getTime());
        alarmCalendarOneWeekBefore.add(Calendar.DAY_OF_MONTH,-7);

        Log.d(LOG_TAG,"checkState: alarmCalendar = "+alarmCalendar.toString());
        Log.d(LOG_TAG,"checkState: alarmCalendarOneWeekBefore = "+alarmCalendarOneWeekBefore.toString());
        Log.d(LOG_TAG,"checkState: currentCalendar = "+currentCalendar.toString());

        if(currentCalendar.compareTo(alarmCalendarOneWeekBefore)>=0 && currentCalendar.compareTo(alarmCalendar)<0){
            //It's less than 7 days.
            Log.d(LOG_TAG,"checkState: < WEEK");
            view.setStateText(R.string.stateAWeekLeft);
        } else {
            currentCalendar.set(Calendar.HOUR_OF_DAY,0);
            currentCalendar.set(Calendar.MINUTE,0);
            currentCalendar.set(Calendar.SECOND,0);

            if(currentCalendar.compareTo(alarmCalendar)==0){
                //It's the day
                Log.d(LOG_TAG,"checkState: TODAY");
                view.setStateText(R.string.stateToday);
            } else {
                Log.d(LOG_TAG,"checkState: Normal");
                view.setStateText(R.string.stateNormal);
            }
        }

    }

    interface View{
        void showMessage(int stringResourceId);
        void showAnniversaryDate(int day, int month);
        void setStateText(int stringResourceId);
    }
}
