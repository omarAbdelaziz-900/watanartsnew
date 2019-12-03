package com.WattanArt.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.View;


import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.ui.Home.HomeActivity;

import java.util.Locale;

/**
 * Created by halima.reda on 3/12/2016.
 */
public class Localization {

    public static final int ARABIC_VALUE = 1;
    public static final int ENGLISH_VALUE = 2;

    public void setLanguage(Context context, int langaugeId) {
        Localization localization = new Localization();
        if (langaugeId == ARABIC_VALUE) {
            //arabic
            localization.changeLocale(context, new Locale("ar"), Localization.ARABIC_VALUE);
        } else if (langaugeId == ENGLISH_VALUE) {
            //english
            localization.changeLocale(context, new Locale("en"), Localization.ENGLISH_VALUE);
        }
        new UserData().saveLocalization(context, langaugeId);
    }

    public void setLanguagefromSplash(Context context, int langaugeId) {
        Localization localization = new Localization();
        if (langaugeId == ARABIC_VALUE) {
            //arabic
            localization.changeLocalefromSplash(context, new Locale("ar"), Localization.ARABIC_VALUE);
        } else if (langaugeId == ENGLISH_VALUE) {
            //english
            localization.changeLocalefromSplash(context, new Locale("en"), Localization.ENGLISH_VALUE);
        }
        new UserData().saveLocalization(context, langaugeId);
    }

    public void changeLocalefromSplash(Context context, Locale locale, int langaugeId) {
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        Locale.setDefault(locale);
        new UserData().saveLocalization(context, langaugeId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(conf.locale);
        }
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());

//        Intent intent = new Intent(context, HomeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
    }
    public void changeLocale(Context context, Locale locale, int langaugeId) {
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        Locale.setDefault(locale);
        new UserData().saveLocalization(context, langaugeId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(conf.locale);
        }
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());

        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public int getDefaultLocal(Context context) {
        Configuration config = context.getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            Log.e("LayoutDirection", "RTL");
            return ARABIC_VALUE;
        } else {
            Log.e("LayoutDirection", "LTR");
            return ENGLISH_VALUE;
        }
    }

    public  void changeLanguage(String language, Context context) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    // return current Language ID
    public int getCurrentLanguageID(Context context) {
        /**
         * 0 = arabic
         * 1 = english */
        int languageID = new UserData().getLocalization(context);
        Log.i("languageID", "" + languageID);
        return languageID;
    }
}
