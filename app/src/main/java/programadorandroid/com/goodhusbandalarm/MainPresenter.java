package programadorandroid.com.goodhusbandalarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import programadorandroid.com.goodhusbandalarm.interactors.ScheduleAlarm;

/**
 * Created by Guillermo on 05/11/2017.
 */

class MainPresenter {
    private final static String LOG_TAG = "MainPresenter";
    private final static String PREFERENCE_ANNIVERSARY = "anni";

    private View view;

    MainPresenter(View view){
        this.view = view;
    }

    void saveAlarmChanges(AppCompatActivity context, Calendar calendar){
        Log.d(LOG_TAG,"saveAlarmChanges");

        //Schedule alarm
        ScheduleAlarm scheduleAlarm = new ScheduleAlarm();
        scheduleAlarm.execute(context,calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.YEAR));

        //Save the new date
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(PREFERENCE_ANNIVERSARY, calendar.getTimeInMillis());
        editor.commit();

        //Show the new date
        view.showAnniversaryDate(calendar);

        //Show message to user
        view.showMessage(R.string.changesSaved);
    }

    public void onCreate(AppCompatActivity context) {

        //GetAnniversary
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        long anniversaryTime = sharedPref.getLong(PREFERENCE_ANNIVERSARY, 0);

        if(anniversaryTime != 0){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(anniversaryTime);

            view.showAnniversaryDate(calendar);
        }
    }

    interface View{
        void showMessage(int stringResourceId);
        void showAnniversaryDate(Calendar calendar);
    }
}
