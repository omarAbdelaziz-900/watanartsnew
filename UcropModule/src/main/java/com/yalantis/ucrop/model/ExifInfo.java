package com.yalantis.ucrop.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Oleksii Shliama [https://github.com/shliama] on 6/21/16.
 */
public class ExifInfo implements Parcelable {

    private int mExifOrientation;
    private int mExifDegrees;
    private int mExifTranslation;

    public ExifInfo(int exifOrientation, int exifDegrees, int exifTranslation) {
        mExifOrientation = exifOrientation;
        mExifDegrees = exifDegrees;
        mExifTranslation = exifTranslation;
    }

    public int getExifOrientation() {
        return mExifOrientation;
    }

    public int getExifDegrees() {
        return mExifDegrees;
    }

    public int getExifTranslation() {
        return mExifTranslation;
    }

    public void setExifOrientation(int exifOrientation) {
        mExifOrientation = exifOrientation;
    }

    public void setExifDegrees(int exifDegrees) {
        mExifDegrees = exifDegrees;
    }

    public void setExifTranslation(int exifTranslation) {
        mExifTranslation = exifTranslation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExifInfo exifInfo = (ExifInfo) o;

        if (mExifOrientation != exifInfo.mExifOrientation) return false;
        if (mExifDegrees != exifInfo.mExifDegrees) return false;
        return mExifTranslation == exifInfo.mExifTranslation;

    }

    @Override
    public int hashCode() {
        int result = mExifOrientation;
        result = 31 * result + mExifDegrees;
        result = 31 * result + mExifTranslation;
        return result;
    }


    protected ExifInfo(Parcel in) {
        mExifOrientation = in.readInt();
        mExifDegrees = in.readInt();
        mExifTranslation = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mExifOrientation);
        dest.writeInt(mExifDegrees);
        dest.writeInt(mExifTranslation);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ExifInfo> CREATOR = new Parcelable.Creator<ExifInfo>() {
        @Override
        public ExifInfo createFromParcel(Parcel in) {
            return new ExifInfo(in);
        }

        @Override
        public ExifInfo[] newArray(int size) {
            return new ExifInfo[size];
        }
    };
}