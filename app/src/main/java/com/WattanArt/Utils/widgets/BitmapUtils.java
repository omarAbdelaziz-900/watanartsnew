package com.WattanArt.Utils.widgets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class BitmapUtils {
    public static Bitmap convertUrlToBitmap(String imgName){
        Bitmap bitmap = null;
        String drawableRes="http://23.236.154.106:8063/UploadedImages/"+imgName;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(drawableRes);
            bitmap= BitmapFactory.decodeStream((InputStream)url.getContent());
        } catch (IOException e) {
            //Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }



   public static void getBitmab(ArrayList<String>photos,Bitmap currentBitmap) {
        Log.e("photo",photos.get(0));
        File imgFile = new File(photos.get(0));
        if (imgFile.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(imgFile), options);
            bitmap=currentBitmap;
            if (bitmap != null) {
//                    mPresenter.returnUploadedImage(photos,imgFile);

            }
        }
    }
}
