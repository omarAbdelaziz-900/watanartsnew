package com.WattanArt.ui.Home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.WattanArt.R;
import com.WattanArt.Utils.BottomNavigationViewHelper;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.ui.HomeFragment.HomeFragment;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryFragment;
import com.WattanArt.ui.Setting.SettingFragment;
import com.WattanArt.ui.Shipping.ShippingActivity;
import com.WattanArt.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

public class HomeActivity extends BaseActivity {
    UserData userData;
    String userId;
    BottomNavigationView navigation;


    @BindView(R.id.toolbar_image_view)
    public ImageView mToolbarBackImageView;

    @BindView(R.id.toolbar_tv_title)
    public CustomeTextViewBold mToolbarTitleTextView;

    boolean isMeshcanvasSelected = false;

    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (isMeshcanvasSelected) {
                        isMeshcanvasSelected = false;
                        pickFromGallery();
                    } else {
                        if (isNetworkConnected()) {
                            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            finish();
                        } else {
                            showMessage(getString(R.string.error_no_internet_connection));

                        }
                    }
                    return true;
                case R.id.navigation_meshcanvas:

                    isMeshcanvasSelected = true;
                    pickFromGallery();
//                    navigation.setSelectedItemId(R.id.navigation_home);
//
////                    try {
////                        navigation.getMenu().getItem(0).setChecked(true);
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }

                    return false;

                case R.id.navigation_history:
                    userId = userData.getUserID(HomeActivity.this);
                    if (!userId.isEmpty()) {
                        if (isNetworkConnected()) {
                            openFragment(OrderHistoryFragment.class, null);
                        } else {
                            showMessage(R.string.error_no_internet_connection);
                        }
                    } else {
                        showMessage(R.string.login_before);
//                        navigation.setSelectedItemId(R.id.navigation_history);
                    }


                    return true;
                case R.id.navigation_setting:
                    openFragment(SettingFragment.class, null);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder , new SettingFragment()).commit();

                    return true;
            }
            return false;
        }
    };


    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery() {

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPreviewEnabled(false).setShowCamera(false)
                    .start(HomeActivity.this);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
//        if (isMeshcanvasSelected) {
//            isMeshcanvasSelected = false;
//            if (getSupportFragmentManager().findFragmentById(R.id.fragment_holder) instanceof HomeFragment)
//                if (navigation != null) {
//                    navigation.setSelectedItemId(R.id.navigation_home);
//                }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
//        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mToolbarBackImageView.setVisibility(View.GONE);




//        Log.e("ccccccccccc",UtilitiesManager.getDeviceCountryCode(this));
//        mToolbarBackImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        navigation = findViewById(R.id.navigation);
        userData = new UserData();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        if (getIntent().hasExtra("From")) {
            navigation.setSelectedItemId(R.id.navigation_history);
        } else
            openFragment(HomeFragment.class, null);

    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

//        for (Fragment fragment  : getSupportFragmentManager().getFragments()){
//            if (fragment!=null){
//                fragment.onActivityResult(requestCode, resultCode ,data);
//            }
//        }

        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

//                    ArrayList<ImageModel> imageModels = new ArrayList<>();
//                    for (String path : photos){
//                        imageModels.add(new ImageModel(path));
//                    }
                    Intent intent = new Intent(HomeActivity.this, ShippingActivity.class);
                    intent.putStringArrayListExtra("photos_list", photos);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_holder);

        if (fragment instanceof SettingFragment || fragment instanceof OrderHistoryFragment) {
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
//            openFragment(HomeFragment.class,null);
        } else {
            finish();

        }
    }

    public void setToolbarTitle(String toolbarTitle) {

        getSupportActionBar().setTitle("");
        mToolbarTitleTextView.setText(toolbarTitle);

    }

    public void visibileToolbarIconBack() {
//        mToolbarBackImageView.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFragment(Class fragmentClass, Object myObject) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_holder, fragment).commit();

    }
}
