/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yalantis.ucrop.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class FastBitmapDrawable extends Drawable {

    private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

    private Bitmap mBitmap;
    private int mAlpha;
    private int mWidth, mHeight;

    public FastBitmapDrawable(Bitmap b) {
        mAlpha = 255;

//        b = Bitmap.createScaledBitmap(b,
//                b.getWidth()/2, b.getHeight()/2, false);
//        b = getResizedBitmap(b, 4000);
//        Log.e("tetete", "" + getBitmapSize(b));
//
//        if (getBitmapSize(b)  > 10){
//            b = getResizedBitmap(b , 4000);
//        }
         setBitmap(b);
    }

//    private long getBitmapSize(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] imageInByte = stream.toByteArray();
//        return imageInByte.length/1024/1024;
//    }

//    /**
//     * reduces the size of the image
//     *
//     * @param image
//     * @param maxSize
//     * @return
//     */
//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float) width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }


    @Override
    public void draw(Canvas canvas) {
        if (mBitmap != null && !mBitmap.isRecycled()) {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            options.inScaled = false;
//            Bitmap result = Bitmap.createScaledBitmap(mBitmap,
//                    mBitmap.getWidth()/2, mBitmap.getHeight()/2, true);
//            result.copy(options , true);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG,60,new ByteArrayOutputStream());

            canvas.drawBitmap(mBitmap, null, getBounds(), mPaint);
        }
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setFilterBitmap(boolean filterBitmap) {
        mPaint.setFilterBitmap(filterBitmap);
    }

    public int getAlpha() {
        return mAlpha;
    }

    @Override
    public void setAlpha(int alpha) {
        mAlpha = alpha;
        mPaint.setAlpha(alpha);
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mHeight;
    }

    @Override
    public int getMinimumWidth() {
        return mWidth;
    }

    @Override
    public int getMinimumHeight() {
        return mHeight;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap b) {
        mBitmap = b;
        if (b != null) {
            mWidth = mBitmap.getWidth();
            mHeight = mBitmap.getHeight();
        } else {
            mWidth = mHeight = 0;
        }
    }

}
