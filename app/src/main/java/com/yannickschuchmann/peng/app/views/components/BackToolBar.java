package com.yannickschuchmann.peng.app.views.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.views.ToolbarBackView;


public class BackToolbar extends LinearLayout {
    TextView mTitleView;

    public BackToolbar(Context context) {
        super(context);
        init(context);
    }

    public BackToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BackToolbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context) {
        this.init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.toolbar_back, this);
        ButterKnife.bind(this, view);
        mTitleView = (TextView) findViewById(R.id.title_text);
    }

    @OnClick(R.id.button_back)
    public void onBack() {
        ToolbarBackView activity = (ToolbarBackView) getContext();
        activity.onToolbarBackClicked();
    }

    public void setTitleText(String text) {
        mTitleView.setText(text);
    }

}
