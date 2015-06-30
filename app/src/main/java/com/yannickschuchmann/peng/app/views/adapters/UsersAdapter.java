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
import com.yannickschuchmann.peng.model.User;

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
        UsersRowHolder mh = new UsersRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(UsersRowHolder usersRowHolder, int i) {
        User user = users.get(i);

        usersRowHolder.thumbnail.setImageResource(R.drawable.dummy_profile);
        usersRowHolder.nick.setText(user.getNick());
        usersRowHolder.slogan.setText(user.getSlogan());
        usersRowHolder.rank.setText(user.getRank());
    }

    @Override
    public int getItemCount() {
        return (null != users ? users.size() : 0);
    }

    public class UsersRowHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.nick) TextView nick;
        @Bind(R.id.thumbnail) ImageView thumbnail;
        @Bind(R.id.slogan) TextView slogan;
        @Bind(R.id.rank) TextView rank;

        public UsersRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
