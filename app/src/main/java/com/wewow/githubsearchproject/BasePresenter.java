package com.wewow.githubsearchproject;

/**
 * This is the BasePresenter interface.
 * It defines the basic abstract methods that
 * all concrete implementations for Presenters
 * in the app would need to implement
 */

public interface BasePresenter<V extends BaseView> {

    void addView(V view);
    void removeView();

}
