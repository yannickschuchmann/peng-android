package com.yannickschuchmann.peng.app.views.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;

public class DuelActivity extends TransitionActivity {

    @Bind(R.id.dummy_duel_id) TextView dummyDuelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int duelId = extras.getInt("duelId");
            dummyDuelId.setText(String.valueOf(duelId));
        }
    }
}
