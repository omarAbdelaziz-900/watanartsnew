package com.WattanArt.ui.base;

import android.util.Log;

import com.WattanArt.R;
import com.WattanArt.network.AppDataManager;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = "BasePresenter";

    private final AppDataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    @Inject
    public BasePresenter(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable;
        mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
//        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public AppDataManager getDataManager() {
        return mDataManager;
    }

//    public SchedulerProvider getSchedulerProvider() {
//        return mSchedulerProvider;
//    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public void handleThrowableError(Throwable throwable) {
        if (throwable == null)
            return;

        Log.e("Throwable" , "-->"+throwable.getMessage());
        throwable.printStackTrace();



        if (throwable instanceof SocketTimeoutException)
            getMvpView().showMessage(R.string.slow_connection);
        else if (throwable.getMessage()!=null && !throwable.getMessage().isEmpty())
            getMvpView().showMessage(throwable.getMessage());
        else
            getMvpView().showMessage(R.string.some_error);

    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
