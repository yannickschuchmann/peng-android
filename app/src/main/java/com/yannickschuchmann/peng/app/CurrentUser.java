package com.yannickschuchmann.peng.app;

import android.content.Context;
import android.content.SharedPreferences;
import com.yannickschuchmann.peng.model.entities.User;

/**
 * Created by yannick on 02.07.15.
 */
public class CurrentUser {
    private static CurrentUser mInstance = null;
    private User mUser;
    private static Context mContext;
    public static final String PREFS_NAME = "CurrentUserFile";

    public CurrentUser() {}

    public static CurrentUser getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new CurrentUser();
        }
        return mInstance;
    }

    public void setUser(User user) {
        mUser = user;
        setUserId(user.id);
    }

    public User getUser() {
        return mUser;
    }

    public void setUserId(int id) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("userId", id);
        editor.commit();
    }

    public int getUserId() {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt("userId", 0);
    }

    public int getCharacterId() {
        return mUser.getCharacterOrder();
    }
}
