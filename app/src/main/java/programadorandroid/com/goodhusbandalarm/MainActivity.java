package programadorandroid.com.goodhusbandalarm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private final static String LOG_TAG = "MainActivity";

    private FloatingActionButton fab;
    private TextView anniversaryTextView;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        anniversaryTextView = (TextView) findViewById(R.id.anniversaryTextView);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButtonPressed();
            }
        });

        presenter.onCreate(this);
    }

    private void floatingActionButtonPressed() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setContext(this);
        datePickerFragment.setPresenter(presenter);
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void showMessage(int stringResourceId) {
        Snackbar.make(fab, stringResourceId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showAnniversaryDate(int day, int month) {
        //TODO: show date in a best way (month's name or something)
        String anniversaryText = day+" / "+month;

        anniversaryTextView.setText(anniversaryText);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private AppCompatActivity context;
        private MainPresenter presenter;

        public void setContext(AppCompatActivity context){
            this.context = context;
        }
        public void setPresenter(MainPresenter presenter){
            this.presenter = presenter;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar calendarNow = Calendar.getInstance();
            int year = calendarNow.get(Calendar.YEAR);
            int month = calendarNow.get(Calendar.MONTH);
            int day = calendarNow.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog, this, year,month, day);
            // Hide the year (The app only need to know the day and the month)
            datePickerDialog.getDatePicker().findViewById(getResources().getIdentifier("year","id","android")).setVisibility(View.GONE);
            // return it
            return datePickerDialog;

            //return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Log.d(LOG_TAG,"onDateSet");
            presenter.saveAlarmChanges(context,day,month);
        }
    }
}
