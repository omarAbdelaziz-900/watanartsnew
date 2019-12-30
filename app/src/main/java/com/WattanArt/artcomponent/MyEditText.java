package com.WattanArt.artcomponent;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.WattanArt.R;

import java.lang.reflect.Field;

import static android.R.attr.minWidth;

public class MyEditText extends AppCompatEditText
{


    public MyEditText(Context context) {
        super(context);

        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/" +"GOTHIC_2.TTF");
        this.setTypeface(face);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/" +"GOTHIC_2.TTF");
        this.setTypeface(face);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/" +"GOTHIC_2.TTF");
        this.setTypeface(face);
    }
//    GOTHICB_1.TTF >> BOLD

    protected void onDraw (Canvas canvas) {
        canvas.setDrawFilter(mDrawFilter);
        super.onDraw(canvas);
    }
    private PaintFlagsDrawFilter mDrawFilter =
            new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);


    public boolean isTouchable() {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return isTouchable() && super.dispatchTouchEvent(event);
    }

}