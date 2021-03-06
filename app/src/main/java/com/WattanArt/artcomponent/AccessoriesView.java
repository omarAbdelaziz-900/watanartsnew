package com.WattanArt.artcomponent;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class AccessoriesView extends ViewTemplate<String> {


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


//    @Override
//    public void setData(Integer data) {
//        if (data > 0)
//            setImageResource(data);
//    }

    @Override
    public void setData(String data) {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(40));
        Glide.with(this)
                .load(data)
//                .apply(requestOptions)
                .into(this);
        if (dimensionData == null)return;
        setCornerRadius(dimensionData.getRadius());
    }
}