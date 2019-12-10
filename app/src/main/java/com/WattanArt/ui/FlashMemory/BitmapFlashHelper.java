package com.WattanArt.ui.FlashMemory;

import android.graphics.Bitmap;

public class BitmapFlashHelper {
    private Bitmap bitmapFront=null;
    private Bitmap bitmapFrontCover=null;
    private Bitmap bitmapBack=null;
    private Bitmap bitmapBackCover=null;

    private static  BitmapFlashHelper instance=new BitmapFlashHelper();

    public BitmapFlashHelper(){

    }
    public static BitmapFlashHelper getInstance(){
        if (instance==null)
            instance = new BitmapFlashHelper();
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }


    public Bitmap getBitmapFront() {
        return bitmapFront;
    }

    public void setBitmapFront(Bitmap bitmapFront) {
        this.bitmapFront = bitmapFront;
    }

    public Bitmap getBitmapFrontCover() {
        return bitmapFrontCover;
    }

    public void setBitmapFrontCover(Bitmap bitmapFrontCover) {
        this.bitmapFrontCover = bitmapFrontCover;
    }

    public Bitmap getBitmapBack() {
        return bitmapBack;
    }

    public void setBitmapBack(Bitmap bitmapBack) {
        this.bitmapBack = bitmapBack;
    }

    public Bitmap getBitmapBackCover() {
        return bitmapBackCover;
    }

    public void setBitmapBackCover(Bitmap bitmapBackCover) {
        this.bitmapBackCover = bitmapBackCover;
    }


}
