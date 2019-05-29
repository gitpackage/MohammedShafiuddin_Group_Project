package group4.cs422.medicationmanager;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// class used for managing a date
public class MedicationDate {

    private static Map<Long, MedicationDate> datesMap = new HashMap<>();

    private int month;
    private int day;
    private int year;

    private long epochTime; // epoch time corresponding to this date

    private ArrayList<MedicationTransaction> listOfTransactions;


    private MedicationDate(int month, int day, int year) {
        listOfTransactions = new ArrayList<>(0);

        this.month = month;
        this.day = day;
        this.year = year;

        setEpochTime();
        addDateToDatesMap(this);
    }

    private void setEpochTime() {

        epochTime = TimeAndDates.epochFromDate(month, day, year);
    }

    public int getMonth() { return month;}
    public int getDay() { return day;}
    public int getYear() { return year;}



    private static void addDateToDatesMap(MedicationDate date) {
        Log.i("Date", "Epoch: " + String.valueOf(date.epochTime));

        datesMap.put(date.epochTime, date);

    }


    public static MedicationDate getDateFromEpoch(long epochTime) {
        // gets the date, if there is no date, creates and returns one - Singleton Design Patern

        if (datesMap.get(epochTime) == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(epochTime);

            MedicationDate medDate = new MedicationDate(cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.YEAR));

            // adds the new medication
            datesMap.put(epochTime, medDate);
        }

        return datesMap.get(epochTime);
    }


    public static void addTransactionToDate(int month, int day, int year, MedicationTransaction transaction) {

        MedicationDate date = getDateFromEpoch(TimeAndDates.epochFromDate(month, day, year));

        date.listOfTransactions.add(transaction);

        Log.i("Date", "MedName: " + transaction.getMedName());
        Log.i("Date", "Time: " + transaction.getTimeTaken());
    }

    public ArrayList<MedicationTransaction> getListOfTransactions() { return listOfTransactions;}


    public static void checkListSizes() {
        int position = 1;
        for (MedicationDate date: datesMap.values()) {
            Log.i("Date: ",position + "-listSize: " + date.listOfTransactions.size());
            position++;
        }
    }


}
