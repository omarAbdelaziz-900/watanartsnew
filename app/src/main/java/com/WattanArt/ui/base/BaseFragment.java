package com.WattanArt.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.Utils.ViewHelper;


public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseActivity mActivity;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLoadingFragment() {
        hideLoadingFragment();
        ViewHelper.showProgressFragment(mActivity);
    }

    @Override
    public void hideLoadingFragment() {
        ViewHelper.hideProgressFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        ViewHelper.showProgressDialog(getActivity());
    }

    @Override
    public void hideLoading() {
        ViewHelper.hideProgressDialog();
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError( int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage( int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity.openActivityOnTokenExpire();
        }
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}

//import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//import android.widget.ListView;
//
//import com.WattanArt.R;
//import com.WattanArt.logging.Logger;
//import com.WattanArt.utilities.UtilitiesManager;
//import com.WattanArt.utilities.ViewHelper;
//import com.WattanArt.MyApplication;
//
///**
// * Created by Android Team on 1/14/2018.
// */
//
//public abstract class BaseFragment extends Fragment implements BaseActivityPresenterImp.ViewsListener {
//
//    BaseActivityPresenter mainPresenter;
//    private Toolbar mToolbar;
//    private final String TAG = getClass().getSimpleName();
//
//    MyApplication baseApplication;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mainPresenter = new BaseActivityPresenterImp();
//        mainPresenter.setView(this);
//
//
//        setupToolbar(((BaseActivity) getActivity()).getToolbar());
//    }
//
//    @Override
//    public void setupToolbar(Toolbar toolbar) {
//        toolbar.setTitle("test");
//    }
//
//    public BaseActivity getBaseActivity(){
//        if (getActivity() instanceof BaseActivity){
//            return (BaseActivity) getActivity();
//        }else {
//            throw new IllegalArgumentException("Holder Activity Must be instance of BaseActivity");
//        }
//    }
//
//
//    /**
//     * We initialize any views and Objects here .
//     * <p/>
//     * we pass the viewInterface to the Presenter to deal with the events.
//     */
//
//    @Override
//    public void onStart() {
//        mainPresenter.onStart();
//        super.onStart();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        baseApplication = (MyApplication) getActivity().getApplicationContext();
//    }
//
//    @Override
//    public void onDestroy() {
//        mainPresenter.onDestroy();
//        super.onDestroy();
//    }
//
//    @Override
//    public void showProgressDialog() {
//        ViewHelper.showProgressDialog(getBaseActivity());
//    }
//
//    @Override
//    public void hideProgressDialog() {
//        ViewHelper.hideProgressDialog();
//    }
//
//
//
//    @Override
//    public void setToolbarTitle(int titleResId) {
//        if (mToolbar != null)
//            mToolbar.setTitle(titleResId);
//    }
//
//    @Override
//    public Toolbar getToolbar() {
//        return mToolbar;
//    }
//
//    @Override
//    public void hideKeyboard() {
//        ViewHelper.hideKeyboard(getActivity());
//
//    }
//
//    @Override
//    public void destroy() {
//        mToolbar = null;
//    }
//
//
//    @Override
//    public void reSetToolbarColors() {
//        if (mToolbar != null) {
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.primary_color));
//        }
//    }
//
//
//    @Override
//    public void displayError(String error) {
//        ViewHelper.displayError(getActivity(), error);
//
//    }
//
//    @Override
//    public boolean checkConnectivity(Context context) {
//        if (UtilitiesManager.canConnect(context))
//            return true;
//        else {
//            ViewHelper.showMessage(context, R.string.error_no_internet_connection);
//            return false;
//        }
//    }
//
//    public void adjustListViewDividerColor(ListView listView, int color) {
//        ColorDrawable listViewDivider = new ColorDrawable(color);
//        listView.setDivider(listViewDivider);
//        listView.setDividerHeight(1);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            getActivity().onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    @Override
//    public boolean isTablet() {
//        return (getResources().getConfiguration().screenLayout
//                & Configuration.SCREENLAYOUT_SIZE_MASK)
//                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
//    }
//
//    @Override
//    public void lockScreenOrientation() {
//        if (isTablet()) {
//            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//            Logger.info(TAG, "Locking Orientation To Tablet");
//            return;
//        } else getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//        Logger.info(TAG, "Locking Orientation To Phone");
//
//    }
//
//    /**
//     * Returns application node for later usage across the entire application
//     *
//     * @return MyApplication
//     */
//    @Override
//    public MyApplication getATCApplication() {
//        return (MyApplication) getActivity().getApplicationContext();
//    }
//
//    @Override
//    public void addFragmentToActivity(Fragment fragment) {
//
//    }
//}
