/*
    Group 4
    CS 422 Spring 2019
    Project Prototype - GR4
    University of Illinois at Chicago

    This activity displays the details of a medication.
    The user can view everything here as well as click the
    edit button to edit the details which will be saved, or
    go back to the list of medications.

    // TODO: Display start/end/added dates?
*/
package group4.cs422.medicationmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DisplayMedication extends AppCompatActivity {


    private int indexFromMedicationList;


    private Toolbar activityToolbar;

    private final int CONFIRM_TAKE_MENU = 1;
    private final int CONFIRM_DELETE_MENU = 2;
    private final int CONFIRM_HOME_MENU = 3;

    private int whichContextMenu = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medication);

        activityToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);
        getSupportActionBar().setTitle(null);


        // Action Bar setup
        ImageButton homeButton = (ImageButton)findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichContextMenu = CONFIRM_HOME_MENU;
                registerForContextMenu(findViewById(android.R.id.content));
                openContextMenu(findViewById(android.R.id.content));
            }
        });

        TextView titleText = (TextView)findViewById(R.id.titleText);
        titleText.setText("Medication Viewer");

        Log.i("Activity", "Activity DisplayMedication Started Succesfully!");


        // Get data from intent
        Intent intent = getIntent();
        indexFromMedicationList = intent.getIntExtra("position", -1);

        Medication selectedMedication;

        selectedMedication = Medication.getMedicationAtIndex(indexFromMedicationList);


        String medName = selectedMedication.getMedName();
        String doseQuantity = Integer.toString(selectedMedication.getDoseAmount());
        String hourlyFrequency = Integer.toString(selectedMedication.getHourlyFrequency());
        String daysBetweenDose = Integer.toString(selectedMedication.getDaysBetweenDose());

        Log.i("Activity", "Successfully Got Data from Intent!");

        // Create text views and set data from intent
        TextView medicationName = (TextView) findViewById(R.id.medicationName);
        medicationName.setText(medName);

        Log.i("Activity", "Created Med Name");

        TextView doseAmountText = (TextView) findViewById(R.id.textView1);
        TextView doseAmount = (TextView) findViewById(R.id.doseAmount);
        doseAmount.setText(doseQuantity);

        Log.i("Activity", "Created Dose Amount");

        TextView hourlyFreqText = (TextView) findViewById(R.id.textView2);
        TextView hourlyFreq = (TextView) findViewById(R.id.hourlyFrequency);
        hourlyFreq.setText(hourlyFrequency);

        Log.i("Activity", "Created Hourly Freq");

        TextView daysBwDoseText = (TextView) findViewById(R.id.textView3);
        TextView daysBwDose = (TextView) findViewById(R.id.daysBwDose);
        daysBwDose.setText(daysBetweenDose);

        Log.i("Activity", "Created Days BW Dose");


        Button editButton = (Button) findViewById(R.id.editButton);
        Button takeMedicationButton = (Button)findViewById(R.id.takeMedicationButton);
        Button deleteButton = (Button)findViewById(R.id.deleteButton);

        Log.i("Activity", "Created Buttons");

        String startTime = TimeAndDates.timeStringFromValues
                (selectedMedication.getStartHour(), selectedMedication.getStartMinute());

        if (startTime.length() == 7)
            startTime = " " + startTime;

        TextView startTimeDisplay = (TextView)findViewById(R.id.displayStartTime);
        startTimeDisplay.setText(startTime);


        String endTime = TimeAndDates.timeStringFromValues
                (selectedMedication.getEndHour(), selectedMedication.getEndMinute());

        if (endTime.length() == 7) {
            endTime = "  " + endTime;
        }

        TextView endTimeDisplay = (TextView)findViewById(R.id.displayEndTime);
        endTimeDisplay.setText(endTime);

        editButton.setOnClickListener(new View.OnClickListener() {
            // Go to EditMedication Activity
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisplayMedication.this, EditMedicationActivity.class);
                i.putExtra("position", indexFromMedicationList);
                startActivityForResult(i, 999);


            }
        });

        takeMedicationButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                whichContextMenu = CONFIRM_TAKE_MENU;
                registerForContextMenu(findViewById(android.R.id.content));
                openContextMenu(findViewById(android.R.id.content));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // clicking delete will open a popup confirmation
                whichContextMenu = CONFIRM_DELETE_MENU;
                registerForContextMenu(findViewById(android.R.id.content));
                openContextMenu(findViewById(android.R.id.content));
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (whichContextMenu) {
            case CONFIRM_TAKE_MENU: {
                menu.setHeaderTitle("Mark Medication as Taken?");
                getMenuInflater().inflate(R.menu.confirm_take_menu, menu);
                break;
            }

            case CONFIRM_DELETE_MENU: {
                menu.setHeaderTitle("Delete Medication?");
                getMenuInflater().inflate(R.menu.confirm_delete_menu, menu);
                break;
            }

            case CONFIRM_HOME_MENU: {
                menu.setHeaderTitle("Return to Home Menu?");
                getMenuInflater().inflate(R.menu.confirm_home_menu, menu);
                break;
            }
        }



    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            // User marks medication at taken
            case R.id.take_Yes: {

                long currentTime = System.currentTimeMillis();

                // get date from currentTime, get epoch of date, add to that dates's transactions
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(currentTime);

                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);


                long dateEpoch = TimeAndDates.epochFromDate(month, day, year);
                Log.i("Display", "DateEpoch: " + String.valueOf(dateEpoch));

                // gets time of transaction
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                String medName = Medication.getMedicationAtIndex(indexFromMedicationList).getMedName();
                MedicationTransaction medicationTransaction = new MedicationTransaction(medName, hour, minute);


                // puts this transaction of taking the medication in the correct date
                MedicationDate.addTransactionToDate(month, day, year, medicationTransaction);


                Intent i = new Intent(DisplayMedication.this, MedicationsListActivity.class);

                Toast toast = Toast.makeText(getApplicationContext(), "Medication Taken", Toast.LENGTH_SHORT);
                toast.show();
                setResult(RESULT_OK);
                startActivity(i);
                finish();
                return true;
            }

            // User deletes the displayed medication
            case R.id.delete_Yes: {


                Medication.deleteMedicationAtIndex(indexFromMedicationList);


                if (Medication.getMedicationsList().size() <= 0 ) {
                    Intent i = new Intent(DisplayMedication.this, MainMenuActivity.class);
                    startActivity(i);
                }

                else {
                    Intent i = new Intent(DisplayMedication.this, MedicationsListActivity.class);
                    startActivity(i);
                }

                Toast toast = Toast.makeText(getApplicationContext(), "Medication Deleted", Toast.LENGTH_SHORT);
                toast.show();
                finish();
                return true;
            }

            case R.id.home_Yes: {
                Intent i = new Intent(DisplayMedication.this, MainMenuActivity.class);
                startActivity(i);

            }

            default: {
                // do nothing
                return super.onContextItemSelected(item);
            }
        }
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
