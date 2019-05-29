package group4.cs422.medicationmanager;

import java.util.ArrayList;
import java.util.Map;

// class responsible for cataloging when medications were taken
public class MedicationTransaction {

    private String medName;
    private int hourTaken;
    private int minuteTaken;


    public MedicationTransaction(String medName, int hour, int minute) {

        this.medName = medName;
        this.hourTaken = hour;
        this.minuteTaken = minute;
    }

    public String getMedName() { return medName;}
    public String getTimeTaken() { return TimeAndDates.timeStringFromValues(hourTaken, minuteTaken);}

}
