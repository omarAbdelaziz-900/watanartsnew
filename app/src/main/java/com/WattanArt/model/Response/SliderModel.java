package com.WattanArt.model.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SliderModel extends BaseModel {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }


    public class Result implements Parcelable {

        @SerializedName("SliderID")
        @Expose
        private Integer sliderID;
        @SerializedName("TitleAr")
        @Expose
        private Object titleAr;
        @SerializedName("TitleEn")
        @Expose
        private Object titleEn;
        @SerializedName("DescriptionAr")
        @Expose
        private String descriptionAr;
        @SerializedName("DescriptionEn")
        @Expose
        private Object descriptionEn;
        @SerializedName("SliderUrl")
        @Expose
        private String sliderUrl;
        @SerializedName("SliderImage")
        @Expose
        private String sliderImage;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("Language")
        @Expose
        private Integer language;
        @SerializedName("Base64")
        @Expose
        private Object base64;
        @SerializedName("Extension")
        @Expose
        private Object extension;
        @SerializedName("SharedLink")
        @Expose
        private Object sharedLink;
        @SerializedName("TotalRows")
        @Expose
        private Integer totalRows;

        protected Result(Parcel in) {
            if (in.readByte() == 0) {
                sliderID = null;
            } else {
                sliderID = in.readInt();
            }
            descriptionAr = in.readString();
            sliderUrl = in.readString();
            sliderImage = in.readString();
            byte tmpIsActive = in.readByte();
            isActive = tmpIsActive == 0 ? null : tmpIsActive == 1;
            createDate = in.readString();
            if (in.readByte() == 0) {
                language = null;
            } else {
                language = in.readInt();
            }
            if (in.readByte() == 0) {
                totalRows = null;
            } else {
                totalRows = in.readInt();
            }
        }

        public final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };

        public Integer getSliderID() {
            return sliderID;
        }

        public void setSliderID(Integer sliderID) {
            this.sliderID = sliderID;
        }

        public Object getTitleAr() {
            return titleAr;
        }

        public void setTitleAr(Object titleAr) {
            this.titleAr = titleAr;
        }

        public Object getTitleEn() {
            return titleEn;
        }

        public void setTitleEn(Object titleEn) {
            this.titleEn = titleEn;
        }

        public String getDescriptionAr() {
            return descriptionAr;
        }

        public void setDescriptionAr(String descriptionAr) {
            this.descriptionAr = descriptionAr;
        }

        public Object getDescriptionEn() {
            return descriptionEn;
        }

        public void setDescriptionEn(Object descriptionEn) {
            this.descriptionEn = descriptionEn;
        }

        public String getSliderUrl() {
            return sliderUrl;
        }

        public void setSliderUrl(String sliderUrl) {
            this.sliderUrl = sliderUrl;
        }

        public String getSliderImage() {
            return sliderImage;
        }

        public void setSliderImage(String sliderImage) {
            this.sliderImage = sliderImage;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Integer getLanguage() {
            return language;
        }

        public void setLanguage(Integer language) {
            this.language = language;
        }

        public Object getBase64() {
            return base64;
        }

        public void setBase64(Object base64) {
            this.base64 = base64;
        }

        public Object getExtension() {
            return extension;
        }

        public void setExtension(Object extension) {
            this.extension = extension;
        }

        public Object getSharedLink() {
            return sharedLink;
        }

        public void setSharedLink(Object sharedLink) {
            this.sharedLink = sharedLink;
        }

        public Integer getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(Integer totalRows) {
            this.totalRows = totalRows;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (sliderID == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(sliderID);
            }
            dest.writeString(descriptionAr);
            dest.writeString(sliderUrl);
            dest.writeString(sliderImage);
            dest.writeByte((byte) (isActive == null ? 0 : isActive ? 1 : 2));
            dest.writeString(createDate);
            if (language == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(language);
            }
            if (totalRows == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(totalRows);
            }
        }
    }
}