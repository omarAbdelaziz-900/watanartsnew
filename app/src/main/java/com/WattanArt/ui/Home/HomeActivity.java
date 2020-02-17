package com.WattanArt.ui.Home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.WattanArt.BuildConfig;
import com.WattanArt.R;
import com.WattanArt.UpdateApp.HttpHandler;
import com.WattanArt.Utils.BottomNavigationViewHelper;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.ui.ActivityFeed.ActivityFeedFragment;
import com.WattanArt.ui.Category.CategoryActivity;
import com.WattanArt.ui.HomeFragment.HomeFragment;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryFragment;
import com.WattanArt.ui.PublicShipping.PublicBitmapsModel;
import com.WattanArt.ui.PublicShipping.PublicShippingActivity;
import com.WattanArt.ui.Setting.SettingFragment;
import com.WattanArt.ui.Shipping.ShippingActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.WattanArt.ui.myAccount.MyAccountFragment;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

import static com.WattanArt.ui.PublicShipping.PublicShippingActivity.publicBitmapsModels;

public class HomeActivity extends BaseActivity {

//    private static  String url = "http://androidmkab.com/paperVersion.json";
    private static  String url = Constants.BASE_URL+"api/SettingApi/GetVersions";
    String VersionUpdate;

    UserData userData;
    String userId;
    BottomNavigationView navigation;


    @BindView(R.id.container)
    public RelativeLayout container;

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
                            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_holder);
                            if (f instanceof HomeFragment) {

                            }else {
                                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                finish();
                            }
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

                        if (isNetworkConnected()) {
//                            openFragment(OrderHistoryFragment.class, null);

                            if (publicBitmapsModels!=null) {
                                if (!publicBitmapsModels.isEmpty()) {
                                    Intent intent = new Intent(HomeActivity.this, PublicShippingActivity.class);
                                    startActivity(intent);
                                    Log.e("ddddddd22",publicBitmapsModels.size()+"");
                                    return true;
                                }else {
                                    showMessage(R.string.no_cart);
                                    return false;
                                }
                            }else {
                                Log.e("ddddddd",publicBitmapsModels.size()+"");
                                showMessage(R.string.no_cart);
                                return false;
                            }
                        } else {
                            showMessage(R.string.error_no_internet_connection);
                        }

//                    if (!userId.isEmpty()) {
//                        if (isNetworkConnected()) {
//                            openFragment(OrderHistoryFragment.class, null);
//                        } else {
//                            showMessage(R.string.error_no_internet_connection);
//                        }
//                    } else {
//                        showMessage(R.string.login_before);
////                        navigation.setSelectedItemId(R.id.navigation_history);
//                    }

                    return true;
                case R.id.navigation_setting:
                    openFragment(SettingFragment.class, null);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder , new SettingFragment()).commit();

                    return true;

//                    case R.id.navigation_account:
//                    openFragment(MyAccountFragment.class, null);
////                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder , new SettingFragment()).commit();
//
//                    return true;
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

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
        } else {
            openFragment(HomeFragment.class, null);
        }
//            openFragment(ActivityFeedFragment.class, null);

        new VersionCheck().execute();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        switch (requestCode) {
//
//            case REQ_CODE_VERSION_UPDATE:
//                if (resultCode != RESULT_OK) { //RESULT_OK / RESULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
//                    Log.d("Update flow failed! Result code: " , resultCode+"");
//                    // If the update is cancelled or fails,
//                    // you can request to start the update again.
//                    unregisterInstallStateUpdListener();
//                }
//
//                break;
//
//            case PhotoPicker.REQUEST_CODE:
//                if (data != null) {
//                    ArrayList<String> photos =
//                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                    Intent intent = new Intent(HomeActivity.this, ShippingActivity.class);
//                    intent.putStringArrayListExtra("photos_list", photos);
//                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                    startActivity(intent);
//                }
//                break;
//        }
//
        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
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

        if (fragment instanceof SettingFragment || fragment instanceof OrderHistoryFragment | fragment instanceof MyAccountFragment) {
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



    public class VersionCheck extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject jsonObject = jsonObj.getJSONObject("result");
                    JSONArray version = jsonObject.getJSONArray("Version");
                    for (int i = 0; i < version.length(); i++){

                        JSONObject v = version.getJSONObject(i);

                        VersionUpdate = v.getString("version");
                        Log.e("VersionUpdateeed",VersionUpdate);
                        VersionUpdate="1.20";
                    }
                }catch (final JSONException e) {
                    // Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
                        }
                    });
                }
            } else {
                //Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
                    }
                });

            }

//            VersionUpdate="17";
//            VersionUpdate="1.20";
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            String VersionName = BuildConfig.VERSION_NAME;
            Log.e("VersionName",VersionName);

            if (VersionUpdate!=null) {
                if (VersionUpdate.equals(VersionName)) {

                    //Do Nothing
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Our App got Update");
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setCancelable(false);
                    builder.setMessage("New version available, select update to update our app")
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    final String appName = getPackageName();

                                    try {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.wattanart")));
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
                                        Log.e("aaaaaaaa", "aaaaaaaa");
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
                                        Log.e("bbbbbbbb", "bbbbbbbbb");
                                    }

                                    finish();

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        builder.setCancelable(true);
                            dialog.cancel();

                        }
                    });
//                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() { // define the 'Cancel' button
//                    public void onClick(DialogInterface dialog, int which) {
//                        builder.ca
//                    }
//                });

                    AlertDialog alert = builder.create();
                    alert.show();


                }

            }
        }
    }
}
