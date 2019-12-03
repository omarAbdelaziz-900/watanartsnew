package com.WattanArt.artcomponent;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ObjectView extends ViewTemplate<String> {

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


//    @Override
//    public void setData(Integer data) {
//        setImageResource(data);
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