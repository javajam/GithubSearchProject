package com.wewow.githubsearchproject.detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wewow.githubsearchproject.R;
import com.wewow.githubsearchproject.injection.detail.DaggerDetailActivityComponent;
import com.wewow.githubsearchproject.source.remote.entries.Item;
import com.wewow.githubsearchproject.source.remote.entries.Repo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wewow.githubsearchproject.util.Constants.BROADCAST_NAME;
import static com.wewow.githubsearchproject.util.Constants.REPO_URL;

public class DetailActivity extends AppCompatActivity implements DetailActivityContract.View {

    private static final String TAG = "DetailActivity";

    private String repoUrl;

    IntentFilter mIntentFilter;
    BroadcastReceiver mBroadcastReceiver;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    List<Repo> repos = new ArrayList<>();

    @Inject
    DetailActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        DaggerDetailActivityComponent.create().inject(this);

        mPresenter.addView(this);

        repoUrl = getIntent().getStringExtra(REPO_URL);

        mPresenter.processUser(repoUrl);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<Repo> repos = intent.getParcelableArrayListExtra("repos");
                updateView(repos);
            }
        };
    }

    @Override
    public void updateView(List<Repo> repos) {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);

        RepoAdapter adapter = new RepoAdapter(repos);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIntentFilter = new IntentFilter(BROADCAST_NAME);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    public void processByHandlers(View view) {
        mPresenter.processWithHandler(repoUrl);
    }

    public void processByBroadRec(View view) {
        mPresenter.processWithBroadRec(LocalBroadcastManager.getInstance(this), repoUrl);
    }
}
