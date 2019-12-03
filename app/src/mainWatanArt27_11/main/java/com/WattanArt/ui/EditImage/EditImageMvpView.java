package com.WattanArt.ui.EditImage;

import com.WattanArt.model.Response.AboutUsResponseModel;
import com.WattanArt.ui.base.MvpView;

/**
 * Created by Android Team on 1/18/2018.
 */

public interface EditImageMvpView extends MvpView {
    void onReplaceImage();
    void onDeleteImage();
}
