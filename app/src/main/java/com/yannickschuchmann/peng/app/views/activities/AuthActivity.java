package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Arrays;
import java.util.List;


public class AuthActivity extends TransitionActivity implements FacebookCallback<LoginResult> {
    List<String> permissionNeeds= Arrays.asList("public_profile", "email", "user_friends");

    UserService mService;
    CallbackManager callbackManager;

    @Bind(R.id.login_button)
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                if (newAccessToken != null) {
                    loginFacebookUser();
                }
            }
        };

        mService = new RestSource().getRestAdapter().create(UserService.class);

        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setTitle(R.string.app_name);

        loginButton.setReadPermissions(permissionNeeds);
        loginButton.registerCallback(callbackManager, this);

    }

    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        loginFacebookUser();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException e) {

    }

    public void loginFacebookUser() {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,picture,friends");
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                mService.loginFacebook(new Gson().fromJson(jsonObject.toString(), User.class), new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        CurrentUser.getInstance(getContext()).setUser(user);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivityWithAnimation(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }
        });
        request.setParameters(parameters);
        request.executeAsync();
    }
}
