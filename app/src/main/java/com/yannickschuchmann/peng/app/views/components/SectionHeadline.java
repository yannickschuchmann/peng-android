package com.yannickschuchmann.peng.app.views.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yannickschuchmann.peng.app.R;


public class SectionHeadline extends LinearLayout {
    TextView mHeadlineView;

    public SectionHeadline(Context context) {
        super(context);
        init(context);
    }

    public SectionHeadline(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SectionHeadline(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context) {
        this.init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.section_headline, this);
        mHeadlineView = (TextView) findViewById(R.id.headline);

        String headlineText = "HeadlineText";

        // Assign custom attributes
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.SectionHeadline,
                    0, 0);


            try {
                headlineText = a.getString(R.styleable.SectionHeadline_headlineText);
            } catch (Exception e) {
                Log.e("SectionHeadline", "There was an error loading attributes.");
            } finally {
                a.recycle();
            }

        }
        setHeadlineText(headlineText);
    }

    public void setHeadlineText(String text) {
        mHeadlineView.setText(text);
    }

}
