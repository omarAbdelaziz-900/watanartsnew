package com.WattanArt.Utils;

/**
 * Created by khaled.badawy on 5/27/2018.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PictUtil {

    private static Bitmap b2;

    public static File getSavePath() {
        File path;
        if (hasSDCard()) { // SD card
            path = new File(getSDCardPath() + "/.WattanArt/.Cache");
            path.mkdirs();
        } else {
            path = Environment.getDataDirectory();
        }
        return path;
    }


    public static String getCacheFilenameCustom(String imageName) {
        File f = getSavePath();
        return f.getAbsolutePath() + "/" + imageName;
    }

    public static Bitmap loadFromFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) {
                return null;
            }
            Bitmap tmp = BitmapFactory.decodeFile(filename);
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    //    public static Bitmap loadFromCacheFile() {
//        return loadFromFile(getCacheFilename());
//    }
    public static void saveToCacheFile(Bitmap bmp, float rotateDegree, String fileName, int desiredWidth,
                                       int desiredHeight, saveBitmapCallback callback) {
        saveToFile(getCacheFilenameCustom(fileName), bmp, rotateDegree, desiredWidth, desiredHeight, callback);
    }

    public static void saveToFile(String filename, Bitmap bmp, float rotateDegree, final int width, final int height, saveBitmapCallback callback) {
        Log.e("CacheFileName", "-->" + filename);

        Bitmap[] bitmaps = new Bitmap[1];
        int[] dimens = new int[2];
        dimens[0] = width;
        dimens[1] = height;

        bitmaps[0] = bmp;
//        try {
//
//            FileOutputStream out = null;
//            try {
//                out = new FileOutputStream(filename);
//                bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
//
//                out.flush();
//                out.close();
//
//                callback.onSuccess(filename);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Log.e("asdsadad","111111ddddddddddddddd");
//
//                callback.onFail(e);
//            } catch (IOException e) {
//                Log.e("asdsadad","dddqqqqqqqqqqqqqqdddddddddddd");
//
//                e.printStackTrace();
//                callback.onFail(e);
//            }

//            getScaledBitmap(bmp, width, height).subscribe(new Observer() {
//                @Override
//                public void onSubscribe(Disposable d) {
//
//                }
//
//                @Override
//                public void onNext(Object o) {
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                }
//
//                @Override
//                public void onComplete() {

//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filename, bmOptions);
//        int photoW = bmOptions.outWidth ;
//        int photoH = bmOptions.outHeight ;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(width /photoW ,  height/photoH );
//
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;


        Observable.create(emitter -> {
            FileOutputStream out = null;
            try {
                if (rotateDegree != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotateDegree);

                    bitmaps[0] = Bitmap.createBitmap(bitmaps[0], 0, 0,
                            bitmaps[0].getWidth(),
                            bitmaps[0].getHeight(), matrix, false);

                    if (rotateDegree == 90f || rotateDegree == 270f) {
                        dimens[0] = height;
                        dimens[1] = width;
                    }

                }
                bitmaps[0] = BITMAP_RESIZER(bitmaps[0], dimens[0], dimens[1]);

                out = new FileOutputStream(filename);
                bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 99, out);


//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 90, out);
//                bitmaps[0] = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
//
//                OnlySaveToLocation(filename , bitmaps[0]);



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                callback.onFail(e);
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFail(e);
            } catch (Exception e) {
                e.printStackTrace();
                callback.onFail(e);
            }
            out.flush();
            out.close();
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                callback.onSuccess(filename);
            }
        });
    }


    public static void OnlySaveToLocation(String filePath , Bitmap bmp){
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            out.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static Bitmap scaleBitmap(Bitmap bitmapToScale, float newWidth, float newHeight) {
        if (bitmapToScale == null)
            return null;
//get the original width and height
        int width = bitmapToScale.getWidth();
        int height = bitmapToScale.getHeight();
// create a matrix for the manipulation
        Matrix matrix = new Matrix();

// resize the bit map
        matrix.postScale(newWidth / width, newHeight / height);

// recreate the new Bitmap and set it back
        return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);
    }

    public static Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2,
                middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }


//    fun resizeImage(file: File, scaleTo: Int = 1024) {
//        val bmOptions = BitmapFactory.Options()
//        bmOptions.inJustDecodeBounds = true
//        BitmapFactory.decodeFile(file.absolutePath, bmOptions)
//        val photoW = bmOptions.outWidth
//        val photoH = bmOptions.outHeight
//
//        // Determine how much to scale down the image
//        val scaleFactor = Math.min(photoW / scaleTo, photoH / scaleTo)
//
//        bmOptions.inJustDecodeBounds = false
//        bmOptions.inSampleSize = scaleFactor
//
//        val resized = BitmapFactory.decodeFile(file.absolutePath, bmOptions) ?: return
//                file.outputStream().use {
//            resized.compress(Bitmap.CompressFormat.JPEG, 75, it)
//            resized.recycle()
//        }
//    }

    private static Observable getScaledBitmap(Bitmap bmp, int width, int height) {
        return Observable.create(emitter -> {
            b2 = Bitmap.createScaledBitmap(bmp, width, height, true);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static boolean hasSDCard() { // SD????????
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }


    public static String getSDCardPath() {
        File path = Environment.getExternalStorageDirectory();
        return path.getAbsolutePath();
    }


    public interface saveBitmapCallback {
        void onSuccess(String path);

        void onFail(Exception e);
    }

}
