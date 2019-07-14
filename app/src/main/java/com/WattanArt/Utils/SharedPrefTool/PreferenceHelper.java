package com.WattanArt.Utils.SharedPrefTool;

import android.content.Context;

import com.WattanArt.model.Response.User;
import com.WattanArt.Utils.config.Constants;


/**
 * Created by Android Team on 1/31/2018.
 */

public class PreferenceHelper {

    public static String TAG_LANGUAGE_SCREEN = "language";
    public static String TAG_GENRATE_CODE = "code";
    public static String TAG_NOTIFICATION_COUNT = "notification_count";
    public static String TAG_LOCALIZATION = "localization";
    public static String TAG_SAVE_PROFILE_INFO = "profile_info";


    public static void saveChildProfile(Context context, Object myObject) {
        SharedPreferencesTool.saveObject(context, TAG_SAVE_PROFILE_INFO, myObject);
    }


    public static User getChildProfile(Context context) {
        return SharedPreferencesTool.getObject(context, TAG_SAVE_PROFILE_INFO, User.class);
    }

    public static void saveGenrateCode(Context context, boolean genrateCode) {
        SharedPreferencesTool.setBoolean(context, genrateCode, TAG_GENRATE_CODE);
    }

    public static boolean getGenrateCode(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_GENRATE_CODE);
    }

    public static void saveNotificationCount(Context context, boolean count) {
        SharedPreferencesTool.setBoolean(context, count, TAG_NOTIFICATION_COUNT);
    }

    public static boolean getNotificationCount(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_NOTIFICATION_COUNT);
    }


    public static void saveLocalizationID(Context context, int value) {
        SharedPreferencesTool.setInt(context, TAG_LOCALIZATION, value);
    }

    public static int getLocalizationID(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_LOCALIZATION);
    }


    public static void clearShared(Context context) {
        SharedPreferencesTool.clearObject(context);
    }

    public void setLanguageScreen(Context context, boolean isShown) {
        SharedPreferencesTool.setBoolean(context, isShown, TAG_LANGUAGE_SCREEN);
    }

    public boolean isLanguageShown(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_LANGUAGE_SCREEN);
    }


    public static String getUserId(Context context) {
        User user = getChildProfile(context);
        if (user != null) {
            return user.UserID;
        }
        return null;
    }


    public static void saveCurrentLanguage(Context context, String Lang) {
        SharedPreferencesTool.saveObject(context, TAG_LANGUAGE_SCREEN, Lang);
    }

    public static String getCurrentLanguage(Context context) {
        Object o = SharedPreferencesTool.getString(context, TAG_LANGUAGE_SCREEN);
        if (o != null) {
            return o.toString();
        } else {
            return Constants.EN_LANG;
        }

    }


}
