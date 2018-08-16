package com.juvetic.newsapp.core;

import android.content.Context;
import android.util.Log;

import com.juvetic.newsapp.model.EnSourceResponse;
import com.juvetic.newsapp.model.Source;
import com.juvetic.newsapp.model.Sources;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SourceInteractor implements GetSourceDataContract.Interactor {

    private GetSourceDataContract.onGetDataListener mOnGetDataListener;
    List<String> sourceData = new ArrayList<>();
    List<Sources> sourcesList = new ArrayList<>();

    public SourceInteractor(GetSourceDataContract.onGetDataListener mOnGetDataListener) {
        this.mOnGetDataListener = mOnGetDataListener;
    }

    @Override
    public void initRetrofitCall(Context context, String url) {

        Retrofit retrofit = NewsApi.getClient();

        EnSourceResponse response = retrofit.create(EnSourceResponse.class);
        Call<Source> call = response.getSource();
        call.enqueue(new Callback<Source>() {
            @Override
            public void onResponse(Call<Source> call, Response<Source> response) {
                Source jsonResponse = response.body();
                sourcesList = jsonResponse.getSources();
                for(int i=0;i<sourcesList.size();i++){
                    sourceData.add(sourcesList.get(i).getName());
                }
                mOnGetDataListener.onSuccess("List Size: " + sourceData.size(), sourcesList);
            }

            @Override
            public void onFailure(Call<Source> call, Throwable t) {
                mOnGetDataListener.onFailure(t.getMessage());
            }
        });


    }


}
