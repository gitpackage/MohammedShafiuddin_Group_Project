package group4.cs422.medicationmanager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditMedicationActivity extends AppCompatActivity {

    Button confirmButton;

    EditText medicationNameInput;
    EditText amountToTakeInput;
    EditText timeCycleInput;
    EditText daysRepeatedInput;


    Button startTimeButton;
    Button endTimeButton;

    // starting time selection parameters
    private int sHour;
    private int sMinute;

    // ending time selection parameters
    private int eHour;
    private int eMinute;

    private int indexFromMedicationList;
    private Medication selectedMedication;

    private Toolbar activityToolbar;

    private final int CONFIRM_EDIT_MENU = 1;
    private final int CONFIRM_HOME_MENU = 2;
    private int whichContextMenu = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmedication);

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
        titleText.setText("Editing Medication");

        Intent intent  = getIntent();
        indexFromMedicationList = intent.getIntExtra("position", -1);
        selectedMedication = Medication.getMedicationAtIndex(indexFromMedicationList);


        // ActionBar Setup

        // Edit Text fields
        medicationNameInput = (EditText)findViewById(R.id.medicationNameInput);
        amountToTakeInput = (EditText)findViewById(R.id.amountToTakeInput);
        timeCycleInput = (EditText)findViewById(R.id.timeCycleInput);
        daysRepeatedInput = (EditText)findViewById(R.id.daysRepeatedInput);

        // focus change listeners - hides the keyboard if any of the input fields loses focus
        medicationNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        amountToTakeInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        timeCycleInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        daysRepeatedInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        startTimeButton = (Button) findViewById(R.id.startTimeInput);

        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates a time picker dialog for start time
                createTimePicker(startTimeButton);
            }
        });


        endTimeButton = (Button)findViewById(R.id.endTimeInput);
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates a time picker dialog for end time
                createTimePicker(endTimeButton);
            }
        });

        fillFields(selectedMedication);

        sHour = selectedMedication.getStartHour();
        sMinute = selectedMedication.getStartMinute();

        eHour = selectedMedication.getEndHour();
        eMinute = selectedMedication.getEndMinute();

        confirmButton = (Button)findViewById(R.id.confirmEditButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {


            // clicking the button will display a confirmation
            @Override
            public void onClick(View v) {

                // boolean value that checks if all the inputs have values
                boolean allFieldsFilled = (medicationNameInput.getText().length() > 0
                        && amountToTakeInput.getText().length() > 0 && timeCycleInput.getText().length() > 0
                        && daysRepeatedInput.getText().length() > 0
                        && startTimeButton.getText().length() > 0 && endTimeButton.getText().length() > 0);


                boolean validTimeSelections = (sHour <= eHour);

                if (!allFieldsFilled) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please fill in all of the fields.", Toast.LENGTH_LONG);
                    toast.show();
                }

                else if (!validTimeSelections) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Ending Time must come after Starting Time", Toast.LENGTH_LONG);
                    toast.show();
                }

                else {
                    whichContextMenu = CONFIRM_EDIT_MENU;
                    registerForContextMenu(findViewById(android.R.id.content));
                    openContextMenu(findViewById(android.R.id.content));
                }

            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(EditMedicationActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void createTimePicker(final Button button) {

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                // updates start time
                if (button.equals(startTimeButton)) {
                    sHour = hourOfDay;
                    sMinute = minute;
                }

                if (button.equals(endTimeButton)) {
                    eHour = hourOfDay;
                    eMinute = minute;
                }

                button.setText(TimeAndDates.timeStringFromValues(hourOfDay, minute));
                Log.i("Add", "hourOfDay is: " + hourOfDay);
            }

        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditMedicationActivity.this,
                R.style.TimePickerTheme, timeSetListener, 12, 0, false);
        timePickerDialog.show();



    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (whichContextMenu) {

            case CONFIRM_EDIT_MENU: {
                menu.setHeaderTitle("Change Medication?");
                getMenuInflater().inflate(R.menu.confirm_edit_menu, menu);
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

        switch (item.getItemId()) {
            case R.id.confirm_Yes: {

                changeMedicationValues(selectedMedication);
                Toast toast = Toast.makeText(getApplicationContext(), "Changed Medication Information", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(EditMedicationActivity.this, DisplayMedication.class);
                i.putExtra("position", indexFromMedicationList);
                setResult(RESULT_OK);
                startActivity(i);
                finish();
                return true;
            }


            case R.id.home_Yes: {

                Intent i = new Intent(EditMedicationActivity.this, MainMenuActivity.class);
                startActivity(i);
                finish();
            }

            default: {
                // User selected No in the context menu
                return super.onContextItemSelected(item);
            }
        }

    }

    // function that is used to fill in the values
    private void fillFields(Medication medication) {

        medicationNameInput.setText(medication.getMedName());
        amountToTakeInput.setText(String.valueOf(medication.getDoseAmount()));
        timeCycleInput.setText(String.valueOf(medication.getHourlyFrequency()));
        daysRepeatedInput.setText(String.valueOf(medication.getDaysBetweenDose()));

        startTimeButton.setText(TimeAndDates.timeStringFromValues(medication.getStartHour(),
                medication.getStartMinute()));

        endTimeButton.setText(TimeAndDates.timeStringFromValues(medication.getEndHour(),
                medication.getEndMinute()));
    }

    // function used if user confirms to edit medication values
    private void changeMedicationValues(Medication medication) {
        medication.setMedName(medicationNameInput.getText().toString());
        medication.setDoseAmount(Integer.parseInt(amountToTakeInput.getText().toString()));
        medication.setHourlyFrequency(Integer.parseInt(timeCycleInput.getText().toString()));
        medication.setDaysBetweenDose(Integer.parseInt(daysRepeatedInput.getText().toString()));

        medication.setStartHour(sHour);
        medication.setStartMinute(sMinute);
        medication.setEndHour(eHour);
        medication.setEndMinute(eMinute);

    }

}
