package com.WattanArt.artcomponent;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.WattanArt.R;
import com.bumptech.glide.Glide;

import carbon.widget.ImageView;

public class CircleImageViewOmar  extends CoverTemplate<String> {

    private static final int DEF_PRESS_HIGHLIGHT_COLOR = 0x32000000;

    private Shader mBitmapShader;
    private Matrix mShaderMatrix;

    private RectF mBitmapDrawBounds;
    private RectF mStrokeBounds;

    private Bitmap mBitmap;

    private Paint mBitmapPaint;
    private Paint mStrokePaint;
    private Paint mPressedPaint;

    private boolean mInitialized;
    private boolean mPressed;
    private boolean mHighlightEnable;

    public CircleImageViewOmar(Context context) {
        this(context, null);
    }

    public CircleImageViewOmar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        int strokeColor = Color.TRANSPARENT;
        float strokeWidth = 0;
        boolean highlightEnable = true;
        int highlightColor = DEF_PRESS_HIGHLIGHT_COLOR;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageViewOmar, 0, 0);

//            strokeColor = a.getColor(R.styleable.CircleImageViewOmar_strokeColor, Color.TRANSPARENT);
//            strokeWidth = a.getDimensionPixelSize(R.styleable.CircleImageViewOmar_strokeWidth, 0);
//            highlightEnable = a.getBoolean(R.styleable.CircleImageViewOmar_highlightEnable, true);
//            highlightColor = a.getColor(R.styleable.CircleImageViewOmar_highlightColor, DEF_PRESS_HIGHLIGHT_COLOR);

            a.recycle();
        }

        mShaderMatrix = new Matrix();
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokeBounds = new RectF();
        mBitmapDrawBounds = new RectF();
        mStrokePaint.setColor(strokeColor);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(strokeWidth);

        mPressedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPressedPaint.setColor(highlightColor);
        mPressedPaint.setStyle(Paint.Style.FILL);

        mHighlightEnable = highlightEnable;
        mInitialized = true;
    }

    @Override
    int getDimenWidth() {
        return 0;
    }

    @Override
    int getDimenHeight() {
        return 0;
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
}
