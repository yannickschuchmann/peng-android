package com.yannickschuchmann.peng.app.views.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.MainPresenter;
import com.yannickschuchmann.peng.app.views.views.MainView;


public class MainActivity extends Activity implements MainView {

    @Bind(R.id.user_nick) TextView mNick;
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenter(this);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainPresenter.start();
    }

    @Override
    public void setNick(String nick) {
        mNick.setText(nick);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
