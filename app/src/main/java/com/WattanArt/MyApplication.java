package com.WattanArt;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.WattanArt.Dagger.component.ApplicationComponent;
import com.WattanArt.Dagger.component.DaggerApplicationComponent;
import com.WattanArt.Dagger.module.ApplicationModule;
import com.crashlytics.android.Crashlytics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;


public class MyApplication extends MultiDexApplication {
    private ApplicationComponent mApplicationComponent;

    static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        printHashKey();
        Fabric.with(this, new Crashlytics());
//        Paper.init(context);

//        StrictMode.ThreadPolicy policy = new
//                StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();


        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();


        mApplicationComponent.inject(this);


//        Twitter.initialize(this);
//        TwitterConfig config = new TwitterConfig.Builder(this)
//                .logger(new DefaultLogger(Log.DEBUG))
//                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.twitter_app_id),
//                        getString(R.string.twitter_app_secret)))
//                .debug(true)
//                .build();
//        Twitter.initialize(config);

        context = this.getApplicationContext();


    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return context;
    }

    public void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //life saver
                //https://stackoverflow.com/questions/44355452/google-play-app-signing-key-hash/44448437#44448437
                byte[] sha={
                        (byte)0xCF,(byte)0x2C,(byte)0xC3,(byte)0x04,(byte)0xA7,(byte)0xC5,(byte)0x76,
                        (byte)0x7C,(byte)0x9D,(byte)0x08,(byte)0x25,(byte)0x6D,(byte)0x9E,(byte)0x17,
                        (byte)0x25,(byte)0xF5,(byte)0x79,(byte)0xBF,(byte)0xF9,(byte)0x3F

                };


//                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                Log.e("KeyHash:", Base64.encodeToString(sha, Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

}
