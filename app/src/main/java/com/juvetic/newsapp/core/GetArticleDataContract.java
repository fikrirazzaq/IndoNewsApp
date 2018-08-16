package com.juvetic.newsapp.core;

import android.content.Context;

import com.juvetic.newsapp.model.Article;
import com.juvetic.newsapp.model.Sources;

import java.util.List;

public interface GetArticleDataContract {

    interface View{
        void onGetDataSuccess(String message, List<Article> list);
        void onGetDataFailure(String message);
    }

    interface Presenter{
        void getDataFromURL(Context context, String url, String sourceId);
    }

    interface Interactor{
        void initRetrofitCall(Context context, String url, String sourceId);

    }

    interface onGetDataListener{
        void onSuccess(String message, List<Article> list);
        void onFailure(String message);
    }
}
