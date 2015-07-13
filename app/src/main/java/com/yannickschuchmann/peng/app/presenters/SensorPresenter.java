package com.yannickschuchmann.peng.app.presenters;

import android.widget.Toast;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.views.helpers.sensors.Movement;
import com.yannickschuchmann.peng.app.views.views.SensorView;
import com.yannickschuchmann.peng.app.views.views.SettingsView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;
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
        mDuelService = new RestSource().getRestAdapter().create(DuelService.class);

        mDuelService.getDuel(mDuelId, CurrentUser.getInstance(mView.getContext()).getUserId(), new Callback<Duel>() {
            @Override
            public void success(Duel duel, Response response) {
                mDuel = duel;
                mView.setupView(duel);
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

    public void setResult(int result) {
        if (mDuel == null) return;
        mDuelService.postAction(mDuel.id, mDuel.getMe().getUserId(), Movement.ResultCodeToString(result),
            new Callback<Duel>() {
                @Override
                public void success(Duel duel, Response response) {
                    mDuel = duel;
                    mView.setupView(duel);
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
}
