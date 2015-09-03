package com.yannickschuchmann.peng.app.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.adapters.UsersAdapter;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;


public class UsersFragment extends Fragment {
    protected UsersAdapter mAdapter;
    @Bind(R.id.userRecyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.progress_overlay)
    FrameLayout overlay;
    private UserService mService;


    public static UsersFragment newInstance(Bundle bundle) {
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);

        mService = new RestSource().getRestAdapter().create(UserService.class);

        Bundle bundle = getArguments();
        if (getArguments().getString("type") == "ranking") {
            getRanking();
        } else {
            // friends
            getRanking();
        }

        return view;
    }

    private void initRecyclerView(List<User> list) {
        mAdapter = new UsersAdapter(getActivity().getApplicationContext(), list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getRanking() {
        overlay.setVisibility(View.VISIBLE);
        mService.getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                initRecyclerView(users);
                overlay.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(
                        getActivity(),
                        "ups, da ist was schief gegangen",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

}
