package com.yannickschuchmann.peng.app.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.helpers.CharacterImage;
import com.yannickschuchmann.peng.model.entities.Character;

/**
 * Created by yannick on 02.07.15.
 */
public class CharacterPageFragment extends Fragment {
    Character mCharacter;

    @Bind(R.id.character_image)
    ImageView mImage;
    @Bind(R.id.character_name)
    TextView mName;
    @Bind(R.id.character_description)
    TextView mDescription;


    public static CharacterPageFragment newInstance(Character character) {
        CharacterPageFragment fragment = new CharacterPageFragment();
        fragment.mCharacter = character;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_page, container, false);
        ButterKnife.bind(this, view);

        CharacterImage characterImage = new CharacterImage(getActivity(), mCharacter.getName());
        mImage.setImageDrawable(characterImage.getDrawable());
        mName.setText(mCharacter.getNameDe());
        mDescription.setText(mCharacter.getDescription());

        return view;
    }
}
