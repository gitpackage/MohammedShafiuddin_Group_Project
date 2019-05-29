package group4.cs422.medicationmanager;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeAndDates {

    private static String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday"};
    private static String[] months = { "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December" };


    public static String getDateString(long epoch) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(epoch);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);

        return (days[dayOfWeek - 1] + ",\n" + months[month] + " " + day + ordinal(day) + ", " + year);
    }

    public static String timeStringFromValues(int hour, int minute) {

        String AMorPM = "AM";

        Log.i("Time", "Hour is " + String.valueOf(hour));
        if (hour >= 12) {
            AMorPM = "PM";
            hour -= 12;
        }

        if (hour == 0)
            hour = 12;

        return (String.format("%d:%02d", hour, minute) + " " + AMorPM);

    }

    public static long epochFromDate(int month ,int day, int year) {
        long epochTime = -1;

        try {
            String dateString = month + "/" + day + "/" + year;
            epochTime =  new SimpleDateFormat("MM/dd/yyyy").parse(dateString).getTime();
        } catch (Exception e) {
            Log.i("TimeAndDate", "Caught Parsing Exception");
        }

        return epochTime;
    }

    private static String ordinal(int value) {
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
