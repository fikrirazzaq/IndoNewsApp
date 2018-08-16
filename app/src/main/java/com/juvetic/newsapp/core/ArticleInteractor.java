package com.juvetic.newsapp.core;

import android.content.Context;
import android.util.Log;

import com.juvetic.newsapp.model.Article;
import com.juvetic.newsapp.model.ArticleResponse;
import com.juvetic.newsapp.model.RecentArticle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ArticleInteractor implements GetArticleDataContract.Interactor {

    private GetArticleDataContract.onGetDataListener mOnGetDataListener;
    List<String> articleData = new ArrayList<>();
    List<Article> articlesList = new ArrayList<>();

    public ArticleInteractor(GetArticleDataContract.onGetDataListener mOnGetDataListener) {
        this.mOnGetDataListener = mOnGetDataListener;
    }

    @Override
    public void initRetrofitCall(Context context, String url, String sourceId) {
        Retrofit retrofit = NewsApi.getClient();

        ArticleResponse response = retrofit.create(ArticleResponse.class);

        Call<RecentArticle> call = response.getArticle(sourceId);
        call.enqueue(new Callback<RecentArticle>() {
            @Override
            public void onResponse(Call<RecentArticle> call, Response<RecentArticle> response) {
                RecentArticle jsonResponse = response.body();
                articlesList = jsonResponse.getArticles();
                for(int i=0; i<articlesList.size(); i++) {
                    articleData.add(articlesList.get(i).getTitle());
                }
                mOnGetDataListener.onSuccess("List Size: " + articleData.size(), articlesList);
            }

            @Override
            public void onFailure(Call<RecentArticle> call, Throwable t) {
                mOnGetDataListener.onFailure(t.getMessage());
            }
        });
    }
}
