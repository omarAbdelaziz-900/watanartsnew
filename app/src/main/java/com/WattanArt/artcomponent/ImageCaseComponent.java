package com.WattanArt.artcomponent;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.WattanArt.R;


public class ImageCaseComponent extends FrameLayout {

    /*views*/
    public ConstraintLayout container;
    public ImageView errorView;

    /*Dimension*/

    private DimensionData dimensions = null;

    public void setDimensions(DimensionData dimensions) {
        this.dimensions = dimensions;
        positioningViews(this);

    }


    void setDimens(int width, int height, int coverWidth, int coverHeight) {
        setDimensions(new DimensionData(width, height, coverWidth, coverHeight,
                0, 0, 0, 0, 0,
                0, 0));
    }


    public ImageCaseComponent(Context context) {
        super(context);
        setLayoutToView();
    }

    public ImageCaseComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutToView();
    }

    public ImageCaseComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutToView();
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageCaseComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setLayoutToView();
    }


    private void setLayoutToView() {
        container = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.componentview, this, false);
        this.addView(container);
        setViews();
    }

    private void setViews() {
        errorView = container.findViewById(R.id.error);
        positioningViews(this);
    }

    private void positioningViews(ViewGroup view) {
        positionView(view);
        for (int i = 0; i < view.getChildCount(); i++) {
            View v = view.getChildAt(i);
            if (v instanceof ViewGroup) positioningViews((ViewGroup) v);
            positionView(v);
        }
    }

    private void positionView(View it) {
        if (dimensions == null) return;
        if (it instanceof ComponentView) ((ComponentView) it).setDimension(dimensions);
    }

    public <T> ComponentView<T> getObjectComponent() {
        return getComponent(R.id.component);
    }

    public <T> ComponentView<T> getAccessoriesComponent() {
        return getComponent(R.id.accessories);
    }

    public <T> ComponentView<T> getCoverComponent() {
        return getComponent(R.id.cover);
    }



    public <T, X, V> void initData(DimensionData dimensionData, T t, X x, V v) {
        setDimensions(dimensionData);
        getObjectComponent().setData(t);
        getAccessoriesComponent().setData(x);
        getCoverComponent().setData(v);

    }

    public <T> void initData(DimensionData dimensionData, Pair<T, T> t) {
        setDimensions(dimensionData);
        getObjectComponent().setData(t.first);
//        getAccessoriesComponent().setData(t.second);
    }

    public <T> void initUrlData(DimensionData dimensionData, Pair<String, String> t) {
        setDimensions(dimensionData);
        getObjectComponent().setData(t.first);
        getAccessoriesComponent().setData(t.second);
    }

    public <T> void setCoverData(T t) {
        getCoverComponent().setData(t);
    }

    public <T> ComponentView<T> getComponent(int id) {
        View view = this.findViewById(id);
        if (view instanceof ComponentView) {
            return (ComponentView<T>) view;
        }
        return null;
    }

    public View getComponentView(int id) {
        View view = this.findViewById(id);
        if (view instanceof ComponentView) {
            return view;
        }
        return null;
    }


}