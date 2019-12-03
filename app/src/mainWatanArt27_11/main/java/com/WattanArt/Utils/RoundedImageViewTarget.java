package com.WattanArt.Utils;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.lang.ref.WeakReference;

/**
 * Created by Android Team on 1/31/2018.
 */

public class RoundedImageViewTarget extends BitmapImageViewTarget {
    WeakReference<ImageView> mImageView;

    public RoundedImageViewTarget(ImageView view) {
        super(view);
        mImageView = new WeakReference<ImageView>(view);
    }

    @Override
    protected void setResource(Bitmap resource) {


//        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(mActivity.getResources(), resource);
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(mImageView.get().getResources(), resource);
        circularBitmapDrawable.setCircular(true);
        mImageView.get().setImageDrawable(circularBitmapDrawable);
    }
}
