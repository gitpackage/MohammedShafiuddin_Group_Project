/*
    Group 4
    CS 422 Spring 2019
    Project Prototype - GR4
    University of Illinois at Chicago

    This activity is where the user can view a list
    of their current medications. Clicking on a specific
    medication takes them to a different activity where
    they can see the details of that medication.


*/
package group4.cs422.medicationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MedicationsListActivity extends AppCompatActivity {


    private Toolbar activityToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_list);

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
        titleText.setText("Current Medications");

        // Create list view
        ListView lv = (ListView) findViewById(R.id.medication_list);

        // Create medication list, give list to an adapter and add adapter to list view
        MedicationAdapter medAdapter = new MedicationAdapter(this, 0, Medication.getMedicationsList());
        lv.setAdapter(medAdapter);

        // When user clicks on an item, do stuff
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                displayMedication(view, position);
            }
        });
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

                Intent i = new Intent(MedicationsListActivity.this, MainMenuActivity.class);
                startActivity(i);
            }

            default: {
                // User selected No in the context menu
                return super.onContextItemSelected(item);
            }
        }

    }

    // This activity shows the details of the medication clicked on
    protected void displayMedication(View view, int position) {

        Intent i = new Intent(MedicationsListActivity.this, DisplayMedication.class);
        i.putExtra("position", position);

        Log.i("Activity", "Starting Activity: DisplayMedication");

        startActivityForResult(i, 999);
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
