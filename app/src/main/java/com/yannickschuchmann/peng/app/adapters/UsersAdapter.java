package com.yannickschuchmann.peng.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_row, null);
        UsersRowHolder mh = new UsersRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(UsersRowHolder usersRowHolder, int i) {
        User user = users.get(i);

        usersRowHolder.thumbnail.setImageResource(R.drawable.dummy_profile);
        usersRowHolder.nick.setText(user.getNick());
    }

    @Override
    public int getItemCount() {
        return (null != users ? users.size() : 0);
    }

    public class UsersRowHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;
        protected TextView nick;

        public UsersRowHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.nick = (TextView) view.findViewById(R.id.nick);
        }

    }
}
