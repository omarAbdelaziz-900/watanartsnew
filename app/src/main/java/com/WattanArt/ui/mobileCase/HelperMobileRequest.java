package com.WattanArt.ui.mobileCase;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class HelperMobileRequest {

    private static  HelperMobileRequest instance=new HelperMobileRequest();

    public HelperMobileRequest(){

    }

    public static HelperMobileRequest getInstance(){
        if (instance==null)
            instance = new HelperMobileRequest();
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }


    private List<Integer> ids=new ArrayList<>();
    private List<Bitmap> coverBitmaps=new ArrayList<>();
    private List<Bitmap> screenShootBitmaps=new ArrayList<>();

    public HelperMobileRequest(List<Integer> ids, List<Bitmap> coverBitmaps, List<Bitmap> screenShootBitmaps) {
        this.ids = ids;
        this.coverBitmaps = coverBitmaps;
        this.screenShootBitmaps = screenShootBitmaps;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Bitmap> getCoverBitmaps() {
        return coverBitmaps;
    }

    public void setCoverBitmaps(List<Bitmap> coverBitmaps) {
        this.coverBitmaps = coverBitmaps;
    }

    public List<Bitmap> getScreenShootBitmaps() {
        return screenShootBitmaps;
    }

    public void setScreenShootBitmaps(List<Bitmap> screenShootBitmaps) {
        this.screenShootBitmaps = screenShootBitmaps;
    }
}
