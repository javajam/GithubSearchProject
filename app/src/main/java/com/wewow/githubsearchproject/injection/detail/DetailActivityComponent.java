package com.wewow.githubsearchproject.injection.detail;

import com.wewow.githubsearchproject.detail.DetailActivity;

import dagger.Component;

/**
 * Created by eleroy on 6/22/2017.
 */

@Component(modules = DetailActivityModule.class)
public interface DetailActivityComponent {
    void inject(DetailActivity detailActivity);
}
