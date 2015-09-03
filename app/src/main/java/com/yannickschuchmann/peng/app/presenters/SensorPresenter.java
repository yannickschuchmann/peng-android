package com.yannickschuchmann.peng.app.presenters;

import android.app.Activity;
import android.widget.Toast;
import com.squareup.otto.Subscribe;
import com.yannickschuchmann.peng.app.BusProvider;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.events.ActionPostedEvent;
import com.yannickschuchmann.peng.app.socket.SocketAPI;
import com.yannickschuchmann.peng.app.views.helpers.sensors.Movement;
import com.yannickschuchmann.peng.app.views.views.SensorView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.DuelService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yannick on 30.06.15.
 */
public class SensorPresenter extends Presenter {
    private SensorView mView;
    private int mDuelId;
    private DuelService mDuelService;
    private Duel mDuel;
    public SensorPresenter(SensorView view, int duelId) {
        mDuelId = duelId;
        mView = view;
    }

    @Override
    public void start() {
        BusProvider.getInstance().register(this);

        mDuelService = new RestSource().getRestAdapter().create(DuelService.class);

        mDuelService.getDuel(mDuelId, CurrentUser.getInstance(mView.getContext()).getUserId(), new Callback<Duel>() {
            @Override
            public void success(Duel duel, Response response) {
                mDuel = duel;
                mView.setupView(duel, true);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(
                        mView.getContext().getApplicationContext(),
                        "ups, da ist was schief gegangen",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    public void stop() {

    }

    public Duel getDuel() {
        return mDuel;
    }

    public boolean setResult(int result) {
        if (mDuel == null) return false;

        if (mDuel.getMe().getShots() == 0 && result == 1) {
            Toast.makeText(
                    mView.getContext().getApplicationContext(),
                    "Keine Patronen mehr! Du musst Nachladen!",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }
        if (mDuel.getMe().getShots() == 3 && result == 2) {
            Toast.makeText(
                    mView.getContext().getApplicationContext(),
                    "Trommel ist voll! Schiess mal!",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

        mDuelService.postAction(mDuel.id, mDuel.getMe().getUserId(), Movement.ResultCodeToString(result),
            new Callback<Duel>() {
                @Override
                public void success(Duel duel, Response response) {
                    mDuel = duel;
                    mView.setupView(duel, duel.isMyTurn());
                    System.out.println(SocketAPI.getStatus());
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(
                            mView.getContext().getApplicationContext(),
                            "ups, da ist was schief gegangen",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        });
        return true;
    }

    @Subscribe public void onActionPosted(ActionPostedEvent event) {
        mDuel = event.duel;
        Activity activity = (Activity) mView.getContext();
        System.out.println("BusEvent: " + SocketAPI.getStatus());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.receiveAction(mDuel, mDuel.isMyTurn());
            }
        });
    }
}
