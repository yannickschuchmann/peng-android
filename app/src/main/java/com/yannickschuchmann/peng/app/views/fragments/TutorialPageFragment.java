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

/**
 * Created by Simon on 25.10.2015.
 */
public class TutorialPageFragment extends Fragment {
    /*TEST CODE
    @Bind(R.id.tutorial_image)
    ImageView tutorial_image;
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tutorial_page, container, false);
        /*TEST CODE
        Drawable drawable = getResources().getDrawable(R.drawable.tutorial_dummy_page_0);
        tutorial_image.setImageDrawable(drawable);
        //tutorial.setImageDrawable(null);//R.drawable.tutorial_dummy_page_0
        */
        return rootView;
    }

    public static void instantiate(Drawable drawable) {

    }
}
