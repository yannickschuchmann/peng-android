package com.yannickschuchmann.peng.app.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.adapters.DuelsAdapter;
import com.yannickschuchmann.peng.model.entities.Duel;

import java.util.List;


public class DuelsFragment extends Fragment {
    protected DuelsAdapter mAdapter;
    @Bind(R.id.duelRecyclerView) RecyclerView mRecyclerView;
    private List<Duel> mDuels;


    public static DuelsFragment newInstance(Bundle bundle, List<Duel> duels) {
        DuelsFragment fragment = new DuelsFragment();
        fragment.setArguments(bundle);
        fragment.mDuels = duels;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_duels, container, false);
        ButterKnife.bind(this, view);

        int viewHeight = 100 * mDuels.size();
        mRecyclerView.getLayoutParams().height = viewHeight;

        initRecyclerView(mDuels);

        return view;
    }

    private void initRecyclerView(List<Duel> list) {
        mAdapter = new DuelsAdapter(getActivity().getApplicationContext(), list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

}
