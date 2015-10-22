package com.yannickschuchmann.peng.app.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.activities.SensorActivity;

/**
 * Created by yannick on 10.07.15.
 */
public class DuelNotificationService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, SensorActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(getString(R.string.notificationContentTitleChallange));
        builder.setContentText(String.valueOf(intent.getIntExtra("duelId", 0)));
        builder.setSmallIcon(R.drawable.character_cowboy);
        builder.setContentIntent(pendingIntent);
        builder.setLights(0xffff0000, 1000, 1000);
        builder.setColor(0xff00ff00);

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
