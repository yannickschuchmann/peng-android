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
import com.yannickschuchmann.peng.model.rest.services.UserService;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class AuthActivity extends TransitionActivity implements FacebookCallback<LoginResult> {
    List<String> permissionNeeds= Arrays.asList("public_profile", "email");

    UserService mService;
    CallbackManager callbackManager;

    @Bind(R.id.login_button)
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();


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
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,last_name,link,email,picture");
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                CurrentUser.getInstance(getContext()).setUser(new Gson().fromJson(jsonObject.toString(), User.class));
            }
        });
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException e) {

    }
}
