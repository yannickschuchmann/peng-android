package com.yannickschuchmann.peng.app.views.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class TutorialImage {
    private int mImageID;
    private Context mContext;

    public TutorialImage(Context context, int id) {
        this.mContext = context;
        this.mImageID = mContext.getResources()
                .getIdentifier("tutorial_dummy_page_" + id, "drawable", "com.yannickschuchmann.peng.app");
    }

    public Drawable getDrawable() {
        return mContext.getResources().getDrawable(mImageID);
    }
}
