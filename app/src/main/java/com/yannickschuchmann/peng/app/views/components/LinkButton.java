package com.yannickschuchmann.peng.app.views.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yannickschuchmann.peng.app.R;


public class LinkButton extends LinearLayout {
    TextView mLinkButtonView;

    public LinkButton(Context context) {
        super(context);
        init(context);
    }

    public LinkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LinkButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context) {
        this.init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.link_button, this);
        mLinkButtonView = (TextView) findViewById(R.id.button_text);

        String text = "ButtonText";

        // Assign custom attributes
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LinkButton,
                    0, 0);


            try {
                text = a.getString(R.styleable.LinkButton_ButtonText);
            } catch (Exception e) {
                Log.e("LinkButton", "There was an error loading attributes.");
            } finally {
                a.recycle();
            }

        }
        setButtonText(text);
    }

    public void setButtonText(String text) {
        mLinkButtonView.setText(text);
    }

}
