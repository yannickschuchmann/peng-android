package com.yannickschuchmann.peng.app.views.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yannickschuchmann.peng.app.R;

/**
 * Created by Simon on 25.10.2015.
 */
public class TutorialPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tutorial_page, container, false);

        return rootView;
    }

    public static void instantiate(Drawable drawable) {

    }
}
