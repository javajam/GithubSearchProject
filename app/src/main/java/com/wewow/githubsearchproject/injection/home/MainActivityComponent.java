package com.wewow.githubsearchproject.injection.home;

import com.wewow.githubsearchproject.home.MainActivity;

import dagger.Component;

/**
 * Created by eleroy on 6/21/2017.
 */

@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
