package com.WattanArt.ui.Category;

public class CategoryItemModel {

    public String name;
    public int img;

    public CategoryItemModel(String name) {
        this.name = name;
    }

    public CategoryItemModel(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public CategoryItemModel(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
