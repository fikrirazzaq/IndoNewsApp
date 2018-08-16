package com.juvetic.newsapp.core;

import android.content.Context;

import com.juvetic.newsapp.model.Sources;

import java.util.List;

public class SourcePresenter implements GetSourceDataContract.Presenter, GetSourceDataContract.onGetDataListener {

    private GetSourceDataContract.View mGetDataView;
    private SourceInteractor mSourceInteractor;

    public SourcePresenter(GetSourceDataContract.View mGetDataView) {
        this.mGetDataView = mGetDataView;
        mSourceInteractor = new SourceInteractor(this);
    }

    @Override
    public void getDataFromURL(Context context, String url) {
        mSourceInteractor.initRetrofitCall(context,url);
    }

    @Override
    public void onSuccess(String message, List<Sources> list) {
        mGetDataView.onGetDataSuccess(message, list);
    }

    @Override
    public void onFailure(String message) {
        mGetDataView.onGetDataFailure(message);
    }
}
