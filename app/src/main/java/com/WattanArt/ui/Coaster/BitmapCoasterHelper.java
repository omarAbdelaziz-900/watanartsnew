package com.WattanArt.ui.Coaster;

import android.graphics.Bitmap;

public class BitmapCoasterHelper {

    private Bitmap bitmapCoasterCircle=null;
    private Bitmap bitmapCoasterCircleCover=null;
    private Bitmap bitmapCoasterSquare=null;
    private Bitmap bitmapCoasterSquareCover=null;

    private static  BitmapCoasterHelper instance=new BitmapCoasterHelper();

    public BitmapCoasterHelper(){

    }
    public static BitmapCoasterHelper getInstance(){
        if (instance==null)
            instance = new BitmapCoasterHelper();
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }


    public Bitmap getBitmapCoasterCircle() {
        return bitmapCoasterCircle;
    }

    public void setBitmapCoasterCircle(Bitmap bitmapCoasterCircle) {
        this.bitmapCoasterCircle = bitmapCoasterCircle;
    }

    public Bitmap getBitmapCoasterCircleCover() {
        return bitmapCoasterCircleCover;
    }

    public void setBitmapCoasterCircleCover(Bitmap bitmapCoasterCircleCover) {
        this.bitmapCoasterCircleCover = bitmapCoasterCircleCover;
    }

    public Bitmap getBitmapCoasterSquare() {
        return bitmapCoasterSquare;
    }

    public void setBitmapCoasterSquare(Bitmap bitmapCoasterSquare) {
        this.bitmapCoasterSquare = bitmapCoasterSquare;
    }

    public Bitmap getBitmapCoasterSquareCover() {
        return bitmapCoasterSquareCover;
    }

    public void setBitmapCoasterSquareCover(Bitmap bitmapCoasterSquareCover) {
        this.bitmapCoasterSquareCover = bitmapCoasterSquareCover;
    }


}
