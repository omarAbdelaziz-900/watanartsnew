package com.WattanArt.Utils.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class CustomEditTextBold extends AppCompatEditText {


public CustomEditTextBold(Context context){
        super(context);

        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/"+"GOTHICB_1.TTF");
        this.setTypeface(face);
        }

public CustomEditTextBold(Context context,AttributeSet attrs){
        super(context,attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/"+"GOTHICB_1.TTF");
        this.setTypeface(face);
        }

public CustomEditTextBold(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/"+"GOTHICB_1.TTF");
        this.setTypeface(face);
        }

protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        }
        }
