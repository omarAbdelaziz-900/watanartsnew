package com.WattanArt.artcomponent;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

public class ObjectView extends ViewTemplate<Integer> {

    //
    public ObjectView(Context context) {
        super(context);
    }

    public ObjectView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ObjectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public void setData(Integer data) {
        setImageResource(data);
    }


//    @Override
//    public void setData(String data) {
//        Glide.with(this)
//                .load(data)
//                .into(this);
//        if (dimensionData == null)return;
//        setCornerRadius(dimensionData.getRadius());
//
//    }

    @Override
    public boolean isTouchable() {
        return false;
    }

    @Override
    public int getDimenHeight() {
        return dimensionData.getImageHeight();
    }

    @Override
    public int getDimenWidth() {
        return dimensionData.getImageWidth();
    }
}