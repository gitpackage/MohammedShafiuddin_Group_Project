/*
    Group 4
    CS 422 Spring 2019
    Project Prototype - GR4
    University of Illinois at Chicago

    This is an implementation of the main menu of our medication
    manager app where the user can do a variety of things such
    as viewing their medications, adding a new medication, and
    so on. This is the first activity the user sees upon launching
    the app.



*/
package group4.cs422.medicationmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    // TODO - broadcast reciever

    private BroadcastReceiver mReceiver;
    private IntentFilter mFilter;

    private String CATCH_ALARM_INTENT = "group4.cs422.prototype.ALARM";

    private Toolbar activityToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityToolbar = (Toolbar)findViewById(R.id.homebar);
        setSupportActionBar(activityToolbar);
        getSupportActionBar().setTitle(null);

        // Create buttons and the text that accompanies them
        ImageButton currentMedsButton = (ImageButton) findViewById(R.id.currentMedsButton);
        ImageButton addNewMedButton = (ImageButton) findViewById(R.id.addNewMedButton);
        ImageButton medsHistoryButton = (ImageButton) findViewById(R.id.medsHistoryButton);
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        TextView currentMedsText = (TextView) findViewById(R.id.currentMedsText);
        TextView addNewMedsText = (TextView) findViewById(R.id.addMedsText);
        TextView medsHistoryText = (TextView) findViewById(R.id.medsHistoryText);
        final TextView settingsText = (TextView) findViewById(R.id.settingsText);



        // TODO: Set date on top of screen, above buttons
        TextView currentDateText = (TextView) findViewById(R.id.currentDateText);

        long currentTime = System.currentTimeMillis();

        currentDateText.setText(TimeAndDates.getDateString(currentTime));



        // Create event listeners for all the buttons
        currentMedsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if there are no medications added, the user gets a message telling them
                // no medications are added so far
                if (Medication.getMedicationsList().size() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No medications available.", Toast.LENGTH_LONG);
                    toast.show();
                }

                else
                    startMedicationListActivity();
            }
        });

        addNewMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddMedicationActivity();
            }
        });

        medsHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMedicationHistoryActivity();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingActivity();
            }
        });

        // FIXME - remove when Alarm Managing System is finished
        registerForContextMenu(findViewById(android.R.id.content));
    }


    // FIXME - remove when Alarm Managing System is finished
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.send_notification, menu);


    }

    // FIXME - remove when Alarm Managing System is finished
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.getNotifcation: {

                createAlarm();
                return true;
            }

            default: {
                // do nothing
                return super.onContextItemSelected(item);
            }
        }
    }
    /* <-----   -----> */


    private void createAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);


        long currentTime = System.currentTimeMillis();

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTime + 30, pendingIntent);
    }


    protected void startMedicationListActivity(){
        Intent i = new Intent(MainMenuActivity.this, MedicationsListActivity.class);
        startActivity(i);
    }

    protected void startAddMedicationActivity(){
        Intent i = new Intent(MainMenuActivity.this, AddMedicationActivity.class);
        startActivity(i);
    }

    protected void startMedicationHistoryActivity(){
        Intent i = new Intent(MainMenuActivity.this, CalendarActivity.class);
        startActivity(i);
    }

    protected void startSettingActivity() {
        Intent i = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // user hit back, restore current screen
        if (resultCode == RESULT_OK) {
            Log.i("DISP1", "finished");
            finish();
        }

        // user confirmed, delete
        else {
            Log.i("DISP1", "not finished");
        }

    }
}
