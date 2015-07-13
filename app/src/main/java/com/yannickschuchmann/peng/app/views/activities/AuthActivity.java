package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.digits.sdk.android.*;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.activities.TransitionActivity;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import io.fabric.sdk.android.Fabric;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Map;


public class AuthActivity extends TransitionActivity {
    private static final String TWITTER_KEY = "vwK7eyt8DGCSsOT7G35hWcCh3";
    private static final String TWITTER_SECRET = "ORpaqLZSHZ3Xwx0cArsMLepcCMePaPGCV7PfjtqzbBVZR2iJp5";

    UserService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        int userId = CurrentUser.getInstance(getContext()).getUserId();
        if (userId != 0) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivityWithAnimation(intent);
        }

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());

        mService = new RestSource().getRestAdapter().create(UserService.class);

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setAuthTheme(R.style.DigitsTheme);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
                TwitterAuthToken authToken = (TwitterAuthToken) session.getAuthToken();
                DigitsOAuthSigning oauthSigning = new DigitsOAuthSigning(authConfig, authToken);

                Map<String, String> authHeaders = oauthSigning.getOAuthEchoHeadersForVerifyCredentials();

                mService.checkCredentials(authHeaders.get("X-Auth-Service-Provider"),
                        authHeaders.get("X-Verify-Credentials-Authorization"), new Callback<User>() {
                            @Override
                            public void success(User user, Response response) {
                                CurrentUser.getInstance(getContext()).setUser(user);
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivityWithAnimation(intent);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(
                                        getContext().getApplicationContext(),
                                        "ups, da ist was schief gegangen",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
            }

            @Override
            public void failure(DigitsException exception) {
            }
        });
    }

    public Context getContext() {
        return this;
    }

}
