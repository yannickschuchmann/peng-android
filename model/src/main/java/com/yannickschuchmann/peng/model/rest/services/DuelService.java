package com.yannickschuchmann.peng.model.rest.services;

import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;
import retrofit.Callback;
import retrofit.http.*;

/**
 * Created by yannick on 30.06.15.
 */
public interface DuelService {
    @GET("/api/v1/duels/{id}")
    void getDuel(@Path("id") int id, Callback<Duel> cb);

    @FormUrlEncoded
    @POST("/api/v1/duels")
    void postDuel(@Field("user_id") int userId, @Field("opponent_id") int opponent_id, Callback<Duel> cb);

    @FormUrlEncoded
    @POST("/api/v1/duels/random")
    void postRandomDuel(@Field("user_id") int userId, Callback<Duel> cb);
}
