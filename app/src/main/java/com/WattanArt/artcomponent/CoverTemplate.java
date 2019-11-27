package com.WattanArt.artcomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.yalantis.ucrop.view.GestureCropImageView;
import com.yalantis.ucrop.view.TransformImageView;
import com.yalantis.ucrop.view.TransformativeImageView;


public abstract class CoverTemplate<T> extends TransformativeImageView implements ComponentView<T> {

    DimensionData dimensionData = null;
    T info = null;

    public CoverTemplate(Context context) {
        super(context);
    }

    public CoverTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoverTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return isTouchable() && super.dispatchTouchEvent(ev);
    }

    public boolean isTouchable() {
        return false;
    }

    abstract int getDimenWidth();

    abstract int getDimenHeight();

    public int getDimenTop() {
        return 0;
    }

    public int getDimenBottom() {
        return 0;
    }

    public int getDimenStart() {
        return 0;
    }

    public int getDimenEnd() {
        return 0;
    }

    @Override
    public void setDimension(DimensionData dimensionData) {
        this.dimensionData = dimensionData;
        setPosition();

    }


    public float convertPixelsToDp(float px){
        return px;
    }

    private void setPosition() {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
        params.width = (int) convertPixelsToDp(getDimenWidth());
        params.height = (int) convertPixelsToDp(getDimenHeight());
        if (getDimenStart() > 0)
            params.setMarginStart((int) convertPixelsToDp(getDimenStart()));
        if (getDimenTop() > 0)
            params.topMargin = (int) convertPixelsToDp(getDimenTop());
        this.setLayoutParams(params);
    }
}