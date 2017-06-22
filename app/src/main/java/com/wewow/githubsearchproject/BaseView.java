package com.wewow.githubsearchproject;

/**
 * The BaseView defines the abstract
 * method that every View in the app
 * should implement.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

}
