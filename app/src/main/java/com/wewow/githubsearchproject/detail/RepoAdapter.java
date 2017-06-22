package com.wewow.githubsearchproject.detail;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.wewow.githubsearchproject.R;
import com.wewow.githubsearchproject.source.remote.entries.Repo;

import java.util.List;

/**
 * Created by eleroy on 6/22/2017.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.MyViewHolder> {

    private List<Repo> mRepos;

    public RepoAdapter(List<Repo> repos) {
        this.mRepos = repos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_v, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.myText.setText(mRepos.get(position).getFullName());
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cv;
        public TextView myText;

        public MyViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            myText = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
