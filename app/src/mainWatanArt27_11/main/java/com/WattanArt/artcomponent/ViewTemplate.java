package com.WattanArt.artcomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.yalantis.ucrop.view.TouchImageView;


public abstract class ViewTemplate<T> extends TouchImageView implements ComponentView<T> {

    DimensionData dimensionData = null;
    T info = null;

    public ViewTemplate(Context context) {
        super(context);
    }

    public ViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
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

    private void setPosition() {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
        params.width = getDimenWidth();
        params.height = getDimenHeight();
        if (getDimenStart() > 0)
            params.setMarginStart(getDimenStart());
        if (getDimenTop() > 0)
            params.topMargin = getDimenTop();
        this.setLayoutParams(params);
    }

}