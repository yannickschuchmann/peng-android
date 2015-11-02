package com.yannickschuchmann.peng.app.views.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yannickschuchmann.peng.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 25.10.2015.
 */
public class TutorialPageFragment extends Fragment {
    private String mSlide;

        @Bind(R.id.tutorial_image)
        ImageView tutorial_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tutorial_page, container, false);
        ButterKnife.bind(this, rootView);

        int slideId = getResources().getIdentifier(mSlide, "drawable", "com.yannickschuchmann.peng.app");
        Drawable drawable = getResources().getDrawable(slideId);
        tutorial_image.setImageDrawable(drawable);

        return rootView;
    }

    public static TutorialPageFragment newInstance(String slide){
    TutorialPageFragment fragment = new TutorialPageFragment();
        fragment.mSlide = slide;
        return fragment;
    }
    
    public static void instantiate(Drawable drawable) {

    }
}
