package com.WattanArt.ui.Shipping;

public class InEgyptHelper {

    private static  InEgyptHelper instance=new InEgyptHelper();

    public InEgyptHelper(){

    }
    public static InEgyptHelper getInstance(){
        if (instance==null)
            instance = new InEgyptHelper();
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }
    private boolean isInEgypt;

    public boolean isInEgypt() {
        return isInEgypt;
    }

    public void setInEgypt(boolean inEgypt) {
        isInEgypt = inEgypt;
    }
}
