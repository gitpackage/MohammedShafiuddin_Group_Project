package group4.cs422.medicationmanager;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendar;
    private Button checkButton;

    private Toolbar activityToolbar;

    private int mYear, mMonth, mDayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        activityToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);
        getSupportActionBar().setTitle(null);


        // Action Bar setup
        ImageButton homeButton = (ImageButton)findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(findViewById(android.R.id.content));
                openContextMenu(findViewById(android.R.id.content));
            }
        });

        TextView titleText = (TextView)findViewById(R.id.titleText);
        titleText.setText("Medication History");


        calendar = (CalendarView)findViewById(R.id.calendarView);

        // TODO - set this lower bound to an appropriate value
        calendar.setMinDate(1554901320000L); // sets the lower bound of the calendar to April 10, 2019
        //calendar.setMaxDate(1556456520000L); // sets the upper bound of the calendar to April 28, 2019

        long timeStamp = calendar.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp);

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH) + 1;
        mDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month + 1; // fixes offset of month numbering
                mDayOfMonth = dayOfMonth;
            }
        });

        checkButton = (Button)findViewById(R.id.checkButton);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHistoryActivity();
            }
        });

    }

    private void openHistoryActivity() {
        Intent i = new Intent(CalendarActivity.this, SelectedDateActivity.class);
        i.putExtra("year", mYear);
        i.putExtra("month", mMonth);
        i.putExtra("dayOfMonth", mDayOfMonth);
        startActivity(i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Return to Home Menu?");
        getMenuInflater().inflate(R.menu.confirm_home_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home_Yes: {

                Toast toast = Toast.makeText(getApplicationContext(), "Changed Medication Information", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(CalendarActivity.this, MainMenuActivity.class);
                startActivity(i);
                //this.onDestroy();
                return true;
            }


            default: {
                // User selected No in the context menu
                return super.onContextItemSelected(item);
            }
        }

    }

}
