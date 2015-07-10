package com.yannickschuchmann.peng.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.yannickschuchmann.peng.app.services.DuelNotificationService;

/**
 * Created by yannick on 10.07.15.
 */
public class DuelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getIntExtra("duelId", 0) != 0) {
            Intent notiService = new Intent(context, DuelNotificationService.class);
            context.startService(notiService);
        }
    }
}
