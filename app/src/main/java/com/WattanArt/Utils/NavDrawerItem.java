package com.WattanArt.Utils;


public class NavDrawerItem {




    public String title;
    public int resourceId;

    public NavDrawerItem(String title, int resId) {
        this.title = title;
        this.resourceId = resId;
    }

    @Override
    public String toString() {
        return " " + title;
    }


}
