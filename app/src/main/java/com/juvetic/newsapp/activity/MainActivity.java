package com.juvetic.newsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.juvetic.newsapp.R;
import com.juvetic.newsapp.adapter.AdapterSource;
import com.juvetic.newsapp.core.GetSourceDataContract;
import com.juvetic.newsapp.core.SourcePresenter;
import com.juvetic.newsapp.model.Sources;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GetSourceDataContract.View {

    private static final String TAG = "MainActivity";
    private SourcePresenter mSourcePresenter;
    @BindView(R.id.recycler) RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterSource adapterSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
    }

    public void initControls() {
        ButterKnife.bind(this);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.ic_logo);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        mSourcePresenter = new SourcePresenter(this);
        mSourcePresenter.getDataFromURL(getApplicationContext(), "");
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onGetDataSuccess(String message, List<Sources> list) {
        adapterSource = new AdapterSource(getApplicationContext(), list);
        recyclerView.setAdapter(adapterSource);
    }

    @Override
    public void onGetDataFailure(String message) {
        Log.d(TAG, "onGetDataFailure: " + message);
    }
}
