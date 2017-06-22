package com.wewow.githubsearchproject.injection.home;

import com.wewow.githubsearchproject.home.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eleroy on 6/21/2017.
 */

@Module
public class MainActivityModule {

    @Provides
    public MainActivityPresenter provideMainActivityPresenter() {
        return new MainActivityPresenter();
    }
}
