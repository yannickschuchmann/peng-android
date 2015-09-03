package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.login.widget.LoginButton;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.model.rest.services.UserService;


public class AuthActivity extends TransitionActivity {
    UserService mService;

    @Bind(R.id.login_button)
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setTitle(R.string.app_name);

        ButterKnife.bind(this);

        int userId = CurrentUser.getInstance(getContext()).getUserId();
        if (userId != 0) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivityWithAnimation(intent);
        }

    }

    public Context getContext() {
        return this;
    }

}
