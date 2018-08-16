package com.juvetic.newsapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juvetic.newsapp.R;
import com.juvetic.newsapp.adapter.AdapterArticle;
import com.juvetic.newsapp.adapter.AdapterSource;
import com.juvetic.newsapp.core.ArticlePresenter;
import com.juvetic.newsapp.core.GetArticleDataContract;
import com.juvetic.newsapp.core.GetSourceDataContract;
import com.juvetic.newsapp.core.SourcePresenter;
import com.juvetic.newsapp.model.Article;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListArticleActivity extends AppCompatActivity implements GetArticleDataContract.View {

    private static final String TAG = "ListArticleActivity";
    private ArticlePresenter mArticlePresenter;

    @BindView(R.id.recycler_article) RecyclerView recyclerView;
    Toolbar toolbarListArticle;
    LinearLayoutManager linearLayoutManager;

    List<Article> articles;
    AdapterArticle adapterArticle;
    String currentQuery;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_article);

        initControls();
        setupSearch();
    }

    public void initControls() {
        ButterKnife.bind(this);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("source_name"));

        toolbarListArticle = findViewById(R.id.toolbar_list_article);
        setSupportActionBar(toolbarListArticle);
        if(getSupportActionBar() != null){
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mArticlePresenter = new ArticlePresenter(this);
        mArticlePresenter.getDataFromURL(getApplicationContext(), "", intent.getStringExtra("source_id"));
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupSearch() {
        searchView = findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processQuery(newText);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setQuery(currentQuery, false);
            }

            @Override
            public void onSearchViewClosed() {
                currentQuery = "";
                adapterArticle.setArticles(articles);
            }
        });
    }

    private void processQuery(String query) {
        currentQuery = query;
        List<Article> result = new ArrayList<>();

        // case insensitive search
        for (Article article : articles) {
            if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
                result.add(article);
            }
        }

        adapterArticle.setArticles(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetDataSuccess(String message, List<Article> list) {
        articles = list;

        adapterArticle = new AdapterArticle(getApplicationContext(), list);
        recyclerView.setAdapter(adapterArticle);
    }

    @Override
    public void onGetDataFailure(String message) {
        Log.d(TAG, "onGetDataFailure: " + message);
    }
}
