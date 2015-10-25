package com.yannickschuchmann.peng.app.views.views;

import com.yannickschuchmann.peng.model.entities.Character;

import java.util.List;

/**
 * Created by yannick on 30.06.15.
 */
public interface TutorialPagerView extends ToolbarBackView {
    void setPagerAdapter(List<Character> characters, int currentIndex);
}
