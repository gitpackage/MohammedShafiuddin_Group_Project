package group4.cs422.medicationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    private Toolbar activityToolbar;
    private boolean switchOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        activityToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);
        getSupportActionBar().setTitle(null);

        Switch settingsSwitch = (Switch)findViewById(R.id.reminderSwitch);
        settingsSwitch.setChecked(true);

        final RadioButton alarmButton = (RadioButton)findViewById(R.id.alarm);
        final RadioButton systemButton  = (RadioButton)findViewById(R.id.system);

        alarmButton.setChecked(true);


        settingsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchOn = !switchOn;

                if (!switchOn) {
                    alarmButton.setEnabled(false);
                    systemButton.setEnabled(false);
                }

                else {
                    alarmButton.setEnabled(true);
                    systemButton.setEnabled(true);
                }
            }
        });


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
        titleText.setText("Settings");


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

                Intent i = new Intent(SettingsActivity.this, MainMenuActivity.class);
                startActivity(i);
                finish();
            }

            default: {
                // User selected No in the context menu
                return super.onContextItemSelected(item);
            }
        }

    }
}
