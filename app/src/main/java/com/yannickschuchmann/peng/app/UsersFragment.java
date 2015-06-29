package com.yannickschuchmann.peng.app;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.adapters.UsersAdapter;
import com.yannickschuchmann.peng.model.User;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {
    protected UsersAdapter mAdapter;
    @Bind(R.id.userRecyclerView) RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        mAdapter = new UsersAdapter(getActivity().getApplicationContext(), generateDummyList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<User> generateDummyList() {
        List<User> usersList = new ArrayList<User>();
        for (int i = 0; i < 30; i++) {
            User item = new User();
            item.setNick("Nickname #" + i);
            item.setSlogan("\"Ich gewinne immer\"");
            item.setRank(i);
            usersList.add(item);
        }
        return usersList;
    }

}
