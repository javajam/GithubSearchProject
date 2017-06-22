package com.wewow.githubsearchproject.injection.detail;

import com.wewow.githubsearchproject.detail.DetailActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eleroy on 6/22/2017.
 */

@Module
public class DetailActivityModule {

    @Provides
    public DetailActivityPresenter provideMainActivityPresenter() {
        return new DetailActivityPresenter();
    }
}
