/*
    Group 4
    CS 422 Spring 2019
    Project Prototype - GR4
    University of Illinois at Chicago

    This is represents and individual medication.
*/
package group4.cs422.medicationmanager;

import java.util.ArrayList;

public class Medication {

    private static ArrayList<Medication> medicationsList = new ArrayList<>(0);

    private String medName;
    private int doseAmount;         // Number of pills per dose
    private int hourlyFrequency;    // How many hours between dose
    private int daysBetweenDose;    // How many days between dose

    // starting and end times as long integers
    private int startHour;
    private int startMinute;

    private int endHour;
    private int endMinute;


    // Constructor
    public Medication(String medName, int doseAmount, int hourlyFrequency, int daysBetweenDose,
                      int startHour, int startMinute, int endHour, int endMinute) {
        this.medName = medName;
        this.doseAmount = doseAmount;
        this.hourlyFrequency = hourlyFrequency;
        this.daysBetweenDose = daysBetweenDose;

        this.startHour = startHour;
        this.startMinute = startMinute;

        this.endHour = endHour;
        this.endMinute = endMinute;

        // adds the newly created object to the static collection of medications
        medicationsList.add(this);
    }



    // Getter methods
    public String getMedName() { return this.medName; }
    public int getDoseAmount() { return this.doseAmount; }
    public int getHourlyFrequency() { return this.hourlyFrequency; }
    public int getDaysBetweenDose() { return this.daysBetweenDose; }

    public int getStartHour() { return this.startHour;}
    public int getStartMinute() {return  this.startMinute;}

    public int getEndHour() {   return this.endHour;}
    public int getEndMinute() { return this.endMinute;}

    // Setter methods
    public void setMedName(String medName) {this.medName = medName;}
    public void setDoseAmount(int doseAmount) {this.doseAmount = doseAmount;}
    public void setHourlyFrequency(int hourlyFrequency) {this.hourlyFrequency = hourlyFrequency;}
    public void setDaysBetweenDose(int daysBetweenDose) {this.daysBetweenDose = daysBetweenDose;}

    public void setStartHour(int startHour) {this.startHour = startHour;}
    public void setStartMinute(int startMinute) {this.startMinute = startMinute;}

    public void setEndHour(int endHour) {this.endHour = endHour;}
    public void setEndMinute(int endMinute) {this.endMinute = endMinute;}



    public static ArrayList<Medication> getMedicationsList() { return medicationsList;}

    public static Medication getMedicationAtIndex(int index) {
        return medicationsList.get(index);
    }

    public static void deleteMedicationAtIndex(int index) {
        Medication medToDelete = medicationsList.get(index);
        medToDelete = null;
        medicationsList.remove(index);
    }

}

