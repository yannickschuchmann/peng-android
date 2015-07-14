package com.yannickschuchmann.peng.app.socket;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import br.net.bmobile.websocketrails.WebSocketRailsDataCallback;
import br.net.bmobile.websocketrails.WebSocketRailsDispatcher;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.receiver.DuelReceiver;
import com.yannickschuchmann.peng.app.services.DuelNotificationService;
import com.yannickschuchmann.peng.app.views.activities.SensorActivity;
import com.yannickschuchmann.peng.common.Constants;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.Message;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by yannick on 10.07.15.
 */
public class SocketAPI {
    private static SocketAPI mInstance = null;
    private WebSocketRailsDispatcher mDispatcher;
    private Context mContext;

    private ObjectMapper mapper = new ObjectMapper();

    public SocketAPI(Context context) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mContext = context;
        try {
            mDispatcher = new WebSocketRailsDispatcher(new URL(Constants.API_URL + "/websocket"));
            mDispatcher.connect();
            login();
            bindEvents();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    ;

    public static SocketAPI getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SocketAPI(context);
        }
        return mInstance;
    }

    private void login() {
        Message msg = new Message();
        msg.setUser_id(CurrentUser.getInstance(mContext).getUserId());
        mDispatcher.trigger("users.login", msg);
    }

    private void bindEvents() {
        mDispatcher.bind("duels.challenged", new WebSocketRailsDataCallback() {
            @Override
            public void onDataAvailable(Object data) {
                LinkedHashMap map = (LinkedHashMap) data;
                Duel duel = mapper.convertValue(map, Duel.class);

                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);

                Intent mIntent = new Intent(mContext, SensorActivity.class);
                mIntent.putExtra("duelId", duel.id);
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
                builder.setContentTitle("Du wurdest herausgefordert!");
                builder.setContentText("Von " + duel.getOpponent().getNick());
                builder.setSmallIcon(R.drawable.character_cowboy);
                builder.setContentIntent(pendingIntent);
                builder.setLights(0xffff0000, 1000, 1000);
                builder.setColor(0xff00ff00);

                notificationManager.notify(762, builder.build());
            }
        });

        mDispatcher.bind("duels.action_posted", new WebSocketRailsDataCallback() {
            @Override
            public void onDataAvailable(Object data) {
                LinkedHashMap map = (LinkedHashMap) data;
                Duel duel = mapper.convertValue(map, Duel.class);

                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);

                Intent mIntent = new Intent(mContext, SensorActivity.class);
                mIntent.putExtra("duelId", duel.id);
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
                builder.setContentTitle("Du bist dran!");
                builder.setContentText("Gegen " + duel.getOpponent().getNick());
                builder.setSmallIcon(R.drawable.character_cowboy);
                builder.setContentIntent(pendingIntent);
                builder.setLights(0xffff0000, 1000, 1000);
                builder.setColor(0xff00ff00);

                notificationManager.notify(762, builder.build());
            }
        });
    }


}
