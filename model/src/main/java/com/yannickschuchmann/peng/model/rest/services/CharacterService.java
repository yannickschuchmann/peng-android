package com.yannickschuchmann.peng.model.rest.services;

import com.yannickschuchmann.peng.model.entities.*;
import com.yannickschuchmann.peng.model.entities.Character;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

/**
 * Created by yannick on 30.06.15.
 */
public interface CharacterService {
    @GET("/api/v1/characters")
    void getCharacters(Callback<List<Character>> cb);
}
