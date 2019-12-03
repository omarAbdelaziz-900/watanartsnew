package com.WattanArt.ui.base;




/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment. BaseMvpView
 */
public interface MvpView {

    void showLoading();

    void showLoadingFragment();
    void hideLoadingFragment();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError( int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage( int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

//    void addFragmentToActivity(Fragment fragment);
}
