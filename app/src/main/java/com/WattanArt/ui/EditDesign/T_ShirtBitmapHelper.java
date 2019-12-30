package com.WattanArt.ui.EditDesign;

import android.graphics.Bitmap;

public class T_ShirtBitmapHelper {
    private Bitmap bitmapFront=null;
    private Bitmap bitmapFrontCover=null;
    private Bitmap bitmapBack=null;
    private Bitmap bitmapBackCover=null;

    private static  T_ShirtBitmapHelper instance=new T_ShirtBitmapHelper();

    public T_ShirtBitmapHelper(){

    }
    public static T_ShirtBitmapHelper getInstance(){
        if (instance==null)
            instance = new T_ShirtBitmapHelper();
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
