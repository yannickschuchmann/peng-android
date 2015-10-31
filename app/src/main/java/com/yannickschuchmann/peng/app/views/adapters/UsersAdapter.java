package com.yannickschuchmann.peng.app.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.helpers.CharacterImage;
import com.yannickschuchmann.peng.app.views.views.UserAdapterView;
import com.yannickschuchmann.peng.model.entities.User;

import java.util.List;

/**
 * Created by yannick on 29.06.15.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersRowHolder> {

    private List<User> users;
    private Context mContext;

    public UsersAdapter(Context context, List<User> users) {
        this.users = users;
        this.mContext = context;
    }

    @Override
    public UsersRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_row, viewGroup, false);
        UsersRowHolder mh = new UsersRowHolder(v, new UsersRowHolder.IUsersRowHolderClicks() {
            @Override
            public void onItem(View caller, User user) {

                UserAdapterView activity = (UserAdapterView) caller.getContext();

                caller.setSelected(true);

                activity.onItemClicked(user);
            }
        });
        return mh;
    }

    @Override
    public void onBindViewHolder(UsersRowHolder usersRowHolder, int i) {
        User user = users.get(i);

        CharacterImage ci = new CharacterImage(mContext, user);

        usersRowHolder.thumbnail.setImageDrawable(ci.getBlueDrawable());
        usersRowHolder.mUser = user;
        usersRowHolder.nick.setText(user.getNick());
        usersRowHolder.slogan.setText(user.getSlogan());
        usersRowHolder.rank.setText(String.valueOf(user.getRank()));
        usersRowHolder.rankPlace.setText(mContext.getString(R.string.rankingPlace));
    }

    @Override
    public int getItemCount() {
        return (null != users ? users.size() : 0);
    }

    public static class UsersRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.nick) TextView nick;
        @Bind(R.id.thumbnail) ImageView thumbnail;
        @Bind(R.id.slogan) TextView slogan;
        @Bind(R.id.rank) TextView rank;
        @Bind(R.id.rankPlace) TextView rankPlace;

        public User mUser;

        IUsersRowHolderClicks mListener;

        public UsersRowHolder(View view, IUsersRowHolderClicks listener) {
            super(view);
            mListener = listener;
            ButterKnife.bind(this, view);

            view.setSelected(false);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItem(v, mUser);
        }

        public static interface IUsersRowHolderClicks {
            public void onItem(View caller, User user);
        }
    }

    public void clearData() {
        int size = this.users.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.users.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

}
