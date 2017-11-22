package programadorandroid.com.goodhusbandalarm.interactors;


import java.util.Calendar;

public class GetAlarmDate {
    //private final String LOG_TAG = "GetAlarmDate";
    public Calendar execute(int day, int month){
        //Log.d(LOG_TAG,"day="+day+" month="+month);
        //Next alarm for next event
        Calendar nextEventCalendar = Calendar.getInstance();
        nextEventCalendar.set(Calendar.DAY_OF_MONTH,day);
        nextEventCalendar.set(Calendar.MONTH,month);

        //Year depending on current date
        Calendar currentTimeCalendar = Calendar.getInstance();
        int currentYear = currentTimeCalendar.get(Calendar.YEAR);

        if(currentTimeCalendar.get(Calendar.MONTH) > month){
            nextEventCalendar.set(Calendar.YEAR,currentYear+1);
            //Log.d(LOG_TAG,"year="+currentYear+1);
        } else {
            nextEventCalendar.set(Calendar.YEAR,currentYear);
            //Log.d(LOG_TAG,"year="+currentYear);
        }

        //At midnight
        nextEventCalendar.set(Calendar.HOUR_OF_DAY, 0);
        nextEventCalendar.set(Calendar.MINUTE, 0);
        nextEventCalendar.set(Calendar.SECOND, 0);

        return nextEventCalendar;
    }
}
