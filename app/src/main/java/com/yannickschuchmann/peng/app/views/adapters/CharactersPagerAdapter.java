package com.yannickschuchmann.peng.app.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.yannickschuchmann.peng.app.views.fragments.CharacterPageFragment;
import com.yannickschuchmann.peng.model.entities.Character;

import java.util.List;

/**
 * Created by yannick on 02.07.15.
 */
public class CharactersPagerAdapter extends FragmentPagerAdapter {

    private List<Character> mCharacters;

    public CharactersPagerAdapter(FragmentManager fragmentManager, List<Character> characters) {
        super(fragmentManager);
        mCharacters = characters;
    }

    @Override
    public Fragment getItem(int position) {
        return CharacterPageFragment.newInstance(mCharacters.get(position));
    }

    @Override
    public int getCount() {
        return mCharacters.size();
    }
}
