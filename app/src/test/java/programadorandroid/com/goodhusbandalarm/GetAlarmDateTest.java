package programadorandroid.com.goodhusbandalarm;

import org.junit.Test;

import java.util.Calendar;

import programadorandroid.com.goodhusbandalarm.interactors.GetAlarmDate;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Guillermo on 21/11/2017.
 */

public class GetAlarmDateTest {

    @Test
    public void getAlarmDate_isCorrect(){
        GetAlarmDate getAlarmDate = new GetAlarmDate();

        //Result Calendar has the same day and month we send to the function
        Calendar calendar = getAlarmDate.execute(1,1);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH),1);
        assertEquals(calendar.get(Calendar.MONTH),1);

        //Alarm must be at midnight
        assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 0);
        assertEquals(calendar.get(Calendar.MINUTE), 0);
        assertEquals(calendar.get(Calendar.SECOND), 0);

        //Year can be the current one or the next one depending on the day and month
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);

        Calendar calendarSameYear = getAlarmDate.execute(31,11);
        assertEquals(currentYear,calendarSameYear.get(Calendar.YEAR));

        if(!(currentCalendar.get(Calendar.DAY_OF_MONTH) == 1) && (currentCalendar.get(Calendar.MONTH) == 1)) {
            Calendar calendarNextYear = getAlarmDate.execute(1, 1);
            assertEquals(currentYear + 1, calendarNextYear.get(Calendar.YEAR));
        }

        Calendar calendarDameDate = getAlarmDate.execute(currentCalendar.get(Calendar.DAY_OF_MONTH), currentCalendar.get(Calendar.MONTH));
        assertEquals(calendarDameDate.get(Calendar.YEAR), currentYear);

    }
}
