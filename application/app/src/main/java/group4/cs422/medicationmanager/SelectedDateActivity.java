package group4.cs422.medicationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class SelectedDateActivity extends AppCompatActivity {

    private TextView selectedDateText;
    private String[] months = { "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December" };


    private ListView listView;

    private Toolbar activityToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecteddate);

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
        titleText.setText("Medications Taken");

        selectedDateText = (TextView)findViewById(R.id.selectedDateText);
        TextView medLabel = (TextView)findViewById(R.id.medicationLabel);
        TextView timeLabel = (TextView)findViewById(R.id.timeLabel);
        TextView noneLabel = (TextView)findViewById(R.id.noneMessage);

        Intent i = getIntent();
        int mYear = i.getIntExtra("year", 0);
        int mMonth = i.getIntExtra("month", 0);
        int mDayOfMonth = i.getIntExtra("dayOfMonth", 0);

        selectedDateText.setText(months[mMonth] + " " + mDayOfMonth + ordinal(mDayOfMonth) + ", " + mYear);

        // Get TransactionDate from epochTime
        long epochTime = TimeAndDates.epochFromDate(mMonth, mDayOfMonth, mYear);


        MedicationDate selectedDate = MedicationDate.getDateFromEpoch(epochTime);

        if (selectedDate.getListOfTransactions().size() == 0) {
            medLabel.setText("");
            timeLabel.setText("");
            noneLabel.setVisibility(View.VISIBLE);
        }


        listView = (ListView)findViewById(R.id.historyList);
        listView.setAdapter(new HistoryItemAdapter(this, selectedDate.getListOfTransactions()));

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

                Intent i = new Intent(SelectedDateActivity.this, MainMenuActivity.class);
                startActivity(i);
            }

            default: {
                // User selected No in the context menu
                return super.onContextItemSelected(item);
            }
        }

    }

    private String ordinal(int value) {
        int r = value % 10;
        switch (r) {
            case 1: {
                if (value == 11)
                    return "th";
                else
                    return "st";
            }
            case 2: {
                if (value == 12)
                    return "th";
                else
                    return "nd";
            }
            case 3: {
                if (value == 13)
                    return "th";
                else
                    return "rd";
            }
            case 4: {
                return "th";
            }
            default: {
                return "th";
            }
        }
    }


}