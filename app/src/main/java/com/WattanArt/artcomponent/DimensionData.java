package com.WattanArt.artcomponent;

public class DimensionData {

    public DimensionData(
            int width,
            int height,
            int coverWidth,
            int coverHeight,
            float startCoverX,
            float startCoverY,
            int radius,
            int accessoriesWidth,
            int accessoriesHeight,
            int accessoriesX,
            int accessoriesY
    ) {
        this.imageWidth = width;
        this.imageHeight = height;
        this.coverWidth = coverWidth;
        this.coverHeight = coverHeight;
        this.radius = radius;
        this.accessoriesWidth = accessoriesWidth;
        this.accessoriesHeight = accessoriesHeight;
        this.accessoriesX = accessoriesX;
        this.accessoriesY = accessoriesY;
        this.startCoverX = startCoverX;
        this.startCoverY = startCoverY;
    }
    public DimensionData(
            int width,
            int height,
            int coverWidth,
            int coverHeight,
            int radius,
            int accessoriesWidth,
            int accessoriesHeight,
            int accessoriesX,
            int accessoriesY
    ) {
        this.imageWidth = width;
        this.imageHeight = height;
        this.coverWidth = coverWidth;
        this.coverHeight = coverHeight;
        this.radius = radius;
        this.accessoriesWidth = accessoriesWidth;
        this.accessoriesHeight = accessoriesHeight;
        this.accessoriesX = accessoriesX;
        this.accessoriesY = accessoriesY;
    }



    /**********Variables Dimens****************/
    /*Object Resolution*/
    private int imageWidth = 0;
    private int imageHeight = 0;

    /*Object acessories Resolutions*/
    private int accessoriesWidth = 0;
    private int accessoriesHeight = 0;
    private int accessoriesX = 0;
    private int accessoriesY = 0;

    /*Cover Resolutions*/
    private int coverWidth = 0;
    private int coverHeight = 0;
    private int radius = 0;
    private float startCoverX = 0f;
    private float startCoverY = 0f;

    /*Calculations*/
    public float getCenterX() {
        return imageWidth / 2f;
    }

    public float getCenterY() {
        return imageHeight / 2f;
    }

    public float getStartX() {
        return startCoverX > 0 ? startCoverX : getCenterX() - coverWidth / 2f;
    }

    //    var startX: () -> Float? = {40f}
    public float getStartY() {
        return startCoverY > 0 ? startCoverY : getCenterY() - coverHeight / 2f;
    }
//    var startY: () -> Float? = { 40f }

    public boolean haveAccessories() {
        return accessoriesWidth > 0 && accessoriesHeight > 0;
    }

    /**************************************************/

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getAccessoriesWidth() {
        return accessoriesWidth;
    }

    public void setAccessoriesWidth(int accessoriesWidth) {
        this.accessoriesWidth = accessoriesWidth;
    }

    public int getAccessoriesHeight() {
        return accessoriesHeight;
    }

    public void setAccessoriesHeight(int accessoriesHeight) {
        this.accessoriesHeight = accessoriesHeight;
    }

    public int getAccessoriesX() {
        return accessoriesX;
    }

    public void setAccessoriesX(int accessoriesX) {
        this.accessoriesX = accessoriesX;
    }

    public int getAccessoriesY() {
        return accessoriesY;
    }

    public void setAccessoriesY(int accessoriesY) {
        this.accessoriesY = accessoriesY;
    }

    public int getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(int coverWidth) {
        this.coverWidth = coverWidth;
    }

    public int getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(int coverHeight) {
        this.coverHeight = coverHeight;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getStartCoverX() {
        return startCoverX;
    }

    public void setStartCoverX(float startCoverX) {
        this.startCoverX = startCoverX;
    }

    public float getStartCoverY() {
        return startCoverY;
    }

    public void setStartCoverY(float startCoverY) {
        this.startCoverY = startCoverY;
    }
}