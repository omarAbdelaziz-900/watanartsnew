
package com.WattanArt.ui.base;


/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MvpPresenter<T extends MvpView> {

    void onAttach(T mvpView);

    void onDetach();

//    void setUserAsLoggedOut();
}
