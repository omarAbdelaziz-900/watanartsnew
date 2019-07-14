package me.iwf.photopicker.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by myc on 2016/12/14.
 * More Code on 1101255053@qq.com
 * Description:
 */
public class FileUtils {
    public static boolean fileIsExists(String path) {
        if (path == null || path.trim().length() <= 0) {
            return false;
        }
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isImageLowResolution(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int height = options.outHeight;

//        Log.e("ImageAttr", "-->" + path + "       width = " + width + "       height = " + height);
        if (width < 600 || height < 600) {
            return true;
        } else {
            return false;
        }
    }

//        public static boolean isImageLowResolution(Context context, final String path) {
//        Glide.with(context)
//                .asBitmap()
//                .load(path)
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap bitmap,
//                                                Transition<? super Bitmap> transition) {
//                        int width = bitmap.getWidth();
//                        int height = bitmap.getHeight();
//
//                        Log.e("ImageAttr", "-->" + path + "       width = " + width + "       height = " + height);
//                        if (width < 600 || height < 600) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }
//                });
////
//    }


}
