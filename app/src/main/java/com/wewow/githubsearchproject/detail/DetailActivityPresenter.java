package com.wewow.githubsearchproject.detail;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wewow.githubsearchproject.source.remote.entries.Repo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wewow.githubsearchproject.util.Constants.BROADCAST_NAME;

/**
 * Created by eleroy on 6/22/2017.
 */

public class DetailActivityPresenter implements DetailActivityContract.Presenter{

    private static final String TAG = "DetailActivityPresenter";

    private Handler mHandler;

    private ArrayList<Repo> mRepos;

    DetailActivityContract.View view;

    @Override
    public void addView(DetailActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void processUser(String repoUrl) {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                if (mRepos == null) {
                    Log.i(TAG, "No repositories found!");
                    return;
                }
                view.updateView(mRepos);
            }
        };

        processResults(repoUrl);
    }

    @Override
    public void processWithHandler(String repoUrl) {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                if (mRepos == null) {
                    Log.i(TAG, "No repositories found!");
                    return;
                }
                view.updateView(mRepos);
            }
        };

        processResults(repoUrl);
    }

    @Override
    public void processWithBroadRec(LocalBroadcastManager lbm, String repoUrl) {
        processResultsThenBC(lbm, repoUrl);
    }

    private void processResults(String repoUrl) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(repoUrl).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Create new gson object
        final Gson gson = new Gson();
        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure");
            }

            // Parse response using gson deserializer
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // Process the data on the worker thread
                mRepos = gson.fromJson(response.body().charStream(), new TypeToken<List<Repo>>() {}.getType());
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    private void processResultsThenBC(final LocalBroadcastManager lbm, String repoUrl) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(repoUrl).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Create new gson object
        final Gson gson = new Gson();
        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure");
            }

            // Parse response using gson deserializer
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // Process the data on the worker thread
                mRepos = gson.fromJson(response.body().charStream(), new TypeToken<List<Repo>>() {}.getType());
                Intent intent = new Intent();
                intent.setAction(BROADCAST_NAME);
                intent.putParcelableArrayListExtra("repos", mRepos);
                lbm.sendBroadcast(intent);
            }
        });
    }
}
