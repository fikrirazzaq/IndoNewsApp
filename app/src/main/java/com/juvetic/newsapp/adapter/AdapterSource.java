package com.juvetic.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.juvetic.newsapp.R;
import com.juvetic.newsapp.activity.ListArticleActivity;
import com.juvetic.newsapp.model.Sources;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Jhordan on 13/10/15.
 */
public class AdapterSource extends RecyclerView.Adapter<AdapterSource.ViewHolder> {

    private Context context;
    private List<Sources> sources = new ArrayList<>();

    public AdapterSource(Context context, List<Sources> sources) {
        this.context = context;
        this.sources = sources;
    }

    @Override
    public AdapterSource.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_source,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterSource.ViewHolder holder, int position) {
        holder.tvSourceName.setText(sources.get(position).getName());
        holder.sourceId = sources.get(position).getId();
        holder.sourceName = sources.get(position).getName();
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_source_name) TextView tvSourceName;
        String sourceName, sourceId;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ListArticleActivity.class);
                    intent.putExtra("source_id", sourceId);
                    intent.putExtra("source_name", sourceName);
                    context.startActivity(intent);
                }
            });
        }


    }
}