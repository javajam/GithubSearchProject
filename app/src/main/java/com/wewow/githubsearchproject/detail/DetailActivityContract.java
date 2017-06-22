package com.wewow.githubsearchproject.detail;

import android.support.v4.content.LocalBroadcastManager;

import com.wewow.githubsearchproject.source.remote.entries.Item;
import com.wewow.githubsearchproject.source.remote.entries.Repo;

import java.util.List;

/**
 * Created by eleroy on 6/22/2017.
 */

public interface DetailActivityContract {
    interface View {
        void updateView(List<Repo> repos);
    }

    interface Presenter<V extends View> {
        void addView(V view);
        void processUser(String repoUrl);
        void processWithHandler(String repoUrl);
        void processWithBroadRec(LocalBroadcastManager lbm, String repoUrl);
    }
}
