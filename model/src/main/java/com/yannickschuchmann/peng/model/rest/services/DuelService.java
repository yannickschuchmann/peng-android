package com.yannickschuchmann.peng.model.rest.services;

import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;
import retrofit.Callback;
import retrofit.http.*;

/**
 * Created by yannick on 30.06.15.
 */
public interface DuelService {
    @FormUrlEncoded
    @POST("/api/v1/duels")
    void postDuel(@Field("user_id") int userId, @Field("opponent_id") int opponent_id, @Field("bet") String bet, Callback<Duel> cb);

    @FormUrlEncoded
    @POST("/api/v1/duels/random")
    void postRandomDuel(@Field("user_id") int userId, Callback<Duel> cb);

    @GET("/api/v1/duels/{id}")
    void getDuel(@Path("id") int id, @Query("user_id") int userId, Callback<Duel> cb);

    @FormUrlEncoded
    @POST("/api/v1/duels/{id}")
    void postAction(@Path("id") int id,
                    @Field("user_id") int userId,
                    @Field("opponent_id") int opponentId,
                    @Field("action_type") String actionType,
                    Callback<Duel> cb);
}
