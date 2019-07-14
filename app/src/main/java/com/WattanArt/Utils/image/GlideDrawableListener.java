package com.WattanArt.Utils.image;

import android.graphics.Bitmap;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

abstract class GlideDrawableListener implements RequestListener<Bitmap> {


    @Override
    public boolean onLoadFailed( GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
        onFail(e.getMessage());
        return false;
    }

    @Override
    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
        onSuccess(model.toString());
        return false;
    }

    public void onSuccess(String url) {

    }

    public void onFail(String url) {
    }

}
