package com.WattanArt.ui.CanvasPrint;

import com.WattanArt.ui.base.MvpPresenter;

public interface CanvasPrintMvpPresnter  <V extends CanvasPrintMvpView > extends MvpPresenter<V>{

    void getData(int Language);
}
