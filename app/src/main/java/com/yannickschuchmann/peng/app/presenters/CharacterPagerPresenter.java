package com.yannickschuchmann.peng.app.presenters;

import android.widget.Toast;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.views.CharacterPagerView;
import com.yannickschuchmann.peng.model.entities.Character;
import com.yannickschuchmann.peng.model.entities.User;
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
    private int mActiveId;

    public CharacterPagerPresenter(CharacterPagerView view) {
        mView = view;
    }

    @Override
    public void start() {
        mUserService = new RestSource().getRestAdapter().create(UserService.class);
        mService = new RestSource().getRestAdapter().create(CharacterService.class);

        mView.setToolbarTitle(mView.getContext().getString(R.string.toolbarTitleCharacter));

        mActiveId = CurrentUser.getInstance(mView.getContext()).getCharacterId();

        mView.showLoading();
        mService.getCharacters(new Callback<List<Character>>() {
            @Override
            public void success(List<Character> characters, Response response) {
                mCharacters = characters;
                int activeCharacterIndex = 0;
                for (int i = 0; i < characters.size(); i++) {
                    if (characters.get(i).id == mActiveId) activeCharacterIndex = i;
                }
                mActiveCharacter = characters.get(activeCharacterIndex);
                mView.setPagerAdapter(characters, activeCharacterIndex);
                mView.hideLoading();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(
                        mView.getContext().getApplicationContext(),
                        R.string.toastFailureMessage,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    public void stop() {
    }

    public void updateCharacter() {
        CurrentUser currentUser = CurrentUser.getInstance(mView.getContext());
        currentUser.getUser().characterId = mActiveCharacter.id;
        currentUser.getUser().setCharacterName(mActiveCharacter.getName());
        
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
