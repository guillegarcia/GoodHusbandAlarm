package programadorandroid.com.goodhusbandalarm.broadcastReceiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import programadorandroid.com.goodhusbandalarm.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver{
    private final static String LOG_TAG = "AlarmReceiver";
    private static final String NOTIFICATION_CHANNEL_ID = "dgh_channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Log.d(LOG_TAG, "onReceive");
        createAnniversaryNotification(context);
    }

    private void createAnniversaryNotification(Context context){
        Log.d(LOG_TAG,"createMessageNotification");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(context.getString(R.string.annyversaryNotificationTitle))
                .setContentText(context.getString(R.string.annyversaryNotificationText))
                .setAutoCancel(true);

        // Creates an explicit intent for an Activity in your app
        //Intent resultIntent = new Intent(this, MainActivity.class);
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);

        // Gets an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        // Builds the notification and issues it.
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
