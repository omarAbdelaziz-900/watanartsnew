package com.WattanArt.ui.ActivityFeed;

public class ItemsOfTypesModel {

    private String name;
    private int image_drawable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage_drawable() {
        return image_drawable;
    }

    public void setImage_drawable(int image_drawable) {
        this.image_drawable = image_drawable;
    }

    public ItemsOfTypesModel(String name, int image_drawable) {
        this.name = name;
        this.image_drawable = image_drawable;
    }
}
