package com.juvetic.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.juvetic.newsapp.R;
import com.juvetic.newsapp.activity.ArticleActivity;
import com.juvetic.newsapp.activity.ListArticleActivity;
import com.juvetic.newsapp.model.Article;
import com.juvetic.newsapp.model.Sources;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.ViewHolder> {

    private Context context;
    private List<Article> articles;

    public AdapterArticle(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public AdapterArticle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterArticle.ViewHolder holder, int position) {
        holder.tvArticleName.setText(articles.get(position).getTitle());
        holder.tvArticleTime.setText(articles.get(position).getPublishedAt());
        setArticleImage(articles.get(position).getUrlToImage(), holder.imgvArticleImage);
        holder.url = articles.get(position).getUrl();
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public void setArticleImage(String url, ImageView image) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.image_not_found)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_article_name) TextView tvArticleName;
        @BindView(R.id.tv_article_time) TextView tvArticleTime;
        @BindView(R.id.imgv_article_image) ImageView imgvArticleImage;
        String url;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.putExtra("url_news", url);
                    intent.putExtra("title_news", tvArticleName.getText());
                    context.startActivity(intent);
                }
            });
        }
    }
}