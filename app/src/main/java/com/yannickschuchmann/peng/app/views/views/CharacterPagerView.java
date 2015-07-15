package com.yannickschuchmann.peng.app.views.views;

import android.graphics.drawable.Drawable;
import com.yannickschuchmann.peng.model.entities.*;
import com.yannickschuchmann.peng.model.entities.Character;

import java.util.List;

/**
 * Created by yannick on 30.06.15.
 */
public interface CharacterPagerView extends LoadingToolbarBackView {
    void setPagerAdapter(List<Character> characters, int currentIndex);
}
