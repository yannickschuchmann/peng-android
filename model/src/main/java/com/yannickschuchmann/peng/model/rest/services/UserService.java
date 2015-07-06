package com.yannickschuchmann.peng.model.rest.services;

import com.yannickschuchmann.peng.model.entities.User;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

/**
 * Created by yannick on 30.06.15.
 */
public interface UserService {
    @GET("/api/v1/users/{id}")
    void getUser(@Path("id") int id, Callback<User> cb);

    @GET("/api/v1/users")
    void getUsers(Callback<List<User>> cb);

    @PUT("/api/v1/users/{id}")
    void updateUser(@Path("id") int id, @Body User user, Callback<User> cb);

    @FormUrlEncoded
    @POST("/api/v1/users/check_credentials")
    void checkCredentials(@Field("X-Auth-Service-Provider") String serviceProvider,
                          @Field("X-Verify-Credentials-Authorization") String authorization, Callback<User> cb);
}
