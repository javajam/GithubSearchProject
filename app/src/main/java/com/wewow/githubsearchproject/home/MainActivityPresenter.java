package com.wewow.githubsearchproject.home;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wewow.githubsearchproject.source.remote.entries.Item;
import com.wewow.githubsearchproject.source.remote.entries.Results;
import com.wewow.githubsearchproject.util.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by eleroy on 6/21/2017.
 */
public class MainActivityPresenter implements MainActivityContract.Presenter {

    private static final String TAG = "MainActivityPresenter";

    private Handler mHandler;

    private Item user;

    MainActivityContract.View view;

    @Override
    public void addView(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void processUser(String userName) {

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                if (user == null) {
                    Log.i(TAG, "No user found!");
                    return;
                }
                view.updateView(user);
            }
        };

        processResults(userName);
    }

    private void processResults(String userName) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL + Constants.SEARCH_USERS).newBuilder();
        urlBuilder.addQueryParameter("q", userName);
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
                Results results = gson.fromJson(response.body().charStream(), Results.class);
                if (results == null || results.getItems().size() == 0) {
                    return;
                }
                user = results.getItems().get(0);
                mHandler.sendEmptyMessage(0);
            }
        });
    }
}
