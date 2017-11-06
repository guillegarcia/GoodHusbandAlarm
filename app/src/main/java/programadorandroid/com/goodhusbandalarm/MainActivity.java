package programadorandroid.com.goodhusbandalarm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    public void showAnniversaryDate(Calendar calendar) {
        String anniversaryText = calendar.get(Calendar.DAY_OF_MONTH)+" / "+calendar.get(Calendar.MONTH);

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
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog, this, year,month, day);
            datePickerDialog.getDatePicker().findViewById(getResources().getIdentifier("year","id","android")).setVisibility(View.GONE);
            return datePickerDialog;

            //return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Log.d(LOG_TAG,"onDateSet");
            // Do something with the date chosen by the user
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            presenter.saveAlarmChanges(context,calendar);
        }
    }
}
