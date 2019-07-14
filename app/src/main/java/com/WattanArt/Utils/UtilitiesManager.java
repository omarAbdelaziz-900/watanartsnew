package com.WattanArt.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.os.ConfigurationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;

//import com.google.firebase.iid.FirebaseInstanceId;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by Android Team on 1/31/2018.
 */

public class UtilitiesManager {

//    public static String getDeviceID(Context context) {
//        TelephonyManager telecomManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        String id = telecomManager.getDeviceId();
//        if (id != null && id.length() != 0)
//            return id;
//        else return "empty";
//    }

    public static String getDeviceID(Context activity) {
        return Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
    public static PopupMenu getPopupMenu(final Activity activity, final View view, int menuLayout){
        PopupMenu popup = new PopupMenu(activity,view);
        popup.getMenuInflater().inflate(menuLayout, popup.getMenu());
        return  popup;
    }

    public static boolean isSdCardMounted() {
        String s = Environment.getExternalStorageState();
        return s.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Get ISO 3166-1 alpha-2 country code for this device (or null if not available)
     *
     * @param context Context reference to get the TelephonyManager instance from
     * @return country code or null
     */
    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getDeviceCountryCode(Context context) {
        Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
Log.e("Local" , locale.getCountry());
return locale.getDisplayCountry();

//        String countryCode;
//
//        // try to get country code from TelephonyManager service
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if(tm != null) {
//            // query first getSimCountryIso()
//            countryCode = tm.getSimCountryIso();
//            if (countryCode != null && countryCode.length() == 2)
//                return countryCode.toLowerCase();
//
//            if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
//                // special case for CDMA Devices
//                countryCode = getCDMACountryIso();
//            } else {
//                // for 3G devices (with SIM) query getNetworkCountryIso()
//                countryCode = tm.getNetworkCountryIso();
//            }
//
//            if (countryCode != null && countryCode.length() == 2)
//                return countryCode.toLowerCase();
//        }
//
//        // if network country not available (tablets maybe), get country code from Locale class
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            countryCode = context.getResources().getConfiguration().getLocales().get(0).getCountry();
//        } else {
//            countryCode = context.getResources().getConfiguration().locale.getCountry();
//        }
//
//        if (countryCode != null && countryCode.length() == 2) {
//
//            Log.e("coun found" , ""+ countryCode.toLowerCase());
//            return countryCode.toLowerCase();
//        }
//
//        // general fallback to "us"
//        Log.e("NOT FOund" , " uuuuuuuuuusssssssss");
//        return "us";
    }

    @SuppressLint("PrivateApi")
    public static String getCDMACountryIso() {
        try {
            // try to get country code from SystemProperties private class
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class);

            // get homeOperator that contain MCC + MNC
            String homeOperator = ((String) get.invoke(systemProperties,
                    "ro.cdma.home.operator.numeric"));

            // first 3 chars (MCC) from homeOperator represents the country code
            int mcc = Integer.parseInt(homeOperator.substring(0, 3));

            // mapping just countries that actually use CDMA networks
            switch (mcc) {
                case 330: return "PR";
                case 310: return "US";
                case 311: return "US";
                case 312: return "US";
                case 316: return "US";
                case 283: return "AM";
                case 460: return "CN";
                case 455: return "MO";
                case 414: return "MM";
                case 619: return "SL";
                case 450: return "KR";
                case 634: return "SD";
                case 434: return "UZ";
                case 232: return "AT";
                case 204: return "NL";
                case 262: return "DE";
                case 247: return "LV";
                case 255: return "UA";
            }
        } catch (ClassNotFoundException ignored) {
        } catch (NoSuchMethodException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (InvocationTargetException ignored) {
        } catch (NullPointerException ignored) {
        }

        return null;
    }
    /**
     * Checks Connection availability
     *
     * @param context
     * @return True if the device has internet connection and false otherwise
     */
    public static boolean canConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

//    public static String getFirebaseToken() {
//        return FirebaseInstanceId.getInstance().getToken();
//    }
}
