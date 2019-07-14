package com.WattanArt.Utils.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by khaled.badawy .
 */

public class CustomeButton extends android.support.v7.widget.AppCompatButton {


    public CustomeButton(Context context) {
        super(context);
        init(context);
    }

    public CustomeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void init(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/" + "GOTHIC_2.TTF");
        this.setTypeface(face);
        setStateListAnimator(null);
    }
}