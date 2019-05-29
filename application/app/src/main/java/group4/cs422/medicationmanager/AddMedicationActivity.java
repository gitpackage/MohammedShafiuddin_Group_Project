package group4.cs422.medicationmanager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddMedicationActivity extends AppCompatActivity {


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

    Toolbar activityToolbar;


    private final int CONFIRM_ADD_MENU = 1;
    private final int CONFIRM_HOME_MENU = 2;
    private int whichContextMenu = - 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmedication);

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
        titleText.setText("Adding New Medication");

        // Edit Text fields
        medicationNameInput = (EditText)findViewById(R.id.medicationNameInput);
        amountToTakeInput = (EditText)findViewById(R.id.amountToTakeInput);
        timeCycleInput = (EditText)findViewById(R.id.timeCycleInput);
        daysRepeatedInput = (EditText)findViewById(R.id.daysRepeatedInput);


        // removes suggestions appearing for this input field
        medicationNameInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS |
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);


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


        confirmButton = (Button)findViewById(R.id.confirmAddButton);

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
                    whichContextMenu = CONFIRM_ADD_MENU;
                    registerForContextMenu(findViewById(android.R.id.content));
                    openContextMenu(findViewById(android.R.id.content));
                }

            }
        });

    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(AddMedicationActivity.INPUT_METHOD_SERVICE);
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


                String timeString = TimeAndDates.timeStringFromValues(hourOfDay, minute);

                button.setText(timeString);
                Log.i("Add", "hourOfDay is: " + hourOfDay);
            }

        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddMedicationActivity.this,
                R.style.TimePickerTheme, timeSetListener, 12, 0, false);
        timePickerDialog.show();

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (whichContextMenu) {

            case CONFIRM_ADD_MENU: {
                menu.setHeaderTitle("Add Medication?");
                getMenuInflater().inflate(R.menu.confirm_add_menu, menu);
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
            case R.id.add_Yes: {

                addMedication();

                Toast toast = Toast.makeText(getApplicationContext(), "Medication Added", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(AddMedicationActivity.this, MedicationsListActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            case R.id.home_Yes: {
                Intent i = new Intent(AddMedicationActivity.this, MainMenuActivity.class);
                startActivity(i);
                finish();
            }

            default: {
                // User selected No in either context menu
                return super.onContextItemSelected(item);
            }
        }

    }

    // add parameters and create new medication from list
    private void addMedication() {

        String medName = medicationNameInput.getText().toString();
        int amountToTake = Integer.parseInt(amountToTakeInput.getText().toString());
        int timeCycle = Integer.parseInt(timeCycleInput.getText().toString());
        int daysRepeated = Integer.parseInt(daysRepeatedInput.getText().toString());


        // creating this medication adds it to the list of medications
        Medication medication = new Medication(medName, amountToTake, timeCycle, daysRepeated,
                sHour, sMinute, eHour, eMinute);
    }


}
