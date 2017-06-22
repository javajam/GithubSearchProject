package com.wewow.githubsearchproject.home;

import com.wewow.githubsearchproject.source.remote.entries.Item;

/**
 * Created by eleroy on 6/21/2017.
 */

public interface MainActivityContract {
    interface View {
        void updateView(Item user);
    }

    interface Presenter<V extends View> {
        void addView(V view);
        void processUser(String userName);
    }
}
