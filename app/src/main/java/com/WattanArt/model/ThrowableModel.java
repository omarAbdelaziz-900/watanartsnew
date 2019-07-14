package com.WattanArt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by khaled.badawy on 5/20/2018.
 */

public class ThrowableModel {
    @SerializedName("boundary")
    @Expose
    private Boundary boundary;
    @SerializedName("contentLength")
    @Expose
    private Integer contentLength;
    @SerializedName("contentType")
    @Expose
    private ContentType contentType;
    @SerializedName("originalType")
    @Expose
    private OriginalType originalType;
    @SerializedName("parts")
    @Expose
    private List<Part> parts = null;

    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public OriginalType getOriginalType() {
        return originalType;
    }

    public void setOriginalType(OriginalType originalType) {
        this.originalType = originalType;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public class Part {

        @SerializedName("headers")
        @Expose
        private Headers headers;

        public Headers getHeaders() {
            return headers;
        }

        public void setHeaders(Headers headers) {
            this.headers = headers;
        }

    }

    public class OriginalType {

        @SerializedName("mediaType")
        @Expose
        private String mediaType;
        @SerializedName("subtype")
        @Expose
        private String subtype;
        @SerializedName("type")
        @Expose
        private String type;

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    public class Headers {

        @SerializedName("namesAndValues")
        @Expose
        private List<String> namesAndValues = null;

        public List<String> getNamesAndValues() {
            return namesAndValues;
        }

        public void setNamesAndValues(List<String> namesAndValues) {
            this.namesAndValues = namesAndValues;
        }

    }

    public class ContentType {

        @SerializedName("mediaType")
        @Expose
        private String mediaType;
        @SerializedName("subtype")
        @Expose
        private String subtype;
        @SerializedName("type")
        @Expose
        private String type;

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    public class Boundary {

        @SerializedName("data")
        @Expose
        private List<Integer> data = null;

        public List<Integer> getData() {
            return data;
        }

        public void setData(List<Integer> data) {
            this.data = data;
        }
    }
}
