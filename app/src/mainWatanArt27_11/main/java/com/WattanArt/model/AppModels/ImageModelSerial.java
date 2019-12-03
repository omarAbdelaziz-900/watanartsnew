

package com.WattanArt.model.AppModels;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;

import com.yalantis.ucrop.model.ExifInfo;
import com.zomato.photofilters.imageprocessors.Filter;

import java.io.Serializable;

/**
 * Created by khaled.badawy on 4/3/2018.
 */

public class ImageModelSerial implements Serializable {
    String path;
    Bitmap bitmap;
    boolean isSegmented;
    Bitmap filteredBitmap;
    float currentBrightness;
    float currentContrast;
    float currentLight;
    Filter currentFilter;
    int currentColor;
    float currentRotate;

    boolean isFlipped;
    float currentRatio;
    int positionX;
    int positionY;
    int Hue;
    int Saturation;
    float currentScale;

    Uri uri;
    int quantity;


    ExifInfo mExifInfo;
    int mMaxResultImageSizeX;
    int mMaxResultImageSizeY;
    String mImageInputPath;
    String mImageOutputPath;

    RectF mCropRect;
    float[] mCurrentImageCorners;
    float currentAngle;

    public ExifInfo getmExifInfo() {
        return mExifInfo;
    }

    public void setmExifInfo(ExifInfo mExifInfo) {
        this.mExifInfo = mExifInfo;
    }

    public int getmMaxResultImageSizeX() {
        return mMaxResultImageSizeX;
    }

    public void setmMaxResultImageSizeX(int mMaxResultImageSizeX) {
        this.mMaxResultImageSizeX = mMaxResultImageSizeX;
    }

    public int getmMaxResultImageSizeY() {
        return mMaxResultImageSizeY;
    }

    public void setmMaxResultImageSizeY(int mMaxResultImageSizeY) {
        this.mMaxResultImageSizeY = mMaxResultImageSizeY;
    }

    public String getmImageInputPath() {
        return mImageInputPath;
    }

    public void setmImageInputPath(String mImageInputPath) {
        this.mImageInputPath = mImageInputPath;
    }

    public String getmImageOutputPath() {
        return mImageOutputPath;
    }

    public void setmImageOutputPath(String mImageOutputPath) {
        this.mImageOutputPath = mImageOutputPath;
    }

    public RectF getmCropRect() {
        return mCropRect;
    }

    public void setmCropRect(RectF mCropRect) {
        this.mCropRect = mCropRect;
    }

    public float[] getmCurrentImageCorners() {
        return mCurrentImageCorners;
    }

    public void setmCurrentImageCorners(float[] mCurrentImageCorners) {
        this.mCurrentImageCorners = mCurrentImageCorners;
    }

    public float getCurrentAngle() {
        return currentAngle;
    }

    public void setCurrentAngle(float currentAngle) {
        this.currentAngle = currentAngle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSegmented() {
        return isSegmented;
    }

    public void setSegmented(boolean segmented) {
        isSegmented = segmented;
    }


    public Bitmap getFilteredBitmap() {
        return filteredBitmap;
    }

    public void setFilteredBitmap(Bitmap filteredBitmap) {
        this.filteredBitmap = filteredBitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    boolean isLowResolution;

    public int getHue() {
        return Hue;
    }

    public void setHue(int hue) {
        Hue = hue;
    }

    public int getSaturation() {
        return Saturation;
    }

    public void setSaturation(int saturation) {
        Saturation = saturation;
    }

    public boolean isLowResolution() {
        return isLowResolution;
    }

    public void setLowResolution(boolean lowResolution) {
        isLowResolution = lowResolution;
    }

    public float getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(float currentRatio) {
        this.currentRatio = currentRatio;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
    }

    public float getCurrentBrightness() {
        return currentBrightness;
    }

    public void setCurrentBrightness(float currentBrightness) {
        this.currentBrightness = currentBrightness;
    }

    public float getCurrentRotate() {
        return currentRotate;
    }

    public void setCurrentRotate(float currentRotate) {
        this.currentRotate = currentRotate;
    }

    public float getCurrentContrast() {
        return currentContrast;
    }

    public void setCurrentContrast(float currentContrast) {
        this.currentContrast = currentContrast;
    }

    public float getCurrentLight() {
        return currentLight;
    }

    public void setCurrentLight(float currentLight) {
        this.currentLight = currentLight;
    }

    public Filter getCurrentFilter() {
        return currentFilter;
    }

    public void setCurrentFilter(Filter currentFilter) {
        this.currentFilter = currentFilter;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageModelSerial() {

    }

    public ImageModelSerial(Uri uri, float currentRatio) {
        this.uri = uri;
        this.currentRatio = currentRatio;
    }

    public ImageModelSerial(Bitmap bitmap, float currentRatio, String path) {
        this.bitmap = bitmap;
        this.currentRatio = currentRatio;
        this.path = path;
    }

}


