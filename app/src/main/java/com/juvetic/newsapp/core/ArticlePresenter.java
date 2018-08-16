package com.juvetic.newsapp.core;

import android.content.Context;

import com.juvetic.newsapp.model.Article;

import java.util.List;

public class ArticlePresenter implements GetArticleDataContract.Presenter, GetArticleDataContract.onGetDataListener {

    private GetArticleDataContract.View mGetDataView;
    private ArticleInteractor mArticleInteractor;

    public ArticlePresenter(GetArticleDataContract.View mGetDataView) {
        this.mGetDataView = mGetDataView;
        mArticleInteractor = new ArticleInteractor(this);
    }

    @Override
    public void getDataFromURL(Context context, String url, String sourceId) {
        mArticleInteractor.initRetrofitCall(context, url, sourceId);
    }

    @Override
    public void onSuccess(String message, List<Article> list) {
        mGetDataView.onGetDataSuccess(message, list);
    }

    @Override
    public void onFailure(String message) {
        mGetDataView.onGetDataFailure(message);
    }
}
