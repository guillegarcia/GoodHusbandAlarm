package programadorandroid.com.goodhusbandalarm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private final static String LOG_TAG = "MainActivity";

    private FloatingActionButton fab;
    private TextView anniversaryTextView;
    private TextView anniversaryTitleTextView;
    private TextView stateTextView;
    private MainPresenter presenter;
    private boolean husbandImageAnimationStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        anniversaryTextView = findViewById(R.id.anniversaryTextView);
        anniversaryTitleTextView = findViewById(R.id.anniversaryTitleTextView);
        stateTextView = findViewById(R.id.stateTextView);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButtonPressed();
            }
        });

        husbandImageAnimationStarted = false;

        presenter.onCreate(this);
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus){
        Log.d(LOG_TAG,"onWindowFocusChanged hasFocus="+hasFocus);
        super.onWindowFocusChanged(hasFocus);
        husbandImageAnimation();
    }

    private void husbandImageAnimation(){

        if(!husbandImageAnimationStarted) {
            // Load the ImageView that will host the animation and
            // set its background to our AnimationDrawable XML resource.
            ImageView img = (ImageView) findViewById(R.id.imageView);
            img.setBackgroundResource(R.drawable.blink_animation);

            // Get the background, which has been compiled to an AnimationDrawable object.
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

            // Start the animation (looped playback by default).
            frameAnimation.start();
            Log.d(LOG_TAG, "husbandImageAnimation");
            husbandImageAnimationStarted = true;
        }
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
        Calendar calendarAnniversary = Calendar.getInstance();
        calendarAnniversary.set(Calendar.DAY_OF_MONTH,day);
        calendarAnniversary.set(Calendar.MONTH,month);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault());

        anniversaryTextView.setText(simpleDateFormat.format(calendarAnniversary.getTime()));

        anniversaryTitleTextView.setText(getString(R.string.yourAnniversary));
    }

    @Override
    public void setStateText(int stringResourceId) {
        stateTextView.setText(stringResourceId);
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

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar calendarNow = Calendar.getInstance();
            int year = calendarNow.get(Calendar.YEAR);
            int month = calendarNow.get(Calendar.MONTH);
            int day = calendarNow.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog, this, year,month, day);
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
