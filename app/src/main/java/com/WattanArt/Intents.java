package com.WattanArt;


import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by cl-macmini-108 on 10/05/2016 AD.
 */
public class Intents {


    /************************Activity Intents*************************/

    /*public static void removeAllAndStartNewActivity(@NonNull final Class<?> destination,
                                                    @NonNull final Object context) {
        removeAllAndStartNewActivity(destination, context, null);
    }

    public static void removeAllAndStartNewActivity(@NonNull final Class<?> destination,
                                                    @NonNull final Object context,
                                                    final Bundle bundle) {
        Activity activity = GeneralFunction.getActivity(context);
        Intent intent = new Intent(activity, destination);
        if (null != bundle)
            intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        ActivityCompat.finishAffinity(activity);
    }


    public static void nextActivity(@NonNull final Class<?> destination,
                                    @NonNull final Object context) {
        nextActivity(destination, context, null);
    }

    public static void nextNewActivity(@NonNull final Class<?> destination,
                                       @NonNull final Object context) {
        Activity activity = GeneralFunction.getActivity(context);
        Intent intent = new Intent(activity, destination);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent, activity);
    }

    public static void nextActivity(@NonNull final Class<?> destination,
                                    @NonNull final Object context, final Bundle bundle) {
        Activity activity = GeneralFunction.getActivity(context);
        Intent intent = new Intent(activity, destination);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent, activity);
    }

    private static void startActivity(@NonNull final Intent intent,
                                      @NonNull final Activity context) {
        context.startActivity(intent);
    }

    public static void startActivityForResult(@NonNull final Class<?> destination,
                                              @NonNull final Object context, int code) {
        startActivityForResult(destination, context, code, null);
    }

    public static void startActivityForResult(@NonNull final Class<?> destination,
                                              @NonNull final Object context, int code,
                                              final Bundle bundle) {
        Activity activity = GeneralFunction.getActivity(context);
        Intent intent = new Intent(activity, destination);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivityForResult(intent, code);
    }

    public static void startActivityForResult(@NonNull final Class<?> destination,
                                              @NonNull final Fragment context, int code) {
        startActivityForResult(destination, context, code, null);
    }

    public static void startActivityForResult(@NonNull final Class<?> destination,
                                              @NonNull final Fragment context, int code,
                                              final Bundle bundle) {
        Context activity = context.getContext();
        Intent intent = new Intent(activity, destination);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent, code);
    }

    public static void withoutHistory(@NonNull final Class<?> destination,
                                      @NonNull final Object context) {
        withoutHistory(destination, context, null);
    }

    private static void withoutHistory(@NonNull final Class<?> destination,
                                       @NonNull final Object context, final Bundle bundle) {
        Activity activity = GeneralFunction.getActivity(context);
        Intent intent = new Intent(activity, destination);
        if (bundle != null)
            intent.putExtras(bundle);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
    }

    public static void removeFragment(@NonNull final Object context, @NonNull Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager(context);

        if (fragmentManager==null)
            return;

        Fragment myFragment = fragmentManager.
                findFragmentByTag(GeneralFunction.getClassName(fragment));
        if (myFragment != null)
            fragmentManager.beginTransaction().remove(fragment).commitNow();
    }

    */

    /****************************Fragment Operations**********************************//*
    public static void replaceFragment(@NonNull final Object context,
                                       @IdRes int target, @NonNull Fragment fragment,
                                       Bundle bundle) {
//        FragmentManager fragmentManager = getFragmentManager(context);
        if (fragmentManager==null)
            return;

        Fragment myFragment = fragmentManager.findFragmentByTag(
                fragment.getClass().getSimpleName());
        if (myFragment != null && myFragment.isVisible())
            return;

        if (bundle != null)
            fragment.setArguments(bundle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(fragment);
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(fragment);
        }
        fragmentManager.beginTransaction().replace(target, fragment,
                GeneralFunction.getClassName(fragment))
                .commitAllowingStateLoss();
    }

//    public static void replaceFragment(@NonNull final Object context,
//                                       @IdRes int target, @NonNull Fragment fragment, boolean addToStack) {
//        FragmentManager fragmentManager = getFragmentManager(context);
//
//        Fragment myFragment = fragmentManager.findFragmentByTag(
//                fragment.getClass().getSimpleName());
//        if (myFragment != null && myFragment.isVisible())
//            return;
//
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//
//
//        fragmentTransaction.replace(target, fragment, GeneralFunction.getClassName(fragment));
//        if (addToStack) {
//            fragmentTransaction.addToBackStack(GeneralFunction.getClassName(fragment));
//        }
//
//        fragmentTransaction.commitAllowingStateLoss();
//    }

    public static <T extends Fragment> void replaceFragment(@NonNull final Object context,
                                                            @IdRes int target, @NonNull T fragment) {
        replaceFragment(context, target, fragment, null);
    }


    public static void replaceFragmentBackStack(@NonNull final Object context, @IdRes int target,
                                                @NonNull Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = getFragmentManager(context);
        if (fragmentManager==null)
            return;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (bundle != null)
            fragment.setArguments(bundle);

        Fragment myFragment = fragmentManager.findFragmentByTag(GeneralFunction.
                getClassName(fragment));
        if (myFragment != null) {
            if (myFragment.isVisible())
                return;
            else if (myFragment.getClass().equals(fragment.getClass()))
                fragmentTransaction.replace(target, fragment,
                        GeneralFunction.getClassName(fragment)).commit();
        } else
            fragmentTransaction.replace(target, fragment, GeneralFunction.getClassName(fragment)).
                    addToBackStack(GeneralFunction.getClassName(fragment)).commit();
    }

    public static <T extends Fragment> void replaceFragmentBackStack(@NonNull final Object context,
                                                                     @IdRes int target,
                                                                     @NonNull T fragment) {
        replaceFragmentBackStack(context, target, fragment, null);
    }

    public static <T extends Fragment> void addFragment(@NonNull final Object context,
                                                        @NonNull T fragment) {
        addFragment(context, fragment, GeneralFunction.getClassName(fragment));
    }
*/
    public static void showProgressDialog(DialogFragment currentDialog, String tagName,
                                          Object context) {

        addFragment(context, currentDialog, tagName);
    }

    private static <T extends Fragment> void addFragment(@NonNull final Object context,
                                                         @NonNull T fragment, String tagName) {
        FragmentManager fragmentManager = getFragmentManager(context);
        if (fragmentManager == null)
            return;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, tagName);
        transaction.commitAllowingStateLoss();
    }

    public static void hideProgressDialog(String tagName, Object context) {
        if (null == context)
            return;
        FragmentManager fragmentManager = getFragmentManager(context);
        if (fragmentManager == null)
            return;

        DialogFragment transparentDialog = (DialogFragment) fragmentManager.findFragmentByTag(tagName);
        if (transparentDialog == null || !transparentDialog.isAdded()) {
            return;
        }

        transparentDialog.dismissAllowingStateLoss();
    }

//    public static <T extends DialogFragment> void showDialogFragment(@NonNull final Object context,
//                                                                     @NonNull T fragment) {
//        showDialogFragment(context, fragment, null);
//    }

    /*Fragment Manager From Activity and ChildFragment Namager
     * from fragment*/
    private static FragmentManager getFragmentManager(Object context) {
        if (context instanceof FragmentActivity)
            return ((FragmentActivity) context).getSupportFragmentManager();
        else if (((Fragment) context).isAdded())
            return ((Fragment) context).getChildFragmentManager();

        return null;
    }
    /*public static <T extends DialogFragment> void showDialogFragment
            (@NonNull final Object context, @NonNull T fragment, Bundle bundle) {
        FragmentManager fragmentManager = getFragmentManager(context);
        if (fragmentManager==null)
            return;
        DialogFragment myFragment = (DialogFragment) fragmentManager.findFragmentByTag
                (GeneralFunction.getClassName(fragment));
        if (null != myFragment)
            myFragment.dismiss();
        if (null != bundle)
            fragment.setArguments(bundle);
        fragment.show(fragmentManager, GeneralFunction.getClassName(fragment));
    }

    public static <T extends DialogFragment> void hideDialogFragment
            (@NonNull final Object context, @NonNull T fragment) {
        FragmentManager fragmentManager = getFragmentManager(context);
        if (fragmentManager==null)
            return;
        DialogFragment myFragment = (DialogFragment) fragmentManager.
                findFragmentByTag(GeneralFunction.getClassName(fragment));
        if (myFragment != null)
            myFragment.dismiss();
    }



    *//********************************Call a Number******************************//*
    public static void callNumberWithDialer(final Object object, final String number) {
        try {
            Activity context = GeneralFunction.getActivity(object);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.e("SampleApp", "Failed to invoke call", e);
        }
    }


    *//*************Send email with an email Client***********************//*
    public static void openEmailClient(final Object object, final String email) {
        openEmailClient(object, email, "", null);
    }

    public static void openEmailClient(final Object object, final String email,
                                       final String subject, final String body) {
        Activity context = GeneralFunction.getActivity(object);
        *//* Create the Intent *//*
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        *//* Fill it with Data *//*
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        emailIntent.setFlags(emailIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
             *//* Send it off to the Activity-Chooser *//*
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (Exception e) {
            GeneralFunction.showToast("No app is installed for email", context);
        }
    }

    public static void startIntent(final Activity activity, final Intent intent, final int code) {
        try {
             *//* Send it off to the Activity-Chooser *//*
            activity.startActivityForResult(intent,
                    EnumNumericConstants.NumericConstants.CAMERA.getCode());
        } catch (Exception e) {
            GeneralFunction.showToast("No app is installed for app", activity);
        }
    }

    public static void startIntent(final Fragment activity, final Intent intent, final int code) {
        try {
             *//* Send it off to the Activity-Chooser *//*
            activity.startActivityForResult(intent, EnumNumericConstants.NumericConstants.CAMERA.
                    getCode());
        } catch (Exception e) {
            GeneralFunction.showToast("No app is installed for this app", activity.getContext());
        }
    }

    *//**********************************************View Url in Application and failed
     * to find then open the linke
     in browser *****************************************************//*
    public static void viewURL(Context context, String url) {
        if (null == url) {
            GeneralFunction.showToast("Invalid URL", context);
            return;
        }
        if (null == context)
            return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setData(Uri.parse(url));
        intent.setDataAndType(Uri.parse(url), GeneralFunction.getMimeType(url));
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            openBrowser(context, url);
        }
    }

    private static void openBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(browserIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        browserIntent.setData(Uri.parse(url));
        try {
            context.startActivity(browserIntent);
        } catch (Exception e) {
            GeneralFunction.showToast(String.format(context.getString
                            (R.string.s_error_toast_no_application),
                    GeneralFunction.getFileType(url)), context);
        }
    }

    *//****************************Open App in Play Store or Web**********************************//*
    public static void openAppInPlaystore(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        //packageName=context.getPackageName();
        Uri uri = null;
        try {
            uri = Uri.parse("market://details?id=" + packageName);
        } catch (android.content.ActivityNotFoundException anfe) {
            uri = Uri.parse("https://play.google.com/store/apps/details?id=" + packageName);
        }
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void shareApp(String packageName, Context context, String shareName) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Sharing Saydl");

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);

        for (final ResolveInfo app : activityList) {
            if ((app.activityInfo.name).contains(packageName)) {
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                        activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setComponent(name);
                context.startActivity(shareIntent);
                return;
            }
        }

        GeneralFunction.showToast(String.format(context.getString(R.string.s_error_ShareError),
                shareName), context);
    }


    public static void shareTextUrl(Context context , String title , String message) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.

        share.putExtra(Intent.EXTRA_SUBJECT , title);
        share.putExtra(Intent.EXTRA_TEXT, message);

        context.startActivity(Intent.createChooser(share, "Share!"));
    }

    public static void ShareUrl(Context context , String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(Intent.createChooser(browserIntent, "Open With!"));
    }
*/


}

