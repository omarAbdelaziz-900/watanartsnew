package com.WattanArt.ui.mobileCase;

public class FontTypeModel {
    int id;
    String fontName;
    int image;

    public FontTypeModel() {
    }
    public FontTypeModel(String fontName) {
        this.fontName = fontName;
    }

    public FontTypeModel(String fontName, int image) {
        this.fontName = fontName;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
