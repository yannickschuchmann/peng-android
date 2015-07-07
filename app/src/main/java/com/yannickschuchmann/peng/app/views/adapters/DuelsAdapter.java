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
import com.yannickschuchmann.peng.app.views.views.DuelAdapterView;
import com.yannickschuchmann.peng.app.views.views.UserAdapterView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;

import java.util.List;

/**
 * Created by yannick on 29.06.15.
 */
public class DuelsAdapter extends RecyclerView.Adapter<DuelsAdapter.DuelsRowHolder> {

    private List<Duel> duels;
    private Context mContext;

    public DuelsAdapter(Context context, List<Duel> duels) {
        this.duels = duels;
        this.mContext = context;
    }

    @Override
    public DuelsRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.duels_row, viewGroup, false);
        DuelsRowHolder mh = new DuelsRowHolder(v, new DuelsRowHolder.IDuelsRowHolderClicks() {
            @Override
            public void onItem(View caller, Duel duel) {

                DuelAdapterView activity = (DuelAdapterView) caller.getContext();

                caller.setSelected(true);

                activity.onDuelClicked(duel);
            }
        });
        return mh;
    }

    @Override
    public void onBindViewHolder(DuelsRowHolder duelsRowHolder, int i) {
        Duel duel = duels.get(i);


        duelsRowHolder.mDuel = duel;

        User opponent = new User();
        opponent.setNick("Gegnername");

//        CharacterImage ci = new CharacterImage(mContext, duel);
//        duelsRowHolder.thumbnail.setImageDrawable(ci.getBlueDrawable());
        duelsRowHolder.nick.setText(opponent.getNick());
        duelsRowHolder.bet.setText(duel.getBet());
        duelsRowHolder.status.setText(duel.getStatus());
    }

    @Override
    public int getItemCount() {
        return (null != duels ? duels.size() : 0);
    }

    public static class DuelsRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.nick) TextView nick;
        @Bind(R.id.thumbnail) ImageView thumbnail;
        @Bind(R.id.bet) TextView bet;
        @Bind(R.id.status) TextView status;

        public Duel mDuel;

        IDuelsRowHolderClicks mListener;

        public DuelsRowHolder(View view, IDuelsRowHolderClicks listener) {
            super(view);
            mListener = listener;
            ButterKnife.bind(this, view);

            view.setSelected(false);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItem(v, mDuel);
        }

        public static interface IDuelsRowHolderClicks {
            public void onItem(View caller, Duel duel);
        }
    }
}
