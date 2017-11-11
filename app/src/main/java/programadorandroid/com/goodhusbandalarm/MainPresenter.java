package programadorandroid.com.goodhusbandalarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import programadorandroid.com.goodhusbandalarm.interactors.ScheduleAlarm;

/**
 * Created by Guillermo on 05/11/2017.
 */
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
        editor.commit();

        //Show the new date
        view.showAnniversaryDate(day,month);

        //Show message to user
        view.showMessage(R.string.changesSaved);
    }

    public void onCreate(AppCompatActivity context) {

        //GetAnniversary
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        int anniversaryDay = sharedPref.getInt(PREFERENCE_ANNIVERSARY_DAY, 0);
        int anniversaryMonth = sharedPref.getInt(PREFERENCE_ANNIVERSARY_MONTH, 0);

        if(anniversaryDay != 0 && anniversaryMonth != 0){
            view.showAnniversaryDate(anniversaryDay,anniversaryMonth);
        }
    }

    interface View{
        void showMessage(int stringResourceId);
        void showAnniversaryDate(int day, int month);
    }
}
