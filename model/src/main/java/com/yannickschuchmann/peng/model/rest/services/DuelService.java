package com.yannickschuchmann.peng.model.rest.services;

import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by yannick on 30.06.15.
 */
public interface DuelService {
    // TODO change to POST
    @GET("/api/v1/duels/new/random")
    void postRandomDuel(@Path("id") int id, Callback<Duel> cb);

    // TODO change to POST
    @GET("/api/v1/duels/new")
    void postNewDuel(@Path("id") int id, Callback<Duel> cb);


    @GET("/api/v1/duels/{id}")
    void getDuel(@Path("id") int id, Callback<Duel> cb);


}
