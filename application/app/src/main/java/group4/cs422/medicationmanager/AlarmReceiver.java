package group4.cs422.medicationmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {


    // TODO - get relevant medication information to use with this notification

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationChannel notificationChannel = new NotificationChannel("CHANNELID", "name", NotificationManager.IMPORTANCE_HIGH );
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        notificationManager.createNotificationChannel(notificationChannel);

        Intent i = new Intent(context, DisplayMedication.class);

        // FIXME - hardcoded values
        i.putExtra("MedicationName","Vicodin" + "");
        i.putExtra("DoseAmount","1" + "");
        i.putExtra("HourlyFrequency","2" + "");
        i.putExtra("DaysBetweenDose","7" + "");
        /* <--- above this line ---> */


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNELID")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Medication Time")
                .setContentText("Time to take a medication")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(0, builder.build());


    }
}
