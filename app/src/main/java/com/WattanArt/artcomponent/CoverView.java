package com.WattanArt.artcomponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.bumptech.glide.Glide;


public class CoverView extends CoverTemplate<String> {

    public CoverView(Context context) {
        super(context);
    }

    public CoverView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void setData(String data) {

        Glide.with(this)
                .load(data)
                .into(this);
        if (dimensionData == null)return;
        setCornerRadius(dimensionData.getRadius());

    }

    @Override
    public boolean isTouchable() {
        return true;
    }



    @Override
    int getDimenWidth() {
        return dimensionData.getCoverWidth();
    }

    @Override
    int getDimenHeight() {
        return dimensionData.getCoverHeight();
    }

    @Override
    public int getDimenStart() {
        return (int) dimensionData.getStartX();
    }

    @Override
    public int getDimenTop() {
        return (int) dimensionData.getStartY();
    }


    public void setBitmapData(Bitmap bitmap){
        setImageBitmap(bitmap);
        if (dimensionData == null)return;
        setCornerRadius(dimensionData.getRadius());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}