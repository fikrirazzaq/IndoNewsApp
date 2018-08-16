package com.juvetic.newsapp.core;

import android.content.Context;

import com.juvetic.newsapp.model.Sources;

import java.util.List;

public interface GetSourceDataContract {

    interface View{
        void onGetDataSuccess(String message, List<Sources> list);
        void onGetDataFailure(String message);
    }

    interface Presenter{
        void getDataFromURL(Context context, String url);
    }

    interface Interactor{
        void initRetrofitCall(Context context, String url);

    }

    interface onGetDataListener{
        void onSuccess(String message, List<Sources> list);
        void onFailure(String message);
    }
}