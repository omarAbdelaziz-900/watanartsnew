package com.WattanArt.artcomponent;



public interface ComponentView<T> {
    void setDimension(DimensionData dimensionData);
    void setData(T data);
}