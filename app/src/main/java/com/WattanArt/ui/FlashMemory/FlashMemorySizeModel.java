package com.WattanArt.ui.FlashMemory;

import java.io.Serializable;

public class FlashMemorySizeModel implements Serializable {
   private int prod_Id;
    private String priceIn ;
    private String priceOut ;
    private String prod_Name ;

    public FlashMemorySizeModel(int prod_Id, String priceIn, String priceOut, String prod_Name) {
        this.prod_Id = prod_Id;
        this.priceIn = priceIn;
        this.priceOut = priceOut;
        this.prod_Name = prod_Name;
    }

    public int getProd_Id() {
        return prod_Id;
    }

    public void setProd_Id(int prod_Id) {
        this.prod_Id = prod_Id;
    }

    public String getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(String priceIn) {
        this.priceIn = priceIn;
    }

    public String getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(String priceOut) {
        this.priceOut = priceOut;
    }

    public String getProd_Name() {
        return prod_Name;
    }

    public void setProd_Name(String prod_Name) {
        this.prod_Name = prod_Name;
    }
}
