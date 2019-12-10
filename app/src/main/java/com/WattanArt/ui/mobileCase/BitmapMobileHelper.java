package com.WattanArt.ui.mobileCase;

import android.graphics.Bitmap;

public class BitmapMobileHelper {

    private static  BitmapMobileHelper instance=new BitmapMobileHelper();

    public BitmapMobileHelper(){

    }

    public static BitmapMobileHelper getInstance(){
        if (instance==null)
            instance = new BitmapMobileHelper();
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }

    private Bitmap bitmapMobile=null;
    private Bitmap bitmapCover=null;
    private Bitmap bitmapCoverMobileWhite=null;


    public Bitmap getBitmapMobile() {
        return bitmapMobile;
    }

    public void setBitmapMobile(Bitmap bitmap) {
        this.bitmapMobile = bitmap;
    }

    public Bitmap getBitmapCover() {
        return bitmapCover;
    }

    public void setBitmapCover(Bitmap bitmapCover) {
        this.bitmapCover = bitmapCover;
    }

    public Bitmap getBitmapCoverMobileWhite() {
        return bitmapCoverMobileWhite;
    }

    public void setBitmapCoverMobileWhite(Bitmap bitmapCoverMobileWhite) {
        this.bitmapCoverMobileWhite = bitmapCoverMobileWhite;
    }
}
