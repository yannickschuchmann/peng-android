package com.yannickschuchmann.peng.app.presenters;

import android.content.Context;
import android.widget.Toast;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.views.helpers.CharacterImage;
import com.yannickschuchmann.peng.app.views.views.CharacterPagerView;
import com.yannickschuchmann.peng.app.views.views.MainView;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.entities.Character;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.CharacterService;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by yannick on 30.06.15.
 */
public class CharacterPagerPresenter extends Presenter {

    private CharacterPagerView mView;
    private CharacterService mService;
    private UserService mUserService;
    private Character mActiveCharacter;
    private List<Character> mCharacters;
    private int mActiveIndex;

    public CharacterPagerPresenter(CharacterPagerView view) {
        mView = view;
    }

    @Override
    public void start() {
        mUserService = new RestSource().getRestAdapter().create(UserService.class);
        mService = new RestSource().getRestAdapter().create(CharacterService.class);

        mView.setToolbarTitle("Charakter");

        mActiveIndex = CurrentUser.getInstance(mView.getContext()).getCharacterId();

        mService.getCharacters(new Callback<List<Character>>() {
            @Override
            public void success(List<Character> characters, Response response) {
                mCharacters = characters;
                mActiveCharacter = characters.get(mActiveIndex);
                mView.setPagerAdapter(characters, mActiveIndex);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void stop() {
    }

    public void updateCharacter() {
        CurrentUser currentUser = CurrentUser.getInstance(mView.getContext());
        currentUser.getUser().characterId = mActiveCharacter.id;
        mUserService.updateUser(currentUser.getUserId(),
                currentUser.getUser(),
                new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Character savedCharacter = mCharacters.get(user.getCharacterOrder());
                        Toast.makeText(mView.getContext(), "Saved " + savedCharacter.getNameDe(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });


    }

    public void onCharacterChanged(int position) {
        mActiveCharacter = mCharacters.get(position);
    }


}