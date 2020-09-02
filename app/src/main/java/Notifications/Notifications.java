package Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.guardnet_lite_gabrovo.MainActivity;
import com.example.guardnet_lite_gabrovo.R;

public class Notifications {

    private NotificationManager notifManager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotification(String Notification_title,String content, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = String.valueOf(NOTIFY_ID);
        String title = context.getString(R.string.app_name);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);// = NotificationVisibility.Public;
                mChannel.enableLights(true);

                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(Notification_title)                            // required
                //    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    //      .setContentText(context.getString(R.string.app_name)) // required
                    .setContentText(content)
                    .setOngoing(true)
                    .setTimeoutAfter(-1)
                    .setDefaults(Notification.DEFAULT_ALL)
                //    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                //    .setTicker(aMessage)
                    .setSound(alarmSound)

                    //         .setLights(Color.YELLOW, 500, 5000)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                //    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);

            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(Notification_title)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
             //       .setAutoCancel(true)
                    .setOngoing(true)
              //      .setTimeoutAfter(-1)
                    .setContentIntent(pendingIntent)
                  //  .setTicker(Notification_title)
                    .setSound(alarmSound)
                 //   .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

}
