package com.WattanArt.ui.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.WattanArt.R;
import com.WattanArt.Services.firebase.FirebaseIDService;
import com.WattanArt.TestActivity;
import com.WattanArt.Utils.Localization;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.Login.LoginActivity;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class SplashActivity extends Activity {
    ImageView imgVSplash;
    UserData userData;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        AppEventsLogger logger = AppEventsLogger.newLogger(this);
//        logger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP);
        userData = new UserData();

//        startActivity(new Intent(this , TestActivity.class));
        imgVSplash = findViewById(R.id.ivSplash);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", ""+newToken);
            }
        });
        animateFadeIN(imgVSplash);

        initializeTimer();
    }

    /**
     * This sample has a single Activity, so we need to manually record "screen views" as
     * we change fragments.
     */
//    private void recordScreenView() {
//        // This string must be <= 36 characters long in order for setCurrentScreen to succeed.
//        String screenName = "Test Android FireBase Analytics";
//
//        // [START set_current_screen]
//        mFirebaseAnalytics.setCurrentScreen(this, screenName, null /* class override */);
//        // [END set_current_screen]
//    }
    private void initializeTimer() {
        checkLanguage();
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, getNextActivity());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }


    private Class getNextActivity() {
        Class aClass = null;

        if (userData.getRemmemberMe(SplashActivity.this)) {
            aClass = HomeActivity.class;
        } else {
            aClass = LoginActivity.class;
        }

//        checkLanguage();

        return aClass;

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //------------------- Implemented methods-------------------------------------//


    private void animateFadeIN(ImageView imgv) {
        Log.e("Tag", "2");
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setStartOffset(500);
        fadeIn.setDuration(3000);
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        imgv.setAnimation(animation);
        Log.e("Tag", "3");
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    void checkLanguage() {
        if (userData.getLocalization(SplashActivity.this) == -1) { // no found lang before .. first set up application
            if (new Localization().getDefaultLocal(SplashActivity.this) == Localization.ARABIC_VALUE) { //RTL
                userData.saveLocalization(SplashActivity.this, Localization.ARABIC_VALUE);
            } else {
                userData.saveLocalization(SplashActivity.this, Localization.ENGLISH_VALUE);
            }
        } else {
            userData.saveLocalization(SplashActivity.this, userData.getLocalization(SplashActivity.this));

        }
        new Localization().setLanguagefromSplash(SplashActivity.this, userData.getLocalization(SplashActivity.this));
    }
}
