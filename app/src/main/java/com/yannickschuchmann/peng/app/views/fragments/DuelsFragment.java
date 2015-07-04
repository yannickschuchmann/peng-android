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
import com.yannickschuchmann.peng.app.views.adapters.UsersAdapter;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;


public class DuelsFragment extends Fragment {
    protected DuelsAdapter mAdapter;
    @Bind(R.id.duelRecyclerView) RecyclerView mRecyclerView;
    private List<Duel> mDuels;
    private UserService mService;


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

        mService = new RestSource().getRestAdapter().create(UserService.class);

//        initRecyclerView(mDuels);
        getRanking();
        return view;
    }

    private void getRanking() {
        mService.getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                initRecyclerView(users);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void initRecyclerView(List<User> list) {
        mAdapter = new DuelsAdapter(getActivity().getApplicationContext(), list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

}
