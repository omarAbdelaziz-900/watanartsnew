package com.WattanArt.ui.PublicShipping;

import android.graphics.Bitmap;

import java.util.List;

public class PublicBitmapsModel {

    public String cat_ID;
    public String product_Id;
    public String price_In;
    public String price_Out;
    public int quantitty=1;
    public Bitmap bitmapFront=null;
    public List<PublicBitmapsModel> bitmapList;

    private static  PublicBitmapsModel instance=new PublicBitmapsModel();

    public PublicBitmapsModel(){

    }
    public static PublicBitmapsModel getInstance(){
        if (instance==null)
            instance = new PublicBitmapsModel();
        return instance;
    }

    public PublicBitmapsModel(Bitmap bitmapFront) {
        this.bitmapFront = bitmapFront;
    }

    public PublicBitmapsModel(String cat_ID,String product_Id, String price_In, String price_Out, Bitmap bitmapFront) {
        this.cat_ID = cat_ID;
        this.product_Id = product_Id;
        this.price_In = price_In;
        this.price_Out = price_Out;
        this.bitmapFront = bitmapFront;
    }

    public String getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(String product_Id) {
        this.product_Id = product_Id;
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


    public List<PublicBitmapsModel> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<PublicBitmapsModel> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public String getCat_ID() {
        return cat_ID;
    }

    public void setCat_ID(String cat_ID) {
        this.cat_ID = cat_ID;
    }

    public String getPrice_In() {
        return price_In;
    }

    public void setPrice_In(String price_In) {
        this.price_In = price_In;
    }

    public String getPrice_Out() {
        return price_Out;
    }

    public void setPrice_Out(String price_Out) {
        this.price_Out = price_Out;
    }

    public int getQuantitty() {
        return quantitty;
    }

    public void setQuantitty(int quantitty) {
        this.quantitty = quantitty;
    }
}
