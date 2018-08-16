package com.juvetic.newsapp.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EnSourceResponse {

    @GET("sources?language=en&apiKey=a8449740cee540a9b7bee1374eb306f1")
    Call<Source> getSource();
}
