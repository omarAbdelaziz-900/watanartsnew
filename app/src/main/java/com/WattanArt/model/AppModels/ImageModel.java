package com.WattanArt.model.AppModels;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.yalantis.ucrop.model.ExifInfo;
import com.zomato.photofilters.imageprocessors.Filter;

/**
 * Created by khaled.badawy on 4/3/2018.
 */

public class ImageModel implements Parcelable {
    String path;
    Bitmap bitmap;
    boolean isSegmented;
    Bitmap filteredBitmap , screenShotImage;
    float currentBrightness;
    float currentContrast;
    float currentLight;
    Filter currentFilter;
    int currentColor;
    float currentRotate;

    boolean isFlipped;
    float currentRatio;
    int typePatternId =1;
    int positionX;
    int positionY;
    float Hue;
    float Saturation;
    float currentScale;

    Uri uri, outputUri;
    int quantity;

    int viewWidth , viewHeight ;

    private float currentRatioIndex;

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public Bitmap getScreenShotImage() {
        return screenShotImage;
    }

    public void setScreenShotImage(Bitmap screenShotImage) {
        this.screenShotImage = screenShotImage;
    }

    public Uri getOutputUri() {
        return outputUri;
    }

    public void setOutputUri(Uri outputUri) {
        this.outputUri = outputUri;
    }

    int imageWidth;
    int imageHeight;

    double factorWidth, factorHeight;


    public double getFactorWidth() {
        if (factorWidth > 1)
            return factorWidth;
        else
            return 1;
    }

    public void setFactorWidth(double factorWidth) {
        this.factorWidth = factorWidth;
    }

    public double getFactorHeight() {
        if (factorHeight > 1)
            return factorHeight;
        else
            return 1;
    }

    public void setFactorHeight(double factorHeight) {
        this.factorHeight = factorHeight;
    }

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

    ExifInfo mExifInfo;
    int mMaxResultImageSizeX;
    int mMaxResultImageSizeY;
    String mImageInputPath;
    String mImageOutputPath;

    RectF mCropRect;
    float[] mCurrentImageCorners;
    float[] mInitialImageCenter;
    float[] mInitialImageCorners;
    float[] mCurrentImageCenter;
    float currentAngle;

    Matrix matrix;

    int mainImageWidth, mainImageHeight;


    public int getMainImageWidth() {
        return mainImageWidth;
    }

    public void setMainImageWidth(int mainImageWidth) {
        this.mainImageWidth = mainImageWidth;
    }

    public int getMainImageHeight() {
        return mainImageHeight;
    }

    public void setMainImageHeight(int mainImageHeight) {
        this.mainImageHeight = mainImageHeight;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public float[] getmInitialImageCenter() {
        return mInitialImageCenter;
    }

    public void setmInitialImageCenter(float[] mInitialImageCenter) {
        this.mInitialImageCenter = mInitialImageCenter;
    }

    public float[] getmInitialImageCorners() {
        return mInitialImageCorners;
    }

    public void setmInitialImageCorners(float[] mInitialImageCorners) {
        this.mInitialImageCorners = mInitialImageCorners;
    }

    public float[] getmCurrentImageCenter() {
        return mCurrentImageCenter;
    }

    public void setmCurrentImageCenter(float[] mCurrentImageCenter) {
        this.mCurrentImageCenter = mCurrentImageCenter;
    }

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

    public float getHue() {
        return Hue;
    }

    public void setHue(float hue) {
        Hue = hue;
    }

    public float getSaturation() {
        return Saturation;
    }

    public void setSaturation(float saturation) {
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

    public int getTypePatternId() {
        return typePatternId;
    }

    public void setTypePatternId(int typePatternId) {
        this.typePatternId = typePatternId;
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

    public ImageModel() {

    }

    public ImageModel(Uri uri, float currentRatio ) {
        this.uri = uri;
        this.currentRatio = currentRatio;
//        this.typePatternId = typePatternId;
    }

    public ImageModel(Bitmap bitmap, float currentRatio, String path) {
        this.bitmap = bitmap;
        this.currentRatio = currentRatio;
        this.path = path;
    }

    protected ImageModel(Parcel in) {
        path = in.readString();
        bitmap = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        isSegmented = in.readByte() != 0x00;
        filteredBitmap = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        currentBrightness = in.readFloat();
        currentContrast = in.readFloat();
        currentLight = in.readFloat();
        currentFilter = (Filter) in.readValue(Filter.class.getClassLoader());
        currentColor = in.readInt();
        currentRotate = in.readFloat();
        isFlipped = in.readByte() != 0x00;
        currentRatio = in.readFloat();
        typePatternId =in.readInt();
        positionX = in.readInt();
        positionY = in.readInt();
        Hue = in.readInt();
        Saturation = in.readInt();
        currentScale = in.readFloat();
        uri = (Uri) in.readValue(Uri.class.getClassLoader());
        quantity = in.readInt();
        mExifInfo = (ExifInfo) in.readValue(ExifInfo.class.getClassLoader());
        mMaxResultImageSizeX = in.readInt();
        mMaxResultImageSizeY = in.readInt();
        mImageInputPath = in.readString();
        mImageOutputPath = in.readString();
        mCropRect = (RectF) in.readValue(RectF.class.getClassLoader());
        currentAngle = in.readFloat();
        isLowResolution = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeValue(bitmap);
        dest.writeByte((byte) (isSegmented ? 0x01 : 0x00));
        dest.writeValue(filteredBitmap);
        dest.writeFloat(currentBrightness);
        dest.writeFloat(currentContrast);
        dest.writeFloat(currentLight);
        dest.writeValue(currentFilter);
        dest.writeInt(currentColor);
        dest.writeFloat(currentRotate);
        dest.writeByte((byte) (isFlipped ? 0x01 : 0x00));
        dest.writeFloat(currentRatio);
        dest.writeInt(typePatternId);
        dest.writeInt(positionX);
        dest.writeInt(positionY);
        dest.writeFloat(Hue);
        dest.writeFloat(Saturation);
        dest.writeFloat(currentScale);
        dest.writeValue(uri);
        dest.writeInt(quantity);
        dest.writeValue(mExifInfo);
        dest.writeInt(mMaxResultImageSizeX);
        dest.writeInt(mMaxResultImageSizeY);
        dest.writeString(mImageInputPath);
        dest.writeString(mImageOutputPath);
        dest.writeValue(mCropRect);
        dest.writeFloat(currentAngle);
        dest.writeByte((byte) (isLowResolution ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public void setCurrentRatioIndex(float currentRatioIndex) {
        this.currentRatioIndex = currentRatioIndex;
    }

    public float getCurrentRatioIndex() {
        return currentRatioIndex;
    }
}

