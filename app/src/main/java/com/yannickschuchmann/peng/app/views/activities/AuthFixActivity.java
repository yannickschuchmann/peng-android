package com.yannickschuchmann.peng.app.views.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.views.LoadingView;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AuthFixActivity extends LoadingActivity {

    private UserService mService;

    @Bind(R.id.phone_number)
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_fix);
        setTitle("Peng");

        int userId = CurrentUser.getInstance(getContext()).getUserId();
        if (userId != 0) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivityWithAnimation(intent);
        }

        mService = new RestSource().getRestAdapter().create(UserService.class);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.login_button)
    public void onLoginClick() {
        String number = phoneNumber.getText().toString();

        if (PhoneNumberUtils.isGlobalPhoneNumber(number)) {
            this.showLoading();

            mService.checkCredentialsFix(number, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    CurrentUser.getInstance(getContext()).setUser(user);
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    hideLoading();
                    startActivityWithAnimation(intent);
                }

                @Override
                public void failure(RetrofitError error) {
                    hideLoading();
                    Toast.makeText(
                            getContext().getApplicationContext(),
                            "ups, da ist was schief gegangen",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        } else {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    "Keine valide Nummer. Hast du dich vertippt?",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


}
