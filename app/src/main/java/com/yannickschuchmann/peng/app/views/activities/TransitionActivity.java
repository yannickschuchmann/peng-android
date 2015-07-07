package com.yannickschuchmann.peng.app.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.MainPresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by yannick on 01.07.15.
 */
public class TransitionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Futura-Extended.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

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
