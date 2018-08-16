package com.juvetic.newsapp.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleResponse {

    @GET("everything?apiKey=a8449740cee540a9b7bee1374eb306f1")
    Call<RecentArticle> getArticle(@Query("sources") String sources);
}
