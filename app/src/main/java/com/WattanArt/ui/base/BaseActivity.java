package com.WattanArt.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.Dagger.component.DaggerActivityComponent;
import com.WattanArt.Dagger.module.ActivityModule;
import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.ViewHelper;



public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback {


    private ActivityComponent mActivityComponent;

    AppCompatActivity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MyApplication) getApplication()).getComponent())
                .build();

//        setUpActivityOrFragmentRequirment();

    }

    public AppCompatActivity getMyContext() {
        return context;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M &&
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        ViewHelper.showProgressDialog(this);
    }


    @Override
    public void showLoadingFragment() {
        hideLoadingFragment();
        ViewHelper.showProgressFragment(this);
    }

    @Override
    public void hideLoadingFragment() {
        ViewHelper.hideProgressFragment();
    }

    @Override
    public void hideLoading() {
        ViewHelper.hideProgressDialog();
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.ms_white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void onError( int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.some_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage( int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return UtilitiesManager.canConnect(this);
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
//        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

//    @Override
//    public void addFragmentToActivity(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction().
//                replace(R.id.fragment_container, fragment)
////               .addToBackStack(fragment.getTag())
//                .commit();
//    }

    //TODO: SETUP YOUR VIEW REQUIRMENT OR ADAPTERS ...ETC
    protected abstract void setUpActivityOrFragmentRequirment();
}

