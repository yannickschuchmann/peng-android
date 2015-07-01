package com.yannickschuchmann.peng.app.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.MainPresenter;

/**
 * Created by yannick on 01.07.15.
 */
public class TransitionActivity extends Activity {
    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    public void startActivityWithAnimation(Intent intent, boolean back) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        if (!back) {
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        } else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
    }

    public void startActivityWithAnimation(Intent intent) {
        this.startActivityWithAnimation(intent, false);
    }

}
