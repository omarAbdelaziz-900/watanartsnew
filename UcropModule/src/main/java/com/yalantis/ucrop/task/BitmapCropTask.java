package com.yalantis.ucrop.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.util.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Crops part of image that fills the crop bounds.
 * <p/>
 * First image is downscaled if max size was set and if resulting image is larger that max size.
 * Then image is rotated accordingly.
 * Finally new Bitmap object is created and saved to file.
 */
public class BitmapCropTask extends AsyncTask<Void, Void, Throwable> {

    private static final String TAG = "BitmapCropTask";

    static {
        System.loadLibrary("ucrop");
    }

    private Bitmap mViewBitmap;

    private final RectF mCropRect;
    private final RectF mCurrentImageRect;

    private float mCurrentScale, mCurrentAngle;
    private int mMaxResultImageSizeX, mMaxResultImageSizeY;

    private final Bitmap.CompressFormat mCompressFormat;
    private final int mCompressQuality;
    private final String mImageInputPath, mImageOutputPath;
    private final ExifInfo mExifInfo;
    private final BitmapCropCallback mCropCallback;

    private int mCroppedImageWidth, mCroppedImageHeight;
    private int cropOffsetX, cropOffsetY;

    Uri uri;
    Matrix matrix;

    public BitmapCropTask(@Nullable Bitmap viewBitmap, Matrix matrix, Uri uri,
                          @NonNull ImageState imageState,
                          @NonNull CropParameters cropParameters,
                          @Nullable BitmapCropCallback cropCallback) {
        this.uri = uri;
        this.matrix = matrix;

        mViewBitmap = viewBitmap;
        mCropRect = imageState.getCropRect();
        mCurrentImageRect = mCropRect;

        mCurrentScale = imageState.getCurrentScale();
        mCurrentAngle = imageState.getCurrentAngle();

        mMaxResultImageSizeX = cropParameters.getMaxResultImageSizeX();
        mMaxResultImageSizeY = cropParameters.getMaxResultImageSizeY();

        mCompressFormat = cropParameters.getCompressFormat();
        mCompressQuality = cropParameters.getCompressQuality();

        cropOffsetX = cropParameters.getX();
        cropOffsetY = cropParameters.getY();


        mImageInputPath = cropParameters.getImageInputPath();
        mImageOutputPath = cropParameters.getImageOutputPath();
        mExifInfo = cropParameters.getExifInfo();

        mCropCallback = cropCallback;
    }

    @Override
    @Nullable
    protected Throwable doInBackground(Void... params) {
        if (mViewBitmap == null) {
            return new NullPointerException("ViewBitmap is null");
        } else if (mViewBitmap.isRecycled()) {
            return new NullPointerException("ViewBitmap is recycled");
        } /*else if (mCurrentImageRect.isEmpty()) {
            return new NullPointerException("CurrentImageRect is empty");
        }*/

        float resizeScale = resize();

        try {
            crop(resizeScale);
            mViewBitmap = null;
        } catch (Throwable throwable) {
            return throwable;
        }

        return null;
    }

    private float resize() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageInputPath, options);

//        boolean swapSides = mExifInfo.getExifDegrees() == 90 || mExifInfo.getExifDegrees() == 270;
        float scaleX = (/*swapSides ? options.outHeight : */options.outWidth) / (float) mViewBitmap.getWidth();
        float scaleY = (/*swapSides ? options.outWidth : */options.outHeight) / (float) mViewBitmap.getHeight();

        float resizeScale = Math.min(scaleX, scaleY);

        mCurrentScale /= resizeScale;

        resizeScale = 1;
        if (mMaxResultImageSizeX > 0 && mMaxResultImageSizeY > 0) {
            float cropWidth = mCropRect.width() / mCurrentScale;
            float cropHeight = mCropRect.height() / mCurrentScale;
            Log.e("TEST111", "cropWidth -> " + cropWidth + "    mMaxResultImageSizeX ->"
                    + mMaxResultImageSizeX + "     cropHeight " + cropHeight + "     mMaxResultImageSizeY " + mMaxResultImageSizeY);

            if (cropWidth > mMaxResultImageSizeX || cropHeight > mMaxResultImageSizeY) {

                scaleX = mMaxResultImageSizeX / cropWidth;
                scaleY = mMaxResultImageSizeY / cropHeight;
                resizeScale = Math.min(scaleX, scaleY);
                Log.e("TEST2222", "scaleX -> " + scaleX + "    scaleY ->" + scaleY + "     TEST " + resizeScale);

                mCurrentScale /= resizeScale;
            }
        }
        return resizeScale;
    }

    private boolean crop(float resizeScale) throws IOException {
//        ExifInterface originalExif = new ExifInterface(mImageInputPath);

//        mCroppedImageWidth = Math.round(mCropRect.width() / mCurrentScale);
//        mCroppedImageHeight = Math.round(mCropRect.height() / mCurrentScale);

        boolean shouldCrop = shouldCrop(mCroppedImageWidth, mCroppedImageHeight);
        Log.e(TAG, "Should crop: " + shouldCrop);

        if (shouldCrop) {
            Log.e("mCropRect", "-->" + mCropRect);
            Log.e("mImageInputPath", "-->" + mImageInputPath);
            Log.e("mImageOutputPath", "-->" + mImageOutputPath);
            Log.e("mCurrentImageRect", "-->" + mCurrentImageRect);

            Log.e("mCroppedImageWidth", "-->" + mCroppedImageWidth);
            Log.e("mCroppedImageHeight", "-->" + mCroppedImageHeight);

            Log.e("mCurrentScale", "-->" + mCurrentScale);
            Log.e("cropOffsetX", "-->" + cropOffsetX);
            Log.e("cropOffsetY", "-->" + cropOffsetY);
            Log.e("mCurrentAngle", "-->" + mCurrentAngle);
            Log.e("resizeScale", "-->" + resizeScale);
            Log.e("mCompressFormat.ordinal", "-->" + mCompressFormat.ordinal());
            Log.e("mCompressQuality", "-->" + mCompressQuality);
            Log.e("mExifInfo.ExifDegrees()", "-->" + mExifInfo.getExifDegrees());
            Log.e("mExifInfo.ExifTransla", "-->" + mExifInfo.getExifTranslation());


            //if mCurrentScale = infinity it throw io exception
            if (mCurrentScale == Float.POSITIVE_INFINITY) {
                mCurrentScale = 1f;
            }

            if (cropOffsetX < 0) {
                cropOffsetX = 0;
            }
            if (cropOffsetY < 0) {
                cropOffsetY = 0;
            }


            Bitmap bitmap = BitmapFactory.decodeFile(mImageInputPath);
            if (cropOffsetX + mMaxResultImageSizeX > bitmap.getWidth()) {
                mMaxResultImageSizeX = bitmap.getWidth() - cropOffsetX;
            }

            if (cropOffsetY + mMaxResultImageSizeY > bitmap.getHeight()) {
                mMaxResultImageSizeY = bitmap.getHeight() - cropOffsetY;
            }

            bitmap = Bitmap.createBitmap(bitmap, cropOffsetX, cropOffsetY,
                    mMaxResultImageSizeX, mMaxResultImageSizeY/*, matrix, true*/);
            FileOutputStream out = null;
            try {
                bitmap = scaleBitmap(bitmap, mMaxResultImageSizeX, mMaxResultImageSizeY);

                out = new FileOutputStream(mImageOutputPath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 99, out);

//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
//
//                OnlySaveToLocation(mImageOutputPath , bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.flush();
            out.close();
            return true;
//            boolean cropped = cropCImg(mImageInputPath, mImageOutputPath,
//                    cropOffsetX, cropOffsetY, mMaxResultImageSizeX, mMaxResultImageSizeY,
//                    mCurrentAngle, resizeScale, mCompressFormat.ordinal(), mCompressQuality,
//                    mExifInfo.getExifDegrees(), mExifInfo.getExifTranslation());

//            Log.e("Cropped", "-->" + cropped);
//            if (cropped && mCompressFormat.equals(Bitmap.CompressFormat.JPEG)) {
//                ImageHeaderParser.copyExif(originalExif, mCroppedImageWidth, mCroppedImageHeight, mImageOutputPath);
//            }
//            saveCroppedImage(uri);
//            return cropped;
        } else {
            FileUtils.copyFile(mImageInputPath, mImageOutputPath);
//            saveCroppedImage(uri);
            return false;
        }
    }


    public static void OnlySaveToLocation(String filePath , Bitmap bmp){
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
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

    /**
     * Check whether an image should be cropped at all or just file can be copied to the destination path.
     * For each 1000 pixels there is one pixel of error due to matrix calculations etc.
     *
     * @param width  - crop area width
     * @param height - crop area height
     * @return - true if image must be cropped, false - if original image fits requirements
     */
    private boolean shouldCrop(int width, int height) {
        int pixelError = 1;
        pixelError += Math.round(Math.max(width, height) / 1000f);
        return (mMaxResultImageSizeX > 0 && mMaxResultImageSizeY > 0)
                || Math.abs(mCropRect.left - mCurrentImageRect.left) > pixelError
                || Math.abs(mCropRect.top - mCurrentImageRect.top) > pixelError
                || Math.abs(mCropRect.bottom - mCurrentImageRect.bottom) > pixelError
                || Math.abs(mCropRect.right - mCurrentImageRect.right) > pixelError
                || mCurrentAngle != 0;
    }

    @SuppressWarnings("JniMissingFunction")
    native public static boolean
    cropCImg(String inputPath, String outputPath,
             int left, int top, int width, int height,
             float angle, float resizeScale,
             int format, int quality,
             int exifDegrees, int exifTranslation) throws IOException, OutOfMemoryError;

    @Override
    protected void onPostExecute(@Nullable Throwable t) {
        if (mCropCallback != null) {
            if (t == null) {
                Uri uri = Uri.fromFile(new File(mImageOutputPath));
                mCropCallback.onBitmapCropped(uri, cropOffsetX, cropOffsetY, mCroppedImageWidth, mCroppedImageHeight);
            } else {
                mCropCallback.onCropFailure(t);
            }
        }
    }

}
