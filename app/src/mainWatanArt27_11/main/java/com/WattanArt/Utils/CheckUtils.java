package com.WattanArt.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CheckUtils {
    public static Dialog dialogNetwork = null;
    public static ArrayList<Integer> compareItemsIntegerList = new ArrayList<>();
    public static int compareCount;
     public static void addCompareItem(int id){
         compareItemsIntegerList.add(id);
         compareCount=compareItemsIntegerList.size();
     }
//    private int getSize() {
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//        Log.d("width", width + "");
//        Log.d("height", height + "");
//        return width;
//    }

    public static void removeCompareItem(int id){
        compareItemsIntegerList.remove(id);
        compareCount=compareItemsIntegerList.size();
    }
    public static int getCompareCount() {
        return compareCount;
    }

    public static List<Integer> getCompareItemsIntegerList() {
        return compareItemsIntegerList;
    }

    public static void hideKeypad(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openWebPage(Context context, String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Uri webPage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            context.startActivity(intent);
        } catch (Exception ex) {
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mConnectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivity.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting();
    }

//    public static void  OpenLocationGps(final Activity context) {
//        PermissionTool.checkAllPermission(context, new String[]
//                {PermissionTool.PERMISSION_LOCATION, PermissionTool.PERMISSION_location_COARSE});
//
//        new LocationTool(context).getNewLocation(new LocationTool.LocationListener() {
//            @Override
//            public void gotLocation(Location location) {
//                if (location != null) {
//                    if (location.getLongitude() > 0 && location.getLatitude() > 0) {
//
//
////                        Toast.makeText(context, location.getLongitude() + "" + ";;;;" +
////                                location.getLatitude(), Toast.LENGTH_SHORT).show();
//
//                    }else {
//
////                        Toast.makeText(context, location.getLongitude() + "" + ";;;;" +
////                                location.getLatitude(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
////                    getLocationGps();
////                    Toast.makeText(RegisterAsUser.this, "fail", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }
//
//
//    public static void loadSimplePicWithError(Context context, String url, ImageView pic) {
//        Glide.with(context).load(url).placeholder(R.drawable.ic_no_image).error(R.drawable.ic_no_image)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .dontAnimate().into(pic);
//    }

    public static boolean validString(String string) {
        if (string != null) {
            if (string.length() > 0) {
                return true;
            }
            return false;
        }
        return false;

    }

    public static boolean validObject(Object myObject) {
        if (myObject != null) {
            return true;
        }
        return false;

    }

    public static boolean validList(List list) {
        if (list != null) {
            if (list.size() > 0) {
                return true;
            }
            return false;
        }
        return false;

    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    //get image path
    public static String getRealPathFromURI(Context context, Uri uri) {
        String filePath = "";
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        }
        cursor.close();
        return filePath;
    }

    public static float getImageSize(String picturePath) {
        File file = new File(picturePath);
        // Get length of file in bytes
        long fileSizeInBytes = file.length();
        float fileSizeInKB = fileSizeInBytes / 1024;
        float fileSizeInMB = fileSizeInKB / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        return fileSizeInMB;
    }

    public static String encodeFileToBase64(String sourceFile) throws Exception {


        String base64EncodedData = Base64.encodeToString(loadFileAsBytesArray(sourceFile), Base64.DEFAULT);
        // Log.d("base 64 ", base64EncodedData + "");

        return base64EncodedData;
    }

    public static byte[] loadFileAsBytesArray(String fileName) throws Exception {
        File file = new File(fileName);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;

    }

    public static Bitmap decodeUri(Uri selectedImage, Context context) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 200;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(selectedImage), null, o2);
    }

//    public static void colorStatusBar(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//        }
//    }

    // to server
//    public static int getLocalization(Context context) {
////        Localization localization = new Localization();
//        UserData userData = new UserData();
//        return userData.getLocalization(context);
////        return localization.getCurrentLanguageID(context);
//
//    }



    public static String formateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy EEE");
        Date d = null;
        try {
            try {
                d = sdf.parse(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }

    public static String formateNewDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        Date d = null;
        try {
            try {
                d = sdf.parse(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }

    public static String formateTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            try {
                d = sdf.parse(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }

//    public static void setFontTextView(Context context, TextViewNormal textViewNormal) {
//        textViewNormal.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GE_Dinar_Two_Medium.otf"), Typeface.BOLD);
//        textViewNormal.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//
//    }


    public static Animation expand(final View item) {
        if (item.getVisibility() == View.GONE) {
            item.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            final int targetHeight = item.getMeasuredHeight();
            // Older versions of android (pre API 21) cancel animations for views with aaa height of 0.
            item.getLayoutParams().height = 1;
            item.setVisibility(View.VISIBLE);
            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    item.getLayoutParams().height = interpolatedTime == 1
                            ? LinearLayout.LayoutParams.WRAP_CONTENT
                            : (int) (targetHeight * interpolatedTime);
                    item.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            anim.setDuration((int) (targetHeight / item.getContext().getResources().getDisplayMetrics().density));
            item.startAnimation(anim);
            return anim;
        }
        return null;
    }

    public static Animation collapse(final View item) {
        if (item.getVisibility() == View.VISIBLE) {
            final int initialHeight = item.getMeasuredHeight();
            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        item.setVisibility(View.GONE);
                    } else {
                        item.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        item.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            // 1dp/ms
            anim.setDuration((int) (initialHeight / item.getContext().getResources().getDisplayMetrics().density));
            item.startAnimation(anim);
            return anim;
        }
        return null;
    }




}
