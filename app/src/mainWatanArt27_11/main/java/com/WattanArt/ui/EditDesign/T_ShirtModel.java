package com.WattanArt.ui.EditDesign;

public class T_ShirtModel {
    public String name;
    public int img;

    public T_ShirtModel(String name) {
        this.name = name;
    }

    public T_ShirtModel(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }
}
