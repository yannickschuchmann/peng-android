package com.yannickschuchmann.peng.app.views.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.model.entities.User;

/**
 * Created by yannick on 02.07.15.
 */
public class CharacterImage {
    private User mUser;
    private Drawable mDrawable;
    private int mImageID;
    private Context mContext;

    public CharacterImage(Context context, User user) {
        this.mUser = user;
        this.mContext = context;
        this.mImageID = mContext.getResources()
                .getIdentifier("character_" + mUser.getCharacterName(), "drawable", "com.yannickschuchmann.peng.app");
    }

    public Drawable getDrawable() {
        return mContext.getResources().getDrawable(mImageID);
    }

    private Drawable createFilteredDrawable(int color) {
        return ImageFilter.applyFiltersForColor(mContext, mImageID, color);
    }

    public Drawable getBlueDrawable() {
        return createFilteredDrawable(R.color.image_blue_filter);
    }
}
