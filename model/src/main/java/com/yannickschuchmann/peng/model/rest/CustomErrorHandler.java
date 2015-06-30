package com.yannickschuchmann.peng.model.rest;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by yannick on 30.06.15.
 */
public class CustomErrorHandler implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError cause) {
        String errorDescription = "";
        final  String TAG = "RETROFIT ERROR:";

        if (cause.isNetworkError()) {
            errorDescription = "Keine Internetverbindung!";
        } else {
            if (cause.getResponse() == null) {
                errorDescription = "Keine Antwort vom Server";
            }
        }

        return new Exception(errorDescription);
    }
}
