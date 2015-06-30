package com.yannickschuchmann.peng.model.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yannickschuchmann.peng.common.Constants;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by yannick on 30.06.15.
 */
public class RestSource {
    private RestAdapter restAdapter;

    public RestSource() {
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        Cache cache = new Cache(Constants.CACHEDIR, cacheSize);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.setCache(cache);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSSZ")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new CustomErrorHandler())
                .setEndpoint(Constants.API_URL)
                .setConverter(new GsonConverter(gson))
//                .setRequestInterceptor(new SessionRequestInterceptor())
                .build();
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }
}
