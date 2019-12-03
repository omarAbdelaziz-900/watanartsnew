package com.WattanArt.artcomponent;

import android.content.Context;
import android.util.AttributeSet;

public class AccessoriesView extends ViewTemplate<Integer> {


    public AccessoriesView(Context context) {
        super(context);
    }

    public AccessoriesView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public AccessoriesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    int getDimenWidth() {
        return dimensionData.getAccessoriesWidth();
    }

    @Override
    int getDimenHeight() {
        return dimensionData.getAccessoriesHeight();
    }

    @Override
    public int getDimenStart() {
        return dimensionData.getAccessoriesX();
    }

    @Override
    public int getDimenTop() {
        return dimensionData.getAccessoriesY();
    }


    @Override
    public void setData(Integer data) {
        if (data > 0)
            setImageResource(data);
    }
}