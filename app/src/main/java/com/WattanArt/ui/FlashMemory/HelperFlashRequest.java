package com.WattanArt.ui.FlashMemory;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class HelperFlashRequest {

    private static  HelperFlashRequest instance=new HelperFlashRequest();

    public HelperFlashRequest(){

    }

    public static HelperFlashRequest getInstance(){
        if (instance==null)
            instance = new HelperFlashRequest();
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }


    private List<Integer> ids=new ArrayList<>();
    private List<Bitmap> flashCoverBitmapsFront=new ArrayList<>();
    private List<Bitmap> flashscreenShootBitmapsFront=new ArrayList<>();
    private List<Bitmap> flashCoverBitmapsBack=new ArrayList<>();
    private List<Bitmap> flashscreenShootBitmapsBack=new ArrayList<>();



    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Bitmap> getFlashCoverBitmapsFront() {
        return flashCoverBitmapsFront;
    }

    public void setFlashCoverBitmapsFront(List<Bitmap> flashCoverBitmapsFront) {
        this.flashCoverBitmapsFront = flashCoverBitmapsFront;
    }

    public List<Bitmap> getFlashscreenShootBitmapsFront() {
        return flashscreenShootBitmapsFront;
    }

    public void setFlashscreenShootBitmapsFront(List<Bitmap> flashscreenShootBitmapsFront) {
        this.flashscreenShootBitmapsFront = flashscreenShootBitmapsFront;
    }

    public List<Bitmap> getFlashCoverBitmapsBack() {
        return flashCoverBitmapsBack;
    }

    public void setFlashCoverBitmapsBack(List<Bitmap> flashCoverBitmapsBack) {
        this.flashCoverBitmapsBack = flashCoverBitmapsBack;
    }

    public List<Bitmap> getFlashscreenShootBitmapsBack() {
        return flashscreenShootBitmapsBack;
    }

    public void setFlashscreenShootBitmapsBack(List<Bitmap> flashscreenShootBitmapsBack) {
        this.flashscreenShootBitmapsBack = flashscreenShootBitmapsBack;
    }
}
