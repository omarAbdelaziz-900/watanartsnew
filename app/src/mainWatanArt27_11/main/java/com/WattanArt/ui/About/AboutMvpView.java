package com.WattanArt.ui.About;

import com.WattanArt.model.Response.AboutModel;
import com.WattanArt.model.Response.AboutUsResponseModel;
import com.WattanArt.ui.base.MvpView;

/**
 * Created by Android Team on 1/18/2018.
 */

public interface AboutMvpView extends MvpView {
    void UpdateUi(Object aboutUsData);
}
